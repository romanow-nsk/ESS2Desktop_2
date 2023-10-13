package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUILabel;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;

public class DesktopGUILabel extends View2BaseDesktop {
    public DesktopGUILabel(){
        setType(Values.GUILabel);
        }

    @Override
    public void addToPanel(JPanel panel) {
        JLabel label = setLabel(panel);          // Power==1 - с цветом
        FormContext2 context = getContext();
        Color out;
        Meta2GUILabel element = (Meta2GUILabel) getElement();
        if (element.getColor()==0 || element.getColor()==0xf0f0f0 || element.isCommonColor()){  // Заплатка
            out =   new Color(context.getView().getTextColor() | 0xFF000000);
            }
        else{
            out =  new Color(element.getColor() | 0xFF000000);
            }
        label.setForeground(out);
        }

    @Override
    public void showInfoMessage() {}

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        return null;
        }
    @Override
    public void putValue(long vv) throws UniException {}
}
