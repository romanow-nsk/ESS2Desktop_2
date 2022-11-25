package romanow.abc.desktop.view2;

import lombok.Getter;
import lombok.Setter;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2GUIForm;
import romanow.abc.core.entity.metadata.Meta2GUIView;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.subjectarea.AccessManager;
import romanow.abc.desktop.MainBaseFrame;
import romanow.abc.desktop.ScreenMode;


public abstract class FormContext2 {
    public final static int ModeNext=0;
    public final static int ModeCrearIdx=1;
    public final static int ModeForce=2;
    @Getter @Setter private MainBaseFrame main;
    @Getter @Setter private Meta2GUIView view;                      // Общие данные ЧМИ
    @Getter @Setter private String platformName="";
    @Getter @Setter private boolean valid=false;
    @Getter @Setter private ScreenMode screen = new ScreenMode();   // Данные экрана (панели)
    @Getter @Setter private boolean infoMode = false;               // Индикатор тежима Info
    @Getter @Setter private long mainServerNodeId = 0;              // Номер узала на сервере ДЦ (0 - СНЭЭ)
    @Getter @Setter private boolean runtimeEditMode = false;        // Индикатор режима редактирования на лету
    @Getter @Setter private Meta2GUI selectedView=null;             // Выбранный элемент для клонирования
    @Getter @Setter private boolean localUser = false;              // Индикатор локального пользователя
    @Getter @Setter private boolean superUser = false;              // Индикатор суперпользователя
    @Getter @Setter private Meta2GUIForm form = null;               // Текущая форма
    @Getter @Setter private Meta2GUIForm baseForm = null;           // Текущая форма (отображаемая)
    @Getter @Setter private AccessManager manager = null;           // Менеджер доступа с данными User
    @Getter @Setter private RestAPIBase service = null;             // Сервис webAPI текущего соединения
    @Getter @Setter private RestAPIESS2 service2 = null;            // Сервис webAPI ESS2 текущего соединения
    @Getter @Setter private String token="";                        // Токен сессии сервера данных
    @Getter @Setter private int idx[]=new int[Values.FormStackSize];// Стек начальных индексов групповых элементов GUI
    @Getter @Setter private int size[]=new int[Values.FormStackSize];// Стек размерностей групповых элементов GUI в форме
    @Getter @Setter private String menuFormStack[] = new String[Values.MenuStackSize];
    public void show(){
        System.out.print((form!=null ? form.getFormLevel() : "")+"->");
        for(int i=0;i<idx.length;i++)
            System.out.print(idx[i]+"["+size[i]+"] ");
        System.out.println();
        }
    public int getIndex(int level) {
        if (level<=0) return 0;
        return idx[level-1]; }
    public void setIndex(int level, int val) {
        idx[level-1] = val;
        for(int i=level;i<Values.FormStackSize;i++)
            idx[i]=0;
        }
    public String getName(int level) {
        if (level<=0) return "";
        return menuFormStack[level-1]; }
    public void setName(int level, String val) {
        menuFormStack[level-1] = val;
        for(int i=level;i<Values.MenuStackSize;i++)
            menuFormStack[i]="";
        }
    public int getSize(int level) {
        if (level<=0) return 0;
        return size[level-1]; }
    public void setSize(int level, int val) {
        size[level-1] = val;
        for(int i=level;i<Values.FormStackSize;i++)
            size[i]=0;
        }
    public FormContext2(int idx1, int idx2, int idx3) {
        for(int i=0;i<Values.FormStackSize;i++)
            idx[i]=size[i]=0;
        idx[0] = idx1;
        idx[1] = idx2;
        idx[2] = idx3; }
    public FormContext2() {
        for(int i=0;i<Values.FormStackSize;i++)
            idx[i]=size[i]=0;
        }
    public RestAPIBase getService() {
        return service; }
    public void setService(RestAPIBase service) {
        this.service = service; }
    public int x(int x){
        return screen.x(x);
        }
    public int y(int y){
        return screen.y(y);
        }
    public abstract void reOpenForm();
    public void openForm(String formName){
        openForm(formName,ModeNext);
        }
    public abstract void openForm(String formName,int mode);
    public abstract void openForm(Meta2GUIForm form,int mode);
    public abstract void repaintView();
    public abstract void repaintValues();
    public abstract void popup(String mes);
    public boolean isActionEnable(){
        return manager.getCurrentAccessLevel() <= form.getWriteLevel();
        }
}
