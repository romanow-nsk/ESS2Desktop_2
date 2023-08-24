package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIRegW2;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;

public class DesktopGUIDataLabel extends View2BaseDesktop {
    private JLabel label;
    public DesktopGUIDataLabel(){
        setType(Values.GUIDataLabel);
        }

    @Override
    public void addToPanel(JPanel panel) {
        label = setLabel(panel);
        setInfoClick(label);
        }
    public void showInfoMessage() {
        Meta2Register set = (Meta2Register) getRegister();
        String ss = "Регистр данных/уставка "+(set.getRegNum()+getRegOffset()+" ["+set.getRegNum()+"] "+set.getShortName()+"$"+set.getTitle()+"$");
        if (set instanceof Meta2DataRegister)
            ss+="Потоковый  - "+(((Meta2DataRegister)set).getStreamType()!=Values.DataStreamNone ? "да":"нет")+",";
        ss+=" Ед.изм. "+ set.getUnit();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        Meta2Register register = (Meta2Register) getRegister();
        int type = register.getFormat();
        Meta2GUIRegW2 metaGUI = (Meta2GUIRegW2)getElement();
        String ss = register.regValueToString(getUnitIdx(),(int)vv,metaGUI);
        label.setText(" " + getElement().getTitle() + " = " + ss + " " + register.getUnit());
        }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register =  getRegister();
        if (!(register instanceof Meta2DataRegister || register instanceof Meta2SettingRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
}
