package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIFormButton;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view.MultiTextButton;
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
        FormContext2 context= getContext();
        Meta2GUI element = getElement();
        int hh = element.getH()==0 ? 25 : element.getH();
        textField = new JButton();
        textField.setBounds(
                context.x(element.getX()),
                context.y(element.getY()),
                context.dx(element.getDx()),
                context.dy(hh));
        setButtonParams(textField,true);
        setInfoClick(textField);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Meta2GUIFormButton elem =(Meta2GUIFormButton)element;
                if (elem.isOwnUnit()){
                    context.setIndex(elem.getUnitLevel()+1,elem.getUnitIdx());
                    }
                context.openForm(elem.getFormName(),FormContext2.ModeForce);
                }
            });
        panel.add(textField);
        }
    @Override
    public void putValue(long vv) throws UniException {}

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
