package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2DateTime;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIData;
import romanow.abc.core.entity.metadata.view.Meta2GUIDateTime;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUIDateTime extends View2BaseDesktop {
    private JTextField textField;
    public DesktopGUIDateTime(){
        setType(Values.GUIDateTime);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        Meta2GUIDateTime element = (Meta2GUIDateTime) getElement();
        textField = new JTextField();
        int dd=element.getW2();
        if (dd==0) dd=100;
        int hh = element.getH();
        if (hh==0) hh=25;
        textField.setBounds(
                context.x(element.getX()+getDxOffset()+element.getDx()+5),
                context.y(element.getY()+getDyOffset()),
                context.dx(dd),
                context.dy(hh));
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.dy(12)));
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.LEFT);
        panel.add(textField);
        Color color=new Color(element.getColor());
        textField.setBackground(color);
        Color textColor = new Color(context.getView().getTextColor());
        textField.setBorder(BorderFactory.createLineBorder(textColor,1));
        textField.setForeground(textColor);
        setInfoClick(textField);
        }
    public void showInfoMessage() {
        Meta2Register set =  getRegister();
        String ss = "Регистр даты/времени "+toHex(set.getRegNum()+getRegOffset())
                +" ["+toHex(set.getRegNum())+"] "+set.getShortName()+"$"+set.getTitle()+"$";
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        Meta2Register register = getRegister();
        int type = register.getFormat();
        String ss = String.format("%2d",vv & 0x0FF);
        vv >>=8;
        ss = String.format("%2d:",vv & 0x0FF)+ss;
        textField.setText(ss);
        vv >>=8;
        ss = String.format("%2d:",vv & 0x0FF)+ss;
        vv >>=8;
        String ss2 = String.format("%2d-",vv & 0x0FF);
        vv >>=8;
        ss2 = ss2+String.format("%2d-",vv & 0x0FF);
        vv >>=8;
        ss2 = ss2+String.format("%2d",(vv & 0x0FF)+2000);
        textField.setText(" "+ss2+" "+ss);
        /*
        if (type==Values.FloatValue)
            textField.setText(""+Float.intBitsToFloat(vv));
        else{
            if (register instanceof Meta2DataRegister)
                textField.setText(((Meta2DataRegister)register).valueWithPower(vv));
            else
                textField.setText(((Meta2SettingRegister)register).valueWithPower(vv));
            }
         */
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register = getRegister();
        if (!(register instanceof Meta2DateTime))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
}
