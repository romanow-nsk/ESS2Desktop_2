package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUITimeSetting;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.ESS2TimeSetting;
import romanow.abc.desktop.I_Success;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DesktopGUITimeSetting extends View2BaseDesktop {
    private JTextField textField;
    public DesktopGUITimeSetting(){
        setType(Values.GUITimeSetting);
        }
    private I_Success back=null;

    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        Meta2GUITimeSetting element = (Meta2GUITimeSetting) getElement();
        Meta2SettingRegister register = (Meta2SettingRegister) getRegister();
        int w2=element.getW2();
        if (w2==0) w2=100;
        textField = new JTextField();
        textField.setBounds(
                context.x(element.getX()+element.getDx()+5),
                context.y(element.getY()),
                context.dx(w2),
                context.dy(25));
        textField.setEditable(false);
        textField.setFont(new Font(Values.FontName, Font.PLAIN, context.y(12)));
        textField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textField);
        Color color=new Color(element.getColor());
        textField.setBackground(color);
        textField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()<2)
                    return;
                new ESS2TimeSetting(getArchitecture(), register, getDevice().getDriver(), new I_Success() {
                    @Override
                    public void onSuccess() {
                        if (back!=null)
                            back.onSuccess();
                    }
                });
                }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) { }
            });
        }

    @Override
    public void putValue(long zz) throws UniException {
        textField.setText(String.format("%2d:%2d:%2d",zz/3600,zz%3600/60,zz%60));
        }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register =  getRegister();
        if (register==null)
            return getRegisterTitle();
        if (!(register instanceof Meta2SettingRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
    }

    @Override
    public void showInfoMessage() {

    }
}
