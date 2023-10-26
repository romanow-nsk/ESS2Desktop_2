package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIFormButton extends GUIElement {
    private JButton textField;
    public GUIFormButton(){
        type = Values.GUIFormButton;
        }

    @Override
    public void addToPanel(JPanel panel) {
        textField = new JButton();
        textField.setBounds(
                context.x(element.getX()),
                context.y(element.getY()),
                context.x(element.getDx()),
                context.y(25));
        textField.setText(element.getTitle());
        textField.setFont(new Font(Values.FontName, Font.PLAIN, context.y(12)));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                context.openForm(element.getClickFormName(),-1,0);
                }
            });
        setInfoClick(textField);
        panel.add(textField);
        Color color=new Color(element.getColor());
        }
    @Override
    public void putValue(long vv) throws UniException {}

    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent onEvent0) {
        super.setParams(context,meta,register0, element0,plm0,onEvent0);
        return null;
        }
    public boolean needRegister() {
        return false;
        }
}
