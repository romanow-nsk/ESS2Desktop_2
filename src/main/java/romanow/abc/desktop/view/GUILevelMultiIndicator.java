package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.I_Success;

import javax.swing.*;
import java.awt.*;

public class GUILevelMultiIndicator extends GUIElement {
    private int regNums[] = new int[3];
    private int hh[] = new int[3];
    private double vv[] = new double[3];
    private int regNum=0;
    private int regNum2=0;
    private int regNum3=0;
    private JTextField textField;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField high;
    public GUILevelMultiIndicator(){
        type = Values.GUILevelMultiIndicator;
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
        textField2 = new JTextField();
        textField3 = new JTextField();
        high = new JTextField();
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
    public int getRegStep(){
        if (register instanceof MetaDataRegister)
            return ((MetaDataRegister)register).getStep();
        else
            return 0;
        }
    @Override
    public void putValue(long vv) throws UniException {}

    private Color getValueColor(double vv){
        Color color = Color.gray;
        if (settingsValues==null)
            return color;
        color = Color.green;
        if (vv >= settingsValues[0] || vv <= settingsValues[3])
            color = Color.red;
        else if (vv >= settingsValues[1] || vv <= settingsValues[2])
             color = Color.yellow;
        return color;
        }

    @Override
    public void putValue(int zz[]) throws UniException {
        //System.out.println(zz[0]+" "+zz[1]+" "+zz[2]);
        if (settingsValues==null){
            high.setBounds(
                    context.x(element.getX()+dxOffset+5),
                    context.y(element.getY()+dyOffset),
                    context.x(w2),
                    context.y(h));
            return;
            }
        for(int i=0;i<3;i++){
            vv[i] = doubleWithPower(zz[i]);
            if (vv[i]<vMin) vv[i]=vMin*1.03;
            if (vv[i]>vMax) vv[i]=vMax*0.98;
            }
        for(int i=0;i<3;i++)
            hh[i] = (int)((vv[i]-vMin)/(vMax-vMin)*h);
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
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent back0) {
        super.setParams(context,meta,register0, element0,plm0,back0);
        regNum = register0.getRegNum();
        regNum2 = element0.getRegNum2();
        regNum3 = element0.getRegNum3();
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
    public int []getRegNum() {
        regNums[0]=regNum+regOffset;
        regNums[1]=regNum2+regOffset;
        regNums[2]=regNum3+regOffset;
        return regNums;
        }
}
