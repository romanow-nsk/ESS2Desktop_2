package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Bit;
import romanow.abc.core.entity.metadata.Meta2BitRegister;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIMultiBitState;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DesktopGUIMultiBitState extends View2BaseDesktop {
    private int regNums[];
    private Choice textField;
    public DesktopGUIMultiBitState(){
        setType(Values.GUIMultiBitState);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        Meta2GUIMultiBitState element = (Meta2GUIMultiBitState)   getElement();
        Meta2BitRegister register = (Meta2BitRegister)  getRegister();
        textField = new Choice();
        int dd=element.getW2();
        if (dd==0) dd=100;
        textField.setBounds(
                context.x(element.getX()+getDxOffset()+element.getDx()+5),
                context.y(element.getY()+getDyOffset()),
                context.dx(dd),
                context.dy(25));
        textField.setFont(new Font(Values.FontName, Font.PLAIN, context.y(12)));
        panel.add(textField);
        Color color=new Color(element.getColor());
        textField.setBackground(color);
        setInfoClick(textField);
        }
    public ArrayList<Meta2Bit>  createStateText(int regNum, int bitMask, long regVal){
        ArrayList<Meta2Bit> out = new ArrayList<>();
        if (regNum==0)
            return out;
        Meta2BitRegister register = (Meta2BitRegister) getRegister();
        if (register==null){
            new Message(300,300,"Группа состояний: не найден регистр "+regNum,Values.PopupMessageDelay);
            return out;
            }
        if (register.getType()!=Values.RegBitSet){
            new Message(300,300,"Группа состояний: тип регистра "+regNum,Values.PopupMessageDelay);
            return out;
            }
        for(int i=0,vv=1;i<16;i++,vv<<=1){
            if ((vv & bitMask)!=0 && (vv & regVal)!=0){
                Meta2Bit bit = register.getBits().getByCode(i);
                if (bit!=null)
                    out.add(bit);
                else
                    new Message(300,300,"Не найден "+i+"-ый разряд в "+getElement().getFullTitle(),Values.PopupMessageDelay);
                }
            }
        return out;
        }
    public String createRegInfo(int regNum, int bitMask){
        return regNum==0 ? "" : " "+regNum+String.format("[%x] ",bitMask);
        }
    public void showInfoMessage() {
        FormContext2 context= getContext();
        Meta2GUIMultiBitState element = (Meta2GUIMultiBitState)   getElement();
        Meta2DataRegister register = (Meta2DataRegister)  getRegister();
        String ss = "Группа состояний "+register.getFullTitle()+String.format("[%x] ",element.getBitMask());
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        Meta2GUIMultiBitState element = (Meta2GUIMultiBitState) getElement();
        ArrayList<Meta2Bit> out = createStateText(element.getRegNum(),element.getBitMask(),vv);
        textField.removeAll();
        for(Meta2Bit bb : out)
            textField.add(bb.getTitle());
        }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2BitRegister register = (Meta2BitRegister)  getRegister();
        if (register==null)
            return getRegisterTitle();
        if (!(register instanceof Meta2BitRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
    }

