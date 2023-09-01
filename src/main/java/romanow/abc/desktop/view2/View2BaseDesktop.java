package romanow.abc.desktop.view2;

import romanow.abc.desktop.UtilsDesktop;

import javax.swing.*;
import java.awt.*;

public abstract class View2BaseDesktop extends View2Base implements I_View2Desktop {
    public void setTextFieldParams(JTextField textField) {
        setComponentParams(textField,true);
        textField.setText(element.getTitle());
        textField.setHorizontalAlignment(element.isOnCenter() ? JTextField.CENTER : JTextField.LEFT);
        }
    public void setButtonParams(JButton textField) {
        setButtonParams(textField, false);
        }
    public void setButtonParams(JButton textField, boolean noOneString) {
        setComponentParams(textField,false);
        String ss = element.getTitle();
        textField.setText(!noOneString ? ss : "<html>"+(element.isOnCenter() ? "<center>" : "") + ss.replaceAll(" ", "<br>") + "</html>");
        textField.setHorizontalAlignment(element.isOnCenter() ? JTextField.CENTER : JTextField.LEFT);
        }
    public Font createFont() {
        int fontSize = element.getFontSize();
        if (fontSize == 0) fontSize = 12;
        int type = element.isBold() ? Font.BOLD : Font.PLAIN;
        return new Font("Arial Cyr", type, context.dy(fontSize));
        }
    public void setComponentParams(JComponent textField, boolean border){
        if (element.isBackColor()){
            textField.setBackground(new Color(context.getView().getBackColor()));
            }
        else{
            if (element.getColor()==0 || element.isCommonColor()){
                textField.setBackground(new Color(context.getView().getCommonBackColor()));
                }
            else{
                textField.setBackground(new Color(element.getColor()));
                }
            }
        textField.setFont(createFont());
        Color textColor = new Color(context.getView().getTextColor());
        if (border){
            textField.setBorder(BorderFactory.createLineBorder(textColor,1));
            textField.setOpaque(true);
            }
        textField.setForeground(textColor);
        }
    public JLabel setLabel(JPanel panel){
        String text = element.getTitle();
        //------------- TODO ------------------- подпись индекса --------------------------------
        //if (getType()== Values.GUILabel && inGroup)               // НЕ ООП !!!!!!!!!!!!!!!!!!!
        //    text += " "+(groupIndex+1);
        int hh = element.getH();
        if (hh==0) hh=25;
        JLabel label = new JLabel();
        label.setBounds(
                context.x(element.getX()+dxOffset),
                context.y(element.getY()+dyOffset),
                context.dx(element.getDx()),
                context.dy(hh));
        int size = element.getStringSize();
        if (size==0)
            label.setText("  "+text);
        else
            UtilsDesktop.setLabelText(label,text,size);
        //setComponentParams(label,false,true);
        label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        label.setForeground(new Color(context.getView().getTextColor()));
        label.setOpaque(true);
        if (element.isLabelBackColor()){
            label.setBackground(new Color(context.getView().getBackColor()));
            }
        else{
            if (element.getLabelColor()==0 || element.isLabelCommonColor()){
                label.setBackground(new Color(context.getView().getCommonBackColor()));
                }
            else{
                label.setBackground(new Color(element.getLabelColor()));
                }
            }
        int fontSize = element.getFontSize();
        if (fontSize==0) fontSize=12;
        int type = element.isLabelBold()? Font.BOLD : Font.PLAIN;
        label.setFont(new Font("Arial Cyr", type, context.dy(fontSize)));
        label.setHorizontalAlignment(element.isLabelOnCenter() ? JTextField.CENTER : JTextField.LEFT);
        panel.add(label);
        return label;
        }


}
