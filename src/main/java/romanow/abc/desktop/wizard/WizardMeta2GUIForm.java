/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import okhttp3.MultipartBody;
import retrofit2.Call;
import romanow.abc.core.API.RestAPICommon;
import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.artifacts.ArtifactList;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUICollection;
import romanow.abc.core.utils.FileNameExt;
import romanow.abc.desktop.APICall;
import romanow.abc.desktop.I_Button;

import romanow.abc.desktop.OK;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;


import static romanow.abc.core.entity.metadata.view.Meta2GUI.createByType;

/**
 *
 * @author romanow0
 */
public class WizardMeta2GUIForm extends WizardBaseView {

    /**
     * Creates new form WizardMeta2Equipment
     */
    public WizardMeta2GUIForm() {
        initComponents();
        }
    private ArtifactList artifacts = new ArtifactList();
    private ArrayList<ConstValue> types = new ArrayList<>();
    private ArrayList<ConstValue> access = new ArrayList<>();
    private Meta2GUIForm view;
    private Meta2GUICollection list;
    private WizardMetaEntitySelector selector;
    private Meta2EntityList<Meta2GUIForm> forms;
    @Override
    public void openForm(WizardBaseView parentView0, Meta2Entity entity0){
        super.openForm(parentView0,entity0);
        resizeHeight(480);
        view = (Meta2GUIForm) entity0;
        list = view.getControls();
        selector = new WizardMetaEntitySelector("Элементы ЧМИ", Values.MEGUIElement,callBack);
        selector.setBounds(10,80,750,40);
        getContentPane().add(selector);
        access = Values.constMap().getGroupList("AccessLevel");
        types = Values.constMap().getGroupList("GUIType");
        types.sort(new Comparator<ConstValue>() {
            @Override
            public int compare(ConstValue o1, ConstValue o2) {
                return o1.title().compareTo(o2.title());
                }
            });
        Types.removeAll();
        for(ConstValue cc : types)
            Types.add(cc.title());
        AccessLevel.removeAll();
        WriteLevel.removeAll();
        for(ConstValue cc : access){
            AccessLevel.add(cc.title());
            WriteLevel.add(cc.title());
            }
        Parents.removeAll();
        WizardBaseView root = parentView;
        if (root!=null){
            while(root.parentView!=null)
                root = root.parentView;
            if (!(root instanceof WizardMeta2GUIView))
                System.out.println("Ошибка поиска предка");
            else{
                forms = ((WizardMeta2GUIView)root).getList();
                Parents.add("...");
                for(Meta2GUIForm ff : forms.getList())
                    Parents.add(ff.getTitle());
                Parents.select(0);
                for(int i=0;i<forms.getList().size();i++){
                    if (view.getParentName().equals(forms.getList().get(i).getTitle())){
                        Parents.select(i+1);
                        break;
                        }
                    }
                }
            }
        refreshList();
        }
    public void selectCoice(int value, Choice cc){
        for(int i=0;i<access.size();i++)
            if (access.get(i).value()==value){
                cc.select(i);
                break;
            }
        }
    public void refreshList(){
            Choice choice = selector.getList();
            choice.removeAll();
            for(Meta2GUI element : list.getList()){
                String ss = element.getFullTitle();
                choice.add(ss);
                }
            FormLevel.setText(""+view.getFormLevel());
            Level.setText(""+view.getLevel());
            ElemCount.setText(""+view.getElementsCount());
            Module.setText(view.getModuleName());
            NoMenu.setSelected(view.isNoMenu());
            Empty.setSelected(view.isEmpty());
            Image.setText(view.getBackImage());
            selectCoice(view.getWriteLevel(),WriteLevel);
            selectCoice(view.getAccessLevel(),AccessLevel);
            GroupIndex.setText(""+view.getBaseFormIndex());
            LinkForm.setSelected(view.isLinkForm());
            BaseForm.setSelected(view.isBaseForm());
            SnapShot.setSelected(view.isSnapShot());
            DebugForm.setSelected(view.isDebugForm());
            //---------------------------------------------------------------------------
            ImageX0.setText(""+view.getImageX0());
            ImageY0.setText(""+view.getImageY0());
            ImageW.setText(""+view.getImageW());
            ImageH.setText(""+view.getImageH());
            refreshImageList();
            //---------------------------------------------------------------------------
            MenuOnColor.setText(""+String.format("%06x",view.getMenuButtonOnColor()));
            MenuOnColorButton.setBackground(new Color(view.getMenuButtonOnColor()));
            MenuOffColor.setText(""+String.format("%06x",view.getMenuButtonOffColor()));
            MenuOffColorButton.setBackground(new Color(view.getMenuButtonOffColor()));
            MenuTextColor.setText(""+String.format("%06x",view.getMenuButtonTextColor()));
            MenuTextColorButton.setBackground(new Color(view.getMenuButtonTextColor()));
            MenuBold.setSelected(view.isMenuFontBold());
            MenuFontSize.setText(""+view.getMenuButtonFontSize());
            MenuButtonW.setText(""+view.getMenuButtonW());
            MenuButtonH.setText(""+view.getMenuButtonH());
            ModuleX0.setText(""+view.getModuleX0());
            ModuleY0.setText(""+view.getModuleY0());
            ModuleDX.setText(""+view.getModuleDX());
            ModuleDY.setText(""+view.getModuleDY());
            //-------------------------------------------------------------------------
            }
    private void refreshImageList(){
        new APICall<ArtifactList>(main) {
            @Override
            public Call<ArtifactList> apiFun() {
                return main.getService().getArtifactConditionList(main.getDebugToken(), Values.ArtifactImageType,"",
                        Values.ESSImageFileDescription,"",0,0,0,0);
            }
            @Override
            public void onSucess(ArtifactList oo) {
                artifacts = oo;
                setArtifactsChoice(artifacts,ImageList,view.getPicture().getOid());
                setArtifactsChoice(artifacts,IconList,view.getIcon().getOid());
                }
            };
        }
    private I_WizardEntitySelector callBack = new I_WizardEntitySelector() {
        @Override
        public void onEdit(int type, int idx) {
            String ss = openWizardByType(list.getList().get(idx));
            if (ss!=null)
                System.out.println(ss);
            }
        @Override
        public void onRemove(int type, int idx) {
            final String title = list.getList().get(idx).getTitle();
            new OK(300, 300, "Удалить " + title, new I_Button() {
                @Override
                public void onPush() {
                    list.getList().remove(idx);
                    refreshList();
                    back.onEnter("Удален: "+title);
                }
            });
        }
        @Override
        public void onAdd(int type0) {
            int type = types.get(Types.getSelectedIndex()).value();
            Meta2GUI entity = (Meta2GUI) createByType(type);
            if (entity.getErrors().getErrCount()!=0){
                System.out.println(entity.getErrors().toString());
                main.popup("Ошибка создания элемента ЧМИ");
                return;
                }
            list.add(entity);
            refreshList();
            String ss = openWizardByType(entity);
            if (ss!=null)
                 System.out.println(ss);
            }
        @Override
        public void onSelect(int type, int idx) {}
        };

    @Override
    public void closeForm(){
        super.closeForm();
        }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Types = new java.awt.Choice();
        jLabel1 = new javax.swing.JLabel();
        label111 = new javax.swing.JLabel();
        FormLevel = new javax.swing.JTextField();
        label112 = new javax.swing.JLabel();
        Parents = new java.awt.Choice();
        Module = new javax.swing.JTextField();
        label113 = new javax.swing.JLabel();
        Empty = new javax.swing.JCheckBox();
        NoMenu = new javax.swing.JCheckBox();
        SaveParent = new javax.swing.JButton();
        AccessLevel = new java.awt.Choice();
        label114 = new javax.swing.JLabel();
        SaveAccessLevel = new javax.swing.JButton();
        WriteLevel = new java.awt.Choice();
        label115 = new javax.swing.JLabel();
        SaveWriteLevel = new javax.swing.JButton();
        label116 = new javax.swing.JLabel();
        Image = new javax.swing.JTextField();
        label117 = new javax.swing.JLabel();
        ElemCount = new javax.swing.JTextField();
        label118 = new javax.swing.JLabel();
        label119 = new javax.swing.JLabel();
        Level = new javax.swing.JTextField();
        label120 = new javax.swing.JLabel();
        label121 = new javax.swing.JLabel();
        label122 = new javax.swing.JLabel();
        GroupIndex = new javax.swing.JTextField();
        label123 = new javax.swing.JLabel();
        BaseForm = new javax.swing.JCheckBox();
        LinkForm = new javax.swing.JCheckBox();
        SnapShot = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        MenuOnColor = new javax.swing.JTextField();
        MenuOnColorButton = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        MenuOffColor = new javax.swing.JTextField();
        MenuOffColorButton = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        MenuTextColor = new javax.swing.JTextField();
        MenuTextColorButton = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        MenuBold = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        MenuButtonW = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        MenuFontSize = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        MenuButtonH = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        label124 = new javax.swing.JLabel();
        ModuleDX = new javax.swing.JTextField();
        label125 = new javax.swing.JLabel();
        label126 = new javax.swing.JLabel();
        ModuleX0 = new javax.swing.JTextField();
        ModuleDY = new javax.swing.JTextField();
        ModuleY0 = new javax.swing.JTextField();
        label127 = new javax.swing.JLabel();
        label128 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        ImageH = new javax.swing.JTextField();
        ImageW = new javax.swing.JTextField();
        ImageList = new java.awt.Choice();
        SetImage = new javax.swing.JButton();
        UploadImage = new javax.swing.JButton();
        ImageAlias = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        ImageX0 = new javax.swing.JTextField();
        ImageY0 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        IconList = new java.awt.Choice();
        SetIcon = new javax.swing.JButton();
        UploadIcon = new javax.swing.JButton();
        IconAlias = new javax.swing.JTextField();
        DebugForm = new javax.swing.JCheckBox();

        getContentPane().setLayout(null);
        getContentPane().add(Types);
        Types.setBounds(610, 150, 140, 25);

        jLabel1.setText("Элемент ЧМИ+");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(510, 150, 100, 16);

        label111.setText("Y0");
        getContentPane().add(label111);
        label111.setBounds(690, 280, 30, 16);

        FormLevel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FormLevelKeyPressed(evt);
            }
        });
        getContentPane().add(FormLevel);
        FormLevel.setBounds(70, 190, 40, 25);

        label112.setText("группы");
        getContentPane().add(label112);
        label112.setBounds(10, 205, 60, 10);
        getContentPane().add(Parents);
        Parents.setBounds(310, 150, 140, 20);

        Module.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ModuleKeyPressed(evt);
            }
        });
        getContentPane().add(Module);
        Module.setBounds(430, 280, 170, 25);

        label113.setText("Тип добавляемого");
        getContentPane().add(label113);
        label113.setBounds(610, 130, 140, 16);

        Empty.setText("Пустая");
        Empty.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                EmptyItemStateChanged(evt);
            }
        });
        getContentPane().add(Empty);
        Empty.setBounds(500, 250, 80, 20);

        NoMenu.setText("Без меню");
        NoMenu.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NoMenuItemStateChanged(evt);
            }
        });
        getContentPane().add(NoMenu);
        NoMenu.setBounds(500, 210, 90, 20);

        SaveParent.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SaveParent.setBorderPainted(false);
        SaveParent.setContentAreaFilled(false);
        SaveParent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveParentActionPerformed(evt);
            }
        });
        getContentPane().add(SaveParent);
        SaveParent.setBounds(460, 140, 30, 30);
        getContentPane().add(AccessLevel);
        AccessLevel.setBounds(310, 190, 140, 20);

        label114.setText("Доступ");
        getContentPane().add(label114);
        label114.setBounds(250, 190, 60, 16);

        SaveAccessLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SaveAccessLevel.setBorderPainted(false);
        SaveAccessLevel.setContentAreaFilled(false);
        SaveAccessLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveAccessLevelActionPerformed(evt);
            }
        });
        getContentPane().add(SaveAccessLevel);
        SaveAccessLevel.setBounds(460, 180, 30, 30);
        getContentPane().add(WriteLevel);
        WriteLevel.setBounds(310, 230, 140, 20);

        label115.setText("Запись");
        getContentPane().add(label115);
        label115.setBounds(250, 230, 60, 16);

        SaveWriteLevel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SaveWriteLevel.setBorderPainted(false);
        SaveWriteLevel.setContentAreaFilled(false);
        SaveWriteLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveWriteLevelActionPerformed(evt);
            }
        });
        getContentPane().add(SaveWriteLevel);
        SaveWriteLevel.setBounds(460, 220, 30, 30);

        label116.setText("Фон (файл)");
        getContentPane().add(label116);
        label116.setBounds(510, 185, 80, 16);

        Image.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImageKeyPressed(evt);
            }
        });
        getContentPane().add(Image);
        Image.setBounds(610, 180, 140, 25);

        label117.setText(" элементов");
        getContentPane().add(label117);
        label117.setBounds(130, 165, 70, 16);

        ElemCount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ElemCountKeyPressed(evt);
            }
        });
        getContentPane().add(ElemCount);
        ElemCount.setBounds(200, 150, 40, 25);

        label118.setText("Уровень");
        getContentPane().add(label118);
        label118.setBounds(10, 155, 60, 16);

        label119.setText("Уровень");
        getContentPane().add(label119);
        label119.setBounds(10, 190, 60, 16);

        Level.setEnabled(false);
        Level.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LevelKeyPressed(evt);
            }
        });
        getContentPane().add(Level);
        Level.setBounds(70, 150, 40, 25);

        label120.setText("Групповых");
        getContentPane().add(label120);
        label120.setBounds(130, 150, 70, 16);

        label121.setText("Индекс");
        getContentPane().add(label121);
        label121.setBounds(130, 190, 70, 10);

        label122.setText("формы");
        getContentPane().add(label122);
        label122.setBounds(130, 215, 70, 16);

        GroupIndex.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GroupIndexKeyPressed(evt);
            }
        });
        getContentPane().add(GroupIndex);
        GroupIndex.setBounds(200, 190, 40, 25);

        label123.setText("базовой");
        getContentPane().add(label123);
        label123.setBounds(130, 200, 70, 16);

        BaseForm.setText("Базовая");
        BaseForm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                BaseFormItemStateChanged(evt);
            }
        });
        getContentPane().add(BaseForm);
        BaseForm.setBounds(500, 230, 81, 20);

        LinkForm.setText("Ссылка на базовую");
        LinkForm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                LinkFormItemStateChanged(evt);
            }
        });
        getContentPane().add(LinkForm);
        LinkForm.setBounds(610, 230, 150, 20);

        SnapShot.setText("SnapShot");
        SnapShot.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SnapShotItemStateChanged(evt);
            }
        });
        getContentPane().add(SnapShot);
        SnapShot.setBounds(610, 250, 80, 20);

        jLabel10.setText("Меню+");
        getContentPane().add(jLabel10);
        jLabel10.setBounds(10, 285, 50, 16);

        MenuOnColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuOnColorKeyPressed(evt);
            }
        });
        getContentPane().add(MenuOnColor);
        MenuOnColor.setBounds(60, 280, 60, 25);
        getContentPane().add(MenuOnColorButton);
        MenuOnColorButton.setBounds(120, 280, 25, 25);

        jLabel11.setText("Меню-");
        getContentPane().add(jLabel11);
        jLabel11.setBounds(10, 315, 50, 16);

        MenuOffColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuOffColorKeyPressed(evt);
            }
        });
        getContentPane().add(MenuOffColor);
        MenuOffColor.setBounds(60, 310, 60, 25);
        getContentPane().add(MenuOffColorButton);
        MenuOffColorButton.setBounds(120, 310, 25, 25);

        jLabel15.setText("Ширина");
        getContentPane().add(jLabel15);
        jLabel15.setBounds(160, 315, 50, 16);

        MenuTextColor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuTextColorKeyPressed(evt);
            }
        });
        getContentPane().add(MenuTextColor);
        MenuTextColor.setBounds(210, 280, 60, 25);
        getContentPane().add(MenuTextColorButton);
        MenuTextColorButton.setBounds(270, 280, 25, 25);

        jLabel19.setText("Высота");
        getContentPane().add(jLabel19);
        jLabel19.setBounds(160, 340, 50, 16);

        MenuBold.setText("Bold");
        MenuBold.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MenuBoldItemStateChanged(evt);
            }
        });
        getContentPane().add(MenuBold);
        MenuBold.setBounds(310, 285, 60, 20);

        jLabel20.setText("Текст ");
        getContentPane().add(jLabel20);
        jLabel20.setBounds(160, 285, 50, 16);

        MenuButtonW.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuButtonWKeyPressed(evt);
            }
        });
        getContentPane().add(MenuButtonW);
        MenuButtonW.setBounds(210, 310, 40, 25);

        jLabel21.setText("Шрифт");
        getContentPane().add(jLabel21);
        jLabel21.setBounds(260, 315, 50, 16);

        MenuFontSize.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuFontSizeKeyPressed(evt);
            }
        });
        getContentPane().add(MenuFontSize);
        MenuFontSize.setBounds(310, 310, 40, 25);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Меню");
        getContentPane().add(jLabel22);
        jLabel22.setBounds(60, 340, 50, 16);

        MenuButtonH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenuButtonHKeyPressed(evt);
            }
        });
        getContentPane().add(MenuButtonH);
        MenuButtonH.setBounds(210, 340, 40, 25);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(10, 270, 760, 10);

        label124.setText("Предок");
        getContentPane().add(label124);
        label124.setBounds(250, 150, 60, 16);

        ModuleDX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ModuleDXKeyPressed(evt);
            }
        });
        getContentPane().add(ModuleDX);
        ModuleDX.setBounds(640, 310, 40, 25);

        label125.setText("Модуль");
        getContentPane().add(label125);
        label125.setBounds(370, 285, 60, 16);

        label126.setText("DY");
        getContentPane().add(label126);
        label126.setBounds(690, 310, 30, 16);

        ModuleX0.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ModuleX0KeyPressed(evt);
            }
        });
        getContentPane().add(ModuleX0);
        ModuleX0.setBounds(640, 280, 40, 25);

        ModuleDY.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ModuleDYKeyPressed(evt);
            }
        });
        getContentPane().add(ModuleDY);
        ModuleDY.setBounds(720, 310, 40, 25);

        ModuleY0.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ModuleY0KeyPressed(evt);
            }
        });
        getContentPane().add(ModuleY0);
        ModuleY0.setBounds(720, 280, 40, 25);

        label127.setText("X0");
        getContentPane().add(label127);
        label127.setBounds(620, 280, 30, 16);

        label128.setText("DX");
        getContentPane().add(label128);
        label128.setBounds(620, 310, 30, 16);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        getContentPane().add(jSeparator2);
        jSeparator2.setBounds(360, 280, 10, 70);

        jLabel12.setText("W");
        getContentPane().add(jLabel12);
        jLabel12.setBounds(210, 370, 30, 16);

        ImageH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImageHKeyPressed(evt);
            }
        });
        getContentPane().add(ImageH);
        ImageH.setBounds(260, 390, 40, 25);

        ImageW.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImageWKeyPressed(evt);
            }
        });
        getContentPane().add(ImageW);
        ImageW.setBounds(210, 390, 40, 25);
        getContentPane().add(ImageList);
        ImageList.setBounds(310, 390, 270, 20);

        SetImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SetImage.setBorderPainted(false);
        SetImage.setContentAreaFilled(false);
        SetImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetImageActionPerformed(evt);
            }
        });
        getContentPane().add(SetImage);
        SetImage.setBounds(590, 390, 30, 30);

        UploadImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        UploadImage.setBorderPainted(false);
        UploadImage.setContentAreaFilled(false);
        UploadImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadImageActionPerformed(evt);
            }
        });
        getContentPane().add(UploadImage);
        UploadImage.setBounds(730, 390, 30, 30);
        getContentPane().add(ImageAlias);
        ImageAlias.setBounds(630, 390, 90, 25);

        jLabel16.setText("H");
        getContentPane().add(jLabel16);
        jLabel16.setBounds(260, 370, 30, 16);

        jLabel13.setText("Иконка");
        getContentPane().add(jLabel13);
        jLabel13.setBounds(510, 330, 90, 16);

        ImageX0.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImageX0KeyPressed(evt);
            }
        });
        getContentPane().add(ImageX0);
        ImageX0.setBounds(110, 390, 40, 25);

        ImageY0.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ImageY0KeyPressed(evt);
            }
        });
        getContentPane().add(ImageY0);
        ImageY0.setBounds(160, 390, 40, 25);

        jLabel17.setText("Y0");
        getContentPane().add(jLabel17);
        jLabel17.setBounds(160, 370, 30, 16);

        jLabel18.setText("X0");
        getContentPane().add(jLabel18);
        jLabel18.setBounds(110, 370, 40, 16);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Фон-картинка");
        getContentPane().add(jLabel23);
        jLabel23.setBounds(10, 390, 90, 16);

        jLabel24.setText("Alias");
        getContentPane().add(jLabel24);
        jLabel24.setBounds(630, 420, 80, 16);
        getContentPane().add(IconList);
        IconList.setBounds(310, 350, 270, 20);

        SetIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SetIcon.setBorderPainted(false);
        SetIcon.setContentAreaFilled(false);
        SetIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetIconActionPerformed(evt);
            }
        });
        getContentPane().add(SetIcon);
        SetIcon.setBounds(590, 350, 30, 30);

        UploadIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        UploadIcon.setBorderPainted(false);
        UploadIcon.setContentAreaFilled(false);
        UploadIcon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadIconActionPerformed(evt);
            }
        });
        getContentPane().add(UploadIcon);
        UploadIcon.setBounds(730, 350, 30, 30);
        getContentPane().add(IconAlias);
        IconAlias.setBounds(630, 350, 90, 25);

        DebugForm.setText("Отладочная");
        DebugForm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                DebugFormItemStateChanged(evt);
            }
        });
        getContentPane().add(DebugForm);
        DebugForm.setBounds(610, 210, 130, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EmptyItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_EmptyItemStateChanged
        view.setEmpty(Empty.isSelected());
        back.onEnter("Изменено empty"+view.isEmpty());
    }//GEN-LAST:event_EmptyItemStateChanged

    private void NoMenuItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NoMenuItemStateChanged
        view.setNoMenu(NoMenu.isSelected());
        back.onEnter("Изменено noMenu"+view.isNoMenu());
    }//GEN-LAST:event_NoMenuItemStateChanged

    private void SaveParentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveParentActionPerformed
        view.setParentName(Parents.getSelectedIndex()==0 ? "" : Parents.getSelectedItem());
        back.onEnter("Изменено parentName"+view.getParentName());
    }//GEN-LAST:event_SaveParentActionPerformed

    private void FormLevelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FormLevelKeyPressed
        onKeyPressed("formLevel", FormLevel, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setFormLevel(value);
            }
        });
    }//GEN-LAST:event_FormLevelKeyPressed

    private void SaveAccessLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveAccessLevelActionPerformed
        view.setAccessLevel(access.get(AccessLevel.getSelectedIndex()).value());
        back.onEnter("Изменено accessLevel"+AccessLevel.getSelectedItem());
    }//GEN-LAST:event_SaveAccessLevelActionPerformed

    private void SaveWriteLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveWriteLevelActionPerformed
        view.setWriteLevel(access.get(WriteLevel.getSelectedIndex()).value());
        back.onEnter("Изменено accessLevel"+WriteLevel.getSelectedItem());
    }//GEN-LAST:event_SaveWriteLevelActionPerformed

    private void ModuleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ModuleKeyPressed
        onStringKeyPressed("moduleName", Module, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                view.setModuleName(value);
            }
        });
    }//GEN-LAST:event_ModuleKeyPressed

    private void ImageKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImageKeyPressed
        onStringKeyPressed("image", Image, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                view.setBackImage(value);
            }
        });
    }//GEN-LAST:event_ImageKeyPressed

    private void ElemCountKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ElemCountKeyPressed
        onKeyPressed("elementsCount", ElemCount, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setElementsCount(value);
            }
        });
    }//GEN-LAST:event_ElemCountKeyPressed

    private void LevelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LevelKeyPressed
        onKeyPressed("level", Level, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setLevel(value);
            }
        });
    }//GEN-LAST:event_LevelKeyPressed

    private void GroupIndexKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GroupIndexKeyPressed
        onKeyPressed("baseFormIndex", GroupIndex, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setBaseFormIndex(value);
            }
        });
    }//GEN-LAST:event_GroupIndexKeyPressed

    private void BaseFormItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_BaseFormItemStateChanged
        view.setBaseForm(BaseForm.isSelected());
        back.onEnter("Изменено baseForm"+view.isBaseForm());
    }//GEN-LAST:event_BaseFormItemStateChanged

    private void LinkFormItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_LinkFormItemStateChanged
        view.setLinkForm(LinkForm.isSelected());
        back.onEnter("Изменено linkForm"+view.isLinkForm());
    }//GEN-LAST:event_LinkFormItemStateChanged

    private void SnapShotItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SnapShotItemStateChanged
        view.setSnapShot(SnapShot.isSelected());
        back.onEnter("Изменено snapShot"+SnapShot.isSelected());

    }//GEN-LAST:event_SnapShotItemStateChanged

    private void MenuOnColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuOnColorKeyPressed
        onColorKeyPressed("menuOnColor", MenuOnColor, MenuOnColorButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) { view.setMenuButtonOnColor(value);
            }
        });
    }//GEN-LAST:event_MenuOnColorKeyPressed

    private void MenuOffColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuOffColorKeyPressed
        onColorKeyPressed("menuOffColor", MenuOffColor, MenuOffColorButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) { view.setMenuButtonOffColor(value);
            }
        });
    }//GEN-LAST:event_MenuOffColorKeyPressed

    private void MenuTextColorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuTextColorKeyPressed
        onColorKeyPressed("menuButtonTextColor", MenuTextColor, MenuTextColorButton, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) { view.setMenuButtonTextColor(value);
            }
        });
    }//GEN-LAST:event_MenuTextColorKeyPressed

    private void MenuBoldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MenuBoldItemStateChanged
        if (busy)
        return;
        view.setMenuFontBold(MenuBold.isSelected());
        back.onEnter("Изменено MenuBold="+MenuBold.isSelected());
    }//GEN-LAST:event_MenuBoldItemStateChanged

    private void MenuButtonWKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuButtonWKeyPressed
        onKeyPressed("MenuButtonW", MenuButtonW, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setMenuButtonW(value);
            }
        });
    }//GEN-LAST:event_MenuButtonWKeyPressed

    private void MenuFontSizeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuFontSizeKeyPressed
        onKeyPressed("MenuButtonFontSize", MenuFontSize, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setMenuButtonFontSize(value);
            }
        });
    }//GEN-LAST:event_MenuFontSizeKeyPressed

    private void MenuButtonHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenuButtonHKeyPressed
        onKeyPressed("MenuButtonH", MenuButtonH, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setMenuButtonH(value);
            }
        });
    }//GEN-LAST:event_MenuButtonHKeyPressed

    private void ModuleX0KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ModuleX0KeyPressed
        onKeyPressed("ModuleX0", ModuleX0, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setModuleX0(value);
            }
        });
    }//GEN-LAST:event_ModuleX0KeyPressed

    private void ModuleY0KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ModuleY0KeyPressed
        onKeyPressed("ModuleY0", ModuleY0, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setModuleY0(value);
            }
        });

    }//GEN-LAST:event_ModuleY0KeyPressed

    private void ModuleDXKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ModuleDXKeyPressed
        onKeyPressed("ModuleDX", ModuleDX, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setModuleDX(value);
            }
        });

    }//GEN-LAST:event_ModuleDXKeyPressed

    private void ModuleDYKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ModuleDYKeyPressed
        onKeyPressed("ModuleDY", ModuleDY, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setModuleDY(value);
            }
        });

    }//GEN-LAST:event_ModuleDYKeyPressed

    private void ImageHKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImageHKeyPressed
        onKeyPressed("imageH", ImageH, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setImageH(value);
            }
        });
    }//GEN-LAST:event_ImageHKeyPressed

    private void ImageWKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImageWKeyPressed
        onKeyPressed("imageW", ImageW, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setImageW(value);
            }
        });
    }//GEN-LAST:event_ImageWKeyPressed

    private void SetImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetImageActionPerformed
        if (artifacts.size()==0)
        return;
        int idx = ImageList.getSelectedIndex();
        Artifact art = idx==0 ? new Artifact() : artifacts.get(idx-1);
        view.getPicture().setOidRef(art);
        back.onEnter("Выбрана картинка "+art.getTitle());
    }//GEN-LAST:event_SetImageActionPerformed

    private void UploadImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadImageActionPerformed
        FileNameExt fname = main.getInputFileName("Импорт картинок 2.0", "*.*", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(), Values.ESSImageFileDescription+" "+ImageAlias.getText(), fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact art) {
                view.getPicture().setOidRef(art);
                back.onEnter("Выбрана картинка "+art.getTitle());
                refreshImageList();
                }
            };
    }//GEN-LAST:event_UploadImageActionPerformed

    private void ImageX0KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImageX0KeyPressed
        onKeyPressed("imageX0", ImageX0, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setImageX0(value);
            }
        });
    }//GEN-LAST:event_ImageX0KeyPressed

    private void ImageY0KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ImageY0KeyPressed
        onKeyPressed("imageY0", ImageY0, evt, new I_WizardAction() {
            @Override
            public void onAction(int value) {
                view.setImageY0(value);
            }
        });
    }//GEN-LAST:event_ImageY0KeyPressed

    private void SetIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetIconActionPerformed
        if (artifacts.size()==0)
            return;
        int idx = IconList.getSelectedIndex();
        Artifact art = idx==0 ? new Artifact() : artifacts.get(idx-1);
        view.getIcon().setOidRef(art);
        back.onEnter("Выбрана иконка "+art.getTitle());
    }//GEN-LAST:event_SetIconActionPerformed

    private void UploadIconActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadIconActionPerformed
        FileNameExt fname = main.getInputFileName("Импорт картинок 2.0", "*.*", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(), Values.ESSImageFileDescription+" "+IconAlias.getText(), fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact art) {
                view.getIcon().setOidRef(art);
                back.onEnter("Выбрана иконка "+art.getTitle());
                refreshImageList();
                }
            };
    }//GEN-LAST:event_UploadIconActionPerformed

    private void DebugFormItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_DebugFormItemStateChanged
        if (busy)
            return;
        view.setDebugForm(DebugForm.isSelected());
        back.onEnter("Изменено DebugForm="+DebugForm.isSelected());
    }//GEN-LAST:event_DebugFormItemStateChanged



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Choice AccessLevel;
    private javax.swing.JCheckBox BaseForm;
    private javax.swing.JCheckBox DebugForm;
    private javax.swing.JTextField ElemCount;
    private javax.swing.JCheckBox Empty;
    private javax.swing.JTextField FormLevel;
    private javax.swing.JTextField GroupIndex;
    private javax.swing.JTextField IconAlias;
    private java.awt.Choice IconList;
    private javax.swing.JTextField Image;
    private javax.swing.JTextField ImageAlias;
    private javax.swing.JTextField ImageH;
    private java.awt.Choice ImageList;
    private javax.swing.JTextField ImageW;
    private javax.swing.JTextField ImageX0;
    private javax.swing.JTextField ImageY0;
    private javax.swing.JTextField Level;
    private javax.swing.JCheckBox LinkForm;
    private javax.swing.JCheckBox MenuBold;
    private javax.swing.JTextField MenuButtonH;
    private javax.swing.JTextField MenuButtonW;
    private javax.swing.JTextField MenuFontSize;
    private javax.swing.JTextField MenuOffColor;
    private javax.swing.JButton MenuOffColorButton;
    private javax.swing.JTextField MenuOnColor;
    private javax.swing.JButton MenuOnColorButton;
    private javax.swing.JTextField MenuTextColor;
    private javax.swing.JButton MenuTextColorButton;
    private javax.swing.JTextField Module;
    private javax.swing.JTextField ModuleDX;
    private javax.swing.JTextField ModuleDY;
    private javax.swing.JTextField ModuleX0;
    private javax.swing.JTextField ModuleY0;
    private javax.swing.JCheckBox NoMenu;
    private java.awt.Choice Parents;
    private javax.swing.JButton SaveAccessLevel;
    private javax.swing.JButton SaveParent;
    private javax.swing.JButton SaveWriteLevel;
    private javax.swing.JButton SetIcon;
    private javax.swing.JButton SetImage;
    private javax.swing.JCheckBox SnapShot;
    private java.awt.Choice Types;
    private javax.swing.JButton UploadIcon;
    private javax.swing.JButton UploadImage;
    private java.awt.Choice WriteLevel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel label111;
    private javax.swing.JLabel label112;
    private javax.swing.JLabel label113;
    private javax.swing.JLabel label114;
    private javax.swing.JLabel label115;
    private javax.swing.JLabel label116;
    private javax.swing.JLabel label117;
    private javax.swing.JLabel label118;
    private javax.swing.JLabel label119;
    private javax.swing.JLabel label120;
    private javax.swing.JLabel label121;
    private javax.swing.JLabel label122;
    private javax.swing.JLabel label123;
    private javax.swing.JLabel label124;
    private javax.swing.JLabel label125;
    private javax.swing.JLabel label126;
    private javax.swing.JLabel label127;
    private javax.swing.JLabel label128;
    // End of variables declaration//GEN-END:variables
}
