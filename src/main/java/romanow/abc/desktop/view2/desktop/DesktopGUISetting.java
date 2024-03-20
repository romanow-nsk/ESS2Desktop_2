package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIRegW2;
import romanow.abc.core.entity.metadata.view.Meta2GUISetting;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.*;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUISetting extends View2BaseDesktop {
    private JTextField textField;
    private String lastValue="";
    private Meta2SettingRegister register;
    public DesktopGUISetting(){
        setType(Values.GUISetting);
        }
    public Component getComponent(){ return textField; }
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        FormContext2 context= getContext();
        Meta2GUISetting element = (Meta2GUISetting) getElement();
        register = (Meta2SettingRegister) getRegister();
        int w2=element.getW2();
        if (w2==0) w2=100;
        textField = new JTextField();
        int hh = element.getH();
        if (hh==0) hh=25;
        textField.setBounds(
                context.x(element.getX()+element.getDx()+5),
                context.y(element.getY()),
                context.dx(w2),
                context.dy(hh));
        textField.setEditable(false);
        setTextFieldParams(textField);
        //textField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textField);
        //textField.setFont(new Font(Values.FontName, Font.PLAIN, context.y(12)));
        setInfoClick(textField);
        final boolean remoteDisable = !context.isSuperUser() &&  !context.isLocalUser() && !(register).isRemoteEnable();
        if (!element.isNoEditThere() && (remoteDisable || !context.isActionEnable()))
            textField.setBackground(new Color(Values.AccessDisableColor));
        //Color textColor = new Color(context.getView().getTextColor());
        //textField.setBorder(javax.swing.BorderFactory.createLineBorder(textColor,1));
        //textField.setForeground(textColor);
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton()!=MouseEvent.BUTTON1)
                    return;
                if (context.isInfoMode()){
                    showInfoMessage();
                    return;
                    }
                if (isNoEditThereMes())
                    return;
                if (remoteDisable){
                    new Message(300,300,"Запрет удаленного управления",Values.PopupMessageDelay);
                    return;
                    }
                if (!context.isActionEnable()){
                    new Message(300,300,"Недостаточен уровень доступа",Values.PopupMessageDelay);
                    return;
                    }
                ESS2SettingsCalculator calculator = new ESS2SettingsCalculator();
                //try {
                    //calculator.calculate(getArchitecture(), register, DesktopGUISetting.this.getDevice().getDriver());
                    //new DigitPanel(calculator.getResult(), new I_RealValue() {
                    //----------------- Без подсчета по формулам, напрямую 86.1
                    new DigitPanel(calcResult, new I_RealValue() {
                        @Override
                        public void onEvent(double value) {
                            if (register.getMinValue()!=0 && value < register.getMinValue()){
                                context.popup("Калькулятор: значение - "+value+" меньше разрешенного "+register.getMinValue());
                                return;
                                }
                            if (register.getMaxValue()!=0 && value > register.getMaxValue()){
                                context.popup("Калькулятор: значение - "+value+" больше разрешенного "+register.getMaxValue());
                                return;
                                }
                            Meta2SettingRegister register = (Meta2SettingRegister) getRegister();
                            try {
                                if (register.getFormat()==Values.FloatValue){
                                    if (element.isTwoUnits())
                                        writeMainRegisterTwo(Float.floatToIntBits((float) value));
                                    else
                                        writeMainRegister(Float.floatToIntBits((float) value));
                                    }
                                else{
                                    if (element.isTwoUnits())
                                        writeMainRegisterTwo(register.doubleToRegValueSet(getUnitIdx(),value));
                                    else
                                        writeMainRegister(register.doubleToRegValueSet(getUnitIdx(),value));
                                    }
                                context.getBack().forceRepaint();
                                //context.repaintValues();
                                } catch (UniException ex) {
                                    String ss = "Ошибка записи уставки: "+ex.toString();
                                    context.popup(ss);
                                    System.out.println(ss);
                                    }

                            //onEvent.onEnter(GUISetting.this,0,"");
                            }
                        });
                //    } catch (UniException ex) {
                //        System.out.println("Калькулятор уставок: "+ex.toString());
                //        }
                    }
                });
            }
    @Override
    public void showInfoMessage() {
        Meta2SettingRegister set = (Meta2SettingRegister)getRegister();
        String ss = "Уставка "+toHex(set.getRegNum())+" "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+="Удаленное управление - "+(set.isRemoteEnable() ? "да":"нет")+",";
        ss+=" Ед.изм. "+ set.getUnit()+"$";
        ss+="Формулы: "+set.getDefValueFormula()+" / "+set.getMinValueFormula()+" / "+set.getMaxValueFormula();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(long vv) throws UniException {
        Meta2Register register = getRegister();
        Meta2GUIRegW2 metaGUI = (Meta2GUIRegW2)getElement();
        lastValue = register.regValueToString(getUnitIdx(),(int)vv,metaGUI);
        textField.setText(lastValue);
        }
    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register = getRegister();
        if (register==null)
            return getRegisterTitle();
        if (!(register instanceof Meta2SettingRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
    //------------------------------------------------------------------------------------------------------------------
    private I_Calculator calcResult = new I_Calculator() {
        @Override
        public String getTitle() {
            return "Уставка: "+getRegisterTitle();
            }
        @Override
        public boolean isMinFormulaValid() {
            return register.getMinValue()!=0;
            };
        @Override
        public String getMinValue() {
            return ""+register.getMinValue();
            }
        @Override
        public boolean isMaxFormulaValid() {
            return register.getMaxValue()!=0;
            }
        @Override
        public String getMaxValue() {
            return ""+register.getMaxValue();
            }
        @Override
        public String getStartValue() {
            return lastValue;
            }
        };
}
