package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.Message;

import javax.swing.*;
import java.awt.*;

public class GUIData extends GUIElement {
    private JTextField textField;
    public GUIData(){
        type = Values.GUIData;
        }
    @Override
    public int getRegStep(){
        if (register instanceof MetaDataRegister)
            return ((MetaDataRegister)register).getStep();
        else
            return 0;
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        textField = new JTextField();
        int dd=element.getW2();
        if (dd==0) dd=100;
        textField.setBounds(
                context.x(element.getX()+dxOffset+element.getDx()+5),
                context.y(element.getY()+dyOffset),
                context.x(dd),
                context.y(25));
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textField);
        Color color=new Color(element.getColor());
        textField.setBackground(color);
        setInfoClick(textField);
        }
    public void showInfoMessage() {
        MetaDataRegister set = (MetaDataRegister)register;
        String ss = "Регистр данных "+(set.getRegNum()+regOffset)+" ["+set.getRegNum()+"] "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+="Потоковый  - "+(set.getStreamType()!=Values.DataStreamNone ? "да":"нет")+",";
        ss+=" Ед.изм. "+ set.getUnit();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(int vv) throws UniException {
        int type = register.getFormat();
        if (type==Values.FloatValue)
            textField.setText(""+Float.intBitsToFloat(vv));
        else
            textField.setText(valueWithPower(vv));
        }
    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent onEvent0) {
        super.setParams(context,meta,register0, element0,plm0,onEvent0);
        if (!(register instanceof MetaDataRegister || register instanceof MetaSettingRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
}
