/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import lombok.Getter;
import retrofit2.Response;
import romanow.abc.core.API.RestAPICommon;
import romanow.abc.core.DBRequest;
import romanow.abc.core.OidString;
import romanow.abc.core.UniException;
import romanow.abc.core.Utils;
import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.Entity;
import romanow.abc.core.entity.EntityList;
import romanow.abc.core.entity.EntityRefList;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.baseentityes.*;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.subject2area.*;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.core.script.*;
import romanow.abc.core.utils.FileNameExt;
import okhttp3.MultipartBody;
import retrofit2.Call;
import romanow.abc.core.utils.Pair;
import romanow.abc.desktop.wizard.*;
import romanow.abc.drivers.ModBusClientProxyDriver;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import static romanow.abc.core.constants.Values.*;
import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

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

    public ESSMetaPanel() {
        initComponents();
    }

    public void initPanel(MainBaseFrame main0) {
        super.initPanel(main0);
        Password.setText(main.loginUser.getPassword());
        streamData.setBounds(370, 260, 450, 210);
        add(streamData);
        configSelector.setBounds(370, 400, 450, 165);
        add(configSelector);
        configSelector.setVisible(true);
        MetaDataSaveChanges.setVisible(false);
        MetaDataChanges.setVisible(false);
        MetaDataChangesLabel.setVisible(false);
        RunTimeSaveChanges.setVisible(false);
        RunTimeChanges.setVisible(false);
        RunTimeChangesLabel.setVisible(false);
        ExecScriptServer.setEnabled(false);
        ExecScriptClient.setEnabled(false);
        metaTypesMap = Values.constMap().getGroupMapByValue("MetaType");
        metaTypes = Values.constMap().getGroupList("MetaType");
        MetaTypes.removeAll();
        for(ConstValue cc : metaTypes)
            MetaTypes.add(cc.title());
        refreshArchitectures();
        refreshArchtectureState();
        setRenderingOff();
        if (!main2.getWorkSettings()){
            popup("Ошибка чтения настроек");
            System.out.println("Ошибка чтения настроек");
            }
        else{
            mainServerMode = ((WorkSettings)main2.workSettings).isMainServerMode();
            }
        Deploy.setVisible(!mainServerMode);
        Connect.setVisible(!mainServerMode);
        OnOff.setVisible(!mainServerMode);
        OnOffNode.setVisible(mainServerMode);
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

        jCheckBox1 = new javax.swing.JCheckBox();
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
        jSeparator7 = new javax.swing.JSeparator();
        ArchNodeRefrresh = new javax.swing.JButton();
        RemoveNode = new javax.swing.JButton();
        AddNode = new javax.swing.JButton();
        EditNode = new javax.swing.JButton();
        DownLoadArchitecture = new javax.swing.JButton();
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
        AddScript = new javax.swing.JButton();
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
        IECServerOnOff = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

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
        Password.setBounds(10, 500, 80, 25);

        OnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png"))); // NOI18N
        OnOff.setBorderPainted(false);
        OnOff.setContentAreaFilled(false);
        OnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OnOffActionPerformed(evt);
            }
        });
        add(OnOff);
        OnOff.setBounds(85, 425, 40, 40);

        DeviceRead.setText("Чтение");
        DeviceRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeviceReadActionPerformed(evt);
            }
        });
        add(DeviceRead);
        DeviceRead.setBounds(230, 600, 70, 22);

        DeviceWrite.setText("Запись");
        DeviceWrite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeviceWriteActionPerformed(evt);
            }
        });
        add(DeviceWrite);
        DeviceWrite.setBounds(230, 630, 70, 22);

        RegNum.setText("0");
        add(RegNum);
        RegNum.setBounds(80, 600, 90, 25);

        RegValue.setText("0");
        add(RegValue);
        RegValue.setBounds(80, 630, 90, 25);

        jLabel4.setText("Значение");
        add(jLabel4);
        jLabel4.setBounds(10, 640, 80, 16);

        HEXReg.setText("hex");
        HEXReg.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HEXRegItemStateChanged(evt);
            }
        });
        add(HEXReg);
        HEXReg.setBounds(180, 600, 50, 21);

        jLabel8.setText("Регистр");
        add(jLabel8);
        jLabel8.setBounds(10, 610, 80, 16);

        SettingsList.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SettingsListItemStateChanged(evt);
            }
        });
        add(SettingsList);
        SettingsList.setBounds(380, 90, 470, 20);

        jLabel10.setText("Уставка");
        add(jLabel10);
        jLabel10.setBounds(870, 90, 60, 16);

        SettingValue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SettingValueKeyPressed(evt);
            }
        });
        add(SettingValue);
        SettingValue.setBounds(850, 220, 60, 25);

        jLabel11.setText("Номер");
        add(jLabel11);
        jLabel11.setBounds(710, 165, 50, 16);

        jLabel12.setText("Значение");
        add(jLabel12);
        jLabel12.setBounds(630, 245, 90, 16);

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
        jLabel13.setBounds(380, 165, 90, 16);

        jLabel14.setText("Формула");
        add(jLabel14);
        jLabel14.setBounds(460, 245, 90, 16);

        DefValue.setEnabled(false);
        add(DefValue);
        DefValue.setBounds(630, 160, 60, 25);

        MinValue.setEnabled(false);
        add(MinValue);
        MinValue.setBounds(630, 190, 60, 25);

        jLabel15.setText("Минимум");
        add(jLabel15);
        jLabel15.setBounds(380, 195, 90, 16);

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
        jLabel16.setBounds(380, 225, 90, 16);

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
        CheckLimits.setBounds(790, 110, 90, 20);

        SettingEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SettingEditActionPerformed(evt);
            }
        });
        add(SettingEdit);
        SettingEdit.setBounds(880, 110, 30, 30);

        SettingsName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        SettingsName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(SettingsName);
        SettingsName.setBounds(380, 115, 400, 35);
        add(jSeparator1);
        jSeparator1.setBounds(10, 302, 360, 0);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        add(jSeparator2);
        jSeparator2.setBounds(370, 90, 10, 470);

        Nodes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NodesItemStateChanged(evt);
            }
        });
        add(Nodes);
        Nodes.setBounds(10, 20, 210, 25);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel18.setText("Конфигурации");
        add(jLabel18);
        jLabel18.setBounds(750, 460, 100, 14);

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
        jSeparator3.setBounds(10, 420, 340, 10);
        add(jSeparator4);
        jSeparator4.setBounds(380, 260, 370, 10);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setText("Тренды");
        add(jLabel20);
        jLabel20.setBounds(760, 250, 70, 14);

        WriteFloat.setText("Float");
        add(WriteFloat);
        WriteFloat.setBounds(290, 550, 70, 20);

        WriteInt32.setText("Int32");
        add(WriteInt32);
        WriteInt32.setBounds(290, 570, 60, 20);
        add(jSeparator6);
        jSeparator6.setBounds(380, 470, 360, 10);

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText("ЧМИ");
        add(jLabel24);
        jLabel24.setBounds(10, 215, 90, 14);

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
        Views.setBounds(10, 230, 210, 20);

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
        jLabel27.setBounds(10, 50, 90, 14);

        Architectures.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ArchitecturesItemStateChanged(evt);
            }
        });
        add(Architectures);
        Architectures.setBounds(10, 65, 210, 20);

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel28.setText("Лог.устройство (Unit)");
        add(jLabel28);
        jLabel28.setBounds(10, 130, 210, 14);
        add(Devices);
        Devices.setBounds(10, 190, 210, 20);

        Equipments.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EquipmentsItemStateChanged(evt);
            }
        });
        add(Equipments);
        Equipments.setBounds(10, 105, 210, 20);

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel29.setText("Оборудование");
        add(jLabel29);
        jLabel29.setBounds(10, 90, 90, 14);

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
        RemoveView.setBounds(260, 220, 30, 30);

        AddView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddView.setBorderPainted(false);
        AddView.setContentAreaFilled(false);
        AddView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddViewActionPerformed(evt);
            }
        });
        add(AddView);
        AddView.setBounds(220, 220, 40, 30);

        EditView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditView.setBorderPainted(false);
        EditView.setContentAreaFilled(false);
        EditView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditViewActionPerformed(evt);
            }
        });
        add(EditView);
        EditView.setBounds(290, 220, 30, 30);

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
        add(jSeparator7);
        jSeparator7.setBounds(10, 50, 350, 10);

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

        DownLoadArchitecture.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/no_problem.png"))); // NOI18N
        DownLoadArchitecture.setBorderPainted(false);
        DownLoadArchitecture.setContentAreaFilled(false);
        DownLoadArchitecture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownLoadArchitectureActionPerformed(evt);
            }
        });
        add(DownLoadArchitecture);
        DownLoadArchitecture.setBounds(330, 60, 30, 30);

        Deploy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/settings.png"))); // NOI18N
        Deploy.setBorderPainted(false);
        Deploy.setContentAreaFilled(false);
        Deploy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeployActionPerformed(evt);
            }
        });
        add(Deploy);
        Deploy.setBounds(10, 420, 40, 40);

        ArchitectureLabel.setText("Архитектура не выбрана");
        add(ArchitectureLabel);
        ArchitectureLabel.setBounds(140, 425, 170, 16);

        Connect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/status_gray.png"))); // NOI18N
        Connect.setBorderPainted(false);
        Connect.setContentAreaFilled(false);
        Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectActionPerformed(evt);
            }
        });
        add(Connect);
        Connect.setBounds(50, 425, 40, 40);

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setText("Эмуляторы");
        add(jLabel33);
        jLabel33.setBounds(10, 255, 90, 14);
        add(Emulators);
        Emulators.setBounds(10, 270, 210, 20);

        RemoveEmulator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveEmulator.setBorderPainted(false);
        RemoveEmulator.setContentAreaFilled(false);
        RemoveEmulator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveEmulatorActionPerformed(evt);
            }
        });
        add(RemoveEmulator);
        RemoveEmulator.setBounds(260, 260, 30, 30);

        AddEmulator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddEmulator.setBorderPainted(false);
        AddEmulator.setContentAreaFilled(false);
        AddEmulator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEmulatorActionPerformed(evt);
            }
        });
        add(AddEmulator);
        AddEmulator.setBounds(220, 260, 40, 30);

        EditEmulator.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditEmulator.setBorderPainted(false);
        EditEmulator.setContentAreaFilled(false);
        EditEmulator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditEmulatorActionPerformed(evt);
            }
        });
        add(EditEmulator);
        EditEmulator.setBounds(290, 260, 30, 30);

        ImportScript.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        ImportScript.setBorderPainted(false);
        ImportScript.setContentAreaFilled(false);
        ImportScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportScriptActionPerformed(evt);
            }
        });
        add(ImportScript);
        ImportScript.setBounds(320, 350, 40, 30);
        add(Scripts);
        Scripts.setBounds(10, 390, 340, 20);

        RemoveScript.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveScript.setBorderPainted(false);
        RemoveScript.setContentAreaFilled(false);
        RemoveScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveScriptActionPerformed(evt);
            }
        });
        add(RemoveScript);
        RemoveScript.setBounds(260, 350, 30, 30);

        AddScript.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddScript.setBorderPainted(false);
        AddScript.setContentAreaFilled(false);
        AddScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddScriptActionPerformed(evt);
            }
        });
        add(AddScript);
        AddScript.setBounds(220, 350, 40, 30);

        EditScript.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditScript.setBorderPainted(false);
        EditScript.setContentAreaFilled(false);
        EditScript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditScriptActionPerformed(evt);
            }
        });
        add(EditScript);
        EditScript.setBounds(290, 350, 30, 30);

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel34.setText("Скрипты");
        add(jLabel34);
        jLabel34.setBounds(10, 335, 60, 14);

        ExecScriptServer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/shifton1.png"))); // NOI18N
        ExecScriptServer.setBorderPainted(false);
        ExecScriptServer.setContentAreaFilled(false);
        ExecScriptServer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExecScriptServerActionPerformed(evt);
            }
        });
        add(ExecScriptServer);
        ExecScriptServer.setBounds(120, 350, 40, 30);

        Trace.setText("Трасса");
        add(Trace);
        Trace.setBounds(10, 350, 70, 20);

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
        ExecScriptClient.setBounds(70, 350, 40, 30);

        jLabel19.setText("сервер");
        add(jLabel19);
        jLabel19.setBounds(120, 335, 50, 16);

        jLabel30.setText("клиент");
        add(jLabel30);
        jLabel30.setBounds(70, 335, 50, 16);

        RuntimeEdit.setText("Редакт. \"на лету\"");
        RuntimeEdit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                RuntimeEditItemStateChanged(evt);
            }
        });
        add(RuntimeEdit);
        RuntimeEdit.setBounds(150, 460, 130, 20);

        RunTimeSaveChanges.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        RunTimeSaveChanges.setBorderPainted(false);
        RunTimeSaveChanges.setContentAreaFilled(false);
        RunTimeSaveChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunTimeSaveChangesActionPerformed(evt);
            }
        });
        add(RunTimeSaveChanges);
        RunTimeSaveChanges.setBounds(280, 455, 30, 30);

        RunTimeChanges.setEnabled(false);
        add(RunTimeChanges);
        RunTimeChanges.setBounds(320, 460, 40, 25);

        RunTimeChangesLabel.setText("Изменений");
        add(RunTimeChangesLabel);
        RunTimeChangesLabel.setBounds(280, 440, 70, 16);
        add(MetaTypes);
        MetaTypes.setBounds(650, 50, 140, 20);

        jLabel31.setText(" ограничений");
        add(jLabel31);
        jLabel31.setBounds(790, 130, 80, 16);

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
        jLabel37.setBounds(330, 250, 30, 20);

        ExportXMLView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        ExportXMLView.setBorderPainted(false);
        ExportXMLView.setContentAreaFilled(false);
        ExportXMLView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportXMLViewActionPerformed(evt);
            }
        });
        add(ExportXMLView);
        ExportXMLView.setBounds(330, 220, 30, 30);

        jLabel39.setText("Контроллер (драйвер)");
        add(jLabel39);
        jLabel39.setBounds(10, 550, 210, 16);
        add(DevicesRW);
        DevicesRW.setBounds(10, 570, 210, 20);
        add(jSeparator8);
        jSeparator8.setBounds(10, 540, 350, 10);

        UnitRW.setText("0");
        add(UnitRW);
        UnitRW.setBounds(230, 570, 40, 25);

        jLabel38.setText("Unit");
        add(jLabel38);
        jLabel38.setBounds(230, 550, 40, 16);

        HEXValue.setText("hex");
        HEXValue.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                HEXValueItemStateChanged(evt);
            }
        });
        add(HEXValue);
        HEXValue.setBounds(180, 630, 50, 20);

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setText("Контроллер (драйвер)");
        add(jLabel40);
        jLabel40.setBounds(10, 170, 210, 14);
        add(LogUnits);
        LogUnits.setBounds(10, 145, 210, 20);

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
        jSeparator9.setBounds(10, 490, 350, 10);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setText("Переменные окружения");
        add(jLabel41);
        jLabel41.setBounds(10, 295, 180, 14);
        add(EnvValue);
        EnvValue.setBounds(10, 310, 210, 20);

        RemoveEnvValue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        RemoveEnvValue.setBorderPainted(false);
        RemoveEnvValue.setContentAreaFilled(false);
        RemoveEnvValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveEnvValueActionPerformed(evt);
            }
        });
        add(RemoveEnvValue);
        RemoveEnvValue.setBounds(260, 300, 30, 30);

        AddEnvValue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        AddEnvValue.setBorderPainted(false);
        AddEnvValue.setContentAreaFilled(false);
        AddEnvValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddEnvValueActionPerformed(evt);
            }
        });
        add(AddEnvValue);
        AddEnvValue.setBounds(220, 300, 40, 30);

        EditEnvValue.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/media_list.png"))); // NOI18N
        EditEnvValue.setBorderPainted(false);
        EditEnvValue.setContentAreaFilled(false);
        EditEnvValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditEnvValueActionPerformed(evt);
            }
        });
        add(EditEnvValue);
        EditEnvValue.setBounds(290, 300, 30, 30);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText(" все");
        add(jLabel3);
        jLabel3.setBounds(170, 335, 40, 14);

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
        ExportScripts.setBounds(170, 350, 30, 30);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText(" все");
        add(jLabel5);
        jLabel5.setBounds(500, 70, 40, 14);

        FullScreen.setText("Полный экран");
        add(FullScreen);
        FullScreen.setBounds(150, 440, 120, 20);

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
        CIDLocal.setBounds(320, 505, 40, 30);

        IECServerOnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/status_gray.png"))); // NOI18N
        IECServerOnOff.setBorderPainted(false);
        IECServerOnOff.setContentAreaFilled(false);
        IECServerOnOff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IECServerOnOffActionPerformed(evt);
            }
        });
        add(IECServerOnOff);
        IECServerOnOff.setBounds(280, 500, 40, 40);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("IEC61850");
        add(jLabel6);
        jLabel6.setBounds(220, 520, 70, 16);
    }// </editor-fold>//GEN-END:initComponents

    private void ImportMetaDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportMetaDataActionPerformed
        FileNameExt fname = main.getInputFileName("Импорт мета-данных", "*.xls", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.service.upload(main.debugToken, "Meta-Data import", fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact oo) {
                new APICall<ArrayList<OidString>>(main) {
                    @Override
                    public Call<ArrayList<OidString>> apiFun() {
                        return main2.service2.importMetaData(main.debugToken, Password.getText(), oo.getOid());
                       }
                    @Override
                    public void onSucess(ArrayList<OidString> oo) {
                        System.out.print(oo.get(0));
                        System.out.print(oo.get(1));
                        if (oo.get(0).valid() && oo.get(1).valid())
                            popup("Импорт мета-данных выполнен");
                        else
                            main.sendEventPanel(EventLogToFront,0,0,"Ошибки импорта");
                        //popup("Импорт мета-данных оборудования" + (!oo.o1.valid() ? " не" : "") + " выполнен");
                        //popup("Импорт мета-данных ЧМИ" + (!oo.o2.valid() ? " не" : "") + " выполнен");
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
                return main.service.getEntityList(main.debugToken, "ESS2MetaFile", Values.GetAllModeActual, 1);
                }
            @Override
            public void onSucess(ArrayList<DBRequest> oo) {
                try {
                    metaData.clear();
                    for (DBRequest request : oo) {
                        ESS2MetaFile file = (ESS2MetaFile) request.get(main.gson);
                        metaData.add(file);
                        //ConstValue cc = metaTypesMap.get(file.getMetaType());
                        //MetaFile.add((cc==null ? "" : cc.title()+" ")+file.toString());
                        MetaFile.add(file.toString());
                        }
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
                return main.service.getEntityList(main.debugToken, "ESS2Node", Values.GetAllModeActual, 5);
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
        architecture.createStreamRegisterList();
        }

    private void refreshArchitectures() {
        final int idx = Architectures.getItemCount() != 0 ? Architectures.getSelectedIndex() : -1;
        Architectures.removeAll();
        new APICall<ArrayList<DBRequest>>(main) {
            @Override
            public Call<ArrayList<DBRequest>> apiFun() {
                return main.service.getEntityList(main.debugToken, "ESS2Architecture", Values.GetAllModeActual, 4);
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

    private void refreshArchtectureState(){
        new APICall<ArrayList<Long>>(main) {
            @Override
            public Call<ArrayList<Long>> apiFun() {
                return main2.service2.getArchitectureState(main.debugToken);
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
    public void setRenderingOff(){
        final ESS2View view = main2.currentView;
        main2.currentView=null;
        OnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png")));
        OnOffNode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-off.png")));
        main.sendEvent(EventPLMOff,0);
        setFullScreenOff();
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
                screen.dispose();
                screen = null;
                }
            });
        screen.setVisible(true);
        screen.eventPanel(EventPLMOn,0,0,"",null);
        screen.refresh();
        }
    public void setRenderingOn() {
        main2.setRenderingOn(0,deployed.getViews().get(Views.getSelectedIndex()),Trace.isSelected());
        OnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/connect-on.png")));
        fullScreenOn();
        }

    private void OnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnOffActionPerformed
        if (main2.currentView!=null){
            setRenderingOff();
            }
        else{
            if (!deployed.isConnected())
                return;
            setRenderingOn();
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

    private void StreamDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StreamDataActionPerformed
        /*
        try {
            if (!main2.plm.isReady()) {
                popup("Устройство не готово");
                return;
            }
            int regNum = Integer.parseInt(RegNum.getText());
            new APICall<DataSet>(main) {
                @Override
                public Call<DataSet> apiFun() {
                    return main2.service2.getStreamData(main.debugToken, 0, regNum, 0, 0, 0, 0, 0);
                }

                @Override
                public void onSucess(DataSet oo) {
                    trendView.addTrendView(oo);
                    trendView.toFront();
                }
            };
        } catch (Exception ee) {
            popup(ee.toString());
        }

         */
    }//GEN-LAST:event_StreamDataActionPerformed

    public void refreshMetaData() {
        createMetaDataList();
        changesCount = 0;
        MetaDataChanges.setText("" + changesCount);
        }
    public void refreshScripts() {
        Scripts.removeAll();
        if (architecture == null)
            return;
        for (ESS2ScriptFile script : architecture.getScripts()) {
            Scripts.add(script.toString());
            }
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
                        return main.service.removeArtifact(main.debugToken,metaFile.getFile().getOid());
                        }
                    @Override
                    public void onSucess(JEmpty oo) {
                        new APICall<JBoolean>(main) {
                            @Override
                            public Call<JBoolean> apiFun() {
                                return main.service.removeEntity(main.debugToken,"ESS2MetaFile",metaFile.getOid());
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
        else
            meta2XML = new Meta2GUIView();
        meta2XML.setShortName("newItem");
        meta2XML.setTitle("Новый");
        meta2XML.setXmlType(newType);
        metaFile.synchWithMeta2XML(meta2XML);
        String dataString = new Meta2XStream().toXML(meta2XML);
        new APICall<Artifact>(main){
            @Override
            public Call<Artifact> apiFun() {
                return main.service.createArtifactFromString(main.debugToken,"MetaData.xml",dataString);
                }
            @Override
            public void onSucess(Artifact oo) {
                metaFile.getFile().setOidRef(oo);
                new APICall<JLong>(main){
                    @Override
                    public Call<JLong> apiFun() {
                        return main.service.addEntity(main.debugToken,new DBRequest(metaFile,main.gson),0);
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
            System.out.println("Несохраненные изменения: сохранить или обновить список");
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
                    String zz = WizardBaseView.openWizard(main, metaXML, new I_Value<String>() {
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
                    return main.service.updateEntity(main.debugToken, new DBRequest(metaFile,main.gson));
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
                        return main.service.updateEntity(main.debugToken, new DBRequest(metaFile, main.gson));
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
                        return main.service.removeEntity(main.debugToken, "ESS2Device", device.getOid());
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
                        return main.service.addEntity(main.debugToken, new DBRequest(connector, main.gson), 0);
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
        new OK(200, 200, "Добавить оборудование", new I_Button() {
            @Override
            public void onPush() {
                final ESS2Equipment equipment = new ESS2Equipment("...", "Новое", "");
                equipment.getESS2Architecture().setOid(architecture.getOid());
                equipment.getMetaFile().setOid(metaFile.getOid());
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.service.addEntity(main.debugToken, new DBRequest(equipment, main.gson), 0);
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
                return main.service.removeEntity(main.debugToken, "ESS2View", view.getOid());
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
                    return main.service.removeEntity(main.debugToken, "ESS2LogUnit", connector.getOid());
                }

                @Override
                public void onSucess(JBoolean val) {
                }
            };
        }
        new APICall<JBoolean>(main) {
            @Override
            public Call<JBoolean> apiFun() {
                return main.service.removeEntity(main.debugToken, "ESS2Equipment", equipment.getOid());
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
                        return main.service.removeEntity(main.debugToken, "ESS2Architecture", architecture.getOid());
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
                        return main.service.addEntity(main.debugToken, new DBRequest(arch, main.gson), 0);
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
        new OK(200, 200, "Добавить ЧМИ", new I_Button() {
            @Override
            public void onPush() {
                final ESS2View view = new ESS2View("...", "Новый", "");
                view.getESS2Architecture().setOid(architecture.getOid());
                view.getMetaFile().setOid(metaFile.getOid());
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.service.addEntity(main.debugToken, new DBRequest(view, main.gson), 0);
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
                        return main.service.updateEntity(main.debugToken, new DBRequest(view, main.gson));
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
                        return main.service.updateEntity(main.debugToken, new DBRequest(architecture, main.gson));
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
                        return main.service.updateEntity(main.debugToken, new DBRequest(equipment, main.gson));
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
                        return main.service.updateEntity(main.debugToken, new DBRequest(connector, main.gson));
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
                        return main.service.removeEntity(main.debugToken, "ESS2Node", oid);
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
                        return main.service.addEntity(main.debugToken, new DBRequest(new ESS2Node(), main.gson), 0);
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
                        return main.service.updateEntity(main.debugToken, new DBRequest(node, main.gson));
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

    private void DownLoadArchitectureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownLoadArchitectureActionPerformed
        if (Architectures.getItemCount() == 0)
            return;
        ESS2Architecture vv = main2.loadFullArchitecture(architectures.get(Architectures.getSelectedIndex()).getOid());
        vv.testFullArchitecture();
        if (vv.getErrors().getErrCount() == 0) {
            architecture = vv;
            }
        System.out.println("-------------------------------------\n" + vv.getErrors().toString());
    }//GEN-LAST:event_DownLoadArchitectureActionPerformed

    private void clearDeployedMetaData(){
        ArchitectureLabel.setText("Архитектура не выбрана");
        Deploy.setIcon(new javax.swing.ImageIcon(getClass().getResource(archStateIcons[0])));
        Connect.setIcon(new javax.swing.ImageIcon(getClass().getResource(connStateIcons[0])));
        main2.deployed = null;
        main2.currentView = null;
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
                    return main2.service2.metaDataCancel(main.debugToken,Password.getText());
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
                    return main2.service2.metaDataDeploy(main.debugToken, Password.getText(), oid);
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

    private void ConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectActionPerformed
        setRenderingOff();
        if (deployed.isConnected()){
            new APICall<CallResult>(main){
                @Override
                public Call<CallResult> apiFun() {
                    return main2.service2.disconnectFromEquipment(main2.debugToken);
                    }
                @Override
                public void onSucess(CallResult oo) {
                    int state = oo.getState();
                    boolean connected = state == Values.ASConnected;
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
            popup("Недопустимое состояние мета-данных и обобрудования для соединения");
            return;
            }
        new APICall<CallResult>(main){
            @Override
            public Call<CallResult> apiFun() {
                return main2.service2.connectToEquipment(main2.debugToken);
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
        new OK(200, 200, "Удалить ЧМИ " + emulator.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.service.removeEntity(main.debugToken, "ESS2EquipEmulator", emulator.getOid());
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
                        return main.service.addEntity(main.debugToken, new DBRequest(emulator, main.gson), 0);
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
                        return main.service.updateEntity(main.debugToken, new DBRequest(emulator, main.gson));
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
                return main.service.upload(main.debugToken, "Script import", fname.fileName(), body);
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
                        return main.service.addEntity(main.debugToken, new DBRequest(script,main.gson),0);
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
        // TODO add your handling code here:
    }//GEN-LAST:event_RemoveScriptActionPerformed

    private void AddScriptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddScriptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddScriptActionPerformed

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
                                    return main.service.updateEntity(main.debugToken, new DBRequest(scriptFile, main.gson));
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
                return main2.service2.execScript(main.debugToken,scriptFile.getOid(),Trace.isSelected());
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
        FileNameExt fname = main.getInputFileName("Импорт мета-данных 2.0", "*.xls", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.service.upload(main.debugToken, "Meta-Data import", fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact oo) {
                new APICall<ArrayList<OidString>>(main) {
                    @Override
                    public Call<ArrayList<OidString>> apiFun() {
                        return main2.service2.importMetaData2(main.debugToken, Password.getText(), oo.getOid());
                        }
                    @Override
                    public void onSucess(ArrayList<OidString> oo) {
                        for(OidString ss : oo){
                            System.out.print(ss);
                            if (!ss.valid())
                                main.sendEventPanel(EventLogToFront,0,0,"Импорт мета-данных оборудования не выполнен");
                            else
                                popup("Импорт мета-данных оборудования выполнен");
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
            popup("Нет соединения с оборудованием");
            return;
            }
        final ESS2ScriptFile scriptFile = deployed.getScripts().get(Scripts.getSelectedIndex());
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
        ESS2MetaFile metaFile2 = view.getMetaFile().getRef();
        String dataString = new Meta2XStream().toXML(view.getView());
        main.updateArtifactFromString(metaFile2.getFile().getRef(), dataString, new I_OK() {
            @Override
            public void onOK(Entity ent) {
                metaFile2.getFile().setRef((Artifact) ent);
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.service.updateEntity(main.debugToken, new DBRequest(metaFile2, main.gson));
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
        saveRunTimeChanges(main2.currentView);
        clearRunTimeChanges();
    }//GEN-LAST:event_RunTimeSaveChangesActionPerformed

    private void MetaFileItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MetaFileItemStateChanged
        setMetaTypeSelector(metaData.get(MetaFile.getSelectedIndex()).getMetaType());
    }//GEN-LAST:event_MetaFileItemStateChanged

    private void ImportXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportXMLActionPerformed
        FileNameExt fname = main.getInputFileName("Загрузка мета-данных", "*.xml", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.service.upload(main.debugToken, "Meta-Data load", fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact oo) {
                new APICall<OidString>(main) {
                    @Override
                    public Call<OidString> apiFun() {
                        return main2.service2.loadMetaData(main.debugToken, Password.getText(), oo.getOid());
                       }
                    @Override
                    public void onSucess(OidString oo) {
                        System.out.print(oo);
                        if (oo.valid())
                            popup("Загрузка мета-данных выполнена");
                        else
                            main.sendEventPanel(EventLogToFront,0,0,"Ошибки загрузки");
                        //popup("Импорт мета-данных оборудования" + (!oo.o1.valid() ? " не" : "") + " выполнен");
                        //popup("Импорт мета-данных ЧМИ" + (!oo.o2.valid() ? " не" : "") + " выполнен");
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
                        return main.service.removeEntity(main.debugToken, "ESS2LogUnit", connector.getOid());
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
                        return main.service.addEntity(main.debugToken, new DBRequest(logUnit, main.gson), 0);
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
                        return main.service.updateEntity(main.debugToken, new DBRequest(connector, main.gson));
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
        new OK(200, 200, "Удалить оборудование " + envValue.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                new APICall<JBoolean>(main) {
                    @Override
                    public Call<JBoolean> apiFun() {
                        return main.service.removeEntity(main.debugToken, "ESS2EnvValue", envValue.getOid());
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
                        return main.service.addEntity(main.debugToken, new DBRequest(envValue, main.gson), 0);
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
                        return main.service.updateEntity(main.debugToken, new DBRequest(envValue, main.gson));
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
                FileNameExt ff = main.getOutputFileName("Каталог импорта","aaa","aaa");
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
                FileNameExt ff = main.getOutputFileName("Каталог импорта","aaa","aaa");
                String loadDirectory = ff.getPath();
                ArrayList<Artifact> artifacts = new ArrayList<>();
                for(ESS2ScriptFile metaFile : architecture.getScripts())
                    artifacts.add(metaFile.getFile().getRef());
                main.loadFileGroup(loadDirectory,artifacts,0);
                }
            });
    }//GEN-LAST:event_ExportScriptsActionPerformed

    private void OnOffNodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OnOffNodeActionPerformed
        if (main2.currentView!=null) {
            final ESS2View view = main2.currentView;
            main2.currentView=null;
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
                main2.setRenderingOn(node.getOid(),view,Trace.isSelected());
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

    private void IECServerOnOffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IECServerOnOffActionPerformed
        if (!main2.deployed.isConnected()){
            System.out.println("Допустимо только при подключенном оборудовании");
            return;
            }
        new APICall<CallResult>(main) {
            @Override
            public Call<CallResult> apiFun() {
                return main2.service2.iec61850ServerOnOff(main.debugToken, deployed.getEquipments().get(Equipments.getSelectedIndex()).getOid());
                }
            @Override
            public void onSucess(CallResult vv) {
                System.out.println(vv.toString());
                switch (vv.getState()){
                    case IEC61850StOn:
                        IECServerOnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource( "/drawable/status_green.png")));
                        break;
                    case IEC61850StFail:
                        IECServerOnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource( "/drawable/status_red.png")));
                        break;
                    case IEC61850StOff:
                        IECServerOnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource( "/drawable/status_gray.png")));
                        break;
                        }
                }
            };
    }//GEN-LAST:event_IECServerOnOffActionPerformed


    @Override
    public void refresh() {
        refreshMetaData();
        refreshArchitectures();
        refreshNodes();
        initStreamData();
        /*
        try {
            JBoolean bb = new APICall2<JBoolean>() {
                @Override
                public Call<JBoolean> apiFun() {
                    return main2.service2.isPLMReady(main.debugToken);
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
        RemoveNode.setEnabled(enabled);
        RemoveArch.setEnabled(enabled);
        RemoveEquip.setEnabled(enabled);
        RemoveDevice.setEnabled(enabled);
        RemoveEmulator.setEnabled(enabled);
        RemoveView.setEnabled(enabled);
        RemoveMeta.setEnabled(enabled);
        RemoveLogUnit.setEnabled(enabled);
        EditNode.setEnabled(enabled);
        EditArch.setEnabled(enabled);
        EditEquip.setEnabled(enabled);
        EditDevice.setEnabled(enabled);
        EditEmulator.setEnabled(enabled);
        EditView.setEnabled(enabled);
        EditLogUnit.setEnabled(enabled);
        //EditMeta.setEnabled(enabled);
        ImportScript.setEnabled(enabled);
        ImportMetaData.setEnabled(enabled);
        ImportMetaEquipment2.setEnabled(enabled);
        DownLoadArchitecture.setEnabled(enabled);
        ArchNodeRefrresh.setEnabled(enabled);
        RefreshMeta.setEnabled(enabled);
        ExecScriptServer.setEnabled(!enabled);
        ExecScriptClient.setEnabled(!enabled);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddArch;
    private javax.swing.JButton AddDevice;
    private javax.swing.JButton AddEmulator;
    private javax.swing.JButton AddEnvValue;
    private javax.swing.JButton AddEquip;
    private javax.swing.JButton AddLogUnit;
    private javax.swing.JButton AddMeta;
    private javax.swing.JButton AddNode;
    private javax.swing.JButton AddScript;
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
    private javax.swing.JButton DownLoadArchitecture;
    private javax.swing.JButton EditArch;
    private javax.swing.JButton EditDevice;
    private javax.swing.JButton EditEmulator;
    private javax.swing.JButton EditEnvValue;
    private javax.swing.JButton EditEquip;
    private javax.swing.JButton EditLogUnit;
    private javax.swing.JButton EditMeta;
    private javax.swing.JButton EditNode;
    private javax.swing.JButton EditScript;
    private javax.swing.JButton EditView;
    private java.awt.Choice Emulators;
    private java.awt.Choice EnvValue;
    private java.awt.Choice Equipments;
    private javax.swing.JButton ExecScriptClient;
    private javax.swing.JButton ExecScriptServer;
    private javax.swing.JButton ExportScripts;
    private javax.swing.JButton ExportXML;
    private javax.swing.JButton ExportXMLAll;
    private javax.swing.JButton ExportXMLEquipment;
    private javax.swing.JButton ExportXMLView;
    private javax.swing.JCheckBox FullScreen;
    private javax.swing.JCheckBox HEXReg;
    private javax.swing.JCheckBox HEXValue;
    private javax.swing.JButton IECServerOnOff;
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
    private javax.swing.JButton OnOffNode;
    private javax.swing.JPasswordField Password;
    private javax.swing.JButton RefreshMeta;
    private javax.swing.JTextField RegNum;
    private javax.swing.JTextField RegValue;
    private javax.swing.JButton RemoveArch;
    private javax.swing.JButton RemoveDevice;
    private javax.swing.JButton RemoveEmulator;
    private javax.swing.JButton RemoveEnvValue;
    private javax.swing.JButton RemoveEquip;
    private javax.swing.JButton RemoveLogUnit;
    private javax.swing.JButton RemoveMeta;
    private javax.swing.JButton RemoveNode;
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
    private javax.swing.JCheckBox jCheckBox1;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    // End of variables declaration//GEN-END:variables
}
