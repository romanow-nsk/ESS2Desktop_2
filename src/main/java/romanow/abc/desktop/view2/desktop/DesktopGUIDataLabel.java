package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
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
        Meta2DataRegister set = (Meta2DataRegister) getRegister();
        String ss = "Регистр данных "+(set.getRegNum()+getRegOffset()+" ["+set.getRegNum()+"] "+set.getShortName()+"$"+set.getTitle()+"$");
        ss+="Потоковый  - "+(set.getStreamType()!=Values.DataStreamNone ? "да":"нет")+",";
        ss+=" Ед.изм. "+ set.getUnit();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        Meta2Register register = (Meta2Register) getRegister();
        int type = register.getFormat();
        if (type == Values.FloatValue)
            label.setText(" " + getElement().getTitle() + " = " + Double.longBitsToDouble(vv) + " " + register.getUnit());
        else
            label.setText(" " + getElement().getTitle() + " = " + register.regValueToFloat(getUnitIdx(),(int)vv) + " " + register.getUnit());
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
