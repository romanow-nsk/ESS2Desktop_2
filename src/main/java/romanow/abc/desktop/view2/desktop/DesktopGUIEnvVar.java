package romanow.abc.desktop.view2.desktop;

import lombok.Getter;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIEnvVar;
import romanow.abc.core.entity.metadata.view.Meta2GUIScript;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subject2area.ESS2EnvValue;
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
import java.util.ArrayList;

public class DesktopGUIEnvVar extends View2BaseDesktop {
    private JTextField textField;
    private Meta2GUIEnvVar element;
    private ArrayList<Double> envVarValue=new ArrayList<>();
    private boolean valid=false;
    public DesktopGUIEnvVar(){
        setType(Values.GUIEnvVar);
        }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        element = (Meta2GUIEnvVar)   getElement();
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
        textField.setFont(new Font(Values.FontName, Font.PLAIN, context.y(12)));
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
        String ss = "Переменная окружения "+element.getEnvVarName();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        }
    @Override
    public void repaintBefore(){
        if (!valid)
            return;
        boolean first=true;
        String out = "";
        for(Double dd : envVarValue){
            if (first)
                first=!first;
            else
                out+=",";
            out +=dd;
            }
        textField.setText(out);
        }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2GUIEnvVar envVar = (Meta2GUIEnvVar) element0;
        String name= envVar.getEnvVarName();
        if (envVar.isWithUnit()) {
            FormContext2 context = getContext();
            int idx = context.getIndex(context.getForm().getFormLevel());
            name += ""+idx;
            }
        ESS2EnvValue value = meta0.getEnvValues().getByName(name);
        if (value==null)
            return "Не найдена переменная окружения "+name;
        if (!value.isDone())
            return "Отсутствует значение переменной окружения "+name;
        envVarValue = value.getEnvValues();
        valid = true;
        return null;
        }
    }
