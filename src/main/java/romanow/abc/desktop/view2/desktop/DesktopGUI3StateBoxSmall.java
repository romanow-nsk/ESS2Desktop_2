package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.constants.Values;

import javax.swing.*;
import java.awt.*;

public class DesktopGUI3StateBoxSmall extends DesktopGUI3StateBox {
    public DesktopGUI3StateBoxSmall() {
        setType(Values.GUI3StateBoxSmall);
        }
    private int iconsWarning[]={ 0xFFC8C8C8,0xFF00FFFF,0xFFFF0000,0xFFFFFFFF};
    private int iconsWorking[]={ 0xFFC8C8C8,0xFF00FF00,0xFFFF0000,0xFFFFFFFF};
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
    public void putValue(int vv) {
        JTextField bb = (JTextField) textField;
        int pair = (vv>>bitNum) & 03;
        int color = getContext().getForm().getFormLevel()==0 ? iconsWarning[pair] : iconsWorking[pair];
        bb.setBackground(new Color(color));
        }
    @Override
    protected int getSize(){
        return 15;
        }

}
