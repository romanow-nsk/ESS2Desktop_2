package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.I_Success;

import javax.swing.*;
import java.awt.*;

public class GUILevelIndicator3 extends GUIElement {
    private JTextField textField;
    private JTextField high;
    public GUILevelIndicator3(){
        type = Values.GUILevelIndicator;
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
                context.x(element.getX()+dxOffset+5),
                context.y(element.getY()+dyOffset),
                context.x(w2),
                context.x(h));
        backColor = new Color(element.getColor());
        textField.setBackground(backColor);
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
        panel.add(textField);
        panel.add(high);
        }
    @Override
    public int getRegStep(){
        if (register instanceof MetaDataRegister)
            return ((MetaDataRegister)register).getStep();
        else
            return 0;
        }
    @Override
    public void putValue(int zz) throws UniException {
        if (settingsValues==null){
            high.setBounds(
                    context.x(element.getX()+dxOffset+5),
                    context.y(element.getY()+dyOffset),
                    context.x(w2),
                    context.y(h));
            return;
            }
        double vv = doubleWithPower(zz);
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
                context.x(element.getX()+dxOffset+5),
                context.y(element.getY()+dyOffset),
                context.x(w2),
                context.y(h-hh+2));
        textField.setBounds(
                context.x(element.getX()+dxOffset+5),
                context.y(element.getY()+dyOffset+h-hh),
                context.x(w2),
                context.y(hh));
        textField.setBackground(color);
        }

    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent back0) {
        super.setParams(context,meta,register0, element0,plm0,back0);
        if (!(register instanceof MetaDataRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
    @Override
    public boolean needSettingsValues(){
        return true;
        }
    @Override
    public void setSettingsValues(double values[]){
        settingsValues = values;
        }
    public double[] getSettingsValues(){
        return settingsValues;
        }
}
