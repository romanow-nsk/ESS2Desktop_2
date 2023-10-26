package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;

public class GUIStateSet extends GUIElement {
    private JTextField textField;
    public GUIStateSet(){
        type = Values.GUIStateSet;
        }

    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        textField = new JTextField();
        textField.setBounds(
                context.x(element.getX()+element.getDx()+10),
                context.y(element.getY()),
                context.x(element.getW2()),
                context.y(25));
        textField.setFont(new Font(Values.FontName, Font.PLAIN, context.y(12)));
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textField);
        Color color=new Color(element.getColor());
        textField.setBackground(color);
        setInfoClick(textField);
        }

    @Override
    public void putValue(long vv) throws UniException {
        MetaCommandRegister reg = (MetaCommandRegister)register;
        for(MetaCommand cmd : Collections.unmodifiableList(reg.getCommands())){
            if (cmd.getCode()==vv){
                textField.setText(""+cmd.getTitle());
                return;
                }
            }
        textField.setText("Недопустимое состояние");
        }

    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent onEvent0) {
        super.setParams(context,meta, register0, element0,plm0,onEvent0);
        if (!(register instanceof MetaCommandRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        MetaCommandRegister reg = (MetaCommandRegister)register;
        if (reg.isCommand())
            return "Регистр команд вместо состояний "+register.getRegNum();
        return null;
    }
}
