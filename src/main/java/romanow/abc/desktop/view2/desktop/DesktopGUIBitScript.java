package romanow.abc.desktop.view2.desktop;

import lombok.Getter;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIBitScript;
import romanow.abc.core.entity.metadata.view.Meta2GUIScript;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subject2area.ESS2ScriptFile;
import romanow.abc.core.script.CallContext;
import romanow.abc.core.script.ScriptException;
import romanow.abc.core.types.TypeFace;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class DesktopGUIBitScript extends View2BaseDesktop {
    private JButton textField;
    private Meta2GUIBitScript element;
    @Getter
    private ESS2ScriptFile scriptFile=null;
    public DesktopGUIBitScript(){
        setType(Values.GUIBitScript);
    }
    protected JComponent createComponent(){
        return new JButton();
    }
    protected void viewComponent(){
        JButton bb = (JButton)textField;
        bb.setIcon(new ImageIcon(getClass().getResource("/ballwhite.png"))); // NOI18N
        bb.setBorderPainted(false);
        bb.setContentAreaFilled(false);
        }
    protected void putValueOwn(int cc){
        JButton bb = (JButton)textField;
        URL url = getClass().getResource(getColorIconName(cc));
        bb.setIcon(new ImageIcon(url));
        }
    protected int getSize(){
        return 24;
    }
    protected int getOffset(){
        return 0;
    }
    //-----------------------------------------------------------------------
    private String getColorIconName(int color){
        switch (color){
            case 0x00C0C0C0: return "/balllightgray.png";
            case 0x00FF0000: return "/ballred.png";
            case 0x0000FF00: return "/ballgreen.png";
            case 0x00F0A000: return "/balldarkyellow.png";
            case 0x0000A000: return "/balldarkgreen.png";
            case 0x00FFFF00: return "/ballyellow.png";
            case 0x000000FF: return "/ballblue.png";
            }
        return "/ballwhite.png";
        }
    @Override
    public void addToPanel(JPanel panel) {
        element = (Meta2GUIBitScript) getElement();
        int hh = element.getH();
        if (hh==0) hh=25;
        if (element.getDx()!=0){
            setLabel(panel);
            }
        textField = new JButton();
        int dd = element.getW2();
        if (dd==0) dd=10;
        int sz = getSize();
        int offset = (25-sz)/2;
        FormContext2 context = getContext();
        int xx = element.getX()+element.getDx()+getDxOffset()+dd-5+offset;
        int yy = element.getY()+getDyOffset()+(hh-15)/2-5+offset;
        textField.setBounds(
                context.x(xx),
                context.y(yy),
                context.dx(sz),
                context.dy(sz));
        viewComponent();
        setInfoClick(textField);
        panel.add(textField);
        }
    public void showInfoMessage() {
        String ss = "Данные скрипта "+element.getScripName();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {}
    @Override
    public void repaintBefore(){
        if (scriptFile==null || !scriptFile.isValid() || element.isNoCalc()){
            textField.setBackground(new Color(0xFFF0F000));
            textField.setText("...");
            return;
            }
        try {
            CallContext context = scriptFile.getScriptCode();
            context.reset();
            context.call(false);
            TypeFace result = scriptFile.getScriptCode().getVariables().get(Values.ScriptResultVariable);
            if (result==null)
                new Message(300,300,"Ошибка исполнения скрипта\nОтстутствует результат",Values.PopupMessageDelay);
            else{
                putValueOwn(result.isBoolValue() ? element.getColorYes() : element.getColorNo());
                }
            } catch (ScriptException e) {
                getContext().popup("Ошибка исполнения скрипта\n"+e.toString());
                }
        }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2GUIScript script = (Meta2GUIScript)element0;
        scriptFile = meta0.getScripts().getByName(script.getScripName());
        if (scriptFile==null)
            return "Не найден скрипт "+script.getScripName();
        if (scriptFile.isServerScript())
            return "Cкрипт "+script.getScripName()+" серверный";
        if (scriptFile.getScriptType()!=Values.STCalcClient)
            return "Cкрипт "+script.getScripName()+" - недопустимый тип "+scriptFile.getScriptType();
        if (!scriptFile.isPreCompiled())
            return "Cкрипт "+script.getScripName()+" не компилируется предварительно";
        return null;
    }

}
