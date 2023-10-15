package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.*;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUI2StateBox;
import romanow.abc.core.entity.metadata.view.Meta2GUIBitsList;
import romanow.abc.core.entity.metadata.view.Meta2GUIStateSelector;
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

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUIBitsList extends View2BaseDesktop {
    private Choice list;
    private Meta2GUIBitsList element;
    private Meta2BitRegister reg;
    private Meta2EntityList<Meta2Bit> bits;
    private boolean valid=false;
    private int lastState;
    private boolean busy=false;
    public DesktopGUIBitsList(){
        setType(Values.GUIBitsList);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        element = (Meta2GUIBitsList) getElement();
        reg = (Meta2BitRegister)  getRegister();
        bits = reg.getBits();
        bits.createMap();
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
        panel.add(list);
        setInfoClick(list);
        }

    @Override
    public void putValue(long vv) throws UniException {
        list.removeAll();
        for(int nBit=0; nBit<16;nBit++){
            if ((vv & 1)!=0 ){
                Meta2Bit bit = bits.getByCode(nBit);
                if (bit!=null)
                    list.add(bit.getTitle());
                }
            vv >>=1;
            }
        if (list.getItemCount()==0)
            list.add("...");
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register =  getRegister();
        if (!(register instanceof Meta2BitRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }

    @Override
    public void showInfoMessage() {
        Meta2BitRegister set = (Meta2BitRegister) getRegister();
        String ss = "Список разрядов регистра "+toHex(set.getRegNum()+getRegOffset())+" ["+toHex(set.getRegNum())+"]"+set.getShortName()+"$"+set.getTitle();
        new Message(300,300,ss,Values.PopupMessageDelay);

    }
}
