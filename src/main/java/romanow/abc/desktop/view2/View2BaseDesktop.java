package romanow.abc.desktop.view2;

import romanow.abc.desktop.UtilsDesktop;

import javax.swing.*;
import java.awt.*;

public abstract class View2BaseDesktop extends View2Base implements I_View2Desktop{
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
                context.x(element.getDx()),
                context.y(hh));
        int size = element.getStringSize();
        if (size==0)
            label.setText("  "+text);
        else
            UtilsDesktop.setLabelText(label,text,size);
        label.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        label.setForeground(new Color(context.getView().getTextColor()));
        label.setOpaque(true);
        if (element.getColor()==0 || element.isCommonColor()){
            label.setBackground(new Color(context.getView().getLabelBackColor()));
            }
        else{
            label.setBackground(new Color(element.getColor()));
            }
        int fontSize = element.getFontSize();
        if (fontSize==0) fontSize=12;
        int type = element.isBold()? Font.BOLD : Font.PLAIN;
        label.setFont(new Font("Arial Cyr", type, context.y(fontSize)));
        panel.add(label);
        return label;
        }


}
