package romanow.abc.desktop.view2.desktop;

import lombok.Getter;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
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

public class DesktopGUIScript extends View2BaseDesktop {
    private JTextField textField;
    private Meta2GUIScript element;
    @Getter private ESS2ScriptFile scriptFile=null;
    public DesktopGUIScript(){
        setType(Values.GUIScript);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        element = (Meta2GUIScript)  getElement();
        textField = new JTextField();
        int dd=element.getW2();
        if (dd==0) dd=100;
        int hh = element.getH();
        if (hh==0) hh=25;
        textField.setBounds(
                context.x(element.getX()+getDxOffset()+element.getDx()+5),
                context.y(element.getY()+getDyOffset()),
                context.dx(dd),
                context.dy(hh));
        setTextFieldParams(textField);
        panel.add(textField);
        setInfoClick(textField);
        }
    public void showInfoMessage() {
        String ss = "Данные скрипта "+element.getScripName();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        }
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
                String res;
                if (element.getAfterPoint()>0)
                    res=String.format("%6."+element.getAfterPoint()+"f",result.getRealValue()).trim();
                else
                if (element.getAfterPoint()==0)
                    res=""+result.valueToInt();
                else
                    res=""+result.valueToString();
                textField.setText(res);
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
            return "Cкрипт "+script.getScripName()+" - недопустимый тип скрипта "+Values.constMap().getGroupMapByValue("ScriptType").get(scriptFile.getScriptType()).title();
        if (!scriptFile.isPreCompiled())
            return "Cкрипт "+script.getScripName()+" не компилируется предварительно";
        return null;
        }
    }
