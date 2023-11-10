package romanow.abc.desktop.view2.desktop;

import lombok.Getter;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIData;
import romanow.abc.core.entity.metadata.view.Meta2GUIRegW2;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUIData extends View2BaseDesktop {
    @Getter private JTextField textField;
    public DesktopGUIData(){
        setType(Values.GUIData);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        Meta2GUIData element = (Meta2GUIData) getElement();
        textField = new JTextField();
        int dd=element.getW2();
        if (dd==0) dd=100;
        int hh = element.getH();
        if (hh==0) hh=25;
        textField.setBounds(
                context.x(element.getX()+getDxOffset()+element.getDx()+5),
                context.y(element.getY()+getDyOffset()),
                context.dx(dd),
                context.dy(hh));
        setTextFieldParams(textField);
        textField.setEditable(false);
        panel.add(textField);
        setInfoClick(textField);
        }
    public Component getComponent(){ return textField; }
    public void showInfoMessage() {
        Meta2Register set =  getRegister();
        String ss = "Регистр данных ["+getUnitIdx()+"] "+toHex(set.getRegNum()+getRegOffset())
                +" ["+toHex(set.getRegNum())+"] "+set.getShortName()+"$"+set.getTitle()+"$";
        if (set instanceof Meta2DataRegister){
            Meta2DataRegister set2 = (Meta2DataRegister)set;
            ss+="Потоковый  - "+(set2.getStreamType()!=Values.DataStreamNone ? "да":"нет")+",";
            ss+=" Ед.изм. "+ set2.getUnit();
            }
        else{
            ss+=" Ед.изм. "+ ((Meta2SettingRegister)set).getUnit();
            }
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        Meta2Register register = getRegister();
        Meta2GUIRegW2 metaGUI = (Meta2GUIRegW2)getElement();
        textField.setText(register.regValueToString(getUnitIdx(),(int)vv,metaGUI));
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register = getRegister();
        if (register==null)
            return getRegisterTitle();
        if (!(register instanceof Meta2DataRegister || register instanceof Meta2SettingRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
}
