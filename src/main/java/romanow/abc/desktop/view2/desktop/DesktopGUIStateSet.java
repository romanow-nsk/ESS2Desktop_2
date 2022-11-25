package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIStateSet;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;

public class DesktopGUIStateSet extends View2BaseDesktop {
    private JTextField textField;
    public DesktopGUIStateSet(){
        setType(Values.GUIStateSet);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        Meta2GUIStateSet element = (Meta2GUIStateSet) getElement();
        textField = new JTextField();
        textField.setBounds(
                context.x(element.getX()+element.getDx()+10),
                context.y(element.getY()),
                context.x(element.getW2()),
                context.y(25));
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textField);
        Color color=new Color(element.getColor());
        textField.setBackground(color);
        setInfoClick(textField);
        }

    @Override
    public void putValue(int vv) throws UniException {
        Meta2StateRegister reg = (Meta2StateRegister)  getRegister();
        Meta2State state = reg.getStates().getByCode(vv);
        if (state==null)
            textField.setText("Недопустимое состояние");
        else
            textField.setText(""+state.getTitle());
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register =  getRegister();
        if (!(register instanceof Meta2StateRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
    }

    @Override
    public void showInfoMessage() {

    }
}
