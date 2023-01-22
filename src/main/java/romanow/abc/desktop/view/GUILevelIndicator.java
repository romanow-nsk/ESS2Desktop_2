package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.I_Success;

import javax.swing.*;
import java.awt.*;

public class GUILevelIndicator extends GUIElement {
    //private JProgressBar textField;
    private JPanel textField;
    public GUILevelIndicator(){
        type = Values.GUILevelIndicator;
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
        w2=element.getW2();
        if (w2==0) w2=100;
        h = element.getH();
        if (h==0) h=100;
        vMin = alarmMin - (alarmMax-alarmMin)*proc/100;
        vMax = alarmMax + (alarmMax-alarmMin)*proc/100;
        //textField = new JProgressBar();
        //textField.setOrientation(JProgressBar.VERTICAL);
        //textField.setMinimum(0);
        //textField.setMaximum(h);
        //textField.setBorderPainted(false);
        textField = new JPanel();
        textField.setBounds(
                context.x(element.getX()+dxOffset+5),
                context.y(element.getY()+dyOffset),
                context.x(w2),
                context.y(h));
        panel.add(textField);backColor = new Color(element.getColor());
        setInfoClick(textField);
        }

    @Override
    public void putValue(long xx) throws UniException {
        double vv = doubleWithPower(xx);
        Color color = Color.green;
        if (vv>=alarmMax || vv<=alarmMin)
            color=Color.red;
        else
        if (vv>=warnMax || vv<=warnMin)
            color=Color.yellow;
        int hh = (int)((vv-vMin)/(vMax-vMin)*h);
        Graphics gr = textField.getGraphics();
        gr.setColor(backColor);
        gr.fillRect(0,0,w2,h);
        gr.setColor(color);
        gr.fillRect(0,h-hh,w2,hh);
        }

    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent back0) {
        super.setParams(context,meta,register0, element0,plm0,back0);
        if (!(register instanceof MetaDataRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
}
