package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUILevelIndicator;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.I_Success;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;

public class DesktopGUILevelIndicator2 extends View2BaseDesktop {
    private JProgressBar textField;
    public DesktopGUILevelIndicator2(){
        setType(Values.GUILevelIndicator);
        }
    private I_Success back=null;
    private double alarmMin=1;
    private double alarmMax=5;
    private double warnMin=2;
    private double warnMax=4;
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
        vMin = alarmMin - (alarmMax-alarmMin)*proc/100;
        vMax = alarmMax + (alarmMax-alarmMin)*proc/100;
        textField = new JProgressBar();
        textField.setOrientation(JProgressBar.VERTICAL);
        textField.setMinimum(0);
        textField.setMaximum(h);
        textField.setBorderPainted(false);
        textField.setBounds(
                context.x(element.getX()+getDxOffset()+5),
                context.y(element.getY()+getDyOffset()),
                context.x(w2),
                context.y(h));
        panel.add(textField);backColor = new Color(element.getColor());
        setInfoClick(textField);
        }

    @Override
    public void putValue(long zz) throws UniException {
        Meta2DataRegister register = (Meta2DataRegister)  getRegister();
        double vv = register.floatWithPower(getUnitIdx(),(int)zz);
        Color color = Color.green;
        if (vv>=alarmMax || vv<=alarmMin)
            color=Color.red;
        else
        if (vv>=warnMax || vv<=warnMin)
            color=Color.yellow;
        int hh = (int)((vv-vMin)/(vMax-vMin)*h);
        textField.setStringPainted(true);
        textField.setForeground(color);
        textField.setValue(hh);
        textField.setString("");
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register =getRegister();
        if (!(register instanceof Meta2DataRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }

    @Override
    public void showInfoMessage() {

    }
}
