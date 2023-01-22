package romanow.abc.desktop.view2;

import lombok.Getter;
import lombok.Setter;
import romanow.abc.core.ErrorList;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2RegLink;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIReg;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subject2area.ESS2Device;
import romanow.abc.desktop.BasePanel;
import romanow.abc.desktop.I_Value;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.wizard.WizardBaseView;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public abstract class View2Base implements I_View2 {
    @Getter @Setter int type= Values.GUINull;
    @Getter @Setter Meta2GUI element=null;          // Элемент мета-данных
    @Getter @Setter ESS2Architecture architecture;
    @Getter @Setter I_GUI2Event onEvent=null;
    @Getter @Setter ESS2Device device=null;         // Описание unit-драйвер
    @Getter @Setter int devUnit=0;                  // Явный номер Unit-а (физический)
    @Getter @Setter int unitIdx=0;                  // Явный индекс Unit-а (логический)
    @Getter @Setter int regOffset=0;
    @Getter @Setter int dxOffset=0;
    @Getter @Setter int dyOffset=0;
    @Getter @Setter int groupLevel=0;                           // Уровень групповых элементов
    @Getter int groupIndexes[]=new int[Values.FormStackSize];   // Индексы МЕТАЭЛЕМЕНТОВ в группах
    @Getter @Setter FormContext2 context;
    public Meta2RegLink getRegLink(){                           // Линк регистра в мета-данных
        return element.getRegLink();
        }
    public Meta2RegLink[] getSettingsLinks(){
        return element.getSettingsLinks();
        }
    public Meta2RegLink[] getDataLinks(){
        return element.getDataLinks();
        }
    public void repaintBefore(){}                               // Для исключительных действий (скрипты)
    public void repaintValues(){}                               // После прочтения всех данных
    public void putValue(Meta2Register register, long value, int idx){}
    public abstract void showInfoMessage();
    @Override
    public String getTypeName() {
        return Values.constMap().getGroupMapByValue("GUIType").get(type).title(); }
    @Override
    public String getTitle() {
        return element==null ? "" : element.getTitle();
        }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        context = context0;
        onEvent = onEvent0;
        architecture = meta0;
        element = element0;
        return null;
        }

    private I_Value<String> onClose = new I_Value<String>() {               // Событие - закрытие визарда - обновитьь ЧМИ
        @Override
        public void onEnter(String value) {
            onEvent.onEnter(View2Base.this,0,"");
            }
        };
    private I_Value<String> onChange = new I_Value<String>() {               // Событие - Изменение элемента формы
        @Override
        public void onEnter(String value) {
            context.getMain().sendEventPanel(BasePanel.EventRuntimeEdited,0,0,value);
            }
        };
    public void setInfoClick(Component textField){
        textField.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (context.isInfoMode() || e.getButton() == MouseEvent.BUTTON3){
                    if (!context.isRuntimeEditMode())
                        showInfoMessage();
                    else{
                        context.setSelectedView(element);
                        context.getMain().sendEventPanel(BasePanel.EventRuntimeSelected,0,0,"");
                        String ss = WizardBaseView.openWizardByType(element, null, onClose, onChange);
                        if (ss!=null)
                            new Message(300,300,ss,Values.PopupMessageDelay);
                        }
                    }
                }});
            }
    public void setBitNum(int nbit){}
    public String toString(){
        return getTitle()+" "+Values.title("GUITypeName",getType())+" x="+element.getX()+" y="+element.getY();
        }
    //--------------------------------------------------------------------------------------------------
    public static View2Base createGUIElement(ErrorList errorList, String platform, Meta2GUI entity){
        String name = entity.getClass().getSimpleName();
        if (!name.startsWith("Meta2")){
            errorList.addError("Недопустимое имя класса "+name);
            return null;
            }
        name = "romanow.abc.desktop.view2."+platform.toLowerCase()+"."+platform+name.substring(5);
        try {
            Class  cls = Class.forName(name);
            View2Base element = (View2Base)cls.newInstance();
            return element;
        } catch (Exception ee){
            errorList.addError("Ошибка создания элемента ЧМИ "+name+": "+ee.toString());
            return null;
        }
    }
    //--------------------------------------------------------------------------------------------------
    public Meta2Register getRegister() {
        if (!(getElement() instanceof Meta2GUIReg))
            return null;
        return (Meta2Register) ((Meta2GUIReg) getElement()).getRegLink().getRegister();
        }
    public int readMainRegister() throws UniException {
        if (!(getElement() instanceof Meta2GUIReg))
            throw UniException.config(getElement().getFullTitle()+" на является регистром");
        Meta2RegLink link = (Meta2RegLink)((Meta2GUIReg) getElement()).getRegLink();
        int vv = device.getDriver().readRegister(device.getShortName(),devUnit,link.getRegNum()+getRegOffset());
        return vv;
        }
    public void writeRegister(Meta2RegLink link,int vv) throws UniException {
        int regNumFull = link.getRegNum()+getRegOffset();
        device.getDriver().writeRegister(device.getShortName(),devUnit,regNumFull,vv & 0x0FFFF);
        if (link.getRegister().doubleSize())
            device.getDriver().writeRegister(device.getShortName(),devUnit,regNumFull+1,vv>>16 & 0x0FFFF);
            }
    public void writeMainRegister(int vv) throws UniException {
        if (!(getElement() instanceof Meta2GUIReg))
            throw UniException.config(getElement().getFullTitle()+" на является регистром");
        Meta2RegLink link = (Meta2RegLink)((Meta2GUIReg) getElement()).getRegLink();
        int regNumFull = link.getRegNum()+getRegOffset();
        device.getDriver().writeRegister(device.getShortName(),devUnit,regNumFull,vv & 0x0FFFF);
        if (link.getRegister().doubleSize())
            device.getDriver().writeRegister(device.getShortName(),devUnit,regNumFull+1,vv>>16 & 0x0FFFF);
        }
    public int readRegister(Meta2RegLink link, int regOffset) throws UniException {
        int regNumFull = link.getRegNum()+regOffset;
        int vv = getDevice().getDriver().readRegister(device.getShortName(),devUnit,regNumFull) & 0x0FFFF;
        if (link.getRegister().doubleSize()){
            int vv2 = getDevice().getDriver().readRegister(device.getShortName(),devUnit,regNumFull+1) & 0x0FFFF;
            vv |=vv2<<16;
            }
        return vv;
        }
    public void writeRegister(Meta2RegLink link,int vv, int regOffset) throws UniException {
        device.getDriver().writeRegister(device.getShortName(),devUnit,link.getRegNum()+regOffset,vv);
        }
    //------------------------------------------------------------------------------------------------------------------
    public static long toOneWord(int data[]){
        long out=0;
        for(int i=0;i<4 && i<data.length;i++)
            out |= (((long) data[i])&0x0FFFF) << (i*16);
        return out;
        }
    public void putValue(int data[]) throws UniException {
        putValue(toOneWord(data));
        }
}
