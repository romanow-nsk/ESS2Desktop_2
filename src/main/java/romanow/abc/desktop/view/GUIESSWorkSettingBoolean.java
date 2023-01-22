package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.baseentityes.JBoolean;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.*;
import romanow.abc.desktop.console.APICallC;
import retrofit2.Call;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUIESSWorkSettingBoolean extends GUIElement {
    private JButton textField;
    private boolean setValue=false;
    public GUIESSWorkSettingBoolean(){ type = Values.GUIESSSettingBoolean; }
    @Override
    public void addToPanel(JPanel panel) {
        int hh = element.getH();
        if (hh==0) hh=25;
        setLabel(panel);
        int sz = 24;
        int offset = (25-sz)/2;
        textField = new JButton();
        textField.setBounds(
                context.x(element.getX()+element.getDx()+dxOffset+5+offset),
                context.y(element.getY()+dyOffset+(hh-15)/2-5+offset),
                context.x(sz),
                context.y(sz));
        panel.add(textField);
        setInfoClick(textField);
        final boolean remoteDisable = !context.isSuperUser() &&  !context.isLocalUser() && !((MetaSettingRegister)register).isRemoteEnable();
        Color color=new Color(remoteDisable || !context.isActionEnable() ? Values.AccessDisableColor : element.getColor());
        textField.setBackground(color);
        getSettings();
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (element.getPower()!=0){
                    new Message(300,300,"Настройка не редактируется",Values.PopupMessageDelay);
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
                new OK(200, 200, element.getTitle() + (setValue ? ": выключить" : ": включить"), new I_Button() {
                    @Override
                    public void onPush() {
                        try {
                            setValue = !setValue;
                            new APICallC<JEmpty>(){
                                @Override
                                public Call<JEmpty> apiFun() {
                                    return context.getService().updateWorkSettings(context.getToken(),element.getClickFormName(),setValue);
                                    }
                                }.call();
                            getSettings();
                            } catch (UniException ex) {
                                new Message(300,300,"Ошибка обновления настроек",Values.PopupMessageDelay);
                                }
                            }
                        });
                }
            });
        }
    @Override
    public void showInfoMessage() {
        String ss = "Параметр сервера (int) "+element.getClickFormName();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    private void getSettings(){
        try {
            setValue = new APICallC<JBoolean>(){
                @Override
                public Call<JBoolean> apiFun() {
                    return context.getService().getWorkSettingsBoolean(context.getToken(),element.getClickFormName());
                    }
                }.call().value();
            } catch (UniException ex) {
                new Message(300,300,"Ошибка чтения настроек",Values.PopupMessageDelay);
                return;
                }
        textField.setIcon(new javax.swing.ImageIcon(getClass().getResource(
                setValue ? "/ballgreen.png" : "/balllightgray.png")));
        }

    @Override
    public void putValue(long vv) throws UniException {
        }
    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent back0) {
        super.setParams(context,meta,register0, element0,plm0,back0);
        return null;
    }
}
