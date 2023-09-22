package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIIndexLabel;
import romanow.abc.core.entity.metadata.view.Meta2GUIRegW2;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;

public class DesktopGUIIndexLabel extends View2BaseDesktop {
    private JLabel label;
    public DesktopGUIIndexLabel(){
        setType(Values.GUIIndexLabel);
        }

    @Override
    public void addToPanel(JPanel panel) {
        label = setLabel(panel);
        setInfoClick(label);
        int level = ((Meta2GUIIndexLabel)getElement()).getStackLevel();
        label.setText("" + getElement().getTitle() + " " + (getContext().getIndex(level)+1));        }
    public void showInfoMessage() {
        String ss = "Индекс элемента формы уровня "+ ((Meta2GUIIndexLabel)getElement()).getStackLevel() ;
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {

        }
}
