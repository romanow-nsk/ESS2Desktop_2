/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import lombok.Getter;
import org.openmuc.openiec61850.clientgui.ClientGui;
import romanow.abc.core.*;
import romanow.abc.core.API.RestAPICommon;
import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.Entity;
import romanow.abc.core.entity.EntityList;
import romanow.abc.core.entity.EntityRefList;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.baseentityes.*;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.metadata.render.ScreenMode;
import romanow.abc.core.entity.subject2area.*;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.core.script.*;
import romanow.abc.core.utils.FileNameExt;
import okhttp3.MultipartBody;
import retrofit2.Call;
import romanow.abc.core.utils.Pair;
import romanow.abc.desktop.wizard.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static romanow.abc.core.constants.Values.*;

/**
 *
 * @author romanow
 */
public class ESSMetaPanel extends ESSBasePanel {
    private ESS2SettingsCalculator calculator = new ESS2SettingsCalculator();
    private ESSStreamDataSelector streamData = new ESSStreamDataSelector();
    private ESSSettingsConfigSelector configSelector = new ESSSettingsConfigSelector();
    private EntityList<ArchStreamPeriod> periods = new EntityList<>();
    private TrendView trendView = new TrendView();
    private ESSServiceGUIScreen screen = null;
    private boolean mainServerMode = false;
    private ArrayList<Meta2SettingRegister> settingRegisters = new ArrayList<>();       // Для панели уставок
    private ArrayList<ConstValue> metaTypes;
    private HashMap<Integer,ConstValue> metaTypesMap;
    //----------------------------------------------------------------------------------------------
    @Getter private ArrayList<ESS2MetaFile> metaData = new ArrayList<>();
    private int changesCount = 0;                                       // Счетчик изменений
    private int runTimeChangesCount = 0;                                // Счетчик изменений на лету
    private ESS2MetaFile metaFile = null;                               // Описатель выбранного мета-файла
    private Meta2XML metaXML = null;                                    // Мета-объект
    @Getter private EntityRefList<ESS2Architecture> architectures = new EntityRefList<>();
    @Getter private ESS2Architecture architecture = null;               // Редактируемая архитектура
    private ESS2Architecture deployed = new ESS2Architecture();         // Развернутая архитектура
    private ArrayList<ESS2Node> nodes = new ArrayList<>();
    private GUITimer profilerTimer = new GUITimer();                    // Таймер профилирования
    private final static int profilerTimerDelay=3;                      // Таймер повтора профилирования
    private boolean oldProfilerState=false;
    //---------------------------------------------------------------------------------------------------
    private int  lastViewIndex=-1;
    private String archStateIcons[]={
            "/drawable/settings-gray.png",
            "/drawable/settings-red.png",
            "/drawable/settings-yellow.png",
            "/drawable/settings-green.png",
            "/drawable/settings-green.png",
            "/drawable/settings-green.png"
            };
    private String connStateIcons[]={
            "/drawable/status_gray.png",
            "/drawable/status_gray.png",
            "/drawable/status_gray.png",
            "/drawable/status_gray.png",
            "/drawable/status_red.png",
            "/drawable/status_green.png",
            };
    private String serviceStateIcons[]={
            "/drawable/status_gray.png",
            "/drawable/status_red.png",
            "/drawable/status_green.png",
            "/drawable/status_yellow.png",
        };
    public ESSMetaPanel() {
        initComponents();
    }

    public void initPanel(MainBaseFrame main0) {
        super.initPanel(main0);
        String pass = main.loginUser().getAccount().getPassword();
        Password.setText(pass);
        streamData.setBounds(370, 270, 470, 185);
        add(streamData);
        configSelector.setBounds(370, 450, 400, 125);
        add(configSelector);
        configSelector.setVisible(true);
        MetaDataSaveChanges.setVisible(false);
        MetaDataChanges.setVisible(false);
        MetaDataChangesLabel.setVisible(false);
        RunTimeSaveChanges.setVisible(false);
        RunTimeChanges.setVisible(false);
        RunTimeChangesLabel.setVisible(false);
        ExecScriptServer.setEnabled(false);
        ExecScriptClient.setEnabled(true);
        AddEnvValue.setEnabled(true);
        RemoveEnvValue.setEnabled(true);
        EditEnvValue.setEnabled(true);
        CIDLocal.setEnabled(false);
        IEC61850OnOff.setEnabled(false);
        IEC61850ClientGUI.setEnabled(false);
        ProfilerResults.setVisible(false);
        metaTypesMap = Values.constMap().getGroupMapByValue("MetaType");
        metaTypes = Values.constMap().getGroupList("MetaType");
        MetaTypes.removeAll();
        for(ConstValue cc : metaTypes)
            MetaTypes.add(cc.title());
        refreshArchitectures();
        setRenderingOff();
        refreshArchtectureState();
        if (main.loginUser().getTypeId()== UserESSOperator) {
                if (main.loginUser().getTypeId()==Values.UserESSOperator){
                    if (main2.deployed.getArchitectureState()!=Values.ASConnected){
                        popup("Конфигурация не развернута или не активирована");
                    }
                    else{
                        EntityRefList<ESS2View> list = main2.deployed.getViews();
                        ESS2View view = null;
                        for(ESS2View view1 :list){
                            int type = view1.getView().getXmlType();
                            if (type==Values.MTViewDesktop || type== MTViewFullScreen) {
                                view = view1;
                                break;
                            }
                        }
                        if (view==null){
                            popup("Не найден ЧМИ для ПК");
                        }
                        else{
                            ScreenMode screenMode = new ScreenMode(false,view.getView().getWidth(),view.getView().getHeight(),0,0);
                            main2.setRenderingOn(0,view,false,false,false,screenMode);
                            }
                    }
                }
            getRootPane().setVisible(false);
            }
        String ss=main2.getWorkSettings();
        if (ss!=null){
            popup("Ошибка чтения настроек: "+ss);
            System.out.println("Ошибка чтения настроек: "+ss);
            }
        else{
            mainServerMode = ((WorkSettings)main2.workSettings()).isMainServerMode();
            }
        Deploy.setVisible(!mainServerMode);
        Connect.setVisible(!mainServerMode);
        OnOff.setVisible(!mainServerMode);
        OnOffNode.setVisible(mainServerMode);
        refreshIEC61850State();
        refreshIEC60870State();
        refreshProfilerState();
        }
    private void setMetaTypeSelector(int type){
        int i=0;
        for(ConstValue cc : metaTypes){
            if (cc.value()==type){
                MetaTypes.select(i);
                return;
                }
            i++;
            }
        MetaTypes.select(0);
        }

    public boolean isMainMode() {
        return false;
    }
    public MainBaseFrame getFrame(){ return main; }
    public boolean isESSMode() {
        return true;
    }

    private void initStreamData() {
        if (main2.deployed==null || main2.deployed.getArchitectureState()!=Values.ASConnected)
            return;
        streamData.setVisible(true);
        streamData.init(main2, main2.deployed, new I_Value<StreamRegisterData>() {
            @Override
            public void onEnter(StreamRegisterData value) {
                trendView.getPanel().setBack(null);
                trendView.addTrendView(value);
                trendView.toFront();
            }
        });
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ImportMetaData = new javax.swing.JButton();
        Password = new javax.swing.JPasswordField();
        OnOff = new javax.swing.JButton();
        DeviceRead = new javax.swing.JButton();
        DeviceWrite = new javax.swing.JButton();
        RegNum = new javax.swing.JTextField();
        RegValue = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        HEXReg = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        SettingsList = new java.awt.Choice();
        jLabel10 = new javax.swing.JLabel();
        SettingValue = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        DefValueFormula = new javax.swing.JTextField();
        MinValueFormula = new javax.swing.JTextField();
        MaxValueFormula = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        DefValue = new javax.swing.JTextField();
        MinValue = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        MaxValue = new javax.swing.JTextField();
        WriteSetting = new javax.swing.JButton();
        SettingRegNum = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        SettingShortName = new javax.swing.JTextField();
        SettingUnit = new javax.swing.JTextField();
        CheckLimits = new javax.swing.JCheckBox();
        SettingEdit = new javax.swing.JButton();
        SettingsName = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        Nodes = new java.awt.Choice();
        jLabel18 = new javax.swing.JLabel();
        RefreshMeta = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel20 = new javax.swing.JLabel();
        WriteFloat = new javax.swing.JCheckBox();
        WriteInt32 = new javax.swing.JCheckBox();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        MetaFile = new java.awt.Choice();
        RemoveMeta = new javax.swing.JButton();
        AddMeta = new javax.swing.JButton();
        MetaDataChangesLabel = new javax.swing.JLabel();
        EditMeta = new javax.swing.JButton();
        MetaDataSaveChanges = new javax.swing.JButton();
        MetaDataChanges = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        Views = new java.awt.Choice();
        jLabel25 = new javax.swing.JLabel();
        RemoveDevice = new javax.swing.JButton();
        AddDevice = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        Architectures = new java.awt.Choice();
        jLabel28 = new javax.swing.JLabel();
        Devices = new java.awt.Choice();
        Equipments = new java.awt.Choice();
        jLabel29 = new javax.swing.JLabel();
        RemoveEquip = new javax.swing.JButton();
        AddEquip = new javax.swing.JButton();
        RemoveArch = new javax.swing.JButton();
        AddArch = new javax.swing.JButton();
        RemoveView = new javax.swing.JButton();
        AddView = new javax.swing.JButton();
        EditView = new javax.swing.JButton();
        EditArch = new javax.swing.JButton();
        EditEquip = new javax.swing.JButton();
        EditDevice = new javax.swing.JButton();
        ArchNodeRefrresh = new javax.swing.JButton();
        RemoveNode = new javax.swing.JButton();
        AddNode = new javax.swing.JButton();
        EditNode = new javax.swing.JButton();
        ExportArchitectureFiles = new javax.swing.JButton();
        Deploy = new javax.swing.JButton();
        ArchitectureLabel = new javax.swing.JLabel();
        Connect = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        Emulators = new java.awt.Choice();
        RemoveEmulator = new javax.swing.JButton();
        AddEmulator = new javax.swing.JButton();
        EditEmulator = new javax.swing.JButton();
        ImportScript = new javax.swing.JButton();
        Scripts = new java.awt.Choice();
        RemoveScript = new javax.swing.JButton();
        EditScript = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        ExecScriptServer = new javax.swing.JButton();
        Trace = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        ImportMetaEquipment2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        ExecScriptClient = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        RuntimeEdit = new javax.swing.JCheckBox();
        RunTimeSaveChanges = new javax.swing.JButton();
        RunTimeChanges = new javax.swing.JTextField();
        RunTimeChangesLabel = new javax.swing.JLabel();
        MetaTypes = new java.awt.Choice();
        jLabel31 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        ImportXML = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        ExportXML = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        ExportXMLEquipment = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        ExportXMLView = new javax.swing.JButton();
        jLabel39 = new javax.swing.JLabel();
        DevicesRW = new java.awt.Choice();
        jSeparator8 = new javax.swing.JSeparator();
        UnitRW = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        HEXValue = new javax.swing.JCheckBox();
        jLabel40 = new javax.swing.JLabel();
        LogUnits = new java.awt.Choice();
        RemoveLogUnit = new javax.swing.JButton();
        AddLogUnit = new javax.swing.JButton();
        EditLogUnit = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JSeparator();
        jLabel41 = new javax.swing.JLabel();
        EnvValue = new java.awt.Choice();
        RemoveEnvValue = new javax.swing.JButton();
        AddEnvValue = new javax.swing.JButton();
        EditEnvValue = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        ExportXMLAll = new javax.swing.JButton();
        ExportScripts = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        FullScreen = new javax.swing.JCheckBox();
        OnOffNode = new javax.swing.JButton();
        CIDLocal = new javax.swing.JButton();
        IEC61850OnOff = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        IEC61850ClientGUI = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        ProfilerOnOff = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel21 = new javax.swing.JLabel();
        Profilers = new java.awt.Choice();
        RemoveProfiler = new javax.swing.JButton();
        AddProfiler = new javax.swing.JButton();
        EditProfiler = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        jLabel42 = new javax.swing.JLabel();
        ProfilerResults = new javax.swing.JButton();
        OnlyView = new javax.swing.JCheckBox();
        IEC60870OnOff = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        OnOff2 = new javax.swing.JButton();
        OrigHW = new javax.swing.JCheckBox();
        ForceConnect = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        ForceRender = new javax.swing.JCheckBox();
        ForceDeploy = new javax.swing.JCheckBox();
        ProfileName = new javax.swing.JLabel();
        Gates = new java.awt.Choice();
        jLabel43 = new javax.swing.JLabel();
        AddGate = new javax.swing.JButton();
        RemoveGate = new javax.swing.JButton();
        EditGate = new javax.swing.JButton();
        ExportNode = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();

        setLayout(null);

        ImportMetaData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        ImportMetaData.setBorderPainted(false);
        ImportMetaData.setContentAreaFilled(false);
        ImportMetaData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportMetaDataActionPerformed(evt);
            }
        });
        add(ImportMetaData);
        ImportMetaData.setBounds(540, 40, 30, 30);

        Password.setText("pi31415926");
        add(Password);
        Password.setBounds(10, 560, 110, 25);

        OnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png"))); // NOI18N
        OnOff.setBorderPainted(false);
        OnOff.setContentAreaFilled(false);
        OnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnOffActionPerformed(evt);
            }
        });
        add(OnOff);
        OnOff.setBounds(90, 460, 30, 40);

        DeviceRead.setText("Чтение");
        DeviceRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeviceReadActionPerformed(evt);
            }
        });
        add(DeviceRead);
        DeviceRead.setBounds(600, 610, 70, 22);

        DeviceWrite.setText("Запись");
        DeviceWrite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeviceWriteActionPerformed(evt);
            }
        });
        add(DeviceWrite);
        DeviceWrite.setBounds(600, 640, 70, 22);

        RegNum.setText("0");
        add(RegNum);
        RegNum.setBounds(460, 610, 80, 25);

        RegValue.setText("0");
        add(RegValue);
        RegValue.setBounds(460, 640, 80, 25);

        jLabel4.setText("Значение");
        add(jLabel4);
        jLabel4.setBounds(380, 650, 80, 16);

        HEXReg.setText("hex");
        HEXReg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HEXRegItemStateChanged(evt);
            }
        });
        add(HEXReg);
        HEXReg.setBounds(550, 610, 50, 21);

        jLabel8.setText("Регистр");
        add(jLabel8);
        jLabel8.setBounds(380, 620, 80, 16);

        SettingsList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SettingsListItemStateChanged(evt);
            }
        });
        add(SettingsList);
        SettingsList.setBounds(380, 100, 470, 20);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Уставки");
        add(jLabel10);
        jLabel10.setBounds(860, 100, 60, 16);

        SettingValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SettingValueKeyPressed(evt);
            }
        });
        add(SettingValue);
        SettingValue.setBounds(850, 220, 60, 25);

        jLabel11.setText("Номер");
        add(jLabel11);
        jLabel11.setBounds(710, 160, 50, 16);

        jLabel12.setText("Значение");
        add(jLabel12);
        jLabel12.setBounds(630, 250, 90, 16);

        DefValueFormula.setEnabled(false);
        add(DefValueFormula);
        DefValueFormula.setBounds(460, 160, 160, 25);

        MinValueFormula.setEnabled(false);
        add(MinValueFormula);
        MinValueFormula.setBounds(460, 190, 160, 25);

        MaxValueFormula.setEnabled(false);
        add(MaxValueFormula);
        MaxValueFormula.setBounds(460, 220, 160, 25);

        jLabel13.setText("Умолчание");
        add(jLabel13);
        jLabel13.setBounds(380, 160, 90, 16);

        jLabel14.setText("Формула");
        add(jLabel14);
        jLabel14.setBounds(460, 250, 90, 16);

        DefValue.setEnabled(false);
        add(DefValue);
        DefValue.setBounds(630, 160, 60, 25);

        MinValue.setEnabled(false);
        add(MinValue);
        MinValue.setBounds(630, 190, 60, 25);

        jLabel15.setText("Минимум");
        add(jLabel15);
        jLabel15.setBounds(380, 190, 90, 16);

        MaxValue.setEnabled(false);
        add(MaxValue);
        MaxValue.setBounds(630, 220, 60, 25);

        WriteSetting.setText("Запись");
        WriteSetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WriteSettingActionPerformed(evt);
            }
        });
        add(WriteSetting);
        WriteSetting.setBounds(760, 220, 80, 22);

        SettingRegNum.setEnabled(false);
        add(SettingRegNum);
        SettingRegNum.setBounds(760, 160, 40, 25);

        jLabel16.setText("Максимум");
        add(jLabel16);
        jLabel16.setBounds(380, 220, 90, 16);

        jLabel17.setText("Имя");
        add(jLabel17);
        jLabel17.setBounds(710, 200, 30, 16);

        SettingShortName.setEnabled(false);
        add(SettingShortName);
        SettingShortName.setBounds(760, 190, 80, 25);

        SettingUnit.setEnabled(false);
        add(SettingUnit);
        SettingUnit.setBounds(850, 190, 60, 25);

        CheckLimits.setText("Проверка");
        add(CheckLimits);
        CheckLimits.setBounds(790, 125, 90, 20);

        SettingEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingEditActionPerformed(evt);
            }
        });
        add(SettingEdit);
        SettingEdit.setBounds(880, 130, 30, 30);

        SettingsName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SettingsName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(SettingsName);
        SettingsName.setBounds(380, 130, 400, 25);
        add(jSeparator1);
        jSeparator1.setBounds(10, 302, 360, 0);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        add(jSeparator2);
        jSeparator2.setBounds(370, 90, 10, 580);

        Nodes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NodesItemStateChanged(evt);
            }
        });
        add(Nodes);
        Nodes.setBounds(10, 20, 210, 25);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Modbus-регистры");
        add(jLabel18);
        jLabel18.setBounds(780, 570, 130, 14);

        RefreshMeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/refresh.png"))); // NOI18N
        RefreshMeta.setBorderPainted(false);
        RefreshMeta.setContentAreaFilled(false);
        RefreshMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshMetaActionPerformed(evt);
            }
        });
        add(RefreshMeta);
        RefreshMeta.setBounds(380, 40, 30, 30);
        add(jSeparator3);
        jSeparator3.setBounds(0, 437, 370, 3);
        add(jSeparator4);
        jSeparator4.setBounds(380, 90, 500, 10);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Тренды");
        add(jLabel20);
        jLabel20.setBounds(840, 260, 70, 14);

        WriteFloat.setText("Float");
        add(WriteFloat);
        WriteFloat.setBounds(680, 610, 70, 20);

        WriteInt32.setText("Int32");
        add(WriteInt32);
        WriteInt32.setBounds(680, 640, 60, 20);
        add(jSeparator6);
        jSeparator6.setBounds(380, 450, 390, 10);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText("ЧМИ");
        add(jLabel24);
        jLabel24.setBounds(10, 245, 90, 14);

        MetaFile.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MetaFileItemStateChanged(evt);
            }
        });
        add(MetaFile);
        MetaFile.setBounds(380, 10, 410, 20);

        RemoveMeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveMeta.setBorderPainted(false);
        RemoveMeta.setContentAreaFilled(false);
        RemoveMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveMetaActionPerformed(evt);
            }
        });
        add(RemoveMeta);
        RemoveMeta.setBounds(800, 40, 30, 30);

        AddMeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddMeta.setBorderPainted(false);
        AddMeta.setContentAreaFilled(false);
        AddMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddMetaActionPerformed(evt);
            }
        });
        add(AddMeta);
        AddMeta.setBounds(610, 40, 40, 30);

        MetaDataChangesLabel.setText("Изменений");
        add(MetaDataChangesLabel);
        MetaDataChangesLabel.setBounds(810, 10, 70, 16);

        EditMeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditMeta.setBorderPainted(false);
        EditMeta.setContentAreaFilled(false);
        EditMeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditMetaActionPerformed(evt);
            }
        });
        add(EditMeta);
        EditMeta.setBounds(840, 40, 30, 30);

        MetaDataSaveChanges.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        MetaDataSaveChanges.setBorderPainted(false);
        MetaDataSaveChanges.setContentAreaFilled(false);
        MetaDataSaveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MetaDataSaveChangesActionPerformed(evt);
            }
        });
        add(MetaDataSaveChanges);
        MetaDataSaveChanges.setBounds(880, 40, 30, 30);

        MetaDataChanges.setEnabled(false);
        add(MetaDataChanges);
        MetaDataChanges.setBounds(880, 10, 30, 25);

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setText("Мета-данные ");
        add(jLabel26);
        jLabel26.setBounds(650, 30, 100, 14);
        add(Views);
        Views.setBounds(10, 260, 210, 20);

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setText("Узел СНЭЭ");
        add(jLabel25);
        jLabel25.setBounds(10, 5, 90, 14);

        RemoveDevice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveDevice.setBorderPainted(false);
        RemoveDevice.setContentAreaFilled(false);
        RemoveDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveDeviceActionPerformed(evt);
            }
        });
        add(RemoveDevice);
        RemoveDevice.setBounds(260, 180, 30, 30);

        AddDevice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddDevice.setBorderPainted(false);
        AddDevice.setContentAreaFilled(false);
        AddDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddDeviceActionPerformed(evt);
            }
        });
        add(AddDevice);
        AddDevice.setBounds(220, 180, 40, 30);

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setText("Архитектура");
        add(jLabel27);
        jLabel27.setBounds(10, 45, 90, 14);

        Architectures.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ArchitecturesItemStateChanged(evt);
            }
        });
        add(Architectures);
        Architectures.setBounds(10, 60, 210, 20);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setText("Лог.устройство (Unit)");
        add(jLabel28);
        jLabel28.setBounds(10, 125, 210, 14);
        add(Devices);
        Devices.setBounds(10, 180, 210, 20);

        Equipments.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EquipmentsItemStateChanged(evt);
            }
        });
        add(Equipments);
        Equipments.setBounds(10, 100, 210, 20);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setText("Оборудование");
        add(jLabel29);
        jLabel29.setBounds(10, 85, 90, 14);

        RemoveEquip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveEquip.setBorderPainted(false);
        RemoveEquip.setContentAreaFilled(false);
        RemoveEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveEquipActionPerformed(evt);
            }
        });
        add(RemoveEquip);
        RemoveEquip.setBounds(260, 100, 30, 30);

        AddEquip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddEquip.setBorderPainted(false);
        AddEquip.setContentAreaFilled(false);
        AddEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEquipActionPerformed(evt);
            }
        });
        add(AddEquip);
        AddEquip.setBounds(220, 100, 40, 30);

        RemoveArch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveArch.setBorderPainted(false);
        RemoveArch.setContentAreaFilled(false);
        RemoveArch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveArchActionPerformed(evt);
            }
        });
        add(RemoveArch);
        RemoveArch.setBounds(260, 60, 30, 30);

        AddArch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddArch.setBorderPainted(false);
        AddArch.setContentAreaFilled(false);
        AddArch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddArchActionPerformed(evt);
            }
        });
        add(AddArch);
        AddArch.setBounds(220, 60, 40, 30);

        RemoveView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveView.setBorderPainted(false);
        RemoveView.setContentAreaFilled(false);
        RemoveView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveViewActionPerformed(evt);
            }
        });
        add(RemoveView);
        RemoveView.setBounds(260, 260, 30, 30);

        AddView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddView.setBorderPainted(false);
        AddView.setContentAreaFilled(false);
        AddView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddViewActionPerformed(evt);
            }
        });
        add(AddView);
        AddView.setBounds(220, 260, 40, 30);

        EditView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditView.setBorderPainted(false);
        EditView.setContentAreaFilled(false);
        EditView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditViewActionPerformed(evt);
            }
        });
        add(EditView);
        EditView.setBounds(290, 260, 30, 30);

        EditArch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditArch.setBorderPainted(false);
        EditArch.setContentAreaFilled(false);
        EditArch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditArchActionPerformed(evt);
            }
        });
        add(EditArch);
        EditArch.setBounds(290, 60, 30, 30);

        EditEquip.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditEquip.setBorderPainted(false);
        EditEquip.setContentAreaFilled(false);
        EditEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditEquipActionPerformed(evt);
            }
        });
        add(EditEquip);
        EditEquip.setBounds(290, 100, 30, 30);

        EditDevice.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditDevice.setBorderPainted(false);
        EditDevice.setContentAreaFilled(false);
        EditDevice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditDeviceActionPerformed(evt);
            }
        });
        add(EditDevice);
        EditDevice.setBounds(290, 180, 30, 30);

        ArchNodeRefrresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/refresh.png"))); // NOI18N
        ArchNodeRefrresh.setBorderPainted(false);
        ArchNodeRefrresh.setContentAreaFilled(false);
        ArchNodeRefrresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArchNodeRefrreshActionPerformed(evt);
            }
        });
        add(ArchNodeRefrresh);
        ArchNodeRefrresh.setBounds(330, 160, 30, 30);

        RemoveNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveNode.setBorderPainted(false);
        RemoveNode.setContentAreaFilled(false);
        RemoveNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveNodeActionPerformed(evt);
            }
        });
        add(RemoveNode);
        RemoveNode.setBounds(260, 20, 30, 30);

        AddNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddNode.setBorderPainted(false);
        AddNode.setContentAreaFilled(false);
        AddNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNodeActionPerformed(evt);
            }
        });
        add(AddNode);
        AddNode.setBounds(220, 20, 40, 30);

        EditNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditNode.setBorderPainted(false);
        EditNode.setContentAreaFilled(false);
        EditNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditNodeActionPerformed(evt);
            }
        });
        add(EditNode);
        EditNode.setBounds(290, 20, 30, 30);

        ExportArchitectureFiles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        ExportArchitectureFiles.setBorderPainted(false);
        ExportArchitectureFiles.setContentAreaFilled(false);
        ExportArchitectureFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportArchitectureFilesActionPerformed(evt);
            }
        });
        add(ExportArchitectureFiles);
        ExportArchitectureFiles.setBounds(330, 60, 30, 30);

        Deploy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/settings.png"))); // NOI18N
        Deploy.setBorderPainted(false);
        Deploy.setContentAreaFilled(false);
        Deploy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeployActionPerformed(evt);
            }
        });
        add(Deploy);
        Deploy.setBounds(10, 460, 40, 40);

        ArchitectureLabel.setText("Выбрать архитектуру");
        add(ArchitectureLabel);
        ArchitectureLabel.setBounds(170, 480, 130, 16);

        Connect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/status_gray.png"))); // NOI18N
        Connect.setBorderPainted(false);
        Connect.setContentAreaFilled(false);
        Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectActionPerformed(evt);
            }
        });
        add(Connect);
        Connect.setBounds(50, 460, 40, 40);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setText("Эмуляторы");
        add(jLabel33);
        jLabel33.setBounds(10, 285, 90, 14);
        add(Emulators);
        Emulators.setBounds(10, 300, 210, 20);

        RemoveEmulator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveEmulator.setBorderPainted(false);
        RemoveEmulator.setContentAreaFilled(false);
        RemoveEmulator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveEmulatorActionPerformed(evt);
            }
        });
        add(RemoveEmulator);
        RemoveEmulator.setBounds(260, 300, 30, 30);

        AddEmulator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddEmulator.setBorderPainted(false);
        AddEmulator.setContentAreaFilled(false);
        AddEmulator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEmulatorActionPerformed(evt);
            }
        });
        add(AddEmulator);
        AddEmulator.setBounds(220, 300, 40, 30);

        EditEmulator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditEmulator.setBorderPainted(false);
        EditEmulator.setContentAreaFilled(false);
        EditEmulator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditEmulatorActionPerformed(evt);
            }
        });
        add(EditEmulator);
        EditEmulator.setBounds(290, 300, 30, 30);

        ImportScript.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        ImportScript.setBorderPainted(false);
        ImportScript.setContentAreaFilled(false);
        ImportScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportScriptActionPerformed(evt);
            }
        });
        add(ImportScript);
        ImportScript.setBounds(290, 380, 40, 30);
        add(Scripts);
        Scripts.setBounds(10, 415, 340, 20);

        RemoveScript.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveScript.setBorderPainted(false);
        RemoveScript.setContentAreaFilled(false);
        RemoveScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveScriptActionPerformed(evt);
            }
        });
        add(RemoveScript);
        RemoveScript.setBounds(220, 380, 30, 30);

        EditScript.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditScript.setBorderPainted(false);
        EditScript.setContentAreaFilled(false);
        EditScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditScriptActionPerformed(evt);
            }
        });
        add(EditScript);
        EditScript.setBounds(260, 380, 30, 30);

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setText("Скрипты");
        add(jLabel34);
        jLabel34.setBounds(10, 360, 60, 14);

        ExecScriptServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/shifton1.png"))); // NOI18N
        ExecScriptServer.setBorderPainted(false);
        ExecScriptServer.setContentAreaFilled(false);
        ExecScriptServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExecScriptServerActionPerformed(evt);
            }
        });
        add(ExecScriptServer);
        ExecScriptServer.setBounds(120, 380, 40, 30);

        Trace.setText("Трасса");
        add(Trace);
        Trace.setBounds(10, 380, 70, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("xls 2");
        add(jLabel1);
        jLabel1.setBounds(580, 65, 50, 20);

        ImportMetaEquipment2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        ImportMetaEquipment2.setBorderPainted(false);
        ImportMetaEquipment2.setContentAreaFilled(false);
        ImportMetaEquipment2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportMetaEquipment2ActionPerformed(evt);
            }
        });
        add(ImportMetaEquipment2);
        ImportMetaEquipment2.setBounds(580, 40, 30, 30);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("xml-1");
        add(jLabel2);
        jLabel2.setBounds(460, 70, 40, 14);

        ExecScriptClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/shifton1.png"))); // NOI18N
        ExecScriptClient.setBorderPainted(false);
        ExecScriptClient.setContentAreaFilled(false);
        ExecScriptClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExecScriptClientActionPerformed(evt);
            }
        });
        add(ExecScriptClient);
        ExecScriptClient.setBounds(70, 380, 40, 30);

        jLabel19.setText("сервер");
        add(jLabel19);
        jLabel19.setBounds(120, 360, 50, 16);

        jLabel30.setText("пароль операции");
        add(jLabel30);
        jLabel30.setBounds(10, 540, 120, 16);

        RuntimeEdit.setText("Редакт. \"на лету\"");
        RuntimeEdit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RuntimeEditItemStateChanged(evt);
            }
        });
        add(RuntimeEdit);
        RuntimeEdit.setBounds(10, 500, 130, 20);

        RunTimeSaveChanges.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        RunTimeSaveChanges.setBorderPainted(false);
        RunTimeSaveChanges.setContentAreaFilled(false);
        RunTimeSaveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunTimeSaveChangesActionPerformed(evt);
            }
        });
        add(RunTimeSaveChanges);
        RunTimeSaveChanges.setBounds(280, 500, 30, 30);

        RunTimeChanges.setEnabled(false);
        add(RunTimeChanges);
        RunTimeChanges.setBounds(320, 500, 40, 25);

        RunTimeChangesLabel.setText("Изменений");
        add(RunTimeChangesLabel);
        RunTimeChangesLabel.setBounds(300, 480, 80, 16);
        add(MetaTypes);
        MetaTypes.setBounds(650, 50, 140, 20);

        jLabel31.setText(" ограничений");
        add(jLabel31);
        jLabel31.setBounds(790, 145, 80, 16);

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setText("xls 1");
        add(jLabel35);
        jLabel35.setBounds(540, 70, 30, 14);

        ImportXML.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        ImportXML.setBorderPainted(false);
        ImportXML.setContentAreaFilled(false);
        ImportXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportXMLActionPerformed(evt);
            }
        });
        add(ImportXML);
        ImportXML.setBounds(420, 40, 30, 30);

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel32.setText("xml");
        add(jLabel32);
        jLabel32.setBounds(420, 70, 30, 14);

        ExportXML.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        ExportXML.setBorderPainted(false);
        ExportXML.setContentAreaFilled(false);
        ExportXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportXMLActionPerformed(evt);
            }
        });
        add(ExportXML);
        ExportXML.setBounds(460, 40, 30, 30);

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel36.setText("xml");
        add(jLabel36);
        jLabel36.setBounds(330, 130, 30, 20);

        ExportXMLEquipment.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        ExportXMLEquipment.setBorderPainted(false);
        ExportXMLEquipment.setContentAreaFilled(false);
        ExportXMLEquipment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportXMLEquipmentActionPerformed(evt);
            }
        });
        add(ExportXMLEquipment);
        ExportXMLEquipment.setBounds(330, 100, 30, 30);

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("xml");
        add(jLabel37);
        jLabel37.setBounds(330, 290, 30, 20);

        ExportXMLView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        ExportXMLView.setBorderPainted(false);
        ExportXMLView.setContentAreaFilled(false);
        ExportXMLView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportXMLViewActionPerformed(evt);
            }
        });
        add(ExportXMLView);
        ExportXMLView.setBounds(330, 260, 30, 30);

        jLabel39.setText("Контроллер");
        add(jLabel39);
        jLabel39.setBounds(380, 580, 80, 16);
        add(DevicesRW);
        DevicesRW.setBounds(460, 580, 210, 20);
        add(jSeparator8);
        jSeparator8.setBounds(370, 570, 400, 10);

        UnitRW.setText("0");
        add(UnitRW);
        UnitRW.setBounds(710, 580, 30, 25);

        jLabel38.setText("Unit");
        add(jLabel38);
        jLabel38.setBounds(680, 580, 30, 16);

        HEXValue.setText("hex");
        HEXValue.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HEXValueItemStateChanged(evt);
            }
        });
        add(HEXValue);
        HEXValue.setBounds(550, 640, 50, 20);

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setText("Шлюзы Modbus");
        add(jLabel40);
        jLabel40.setBounds(10, 205, 210, 14);
        add(LogUnits);
        LogUnits.setBounds(10, 140, 210, 20);

        RemoveLogUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveLogUnit.setBorderPainted(false);
        RemoveLogUnit.setContentAreaFilled(false);
        RemoveLogUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveLogUnitActionPerformed(evt);
            }
        });
        add(RemoveLogUnit);
        RemoveLogUnit.setBounds(260, 140, 30, 30);

        AddLogUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddLogUnit.setBorderPainted(false);
        AddLogUnit.setContentAreaFilled(false);
        AddLogUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddLogUnitActionPerformed(evt);
            }
        });
        add(AddLogUnit);
        AddLogUnit.setBounds(220, 140, 40, 30);

        EditLogUnit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditLogUnit.setBorderPainted(false);
        EditLogUnit.setContentAreaFilled(false);
        EditLogUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditLogUnitActionPerformed(evt);
            }
        });
        add(EditLogUnit);
        EditLogUnit.setBounds(290, 140, 30, 30);
        add(jSeparator9);
        jSeparator9.setBounds(10, 590, 360, 10);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setText("Переменные окружения");
        add(jLabel41);
        jLabel41.setBounds(10, 325, 180, 14);
        add(EnvValue);
        EnvValue.setBounds(10, 340, 210, 20);

        RemoveEnvValue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveEnvValue.setBorderPainted(false);
        RemoveEnvValue.setContentAreaFilled(false);
        RemoveEnvValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveEnvValueActionPerformed(evt);
            }
        });
        add(RemoveEnvValue);
        RemoveEnvValue.setBounds(260, 340, 30, 30);

        AddEnvValue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddEnvValue.setBorderPainted(false);
        AddEnvValue.setContentAreaFilled(false);
        AddEnvValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEnvValueActionPerformed(evt);
            }
        });
        add(AddEnvValue);
        AddEnvValue.setBounds(220, 340, 40, 30);

        EditEnvValue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditEnvValue.setBorderPainted(false);
        EditEnvValue.setContentAreaFilled(false);
        EditEnvValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditEnvValueActionPerformed(evt);
            }
        });
        add(EditEnvValue);
        EditEnvValue.setBounds(290, 340, 30, 30);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText(" все");
        add(jLabel3);
        jLabel3.setBounds(170, 360, 40, 14);

        ExportXMLAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        ExportXMLAll.setBorderPainted(false);
        ExportXMLAll.setContentAreaFilled(false);
        ExportXMLAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportXMLAllActionPerformed(evt);
            }
        });
        add(ExportXMLAll);
        ExportXMLAll.setBounds(500, 40, 30, 30);

        ExportScripts.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        ExportScripts.setBorderPainted(false);
        ExportScripts.setContentAreaFilled(false);
        ExportScripts.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportScriptsActionPerformed(evt);
            }
        });
        add(ExportScripts);
        ExportScripts.setBounds(170, 380, 30, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("xml-*");
        add(jLabel5);
        jLabel5.setBounds(500, 70, 40, 14);

        FullScreen.setText("Полный экран");
        add(FullScreen);
        FullScreen.setBounds(160, 500, 120, 20);

        OnOffNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png"))); // NOI18N
        OnOffNode.setBorderPainted(false);
        OnOffNode.setContentAreaFilled(false);
        OnOffNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnOffNodeActionPerformed(evt);
            }
        });
        add(OnOffNode);
        OnOffNode.setBounds(320, 15, 40, 40);

        CIDLocal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        CIDLocal.setBorderPainted(false);
        CIDLocal.setContentAreaFilled(false);
        CIDLocal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CIDLocalActionPerformed(evt);
            }
        });
        add(CIDLocal);
        CIDLocal.setBounds(270, 550, 40, 30);

        IEC61850OnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/status_gray.png"))); // NOI18N
        IEC61850OnOff.setBorderPainted(false);
        IEC61850OnOff.setContentAreaFilled(false);
        IEC61850OnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IEC61850OnOffActionPerformed(evt);
            }
        });
        add(IEC61850OnOff);
        IEC61850OnOff.setBounds(230, 550, 40, 40);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Профилирование");
        add(jLabel6);
        jLabel6.setBounds(10, 590, 130, 16);

        IEC61850ClientGUI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png"))); // NOI18N
        IEC61850ClientGUI.setBorderPainted(false);
        IEC61850ClientGUI.setContentAreaFilled(false);
        IEC61850ClientGUI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IEC61850ClientGUIActionPerformed(evt);
            }
        });
        add(IEC61850ClientGUI);
        IEC61850ClientGUI.setBounds(310, 550, 40, 30);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("МЭК 60870");
        add(jLabel7);
        jLabel7.setBounds(150, 570, 70, 16);

        ProfilerOnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/status_gray.png"))); // NOI18N
        ProfilerOnOff.setBorderPainted(false);
        ProfilerOnOff.setContentAreaFilled(false);
        ProfilerOnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProfilerOnOffActionPerformed(evt);
            }
        });
        add(ProfilerOnOff);
        ProfilerOnOff.setBounds(10, 630, 40, 40);
        add(jSeparator5);
        jSeparator5.setBounds(380, 270, 460, 10);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel21.setText("Конфигурации");
        add(jLabel21);
        jLabel21.setBounds(770, 440, 100, 14);
        add(Profilers);
        Profilers.setBounds(10, 610, 210, 20);

        RemoveProfiler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveProfiler.setBorderPainted(false);
        RemoveProfiler.setContentAreaFilled(false);
        RemoveProfiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveProfilerActionPerformed(evt);
            }
        });
        add(RemoveProfiler);
        RemoveProfiler.setBounds(260, 600, 30, 30);

        AddProfiler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddProfiler.setBorderPainted(false);
        AddProfiler.setContentAreaFilled(false);
        AddProfiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddProfilerActionPerformed(evt);
            }
        });
        add(AddProfiler);
        AddProfiler.setBounds(220, 600, 40, 30);

        EditProfiler.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditProfiler.setBorderPainted(false);
        EditProfiler.setContentAreaFilled(false);
        EditProfiler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditProfilerActionPerformed(evt);
            }
        });
        add(EditProfiler);
        EditProfiler.setBounds(290, 600, 30, 30);
        add(jSeparator10);
        jSeparator10.setBounds(10, 540, 350, 10);

        jLabel42.setText("клиент");
        add(jLabel42);
        jLabel42.setBounds(70, 360, 50, 16);

        ProfilerResults.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/load.png"))); // NOI18N
        ProfilerResults.setBorderPainted(false);
        ProfilerResults.setContentAreaFilled(false);
        ProfilerResults.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProfilerResultsActionPerformed(evt);
            }
        });
        add(ProfilerResults);
        ProfilerResults.setBounds(190, 640, 40, 30);

        OnlyView.setText("Без данных");
        OnlyView.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                OnlyViewItemStateChanged(evt);
            }
        });
        add(OnlyView);
        OnlyView.setBounds(160, 520, 110, 20);

        IEC60870OnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/status_gray.png"))); // NOI18N
        IEC60870OnOff.setBorderPainted(false);
        IEC60870OnOff.setContentAreaFilled(false);
        IEC60870OnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IEC60870OnOffActionPerformed(evt);
            }
        });
        add(IEC60870OnOff);
        IEC60870OnOff.setBounds(120, 550, 40, 40);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("МЭК 61850");
        add(jLabel9);
        jLabel9.setBounds(170, 550, 70, 16);

        OnOff2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png"))); // NOI18N
        OnOff2.setBorderPainted(false);
        OnOff2.setContentAreaFilled(false);
        OnOff2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnOff2ActionPerformed(evt);
            }
        });
        add(OnOff2);
        OnOff2.setBounds(130, 465, 30, 30);

        OrigHW.setText("Ориг. пропорции");
        OrigHW.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OrigHWActionPerformed(evt);
            }
        });
        add(OrigHW);
        OrigHW.setBounds(10, 520, 140, 20);
        add(ForceConnect);
        ForceConnect.setBounds(60, 440, 30, 19);

        jLabel22.setText("Игнорировать ошибки");
        add(jLabel22);
        jLabel22.setBounds(130, 440, 160, 16);
        add(ForceRender);
        ForceRender.setBounds(100, 440, 30, 19);
        add(ForceDeploy);
        ForceDeploy.setBounds(20, 440, 30, 19);

        ProfileName.setText("...");
        add(ProfileName);
        ProfileName.setBounds(50, 650, 130, 16);
        add(Gates);
        Gates.setBounds(10, 220, 210, 20);

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel43.setText("Контроллер (драйвер)");
        add(jLabel43);
        jLabel43.setBounds(10, 165, 210, 14);

        AddGate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddGate.setBorderPainted(false);
        AddGate.setContentAreaFilled(false);
        AddGate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddGateActionPerformed(evt);
            }
        });
        add(AddGate);
        AddGate.setBounds(220, 220, 40, 30);

        RemoveGate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveGate.setBorderPainted(false);
        RemoveGate.setContentAreaFilled(false);
        RemoveGate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveGateActionPerformed(evt);
            }
        });
        add(RemoveGate);
        RemoveGate.setBounds(260, 220, 30, 30);

        EditGate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditGate.setBorderPainted(false);
        EditGate.setContentAreaFilled(false);
        EditGate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditGateActionPerformed(evt);
            }
        });
        add(EditGate);
        EditGate.setBounds(290, 220, 30, 30);

        ExportNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/battery.png"))); // NOI18N
        ExportNode.setBorderPainted(false);
        ExportNode.setContentAreaFilled(false);
        ExportNode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportNodeActionPerformed(evt);
            }
        });
        add(ExportNode);
        ExportNode.setBounds(330, 600, 30, 50);

        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);
        add(jSeparator7);
        jSeparator7.setBounds(325, 593, 50, 100);
    }// </editor-fold>//GEN-END:initComponents

    private void ImportMetaDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportMetaDataActionPerformed
        FileNameExt fname = main.getInputFileName("Импорт Excel-файла метаданных оборудования, формат СНЭЭ-1", "*.xls", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(), "Meta-Data import", fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact oo) {
                new APICall<ArrayList<OidString>>(main) {
                    @Override
                    public Call<ArrayList<OidString>> apiFun() {
                        return main2.service2.importMetaData(main.getDebugToken(), Password.getText(), oo.getOid());
                       }
                    @Override
                    public void onSucess(ArrayList<OidString> oo) {
                        System.out.print(oo.get(0));
                        System.out.print(oo.get(1));
                        if (oo.get(0).valid() && oo.get(1).valid())
                            popup("Импорт метаданных выполнен");
                        else
                            main.sendEventPanel(EventLogToFront,0,0,"Ошибки импорта");
                        //popup("Импорт метаданных оборудования" + (!oo.o1.valid() ? " не" : "") + " выполнен");
                        //popup("Импорт метаданных ЧМИ" + (!oo.o2.valid() ? " не" : "") + " выполнен");
                        refreshMetaData();
                    }
                };
            }
        };
    }//GEN-LAST:event_ImportMetaDataActionPerformed

    private void createMetaDataList() {
        MetaFile.removeAll();
        new APICall<ArrayList<DBRequest>>(main) {
            @Override
            public Call<ArrayList<DBRequest>> apiFun() {
                return main.getService().getEntityList(main.getDebugToken(), "ESS2MetaFile", Values.GetAllModeActual, 1);
                }
            @Override
            public void onSucess(ArrayList<DBRequest> oo) {
                try {
                    metaData.clear();
                    for (int i=oo.size()-1; i>=0;i--) {
                        DBRequest request = oo.get(i);
                        ESS2MetaFile file = (ESS2MetaFile) request.get(main.gson);
                        metaData.add(file);
                        //ConstValue cc = metaTypesMap.get(file.getMetaType());
                        //MetaFile.add((cc==null ? "" : cc.title()+" ")+file.toString());
                        }
                    metaData.sort(new Comparator<ESS2MetaFile>() {
                        @Override
                        public int compare(ESS2MetaFile o1, ESS2MetaFile o2) {
                            long diff = o2.getFile().getRef().getDate().timeInMS() - o1.getFile().getRef().getDate().timeInMS();
                            return diff<0 ? -1 :(diff>0 ? 1 : 0);
                            }
                        });
                    for(ESS2MetaFile file : metaData)
                        MetaFile.add(file.toString());
                    if (MetaFile.getItemCount()!=0)
                        setMetaTypeSelector(metaData.get(MetaFile.getSelectedIndex()).getMetaType());
                    } catch (Exception ee) {
                        System.out.println(ee.toString());
                        }
            }
        };
    }

    private void refreshNodes() {
        Nodes.removeAll();
        new APICall<ArrayList<DBRequest>>(main) {
            @Override
            public Call<ArrayList<DBRequest>> apiFun() {
                return main.getService().getEntityList(main.getDebugToken(), "ESS2Node", Values.GetAllModeActual, 5);
                }
            @Override
            public void onSucess(ArrayList<DBRequest> oo) {
                try {
                    nodes.clear();
                    Nodes.add("...");
                    for (DBRequest request : oo) {
                        ESS2Node node = (ESS2Node) request.get(main.gson);
                        nodes.add(node);
                        Nodes.add(node.getTitle());
                        }
                    } catch (Exception ee) {
                        System.out.println(ee.toString());
                        }
            }
        };
    }

    private void refreshSelectedArchitecture(){
        refreshEquipments();
        refreshViews();
        refreshEmulators();
        refreshScripts();
        refreshDevices();
        refreshEnvValues();
        refreshProfilers();
        refreshGates();
        architecture.createStreamRegisterList();
        }

    private void refreshArchitectures() {
        final int idx = Architectures.getItemCount() != 0 ? Architectures.getSelectedIndex() : -1;
        Architectures.removeAll();
        new APICall<ArrayList<DBRequest>>(main) {
            @Override
            public Call<ArrayList<DBRequest>> apiFun() {
                return main.getService().getEntityList(main.getDebugToken(), "ESS2Architecture", Values.GetAllModeActual, 4);
                }
            @Override
            public void onSucess(ArrayList<DBRequest> oo) {
                try {
                    architectures.clear();
                    for (DBRequest request : oo) {
                        ESS2Architecture node = (ESS2Architecture) request.get(main.gson);
                        architectures.add(node);
                        Architectures.add(node.getTitle());
                        }
                    architectures.createMap();
                    if (idx != -1)
                        Architectures.select(idx);
                    architecture = Architectures.getItemCount()==0 ? null : architectures.get(Architectures.getSelectedIndex());
                    refreshSelectedArchitecture();
                } catch (Exception ee) {
                    System.out.println(ee.toString());
                }
            }
        };
    }

    private void refreshDevices() {
        Devices.removeAll();
        DevicesRW.removeAll();
        if (architecture == null)
            return;
        for (ESS2Device connector : architecture.getDevices()){
            Devices.add(connector.getFullTitle());
            DevicesRW.add(connector.getFullTitle());
            }
        }
    private void refreshLogUnits() {
        LogUnits.removeAll();
        if (architecture == null)
            return;
        if (Equipments.getItemCount() == 0)
            return;
        ESS2Equipment equipment = architecture.getEquipments().get(Equipments.getSelectedIndex());
        int i=0;
        for (ESS2LogUnit connector : equipment.getLogUnits()){
            LogUnits.add(connector.getFullTitle());
            }
       }

    private void refreshEquipments() {
        Equipments.removeAll();
        if (architecture == null)
            return;
        for (ESS2Equipment equipment : architecture.getEquipments()) {
            Equipments.add(equipment.getTitle());
            }
        refreshLogUnits();
        }

    private void refreshViews() {
        Views.removeAll();
        if (architecture == null)
            return;
        for (ESS2View view : architecture.getViews()) {
            Views.add(view.getTitle());
            }
        }
    private void refreshEmulators() {
        Emulators.removeAll();
        if (architecture == null)
            return;
        for (ESS2EquipEmulator emulator : architecture.getEmulators()) {
            Emulators.add(emulator.getTitle());
            }
        }
    private void refreshGates() {
        Gates.removeAll();
        if (architecture == null)
            return;
        for (ESS2ModBusGate gate : architecture.getGates()) {
            ESS2Device device = architecture.getDevices().getById(gate.getDevice().getOid());
            if (device!=null)
                gate.getDevice().setRef(device);
            Gates.add(gate.getTitle());
            }
        }
    private void refreshProfilers() {
        Profilers.removeAll();
        if (architecture == null)
            return;
        for (ESS2ProfilerModule module : architecture.getProfilers()) {
            Profilers.add(module.getTitle());
        }
    }

    private void refreshArchtectureState(){
        new APICall<ArrayList<Long>>(main) {
            @Override
            public Call<ArrayList<Long>> apiFun() {
                return main2.service2.getArchitectureState(main.getDebugToken());
                }
            @Override
            public void onSucess(ArrayList<Long> val) {
                int state = val.get(0).intValue();
                Deploy.setIcon(new javax.swing.ImageIcon(getClass().getResource(archStateIcons[state])));
                Connect.setIcon(new javax.swing.ImageIcon(getClass().getResource(connStateIcons[state])));
                popup("Состояние "+Values.constMap().getGroupMapByValue("ArchState").get(state).title());
                if (state==Values.ASNotDeployed)
                    return;
                long oid = val.get(1);
                int idx=-1;
                for(int i=0;i<architectures.size();i++)
                    if (architectures.get(i).getOid()==oid){
                        idx=i;
                        break;
                        }
                if (idx==-1){
                    popup("Не найдена развернутая архитектура, oid="+oid);
                    return;
                    }
                Architectures.select(idx);
                loadDeployedArchitecture(oid, state);
                deployingViewState();
                deployed.setArchitectureState(state);
                }
            };
        }
    private void clearRunTimeChanges(){
        runTimeChangesCount = 0;
        RunTimeSaveChanges.setVisible(false);
        RunTimeChanges.setVisible(false);
        RunTimeChangesLabel.setVisible(false);
        }
    private void setFullScreenOff(){
        if (screen==null)
            return;
        screen.dispose();
        screen = null;
        }
    public void setRenderingOff() {
        setRenderingOff(false);
        setRenderingOff(true);
        }
    public void setRenderingOff(boolean second){
        final ESS2View view = main2.getCurrentView();
        if (!second) {
            main2.setCurrentView(null);
            OnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png")));
            setFullScreenOff();
            }
        else {
            main2.setCurrentView2(null);
            OnOff2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png")));
            }
        OnOffNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png")));
        main.sendEvent(EventPLMOff,second ? 1 : 0);
        boolean save = runTimeChangesCount!=0;
        clearRunTimeChanges();
        if (save)
            new OK(200, 200, "Сохранить изменения \"на лету\"", new I_Button() {
                @Override
                public void onPush() {
                    saveRunTimeChanges(view);
                }
            });
        }

    public void fullScreenOn(){
        if (!FullScreen.isSelected())
            return;
        screen = new ESSServiceGUIScreen(main, new I_Button() {
             @Override
             public void onPush() {
                 if (screen!=null)
                    screen.dispose();
                 screen = null;
                 }
            });
        Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
        ESS2View view = deployed.getViews().get(Views.getSelectedIndex());
        ScreenMode screenMode = new ScreenMode(false,view.getView().getWidth(),view.getView().getHeight(),sSize.width,sSize.height);
        screen.setVisible(true);
        screen.eventPanel(EventPLMOn,0,Trace.isSelected() ? 1 : 0,"",screenMode);
        screen.refresh();
        }
    public void setRenderingOn(boolean second) {
        lastViewIndex = Views.getSelectedIndex();
        ESS2View view = deployed.getViews().get(Views.getSelectedIndex());
        ScreenMode screenMode = new ScreenMode(OrigHW.isSelected(),view.getView().getWidth(),view.getView().getHeight(),0,0);
        main2.setRenderingOn(0,view,Trace.isSelected(),ForceRender.isSelected(),second,screenMode);
        if (!second){
            OnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-on.png")));
            fullScreenOn();
            }
        else
            OnOff2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-on.png")));
            }

    private void OnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnOffActionPerformed
        if (main2.getCurrentView()!=null){
            setRenderingOff(false);
            }
        else{
            if (!deployed.isConnected())
                return;
            setRenderingOn(false);
            }
    }//GEN-LAST:event_OnOffActionPerformed

    public void refreshSettingsList(){
        SettingsList.removeAll();
        settingRegisters.clear();
        if (!deployed.isDeployed())
            return;
        for(ESS2Equipment equipment : deployed.getEquipments()){
            for(Meta2Register register : equipment.getEquipment().getRegList().getList()){
                if (!(register instanceof Meta2SettingRegister))
                    continue;
                Meta2SettingRegister set = (Meta2SettingRegister) register;
                int level = set.getLevel();
                if (level>Values.MinEquipLevel){
                    System.out.println("Уровень регистра-уставки ="+level+" "+set.getFullTitle());
                    continue;
                    }
                settingRegisters.add(set);
                SettingsList.add(set.getFullTitle());
                }
            }
        if (SettingsList.getItemCount()!=0)
            refreshSetting();
        }

    public void refreshSetting() {
        if (SettingsList.getItemCount() == 0)
            return;
        //----------------- TODO ---------------- Уставки по девайсам и оборудованию
        /*
        Meta2SettingRegister register = settingRegisters.get(SettingsList.getSelectedIndex());
        String name = register.getTitle();
        UtilsDesktop.setLabelText(SettingsName, name, 50);
        DefValueFormula.setText(register.getDefValueFormula());
        MinValueFormula.setText(register.getMinValueFormula());
        MaxValueFormula.setText(register.getMaxValueFormula());
        SettingShortName.setText(register.getShortName());
        SettingUnit.setText(register.getUnit());
        SettingRegNum.setText("" + register.getRegNum());
        try {
            if(deployed.getArchitectureState()==Values.ASConnected){
                calculator.calculate(deployed, register,null);
                SettingValue.setText("" + calculator.getValue());
                MaxValue.setText(calculator.isMaxFormulaValid() ? "" + calculator.getMaxValue() : "");
                MinValue.setText(calculator.isMinFormulaValid() ? "" + calculator.getMinValue() : "");
                DefValue.setText(calculator.isDefFormulaValid() ? "" + calculator.getDefValue() : "");
                }
            else{
                SettingValue.setText("");
                MaxValue.setText("");
                MinValue.setText("");
                DefValue.setText("");
                }
            } catch (UniException ee) {
                popup("Калькулятор уставок: " + ee.toString());
                }
         */
    }

    private int regRWValue=0;
    private int regRWRegNum=0;
    private void setRW(JCheckBox hex, JTextField fld, int val){
        long vv = val & 0x0FFFFFFFFl;
        String ss = hex.isSelected() ? Long.toString(vv, 16) : "" + val;
        fld.setText(ss);
        }
    private Pair<String,Integer> getRW(JCheckBox hex, JTextField fld){
        try {
            String ss = fld.getText();
            long val = hex.isSelected() ? Long.parseLong(ss,16) : Long.parseLong(ss);
            return new Pair<>(null,(int)val);
            } catch (Exception ee){
                return new Pair<>("Недопустимый формат целого "+ fld.getText(),null);
                }
    }
    private void DeviceReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeviceReadActionPerformed
        try {
            if (deployed.getArchitectureState()!=Values.ASConnected) {
                popup("Оборудование не подключено");
                return;
                }
            ESS2Device device = deployed.getDevices().get(DevicesRW.getSelectedIndex());
            Pair<String,Integer> vv = getRW(HEXReg,RegNum);
            if (vv.o1!=null){
                popup(vv.o1);
                return;
                }
            regRWRegNum = vv.o2;
            int unitRW = Integer.parseInt(UnitRW.getText());
            regRWValue = device.getDriver().readRegister(device.getShortName(), unitRW,regRWRegNum) & 0x0FFFF;
            if (WriteFloat.isSelected() || WriteInt32.isSelected()){
                int two = device.getDriver().readRegister(device.getShortName(), unitRW,regRWRegNum+1) & 0x0FFFF;
                regRWValue |= two<<16;
                if (WriteInt32.isSelected())
                    setRW(HEXValue,RegValue,regRWValue);
                else{
                    float dd = Float.intBitsToFloat(regRWValue);
                    RegValue.setText(""+dd);
                    }
                }
            else
                setRW(HEXValue,RegValue,regRWValue);
            } catch (Exception e1) {
                popup("Ошибка чтения: "+e1.toString());
                }
    }//GEN-LAST:event_DeviceReadActionPerformed

    private void DeviceWriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeviceWriteActionPerformed
        try {
            if (deployed.getArchitectureState()!=Values.ASConnected) {
                popup("Оборудование не подключено");
                return;
                }
            ESS2Device device = deployed.getDevices().get(DevicesRW.getSelectedIndex());
            Pair<String,Integer> vv = getRW(HEXReg,RegNum);
            if (vv.o1!=null){
                popup(vv.o1);
                return;
                }
            regRWRegNum = vv.o2;
            int unitRW = Integer.parseInt(UnitRW.getText());
            if (WriteFloat.isSelected()) {
                int floatValue = Float.floatToIntBits(Float.parseFloat(RegValue.getText()));
                device.getDriver().writeRegister(device.getShortName(), unitRW,regRWRegNum, floatValue & 0x0FFFF);
                device.getDriver().writeRegister(device.getShortName(), unitRW,regRWRegNum+1, (floatValue>>16) & 0x0FFFF);
                return;
                }
            Pair<String,Integer> vv2 = getRW(HEXValue,RegValue);
            if (vv2.o1!=null){
                popup(vv2.o1);
                return;
                }
            regRWValue = vv2.o2;
            device.getDriver().writeRegister(device.getShortName(), unitRW,regRWRegNum, regRWValue & 0x0FFFF);
            if (WriteInt32.isSelected()){
                device.getDriver().writeRegister(device.getShortName(), unitRW,regRWRegNum+1, (regRWValue>>16) & 0x0FFFF);
                }
            } catch (Exception e1) {
                popup("Ошибка записи: "+e1.toString());
                }
    }//GEN-LAST:event_DeviceWriteActionPerformed

    private void writeSettings() {
        if (SettingsList.getItemCount() == 0)
            return;
        try {
            calculator.parseAndWrite(SettingValue.getText(), CheckLimits.isSelected());
        } catch (UniException ee) {
            popup("Калькулятор уставок: " + ee.toString());
        }
    }

    private void WriteSettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WriteSettingActionPerformed
        writeSettings();
    }//GEN-LAST:event_WriteSettingActionPerformed

    private void SettingsListItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SettingsListItemStateChanged
        refreshSetting();
    }//GEN-LAST:event_SettingsListItemStateChanged

    private void SettingEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SettingEditActionPerformed
        if (SettingsList.getItemCount() == 0)
            return;
        MetaSettingRegister register = main2.meta.getSettings().get(SettingsList.getSelectedIndex());
        new ESSSettingsRegister(main2.meta, register, main2.plm, new I_Success() {
            @Override
            public void onSuccess() {
                refreshSetting();
            }
        }).setVisible(true);
    }//GEN-LAST:event_SettingEditActionPerformed

    private void SettingValueKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SettingValueKeyPressed
        if (evt.getKeyCode() != 10) return;
        writeSettings();
        MainBaseFrame.viewUpdate(evt, true);
    }//GEN-LAST:event_SettingValueKeyPressed

    public void refreshMetaData() {
        createMetaDataList();
        changesCount = 0;
        MetaDataSaveChanges.setVisible(false);
        MetaDataChanges.setVisible(false);
        MetaDataChangesLabel.setVisible(false);
        }
    public void refreshScripts() {
        int idx = -1;
        if (Scripts.getItemCount()!=0)
            idx = Scripts.getSelectedIndex();
        Scripts.removeAll();
        if (architecture == null)
            return;
        for (ESS2ScriptFile script : architecture.getScripts()) {
            Scripts.add(script.toString());
            }
        if (idx!=-1 && idx<=Scripts.getItemCount())
            Scripts.select(idx);
            }
    public void refreshEnvValues() {
        EnvValue.removeAll();
        if (architecture == null)
            return;
        for (ESS2EnvValue script : architecture.getEnvValues()) {
            EnvValue.add(script.getTitle());
            }
        }
    private void RefreshMetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshMetaActionPerformed
        refreshMetaData();
        }//GEN-LAST:event_RefreshMetaActionPerformed

    private void RemoveMetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveMetaActionPerformed
        if (MetaFile.getItemCount() == 0)
            return;
        metaFile = metaData.get(MetaFile.getSelectedIndex());
        new OK(200, 200, "Удалить " + metaFile.toString(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().removeArtifact(main.getDebugToken(),metaFile.getFile().getOid());
                        }
                    @Override
                    public void onSucess(JEmpty oo) {
                        new APICall<JBoolean>(main) {
                            @Override
                            public Call<JBoolean> apiFun() {
                                return main.getService().removeEntity(main.getDebugToken(),"ESS2MetaFile",metaFile.getOid());
                                }

                            @Override
                            public void onSucess(JBoolean oo) {
                                refreshMetaData();
                            }
                        };
                    }
                };
            }
        });

    }//GEN-LAST:event_RemoveMetaActionPerformed

    private void AddMetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddMetaActionPerformed
        int newType = metaTypes.get(MetaTypes.getSelectedIndex()).value();
        final ESS2MetaFile metaFile = new ESS2MetaFile();
        metaFile.setMetaType(newType);
        Meta2XML meta2XML;
        if (Values.isEquipmentType(newType))
            meta2XML = new Meta2Equipment();
        else{
            meta2XML = new Meta2GUIView();
            Meta2GUIForm newForm = new Meta2GUIForm();
            newForm.setShortName("main");
            newForm.setTitle(MainFormName);
            ((Meta2GUIView)meta2XML).getForms().add(newForm);
            }
        meta2XML.setShortName("newItem");
        meta2XML.setTitle("Новый");
        meta2XML.setXmlType(newType);
        metaFile.synchWithMeta2XML(meta2XML);
        String dataString = new Meta2XStream().toXML(meta2XML);
        new APICall<Artifact>(main){
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().createArtifactFromString(main.getDebugToken(),"MetaData.xml",dataString);
                }
            @Override
            public void onSucess(Artifact oo) {
                metaFile.getFile().setOidRef(oo);
                new APICall<JLong>(main){
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(),new DBRequest(metaFile,main.gson),0);
                        }
                    @Override
                    public void onSucess(JLong oo) {
                        refreshMetaData();
                        }
                };
            }
        };
    }//GEN-LAST:event_AddMetaActionPerformed

    private void EditMetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditMetaActionPerformed
        if (MetaFile.getItemCount() == 0)
            return;
        if (changesCount != 0) {
            popup("Несохраненные изменения: сохранить или обновить список");
            return;
            }
        metaFile = metaData.get(MetaFile.getSelectedIndex());
        main.loadFileAsString(metaFile.getFile().getRef(), new I_DownLoadString() {
            @Override
            public void onSuccess(String ss) {
                try {
                    metaXML = (Meta2XML) new Meta2XStream().fromXML(ss);
                    System.out.println(metaXML.getTitle());
                    metaXML.setHigh(null);
                    metaXML.createMap();
                    metaXML.testLocalConfiguration();
                    System.out.println(metaXML.getErrors());
                    String zz = WizardBaseView.openWizard(metaFile.getFile().getRef().createArtifactFileName(),main, metaXML, new I_Value<String>() {
                        @Override
                        public void onEnter(String value) {
                            changesCount++;
                            MetaDataChanges.setText("" + changesCount);
                            MetaDataSaveChanges.setVisible(true);
                            MetaDataChanges.setVisible(true);
                            MetaDataChangesLabel.setVisible(true);
                            System.out.println(value);
                            if (RuntimeEdit.isSelected())
                                main.sendEventPanel(EventGUIToFront,0,0,"");
                            }
                        });
                    if (zz != null)
                        System.out.println(zz);
                } catch (Exception ex) {
                    System.out.println("Ошибка конвертации xml: " + ex.toString());
                }
            }

            @Override
            public void onError(String mes) {
                System.out.println(mes);
            }
        });
    }//GEN-LAST:event_EditMetaActionPerformed

    private void MetaDataSaveChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MetaDataSaveChangesActionPerformed
        Pair<Boolean,Boolean> result = metaFile.synchWithMeta2XML(metaXML);
        if (result.o1){             // Синхронизовать заголовки
            new APICall<JEmpty>(main) {
                @Override
                public Call<JEmpty> apiFun() {
                    return main.getService().updateEntity(main.getDebugToken(), new DBRequest(metaFile,main.gson));
                }
                @Override
                public void onSucess(JEmpty oo) {}
                };
            }
        String dataString = new Meta2XStream().toXML(metaXML);
        main.updateArtifactFromString(metaFile.getFile().getRef(), dataString, new I_OK() {
            @Override
            public void onOK(Entity ent) {
                changesCount = 0;
                MetaDataSaveChanges.setVisible(false);
                MetaDataChanges.setVisible(false);
                MetaDataChangesLabel.setVisible(false);
                metaFile.getFile().setRef((Artifact) ent);
                metaFile.setShortName(metaXML.getShortName());
                metaFile.setTitle(metaXML.getTitle());
                metaFile.setComment(metaXML.getComment());
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(metaFile, main.gson));
                        }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshMetaData();
                        }
                    };
            }
        });
    }//GEN-LAST:event_MetaDataSaveChangesActionPerformed

    private void RemoveDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveDeviceActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Devices.getItemCount() == 0)
            return;
        final ESS2Device device = architecture.getDevices().get(Devices.getSelectedIndex());
        Pair<String, Integer> vv = architecture.testDeviceLinkCount(device.getOid());
        if (vv.o2.intValue()!=0){
            popup("Обнаружено "+vv.o2.intValue()+" ссылок на контроллер");
            System.out.println(vv.o1);
            return;
            }
        new OK(200, 200, "Удалить контроллер " + device.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().removeEntity(main.getDebugToken(), "ESS2Device", device.getOid());
                        }
                    @Override
                    public void onSucess(JBoolean val) {
                        refreshWithDelay(4);
                    }
                };
            }
        });
    }//GEN-LAST:event_RemoveDeviceActionPerformed

    private void AddDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddDeviceActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        new OK(200, 200, "Добавить контроллер", new I_Button() {
            @Override
            public void onPush() {
                final ESS2Device connector = new ESS2Device();
                connector.getESS2Architecture().setOid(architecture.getOid());
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(connector, main.gson), 0);
                        }
                    @Override
                    public void onSucess(JLong val) {
                        refreshArchitectures();
                    }
                };
            }
        });
    }//GEN-LAST:event_AddDeviceActionPerformed

    private void RemoveEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveEquipActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Equipments.getItemCount() == 0)
            return;
        final ESS2Equipment equipment = architecture.getEquipments().get(Equipments.getSelectedIndex());
        new OK(200, 200, "Удалить оборудование " + equipment.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                removeEquipmentData(equipment, true);
            }
        });
    }//GEN-LAST:event_RemoveEquipActionPerformed

    private void AddEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddEquipActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (MetaFile.getItemCount() == 0) {
            popup("Нет мета-файла оборудования");
            return;
        }
        final ESS2MetaFile metaFile = metaData.get(MetaFile.getSelectedIndex());
        if (!Values.isEquipmentType(metaFile.getMetaType())) {
            popup("Недопустимый тип для мета-файла оборудования");
            return;
        }
        new OK(200, 200, "Добавить оборудование "+metaFile.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                final ESS2Equipment equipment = new ESS2Equipment("...", "Новое "+metaFile.getTitle(), metaFile.getComment());
                equipment.getESS2Architecture().setOid(architecture.getOid());
                equipment.getMetaFile().setOid(metaFile.getOid());
                equipment.setShortName(metaFile.getShortName());
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(equipment, main.gson), 0);
                    }

                    @Override
                    public void onSucess(JLong val) {
                        refreshArchitectures();
                    }
                };
            }
        });
    }//GEN-LAST:event_AddEquipActionPerformed

    private void removeArchitectureData(ESS2Architecture architecture) {
        for (ESS2Equipment equipment : architecture.getEquipments()) {
            removeEquipmentData(equipment, false);
        }
        for (ESS2View view : architecture.getViews()) {
            removeViewData(view, false);
        }
        MainBaseFrame.delayInGUI(4, new Runnable() {
            public void run() {
                refreshArchitectures();
            }
        });
    }

    private void removeViewData(ESS2View view, final boolean refresh) {
        new APICall<JBoolean>(main) {
            @Override
            public Call<JBoolean> apiFun() {
                return main.getService().removeEntity(main.getDebugToken(), "ESS2View", view.getOid());
            }

            @Override
            public void onSucess(JBoolean val) {
                if (refresh)
                    refreshWithDelay(4);
            }
        };
    }

    private void removeEquipmentData(ESS2Equipment equipment, final boolean refresh) {
        for (ESS2LogUnit connector : equipment.getLogUnits()) {
            new APICall<JBoolean>(main) {
                @Override
                public Call<JBoolean> apiFun() {
                    return main.getService().removeEntity(main.getDebugToken(), "ESS2LogUnit", connector.getOid());
                }
                @Override
                public void onSucess(JBoolean val) {
                }
            };
        }
        new APICall<JBoolean>(main) {
            @Override
            public Call<JBoolean> apiFun() {
                return main.getService().removeEntity(main.getDebugToken(), "ESS2Equipment", equipment.getOid());
                }
            @Override
            public void onSucess(JBoolean val) {
                if (refresh)
                    refreshWithDelay(4);
                }
            };
        }

    private void refreshWithDelay(int sec) {
        MainBaseFrame.delayInGUI(sec, new Runnable() {
            @Override
            public void run() {
                refreshArchitectures();
            }
            });
        }

    private void RemoveArchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveArchActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        new OK(200, 200, "Удалить архитектуру " + architecture.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                removeArchitectureData(architecture);
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().removeEntity(main.getDebugToken(), "ESS2Architecture", architecture.getOid());
                        }
                    @Override
                    public void onSucess(JBoolean val) {
                        refreshWithDelay(4);
                    }
                    };
                }
            });
        }//GEN-LAST:event_RemoveArchActionPerformed

    private void AddArchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddArchActionPerformed
        new OK(200, 200, "Добавить архитектуру", new I_Button() {
            @Override
            public void onPush() {
                final ESS2Architecture arch = new ESS2Architecture("...", "Новая", "");
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(arch, main.gson), 0);
                    }

                    @Override
                    public void onSucess(JLong val) {
                        refreshArchitectures();
                    }
                };
            }
        });
    }//GEN-LAST:event_AddArchActionPerformed

    private void RemoveViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveViewActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Views.getItemCount() == 0)
            return;
        final ESS2View view = architecture.getViews().get(Views.getSelectedIndex());
        new OK(200, 200, "Удалить ЧМИ " + view.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                removeViewData(view, true);
            }
        });

    }//GEN-LAST:event_RemoveViewActionPerformed

    private void AddViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddViewActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (MetaFile.getItemCount() == 0) {
            popup("Нет мета-файла оборудования");
            return;
        }
        final ESS2MetaFile metaFile = metaData.get(MetaFile.getSelectedIndex());
        if (!Values.isViewType(metaFile.getMetaType())) {
            popup("Недопустимый тип для мета-файла ЧМИ");
            return;
        }
        new OK(200, 200, "Добавить ЧМИ "+metaFile.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                final ESS2View view = new ESS2View(metaFile.getShortName(), "Новый "+metaFile.getTitle(), metaFile.getComment());
                view.getESS2Architecture().setOid(architecture.getOid());
                view.getMetaFile().setOid(metaFile.getOid());
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(view, main.gson), 0);
                    }

                    @Override
                    public void onSucess(JLong val) {
                        refreshArchitectures();
                    }
                };
            }
        });
    }//GEN-LAST:event_AddViewActionPerformed

    private void EditViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditViewActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Views.getItemCount() == 0)
            return;
        final ESS2View view = architecture.getViews().get(Views.getSelectedIndex());
        new WizardESS2View(this, view, new I_Wizard() {
            @Override
            public void onUpdate() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(view, main.gson));
                    }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshArchitectures();
                    }
                };
            }
            @Override
            public void onEnter(String value) {
                System.out.println(value);
            }
        });
    }//GEN-LAST:event_EditViewActionPerformed

    private void EditArchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditArchActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        new WizardESS2Architecture(this, architecture, new I_Wizard() {
            @Override
            public void onUpdate() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(architecture, main.gson));
                        }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshArchitectures();
                    }
                    };
                }
            @Override
            public void onEnter(String value) {
                System.out.println(value);
            }
        });
    }//GEN-LAST:event_EditArchActionPerformed

    private void EditEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditEquipActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Equipments.getItemCount() == 0)
            return;
        final ESS2Equipment equipment = architecture.getEquipments().get(Equipments.getSelectedIndex());
        new WizardESS2Equipment(this, equipment, new I_Wizard() {
            @Override
            public void onUpdate() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(equipment, main.gson));
                    }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshArchitectures();
                    }
                };
            }
            @Override
            public void onEnter(String value) {
                System.out.println(value);
            }
        });
    }//GEN-LAST:event_EditEquipActionPerformed

    private void EditDeviceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditDeviceActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        final ESS2Device connector = architecture.getDevices().get(Devices.getSelectedIndex());
        new WizardESS2Connector(this, connector, new I_Wizard() {
            @Override
            public void onUpdate() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(connector, main.gson));
                        }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshArchitectures();
                        }
                    };
                }
            @Override
            public void onEnter(String value) {
                System.out.println(value);
            }
        });
    }//GEN-LAST:event_EditDeviceActionPerformed

    private void ArchNodeRefrreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ArchNodeRefrreshActionPerformed
        refreshArchitectures();
        refreshNodes();
    }//GEN-LAST:event_ArchNodeRefrreshActionPerformed

    private void RemoveNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveNodeActionPerformed
        if (Nodes.getItemCount() == 1)
            return;
        int idx =  Nodes.getSelectedIndex();
        if (idx==0) return;
        new OK(200, 200, "Удалить узел СНЭЭ " + nodes.get(idx-1).getTitle(), new I_Button() {
            @Override
            public void onPush() {
                long oid = nodes.get(idx-1).getOid();
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().removeEntity(main.getDebugToken(), "ESS2Node", oid);
                       }
                    @Override
                    public void onSucess(JBoolean val) {
                        refreshNodes();
                        }
                    };
                }
            });
    }//GEN-LAST:event_RemoveNodeActionPerformed

    private void AddNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNodeActionPerformed
        new OK(200, 200, "Добавить узел СНЭЭ", new I_Button() {
            @Override
            public void onPush() {
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(new ESS2Node(), main.gson), 0);
                    }

                    @Override
                    public void onSucess(JLong val) {
                        refreshNodes();
                    }
                };
            }
        });
    }//GEN-LAST:event_AddNodeActionPerformed

    private void EditNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditNodeActionPerformed
        if (Nodes.getItemCount() == 1)
            return;
        int idx = Nodes.getSelectedIndex();
        if (idx==0) return;
        final ESS2Node node = nodes.get(idx-1);
        new WizardESS2Node(this, node, new I_Wizard() {
            @Override
            public void onUpdate() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(node, main.gson));
                        }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshNodes();
                        }
                    };
                }
            @Override
            public void onEnter(String value) {
                System.out.println(value);
            }
        });
    }//GEN-LAST:event_EditNodeActionPerformed

    private void ExportArchitectureFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportArchitectureFilesActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        ESS2Architecture vv = main2.loadFullArchitecture(architectures.get(Architectures.getSelectedIndex()).getOid());
        vv.testFullArchitecture();
        vv.clearCrossReferences();
        if (vv.getErrors().getErrCount() == 0) {
            Meta2XStream xStream = new Meta2XStream();
            String ss = xStream.toXML(vv);
            main.saveFile("Архитектура:"+vv.getTitle(),"xml",vv.getShortName(),ss);
            new APICall<ArrayList<DBRequest>>(main) {
                @Override
                public Call<ArrayList<DBRequest>> apiFun() {
                    return main.getService().getEntityList(main.getDebugToken(),"Artifact",Values.GetAllModeActual,0);
                    }
                @Override
                public void onSucess(ArrayList<DBRequest> list) {
                    ArrayList<Artifact> out =new ArrayList<>();
                    for (DBRequest request : list){
                        try {
                            out.add((Artifact) request.get(main.gson));
                            } catch (Exception ee){
                                System.out.println("Ошибка десериализации Artafct: "+ee.toString()+"\n"+request.getJsonObject());
                                return;
                                }
                        }
                    Meta2XStream xStream = new Meta2XStream();
                    String ss = xStream.toXML(out);
                    main.saveFile("Артефакты","xml","Artifacts",ss);
                    String ss2 = main.getWorkSettings();
                    if (ss2!=null){
                        System.out.println("Недоступны настройки сервера: "+ss);
                        return;
                        }
                    WorkSettings ws = (WorkSettings)main.workSettings();
                    ss = xStream.toXML(ws);
                    main.saveFile("Настройки сервера","xml","WorkSettings",ss);
                    }
                };
            }
        System.out.println("-------------------------------------\n" + vv.getErrors().toString());
    }//GEN-LAST:event_ExportArchitectureFilesActionPerformed

    private void clearDeployedMetaData(){
        ArchitectureLabel.setText("Архитектура не выбрана");
        Deploy.setIcon(new javax.swing.ImageIcon(getClass().getResource(archStateIcons[0])));
        Connect.setIcon(new javax.swing.ImageIcon(getClass().getResource(connStateIcons[0])));
        main2.deployed = null;
        main2.setCurrentView(null);
        main2.setCurrentView2(null);
        }

    private void refreshDeployedMetaData(){
        clearDeployedMetaData();
        int state = deployed.getArchitectureState();
        ArchitectureLabel.setText(deployed.getFullTitle());
        Deploy.setIcon(new javax.swing.ImageIcon(getClass().getResource(archStateIcons[state])));
        Connect.setIcon(new javax.swing.ImageIcon(getClass().getResource(connStateIcons[state])));
        if (!deployed.isDeployed())
            return;
        main2.deployed = deployed;
        setRenderingOff();
        }

    private void loadDeployedArchitecture(long oid, int state) {
        if (!main2.loadDeployedArchitecture(oid,state))
            return;
        deployed = main2.deployed;
        architecture = deployed;
        refreshDeployedMetaData();
        refreshSelectedArchitecture();
        deployingViewState();
        }
    private void DeployActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeployActionPerformed
        setRenderingOff();
        if (deployed.isDeployed()){
            if (deployed.getArchitectureState()==Values.ASConnected){
                popup("Недопустимо при включенном оборудовании");
                return;
                }
            new APICall<CallResult>(main){
                @Override
                public Call<CallResult> apiFun() {
                    String pass = Password.getText();
                    return main2.service2.metaDataCancel(main.getDebugToken(),pass);
                    }
                @Override
                public void onSucess(CallResult val) {
                    deployed.setArchitectureState(val.getState());
                    if (val.getState()==Values.ASNotDeployed)
                        clearDeployedMetaData();
                    architecture = Architectures.getItemCount()==0 ? null : architectures.get(Architectures.getSelectedIndex());
                    refreshSelectedArchitecture();
                    deployingViewState();
                    }
                };
            }
        else {
            if (Architectures.getItemCount() == 0)
                return;
            final long oid = architectures.get(Architectures.getSelectedIndex()).getOid();
            new APICall<CallResult>(main) {
                @Override
                public Call<CallResult> apiFun() {
                    return main2.service2.metaDataDeployForce(main.getDebugToken(), Password.getText(), oid,ForceDeploy.isSelected());
                    }
                @Override
                public void onSucess(CallResult val) {
                    System.out.println("Состояние: " +
                            Values.constMap().getGroupMapByValue("ArchState").get(val.getState()).title());
                    System.out.println(val);
                    int state = val.getState();
                    loadDeployedArchitecture(oid, state);
                    deployingViewState();
                    }
                };
            }
    }//GEN-LAST:event_DeployActionPerformed


    private void setSelectedProfile(boolean enabled){
        if (!enabled){
            ProfileName.setText("...");
            return;
            }
        int count=0;
        String ss="";
        for (int i=0; i<deployed.getProfilers().size();i++){
            ESS2ProfilerModule module = deployed.getProfilers().get(i);
            if (!module.isEnable())
                continue;
            count++;
            ProfileName.setText(module.getTitle());
            if (ss.length()!=0)
                ss +=",";
            ss+=""+i;
            }
        if (count!=1){
            ProfileName.setText("Профилей  "+count+": "+ss);
            }
        }

    private void ConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectActionPerformed
        setRenderingOff();
        if (deployed.isConnected()){
            new APICall<CallResult>(main){
                @Override
                public Call<CallResult> apiFun() {
                    return main2.service2.disconnectFromEquipment(main2.getDebugToken());
                    }
                @Override
                public void onSucess(CallResult oo) {
                    int state = oo.getState();
                    boolean connected = state == Values.ASConnected;
                    refreshIEC61850State();
                    refreshIEC60870State();
                    refreshProfilerState();
                    deployed.setArchitectureState(state);
                    System.out.println(oo);
                    Deploy.setIcon(new javax.swing.ImageIcon(getClass().getResource(archStateIcons[state])));
                    Connect.setIcon(new javax.swing.ImageIcon(getClass().getResource(connStateIcons[state])));
                    OnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                            connected ? "/drawable/connect-on.png" : "/drawable/connect-off.png")));
                    streamData.setVisible(connected);
                    }
                };
            return;
            }
        if (deployed.getArchitectureState()!=Values.ASDeployed){
            popup("Недопустимое состояние метаданных и оборудования для соединения");
            return;
            }
        new APICall<CallResult>(main){
            @Override
            public Call<CallResult> apiFun() {
                return main2.service2.connectToEquipmentForce(main2.getDebugToken(),ForceConnect.isSelected());
                }
            @Override
            public void onSucess(CallResult oo) {
                int state = oo.getState();
                boolean connected = state == Values.ASConnected;
                deployed.setArchitectureState(state);
                System.out.println(oo);
                Deploy.setIcon(new javax.swing.ImageIcon(getClass().getResource(archStateIcons[state])));
                Connect.setIcon(new javax.swing.ImageIcon(getClass().getResource(connStateIcons[state])));
                streamData.setVisible(connected);
                initStreamData();
                if (lastViewIndex!=-1 && lastViewIndex < Views.getItemCount())          // Восстановить последний ЧМИ
                    Views.select(lastViewIndex);
                }
            };
        }//GEN-LAST:event_ConnectActionPerformed

    private void RemoveEmulatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveEmulatorActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Emulators.getItemCount() == 0)
            return;
        final ESS2EquipEmulator emulator= architecture.getEmulators().get(Emulators.getSelectedIndex());
        new OK(200, 200, "Удалить эмулятор " + emulator.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().removeEntity(main.getDebugToken(), "ESS2EquipEmulator", emulator.getOid());
                        }
                    @Override
                    public void onSucess(JBoolean val) {
                        refreshWithDelay(4);
                        }
                    };
            }
        });
    }//GEN-LAST:event_RemoveEmulatorActionPerformed

    private void AddEmulatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddEmulatorActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (MetaFile.getItemCount() == 0) {
            popup("Нет мета-файла оборудования");
            return;
            }
        new OK(200, 200, "Добавить эмулятор", new I_Button() {
            @Override
            public void onPush() {
                final ESS2EquipEmulator emulator = new ESS2EquipEmulator("...", "Новый", "");
                emulator.getESS2Architecture().setOid(architecture.getOid());
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(emulator, main.gson), 0);
                        }
                    @Override
                    public void onSucess(JLong val) {
                        refreshArchitectures();
                    }
                };
            }
        });
    }//GEN-LAST:event_AddEmulatorActionPerformed

    private void EditEmulatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditEmulatorActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Emulators.getItemCount() == 0)
            return;
        final ESS2EquipEmulator emulator = architecture.getEmulators().get(Emulators.getSelectedIndex());
        new WizardESS2EquipEmulator(this, emulator, new I_Wizard() {
            @Override
            public void onUpdate() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(emulator, main.gson));
                        }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshArchitectures();
                        }
                    };
                }
            @Override
            public void onEnter(String value) {
                System.out.println(value);
            }
        });
    }//GEN-LAST:event_EditEmulatorActionPerformed

    private void ImportScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportScriptActionPerformed
        if (architecture==null){
            popup("Не выбрана архитектура");
            return;
            }
        FileNameExt fname = main.getInputFileName("Импорт скрипта", "*.txt", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(), "Script import", fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact oo) {
                final ESS2ScriptFile script = new ESS2ScriptFile();
                script.getFile().setOidRef(oo);
                script.setScriptType(Values.STUndefined);
                script.getESS2Architecture().setOid(architecture.getOid());
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(script,main.gson),0);
                        }
                    @Override
                    public void onSucess(JLong oo) {
                        System.out.print("Импортирован скрипт "+script.toString());
                        refreshArchitectures();
                    }
                };
            }
        };

    }//GEN-LAST:event_ImportScriptActionPerformed

    private void RemoveScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveScriptActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Scripts.getItemCount() == 0)
            return;
        final ESS2ScriptFile scriptFile= architecture.getScripts().get(Scripts.getSelectedIndex());
        new OK(200, 200, "Удалить скрипт " + scriptFile.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().deleteById(main.getDebugToken(), "ESS2ScriptFile", scriptFile.getOid());
                    }
                    @Override
                    public void onSucess(JBoolean val) {
                        refreshWithDelay(4);
                    }
                };
            }
        });
    }//GEN-LAST:event_RemoveScriptActionPerformed

    private void EditScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditScriptActionPerformed
        if (architecture==null || Scripts.getItemCount()==0)
            return;
        final ESS2ScriptFile scriptFile = architecture.getScripts().get(Scripts.getSelectedIndex());
        main.loadFileAsString(scriptFile.getFile().getRef(), new I_DownLoadString() {
            @Override
            public void onSuccess(String ss) {
                new WizardScript(main, scriptFile, ss, new I_Value<String>() {
                    @Override
                    public void onEnter(String text) {
                        if (text==null) {
                            new APICall<JEmpty>(main) {
                                @Override
                                public Call<JEmpty> apiFun() {
                                    return main.getService().updateEntity(main.getDebugToken(), new DBRequest(scriptFile, main.gson));
                                }

                                @Override
                                public void onSucess(JEmpty oo) {
                                    System.out.print("Обновлен скрипт " + scriptFile.toString());
                                    refreshArchitectures();
                                    }
                                };
                            }
                        else{    // Обновить файл скрипта
                            main.updateArtifactFromString(scriptFile.getFile().getRef(), text, new I_OK() {
                                @Override
                                public void onOK(Entity ent) {
                                    scriptFile.getFile().setRef((Artifact) ent);
                                    }
                                });
                            }

                    }
                });
            }
            @Override
            public void onError(String mes) {
                System.out.println(mes);
                }
        });
    }//GEN-LAST:event_EditScriptActionPerformed

    private void NodesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NodesItemStateChanged
        int idx = Nodes.getSelectedIndex();
        if (idx==0)
            return;
        ESS2Node node = nodes.get(idx-1);
        long oid =  node.getArchitecture().getOid();
        if (oid==0)
            return;
        int i=0;
        for(ESS2Architecture arch : architectures){
            if (arch.getOid()==oid){
                Architectures.select(i);
                architecture = arch;
                refreshSelectedArchitecture();
                return;
                }
            i++;
            }
        popup("Не найдена архитектура для "+node.getTitle());
    }//GEN-LAST:event_NodesItemStateChanged

    private void ArchitecturesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ArchitecturesItemStateChanged
        architecture = Architectures.getItemCount()==0 ? null : architectures.get(Architectures.getSelectedIndex());
        refreshSelectedArchitecture();
    }//GEN-LAST:event_ArchitecturesItemStateChanged

    private void ExecScriptServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExecScriptServerActionPerformed
        if (Scripts.getItemCount()==0)
            return;
        if (!deployed.isConnected()){
            popup("Нет соединения с оборудованием");
            return;
            }
        final ESS2ScriptFile scriptFile = deployed.getScripts().get(Scripts.getSelectedIndex());
        new APICall<JString>(main){
            @Override
            public Call<JString> apiFun() {
                return main2.service2.execScript(main.getDebugToken(),scriptFile.getOid(),Trace.isSelected());
                }
            @Override
            public void onSucess(JString oo) {
                //popup("Скрипт "+scriptFile.getFullTitle()+" выполнен (лог)");
                System.out.println(scriptFile.getFullTitle()+"\n"+oo.getValue());
                main.sendEventPanel(EventLogToFront,0,0,"Скрипт "+scriptFile.getFullTitle()+" выполнен (лог)");
                }
            };
    }//GEN-LAST:event_ExecScriptServerActionPerformed

    private void ImportMetaEquipment2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportMetaEquipment2ActionPerformed
        FileNameExt fname = main.getInputFileName("Импорт Excel-файла метаданных оборудования, формат СНЭЭ-2", "*.xls", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(), "Meta-Data import", fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact oo) {
                new APICall<ArrayList<OidString>>(main) {
                    @Override
                    public Call<ArrayList<OidString>> apiFun() {
                        return main2.service2.importMetaData2(main.getDebugToken(), Password.getText(), oo.getOid());
                        }
                    @Override
                    public void onSucess(ArrayList<OidString> oo) {
                        for(OidString ss : oo){
                            System.out.print(ss);
                            if (!ss.valid())
                                main.sendEventPanel(EventLogToFront,0,0,"Импорт метаданных оборудования не выполнен");
                            else
                                popup("Импорт метаданных оборудования выполнен");
                            }
                        refreshMetaData();
                    }
                };
            }
        };
    }//GEN-LAST:event_ImportMetaEquipment2ActionPerformed

    private void ExecScriptClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExecScriptClientActionPerformed
        if (Scripts.getItemCount()==0)
            return;
        if (!deployed.isConnected()){
            popup("Скрипт не должен работать с оборудованием");
            }
        int idx= Scripts.getSelectedIndex();
        final ESS2ScriptFile scriptFile = architecture.getScripts().get(idx);
        main.loadFileAsString(scriptFile.getFile().getRef(), new I_DownLoadString() {
            @Override
            public void onSuccess(String ss) {
                executeScriptLocal(scriptFile,ss);
                }
            @Override
            public void onError(String mes) {
                System.out.println(mes);
                }
            });
    }//GEN-LAST:event_ExecScriptClientActionPerformed

    private void RuntimeEditItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_RuntimeEditItemStateChanged
        main.sendEventPanel(BasePanel.EventRuntimeEditMode,RuntimeEdit.isSelected() ? 1 : 0 ,0,"");
    }//GEN-LAST:event_RuntimeEditItemStateChanged

    private void saveRunTimeChanges(ESS2View view){
        if (view==null)
            return;
        ESS2MetaFile metaFile2 = view.getMetaFile().getRef();
        String dataString = new Meta2XStream().toXML(view.getView());
        main.updateArtifactFromString(metaFile2.getFile().getRef(), dataString, new I_OK() {
            @Override
            public void onOK(Entity ent) {
                metaFile2.getFile().setRef((Artifact) ent);
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(metaFile2, main.gson));
                    }
                    @Override
                    public void onSucess(JEmpty vv) {
                        // refreshMetaData();
                        }
                    };
                }
            });
        }
    private void RunTimeSaveChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunTimeSaveChangesActionPerformed
        saveRunTimeChanges(main2.getCurrentView());
        saveRunTimeChanges(main2.getCurrentView2());
        clearRunTimeChanges();
    }//GEN-LAST:event_RunTimeSaveChangesActionPerformed

    private void MetaFileItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MetaFileItemStateChanged
        setMetaTypeSelector(metaData.get(MetaFile.getSelectedIndex()).getMetaType());
    }//GEN-LAST:event_MetaFileItemStateChanged

    private void ImportXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportXMLActionPerformed
        FileNameExt fname = main.getInputFileName("Импорт xml-файла метаданных", "*.xml", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(), "Meta-Data load", fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact oo) {
                new APICall<OidString>(main) {
                    @Override
                    public Call<OidString> apiFun() {
                        return main2.service2.loadMetaData(main.getDebugToken(), Password.getText(), oo.getOid());
                       }
                    @Override
                    public void onSucess(OidString oo) {
                        System.out.print(oo);
                        if (oo.valid())
                            popup("Загрузка метаданных выполнена");
                        else
                            main.sendEventPanel(EventLogToFront,0,0,"Ошибки загрузки");
                        //popup("Импорт метаданных оборудования" + (!oo.o1.valid() ? " не" : "") + " выполнен");
                        //popup("Импорт метаданных ЧМИ" + (!oo.o2.valid() ? " не" : "") + " выполнен");
                        refreshMetaData();
                    }
                };
            }
        };
    }//GEN-LAST:event_ImportXMLActionPerformed

    private void ExportXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportXMLActionPerformed
        if (MetaFile.getItemCount() == 0)
            return;
        metaFile = metaData.get(MetaFile.getSelectedIndex());
        main.loadFile(metaFile.getFile().getRef());
    }//GEN-LAST:event_ExportXMLActionPerformed

    private void ExportXMLEquipmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportXMLEquipmentActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Equipments.getItemCount() == 0)
            return;
        final ESS2Equipment equipment = architecture.getEquipments().get(Equipments.getSelectedIndex());
        main.loadFile(equipment.getMetaFile().getRef().getFile().getRef());
    }//GEN-LAST:event_ExportXMLEquipmentActionPerformed

    private void ExportXMLViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportXMLViewActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Views.getItemCount() == 0)
            return;
        final ESS2View view = architecture.getViews().get(Views.getSelectedIndex());
        main.loadFile(view.getMetaFile().getRef().getFile().getRef());
    }//GEN-LAST:event_ExportXMLViewActionPerformed

    private void EquipmentsItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EquipmentsItemStateChanged
        refreshLogUnits();
    }//GEN-LAST:event_EquipmentsItemStateChanged

    private void HEXRegItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HEXRegItemStateChanged
        setRW(HEXReg,RegNum,regRWRegNum);
    }//GEN-LAST:event_HEXRegItemStateChanged

    private void RemoveLogUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveLogUnitActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Equipments.getItemCount() == 0)
            return;
        final ESS2Equipment equipment = architecture.getEquipments().get(Equipments.getSelectedIndex());
        final ESS2LogUnit connector = equipment.getLogUnits().get(LogUnits.getSelectedIndex());
        new OK(200, 200, "Удалить лог. устройство (Unit) " + equipment.getTitle() + "-" + connector.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().removeEntity(main.getDebugToken(), "ESS2LogUnit", connector.getOid());
                    }
                    @Override
                    public void onSucess(JBoolean val) {
                        refreshWithDelay(4);
                    }
                };
            }
        });
    }//GEN-LAST:event_RemoveLogUnitActionPerformed

    private void AddLogUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddLogUnitActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Equipments.getItemCount() == 0)
            return;
        final ESS2Equipment equipment = architecture.getEquipments().get(Equipments.getSelectedIndex());
        new OK(200, 200, "Добавить лог. устройство (Unit)", new I_Button() {
            @Override
            public void onPush() {
                final ESS2LogUnit logUnit = new ESS2LogUnit("...", "Новый", "");
                logUnit.getESS2Equipment().setOid(equipment.getOid());
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(logUnit, main.gson), 0);
                        }
                    @Override
                    public void onSucess(JLong val) {
                        refreshArchitectures();
                    }
                };
            }
        });
    }//GEN-LAST:event_AddLogUnitActionPerformed

    private void EditLogUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditLogUnitActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Equipments.getItemCount() == 0)
            return;
        final ESS2Equipment equipment = architecture.getEquipments().get(Equipments.getSelectedIndex());
        if (Devices.getItemCount() == 0)
            return;
        final ESS2LogUnit connector = equipment.getLogUnits().get(LogUnits.getSelectedIndex());
        new WizardESS2LogUnit(this, connector,architecture.getDevices(), new I_Wizard() {
            @Override
            public void onUpdate() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(connector, main.gson));
                        }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshArchitectures();
                        }
                    };
                }
            @Override
            public void onEnter(String value) {
                System.out.println(value);
            }
        });
    }//GEN-LAST:event_EditLogUnitActionPerformed

    private void HEXValueItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_HEXValueItemStateChanged
        setRW(HEXValue,RegValue,regRWValue);
    }//GEN-LAST:event_HEXValueItemStateChanged

    private void RemoveEnvValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveEnvValueActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Equipments.getItemCount() == 0)
            return;
        final ESS2EnvValue envValue = architecture.getEnvValues().get(EnvValue.getSelectedIndex());
        new OK(200, 200, "Удалить переменную окружения " + envValue.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().deleteById(main.getDebugToken(), "ESS2EnvValue", envValue.getOid());
                        }
                    @Override
                    public void onSucess(JBoolean val) {
                        refreshWithDelay(4);
                        }
                    };
                }
        });
    }//GEN-LAST:event_RemoveEnvValueActionPerformed

    private void AddEnvValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddEnvValueActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        new OK(200, 200, "Добавить переменную окружения", new I_Button() {
            @Override
            public void onPush() {
                final ESS2EnvValue envValue = new ESS2EnvValue();
                envValue.getESS2Architecture().setOid(architecture.getOid());
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(envValue, main.gson), 0);
                        }
                    @Override
                    public void onSucess(JLong val) {
                        refreshArchitectures();
                        }
                };
            }
        });

    }//GEN-LAST:event_AddEnvValueActionPerformed

    private void EditEnvValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditEnvValueActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        final ESS2EnvValue envValue = architecture.getEnvValues().get(EnvValue.getSelectedIndex());
        new WizardESS2EnvValue(this, envValue, new I_Wizard() {
            @Override
            public void onUpdate() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(envValue, main.gson));
                        }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshArchitectures();
                        }
                    };
                }
            @Override
            public void onEnter(String value) {
                System.out.println(value);
            }
        });
    }//GEN-LAST:event_EditEnvValueActionPerformed

    private void ExportXMLAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportXMLAllActionPerformed
        new OK(200, 200, "Экспорт XML-файлов", new I_Button() {
            @Override
            public void onPush() {
                FileNameExt ff = main.getOutputFileName("Каталог экспорта метаданных","aaa","aaa");
                String loadDirectory = ff.getPath();
                ArrayList<Artifact> artifacts = new ArrayList<>();
                for(ESS2MetaFile metaFile : metaData)
                    artifacts.add(metaFile.getFile().getRef());
                main.loadFileGroup(loadDirectory,artifacts,0);
                }
            });
    }//GEN-LAST:event_ExportXMLAllActionPerformed

    private void ExportScriptsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportScriptsActionPerformed
        new OK(200, 200, "Экспорт скриптов", new I_Button() {
            @Override
            public void onPush() {
                if (architecture == null)
                    return;
                FileNameExt ff = main.getOutputFileName("Каталог экспорта скриптов","aaa","aaa");
                String loadDirectory = ff.getPath();
                ArrayList<Artifact> artifacts = new ArrayList<>();
                ArrayList<String> outTitles = new ArrayList<>();
                for(ESS2ScriptFile metaFile : architecture.getScripts()){
                    artifacts.add(metaFile.getFile().getRef());
                    outTitles.add(metaFile.getTitle());
                    }
                main.loadFileGroup(loadDirectory,outTitles,artifacts,0);
                }
            });
    }//GEN-LAST:event_ExportScriptsActionPerformed

    private void OnOffNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnOffNodeActionPerformed
        if (main2.getCurrentView()!=null) {
            final ESS2View view = main2.getCurrentView();
            main2.setCurrentView(null);
            OnOffNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png")));
            main.sendEvent(EventPLMOff,0);
            setFullScreenOff();
            return;
            }
        int idx = Nodes.getSelectedIndex();
        if (idx==0){
            popup("Не выбран узел СНЭЭ");
            return;
            }
        ESS2Node node = nodes.get(idx-1);
        main2.mainServerNodeId = node.getOid();
        ESS2Architecture arch = architectures.getById(node.getArchitecture().getOid());
        if (arch == null) {
            System.out.println("Не найдена архитектура для "+node.getTitle());
            return;
            }
        arch = main2.loadFullArchitecture(architectures.get(Architectures.getSelectedIndex()).getOid());
        arch.testFullArchitecture();
        deployed = arch;
        main2.deployed = arch;
        for(ESS2View view : deployed.getViews()){
            if (view.getMetaFile().getRef().getMetaType()== MTViewMainServer){
                main2.setRenderingOn(node.getOid(),view,Trace.isSelected(),ForceRender.isSelected(),false,new ScreenMode());
                OnOffNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-on.png")));
                fullScreenOn();
                return;
                }
            }
        System.out.println("Не найден ЧМИ для ДЦ");

    }//GEN-LAST:event_OnOffNodeActionPerformed

    private void CIDLocalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CIDLocalActionPerformed
        if (!main2.deployed.isDeployed()){
            popup("Архитектура не развернута");
            return;
            }
        ESS2Architecture arch = main2.deployed;
        FileNameExt ff = main.getOutputFileName("МЭК 61850","",arch.getTitle()+".cid");
        for(ESS2Equipment equipment : arch.getEquipments()){
            equipment.createCIDRecord(arch.getShortName());
            CIDCreateData data = equipment.getCidData();
            ff.setName(arch.getTitle()+"_"+equipment.getTitle()+".cid");
            try {
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(ff.fullName()), "UTF-8");
                writer.write(data.toString());
                writer.close();
                System.out.println("Файл записан "+ff.fullName());
                if (!data.errors.valid())
                    System.out.println(data.errors.toString());
                } catch (Exception ee){
                    System.out.println("Ошибка записи "+ff.fileName());
                    }
                }
    }//GEN-LAST:event_CIDLocalActionPerformed

    private void IEC61850OnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IEC61850OnOffActionPerformed
        if (!main2.deployed.isConnected()){
            System.out.println("Допустимо только при подключенном оборудовании");
            return;
            }
        new APICall<CallResult>(main) {
            @Override
            public Call<CallResult> apiFun() {
                return main2.service2.iec61850ServerOnOff(main.getDebugToken(), deployed.getEquipments().get(Equipments.getSelectedIndex()).getOid());
                }
            @Override
            public void onSucess(CallResult vv) {
                System.out.println(vv.toString());
                viewServiceState(IEC61850OnOff,vv.getState());
                }
            };
        }//GEN-LAST:event_IEC61850OnOffActionPerformed

    private void IEC61850ClientGUIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IEC61850ClientGUIActionPerformed
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClientGui gui = new ClientGui();
                gui.setVisible(true);
                gui.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                }
            });
    }//GEN-LAST:event_IEC61850ClientGUIActionPerformed

    private void ProfilerOnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfilerOnOffActionPerformed
        if (main2.deployed==null || !main2.deployed.isConnected()){
            System.out.println("Допустимо только при подключенном оборудовании");
            return;
            }
        new APICall<CallResult>(main) {
            @Override
            public Call<CallResult> apiFun() {
                return main2.service2.profilerOnOff(main.getDebugToken());
            }
            @Override
            public void onSucess(CallResult vv) {
                System.out.println(vv.toString());
                viewServiceState(ProfilerOnOff,vv.getState());
                if (vv.getState()==ServiceStateOn)
                    profilerTimer.start(profilerTimerDelay,limitedProfile);
                }
        };
    }//GEN-LAST:event_ProfilerOnOffActionPerformed

    private void RemoveProfilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveProfilerActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Profilers.getItemCount() == 0)
            return;
        final ESS2ProfilerModule module= architecture.getProfilers().get(Profilers.getSelectedIndex());
        new OK(200, 200, "Удалить модуль профилирования " + module.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().removeEntity(main.getDebugToken(), "ESS2ProfilerModule", module.getOid());
                        }
                    @Override
                    public void onSucess(JBoolean val) {
                        refreshWithDelay(4);
                        }
                    };
            }
        });
    }//GEN-LAST:event_RemoveProfilerActionPerformed

    private void AddProfilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddProfilerActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (MetaFile.getItemCount() == 0) {
            popup("Нет мета-файла оборудования");
            return;
            }
        new OK(200, 200, "Добавить модуль профилирования", new I_Button() {
            @Override
            public void onPush() {
                final ESS2ProfilerModule module = new ESS2ProfilerModule("...", "Новый", "");
                module.getESS2Architecture().setOid(architecture.getOid());
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(module, main.gson), 0);
                        }
                    @Override
                    public void onSucess(JLong val) {
                        refreshArchitectures();
                        }
                    };
                }
            });
    }//GEN-LAST:event_AddProfilerActionPerformed

    private void EditProfilerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditProfilerActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Profilers.getItemCount() == 0)
            return;
        final ESS2ProfilerModule module = architecture.getProfilers().get(Profilers.getSelectedIndex());
        new WizardESS2ЗProfilerModule(this, module, new I_Wizard() {
            @Override
            public void onUpdate() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(module, main.gson));
                        }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshArchitectures();
                        }
                    };
                }
            @Override
            public void onEnter(String value) {
                System.out.println(value);
            }
        });
    }//GEN-LAST:event_EditProfilerActionPerformed

    private void ProfilerResultsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfilerResultsActionPerformed
        ProfilerResults.setVisible(false);
        new APICall<CallResult2>(main) {
            @Override
            public Call<CallResult2> apiFun() {
                return main2.service2.profilerExport(main.getDebugToken());
                }
            @Override
            public void onSucess(CallResult2 vv) {
                if (vv.valid())
                    main.loadFileAndDelete(vv.getArt());
                System.out.println(vv.getErrors());
                }
            };
    }//GEN-LAST:event_ProfilerResultsActionPerformed

    private void OnlyViewItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_OnlyViewItemStateChanged
        main.sendEventPanel(BasePanel.EventRuntimeOnlyView,OnlyView.isSelected() ? 1 : 0 ,0,"");
    }//GEN-LAST:event_OnlyViewItemStateChanged

    private void IEC60870OnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IEC60870OnOffActionPerformed
        if (!main2.deployed.isConnected()){
            System.out.println("Допустимо только при подключенном оборудовании");
            return;
        }
        new APICall<CallResult>(main) {
            @Override
            public Call<CallResult> apiFun() {
                return main2.service2.iec60870ServerOnOff(main.getDebugToken());
                }
            @Override
            public void onSucess(CallResult vv) {
                System.out.println(vv.toString());
                viewServiceState(IEC60870OnOff,vv.getState());
               }
            };
    }//GEN-LAST:event_IEC60870OnOffActionPerformed

    private void OnOff2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnOff2ActionPerformed
        if (main2.getCurrentView2()!=null){
            setRenderingOff(true);
            }
        else{
            if (!deployed.isConnected())
                return;
            setRenderingOn(true);
            }

    }//GEN-LAST:event_OnOff2ActionPerformed

    private void OrigHWActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OrigHWActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_OrigHWActionPerformed

    private void AddGateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddGateActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        new OK(200, 200, "Добавить шлюз Modbus", new I_Button() {
            @Override
            public void onPush() {
                final ESS2ModBusGate gate = new ESS2ModBusGate("...", "Новый", "");
                gate.getESS2Architecture().setOid(architecture.getOid());
                gate.getDevice().setOid(0);
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.getService().addEntity(main.getDebugToken(), new DBRequest(gate, main.gson), 0);
                        }
                    @Override
                    public void onSucess(JLong val) {
                        refreshArchitectures();
                    }
                };
            }
        });
    }//GEN-LAST:event_AddGateActionPerformed

    private void RemoveGateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveGateActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Emulators.getItemCount() == 0)
            return;
        final ESS2ModBusGate gate= architecture.getGates().get(Gates.getSelectedIndex());
        new OK(200, 200, "Удалить эмулятор " + gate.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.getService().removeEntity(main.getDebugToken(), "ESS2ModBusGate", gate.getOid());
                    }
                    @Override
                    public void onSucess(JBoolean val) {
                        refreshWithDelay(4);
                    }
                };
            }
        });
    }//GEN-LAST:event_RemoveGateActionPerformed

    private void EditGateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditGateActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        final ESS2Architecture architecture = architectures.get(Architectures.getSelectedIndex());
        if (Gates.getItemCount() == 0)
            return;
        final ESS2ModBusGate gate = architecture.getGates().get(Gates.getSelectedIndex());
        new WizardESS2ModBusGate(this, gate, architecture.getDevices(), new I_Wizard() {
            @Override
            public void onUpdate() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntity(main.getDebugToken(), new DBRequest(gate, main.gson));
                        }
                    @Override
                    public void onSucess(JEmpty vv) {
                        refreshGates();
                        }
                    };
            }
            @Override
            public void onEnter(String value) {
                System.out.println(value);
            }
        });
    }//GEN-LAST:event_EditGateActionPerformed

    private void ExportNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportNodeActionPerformed
        if (architecture==null){
            popup("Не выбрана архитектура для экспорта");
            }
        new OK(200, 200, "Экспорт архитектуры в сервер: "+architecture.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                exportNodeLogin();
            }
        });
    }//GEN-LAST:event_ExportNodeActionPerformed
    private void exportExit(ClientContext exportContext, String text){
        System.out.println(text);
        Pair<String, JEmpty> res = new APICallSync<JEmpty>(){
            @Override
            public Call<JEmpty> apiFun() {
                return exportContext.getService().logoff(exportContext.getDebugToken());
            }}.call();
        }
    private void exportNode(ClientContext exportContext){
        Pair<String,JString> res1 = new APICallSync<JString>(){
            @Override
            public Call<JString> apiFun() {
                return exportContext.getService().clearDB(exportContext.getDebugToken(),Values.DebugTokenPass);
            }
        }.call();
        if (res1.o1!=null){
            exportExit(exportContext,res1.o1);
            return;
            }
        System.out.println("Очистка БД: "+res1.o2);
        }
    private void exportNodeLogin(){
        final ClientContext exportContext = new ClientContext();
        Login loginForm = new Login(exportContext, new I_LoginBack() {
            @Override
            public void onPush() {
                }
            @Override
            public void onLoginSuccess() {
                exportNode(exportContext);
                }
            @Override
            public void sendPopupMessage(JFrame parent, Container button, String text) {
                System.out.println("Экспорт конфигурации: "+text);
                }
            });
        }
    private void refreshIEC61850State(){
        new APICall<JInt>(main) {
            @Override
            public Call<JInt> apiFun() {
                return main2.service2.iec61850ServerState(main.getDebugToken());
                }
            @Override
            public void onSucess(JInt vv) {
                viewServiceState(IEC61850OnOff,vv.getValue());
                }
            };
        }
    private void refreshIEC60870State(){
        new APICall<JInt>(main) {
            @Override
            public Call<JInt> apiFun() {
                return main2.service2.iec60870ServerState(main.getDebugToken());
            }
            @Override
            public void onSucess(JInt vv) {
                viewServiceState(IEC60870OnOff,vv.getValue());
            }
            };
        }
    private I_EmptyEvent limitedProfile = new I_EmptyEvent() {
        @Override
        public void onEvent() {
            refreshProfilerState();
            }
        };
    private void refreshProfilerState(){
        new APICall<JInt>(main) {
            @Override
            public Call<JInt> apiFun() {
                return main2.service2.profilerState(main.getDebugToken());
                }
            @Override
            public void onSucess(JInt vv) {
                viewServiceState(ProfilerOnOff,vv.getValue());
                if (vv.getValue()== ServiceStateOn){
                    oldProfilerState = true;
                    ProfilerResults.setVisible(false);
                    profilerTimer.start(profilerTimerDelay, limitedProfile);
                    }
                else{
                    if (oldProfilerState)
                        ProfilerResults.setVisible(true);
                    profilerTimer.cancel();
                    }
                }
            };
        }
    private void viewServiceState(JButton button,int state){
        button.setIcon(new javax.swing.ImageIcon(getClass().getResource( serviceStateIcons[state])));
        }
    @Override
    public void refresh() {
        refreshMetaData();
        refreshArchitectures();
        refreshNodes();
        initStreamData();
        configSelector.init(main2);
        /*
        try {
            JBoolean bb = new APICall2<JBoolean>() {
                @Override
                public Call<JBoolean> apiFun() {
                    return main2.service2.isPLMReady(main.getDebugToken());
                }
            }.call(main);
            if (bb.value())
                connect();
            createSystemList();
            setSystemSelector();
            configSelector.init(main2);
        } catch (UniException ee){ popup(ee.toString()); }
         */
    }

    @Override
    public void eventPanel(int code, int par1, long par2, String par3,Object oo) {
        if (code==EventRefreshSettings){
            refresh();
            main.sendEventPanel(EventRefreshSettingsDone,0,0,"",oo);
            }
        if (screen!=null)
            screen.eventPanel(code,par1,par2,par3,oo);
        if (code==EventRuntimeEdited){
            System.out.println(par3);
            runTimeChangesCount++;
            RunTimeChanges.setVisible(true);
            RunTimeChanges.setText(""+runTimeChangesCount);
            RunTimeChangesLabel.setVisible(true);
            RunTimeSaveChanges.setVisible(true);
            }
        if (code==EventPLMOffForce)
            setRenderingOff();
        }

    @Override
    public void shutDown() {
        profilerTimer.cancel();
        if (screen!=null){
            screen.shutDown();
            screen=null;
            }
        //trendView.close();
        }
    public void executeScriptLocal(ESS2ScriptFile scriptFile,String src){
        Syntax SS = main2.compileScriptLocal(scriptFile,src,Trace.isSelected());
        boolean res = SS.getErrorList().size()!=0;
        if (SS.getErrorList().size()==0){
            CallContext context = new CallContext(SS,deployed);
            try {
                context.setScriptName(scriptFile.getTitle());
                context.call(Trace.isSelected());
                } catch (ScriptException ee){
                    res = true;
                    System.out.println(ee.toString());
                }
            System.out.println(context.getTraceList());
            }
        main.sendEventPanel(EventLogToFront,0,0,"Скрипт "+scriptFile.getFullTitle()+
            (res ? " не" : "")+" выполнен (лог)");
        }

    private void deployingViewState(){
        boolean enabled = !deployed.isDeployed();
        Nodes.setEnabled(enabled);
        Architectures.setEnabled(enabled);
        AddNode.setEnabled(enabled);
        AddArch.setEnabled(enabled);
        AddEquip.setEnabled(enabled);
        AddDevice.setEnabled(enabled);
        AddEmulator.setEnabled(enabled);
        AddView.setEnabled(enabled);
        AddMeta.setEnabled(enabled);
        AddLogUnit.setEnabled(enabled);
        AddEnvValue.setEnabled(enabled);
        AddProfiler.setEnabled(enabled);
        RemoveNode.setEnabled(enabled);
        RemoveArch.setEnabled(enabled);
        RemoveEquip.setEnabled(enabled);
        RemoveDevice.setEnabled(enabled);
        RemoveEmulator.setEnabled(enabled);
        RemoveView.setEnabled(enabled);
        RemoveMeta.setEnabled(enabled);
        RemoveLogUnit.setEnabled(enabled);
        RemoveScript.setEnabled(enabled);
        RemoveEnvValue.setEnabled(enabled);
        RemoveProfiler.setEnabled(enabled);
        EditNode.setEnabled(enabled);
        EditArch.setEnabled(enabled);
        EditEquip.setEnabled(enabled);
        EditDevice.setEnabled(enabled);
        EditEmulator.setEnabled(enabled);
        EditView.setEnabled(enabled);
        EditLogUnit.setEnabled(enabled);
        EditScript.setEnabled(enabled);
        EditEnvValue.setEnabled(enabled);
        EditProfiler.setEnabled(enabled);
        ImportMetaData.setEnabled(enabled);
        ImportMetaEquipment2.setEnabled(enabled);
        ExportArchitectureFiles.setEnabled(enabled);
        ArchNodeRefrresh.setEnabled(enabled);
        RefreshMeta.setEnabled(enabled);
        AddGate.setEnabled(enabled);
        RemoveGate.setEnabled(enabled);
        EditGate.setEnabled(enabled);
        ExecScriptServer.setEnabled(!enabled);
        //-----------------------------------------------
        CIDLocal.setEnabled(!enabled);
        IEC61850OnOff.setEnabled(!enabled);
        IEC61850ClientGUI.setEnabled(!enabled);
        setSelectedProfile(!enabled);
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddArch;
    private javax.swing.JButton AddDevice;
    private javax.swing.JButton AddEmulator;
    private javax.swing.JButton AddEnvValue;
    private javax.swing.JButton AddEquip;
    private javax.swing.JButton AddGate;
    private javax.swing.JButton AddLogUnit;
    private javax.swing.JButton AddMeta;
    private javax.swing.JButton AddNode;
    private javax.swing.JButton AddProfiler;
    private javax.swing.JButton AddView;
    private javax.swing.JButton ArchNodeRefrresh;
    private javax.swing.JLabel ArchitectureLabel;
    private java.awt.Choice Architectures;
    private javax.swing.JButton CIDLocal;
    private javax.swing.JCheckBox CheckLimits;
    private javax.swing.JButton Connect;
    private javax.swing.JTextField DefValue;
    private javax.swing.JTextField DefValueFormula;
    private javax.swing.JButton Deploy;
    private javax.swing.JButton DeviceRead;
    private javax.swing.JButton DeviceWrite;
    private java.awt.Choice Devices;
    private java.awt.Choice DevicesRW;
    private javax.swing.JButton EditArch;
    private javax.swing.JButton EditDevice;
    private javax.swing.JButton EditEmulator;
    private javax.swing.JButton EditEnvValue;
    private javax.swing.JButton EditEquip;
    private javax.swing.JButton EditGate;
    private javax.swing.JButton EditLogUnit;
    private javax.swing.JButton EditMeta;
    private javax.swing.JButton EditNode;
    private javax.swing.JButton EditProfiler;
    private javax.swing.JButton EditScript;
    private javax.swing.JButton EditView;
    private java.awt.Choice Emulators;
    private java.awt.Choice EnvValue;
    private java.awt.Choice Equipments;
    private javax.swing.JButton ExecScriptClient;
    private javax.swing.JButton ExecScriptServer;
    private javax.swing.JButton ExportArchitectureFiles;
    private javax.swing.JButton ExportNode;
    private javax.swing.JButton ExportScripts;
    private javax.swing.JButton ExportXML;
    private javax.swing.JButton ExportXMLAll;
    private javax.swing.JButton ExportXMLEquipment;
    private javax.swing.JButton ExportXMLView;
    private javax.swing.JCheckBox ForceConnect;
    private javax.swing.JCheckBox ForceDeploy;
    private javax.swing.JCheckBox ForceRender;
    private javax.swing.JCheckBox FullScreen;
    private java.awt.Choice Gates;
    private javax.swing.JCheckBox HEXReg;
    private javax.swing.JCheckBox HEXValue;
    private javax.swing.JButton IEC60870OnOff;
    private javax.swing.JButton IEC61850ClientGUI;
    private javax.swing.JButton IEC61850OnOff;
    private javax.swing.JButton ImportMetaData;
    private javax.swing.JButton ImportMetaEquipment2;
    private javax.swing.JButton ImportScript;
    private javax.swing.JButton ImportXML;
    private java.awt.Choice LogUnits;
    private javax.swing.JTextField MaxValue;
    private javax.swing.JTextField MaxValueFormula;
    private javax.swing.JTextField MetaDataChanges;
    private javax.swing.JLabel MetaDataChangesLabel;
    private javax.swing.JButton MetaDataSaveChanges;
    private java.awt.Choice MetaFile;
    private java.awt.Choice MetaTypes;
    private javax.swing.JTextField MinValue;
    private javax.swing.JTextField MinValueFormula;
    private java.awt.Choice Nodes;
    private javax.swing.JButton OnOff;
    private javax.swing.JButton OnOff2;
    private javax.swing.JButton OnOffNode;
    private javax.swing.JCheckBox OnlyView;
    private javax.swing.JCheckBox OrigHW;
    private javax.swing.JPasswordField Password;
    private javax.swing.JLabel ProfileName;
    private javax.swing.JButton ProfilerOnOff;
    private javax.swing.JButton ProfilerResults;
    private java.awt.Choice Profilers;
    private javax.swing.JButton RefreshMeta;
    private javax.swing.JTextField RegNum;
    private javax.swing.JTextField RegValue;
    private javax.swing.JButton RemoveArch;
    private javax.swing.JButton RemoveDevice;
    private javax.swing.JButton RemoveEmulator;
    private javax.swing.JButton RemoveEnvValue;
    private javax.swing.JButton RemoveEquip;
    private javax.swing.JButton RemoveGate;
    private javax.swing.JButton RemoveLogUnit;
    private javax.swing.JButton RemoveMeta;
    private javax.swing.JButton RemoveNode;
    private javax.swing.JButton RemoveProfiler;
    private javax.swing.JButton RemoveScript;
    private javax.swing.JButton RemoveView;
    private javax.swing.JTextField RunTimeChanges;
    private javax.swing.JLabel RunTimeChangesLabel;
    private javax.swing.JButton RunTimeSaveChanges;
    private javax.swing.JCheckBox RuntimeEdit;
    private java.awt.Choice Scripts;
    private javax.swing.JButton SettingEdit;
    private javax.swing.JTextField SettingRegNum;
    private javax.swing.JTextField SettingShortName;
    private javax.swing.JTextField SettingUnit;
    private javax.swing.JTextField SettingValue;
    private java.awt.Choice SettingsList;
    private javax.swing.JLabel SettingsName;
    private javax.swing.JCheckBox Trace;
    private javax.swing.JTextField UnitRW;
    private java.awt.Choice Views;
    private javax.swing.JCheckBox WriteFloat;
    private javax.swing.JCheckBox WriteInt32;
    private javax.swing.JButton WriteSetting;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    // End of variables declaration//GEN-END:variables
}
