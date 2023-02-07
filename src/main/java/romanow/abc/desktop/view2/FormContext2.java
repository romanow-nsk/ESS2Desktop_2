package romanow.abc.desktop.view2;

import lombok.Getter;
import lombok.Setter;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2GUIForm;
import romanow.abc.core.entity.metadata.Meta2GUIView;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.subject2area.ESS2View;
import romanow.abc.core.entity.subjectarea.AccessManager;
import romanow.abc.desktop.ESSServiceGUIPanel;
import romanow.abc.desktop.MainBaseFrame;
import romanow.abc.desktop.ScreenMode;


public class FormContext2 {
    public final static int ModeNext=0;
    public final static int ModeCrearIdx=1;
    public final static int ModeForce=2;
    private I_ContextBack back;
    @Getter @Setter private MainBaseFrame main;
    @Getter @Setter private Meta2GUIView view;                      // Общие данные ЧМИ
    @Setter private ESS2View currentView=null;
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
    private String menuFormStack[] = new String[Values.MenuStackSize];
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
    public FormContext2(I_ContextBack back0,int idx1, int idx2, int idx3) {
        back = back0;
        for(int i=0;i<Values.FormStackSize;i++)
            idx[i]=size[i]=0;
        idx[0] = idx1;
        idx[1] = idx2;
        idx[2] = idx3; }
    public FormContext2(I_ContextBack back0) {
        back = back0;
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
    public void openForm(String formName){
        openForm(formName,ModeNext);
        }
    public boolean isActionEnable(){
        return manager.getCurrentAccessLevel() <= form.getWriteLevel();
        }
    public void reOpenForm() {
        repaintView();
    }
    public void openForm(String formName, int mode) {
        Meta2GUIForm form = currentView.getView().getForms().getByTitle(formName);
        if (form==null){
            back.popup("Не найдена форма "+formName);
            return;
            }
        openForm(form,mode);
        }
    public void openForm(Meta2GUIForm form, int mode) {
        setBaseForm(form);
        if (form.isLinkForm()){
            String baseFormName = form.getShortName();
            Meta2GUIForm baseForm = currentView.getView().getForms().getByTitle(baseFormName);
            if (baseForm==null){
                back.popup("Не найдена базовая форма "+baseFormName);
                return;
            }
            int idx = form.getBaseFormIndex();
            int level = form.getFormLevel();
            if (level!=0 && idx!=-1)            // Индекс для предыдущего уровня их групповой кнопки меню
                setIndex(level,idx);
            setBaseForm(baseForm);
            }
        if (back.getAcccessLevel() > form.getAccessLevel()){
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
            for(int i=level;i<menuFormStack.length;i++)
                menuFormStack[i]="";
            for(int ll = level; ll>0;ll--){
                setIndex(ll,0);
                setSize(ll,0);
                menuFormStack[ll-1] = ff.getTitle();
                ff = ff.getParent();
                }
            }
        if (mode==FormContext2.ModeNext){
            menuFormStack[level-1]=form.getTitle();
            }
        int vv[] = getIdx();
        //System.out.println("`Стек` индексов: "+vv[0]+" "+vv[1]+" "+vv[2]+" "+vv[3]);
        //System.out.println("Стек форм: "+menuFormStack[0]+" "+menuFormStack[1]+" "+menuFormStack[2]+" "+menuFormStack[3]);
        repaintView();
        }
    public void repaintView() {
        back.repaintView();
        }
    public void repaintValues() {
        back.repaintValues();
        }
    public void popup(String mes) {
        back.popup(mes);
        }


}
