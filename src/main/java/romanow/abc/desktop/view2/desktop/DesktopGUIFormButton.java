package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIFormButton;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesktopGUIFormButton extends View2BaseDesktop {
    private JButton textField;
    public DesktopGUIFormButton(){
        setType(Values.GUIFormButton);
        }

    @Override
    public void addToPanel(JPanel panel) {
        textField = new JButton();
        FormContext2 context= getContext();
        Meta2GUI element = getElement();
        textField.setBounds(
                context.x(element.getX()),
                context.y(element.getY()),
                context.x(element.getDx()),
                context.y(25));
        textField.setText(element.getTitle());
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.openForm(((Meta2GUIFormButton)element).getFormName(),FormContext2.ModeForce);
                }
            });
        setInfoClick(textField);
        panel.add(textField);
        Color color=new Color(element.getColor());
        }
    @Override
    public void putValue(int vv) throws UniException {}

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        return null;
        }

    @Override
    public void showInfoMessage() {
        new Message(300,300, "Переход на форму "+((Meta2GUIFormButton)getElement()).getFormName(),Values.PopupMessageDelay);
    }

    public boolean needRegister() {
        return false;
        }
}
