package romanow.abc.desktop.view;

import javax.swing.*;
import java.awt.*;

public class MultiTextButton extends JButton {
    private Font font;
    public MultiTextButton(Font font0){
        setLayout(new BorderLayout());
        font = font0;
        setFont(font);
        }
    public void setText(String text){
        int idx=text.indexOf(" ");
        if (idx==-1)
            super.setText(text);
        else{
            JLabel label = new JLabel(text.substring(0,idx));
            label.setFont(font);
            label.setHorizontalAlignment(JTextField.CENTER);
            label.setVerticalAlignment(JTextField.CENTER);
            add(BorderLayout.NORTH,label);
            label = new JLabel(text.substring(idx+1));
            label.setFont(font);
            label.setHorizontalAlignment(JTextField.CENTER);
            label.setVerticalAlignment(JTextField.CENTER);
            add(BorderLayout.SOUTH,label);
            }
        }
}
