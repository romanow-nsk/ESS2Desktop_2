/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import okhttp3.ResponseBody;
import retrofit2.Response;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.DBRequest;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.IntegerList;
import romanow.abc.core.constants.Values;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.baseentityes.JBoolean;
import romanow.abc.core.entity.metadata.Meta2GUIForm;
import romanow.abc.core.entity.metadata.render.I_ContextBack;
import romanow.abc.core.entity.metadata.render.ScreenMode;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUICollection;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.view.GUIElementsFactory;
import romanow.abc.desktop.view.GUIElement;
import romanow.abc.desktop.view.I_GUIEvent;
import romanow.abc.core.utils.OwnDateTime;
import romanow.abc.core.utils.Pair;
import romanow.abc.desktop.console.APICallC;
import romanow.abc.desktop.module.Module;
import retrofit2.Call;
import romanow.abc.desktop.view2.FormContext2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author romanow
 */
public class ESSMainServiceGUIPanel extends ESSBasePanel {
    private final static String buttonToMain = "/refresh.png";
    private final static String buttonLogout = "/login.png";
    private final static String buttonInfoOn = "/question.png";
    private final static String buttonInfoOff = "/question_gray.png";
    private final static String mainFormName="СУ АГЭУ";
    //private MetaGUIForm context.getForm().= null;
    private final static int levelYCoords[]={0,10,55,620,575};
    public final static int buttonXSize=100;
    public final static int buttonYSize=40;
    public final static int buttonSpace=5;
    private Module module=null;
    private GUIElementsFactory factory = new GUIElementsFactory();
    private volatile Thread guiLoop=null;
    private volatile boolean shutDown=false;
    private I_Button logoutCallBack = null;
    private OwnDateTime userLoginTime = new OwnDateTime();
    private ArrayList<ESSNodeViewData> nodeList = new ArrayList<>();
    private JScrollPane NodeListPanel=null;
    private JPanel panel=null;
    private FormContext2 context = new FormContext2(new I_ContextBack() {       // TODO - тут все от предыдущей версии
        @Override
        public void popup(String ss) {
            }
        @Override
        public void repaintView() {
            }
        @Override
        public void repaintValues() {
            }
        @Override
        public int getAcccessLevel() {
            return 0;
            }
        @Override
        public void forceRepaint() {
            }
        });

    public void setLogoutCallBack(I_Button logoutCallBack) {
        this.logoutCallBack = logoutCallBack; }
    public boolean isMainMode(){ return true; }
    public boolean isESSMode(){ return false; }
    public ESSMainServiceGUIPanel() {
        initComponents();
        }
    public ESSMainServiceGUIPanel(ScreenMode screenMode) {
        initComponents();
        context.setScreen(screenMode);
        }
    public void initPanel(MainBaseFrame main){
        super.initPanel(main);
        ESSClient main0 = (ESSClient)main;
        context.setLocalUser(main0.isLocalUser());
        context.setSuperUser(main0.loginUser().getTypeId()== Values.UserSuperAdminType);
        context.setManager(main0.manager);
        context.setService(main0.service);
        context.setToken(main0.debugToken);
        context.setValid(true);
        userLoginTime = new OwnDateTime();
        refreshNodeList();
        }
    private void setForm(Meta2GUIForm aaa){
        context.setForm(aaa);
        repaintView();
        }
     private synchronized void breakCycle(){
        shutDown=true;
        if (guiLoop!=null)
            guiLoop.interrupt();
        guiLoop=null;
        }

     private I_GUIEvent retryPaintValues = new I_GUIEvent(){
         @Override
         public void onEnter(GUIElement element, int iParam, String sParam) {
             guiLoop.interrupt();
             userLoginTime = new OwnDateTime();
             repaintView();
            }
        };
     private synchronized void repaintCycle(){
        //popup("ЧМИ обновлен");
        shutDown = false;
         java.awt.EventQueue.invokeLater(new Runnable() {
             public void run() {
                 repaintValues();
             }
         });
        guiLoop = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(((WorkSettings)main.workSettings).getGUIrefreshPeriod()*1000);
                    } catch (InterruptedException e) {}
                    if (shutDown)
                        return;
                long sec =  (new OwnDateTime().timeInMS()-userLoginTime.timeInMS())/1000;
                if (logoutCallBack!=null && sec > ((WorkSettings)main.workSettings).getUserSilenceTime()*60){
                    shutDown();
                    logoutCallBack.onPush();
                    return;
                    }
                repaintCycle();
                }
            });
        guiLoop.start();
        }

    private synchronized int  repaintViewLevel01(ESSNodeViewData nodeView) {    // TODO - тут все по-другому
         /*
        MetaExternalSystem meta = nodeView.getGuiList();
        MetaGUIForm form = meta.getForms().getByTitle(mainFormName);
        int viewH=0;
        for (Meta2GUIElement element : form.getControls().getControls()) {
            try {
                int type = element.getType();
                GUIElement view = factory.getByType(type);
                MetaRegister register = meta.getRegisters().get(element.getRegNum());
                if (view.needRegister() && register==null){
                    popup("Не найден регистр "+element.getRegNum()+" для элемента "+element.getTitle());
                    continue;
                    }
                String ss = view.setParams(context, meta, register, element, main2.plm, retryPaintValues);
                if (ss!=null){
                    popup("Ошибка назначения регистра "+element.getRegNum()+" для элемента "+element.getTitle()+"\n"+ss);
                    continue;
                    }
                view.setInGroup();
                int regStep=view.getRegStep();
                int groupCount = view.getGroupSize();
                if (groupCount==0) groupCount=1;
                int dXdY = element.getStep();
                for(int i=0;i<groupCount;i++){
                    GUIElement view2 = factory.getByType(type);
                    ss = view2.setParams(context,meta, register, element, main2.plm, retryPaintValues);
                    view2.setInGroup(view);
                    if (dXdY < 0 ) {
                        view2.setDxOffset(-dXdY*i);  // Смещение вьюшки
                        view2.setDyOffset(nodeView.getOffsetH());  // Смещение вьюшки
                        }
                    if (dXdY > 0 ) {
                        view2.setDyOffset(dXdY*i+nodeView.getOffsetH());
                        }
                    if (dXdY==0) view2.setDyOffset(nodeView.getOffsetH());
                    int hh = element.getY()+view2.getDyOffset();
                    if (hh>viewH)
                        viewH = hh;
                    view2.setRegOffset(i*regStep);             // Смещение регистра
                    view2.setGroupIndex(i);
                    view2.setBitNum(element.getBitNum());
                    view2.addToPanel(panel);
                    nodeView.getGuiList().add(view2);
                    }
                } catch (UniException ee){
                    popup(ee.toString());
                    }
                }
            return viewH;

          */
            return 0;
            }


    private double[] getSettingsValues(MetaGUIElement element) {     // TODO - тут все по-другому
        int yy= element.getY();
        int idx=-1;
        //for (int i=0;i<guiList.size();i++){
        //    GUIViewElement el = guiList.get(i);
        //    MetaGUIElement meta = el.getMetaElement();
        Meta2GUICollection elemList = context.getForm().getControls();
        for (int i=0;i<elemList.getList().size();i++){
            Meta2GUI meta = elemList.getList().get(i);
            if (meta.getY()!=yy)
                continue;
            if (meta.getType() != Values.GUISetting)
                continue;
            MetaRegister register = null;   // main2.meta.getRegisterByNum(meta.getRegNum());
            if (register.getType()==Values.RegSetting){
                idx=i;
                break;
                }
            }
        if (idx==-1){
            popup("Не найдены граничные уставки для "+element.getTitle() );
            return null;
            }
        double out[]=new double[4];
        for(int j=0;j<4;j++){
            //GUIViewElement el = guiList.get(j+idx);
            //MetaGUIElement meta = el.getMetaElement();
            Meta2GUI meta = elemList.getList().get(idx+j);
            if (meta.getType()!= Values.GUISetting){
                popup("Недопустимый тип элемента для "+meta.getTitle() );
                return null;
                }
            MetaRegister register = null;   // main2.meta.getRegisterByNum(meta.getRegNum());
            if (register.getType()!=Values.RegSetting){
                popup("Недопустимые тип регистра для "+meta.getTitle() );
                return null;
                }
            try {
                //double value = el.floatWithPower(el.readRegister(0));
                double value = ((MetaSettingRegister)register).doubleWithPower(main2.plm.readRegister("",0,register.getRegNum()));
                out[j]=value;
                } catch (UniException ee){
                    popup("Ошибка чтения регистра "+register.getRegNum());
                    return null;
                    }
            }
        return out;
        }
    //---------------------------------------------------------------------------------------
    @Override
    public void paintComponent(Graphics g){
        ScreenMode screen = context.getScreen();
        Meta2GUIForm form = context.getForm();
        g.setColor(new Color(context.getView().getBackColor()-0x101010));
        g.fillRect( context.x(0), context.y(0), screen.getRealX(), screen.getRealY());
        g.setColor(new Color(context.getView().getBackColor()));
        g.fillRect( context.x(0), context.y(0), screen.ScreenW(), screen.ScreenH());
        if (form==null || form.getPicture().getOid()==0)
            return;
        if (form.isBackgroundError())
            return;
        Image background = form.getBackground();
        if (background==null){
            try {
                Call<ResponseBody> call2 = context.getService().downLoad(context.getToken(),form.getPicture().getOid());
                Response<ResponseBody> response = call2.execute();
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    BufferedImage originalImage = ImageIO.read(body.byteStream());
                    background  = originalImage.getScaledInstance(context.x(form.getImageW()), context.y(form.getImageH()), Image.SCALE_DEFAULT);
                    form.setBackground(background);
                    }
                else{
                    form.setBackgroundError(true);
                    return;
                    }
                } catch (Exception ee){
                    form.setBackgroundError(true);
                    String ss = "Ошибка загрузки: "+form.getPicture().getTitle();
                    popup(ss);
                    System.out.println(ss+"\n"+ee.toString());
                    }
                }
        g.drawImage(background,
                context.x(form.getImageX0()),
                context.y(form.getImageY0()),
                context.dx(form.getImageW()),
                context.dy(form.getImageH()),null);
        //Image im = null;
        //try {
        //    ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/"+form.getBackImage()));
        //    im = icon.getImage();
        //    } catch (Exception e) {
        //        System.out.println(e.toString());
        //        }
        //g.drawImage(im, 0, 0, screen.ScreenW(), screen.ScreenH(),null);
        }
    //---------------------------------------------------------------------------------------
    public synchronized void  repaintView() {
        breakCycle();
        removeAll();
        repaint();
        panel = new JPanel();
        panel.setLayout(null);
        NodeListPanel = new JScrollPane(panel);
        NodeListPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        NodeListPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        NodeListPanel.setBounds(5, 5, context.getScreen().ScreenW() - 40, context.getScreen().ScreenH() - 60);
        add(NodeListPanel);
        //-----------------------------------------------------------------------------------
        JButton toMain = new JButton();
        toMain.setIcon(new javax.swing.ImageIcon(getClass().getResource(buttonToMain))); // NOI18N
        //toMain.setBorderPainted(false);
        //toMain.setContentAreaFilled(false);
        toMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshNodeList();
            }
        });
        panel.add(toMain);
        toMain.setBounds(context.x(820), context.y(10), context.dx(40), context.dy(40));
        //-----------------------------------------------------------------------------------
        JButton info = new JButton();
        info.setIcon(new javax.swing.ImageIcon(getClass().getResource(context.isInfoMode() ? buttonInfoOn : buttonInfoOff))); // NOI18N
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.setInfoMode(!context.isInfoMode());
                info.setIcon(new javax.swing.ImageIcon(getClass().getResource(context.isInfoMode() ? buttonInfoOn : buttonInfoOff))); // NOI18N
                }
            });
        panel.add(info);
        info.setBounds(context.x(770), context.y(10), context.dx(40), context.dy(40));
        //-------------------------------------------------------------------------------------
        int offsetH = 20;
        for (ESSNodeViewData nodeView : nodeList) {
            ESSNode node = nodeView.getNode();
            nodeView.setOffsetH(offsetH);
            String ss = node.getTitle();
            JLabel label = new JLabel();
            label.setFont(new Font("Arial Cyr", Font.BOLD, context.y(16)));
            label.setBounds(context.x(50), context.y(offsetH), context.dx(500), context.dy(20));
            label.setBackground(new Color(0xFFD8D8D8));
            panel.add(label);
            offsetH+=25;
            if (!node.isWorking()) {
                label.setText(ss + " недоступен");
                }
            if (!nodeView.isReady()) {
                label.setText(ss + " нет связи с ПЛК");
                }
            else{
                label.setText(ss);
                repaintViewLevel01(nodeView);
                offsetH += repaintViewLevel01(nodeView);
                }
           }
        //-----------------------------------------------------------------------------------
        TextField userTitle = new TextField();
        int  access = context.getManager().getCurrentAccessLevel();
        String ss = "  "+context.getManager().getUser().getTitle()+" ["+Values.title("AccessLevel",access)+"] ";
        userTitle.setText(ss);
        userTitle.setBounds(context.x(10),context.y(ValuesBase.ScreenDesktopHeight-60),
                context.dx(700),context.dy(25));
        userTitle.setEnabled(false);
        userTitle.setFont(new Font("Arial Cyr", Font.PLAIN, context.dy(12)));
        panel.add(userTitle);
        //-----------------------------------------------------------------------------------
        panel.setBounds(0, 0, context.getScreen().ScreenW() - 40, context.y(offsetH));
        repaintCycle();
        revalidate();
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public void refresh() {
        repaintView();
        }

    @Override
    public void eventPanel(int code, int par1, long par2, String par3,Object oo) {
        if (code==EventRefreshSettings){
            refresh();
            main.sendEventPanel(EventRefreshSettingsDone,0,0,"");
            }
        if (code==EventPLMOn){
            repaintView();
            }
        if (code==EventPLMOff){
            breakCycle();
        }
    }

    public void repaintValuesOnAnswer(ESSNodeViewData nodeView, IntegerList values) throws UniException {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<values.size();i+=2)
            map.put(values.get(i),values.get(i+1));
        for (GUIElement element : nodeView.getGuiList()){
            if (!element.needRegister())
                continue;
            int regNum[] = element.getRegNum();
            if (regNum.length==1){
                Integer vv = map.get(regNum[0]);
                if (vv==null){
                    popup("Не найден регистр в ответе сервера "+regNum[0]);
                    continue;
                }
                if (!element.getRegister().size32Bit())
                    element.putValue(vv.intValue());
                else{
                    Integer vv1 = map.get(regNum[0]+1);
                    if (vv1==null){
                        popup("Не найден двойной регистр в ответе сервера "+regNum[0]);
                        continue;
                        }
                    element.putValue(vv.intValue() & 0x0FFFF | vv1.intValue()<<16);
                    }
                }
            else{
                int out[] = new int[regNum.length];
                for(int i=0;i<regNum.length;i++){
                    if (regNum[i]==-1)
                        continue;
                    Integer vv = map.get(regNum[i]);
                    out[i]=0;
                    if (vv==null)
                        popup("Не найден регистр в ответе сервера " + regNum[0]);
                    out[i] = vv.intValue();
                    }
                element.putValue(out);
                }
            }
        if (module!=null)
            module.repaintValues();
        }

    public synchronized void repaintValues(){
        for (ESSNodeViewData nodeView : nodeList)
            repaintValues(nodeView);
        }
    public synchronized void repaintValues(ESSNodeViewData nodeView){
        if(nodeView.getMeta()==null || !nodeView.isReady())
            return;
        HashMap<Integer,Integer> map = new HashMap<>();
        for (GUIElement element : nodeView.getGuiList()){
            if (!element.needRegister())
                continue;
            int regNum[] = element.getRegNum();
            for(int i=0;i<regNum.length;i++){
                if (regNum[i]==-1)
                    popup("Нет регистра "+element.toString());
                else{
                    map.put(regNum[i],0);
                    if (element.getRegister().size32Bit())
                        map.put(regNum[i]+1,0);
                    }
                }
            }
        Object bb[]= map.keySet().toArray();
        IntegerList list = new IntegerList();
        for(Object cc : bb)
            list.add(((Integer)cc).intValue());
        readPLMRegistersAsync(nodeView,list);        // Асинхронная версия
        /*  ----------- синхронная версия
        try {
            IntegerList values = new APICall2<IntegerList>() {
                @Override
                public Call apiFun() {
                    return nodeView.getService().readRegistersValues(nodeView.getToken(), list);
                    }
                }.call(main);
            repaintValuesOnAnswer(nodeView,list);
            } catch (UniException ee){
                popup("Ошибка сервера: "+ee.toString());
                }
         */
        }
    //------------------------------------------------------------------------
    private synchronized void readPLMRegistersAsync(ESSNodeViewData nodeView,IntegerList list){
        if (nodeView.isAsyncBusy()) return;
        nodeView.setAsyncBusy(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final IntegerList values = new APICall2<IntegerList>() {
                        @Override
                        public Call apiFun() {
                            return nodeView.getService2().readRegistersValues(nodeView.getToken(), list);
                        }
                    }.call(main);
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            nodeView.setAsyncBusy(false);
                            try {
                                repaintValuesOnAnswer(nodeView,values);
                            } catch (UniException e) {
                                popup("Ошибка GUI: "+e.toString());
                            }
                        }
                    });

                } catch (final UniException ee){
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            nodeView.setAsyncBusy(false);
                            popup("Ошибка сервера: "+ee.toString());
                        }
                    });
                }
            }
        }).start();
    }

    //-----------------------------------------------------------------------
    @Override
    public void shutDown() {
        breakCycle();
        }
    //------------------------------------------------------------------------
    private void refreshNodeList(){
        nodeList.clear();
        try {
            ArrayList<DBRequest> list = new APICallC<ArrayList<DBRequest>>() {
                @Override
                public Call<ArrayList<DBRequest>> apiFun() {
                    return main.service.getEntityList(main.debugToken, "ESSNode", Values.GetAllModeActual, 1);
                    }
                }.call();
            for(DBRequest zz : list)
                nodeList.add(new ESSNodeViewData((ESSNode) zz.get(main.gson)));
            } catch (UniException ee){
                popup("Ошибка чтения списка серверов СМУ СНЭ: "+ee.toString());
                return;
                }
            for (ESSNodeViewData nodeView : nodeList){
                ESSNode node = nodeView.getNode();
                if (!node.isConnect())
                    continue;
                try {
                    node.setWorking(false);
                    final Pair<RestAPIBase, String> conn = main.startOneClient(node.getServerIP(), "" + node.getServerPort());
                    final RestAPIESS2 conn2 = main2.startSecondClient(node.getServerIP(), "" + node.getServerPort());
                    MetaExternalSystem mData = new APICallC<MetaExternalSystem>(){
                        @Override
                        public Call<MetaExternalSystem> apiFun() {
                            return conn2.getCurrentMetaData(conn.o2);
                            }
                        }.call();
                    mData.createMap();
                    nodeView.setMeta(mData);
                    node.setWorking(true);
                    nodeView.setService(conn.o1);
                    nodeView.setService2(conn2);
                    nodeView.setToken(conn.o2);
                    node.setStateTestTime(new OwnDateTime());
                    popup("Считаны метаданные "+ node.getTitle());
                    JBoolean plcReady = new APICallC<JBoolean>(){
                        @Override
                        public Call<JBoolean> apiFun() {
                            return conn2.isPLMReady(conn.o2);
                            }
                        }.call();
                    nodeView.setReady(plcReady.value());
                } catch (UniException ee){
                       popup("Ошибка соединения СМУ СНЭ: "+node.getTitle()+"\n"+ee.toString());
                       }
                }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
