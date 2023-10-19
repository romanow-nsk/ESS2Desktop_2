package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIStateSelector;
import romanow.abc.core.entity.metadata.view.Meta2GUIStateSet;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.OK;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUIStateSelector extends View2BaseDesktop {
    private Choice list;
    private Meta2GUIStateSelector element;
    private Meta2StateRegister reg;
    private Meta2EntityList<Meta2State> states;
    private boolean valid=false;
    private int lastState;
    private boolean busy=false;
    public DesktopGUIStateSelector(){
        setType(Values.GUIStateSelector);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        element = (Meta2GUIStateSelector) getElement();
        reg = (Meta2StateRegister)  getRegister();
        states = reg.getStates();
        states.createMap();
        list = new Choice();
        list.setBounds(
                context.x(element.getX()+element.getDx()+10),
                context.y(element.getY()),
                context.dx(element.getW2()),
                context.dy(25));
        list.setFont(createFont());
        Color textColor = new Color(context.getView().getTextColor());
        list.setForeground(textColor);
        Color cc = getElemBackColor();
        list.setBackground(cc);
        list.add("???");
        for(Meta2State state : states.getList())
            list.add(state.getTitle());
        list.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (busy)
                    return;
                int idx = list.getSelectedIndex();
                if (idx==0){
                    selectCurrent();
                    return;
                    }
                Meta2State ss = states.get(idx-1);
                new OK(200, 200, element.getTitle()+" выбрать "+ss.getTitle(), new I_Button() {
                    @Override
                    public void onPush() {
                        try {
                            writeMainRegister(ss.getCode());
                            } catch (UniException ex) {
                                String сс = "Ошибка записи состояния: "+ex.toString();
                                context.popup(сс);
                                System.out.println(сс);
                                }
                        }
                    });
                    }
                });
        panel.add(list);
        setInfoClick(list);
        }

    private void selectCurrent(){
        if (!valid){
            list.select(0);
            return;
            }
        int idx = 0;
        for(idx=0; idx<states.size();idx++)
            if (states.get(idx).getCode()==lastState)
                break;
        busy = true;
        if (idx==states.size())
            list.select(0);
        else
            list.select(idx+1);
        busy = false;
        }

    @Override
    public void putValue(long vv) throws UniException {
        valid = true;
        lastState = (int)vv;
        selectCurrent();
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register =  getRegister();
        if (!(register instanceof Meta2StateRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
    }

    @Override
    public void showInfoMessage() {
        Meta2StateRegister set = (Meta2StateRegister) getRegister();
        String ss = "Значения уставки из списка:  "+toHex(set.getRegNum())+" "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+="Удаленное управление - "+(set.isRemoteEnable() ? "да":"нет");
        new Message(300,300,ss,Values.PopupMessageDelay);
    }
}
