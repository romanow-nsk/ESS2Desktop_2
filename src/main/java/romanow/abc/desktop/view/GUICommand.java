package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.OK;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUICommand extends GUIElement {
    private JButton textField;
    public GUICommand(){
        type = Values.GUIButton;
        }

    @Override
    public void addToPanel(JPanel panel) {
        textField = new JButton();
        textField.setBounds(
                context.x(element.getX()),
                context.y(element.getY()),
                context.x(element.getDx()),
                context.y(25));
        textField.setText(element.getTitle());
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        textField.setHorizontalAlignment(JTextField.CENTER);
        final MetaCommand cmd = ((MetaCommandRegister)register).getCommands().getByNumber(element.getBitNum());
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
                            plm.writeRegister("",0,register.getRegNum(),element.getBitNum());
                            onEvent.onEnter(GUICommand.this,0,"");
                            } catch (UniException ex) {
                                String ss = "Ошибка записи команды: "+ex.toString();
                                new Message(300,300,ss,Values.PopupMessageDelay);
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
        int cmdCode = element.getBitNum();
        MetaCommandRegister cmd = (MetaCommandRegister)register;
        String ss = cmd.getCommands().getByNumber(cmdCode).getTitle();
        new Message(200,200,"Команда "+register.getRegNum()+":="+cmdCode+"$"+ ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {}

    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent onEvent0) {
        super.setParams(context,meta,register0, element0,plm0,onEvent0);
        if (!(register instanceof MetaCommandRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
    }
}
