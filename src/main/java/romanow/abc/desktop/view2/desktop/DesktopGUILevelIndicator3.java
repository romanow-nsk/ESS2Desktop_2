package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUILevelIndicator;
import romanow.abc.core.entity.metadata.view.Meta2GUISetting;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.I_Success;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;

public class DesktopGUILevelIndicator3 extends View2BaseDesktop {
    private JTextField textField;
    private JTextField high;
    public DesktopGUILevelIndicator3(){
        setType(Values.GUILevelIndicator);
        }
    private I_Success back=null;
    private double settingsValues[];
    private double vMin=0,vMax=0;
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
        if (settingsValues!=null){
            vMin = settingsValues[3] - (settingsValues[0]-settingsValues[3])*proc/100;
            vMax = settingsValues[0] + (settingsValues[0]-settingsValues[3])*proc/100;
            }
        textField = new JTextField();
        high = new JTextField();
        high.setBounds(
                context.x(element.getX()+getDxOffset()+5),
                context.y(element.getY()+getDyOffset()),
                context.x(w2),
                context.x(h));
        backColor = new Color(element.getColor());
        textField.setBackground(backColor);
        high.setBackground(backColor);
        setInfoClick(textField);
        setInfoClick(high);
        high.setBounds(
                context.x(element.getX()+getDxOffset()+5),
                context.y(element.getY()+getDyOffset()),
                context.x(w2),
                context.y(h));
        textField.setBounds(
                context.x(element.getX()+getDxOffset()+5),
                context.y(element.getY()+getDyOffset()+h),
                context.x(w2),
                context.y(0));
        panel.add(textField);
        panel.add(high);
        }
    @Override
    public void putValue(long zz) throws UniException {
        FormContext2 context= getContext();
        Meta2GUISetting element = (Meta2GUISetting) getElement();
        Meta2DataRegister register = (Meta2DataRegister)  getRegister();
        if (settingsValues==null){
            high.setBounds(
                    context.x(element.getX()+getDxOffset()+5),
                    context.y(element.getY()+getDyOffset()),
                    context.x(w2),
                    context.y(h));
            return;
            }
        double vv = register.floatWithPower(getUnitIdx(),(int)zz);
        if (vv<vMin) vv=vMin*1.03;
        if (vv>vMax) vv=vMax*0.98;
        Color color = Color.gray;
        if (settingsValues!=null) {
            color = Color.green;
            if (vv >= settingsValues[0] || vv <= settingsValues[3])
                color = Color.red;
            else if (vv >= settingsValues[1] || vv <= settingsValues[2])
                color = Color.yellow;
            }
        int hh = (int)((vv-vMin)/(vMax-vMin)*h);
        high.setBounds(
                context.x(element.getX()+getDxOffset()+5),
                context.y(element.getY()+getDyOffset()),
                context.x(w2),
                context.y(h-hh+2));
        textField.setBounds(
                context.x(element.getX()+getDxOffset()+5),
                context.y(element.getY()+getDyOffset()+h-hh),
                context.x(w2),
                context.y(hh));
        textField.setBackground(color);
        }

    @Override
    public void showInfoMessage() {

    }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register =getRegister();
        if (!(register instanceof Meta2DataRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }

}
