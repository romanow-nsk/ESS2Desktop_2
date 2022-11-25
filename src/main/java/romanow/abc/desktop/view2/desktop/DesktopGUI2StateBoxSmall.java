package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.constants.Values;

import javax.swing.*;
import java.awt.*;

public class DesktopGUI2StateBoxSmall extends DesktopGUI2StateBox {
    public DesktopGUI2StateBoxSmall() {
        setType(Values.GUI2StateBoxSmall);
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
