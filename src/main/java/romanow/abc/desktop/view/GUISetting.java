package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.desktop.ESSSettingsCalculator;
import romanow.abc.core.entity.subjectarea.*;
import romanow.abc.desktop.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUISetting extends GUIElement {
    private JTextField textField;
    public GUISetting(){
        type = Values.GUISetting;
        }

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
        textField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textField);
        textField.setFont(new Font(Values.FontName, Font.PLAIN, context.y(12)));
        setInfoClick(textField);
        final boolean remoteDisable = !context.isSuperUser() &&  !context.isLocalUser() && !((MetaSettingRegister)register).isRemoteEnable();
        Color color=new Color(remoteDisable || !context.isActionEnable() ? Values.AccessDisableColor : element.getColor());
        textField.setBackground(color);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton()!=MouseEvent.BUTTON1)
                    return;
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
                ESSSettingsCalculator calculator = new ESSSettingsCalculator();
                try {
                    calculator.calculate(meta, (MetaSettingRegister) register, plm);
                    new DigitPanel(calculator.getResult(), new I_RealValue() {
                        @Override
                        public void onEvent(double value) {
                            onEvent.onEnter(GUISetting.this,0,"");
                            }
                        });
                    } catch (UniException ex) {
                        System.out.println("Калькулятор уставок: "+ex.toString());
                        }
                    }
                });
            }
    @Override
    public void showInfoMessage() {
        MetaSettingRegister set = (MetaSettingRegister)register;
        String ss = "Уставка "+set.getRegNum()+" "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+="Удаленное управление - "+(set.isRemoteEnable() ? "да":"нет")+",";
        ss+=" Ед.изм. "+ set.getUnit()+"$";
        ss+="Формулы: "+set.getDefValueFormula()+" / "+set.getMinValueFormula()+" / "+set.getMaxValueFormula();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        textField.setText(valueWithPower(vv));
        }
    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent back0) {
        super.setParams(context,meta,register0, element0,plm0,back0);
        if (!(register instanceof MetaSettingRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
    }
}
