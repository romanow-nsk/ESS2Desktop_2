/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import lombok.Getter;
import lombok.Setter;
import romanow.abc.core.ErrorList;
import romanow.abc.core.UniException;
import romanow.abc.core.Utils;
import romanow.abc.core.constants.IntegerList;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.EntityRefList;
import romanow.abc.core.entity.UnitRegisterList;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIArray;
import romanow.abc.core.entity.metadata.view.Meta2GUICollection;
import romanow.abc.core.entity.metadata.view.Meta2GUIReg;
import romanow.abc.core.entity.subject2area.*;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.screen.ScreenMode;
import romanow.abc.desktop.view.*;
import romanow.abc.core.utils.OwnDateTime;
import romanow.abc.core.utils.Pair;
import romanow.abc.desktop.module.I_Module;
import romanow.abc.desktop.module.Module;

import retrofit2.Call;
import romanow.abc.desktop.view2.*;
import romanow.abc.desktop.wizard.WizardBaseView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import static romanow.abc.core.constants.Values.*;

/**
 *
 * @author romanow
 */
public class ESSServiceGUIPanel extends ESSBasePanel {
    @Getter @Setter private boolean secondPanel=false;
    private JButton OnOff=null;
    private final static String buttonEdit = "/drawable/add.png";
    private final static String buttonToMain = "/refresh.png";
    private final static String buttonLogout = "/login.png";
    private final static String buttonInfoOn = "/question.png";
    private final static String buttonInfoOff = "/question_gray.png";
    //--------------------------------- Данные для меню
    //          1
    //          2
    // 7 8     коды    6 5
    //          4
    //          3
    //-----------------------------------------------------------------------------------------
    private final static int maxMenuLevels=5;
    private int menuModes[] = new int[maxMenuLevels];
    private final static int levelYCoords[]={0, MenuButtonMargin,0,0,0,80,80,80,80};
    private final static int levelXCoords[]={0,MenuButtonMargin,MenuButtonMargin,MenuButtonMargin,MenuButtonMargin,
            0,0,MenuButtonMargin,0};
    private final static boolean levelHoriz[] ={true,true,true,true,true,false,false,false,false};
    //-----------------------------------------------------------------
    private int essOnOffState= Values.ESSStateNone;             // Состояние СНЭ
    private Module module=null;
    private ArrayList<View2Base> guiList = new ArrayList<>();
    private ErrorList errorList = new ErrorList();
    private I_Button logoutCallBack = null;                     // CallBack кнопки выхода
    private OwnDateTime userLoginTime = new OwnDateTime();
    private Meta2GUIForm prevForm=null;                         // Предыдущая форма (асинхр обновление)
    private boolean runtimeEditMode=false;
    private boolean runtimeOnlyView=false;
    private final static int PopupLimitCount = 5;
    private final static int PopupLimitTime = 20;
    private PopupLimiter limiter = new PopupLimiter(PopupLimitCount,PopupLimitTime);
    private Meta2GUI selected=null;
    private volatile int asyncCount=0;                          // Счетчик асинхронных вызовов
    JButton insertSelected = null;
    private FormContext2 context = new FormContext2(new I_ContextBack() {
        @Override
        public void forceRepaint(){
            if (guiLoop!=null)
                guiLoop.interrupt();
            }
        @Override
        public void popup(String ss) {
            limiter.popup(ss);
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
        public int getAcccessLevel() {
            return main2.manager.getCurrentAccessLevel();
            }
        });
    //------------------------------ Цикл рендеринга ------------------------------------------------------
    private Thread guiLoop = null;
    private boolean shutDown=false;
    private int renderSeqNum=0;                                         // Последовательный номер рендеринга
    private boolean renderingOn=false;
    private void setRenderingOnOff(boolean vv){
        renderingOn = vv;
        selected=null;
        if (insertSelected!=null)
            insertSelected.setVisible(false);
        if (renderingOn)
            context.setCurrentView(currentView());
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
                            //System.out.println("Разбудили: " + (new OwnDateTime().timeInMS()-tt));
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
                                if (asyncCount!=0)
                                    System.out.println("Пропуск рендеринга, "+asyncCount+" незавершенных запросов");
                                else
                                    repaintValues();
                                }
                            });
                        }
                    }
                });
        guiLoop.start();
        }
    //-----------------------------------------------------------------------------------------------------
    public ESS2View currentView(){
        return main2.currentView(secondPanel);
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
            Meta2GUIForm form = currentView().getView().getForms().getByTitle(Values.MainFormName);
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
            g.fillRect( context.x(0), context.y(0), screen.ScreenW(), screen.ScreenH());
            return;
            }
        Image im = null;
        try {
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/"+form.getBackImage()));
            im = icon.getImage();
            } catch (Exception e) {
                System.out.println(e.toString());
                }
        g.drawImage(im, 0, 0, screen.ScreenW(), screen.ScreenH(),null);
        }
    //---------------------------------------------------------------------------------------
    public synchronized void repaintOff(){
        setRenderingOnOff(false);
        removeAll();
        context.setForm(null);
        repaint();
        }
    //---------------------------------------------------------------------------------------
    private I_Value<String> onClose = new I_Value<String>() {               // Событие - закрытие визарда - обновить ЧМИ
        @Override
        public void onEnter(String value) {
            repaintView();
            context.getMain().sendEventPanel(BasePanel.EventRuntimeEdited,0,0,value);
            }
        };
    private I_Value<String> onChange = new I_Value<String>() {               // Событие - Изменение элемента формы
        @Override
        public void onEnter(String value) {
            context.getMain().sendEventPanel(BasePanel.EventRuntimeEdited,0,0,value);
            }
        };
    public void repaintMenu(){
        ArrayList<JButton> menuButtons[] = new ArrayList[9];
        for(int i=0;i<menuButtons.length;i++)
            menuButtons[i]=null;
        int maxButtonWSize[] = new int[9];
        int maxButtonHSize[] = new int[9];
        for(int i=0;i<maxButtonWSize.length;i++)
            maxButtonWSize[i]=maxButtonHSize[i]=0;
        Meta2GUIView currentView = currentView().getView();
        int vv1 = currentView.getMenuModes();
        int vv=0;
        while(vv1!=0){                              // Перевернуть индексы размещения строк меню для уровней
            vv = vv*10 + vv1%10;
            vv1 /= 10;
            }
        boolean defaultMenu = vv==0;
        for(int i=0;i<maxMenuLevels;i++){           // Раскидать индексы размещения строк меню для уровней
            if (defaultMenu)
                menuModes[i]=i+1;
            else{
                menuModes[i] = vv%10;
                vv=vv/10;
                }
            }
        Meta2EntityList<Meta2GUIForm> formList = currentView.getForms();
        Meta2GUIForm form = context.getForm();
        Meta2GUIForm baseForm = context.getBaseForm();
        int level = form.getLevel();
        //-----------------------------------------------------------------------------------
        if (context.getForm().getTitle().equals(Values.MainFormName)){
            TextField userTitle = new TextField();
            int  access = context.getManager().getCurrentAccessLevel();
            String ss = "  "+context.getManager().getUser().getTitle()+" ["+Values.title("AccessLevel",access)+"] ";
            userTitle.setText(ss);
            userTitle.setBounds(context.x(10),context.y(ScreenDesktopHeight-60),
                    context.dx(700),context.dy(25));
            userTitle.setEnabled(false);
            userTitle.setFont(new Font("Arial Cyr", Font.PLAIN, context.dy(12)));
            add(userTitle);
            }
        //-----------------------------------------------------------------------------------
        Meta2GUIForm ff = baseForm;
        level = ff.getLevel();
        while (true) {
            String currentName = ff.getTitle();
            int ii = 0;
            int menuStringIdx = menuModes[level];
            int baseX = levelXCoords[menuStringIdx];
            int baseY = levelYCoords[menuStringIdx];
            boolean horizontal = levelHoriz[menuStringIdx];
            for (Meta2GUIForm next : formList.getList()) {
                if (!next.getParentName().equals(currentName))
                    continue;
                if (next.isBaseForm())
                    continue;
                String text = next.getTitle().replace("_","");
                text =  "<html>" + text.replaceAll(" ", "<br>") + "</html>";
                //---------------------------------------------------- Параметры кнопки --------------------------------
                JButton bb = new JButton();
                int fontSize = currentView.getMenuButtonFontSize();
                if (fontSize==0 && next.getMenuButtonFontSize()!=0)
                    fontSize = next.getMenuButtonFontSize();
                if (fontSize==0)
                    fontSize = MenuButtonFontSize;
                boolean bold = currentView.isMenuFontBold() || next.isMenuFontBold();
                int type = bold ? Font.BOLD : Font.PLAIN;
                Font font = new Font("Arial Cyr", type, context.dy(fontSize));
                bb.setFont(font);
                int buttonW = next.getMenuButtonW();
                if (buttonW==0 && currentView.getMenuButtonW()!=0)
                    buttonW = currentView.getMenuButtonW();
                if (buttonW==0)
                    buttonW = MenuButtonW;
                if (buttonW > maxButtonWSize[menuStringIdx])
                    maxButtonWSize[menuStringIdx] = buttonW;
                int buttonH = next.getMenuButtonH();
                if (buttonH==0 && currentView.getMenuButtonH()!=0)
                    buttonH = currentView.getMenuButtonH();
                if (buttonH==0)
                    buttonH = MenuButtonH;
                if (buttonH > maxButtonHSize[menuStringIdx])
                    maxButtonHSize[menuStringIdx] = buttonH;
                int color = next.getMenuButtonOffColor();
                if (color==0 && currentView.getMenuButtonOffColor()!=0)
                    color = currentView.getMenuButtonOffColor();
                if (color==0)
                    color = MenuButtonOffColor;
                bb.setBackground(new Color(color));
                color = next.getMenuButtonTextColor();
                if (color==0 && currentView.getMenuButtonTextColor()!=0)
                    color = currentView.getMenuButtonTextColor();
                if (color==0)
                    color = MenuButtonTextСolor;
                bb.setForeground(new Color(color));
                bb.setText(text);
                bb.setHorizontalAlignment(JTextField.CENTER);
                bb.setVisible(!next.isNoMenu());
                next.setButton(bb);
                //------------------------------------------------------------------------------------------------------
                if (menuButtons[menuStringIdx]==null)
                    menuButtons[menuStringIdx] = new ArrayList<>();
                menuButtons[menuStringIdx].add(bb);
                if (buttonW>maxButtonWSize[menuStringIdx])
                    maxButtonWSize[menuStringIdx] = buttonW;
                bb.setBounds(
                    context.x(baseX),
                    context.y(baseY),
                    context.dx(buttonW),
                    context.dy(buttonH));
                if (horizontal)
                    baseX += buttonW + MenuButtonSpace;
                else
                    baseY += buttonH + MenuButtonSpace;
                final Meta2GUIForm zz = next;
                final boolean denied = main2.manager.getCurrentAccessLevel() > zz.getAccessLevel();
                if (denied)
                    bb.setBackground(new Color(Values.AccessDisableColor));
                bb.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3 && context.isRuntimeEditMode()){
                            String ss = WizardBaseView.openWizardByType(next, null, onClose, onChange, context);
                            if (ss!=null)
                                new Message(300,300,ss,Values.PopupMessageDelay);
                            }
                        else
                            context.openForm(zz,FormContext2.ModeNext);
                        }
                    });
                add(bb);
                ii++;
                }
            //----------------------------------------------------------------------------------------------------------
            if (level == 0) break;
            ff = formList.getByTitle(ff.getParentName());
            if (ff == null) {
                popup("Не найден предок для формы " + ff.getTitle());
                break;
                    }
            level--;
            }
        //----------------------- коррекция начала след. столбцов в строке меню ---------------------
        if (maxButtonHSize[2]!=0){
            for(JButton button : menuButtons[2]){
                Rectangle rr = button.getBounds();
                rr.y = context.y(maxButtonHSize[1] + MenuButtonSpace + MenuButtonMargin);
                button.setBounds(rr);
                }
            }
        if (maxButtonHSize[4]!=0){
            for(JButton button : menuButtons[4]){
                Rectangle rr = button.getBounds();
                rr.y = context.y(ScreenDesktopHeight - maxButtonHSize[3] - maxButtonHSize[4] - 2*MenuButtonSpace  - MenuButtonMargin);
                button.setBounds(rr);
                }
            }
        if (maxButtonHSize[3]!=0){
            for(JButton button : menuButtons[3]){
                Rectangle rr = button.getBounds();
                rr.y = context.y(ScreenDesktopHeight - maxButtonHSize[3] - 2*MenuButtonSpace  - MenuButtonMargin);
                button.setBounds(rr);
                }
            }
        if (maxButtonHSize[5]!=0){
            for(JButton button : menuButtons[5]){
                Rectangle rr = button.getBounds();
                rr.x = context.x(ScreenDesktopWidth - maxButtonWSize[5] - MenuButtonSpace - MenuButtonMargin);
                button.setBounds(rr);
                }
            }
        if (maxButtonHSize[6]!=0){
            for(JButton button : menuButtons[6]){
                Rectangle rr = button.getBounds();
                rr.x = context.x(ScreenDesktopWidth - maxButtonWSize[5] - maxButtonWSize[6]- 2*MenuButtonSpace -  MenuButtonMargin);
                button.setBounds(rr);
                }
            }
        if (maxButtonHSize[8]!=0){
            for(JButton button : menuButtons[8]){
                Rectangle rr = button.getBounds();
                rr.x = context.x(maxButtonWSize[7] + MenuButtonSpace + MenuButtonMargin);
                button.setBounds(rr);
                }
            }
        revalidate();
        //----------------------- коррекция начала след. столбцов при вертикальной строке меню ---------------------
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
        Meta2GUIView currentView = currentView().getView();
        Meta2EntityList<Meta2GUIForm> formList = currentView.getForms();
        this.setBackground(new Color(currentView.getBackColor()));
        removeAll();
        errorList.clear();
        if (currentView() == null || !context.isValid()) {
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
            context.openForm(fff,FormContext2.ModeNext);
            return;
            }
        repaint();
        for(ESS2Device device : main2.deployed.getDevices()){          // Очистить кэши
            device.clearCash();
            }
        //----------------------------------------------------------------------------------------
        repaintMenu();
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
        OnOff.setBounds(context.x(5), context.y(10), context.dx(50), context.dy(50));
        testESSOnOffState();
        JButton toMain = new JButton();
        toMain.setIcon(new javax.swing.ImageIcon(getClass().getResource(buttonToMain))); // NOI18N
        //toMain.setBorderPainted(false);
        //toMain.setContentAreaFilled(false);
        toMain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.openForm(Values.MainFormName,FormContext2.ModeCrearIdx);
            }
        });
        add(toMain);
        toMain.setBounds(context.x(ScreenDesktopWidth-50), context.y(10), context.dx(40), context.dy(40));
        //-------------------------------------------------------------------------------------
        insertSelected = new JButton();
        insertSelected.setIcon(new javax.swing.ImageIcon(getClass().getResource(buttonEdit))); // NOI18N
        //toMain.setBorderPainted(false);
        //toMain.setContentAreaFilled(false);
        insertSelected.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (insertSelected==null)
                    return;
                new OK(200,200,"Вставить элемент: "+selected.getFullTitle(), new I_Button() {
                    @Override
                    public void onPush() {
                        Meta2GUI copy = null;
                        try {
                            copy = selected.getClass().newInstance();   // Клонировать элемент
                            } catch (Exception ee){
                            popup("Ошибка создания клона для  "+selected.getFullTitle());
                            return;
                            }
                        copy.cloneGUIData(selected);                    // Метод клонирования содержимого для GUI
                        Meta2GUIForm form1 = context.getForm();
                        Meta2GUIForm form2 = context.getBaseForm();     //
                        if (form2!=null)
                            form1 = form2;
                        form1.getControls().add(copy);                  // Добавить в список элементов управления GUI формы
                        main.sendEventPanel(BasePanel.EventRuntimeEdited,0,0,"Добавлен "+form1.getTitle()+": "+copy.getFullTitle());
                        main.sendEventPanel(EventRuntimeUnSelected,0,0,"");
                        insertSelected.setVisible(false);
                        repaintView();
                        }
                    });
                }
            });
        add(insertSelected);
        insertSelected.setBounds(context.x(ScreenDesktopWidth-50), context.y(ScreenDesktopHeight-80), context.dx(40), context.dy(40));
        insertSelected.setVisible(context.isRuntimeEditMode());
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
        logout.setBounds(context.x(ScreenDesktopWidth-100), context.y(10), context.dx(40), context.dy(40));
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
        info.setBounds(context.x(ScreenDesktopWidth-150), context.y(10), context.dx(40), context.dy(40));
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
        int second = isSecondPanel() ? 1 : 0;
        if (code==EventRefreshSettings){
            refresh();
            main.sendEventPanel(EventRefreshSettingsDone,0,0,"");
            }
        if (code==EventPLMOn && par1==second){
            context.setView(currentView().getView());
            context.setMainServerNodeId(main2.mainServerNodeId);
            limiter.reset();
            context.setScreen((ScreenMode) oo);
            repaintView();
            main.panelToFront(this);
            System.out.println(par3);
            }
        if (code==EventPLMOff && par1==second){
            repaintOff();
            }
        if (code==EventGUIToFront && renderingOn){
            repaintView();
            }
        if (code==EventRuntimeEditMode){
            runtimeEditMode = par1!=0;
            context.setRuntimeEditMode(runtimeEditMode);
            }
        if (code==EventRuntimeOnlyView){
            runtimeOnlyView = par1!=0;
            context.setRuntimeOnlyView(runtimeOnlyView);
            }
        if (code==EventRuntimeSelected && currentView()!=null && runtimeEditMode){
            selected = (Meta2GUI) oo;
            insertSelected.setVisible(true);
            }
        if (code==EventRuntimeUnSelected){
            selected = selected;
            insertSelected.setSelected(false);
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
            if (!link.getRegister().isReadEnable())
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
                data = getRegisterData(device,links[i],unitIdx,element.getRegOffset());
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
        if (!link.getRegister().isReadEnable())
            return;
        int regNumFull = link.getRegNum()+offset;
        int regSize = link.getRegister().size16Bit();
        for(int i=0;i<regSize;i++)
            element.getDevice().putValue(element.getDevUnit(),regNumFull+i,0);     // Регистр со смещением
        }
    public synchronized void repaintValues(){       // По физическим устройствам
        if (!renderingOn)
            return;
        if (runtimeEditMode && runtimeOnlyView)
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
            Meta2RegLink vv[] = element.getSettingsLinks();
            //---------- Вспомогательные регистры с того же девайса и юнита, что и основной, без смещения
            for(Meta2RegLink link2 : vv){
                putOneLinkRegister(element,link2,0);
                }
            vv = element.getDataLinks();
            for(Meta2RegLink link2 : vv){
                putOneLinkRegister(element,link2,element.getRegOffset());
                }
            }
        renderSeqNum++;             // Установить след. номер запроса
        asyncCount=0;
        for(ESS2Device device : main2.deployed.getDevices()){
            ArrayList<UnitRegisterList> list2 = device.createList(false);
                for(UnitRegisterList list : list2){
                    //System.out.println(device.getShortName()+"["+list.getUnitIdx()+"]="+list.size());
                    readPLMRegistersAsync(device,list,context.getForm());               // Асинхронная версия
                    }
                }
        }
    //------------------------------------------------------------------------
    private synchronized void readPLMRegistersAsync(final ESS2Device device,final UnitRegisterList list, final Meta2GUIForm currentForm){
        if (!renderingOn)
            return;
        final int currentRenderSeqNum = renderSeqNum;
        final long tt =  new OwnDateTime().timeInMS();
            asyncCount++;
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
                            synchronized (ESSServiceGUIPanel.this){
                                asyncCount--;
                                if (renderSeqNum!=currentRenderSeqNum){      // Совпадение посл.номера запроса
                                    System.out.println("Несовпадение номеров запроса (трассировка) "+renderSeqNum+" "+currentRenderSeqNum);
                                    return;
                                    }
                                }
                            try {
                                repaintValuesOnAnswer(device, list.getUnitIdx(), values,currentForm);
                                //System.out.println("Ждали: " + (new OwnDateTime().timeInMS()-tt));
                                } catch (UniException e) {
                                    limiter.popup("Ошибка GUI: "+e.getSysMessage());
                                    }
                        }
                    });

                } catch (final UniException ee){
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            asyncCount--;
                            limiter.popup("Ошибка сервера: "+ee.getSysMessage());
                            main.sendEventPanel(EventPLMOffForce,0,0,"",null);
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
                ESS2LogUnit unit = equipment.getLogUnits().get(newElem.getUnitIdx());
                newElem.setDevice(unit.getDevice().getRef());
                newElem.setDevUnit(unit.getUnit());        // Физический Unit
                //----------------------ВТОРОЙ ЛИНК БЕЗ ИНДЕКСАЦИИ -----------------------------------------------------
                ESS2Equipment equipment2 = null;
                Meta2RegLink link2= meta.getSecondLink();
                if (link2!=null){
                    Meta2Register register2 = link2.getRegister();
                    String equipName2= link2.getEquipName();
                    equipment2 = main2.deployed.getEquipments().getByName(equipName2);
                    if (equipment2==null){
                        errorList.addError("Не найдено оборудование "+equipName2+" для "+regGUI.getFullTitle());
                        return;
                        }
                    int connectorsSize2 = equipment2.getLogUnits().size();
                    if (connectorsSize2==0){
                        errorList.addError("Нет устройств для "+equipName2);
                        return;
                        }
                    ESS2LogUnit unit2 = equipment2.getLogUnits().get(link2.getUnitIdx());
                    newElem.setDeviceTwo(unit2.getDevice().getRef());
                    newElem.setDevUnitTwo(unit2.getUnit());        // Физический Unit
                    }
                //-------------------------------------------------------------------------------------------------------
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
