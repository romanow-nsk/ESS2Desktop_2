package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.baseentityes.JString;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.*;
import romanow.abc.desktop.console.APICallC;
import retrofit2.Call;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUIESSWorkSettingString extends GUIElement {
    private JTextField textField;
    private String setValue="";
    public GUIESSWorkSettingString(){ type = Values.GUIESSSettingString; }
    @Override
    public void addToPanel(JPanel panel) {
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
        boolean enabled= element.getPower()==0;
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
                if (element.getPower()!=0){
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
                                    return context.getService().updateWorkSettings(context.getToken(),element.getClickFormName(),value);
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
            setValue = new APICallC<JString>(){
                @Override
                public Call<JString> apiFun() {
                    return context.getService().getWorkSettingsString(context.getToken(),element.getClickFormName());
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
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent back0) {
        super.setParams(context,meta,register0, element0,plm0,back0);
        return null;
    }
}
