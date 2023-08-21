package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Command;
import romanow.abc.core.entity.metadata.Meta2CommandRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIButton;
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

public class DesktopGUIButton extends View2BaseDesktop {
    private JButton textField;
    public DesktopGUIButton(){
        setType(Values.GUIButton);
        }

    @Override
    public void addToPanel(JPanel panel) {
        textField = new JButton();
        FormContext2 context = getContext();
        Meta2GUIButton element = (Meta2GUIButton) getElement();
        textField.setBounds(
                context.x(element.getX()),
                context.y(element.getY()),
                context.dx(element.getDx()),
                context.dy(element.getH()==0 ? 25 : element.getH()));
        setButtonParams(textField);
        textField.setText(element.getTitle());
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        textField.setHorizontalAlignment(JTextField.CENTER);
        Meta2CommandRegister register = (Meta2CommandRegister)getRegister();
        final Meta2Command cmd = register.getCommands().getByCode(element.getCmdCode());
        final boolean remoteDisable = !context.isSuperUser() &&  !context.isLocalUser() && !cmd.isRemoteEnable();
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
                new OK(200, 200, cmd.getTitle(), new I_Button() {
                    @Override
                    public void onPush() {
                        try {
                            writeMainRegister(cmd.getCode());
                            context.getBack().forceRepaint();
                        } catch (UniException ex) {
                                String ss = "Ошибка записи команды: "+ex.toString();
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
        int cmdCode = ((Meta2GUIButton)getElement()).getCode();
        Meta2CommandRegister register = (Meta2CommandRegister)  getRegister();
        String ss = register.getCommands().getByCode(cmdCode).getTitle();
        new Message(200,200,"Команда "+toHex(register.getRegNum())+":="+cmdCode+"$"+ ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {}

    @Override
    public String setParams(FormContext2 context, ESS2Architecture meta, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context,meta, element0,onEvent0);
        if (!(getRegister() instanceof Meta2CommandRegister))
            return "Недопустимый "+getRegister().getTypeName()+" для "+getElement().getFullTitle();
        return null;
        }

}
