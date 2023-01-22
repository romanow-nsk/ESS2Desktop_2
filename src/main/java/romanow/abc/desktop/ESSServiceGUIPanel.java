/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.core.ErrorList;
import romanow.abc.core.UniException;
import romanow.abc.core.Utils;
import romanow.abc.core.constants.IntegerList;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.UnitRegisterList;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIArray;
import romanow.abc.core.entity.metadata.view.Meta2GUICollection;
import romanow.abc.core.entity.metadata.view.Meta2GUIReg;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subject2area.ESS2Device;
import romanow.abc.core.entity.subject2area.ESS2Equipment;
import romanow.abc.core.entity.subject2area.ESS2LogUnit;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.view.*;
import romanow.abc.core.utils.OwnDateTime;
import romanow.abc.core.utils.Pair;
import romanow.abc.desktop.module.I_Module;
import romanow.abc.desktop.module.Module;

import retrofit2.Call;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2Base;
import romanow.abc.desktop.view2.View2BaseDesktop;
import romanow.abc.desktop.wizard.WizardBaseView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import static romanow.abc.core.constants.Values.MTViewAndroid;
import static romanow.abc.core.constants.Values.MTViewFullScreen;

/**
 *
 * @author romanow
 */
public class ESSServiceGUIPanel extends ESSBasePanel {
    private JButton OnOff=null;
    private final static String buttonEdit = "/drawable/edit.png";
    private final static String buttonToMain = "/refresh.png";
    private final static String buttonLogout = "/login.png";
    private final static String buttonInfoOn = "/question.png";
    private final static String buttonInfoOff = "/question_gray.png";
    private final static String mainFormName="Главный";
    private final static int levelYCoords[]={0,10,55,620,575};
    public final static int buttonXSize=100;
    public final static int buttonYSize=40;
    public final static int buttonSpace=5;
    private int essOnOffState= Values.ESSStateNone;             // Состояние СНЭ
    private Module module=null;
    private ArrayList<View2Base> guiList = new ArrayList<>();
    private ErrorList errorList = new ErrorList();
    private I_Button logoutCallBack = null;                     // CallBack кнопки выхода
    private OwnDateTime userLoginTime = new OwnDateTime();
    private Meta2GUIForm prevForm=null;                         // Предыдущая форма (асинхр обновление)
    private boolean runtimeEditMode=false;
    private final static int PopupLimitCount = 5;
    private final static int PopupLimitTime = 20;
    private PopupLimiter limiter = new PopupLimiter(PopupLimitCount,PopupLimitTime);
    private ESSGUIEditPanel editPanel = null;
    JButton editView = null;
    private FormContext2 context = new FormContext2(){
        @Override
        public void reOpenForm() {
            repaintView();
            }
        public void openForm(String formName, int mode) {
            Meta2GUIForm form = main2.currentView.getView().getForms().getByTitle(formName);
            if (form==null){
                limiter.popup("Не найдена форма "+formName);
                return;
                }
            openForm(form,mode);
            }
        @Override
        public void openForm(Meta2GUIForm form, int mode) {
            //---------------- Поиск для групповых кнопок -------------------------------------------------
            setBaseForm(form);
            if (form.isLinkForm()){
                String baseFormName = form.getShortName();
                Meta2GUIForm baseForm = main2.currentView.getView().getForms().getByTitle(baseFormName);
                if (baseForm==null){
                    limiter.popup("Не найдена базовая форма "+baseFormName);
                    return;
                    }
                int idx = form.getBaseFormIndex();
                int level = form.getFormLevel();
                if (level!=0 && idx!=-1)            // Индекс для предыдущего уровня их групповой кнопки меню
                    setIndex(level,idx);
                setBaseForm(baseForm);
                }
            //---------------------------------------------------------------------------------------------
            if (main2.manager.getCurrentAccessLevel() > form.getAccessLevel()){
                popup("Недостаточен уровень доступа");
                return;
                }
            Meta2GUIForm prev = getForm();
            if (mode !=FormContext2.ModeForce && prev!=null && form.getLevel()>prev.getLevel()+1){
                popup("Пропущен уровень формы при переходе из "+prev.getTitle()+" в "+form.getTitle());
                }
            setForm(form);
            int level = form.getLevel();
            if (level!=0 && mode==FormContext2.ModeCrearIdx){
                setIndex(level,0);
                setName(level,getForm().getTitle());
                setSize(level,getBaseForm().getElementsCount());
                }
            if (mode==FormContext2.ModeForce){          // Для неиндексируемых форм
                Meta2GUIForm ff=form;
                String menuFormStack[] = getMenuFormStack();
                for(int i=level;i<menuFormStack.length;i++)
                    menuFormStack[i]="";
                for(int ll = level; ll>0;ll--){
                    setIndex(ll,0);
                    setSize(ll,0);
                    menuFormStack[ll-1] = ff.getTitle();
                    ff = ff.getParent();
                    }
                }
            int vv[] = getIdx();
            System.out.println("Стек индексов: "+vv[0]+" "+vv[1]+" "+vv[2]+" "+vv[3]);
            String ss[] = getMenuFormStack();
            System.out.println("Стек форм: "+ss[0]+" "+ss[1]+" "+ss[2]+" "+ss[3]);
            repaintView();
            }

        @Override
        public void repaintView() {
            ESSServiceGUIPanel.this.repaintView();
            }
        @Override
        public void repaintValues() {
            ESSServiceGUIPanel.this.repaintValues();
            }
        @Override
        public void popup(String mes) {
            limiter.popup(mes);
            }
    };
    //------------------------------ Цикл рендеринга ------------------------------------------------------
    private Thread guiLoop = null;
    private boolean shutDown=false;
    private int renderSeqNum=0;                                         // Последовательный номер рендеринга
    private boolean renderingOn=false;
    private void setRenderingOnOff(boolean vv){
        renderingOn = vv;
        }
    //private boolean repaintValuesOn=false;                           // Обновление данных
    //private boolean repaintBusy=false;                               // Обновление формы
    @Override
    public synchronized void shutDown() {
        shutDown = true;
        wokeUp();}
    private synchronized void wokeUp(){
        guiLoop.interrupt(); }
    private void createLoopThread() {
        shutDown=false;
        guiLoop = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!shutDown){
                    long tt =  new OwnDateTime().timeInMS();
                    try {
                        Thread.sleep(((WorkSettings) main.workSettings).getGUIrefreshPeriod() * 1000);
                        } catch (InterruptedException e) {
                            System.out.println("Разбудили: " + (new OwnDateTime().timeInMS()-tt));
                            }
                        if (shutDown){
                            repaintOff();
                            return;
                            }
                        long sec = (new OwnDateTime().timeInMS() - userLoginTime.timeInMS()) / 1000;
                        if (logoutCallBack != null && sec > ((WorkSettings) main.workSettings).getUserSilenceTime() * 60) {
                            shutDown();
                            logoutCallBack.onPush();
                            return;
                            }
                        if (!renderingOn)
                            continue;
                        java.awt.EventQueue.invokeLater(new Runnable() {
                            public void run() {
                                repaintValues();
                                }
                            });
                        }
                    }
                });
        guiLoop.start();
        }
    //-----------------------------------------------------------------------------------------------------
    public void setLogoutCallBack(I_Button logoutCallBack) {
        this.logoutCallBack = logoutCallBack; }
    public boolean isMainMode(){ return false; }
    public boolean isESSMode(){ return true; }
    public ESSServiceGUIPanel() {
        initComponents();
        }
    public ESSServiceGUIPanel(ScreenMode screenMode) {
        initComponents();
        context.setScreen(screenMode);
        }
    public void initPanel(MainBaseFrame main){
        super.initPanel(main);
        ESSClient main0 = (ESSClient)main;
        context.setLocalUser(main0.isLocalUser());
        context.setSuperUser(main0.loginUser().getTypeId()==Values.UserSuperAdminType);
        context.setManager(main0.manager);
        context.setService(main0.service);
        context.setService2(main2.service2);
        context.setToken(main0.debugToken);
        context.setValid(true);
        context.setPlatformName("Desktop");
        context.setMain(main);
        userLoginTime = new OwnDateTime();
        createLoopThread();
        }

    public boolean setMainForm(){
        if (context.getForm()==null){
            Meta2GUIForm form = main2.currentView.getView().getForms().getByTitle(mainFormName);
            context.setForm(form);
            context.setBaseForm(form);
            }
        if (context.getForm()==null){
            popup("Не найдена главная форма");
            }
        return context.getForm()!=null;
        }
    private void setForm(Meta2GUIForm aaa){
        context.setForm(aaa);
        repaintView();
        }
    private I_GUI2Event retryPaintValues = new I_GUI2Event(){
         @Override
         public void onEnter(View2Base element, int iParam, String sParam) {
             guiLoop.interrupt();
             userLoginTime = new OwnDateTime();
             repaintView();
            }
        };
    public void testESSOnOffState(){
        //---------------- TODO --------------- главная кнопка ---------------------------------------------
        /*
         if (!main2.plm.isReady())
             essOnOffState = Values.ESSStateNone;
         else
            try {
                boolean bb = (main2.plm.readRegister("",0,Values.ESSStateRegister) & Values.ESSStateOnOffBit) !=0;
                essOnOffState = bb ? Values.ESSStateOn : Values.ESSStateOff;
                } catch (UniException ee){
                    essOnOffState = Values.ESSStateNone;
                    popup("Не читается регистр состояния ПЛК "+Values.ESSStateRegister+"\n"+ee.getSysMessage());
                    }
         OnOff.setIcon(new javax.swing.ImageIcon(getClass().getResource(Values.ESSStateIcon[essOnOffState])));
         */
         }
    //---------------------------------------------------------------------------------------
    @Override
    public void paintComponent(Graphics g){
        ScreenMode screen = context.getScreen();
        Meta2GUIForm form = context.getForm();
        if (form==null || form.getBackImage().length()==0){
            g.setColor(new Color(240,240,240));
            g.fillRect( 0, 0, screen.ScreenW, screen.ScreenH);
            return;
            }
        Image im = null;
        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/"+form.getBackImage()));
            im = icon.getImage();
            } catch (Exception e) {
                System.out.println(e.toString());
                }
        g.drawImage(im, 0, 0, screen.ScreenW, screen.ScreenH,null);
        }
    //---------------------------------------------------------------------------------------
    public synchronized void repaintOff(){
        setRenderingOnOff(false);
        removeAll();
        context.setForm(null);
        repaint();
        }
    //---------------------------------------------------------------------------------------
    private I_Value<String> onClose = new I_Value<String>() {               // Событие - закрытие визарда - обновитьь ЧМИ
        @Override
        public void onEnter(String value) {}
        };
    private I_Value<String> onChange = new I_Value<String>() {               // Событие - Изменение элемента формы
        @Override
        public void onEnter(String value) {
            context.getMain().sendEventPanel(BasePanel.EventRuntimeEdited,0,0,value);
            }
        };
    //------------------------------------------------------------------------------------------
    public void repaintMenu(boolean phoneMode){
        Meta2GUIView currentView = main2.currentView.getView();
        Meta2EntityList<Meta2GUIForm> formList = currentView.getForms();
        Meta2GUIForm form = context.getForm();
        Meta2GUIForm baseForm = context.getBaseForm();
        int level = context.getForm().getLevel();
        //-----------------------------------------------------------------------------------
        if (context.getForm().getTitle().equals(mainFormName)){
            TextField userTitle = new TextField();
            int  access = context.getManager().getCurrentAccessLevel();
            String ss = "  "+context.getManager().getUser().getTitle()+" ["+Values.title("AccessLevel",access)+"] ";
            userTitle.setText(ss);
            userTitle.setBounds(context.x(50),context.y(610),
                    context.x(400),context.y(25));
            userTitle.setEnabled(false);
            userTitle.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
            add(userTitle);
            }
        //-----------------------------------------------------------------------------------
        Meta2GUIForm ff = baseForm;
        level = ff.getLevel();
        if (phoneMode){
            String currentName = baseForm.getTitle();
            final ArrayList<Meta2GUIForm> childs = new ArrayList<>();
            final ArrayList<String> names = new ArrayList<>();
            if (!currentName.equals(mainFormName)){
                final Meta2GUIForm parent = baseForm.getParent();
                JButton bb = new MultiTextButton(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
                parent.setButton(bb);
                bb.setText(parent.getTitle().replace("_",""));
                bb.setBackground(new Color(currentView.getMenuButtonOnColor()));
                bb.setForeground(new Color(currentView.getTextColor()));
                int buttonSize = (parent.getButtonSize()==0 ? 100 : parent.getButtonSize())*buttonXSize/100;
                bb.setBounds(
                    context.x(context.getScreen().ScreenW - 40 - buttonSize),
                    context.y(60),
                    context.x(buttonSize),
                    context.y(buttonYSize));
                bb.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        context.openForm(parent,FormContext2.ModeCrearIdx);
                        }
                    });
                add(bb);
                }
            final Meta2GUIForm current = baseForm;
            JButton bb = new MultiTextButton(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
            current.setButton(bb);
            bb.setText(current.getTitle().replace("_",""));
            bb.setBackground(new Color(currentView.getMenuButtonOffColor()));
            bb.setForeground(new Color(currentView.getTextColor()));
            int buttonSize = (current.getButtonSize()==0 ? 100 : current.getButtonSize())*buttonXSize/100;
            bb.setBounds(
                    context.x(context.getScreen().ScreenW - 40 - buttonSize),
                    context.y(60 + buttonYSize + buttonSpace),
                    context.x(buttonSize),
                    context.y(buttonYSize));
            bb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    context.openForm(current,FormContext2.ModeCrearIdx);
                    }
                });
            add(bb);
            }
        while (true) {
            int baseXY = 60 + (phoneMode ? 2*(buttonYSize + buttonSpace) : 0);
            String currentName = ff.getTitle();
            int ii = 0;
            for (Meta2GUIForm next : formList.getList()) {
                if (!next.getParentName().equals(currentName))
                    continue;
                if (next.isBaseForm())
                    continue;
                JButton bb = new MultiTextButton(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
                bb.setText(next.getTitle().replace("_",""));
                bb.setVisible(!next.isNoMenu());
                next.setButton(bb);
                bb.setBackground(new Color(currentView.getMenuButtonOffColor()));
                bb.setForeground(new Color(currentView.getTextColor()));
                int buttonSize = (next.getButtonSize()==0 ? 100 : next.getButtonSize())*buttonXSize/100;
                if (phoneMode) {
                    bb.setBounds(
                        context.x(context.getScreen().ScreenW - 40 - buttonSize),
                        context.y(baseXY),
                        context.x(buttonSize),
                        context.y(buttonYSize));
                    baseXY += buttonYSize + buttonSpace;
                    }
                else{
                    bb.setBounds(
                        context.x(baseXY),
                        context.y(levelYCoords[level + 1]),
                        context.x(buttonSize),
                        context.y(buttonYSize));
                    baseXY += buttonSize + buttonSpace;
                    }
                final Meta2GUIForm zz = next;
                final boolean denied = main2.manager.getCurrentAccessLevel() > zz.getAccessLevel();
                if (denied)
                    bb.setBackground(new Color(Values.AccessDisableColor));
                bb.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3 && context.isRuntimeEditMode()){
                            String ss = WizardBaseView.openWizardByType(next, null, onClose, onChange);
                            if (ss!=null)
                                new Message(300,300,ss,Values.PopupMessageDelay);
                            }
                        else
                            context.openForm(zz,FormContext2.ModeCrearIdx);
                        }
                    });
                //--------------------------------------------------------------------------
                /*
                int regNum = next.getRegNum();          // TODO Сервисный режим ??????
                next.setColoured(false);
                if (regNum!=0){
                    try {
                        int vv = main2.plm.readRegister("",0,regNum);
                        if (((vv>>next.getRegBit()) & 1)!=0){
                            next.setColoured(true);
                            bb.setBackground(new Color(next.getColor()));
                            }
                        } catch (UniException e) {}
                    }
                */
                add(bb);
                ii++;
                }
            if (level == 0) break;
            ff = formList.getByTitle(ff.getParentName());
            if (form == null) {
                popup("Не найден предок для формы " + form.getTitle());
                break;
                    }
            level--;
            if (phoneMode)
                break;
            }
        level = baseForm.getLevel();
        for(int ii=1;ii<=level;ii++){
            String formName = context.getName(ii);
            ff = formList.getByTitle(formName);
            if (ff==null){
                popup("Не найдена форма " + formName+" в стеке");
                continue;
                }
            if (!ff.isColoured()){
                JButton button = (JButton) ff.getButton();
                button.setBackground(new Color(currentView.getMenuButtonOnColor()));
                button.setForeground(Color.black);
                }
            }
        }
    //-------------------------------------------------------------------------------------------
    public synchronized void  repaintView() {
        setRenderingOnOff(true);
        Meta2GUIView currentView = main2.currentView.getView();
        Meta2EntityList<Meta2GUIForm> formList = currentView.getForms();
        this.setBackground(new Color(currentView.getBackColor()));
        removeAll();
        errorList.clear();
        if (main2.currentView == null || !context.isValid()) {
            return;
            }
        if (context.getForm() == null )
            if (!setMainForm()){
                //repaintBusy = false;
                return;
                }
        Meta2GUIForm fff = context.getForm();
        if (fff.isEmpty()){         // пропустить пустые экраны
            while (fff.isEmpty()){
                if (fff.getChilds().size()==0){
                    return;
                    }
                fff = fff.getChilds().get(0);
                }
            context.openForm(fff,FormContext2.ModeForce);
            return;
            }
        repaint();
        for(ESS2Device device : main2.deployed.getDevices()){          // Очистить кэши
            device.clearCash();
            }
        //----------------------------------------------------------------------------------------
        repaintMenu(currentView.getXmlType() == MTViewAndroid);
        //----------------------------------------------------------------------------------------
        OnOff = new JButton();
        OnOff.setBorderPainted(false);
        OnOff.setContentAreaFilled(false);
        OnOff.addActionListener(new ActionListener() {      //???????????????????????????????????????????????
            @Override
            public void actionPerformed(ActionEvent e) {
                testESSOnOffState();
                if (essOnOffState==Values.ESSStateNone)
                    return;
                if (essOnOffState==Values.ESSStateOff && main2.manager.getCurrentAccessLevel() > Values.AccessLevel2){
                    popup("Недостаточен уровень доступа");
                    return;
                    }
                new OK(200, 200, essOnOffState == Values.ESSStateOff ? "Включить СНЭ" : "Выключить СНЭ", new I_Button() {
                    @Override
                    public void onPush() {
                        try {
                            //---------------- TODO --- ГЛАВНАЯ КНОПКА ------------------------------------------
                            main2.plm.writeRegister("",0,Values.LocalCommandRegister, essOnOffState==Values.ESSStateOff ?
                                    Values.ESSOnCommand : Values.ESSOffCommand);
                            testESSOnOffState();
                        }catch (UniException ee){
                            limiter.popup("Не выполняется команда ПЛК "+Values.ESSStateRegister+"\n"+ee.getSysMessage());
                            }
                        }
                    });
                }
            });
        add(OnOff);
        OnOff.setBounds(context.x(5), context.y(10), context.x(50), context.y(50));
        testESSOnOffState();
        JButton toMain = new JButton();
        toMain.setIcon(new javax.swing.ImageIcon(getClass().getResource(buttonToMain))); // NOI18N
        //toMain.setBorderPainted(false);
        //toMain.setContentAreaFilled(false);
        toMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.openForm(mainFormName);
            }
        });
        add(toMain);
        toMain.setBounds(context.x(870), context.y(620), context.x(40), context.y(40));
        //-------------------------------------------------------------------------------------
        editView = new JButton();
        editView.setIcon(new javax.swing.ImageIcon(getClass().getResource(buttonEdit))); // NOI18N
        //toMain.setBorderPainted(false);
        //toMain.setContentAreaFilled(false);
        editView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editPanel = new ESSGUIEditPanel(ESSServiceGUIPanel.this, context, new I_Button() {
                    @Override
                    public void onPush() {
                        editPanel = null;
                        }
                    });
                /*
                final Meta2GUI view = context.getSelectedView();        // Выбранный элемент в контексте рендеринга
                if (!context.isRuntimeEditMode() || view==null){        // Элемент выбран и редактирование "на лету"
                    editView.setVisible(false);
                    return;
                }
                new OK(200,200,"Вставить элемент: "+view.getFullTitle(), new I_Button() {
                    @Override
                    public void onPush() {
                        Meta2GUI copy = null;
                        try {
                            copy = view.getClass().newInstance();       // Клонировать элемент
                        } catch (Exception ee){
                            editView.setVisible(false);
                            popup("Ошибка создания клона для  "+view.getFullTitle());
                            return;
                        }
                        copy.cloneGUIData(view);                        // Метод клонирования содержимого для GUI
                        Meta2GUIForm form1 = context.getForm();
                        form1.getControls().add(copy);                  // Добавить в список элементов управления GUI формы
                        repaintView();                                  // Разослать уведомление (форма редактирования)
                        main.sendEventPanel(BasePanel.EventRuntimeEdited,0,0,"Добавлен "+form1.getTitle()+": "+copy.getFullTitle());
                        }
                    });

                */
                }
            });
        add(editView);
        editView.setBounds(context.x(820), context.y(620), context.x(40), context.y(40));
        editView.setVisible(context.isRuntimeEditMode());
        //-----------------------------------------------------------------------------------
        JButton logout = new JButton();
        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource(buttonLogout))); // NOI18N
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (logoutCallBack!=null){
                    shutDown();
                    logoutCallBack.onPush();
                    }
                }
            });
        add(logout);
        logout.setBounds(context.x(870), context.y(10), context.x(40), context.y(40));
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
        add(info);
        info.setBounds(context.x(820), context.y(10), context.x(40), context.y(40));
        //-----------------------------------------------------------------------------------------
        Meta2GUIForm baseForm = context.getBaseForm();
        guiList.clear();
        int idx[] = new int[Values.FormStackSize];
        renderGuiElement(baseForm.getControls(),0,0,0,idx);  // Рекурсивный рендеринг для всех уровней
        for(View2Base view :  guiList)
            ((View2BaseDesktop)view).addToPanel(this);                      // Добавить на панель
        //----------------------------------------------------------------------------------
        module=null;
        if (!context.getForm().noModule()){
            Pair<String,Object> res = context.getForm().createModule();
            if (res.o1!=null){
                limiter.popup(res.o1);
                }
            else{
                if (!(res.o2 instanceof I_Module)){
                    limiter.popup(context.getForm().getModuleName() +" не модуль ЧМИ");
                    }
                else{
                    module = (Module)res.o2;
                    module.init(main,this,main.service,main2.service2,main.debugToken, context.getForm(),context);
                    }
                }
            }
        //------------------------------------------------------------------------------------
        //repaintBusy=false;
        if (!errorList.valid()){
            limiter.popup(""+errorList.getErrCount()+ " ошибок рендеринга");
            System.out.println(errorList);
            }
        wokeUp();
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
        }

    @Override
    public void eventPanel(int code, int par1, long par2, String par3,Object oo) {
        if (code==EventRefreshSettings){
            refresh();
            main.sendEventPanel(EventRefreshSettingsDone,0,0,"");
            }
        if (code==EventPLMOn){
            context.setView(main2.currentView.getView());
            context.setMainServerNodeId(main2.mainServerNodeId);
            limiter.reset();
            repaintView();
            main.panelToFront(this);
            System.out.println(par3);
            }
        if (code==EventPLMOff){
            repaintOff();
            }
        if (code==EventGUIToFront && renderingOn){
            repaintView();
            }
        if (code==EventRuntimeEditMode){
            runtimeEditMode = par1!=0;
            context.setRuntimeEditMode(runtimeEditMode);
            if (par1==0)
                editView.setVisible(false);
            }
        if (code==EventRuntimeSelected){
            editView.setVisible(true);
            }
        }
    //---------------------------------------------------------------------------------------------
    public int []getRegisterData(ESS2Device device,Meta2RegLink link, int unitIdx,int offset){
        int regNumFull = link.getRegNum()+offset;
        int regSize = link.getRegister().size16Bit();
        int data[] = new int[regSize];
        boolean good=true;
        for(int i=0;i<regSize;i++){
            Integer vv = device.getValue(unitIdx,regNumFull+i);
            if (vv==null){
                limiter.popup("Не найден регистр в ответе сервера "+(regNumFull+i));
                good=false;
                break;
                }
            data[i]=vv;
            }
        return good ? data : null;
        }
    public void repaintValuesOnAnswer(ESS2Device device, int unitIdx,IntegerList values,Meta2GUIForm currentForm) throws UniException {
        if (currentForm!=context.getForm())
            return;
        device.clearCash(unitIdx);
        for(int i=0;i<values.size();i+=2)
            device.putValue(unitIdx,values.get(i),values.get(i+1));
        for (View2Base element : guiList){
            Meta2RegLink link = element.getRegLink();
            if (link==null)
                continue;
            if (!element.getDevice().getShortName().equals(device.getShortName()))        // Пропустить из чужого мапа
                continue;
            if (element.getDevUnit()!=unitIdx)      // Пропустить чужой физический Unit
                continue;
            int data[] = getRegisterData(device,link,unitIdx,element.getRegOffset());
            if (data==null)
                continue;
            element.putValue(data);
            Meta2RegLink links[] = element.getSettingsLinks();
            for(int i=0;i<links.length;i++){
                data = getRegisterData(device,links[i],unitIdx,0);
                if (data==null)
                    continue;
                if (data.length>4){
                    limiter.popup("Ошибка размерности доп. регистра "+links[i].getRegNum());
                    continue;
                    }
                element.putValue(links[i].getRegister(),View2Base.toOneWord(data),i);
                }
            links = element.getDataLinks();
            for(int i=0;i<links.length;i++){
                data = getRegisterData(device,links[i],unitIdx,0);
                if (data==null)
                    continue;
                if (data.length>4){
                    limiter.popup("Ошибка размерности доп. регистра "+links[i].getRegNum());
                    continue;
                    }
                element.putValue(links[i].getRegister(),View2Base.toOneWord(data),i);
                }
            element.repaintValues();
            }
        if (module!=null)
            module.repaintValues();
        revalidate();
        }
    //public synchronized void repaintValues(){       // По физическим устройствам
        //if (repaintBusy)
        //    return;
        //-------------- Смена формы ??????????????????????????
        //if (context.getForm()!=prevForm){
        //    prevForm = context.getForm();
        //    return;
        //    }
    public void putOneLinkRegister(View2Base element,Meta2RegLink link,int offset){
        int regNumFull = link.getRegNum()+offset;
        int regSize = link.getRegister().size16Bit();
        for(int i=0;i<regSize;i++)
            element.getDevice().putValue(element.getDevUnit(),regNumFull+i,0);     // Регистр со смещением
        }
    public synchronized void repaintValues(){       // По физическим устройствам
        if (!renderingOn)
            return;
        testESSOnOffState();
        if (module!=null)
            module.repaintValues();
        for (View2Base element : guiList){
            element.repaintBefore();
            }
        for (View2Base element : guiList){
            Meta2RegLink link = element.getRegLink();
            if (link==null)
                continue;
            putOneLinkRegister(element,link,element.getRegOffset());
            //ESS2Device device = element.getDevice();
            //int regNumFull = link.getRegNum()+element.getRegOffset();                   // Двойные регистры
            //int regSize = link.getRegister().size16Bit();
            //for(int i=0;i<regSize;i++)
            //    device.putValue(element.getDevUnit(),regNumFull+i,0);     // Регистр со смещением
            Meta2RegLink vv[] = element.getSettingsLinks();
            //---------- Вспомогательные регистры с того же девайса и юнита, что и основной, без смещения
            for(Meta2RegLink link2 : vv){
                putOneLinkRegister(element,link2,0);
                //regNumFull = link2.getRegNum();
                //regSize = link.getRegister().size16Bit();
                //for(int i=0;i<regSize;i++)
                //    device.putValue(element.getDevUnit(),regNumFull+i,0); // Регистр БЕЗ СМЕЩЕНИЯ
                }
            vv = element.getDataLinks();
            for(Meta2RegLink link2 : vv){
                putOneLinkRegister(element,link2,0);
                //regNumFull = link2.getRegNum();
                //regSize = link.getRegister().size16Bit();
                //for(int i=0;i<regSize;i++)
                //    device.putValue(element.getDevUnit(),regNumFull+i,0); // Регистр БЕЗ СМЕЩЕНИЯ
                }
            }
        renderSeqNum++;             // Установить след. номер запроса
        for(ESS2Device device : main2.deployed.getDevices()){
            ArrayList<UnitRegisterList> list2 = device.createList(false);
                for(UnitRegisterList list : list2){
                    System.out.println(device.getShortName()+"["+list.getUnitIdx()+"]="+list.size());
                    readPLMRegistersAsync(device,list,context.getForm());               // Асинхронная версия
                    }
                }
        }
    //------------------------------------------------------------------------
    private volatile boolean asyncBusy=false;
    private synchronized void readPLMRegistersAsync(final ESS2Device device,final UnitRegisterList list, final Meta2GUIForm currentForm){
        //if (asyncBusy)
        //    return;
        //asyncBusy=true;
        if (!renderingOn)
            return;
        final int currentRenderSeqNum = renderSeqNum;
        final long tt =  new OwnDateTime().timeInMS();
            new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final long nodeid = context.getMainServerNodeId();
                    final IntegerList values = new APICall2<IntegerList>() {
                        @Override
                        public Call apiFun() {
                            if (nodeid!=0)
                                return main2.service2.readESS2NodeSnapShotValues(main.debugToken, nodeid,device.getShortName(),list);
                            if (currentForm.isSnapShot())
                                return main2.service2.readESS2SnapShotValues(main.debugToken, device.getShortName(),list);
                            else
                                return main2.service2.readESS2RegistersValues(main.debugToken, device.getShortName(),list);
                            }
                    }.call(main);
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            asyncBusy=false;
                            synchronized (ESSServiceGUIPanel.this){
                                if (renderSeqNum!=currentRenderSeqNum){      // Совпадение посл.номера запроса
                                    System.out.println("Несовпадение номеров запроса (трассировка) "+renderSeqNum+" "+currentRenderSeqNum);
                                    return;
                                    }
                                }
                            try {
                                repaintValuesOnAnswer(device, list.getUnitIdx(), values,currentForm);
                                System.out.println("Ждали: " + (new OwnDateTime().timeInMS()-tt));
                            } catch (UniException e) {
                                    limiter.popup("Ошибка GUI: "+e.getSysMessage());
                                    }
                        }
                    });

                } catch (final UniException ee){
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            asyncBusy=false;
                            limiter.popup("Ошибка сервера: "+ee.getSysMessage());
                            }
                        });
                     }
            }
        }).start();
    }

    //----------------------- Рендеринг элементов ----------------------------------------
    public void renderGuiElement(Meta2GUI meta,int baseX, int baseY, int groupLevel, int groupIndexes[]){
        if (meta instanceof Meta2GUIArray){
            Meta2GUIArray array = (Meta2GUIArray) meta;
            Meta2GUI elem = array.getElem();
            for(int i=0;i<array.getSize();i++){
                int cIdx = i;
                groupIndexes[groupLevel] = cIdx;        // Относительные индексы в группе (уровень 1 - level=0)
                int xx = baseX + (array.getDxy() < 0 ? -array.getDxy()*i : 0);
                int yy = baseY + (array.getDxy() > 0 ? array.getDxy()*i : 0);
                renderGuiElement(elem, xx,yy,groupLevel+1,groupIndexes);
                }
            }
        else
        if (meta instanceof Meta2GUICollection){
            Meta2GUICollection collection = (Meta2GUICollection) meta;
            for(Meta2GUI elem : collection.getList()){
                renderGuiElement(elem, baseX,baseY,groupLevel,groupIndexes);
                }
            }
        else{
            //--------------- TODO ------------ вроде не надо --------------------------------------------
            //int formLevel = context.getForm().getLevel();
            //if (groupLevel > formLevel){
            //    errorList.addError("Уровень элемента "+meta.getFullTitle()+"="+groupLevel+" больше уровня формы "+context.getForm().getFullTitle()+"="+formLevel);
            //    return;
            //    }
            View2Base newElem = View2Base.createGUIElement(errorList,context.getPlatformName(),meta);
            if (newElem==null)
                return;
            try {
                String mes = newElem.setParams(context, main2.deployed, meta, retryPaintValues);
                if (mes!=null){
                    errorList.addError("Ошибка настройки элемента "+newElem.getTitle()+"\n"+ mes);
                    return;
                    }
                } catch (Exception ee){
                    errorList.addError("Ошибка настройки элемента "+newElem.getTitle()+"\n"+ Utils.createFatalMessage(ee));
                    return;
                    }
            newElem.setDxOffset(baseX);
            newElem.setDyOffset(baseY);
            newElem.setGroupLevel(groupLevel);
            int vv[] = newElem.getGroupIndexes();
            for(int i=0;i<Values.FormStackSize;i++)
                vv[i]=groupIndexes[i];
            if (meta instanceof Meta2GUIReg) {          // Для регистров - индексы массивов по индесам View
                Meta2GUIReg regGUI = (Meta2GUIReg)meta;
                Meta2RegLink link = regGUI.getRegLink();
                Meta2Register register = link.getRegister();
                String equipName= regGUI.getEquipName();
                ESS2Equipment equipment = main2.deployed.getEquipments().getByName(equipName);
                if (equipment==null){
                    errorList.addError("Не найдено оборудование "+equipName+" для "+regGUI.getFullTitle());
                    return;
                    }
                int connectorsSize = equipment.getLogUnits().size();
                    if (connectorsSize==0){
                        errorList.addError("Нет устройств для "+equipName);
                        return;
                        }
                //------------- Подсчет смещения регистров, индексов контроллеров и Unit  ---------------------
                int treeLevel = register.getArrayLevel()-1;         // Кол-во массивов в дереве Meta-элементов (+device+units) -1
                int grlevel = groupLevel-1;                         // Кол-во массивов в форме
                int stacklevel = context.getForm().getLevel()-1;    // Вершина стека индексов форм для тек. уровня
                if (!link.isOwnUnit() && treeLevel > stacklevel){
                    errorList.addError("Уровень массива мета-данных > уровня формы "+
                            equipName+" для "+regGUI.getFullTitle()+"="+(treeLevel+1)+" "+
                            context.getForm().getTitle()+"="+(stacklevel+1));
                    return;
                    }
                int regOffset=0;
                stacklevel = treeLevel;
                if (link.isOwnUnit()){      // Unit задан явно - не групповые = явно перечисленные
                    newElem.setRegOffset(0);
                    newElem.setUnitIdx(link.getUnitIdx());
                    }
                else{                       // Иначе генерация по массивам
                    for (Meta2Entity cc = register.getHigh(); cc != null; cc = cc.getHigh()) {
                        if (!(cc instanceof Meta2Array))
                            continue;
                        Meta2Array array = (Meta2Array) cc;
                        int elemIdx = context.getIndex(stacklevel + 1);
                        elemIdx += grlevel < 0 ? 0 : groupIndexes[grlevel];
                        if (elemIdx >= array.getSize())          // Выход за пределы массива
                            return;
                        switch (array.getArrayType()) {
                            case Values.ArrayTypeModbus:
                                regOffset += array.getStep() * elemIdx;
                                break;
                            case Values.ArrayTypeUnit:
                                if (!regGUI.getRegLink().isOwnUnit())
                                    newElem.setUnitIdx(elemIdx);
                                break;
                            }
                        grlevel--;
                        stacklevel--;
                        }
                    newElem.setRegOffset(regOffset);
                    }
                if (newElem.getUnitIdx() >= connectorsSize){
                    errorList.addError("Индекс Unit "+newElem.getUnitIdx()+" превышен  "+equipName+" для "+regGUI.getFullTitle());
                    return;
                    }
                newElem.setDevice(equipment.getLogUnits().get(newElem.getUnitIdx()).getDevice().getRef());
                newElem.setDevUnit(equipment.getLogUnits().get(newElem.getUnitIdx()).getUnit());        // Физический Unit
                }
            guiList.add(newElem);
            }
        }
    //------------------------------------------------------------------------------------------------------------------
    class PopupLimiter{
        long timeStamp[];
        int idx=0;
        int delay;
        void reset(){
            for(int i=0;i<timeStamp.length;i++)
                timeStamp[i]=0;
            idx=0;
            }
        PopupLimiter(int size, int delay0){
            timeStamp = new long[size];
            delay = delay0;
            reset();
            }
        void popup(String ss){
            ESSServiceGUIPanel.this.popup(ss);
            long tt = System.currentTimeMillis();
            timeStamp[idx++] = tt;
            if (idx==timeStamp.length)
                idx=0;
            long tt2=timeStamp[idx];
            if (tt2==0)
                return;
           System.out.println((tt-tt2)/1000);
           if ((tt-tt2)/1000 < delay)
                main.sendEvent(EventPLMOffForce,0);
        }
    }
    //------------------------------------------------------------------------------------------------------------------
    public static int getArrayLevel(Meta2GUI meta){
        int arrayLevel=0;

        return arrayLevel;
        }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
