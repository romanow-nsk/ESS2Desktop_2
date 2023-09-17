package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Bit;
import romanow.abc.core.entity.metadata.Meta2BitRegister;
import romanow.abc.core.entity.metadata.Meta2DataRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUI2StateBox;
import romanow.abc.core.entity.metadata.view.Meta2GUIBitCheckBox;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.*;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUIBitCheckBox extends View2BaseDesktop {
    private boolean busy=false;
    protected JComponent textField;
    private int bitNum=0;
    private JCheckBox cmdButton=null;    // Кнопка
    private long lastBitValue=-1;        // Последнее значение разряда
    private long lastValue=0;            //
    public DesktopGUIBitCheckBox(){
        setType(Values.GUI2StateBox);
        }
    //----------------------------------------------------------------------
    protected int getSize(){
        return 24;
        }
    protected int getOffset(){
        return 0;
    }
    //-----------------------------------------------------------------------
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        Meta2GUIBitCheckBox element = (Meta2GUIBitCheckBox) getElement();
        if (element.getDx()!=0)
            setLabel(panel);
        FormContext2 context = getContext();
        int hh = element.getH();
        if (hh==0) hh=25;
        final boolean remoteDisable = !context.isSuperUser() &&  !context.isLocalUser() && !element.isRemoteEnable();
        cmdButton = new JCheckBox();
        cmdButton.setBounds(
                context.x(element.getX()+getDxOffset()+element.getDx()+5),
                context.y(element.getY()+getDyOffset()),
                context.dx(hh),
                context.dy(hh));
        if (remoteDisable)
            cmdButton.setEnabled(false);
        setInfoClick(cmdButton);
        cmdButton.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (busy)
                    return;
                if (context.isInfoMode()){
                    showInfoMessage();
                    return;
                    }
                if (remoteDisable){
                    new Message(300,300,"Запрет удаленного управления", Values.PopupMessageDelay);
                    return;
                    }
                if (!context.isActionEnable()){
                    new Message(300,300,"Недостаточен уровень доступа",Values.PopupMessageDelay);
                    return;
                    }
                if (lastBitValue==-1){
                    new Message(300,300,"Разряд еще не прочитан",Values.PopupMessageDelay);
                    return;
                    }
                new OKFull(200, 200, element.getTitle()+" "+(lastBitValue!=0 ? "ОТКЛ" : "ВКЛ"), new I_ButtonFull() {
                    @Override
                    public void onPush(boolean yes) {
                        if (!yes){
                            busy=true;
                            cmdButton.setSelected(!cmdButton.isSelected());
                            busy=false;
                            return;
                            }
                        try {
                            int mask = 1<<bitNum;
                            long vv = lastValue & ~mask;       // Инвертировать разряд
                            if (cmdButton.isSelected())
                                vv |= mask;
                            writeMainRegister((int)vv);
                            context.getBack().forceRepaint();
                            } catch (UniException ex) {
                                String ss = "Ошибка изменения разряда: "+ex.toString();
                                context.popup(ss);
                                System.out.println(ss);
                                }
                            }
                        });
                    }
                });
        panel.add(cmdButton);
        }
    public void showInfoMessage() {
        Meta2GUIBitCheckBox element = (Meta2GUIBitCheckBox) getElement();
        int bitNumElem = element.getBitNum();
        Meta2BitRegister set = (Meta2BitRegister) getRegister();
        Meta2Bit bit = set.getBits().getByCode(bitNumElem);
        String ss = "Разряд регистра "+toHex(set.getRegNum()+getRegOffset())+" ["+toHex(set.getRegNum())+"]("+bitNum+") "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+=bit==null ? " не найден " : bit.getTitle();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        busy = true;
        Meta2GUIBitCheckBox element = (Meta2GUIBitCheckBox) getElement();
        lastValue = vv;
        lastBitValue = (vv>>bitNum) & 01;
        cmdButton.setSelected(lastBitValue!=0);
        busy = false;
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register = (Meta2Register) getRegister();
        if (!(register instanceof Meta2BitRegister || ((Meta2GUIBitCheckBox) getElement()).isMixedRegister() && register instanceof Meta2DataRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
}
