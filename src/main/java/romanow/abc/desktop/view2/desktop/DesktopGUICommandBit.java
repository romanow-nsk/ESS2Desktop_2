package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Bit;
import romanow.abc.core.entity.metadata.Meta2BitRegister;
import romanow.abc.core.entity.metadata.Meta2Command;
import romanow.abc.core.entity.metadata.Meta2CommandRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIButton;
import romanow.abc.core.entity.metadata.view.Meta2GUICommandBit;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.OK;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUICommandBit extends View2BaseDesktop {
    private JButton textField;
    private Meta2Bit bit;
    public DesktopGUICommandBit(){
        setType(Values.GUICommandBit);
        }
    @Override
    public void addToPanel(JPanel panel) {
        textField = new JButton();
        FormContext2 context = getContext();
        Meta2GUICommandBit element = (Meta2GUICommandBit) getElement();
        textField.setBounds(
                context.x(element.getX()),
                context.y(element.getY()),
                context.dx(element.getDx()),
                context.dy(25));
        textField.setText(element.getTitle());
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.dy(12)));
        textField.setHorizontalAlignment(JTextField.CENTER);
        Meta2BitRegister register = (Meta2BitRegister) getRegister();
        bit = register.getBits().getByCode(element.getBitNum());
        if (bit==null){
            new Message(300,300,"Не найден бит "+element.getBitNum()+" регистра "+register.getTitle(),Values.PopupMessageDelay);
            return;
            }
        final boolean remoteDisable = !context.isSuperUser() &&  !context.isLocalUser() && !bit.isRemoteEnable();
        Color color=new Color(remoteDisable || !context.isActionEnable() ? Values.AccessDisableColor : element.getColor());
        textField.setBackground(color);
        setInfoClick(textField);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (context.isInfoMode()){
                    showInfoMessage();
                    return;
                    }
                if (remoteDisable){
                    new Message(300,300,"Запрет удаленного управления",Values.PopupMessageDelay);
                    return;
                    }
                if (!context.isActionEnable()){
                    new Message(300,300,"Недостаточен уровень доступа",Values.PopupMessageDelay);
                    return;
                    }
                new OK(200, 200, bit.getTitle(), new I_Button() {
                    @Override
                    public void onPush() {
                        try {
                            writeMainRegister(1<<element.getBitNum());
                            } catch (UniException ex) {
                                String ss = "Ошибка записи бита команды: "+ex.toString();
                                context.popup(ss);
                                System.out.println(ss);
                                }
                        }
                    });
                }
            });
        panel.add(textField);
        }
    @Override
    public void showInfoMessage(){
        Meta2BitRegister register = (Meta2BitRegister)  getRegister();
        new Message(200,200,"Разряд "+getElement().getTitle()+" "+toHex(register.getRegNum())+":="+bit.getBitNum()+"$"+ bit.getTitle(),Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {}

    @Override
    public String setParams(FormContext2 context, ESS2Architecture meta, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context,meta, element0,onEvent0);
        if (!(getRegister() instanceof Meta2BitRegister))
            return "Недопустимый "+getRegister().getTypeName()+" для "+getElement().getFullTitle();
        return null;
    }
}
