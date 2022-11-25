package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.Message;

import javax.swing.*;

public class GUIDataLabel extends GUIElement {
    private JLabel label;
    public GUIDataLabel(){
        type = Values.GUIDataLabel;
        }

    @Override
    public void addToPanel(JPanel panel) {
        label = setLabel(panel);
        setInfoClick(label);
        }
    public void showInfoMessage() {
        MetaDataRegister set = (MetaDataRegister)register;
        String ss = "Регистр данных "+(set.getRegNum()+regOffset)+" ["+set.getRegNum()+"] "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+="Потоковый  - "+(set.getStreamType()!=Values.DataStreamNone ? "да":"нет")+",";
        ss+=" Ед.изм. "+ set.getUnit();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public int getRegStep(){
        if (register instanceof MetaDataRegister)
            return ((MetaDataRegister)register).getStep();
        else
            return 0;
        }
    @Override
    public void putValue(int vv) throws UniException {
        int type = register.getFormat();
        if (type==Values.FloatValue)
            label.setText(element.getTitle()+" "+Float.intBitsToFloat(vv));
        else
            label.setText(element.getTitle()+" "+valueWithPower(vv));
        }
    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent onEvent0) {
        super.setParams(context,meta,register0, element0,plm0,onEvent0);
        if (!(register instanceof MetaDataRegister || register instanceof MetaSettingRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
}
