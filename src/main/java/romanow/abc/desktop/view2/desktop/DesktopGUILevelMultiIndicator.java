package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2RegLink;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUILevelMultiIndicator;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.I_Success;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUILevelMultiIndicator extends View2BaseDesktop {
    private final static int alarmMax=0;
    private final static int alarmMin=3;
    private final static int warnMax=1;
    private final static int warnMin=2;
    private final double limits[]={0,0,0,0};
    private double vv[] = new double[]{0,0,0};
    private  int hh[] = new int[]{0,0,0};
    private double vMin,vMax;
    private JTextField textField;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField high;
    public DesktopGUILevelMultiIndicator(){
        setType(Values.GUILevelMultiIndicator);
        }
    private I_Success back=null;
    private int h=0,w2=0;
    private final static int proc=25;
    private Color backColor;
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        Meta2GUILevelMultiIndicator element = (Meta2GUILevelMultiIndicator) getElement();
        Meta2DataRegister register = (Meta2DataRegister)  getRegister();
        w2=element.getW2();
        if (w2==0) w2=100;
        h = element.getH();
        if (h==0) h=100;
        textField = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        high = new JTextField();
        int dxOffset = getDxOffset();
        int dyOffset = getDyOffset();
        high.setBounds(
                context.x(element.getX()+dxOffset+5),
                context.y(element.getY()+dyOffset),
                context.x(w2),
                context.x(h));
        backColor = new Color(element.getColor());
        textField.setBackground(backColor);
        textField2.setBackground(backColor);
        textField3.setBackground(backColor);
        high.setBackground(backColor);
        setInfoClick(textField);
        setInfoClick(high);
        high.setBounds(
                context.x(element.getX()+dxOffset+5),
                context.y(element.getY()+dyOffset),
                context.x(w2),
                context.y(h));
        textField.setBounds(
                context.x(element.getX()+dxOffset+5),
                context.y(element.getY()+dyOffset+h),
                context.x(w2),
                context.y(0));
        textField2.setBounds(
                context.x(element.getX()+dxOffset+5),
                context.y(element.getY()+dyOffset+h),
                context.x(w2),
                context.y(0));
        textField3.setBounds(
                context.x(element.getX()+dxOffset+5),
                context.y(element.getY()+dyOffset+h),
                context.x(w2),
                context.y(0));
        textField.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        textField2.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        textField3.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        //panel.add(high);
        panel.add(textField);
        panel.add(textField2);
        panel.add(textField3);
        }
    @Override
    public void putValue(Meta2Register register, long xx, int idx) {
        if (register instanceof Meta2SettingRegister)
            limits[idx] = register.floatWithPower(getUnitIdx(),(int)xx);
        else{
            if (idx==0)
                vv[1] = register.floatWithPower(getUnitIdx(),(int)xx);
            else
                vv[2] = register.floatWithPower(getUnitIdx(),(int)xx);
            }
        }
    @Override
    public void putValue(long xx) throws UniException {
        Meta2DataRegister register = (Meta2DataRegister) getRegister();
        vv[0] = register.floatWithPower(getUnitIdx(),(int)xx);
        }
    private Color getValueColor(double vv){
        Color color = Color.gray;
        //if (settingsValues==null)
        //    return color;
        color = Color.green;
        if (vv >= limits[alarmMax] || vv <= limits[alarmMin])
            color = Color.red;
        else if (vv >= limits[warnMax] || vv <= limits[warnMin])
             color = Color.yellow;
        return color;
        }

    @Override
    public void repaintValues(){
        int dxOffset = getDxOffset();
        int dyOffset = getDyOffset();
        FormContext2 context= getContext();
        Meta2GUILevelMultiIndicator element = (Meta2GUILevelMultiIndicator) getElement();
        Meta2DataRegister register = (Meta2DataRegister)  getRegister();
        vMin = limits[alarmMin] - (limits[alarmMax]-limits[alarmMin])*proc/100;
        vMax = limits[alarmMax] + (limits[alarmMax]-limits[alarmMin])*proc/100;
        /*
        if (settingsValues==null){
            high.setBounds(
                    context.x(element.getX()+dxOffset+5),
                    context.y(element.getY()+dyOffset),
                    context.x(w2),
                    context.y(h));
            return;
            }
         */
        for(int i=0;i<3;i++){
            if (vv[i]<vMin) vv[i]=vMin*1.03;
            if (vv[i]>vMax) vv[i]=vMax*0.98;
            }
        for(int i=0;i<3;i++){
            hh[i] = (int)((vv[i]-vMin)/(vMax-vMin)*h);
            }
        //high.setBounds(element.getX()+dxOffset+5,element.getY()+dyOffset,w2,h-hh+2);
        int xx = element.getX()+dxOffset+5;
        int yy = element.getY()+dyOffset+h;
        textField.setBounds(
                context.x(xx),
                context.y(yy-hh[0]),
                context.x(w2),
                context.y(hh[0]-hh[1]));
        textField.setBackground(getValueColor(vv[0]));
        textField3.setBounds(
                context.x(xx),
                context.y(yy-hh[1]),
                context.x(w2),
                context.y(5));
        textField3.setBackground(getValueColor(vv[1]));
        textField2.setBounds(
                context.x(xx),
                context.y(yy-hh[1]+5),
                context.x(w2),
                context.y(hh[1]-hh[2]));
        textField2.setBackground(getValueColor(vv[2]));
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register = getRegister();
        Meta2GUILevelMultiIndicator element = (Meta2GUILevelMultiIndicator) getElement();
        if (!(register instanceof Meta2DataRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        Meta2RegLink[] links = getSettingsLinks();
        for(Meta2RegLink link : links){
            register = link.getRegister();
            if (!(register instanceof Meta2SettingRegister))
                return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
            }
        links = getDataLinks();
        for(Meta2RegLink link : links){
            register = link.getRegister();
            if (!(register instanceof Meta2DataRegister))
                return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
            }
        return null;
        }
    @Override
    public void showInfoMessage() {
        Meta2DataRegister set = (Meta2DataRegister)  getRegister();
        String ss = "Индикатор уровня(3) для "+toHex(set.getRegNum()+getRegOffset())+" ["+set.getRegNum()+"] "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+="Регистры уровней: ";
        for(Meta2RegLink link : getElement().getSettingsLinks())
            ss+=toHex(link.getRegister().getRegNum())+" ";
        ss+="Доп.регистры: ";
        for(Meta2RegLink link2 : getElement().getDataLinks())
            ss+=toHex(link2.getRegister().getRegNum()+getRegOffset())+" ";
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    }
