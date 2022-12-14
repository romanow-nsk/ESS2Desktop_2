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
                context.x(dd),
                context.y(hh));
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textField);
        Color color=new Color(element.getColor());
        textField.setBackground(color);
        Color textColor = new Color(context.getView().getTextColor());
        textField.setBorder(BorderFactory.createLineBorder(textColor,1));
        textField.setForeground(textColor);
        setInfoClick(textField);
        }
    public void showInfoMessage() {
        String ss = "???????????? ?????????????? "+element.getScripName();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(int vv) throws UniException {
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
                new Message(300,300,"???????????? ???????????????????? ??????????????\n???????????????????????? ??????????????????",Values.PopupMessageDelay);
            else
                textField.setText(result.valueToString());
            } catch (ScriptException e) {
                getContext().popup("???????????? ???????????????????? ??????????????\n"+e.toString());
                }
    }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2GUIScript script = (Meta2GUIScript)element0;
        scriptFile = meta0.getScripts().getByName(script.getScripName());
        if (scriptFile==null)
            return "???? ???????????? ???????????? "+script.getScripName();
        if (scriptFile.isServerScript())
            return "C?????????? "+script.getScripName()+" ??????????????????";
        if (scriptFile.getScriptType()!=Values.STCalcClient)
            return "C?????????? "+script.getScripName()+" - ???????????????????????? ?????? "+scriptFile.getScriptType();
        if (!scriptFile.isPreCompiled())
            return "C?????????? "+script.getScripName()+" ???? ?????????????????????????? ????????????????????????????";
        if (!scriptFile.isValid())
            return "C?????????? "+script.getScripName()+" ?????? ????????";
        return null;
        }
    }
