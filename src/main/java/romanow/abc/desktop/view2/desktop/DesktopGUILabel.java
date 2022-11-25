package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;

public class DesktopGUILabel extends View2BaseDesktop {
    public DesktopGUILabel(){
        setType(Values.GUILabel);
        }

    @Override
    public void addToPanel(JPanel panel) {
        JLabel label = setLabel(panel);          // Power==1 - с цветом
        setInfoClick(label);
        }

    @Override
    public void showInfoMessage() {}

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        return null;
        }
    @Override
    public void putValue(int vv) throws UniException {}
}
