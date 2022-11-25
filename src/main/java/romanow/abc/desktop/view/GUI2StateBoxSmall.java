package romanow.abc.desktop.view;

import romanow.abc.core.constants.Values;

import javax.swing.*;
import java.awt.*;

public class GUI2StateBoxSmall  extends GUI2StateBox {
    public GUI2StateBoxSmall() {
        type = Values.GUI2StateBoxSmall;
    }
    @Override
    protected JComponent createComponent() {
        return new JTextField();
        }
    @Override
    protected void viewComponent() {
        JTextField bb = (JTextField) textField;
        bb.setEditable(false);
        bb.setText("");
        }
    @Override
    protected void putValueOwn(int cc) {
        JTextField bb = (JTextField) textField;
        bb.setBackground(new Color(cc));
        bb.setText("");
        }
    @Override
    protected int getSize(){
        return 15;
        }

}
