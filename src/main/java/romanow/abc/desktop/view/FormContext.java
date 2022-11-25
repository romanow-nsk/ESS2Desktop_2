package romanow.abc.desktop.view;

import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.entity.subjectarea.AccessManager;
import romanow.abc.core.entity.subjectarea.MetaGUIForm;
import romanow.abc.desktop.ScreenMode;


public abstract class FormContext {
    private boolean valid=false;
    private ScreenMode screen = new ScreenMode();   // Данные экрана (панели)
    private boolean infoMode = false;               // Индикатор тежима Info
    private boolean localUser = false;              // Индикатор локального пользователя
    private boolean superUser = false;              // Индикатор суперпользователя
    private MetaGUIForm form = null;                // Текущая форма
    private AccessManager manager = null;           // Менеджер доступа с данными User
    private RestAPIBase service = null;             // Сервис webAPI текущего соединения
    private String token="";                        // Токен сессии сервера данных
    private int idx[]=new int[3];                   // Стек начальных индексов групповых элементов GUI
    private int size[]=new int[3];                  // Стек размерностей групповых элементов GUI в форме
    public int getIndex(int level) {
        return idx[level-1]; }
    public void setIndex(int level, int val) {
        idx[level-1] = val;
        if (level<3) idx[2]=0;
        if (level<2) idx[1]=0;
        }
    public int getSize(int level) {
        return size[level-1]; }
    public void setSize(int level, int val) {
        size[level-1] = val; }
    public FormContext(int idx1, int idx2, int idx3) {
        idx[0] = idx1;
        idx[1] = idx2;
        idx[2] = idx3; }
    public MetaGUIForm getForm() {
        return form; }
    public void setForm(MetaGUIForm curentForm) {
        this.form = curentForm; }
    public FormContext() {
        idx[0]=idx[1]=idx[2]=0;
        size[0]=size[1]=size[2]=0;
        }
    public AccessManager getManager() {
        return manager; }
    public void setManager(AccessManager manager) {
        this.manager = manager; }
    public RestAPIBase getService() {
        return service; }
    public void setService(RestAPIBase service) {
        this.service = service; }
    public String getToken() {
        return token; }
    public void setToken(String token) {
        this.token = token; }
    public boolean isInfoMode() {
        return infoMode; }
    public void setInfoMode(boolean infoMode) {
        this.infoMode = infoMode; }
    public void setScreen(ScreenMode mode){
        screen = mode;
        }
    public ScreenMode getScreen(){
        return screen;
        }
    public int x(int x){
        return screen.x(x);
        }
    public int y(int y){
        return screen.y(y);
        }
    public abstract void reOpenForm();
    public abstract void openForm(String formName, int level, int idx);
    public abstract void openForm(MetaGUIForm form, int level, int idx);
    public boolean isLocalUser() {
        return localUser; }
    public void setLocalUser(boolean localUser) {
        this.localUser = localUser; }
    public boolean isSuperUser() {
        return superUser; }
    public void setSuperUser(boolean superUser) {
        this.superUser = superUser; }
    public boolean isActionEnable(){
        return manager.getCurrentAccessLevel() <= form.getWriteLevel();
        }
    public boolean isValid() {
        return valid; }
    public void setValid(boolean valid) {
        this.valid = valid; }
}
