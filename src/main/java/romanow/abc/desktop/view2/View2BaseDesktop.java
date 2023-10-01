package romanow.abc.desktop.view2;

import lombok.Getter;
import romanow.abc.core.entity.metadata.Meta2GUIView;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.desktop.UtilsDesktop;

import javax.swing.*;
import java.awt.*;

public abstract class View2BaseDesktop extends View2Base implements I_View2Desktop {
    @Getter private JLabel label;
    public Component getComponent(){ return null; }
    public void setTextFieldParams(JTextField textField) {
        setComponentParams(textField,true);
        textField.setText(element.getTitle());
        textField.setHorizontalAlignment(element.isOnCenter() ? JTextField.CENTER : JTextField.LEFT);
        }
    public void setButtonParams(JButton textField) {
        setButtonParams(textField, false);
        }
    public void setButtonParams(JButton textField, boolean noOneString) {
        String ss = element.getTitle();
        if (noOneString) ss ="<html>"+(element.isOnCenter() ? "<center>" : "") + ss.replaceAll(" ", "<br>") + "</html>";
        ss = ss.replaceAll("_"," ");
        textField.setText(ss);
        textField.setHorizontalAlignment(element.isOnCenter() ? JTextField.CENTER : JTextField.LEFT);
        setComponentParams(textField,false);
        }
    public Font createFont() {
        int fontSize = element.getFontSize();
        if (fontSize == 0) fontSize = 12;
        int type = element.isBold() ? Font.BOLD : Font.PLAIN;
        return new Font("Arial Cyr", type, context.dy(fontSize));
        }
    public Color getElemBackColor(){
        if (element.isBackColor()){
            return  new Color(context.getView().getBackColor() | 0xFF000000);
            }
        else{
            if (element.getColor()==0 || element.isCommonColor()){
                return  new Color(context.getView().getCommonBackColor() | 0xFF000000);
                }
            else{
                return new Color(element.getColor() | 0xFF000000);
                }
            }
        }
    public Color getLabelColor(){
        if (element.isLabelBackColor()){
            return  new Color(context.getView().getBackColor() | 0xFF000000);
            }
        else{
            if (element.getLabelColor()==0 || element.isLabelCommonColor()){
                return new Color(context.getView().getCommonBackColor()  | 0xFF000000);
                }
            else{
                return new Color(element.getLabelColor()  | 0xFF000000);
                }
            }
        }
    public static void setButtonParams(JButton textField, String title, boolean noOneString, FormContext2 context){
        Meta2GUIView view = context.getView();
        textField.setBackground(new Color(view.getMenuButtonOffColor()));
        int fontSize = view.getMenuButtonFontSize();
        if (fontSize == 0) fontSize = 12;
        int type = view.isMenuFontBold()? Font.BOLD : Font.PLAIN;
        Font font = new Font("Arial Cyr", type, context.dy(fontSize));
        textField.setFont(font);
        Color textColor = new Color(view.getMenuButtonTextColor());
        textField.setForeground(textColor);
        String ss = title;
        if (noOneString) ss ="<html><center>" + ss.replaceAll(" ", "<br>") + "</html>";
        ss = ss.replaceAll("_"," ");
        textField.setText(ss);
        textField.setHorizontalAlignment(JTextField.CENTER);
        }
    public void setComponentParams(JComponent textField, boolean border){
        textField.setFont(createFont());
        Color textColor = new Color(context.getView().getTextColor());
        if (border){
            textField.setBorder(BorderFactory.createLineBorder(textColor,1));
            textField.setOpaque(true);
            }
        textField.setForeground(textColor);
        Color cc = getElemBackColor();
        textField.setBackground(cc);
        }
    public JLabel setLabel(JPanel panel){
        String text = element.getTitle();
        //------------- TODO ------------------- подпись индекса --------------------------------
        //if (getType()== Values.GUILabel && inGroup)               // НЕ ООП !!!!!!!!!!!!!!!!!!!
        //    text += " "+(groupIndex+1);
        int hh = element.getH();
        if (hh==0) hh=25;
        label = new JLabel();
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
        label.setBackground(getLabelColor());
        int fontSize = element.getFontSize();
        if (fontSize==0) fontSize=12;
        int type = element.isLabelBold()? Font.BOLD : Font.PLAIN;
        label.setFont(new Font("Arial Cyr", type, context.dy(fontSize)));
        label.setHorizontalAlignment(JTextField.LEFT);
        if (element.isLabelOnCenter())
            label.setHorizontalAlignment(JTextField.CENTER);
        else
            if (element.isLabelOnRight())
                label.setHorizontalAlignment(JTextField.RIGHT);
        setInfoClick(label);
        panel.add(label);
        return label;
        }


}
