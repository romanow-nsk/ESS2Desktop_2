package romanow.abc.desktop;

import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.entity.subjectarea.ESSNode;
import romanow.abc.core.entity.subjectarea.MetaExternalSystem;
import romanow.abc.desktop.view.GUIElement;

import java.util.ArrayList;

public class ESSNodeViewData {
    private ESSNode node;
    //------------------------------ Для СУ АГЭУ ------------------------------------
    private MetaExternalSystem meta=null; // метаданные СМУ СНЭ для СУ АГЭУ
    private RestAPIBase service = null;   // Клиент СМУ СНЭ
    private RestAPIESS2 service2 = null;   // Клиент СМУ СНЭ
    private String token = "";            // Токен соединения со СМУ СНЭ
    private int offsetH=0;                // Смещение начала формы на экране СУ АГЭУ
    private ArrayList<GUIElement> guiList = new ArrayList<>();
    private boolean ready=false;
    private volatile boolean asyncBusy=false;
    //---------------------------------------------------------------------------------
    public RestAPIESS2 getService2() {
        return service2; }
    public void setService2(RestAPIESS2 service2) {
        this.service2 = service2; }
    public boolean isAsyncBusy() {
        return asyncBusy; }
    public void setAsyncBusy(boolean asyncBusy) {
        this.asyncBusy = asyncBusy; }
    public boolean isReady() {
        return ready; }
    public void setReady(boolean ready) {
        this.ready = ready; }
    public ESSNodeViewData(ESSNode node) {
        this.node = node; }
    public MetaExternalSystem getMeta() {
        return meta; }
    public void setMeta(MetaExternalSystem meta) {
        this.meta = meta; }
    public RestAPIBase getService() {
        return service; }
    public void setService(RestAPIBase service) {
        this.service = service; }
    public String getToken() {
        return token; }
    public void setToken(String token) {
        this.token = token; }
    public int getOffsetH() {
        return offsetH; }
    public void setOffsetH(int offsetH) {
        this.offsetH = offsetH; }
    public ArrayList<GUIElement> getGuiList() {
        return guiList; }
    public ESSNode getNode() {
        return node; }
}
