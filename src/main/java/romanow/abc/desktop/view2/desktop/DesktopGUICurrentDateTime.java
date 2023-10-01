package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DateTime;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUICurrentDateTime;
import romanow.abc.core.entity.metadata.view.Meta2GUIDateTime;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.utils.OwnDateTime;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUICurrentDateTime extends View2BaseDesktop {
    private JButton textField;
    private Meta2GUICurrentDateTime element;
    public DesktopGUICurrentDateTime(){
        setType(Values.GUICurrentDateTime);
        }
    @Override
    public void addToPanel(JPanel panel) {
        // setLabel(panel);
        FormContext2 context= getContext();
        element = (Meta2GUICurrentDateTime) getElement();
        textField = new JButton();
        int dd=element.getDx();
        if (dd==0) dd=100;
        int hh = element.getH();
        if (hh==0) hh=25;
        textField.setBounds(
                context.x(element.getX()+getDxOffset()),
                context.y(element.getY()+getDyOffset()),
                context.dx(dd),
                context.dy(hh));
        setButtonParams(textField,true);
        panel.add(textField);
        setInfoClick(textField);
        }
    public void showInfoMessage() {
        Meta2Register set =  getRegister();
        String ss = "Текущая дата/время ";
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {}
    @Override
    public void repaintBefore(){
        OwnDateTime vv = new OwnDateTime(System.currentTimeMillis());
        String ss = String.format("%02d:%02d:%02d",vv.hour(),vv.minute(),vv.second());
        String ss2 = String.format("%02d-%02d-%02d",vv.day(),vv.month(),vv.year());
        if (element.isOnlyTime())
            textField.setText(ss);
        else
            textField.setText("<html>"+(element.isLabelOnCenter() ? "<center>" : "")+ss2 + "<br>"+ss+"</html>");
        }
}
