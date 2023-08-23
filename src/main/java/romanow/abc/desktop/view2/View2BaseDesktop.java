package romanow.abc.desktop.view2;

import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIData;
import romanow.abc.desktop.UtilsDesktop;

import javax.swing.*;
import java.awt.*;

public abstract class View2BaseDesktop extends View2Base implements I_View2Desktop {
    public void setTextFieldParams(JTextField textField) {
        setComponentParams(textField);
        textField.setText(element.getTitle());
        textField.setHorizontalAlignment(JTextField.CENTER);
        }
    public void setButtonParams(JButton textField) {
        setButtonParams(textField, false);
        }
    public void setButtonParams(JButton textField, boolean noOneString) {
        setComponentParams(textField,false);
        String ss = element.getTitle();
        textField.setText(!noOneString ? ss : "<html>" + ss.replaceAll(" ", "<br>") + "</html>");
        textField.setHorizontalAlignment(JTextField.CENTER);
        }
    public Font createFont() {
        int fontSize = element.getFontSize();
        if (fontSize == 0) fontSize = 12;
        int type = element.isBold() ? Font.BOLD : Font.PLAIN;
        return new Font("Arial Cyr", type, context.dy(fontSize));
        }
    public void setComponentParams(JComponent textField) {
        setComponentParams(textField, true);
        }
    public void setComponentParams(JComponent textField, boolean border){
         setComponentParams(textField,border,false);
        }
    public void setComponentParams(JComponent textField, boolean border, boolean label){
        if (element.getColor()==0 || element.isCommonColor()){
            if (label)
                textField.setBackground(new Color(context.getView().getLabelBackColor()));
            else
                textField.setBackground(new Color(context.getView().getBackColor()));
                }
        else{
            textField.setBackground(new Color(element.getColor()));
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
        if (element.getLabelColor()==0 || element.isLabelCommonColor()){
            label.setBackground(new Color(context.getView().getLabelBackColor()));
            }
        else{
            label.setBackground(new Color(element.getLabelColor()));
            }
        int fontSize = element.getFontSize();
        if (fontSize==0) fontSize=12;
        int type = element.isLabelBold()? Font.BOLD : Font.PLAIN;
        label.setFont(new Font("Arial Cyr", type, context.dy(fontSize)));
        panel.add(label);
        return label;
        }


}
