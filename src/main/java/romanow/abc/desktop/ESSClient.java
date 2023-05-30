package romanow.abc.desktop;

import retrofit2.Call;
import retrofit2.Response;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPISecondClient;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.DBRequest;
import romanow.abc.core.UniException;
import romanow.abc.core.Utils;
import romanow.abc.core.constants.Values;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.EntityRefList;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.subject2area.*;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.core.entity.users.User;
import romanow.abc.core.script.*;
import romanow.abc.core.utils.Pair;
import romanow.abc.drivers.ModBusClientProxyDriver;
import romanow.abc.drivers.ModBusMemoryEmulator;

import java.util.ArrayList;
import java.util.HashMap;

import static romanow.abc.core.constants.Values.*;
import static romanow.abc.core.constants.ValuesBase.UserSuperAdminType;
import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;
import static romanow.abc.desktop.BasePanel.EventPLMOn;


public class ESSClient extends Client {
    ESS2Architecture deployed=null;             // Развернутая архитектура
    ESS2View currentView=null;                  // Текущий вид
    long mainServerNodeId = 0;                  // oid текущего узла для ДЦ
    I_ModbusGroupDriver plm= new ModBusMemoryEmulator();
    MetaExternalSystem meta=null;               // Чтобы не удалять 1.0 (ESSMetaPanel ESSMainServiceGUIPanel
    ArrayList<ESSNode> nodes = new ArrayList<>();
    AccessManager manager;
    RestAPIESS2 service2;
    public ESSClient(){
        this(true);
        }
    public ESSClient(boolean setLog){
        super(setLog);
        Values.init();
        setLoginName(Values.env().superUser().getLoginPhone());
        setPassword(Values.env().superUser().getPassword());
        addIP("10.200.200.70");
        addIP("10.200.200.71");
        }
    public void initPanels() {
        super.initPanels();
        panelDescList.add(new PanelDescriptor("Настройки СНЭЭ", ESSWorkSettingsPanel.class,new int[]
                {UserSuperAdminType,UserESSServiceEngeneer,UserESSEngeneer}));
        // Обязательна мета-панель, т.к. запускается соединение с ПЛК при инициализации
        panelDescList.add(new PanelDescriptor("Оператор", ESSServiceGUIPanel.class,new int[]
                {UserSuperAdminType,UserESSServiceEngeneer,UserESSEngeneer,UserESSOperator}));
        panelDescList.add(new PanelDescriptor("Мета-данные", ESSMetaPanel.class,new int[]
                {UserSuperAdminType,UserESSServiceEngeneer,UserESSEngeneer,UserESSOperator}));
        panelDescList.add(new PanelDescriptor("Сервер интегратора", ESSMainServerPanel.class,new int[]
                {UserSuperAdminType,UserESSServiceEngeneer}));
        panelDescList.add(new PanelDescriptor("Интегратор", ESSMainServiceGUIPanel.class,new int[]
                {UserSuperAdminType,UserESSServiceEngeneer,UserESSEngeneer,UserESSOperator}));
        panelDescList.add(new PanelDescriptor("Тренды", ESSTrendPanel.class,new int[]
                {UserSuperAdminType, UserAdminType,UserESSServiceEngeneer,UserESSEngeneer,UserESSOperator}));
        }
    //-------------------------------------------------------------------------------------------------------
    @Override
    public void onLoginSuccess(){
        try {
            service2 = startSecondClient(getServerIP(),""+getServerPort());
            manager = new AccessManager(loginUser);
            getWorkSettings();
            } catch (UniException e) {
                popup("Ошибка соединения: " +e.toString());
                }
        }

    public void setExternalData(String token, User uu, WorkSettings ws0, RestAPIBase service0, RestAPIESS2 service20, boolean localUser0){
        debugToken = token;
        loginUser = uu;
        manager = new AccessManager(loginUser);
        workSettings = ws0;
        service = service0;
        service2 = service20;
        localUser = localUser0;
        }
    public RestAPIESS2 startSecondClient(String ip, String port) throws UniException {
        RestAPIESS2 service = RestAPISecondClient.startClient(ip,port);
        localUser = ip.equals("localhost") || port.equals("127.0.0.1");
        return service;
        }
    //----------------------- Загрузка мета-данных для всех клиентов ---------------------------------------
    public ESS2Architecture loadFullArchitecture(long oid){
        ESS2Architecture arch = new ESS2Architecture();
        try {
            Response<DBRequest> res = service.getEntity(debugToken,"ESS2Architecture",oid,4).execute();
            if (!res.isSuccessful()){
                if (res.code()== ValuesBase.HTTPAuthorization)
                    arch.addErrorData("Сеанс закрыт " + Utils.httpError(res));
                else
                    arch.addErrorData("Ошибка " + res.message()+" ("+res.code()+") "+res.errorBody().string());
                return arch;
            }
            DBRequest request = res.body();
            arch = (ESS2Architecture)  request.get(gson);
            Artifact artifact;
            for(ESS2Equipment equipment : arch.getEquipments()){
                ESS2MetaFile metaFile = equipment.getMetaFile().getRef();
                artifact = metaFile.getFile().getRef();
                Meta2XML xml = loadXMLArtifact(artifact);
                arch.addErrorData(xml);
                equipment.setEquipment((Meta2Equipment) xml);
                }
            for(ESS2View view : arch.getViews()){
                ESS2MetaFile metaFile = view.getMetaFile().getRef();
                artifact = metaFile.getFile().getRef();
                Meta2XML xml = loadXMLArtifact(artifact);
                arch.addErrorData(xml);
                view.setView((Meta2GUIView)xml);
                }
            return arch;
        } catch (Exception ex) {
            arch.addErrorData(Utils.createFatalMessage(ex));
            return arch;
            }
        }
    //------------------------------------------------------------------------------------------------------
    public Meta2XML loadXMLArtifact(Artifact art){
        Meta2XML entity = new Meta2XML();
        Pair<String,String> vv = loadFileAsStringSync(art);
        if (vv.o1!=null) {
            entity.addErrorData(vv.o1);
        }
        else{
            entity = (Meta2XML) new Meta2XStream().fromXML(vv.o2);
            System.out.println(entity.getTitle());
            entity.setHigh(null);
            entity.createMap();
            entity.testLocalConfiguration();
            }
        return entity;
        }
    //-------------------------------------------------------------------------------------------------------
    public boolean loadDeployedArchitecture(long oid, int state) {
        deployed = loadFullArchitecture(oid);
        deployed.setArchitectureState(state);
        deployed.testFullArchitecture();
        ModBusClientProxyDriver driver = new ModBusClientProxyDriver();
        HashMap<String,String> map = new HashMap<>();
        map.put("token",debugToken);
        Object oo[]={service, service2,this};
        try {
            driver.openConnection(oo,map);
            for (ESS2Equipment equipment : deployed.getEquipments()) {
                equipment.createFullEquipmentPath();
            }
            for(ESS2Device device : deployed.getDevices()){         // Настроить прокси
                device.setDriver(driver);
            }
        } catch (Exception ee){
            popup("Ошибка драйвера БД: "+ee.toString());
            return false;
            }
        return true;
        }
    //------------------------------------------------------------------------------------------------------
    public void refreshArchtectureState(){
        new APICall<ArrayList<Long>>(this) {
            @Override
            public Call<ArrayList<Long>> apiFun() {
                return service2.getArchitectureState(debugToken);
            }
            @Override
            public void onSucess(ArrayList<Long> val) {
                int state = val.get(0).intValue();
                popup("Состояние "+Values.constMap().getGroupMapByValue("ArchState").get(state).title());
                if (state==Values.ASNotDeployed)
                    return;
                long oid = val.get(1);
                loadDeployedArchitecture(oid, state);
                deployed.setArchitectureState(state);
                }
            };
        }
    //-------------------------------------------------------------------------------------------------------
    public Syntax compileScriptLocal(ESS2ScriptFile scriptFile,String src, boolean trace){
        ArrayList<String> lines = new ArrayList<>();
        while(true){
            int idx=src.indexOf('\n');
            if (idx==-1){
                lines.add(src);
                lines.add("#");
                break;
                }
            lines.add(src.substring(0,idx-1));
            src = src.substring(idx+1);
            }
        Scaner lex = new Scaner();
        lex.open(lines);
        Syntax SS = new Syntax(lex) {
            @Override
            public void createFunctionMap() {
                createFunctionMap(ValuesBase.StdScriptFunPackage,Values.constMap().getGroupList("ScriptFunStd"));
                createFunctionMap(false,Values.ESS2ScriptFunDesktop,Values.constMap().getGroupList("ScriptFunGUI"));
                }
            };
        FunctionCode ff = SS.compile();
        if (trace)
            System.out.print(ff);
        System.out.println("errors: "+SS.getErrorList().size());
        for(CompileError error : SS.getErrorList())
            System.out.println(error);
        return SS;
        }
    //--------------------------------------------------------------------------------------------------------
    private void preCompileLocalScriptsAsync(boolean trace){
        for(ESS2ScriptFile scriptFile : deployed.getScripts()) {
            scriptFile.setScriptCode(null);
            scriptFile.setValid(false);
            if (!scriptFile.isServerScript() && scriptFile.isPreCompiled() && scriptFile.getScriptType() == Values.STCalcClient) {
                loadFileAsString(scriptFile.getFile().getRef(), new I_DownLoadString() {
                    @Override
                    public void onSuccess(String ss) {
                        Syntax SS = compileScriptLocal(scriptFile, ss,trace);
                        boolean res = SS.getErrorList().size()==0;
                        if (res)
                            scriptFile.setScriptCode(new CallContext(SS, deployed));
                        scriptFile.setValid(res);
                        System.out.println("Скрипт " + scriptFile.getShortName() + " " + scriptFile.getTitle() +
                                (res ? "" : " не")+" скомпилировался");
                        }
                    @Override
                    public void onError(String mes) {
                        System.out.println("Скрипт " + scriptFile.getShortName() + " " + scriptFile.getTitle() +
                                " не загрузился");
                        System.out.println(mes);
                        }
                    });
                }
            }
        }
    //------------------------------------------------------------------------------------------------------
    private void preCompileLocalScripts(boolean trace){
        for(ESS2ScriptFile scriptFile : deployed.getScripts()) {
            scriptFile.setScriptCode(null);
            scriptFile.setValid(false);
            if (!scriptFile.isServerScript() && scriptFile.isPreCompiled() && scriptFile.getScriptType() == Values.STCalcClient) {
                Pair<String, String> res = loadFileAsStringSync(scriptFile.getFile().getRef());
                if (res.o1 != null) {
                    System.out.println("Скрипт " + scriptFile.getShortName() + " " + scriptFile.getTitle() +
                            " не загрузился");
                    System.out.println(res.o1);
                } else {
                    Syntax SS = compileScriptLocal(scriptFile, res.o2,trace);
                    boolean res2 = SS.getErrorList().size() == 0;
                    if (res2)
                        scriptFile.setScriptCode(new CallContext(SS, deployed));
                    scriptFile.setValid(res2);
                    System.out.println("Скрипт " + scriptFile.getShortName() + " " + scriptFile.getTitle() +
                            (res2 ? "" : " не") + " скомпилировался");
                   }
                }
            }
        }
    //------------------------------------------------------------------------------------------------------
    private void setLocalEnvValues(ESS2EnvValuesList oo){
        EntityRefList<ESS2EnvValue> list = deployed.getEnvValues();
        for(ESS2EnvValue value : list)
            value.setDone(false);
        for(Pair<String,ArrayList<Double>> vv : oo.getList()){
            ESS2EnvValue value = list.getByName(vv.o1);
            if (value==null) {
                System.out.println("Не найдена локальная переменная окружения "+vv.o1);
                continue;
                }
            value.setEnvValues(vv.o2);
            value.setDone(true);
            }
        for(ESS2EnvValue value : list){
            if (!value.isDone())
                System.out.println("Не инициализирована локальная переменная окружения "+ value.getShortName());
            else
                System.out.println("Локальная переменная окружения "+ value.toString());
            }
        for(ESS2Equipment equipment : deployed.getEquipments())
            for(Meta2Register reg : equipment.getEquipment().getRegList().getList()){
                String envVarName = reg.getEnvVar();
                if (envVarName==null || envVarName.length()==0)
                    continue;
                ArrayList<Double> values = new ArrayList<>();
                    for(int logUnit=0;logUnit<equipment.getLogUnits().size();logUnit++) {
                        ESS2EnvValue value = deployed.getEnvValues().getByName(envVarName + logUnit);
                        if (value == null || !value.isDone())
                            System.out.println("Не найдена переменная окружения " + (envVarName + logUnit) + " для регистра " + equipment.getShortName() + "." + toHex(reg.getRegNum()));
                        else
                            values.add(value.getEnvValues().get(0));
                        }
                    reg.setEnvVarValue(values);
                    }
            }
    //------------------------------------------------------------------------------------------------------
    public void setRenderingOn() {
        for(ESS2View view : deployed.getViews()){
           if (view.getMetaFile().getRef().getMetaType()== MTViewFullScreen){
                mainServerNodeId = 0;
                setRenderingOn(0,view,true);
                return;
                }
            }
        System.out.println("Не найден ЧМИ для Desktop");
        }
    public void setRenderingOn(long nodeOid,ESS2View view, boolean trace) {
        currentView = view;
        mainServerNodeId = nodeOid;
        preCompileLocalScriptsAsync(trace);
        new APICall<ESS2EnvValuesList>(this){
            @Override
            public Call<ESS2EnvValuesList> apiFun() {
                if (nodeOid==0)
                    return service2.getEnvValues(debugToken);
                else
                    return service2.getEnvValuesNode(debugToken,nodeOid);
                }
            @Override
            public void onSucess(ESS2EnvValuesList oo) {
                setLocalEnvValues(oo);
                }
            };
        sendEvent(EventPLMOn,0);
        }

    //-------------------------------------------------------------------------------------------------------
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Values.init();
                new ESSClient().setVisible(false);
            }
        });
    }
}
