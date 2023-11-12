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
    private boolean isButton = false;
    private JComponent textField;
    private Meta2GUIDateTime element;
    public DesktopGUIDateTime(){
        setType(Values.GUIDateTime);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        element = (Meta2GUIDateTime) getElement();
        isButton = !(element.isOneString() || element.isOnlyDate() || element.isOneString());
        textField = isButton ? new JButton() : new JTextField();
        int dd=element.getW2();
        if (dd==0) dd=100;
        int hh = element.getH();
        if (hh==0) hh=25;
        textField.setBounds(
                context.x(element.getX()+getDxOffset()+element.getDx()+5),
                context.y(element.getY()+getDyOffset()),
                context.dx(dd),
                context.dy(hh));
        if (isButton)
            setButtonParams((JButton) textField,true);
        else
            setTextFieldParams((JTextField) textField);
        panel.add(textField);
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
        String ss = String.format("%02d",vv & 0x0FF);
        vv >>=8;
        ss = String.format("%02d:",vv & 0x0FF)+ss;
        vv >>=8;
        ss = String.format("%02d:",vv & 0x0FF)+ss;
        vv >>=8;
        String ss2 = String.format("%02d-",vv & 0x0FF);
        vv >>=8;
        ss2 = ss2+String.format("%02d-",vv & 0x0FF);
        vv >>=8;
        ss2 = ss2+String.format("%02d",(vv & 0x0FF)+2000);
        if (isButton)
            ((JButton)textField).setText("<html>"+(element.isLabelOnCenter() ? "<center>" : "")+ss2 + "<br>"+ss+"</html>");
        else{
            JTextField textField1 = (JTextField) textField;
        if (element.isOnlyTime())
            textField1.setText(ss);
        else
            if (element.isOnlyDate())
                textField1.setText(ss2);
            else
                textField1.setText(ss2+" "+ss);
            }
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register = getRegister();
        if (register==null)
            return getRegisterTitle();
        if (!(register instanceof Meta2DateTime))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
}
