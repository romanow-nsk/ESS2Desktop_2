package romanow.abc.desktop.view2.desktop;

import lombok.Getter;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIScript;
import romanow.abc.core.entity.metadata.view.Meta2GUIScriptLabel;
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

public class DesktopGUIScriptLabel extends View2BaseDesktop {
    private JLabel label;
    private Meta2GUIScriptLabel element;
    @Getter private ESS2ScriptFile scriptFile=null;
    public DesktopGUIScriptLabel(){
        setType(Values.GUIScript);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        element = (Meta2GUIScriptLabel)getElement();
        label = setLabel(panel);
        setInfoClick(label);
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
            label.setBackground(new Color(0xFFF0F000));
            label.setText("...");
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
                String ss = element.getTitle();
                String res;
                if (element.getAfterPoint()!=0)
                    res=String.format("%6."+element.getAfterPoint()+"f",result.getRealValue()).trim();
                else
                    res=result.valueToString();
                int idx=ss.indexOf("$");
                if(idx==-1)
                    label.setText(" "+ss+" = "+res);
                else
                    label.setText(" "+ss.substring(0,idx)+" = "+res+" "+ss.substring(idx+1));
                }
            } catch (ScriptException e) {
                getContext().popup("Ошибка исполнения скрипта\n"+e.toString());
                }
    }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2GUIScriptLabel script = (Meta2GUIScriptLabel) element0;
        scriptFile = meta0.getScripts().getByName(script.getScripName());
        if (scriptFile==null)
            return "Не найден скрипт "+script.getScripName();
        if (scriptFile.isServerScript())
            return "Cкрипт "+script.getScripName()+" серверный";
        if (scriptFile.getScriptType()!=Values.STCalcClient)
            return "Cкрипт "+script.getScripName()+" - недопустимый тип "+scriptFile.getScriptType();
        if (!scriptFile.isPreCompiled())
            return "Cкрипт "+script.getScripName()+" не компилируется предварительно";
        if (!scriptFile.isValid())
            return "Cкрипт "+script.getScripName()+" нет кода";
        return null;
        }
    }
