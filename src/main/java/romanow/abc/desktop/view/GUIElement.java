package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.MetaExternalSystem;
import romanow.abc.core.entity.subjectarea.MetaGUIElement;
import romanow.abc.core.entity.subjectarea.MetaRegister;
import romanow.abc.core.utils.Pair;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.UtilsDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class GUIElement implements I_ViewElement{
    protected int regNums[] = { -1 };
    protected int type= Values.GUILabel;
    protected MetaRegister register=null;
    protected MetaGUIElement element=null;
    protected MetaExternalSystem meta;
    protected I_ModbusGroupDriver plm=null;
    protected I_GUIEvent onEvent=null;
    protected int regOffset=0;
    protected int dxOffset=0;
    protected int dyOffset=0;
    protected int groupIndex=0;
    protected int groupSize=1;
    protected boolean inGroup=false;
    protected FormContext context;
    protected JLabel label;
    public int getType() {
        return type;
        }
    @Override
    public String getTypeName() {
        return ""; }
    @Override
    public String getTitle() {
        return element==null ? "" : element.getTitle();
        }
    @Override
    public String setParams(FormContext context0, MetaExternalSystem meta0, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent onEvent0) {
        context = context0;
        onEvent = onEvent0;
        meta = meta0;
        register = register0;
        element = element0;
        plm = plm0;
        return null;
        }
    public FormContext getContext() {
        return context; }
    public void setInGroup(){
        inGroup=false;
        groupSize=0;
        int count = element.getCount();
        if (count!=0){
            inGroup=true;
            groupSize=count;
            return;
            }
        if (register==null)
            return;
        count = register.getLevelCount(1);
        inGroup = count!=0;
        }
    public int []getRegNum() {
        regNums[0] = register.getRegNum()+regOffset;
        return regNums;
        }
    public void putValue(int vv[]) throws UniException{
        }
    public int getRegStep(){
        return 0;
        }
    public void setInGroup(GUIElement proto){
        inGroup=proto.inGroup;
        groupSize=proto.groupSize;
        setSettingsValues(proto.getSettingsValues());
        }
    public boolean isInGroup() {
        return inGroup; }
    public int getGroupIndex() {
        return groupIndex; }
    public int getGroupSize() {
        return groupSize; }
    public void setGroupIndex(int groupInex) {
        this.groupIndex = groupInex; }
    @Override
    public boolean needRegister() {
        return true;
        }
    public int getDxOffset() {
        return dxOffset; }
    public void setDxOffset(int dxOffset) {
        this.dxOffset = dxOffset; }
    public int getDyOffset() {
        return dyOffset; }
    public void setDyOffset(int dyOffset) {
        this.dyOffset = dyOffset; }
    public int getRegOffset() {
        return regOffset; }
    public void setRegOffset(int regOffset) {
        this.regOffset = regOffset; }
    public MetaRegister getRegister() {
        return register; }
    public MetaGUIElement getMetaElement() {
        return element; }
    /* Не используются, т.к регистры группируются
    public int readRegister(int offset) throws UniException{
        return readRegister(cash.getMaxRegisterAge(),offset);
        }
    public int readRegister(long age,int offset) throws UniException{
        int regNum = register.getRegNum()+offset;
        RegisterValue value = cash.getCash(regNum);
        if (value==null || (new OwnDateTime()).timeInMS()-value.saveTime.timeInMS()>=age){
            int val = plm.readRegister(regNum);
            cash.putCash(regNum,val);
            return val;
            }
        return value.value;
        }
    public void writeRegister(int value) throws UniException{
        plm.writeRegister(register.getRegNum(),value);
        cash.putCash(register.getRegNum(),value);
        }
     */
    public JLabel setLabel(JPanel panel){
        return setLabel(panel,false);
    }
    public JLabel setLabel(JPanel panel, boolean noColor){
        String text = element.getTitle();
        if (this instanceof GUILabel && inGroup)               // НЕ ООП !!!!!!!!!!!!!!!!!!!
            text += " "+(groupIndex+1);
        int hh = element.getH();
        if (hh==0) hh=25;
        label = new JLabel();
        label.setBounds(
                context.x(element.getX()+dxOffset),
                context.y(element.getY()+dyOffset),
                context.x(element.getDx()),
                context.y(hh));
        int size = element.getSize();
        if (size==0)
            label.setText("  "+text);
        else
            UtilsDesktop.setLabelText(label,text,size);
        label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        if (!noColor){
            label.setOpaque(true);
            Color color=new Color(element.getColor());
            label.setBackground(color);
            }
        int fontSize = element.getFontSize();
        if (fontSize==0) fontSize=12;
        label.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(fontSize)));
        panel.add(label);
        return label;
        }
    public void showInfoMessage(){
        String ss = "Регистр "+(register.getRegNum()+regOffset)+"->"+register.toString();
        new Message(200,200,ss);
        }
    public void setInfoClick(JComponent textField){
        textField.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (context.isInfoMode() || e.getButton() == MouseEvent.BUTTON3)
                    showInfoMessage();
                }});
            }
    public String valueWithPower(int vv){
        int power = register.getPower();
        if (power<0)
            return ""+vv/Math.pow(10,-power);
        if (power>0)
            return ""+vv*Math.pow(10,power);
        return ""+vv;
        }
    public double doubleWithPower(int vv){
        int power = register.getPower();
        if (power<0)
            return vv/Math.pow(10,-power);
        if (power>0)
            return vv*Math.pow(10,power);
        return vv;
        }
    public void setBitNum(int nbit){}
    public String toString(){
        return getTitle()+" "+Values.title("GUITypeName",getType())+" x="+element.getX()+" y="+element.getY();
        }
    //---------------- Для групповых уставок индикаторов
    public boolean needSettingsValues(){
        return false;
        }
    public void setSettingsValues(double values[]){}
    public double[] getSettingsValues(){
        return null;
        }
    //--------------------------------------------------------------------------------------------------
    public static Pair<String, GUIElement> createGUIElement(Meta2GUI entity){
        String name = entity.getClass().getSimpleName();
        if (!name.startsWith("Meta2"))
            return new Pair<>("Недопустимое имя класса "+name,null);
        name = "romanow.abc.desktop.view."+name.substring(5);
        try {
            Class  cls = Class.forName(name);
            GUIElement element = (GUIElement)cls.newInstance();
            return new Pair<>(null,element);
        } catch (Exception ee){
            return new Pair<>("Ошибка создания элемента ЧМИ "+name+": "+ee.toString(),null);
        }
    }
}
