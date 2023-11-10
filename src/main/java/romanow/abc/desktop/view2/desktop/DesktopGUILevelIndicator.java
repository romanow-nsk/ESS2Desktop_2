package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2RegLink;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUILevelIndicator;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.I_Success;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUILevelIndicator extends View2BaseDesktop {
    //private JProgressBar textField;
    private JTextField textField;
    public DesktopGUILevelIndicator(){
        setType(Values.GUILevelIndicator);
        }
    private I_Success back=null;
    private final static int alarmMax=0;
    private final static int alarmMin=3;
    private final static int warnMax=1;
    private final static int warnMin=2;
    private final double limits[]={0,0,0,0};
    private double value=0;
    private double vMin,vMax;
    private int h=0,w2=0;
    private final static int proc=25;
    private Color backColor;
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        Meta2GUILevelIndicator element = (Meta2GUILevelIndicator) getElement();
        Meta2DataRegister register = (Meta2DataRegister)  getRegister();
        w2=element.getW2();
        if (w2==0) w2=100;
        h = element.getH();
        if (h==0) h=100;
        textField = new JTextField();
        textField.setBounds(
                context.x(element.getX()+getDxOffset()+5),
                context.y(element.getY()+getDyOffset()),
                context.dx(w2-1),
                context.dy(h));
        panel.add(textField);
        backColor = new Color(element.getColor());
        setInfoClick(textField);
        }
    @Override
    public void putValue(Meta2Register register, long xx, int idx) {
        limits[idx] = register.regValueToFloat(getUnitIdx(),(int)xx);
        }
    @Override
    public void putValue(long xx) throws UniException {
        Meta2DataRegister register = (Meta2DataRegister) getRegister();
        if (((Meta2GUILevelIndicator)getElement()).isByteSize()){
            value = (byte)xx;
            }
        else
            value = register.regValueToFloat(getUnitIdx(),(int)xx);
        }
    @Override
    public void repaintValues(){
        vMin = limits[alarmMin] - (limits[alarmMax]-limits[alarmMin])*proc/100;
        vMax = limits[alarmMax] + (limits[alarmMax]-limits[alarmMin])*proc/100;
        Color color = Color.green;
        if (value>=limits[alarmMax] || value<=limits[alarmMin])
            color=Color.red;
        else
        if (value>=limits[warnMax] || value<=limits[warnMin])
            color=Color.yellow;
        int hh = (int)((value-vMin)/(vMax-vMin)*h);
        if (hh > h) hh = h;
        if (hh<0) hh=0;
        textField.setBackground(color);
        FormContext2 context= getContext();
        Meta2GUILevelIndicator element = (Meta2GUILevelIndicator) getElement();
        textField.setBounds(
                context.x(element.getX()+getDxOffset()+5),
                context.y(element.getY()+getDyOffset()+h-hh),
                context.dx(w2-1),
                context.dy(hh));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register = getRegister();
        if (register==null)
            return getRegisterTitle();
        if (!(register instanceof Meta2DataRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        Meta2RegLink[] links = getSettingsLinks();
        for(Meta2RegLink link : links){
            register = link.getRegister();
            if (register==null)
                return getRegisterTitle();
            if (!(register instanceof Meta2SettingRegister))
                return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
            }
        return null;
        }
    @Override
    public void showInfoMessage() {
        Meta2DataRegister set = (Meta2DataRegister)  getRegister();
        String ss = "Индикатор уровня для "+toHex(set.getRegNum()+getRegOffset())+" ["+set.getRegNum()+"] "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+="Регистры уровней: ";
        for(Meta2RegLink link : getElement().getSettingsLinks()){
            ss+=toHex(link.getRegister().getRegNum())+" ";
            }
        new Message(300,300,ss,Values.PopupMessageDelay);
    }
}
