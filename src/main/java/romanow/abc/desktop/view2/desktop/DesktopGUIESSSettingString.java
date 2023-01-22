package romanow.abc.desktop.view2.desktop;

import retrofit2.Call;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.baseentityes.JString;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIESSSettingString;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.I_Value;
import romanow.abc.desktop.KeyBoardPanel;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.console.APICallC;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DesktopGUIESSSettingString extends View2BaseDesktop {
    private JTextField textField;
    private String setValue="";
    public DesktopGUIESSSettingString(){ setType(Values.GUIESSSettingString); }
    @Override
    public void addToPanel(JPanel panel) {
        FormContext2 context= getContext();
        Meta2GUIESSSettingString element = (Meta2GUIESSSettingString) getElement();
        setLabel(panel);
        int w2=element.getW2();
        if (w2==0) w2=100;
        textField = new JTextField();
        textField.setBounds(
                context.x(element.getX()+element.getDx()+5),
                context.y(element.getY()),
                context.x(w2),
                context.y(25));
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.LEFT);
        panel.add(textField);
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        setInfoClick(textField);
        Color color=new Color(!context.isActionEnable() ? Values.AccessDisableColor : element.getColor());
        textField.setBackground(color);
        getSettings();
        textField.setText(setValue);
        textField.setBackground(color);
        boolean enabled= element.isEditDisable();
        textField.setEnabled(enabled);
            textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton()!=MouseEvent.BUTTON1)
                    return;
                if (context.isInfoMode()){
                    showInfoMessage();
                    return;
                    }
                if (element.isEditDisable()){
                    new Message(300,300,"Настройка не редактируется",Values.PopupMessageDelay);
                    return;
                    }
                if (!context.isActionEnable()){
                    new Message(300,300,"Недостаточен уровень доступа",Values.PopupMessageDelay);
                    return;
                    }
                getSettings();
                KeyBoardPanel digitPanel = new KeyBoardPanel(false, setValue, new I_Value<String>() {
                    @Override
                    public void onEnter(String value) {
                        try {
                            new APICallC<JEmpty>(){
                                @Override
                                public Call<JEmpty> apiFun() {
                                    return context.getService().updateWorkSettings(context.getToken(),element.getFieldName(),value);
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
        Meta2GUIESSSettingString element = (Meta2GUIESSSettingString) getElement();
        String ss = "Параметр сервера (int) "+element.getFieldName();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    private void getSettings(){
        final FormContext2 context= getContext();
        final Meta2GUIESSSettingString element = (Meta2GUIESSSettingString) getElement();
        try {
            setValue = new APICallC<JString>(){
                @Override
                public Call<JString> apiFun() {
                    return context.getService().getWorkSettingsString(context.getToken(),element.getFieldName());
                    }
                }.call().getValue();
            } catch (UniException ex) {
                new Message(300,300,"Ошибка чтения настроек",Values.PopupMessageDelay);
                return;
                }
            textField.setText(""+setValue);
        }
    @Override
    public void putValue(long vv) throws UniException {
        }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        return null;
        }
}
