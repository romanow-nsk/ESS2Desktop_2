package romanow.abc.desktop.view2.desktop;

import retrofit2.Call;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.baseentityes.JBoolean;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.metadata.view.*;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.OK;
import romanow.abc.desktop.console.APICallC;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesktopGUIESSSettingBoolean extends View2BaseDesktop {
    private JButton textField;
    private boolean setValue=false;
    public DesktopGUIESSSettingBoolean(){ setType(Values.GUIESSSettingBoolean); }
    @Override
    public void addToPanel(JPanel panel) {
        FormContext2 context= getContext();
        Meta2GUIESSSettingBoolean element = (Meta2GUIESSSettingBoolean) getElement();
        int hh = element.getH();
        if (hh==0) hh=25;
        setLabel(panel);
        int sz = 24;
        int offset = (25-sz)/2;
        textField = new JButton();
        textField.setBounds(
                context.x(element.getX()+element.getDx()+getDxOffset()+5+offset),
                context.y(element.getY()+getDyOffset()+(hh-15)/2-5+offset),
                context.x(sz),
                context.y(sz));
        panel.add(textField);
        setInfoClick(textField);
        final boolean remoteDisable = !context.isSuperUser() &&  !context.isLocalUser();
        Color color=new Color(remoteDisable || !context.isActionEnable() ? Values.AccessDisableColor : element.getColor());
        textField.setBackground(color);
        getSettings();
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((Meta2GUIESSSettingBoolean)element).isEditDisable()){
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
                                    return context.getService().updateWorkSettings(context.getToken(),
                                            element.getFieldName(),setValue);
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
        Meta2GUIESSSettingBoolean element = (Meta2GUIESSSettingBoolean) getElement();
        String ss = "Параметр сервера (boolean) "+element.getFieldName();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    private void getSettings(){
        try {
            final FormContext2 context= getContext();
            final Meta2GUI element = getElement();
            setValue = new APICallC<JBoolean>(){
                @Override
                public Call<JBoolean> apiFun() {
                    return context.getService().getWorkSettingsBoolean(context.getToken(),
                            ((Meta2GUIESSSettingBoolean)getElement()).getFieldName());
                    }
                }.call().value();
            } catch (UniException ex) {
                new Message(300,300,"Ошибка чтения настроек",Values.PopupMessageDelay);
                return;
                }
        textField.setIcon(new ImageIcon(getClass().getResource(
                setValue ? "/ballgreen.png" : "/balllightgray.png")));
        }

    @Override
    public void putValue(int vv) throws UniException {}

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        return null;
    }
}
