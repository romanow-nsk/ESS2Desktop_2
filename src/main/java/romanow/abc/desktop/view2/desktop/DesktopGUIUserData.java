package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUILabel;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subjectarea.AccessManager;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;

public class DesktopGUIUserData extends DesktopGUILabel {
    public DesktopGUIUserData(){
        setType(Values.GUIUserData);
        }
    @Override
    public void addToPanel(JPanel panel) {
        super.addToPanel(panel);
        AccessManager manager = getContext().getManager();
        String ss = Values.constMap().getGroupMapByValue("AccessLevel").get(manager.getCurrentAccessLevel()).title();
        getLabel().setText(manager.getUser().getTitle()+": "+ss);
    }
}
