package romanow.abc.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.metadata.Meta2SettingRegister;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subject2area.ESS2Device;
import romanow.abc.dataserver.ESS2Calculator;

public class ESS2SettingsCalculator {
    private double value=0;
    private int power;
    private boolean maxFormulaValid=false;
    private boolean defFormulaValid=false;
    private boolean minFormulaValid=false;
    private double maxValue=0;
    private double defValue=0;
    private double minValue=0;
    private Meta2SettingRegister register;
    ESS2Architecture meta;
    ESS2Calculator calculator;
    public ESS2SettingsCalculator(){}
    I_Calculator result = new I_Calculator() {
        @Override
        public String getTitle() {
            return ""; }
        @Override
        public boolean isMinFormulaValid() {
            return minFormulaValid;
            }
        @Override
        public String getMinValue() {
            return ""+minValue;
            }
        @Override
        public boolean isMaxFormulaValid() {
            return maxFormulaValid;
            }
        @Override
        public String getMaxValue() {
            return ""+maxValue;
            }
        @Override
        public String getStartValue() { return ""; }
        };
    public I_Calculator getResult() {
        return result;
    }

    public String getTitle(){
        return register.getTitle();
        }
    public void calculate(ESS2Architecture  meta0, Meta2SettingRegister register0, I_ModbusGroupDriver driver) throws UniException {
        meta = meta0;
        register = register0;
        power = register.getPower();
        //---------- TODO
        ESS2Device device = meta.getDevices().get(0);
        double val = driver.readRegister(device.getShortName(),0,register.getRegNum());
        if (power==0)
            value=(int)val;
        if (power<0)
            value=val/Math.pow(10,-power);
        if (power>0)
            value = val*Math.pow(10,power);
        calculator = new ESS2Calculator(meta,false);
        String formula = register.getMaxValueFormula();
        maxFormulaValid = calculator.isFormula(formula);
        if (maxFormulaValid)
            maxValue = calculator.calc(formula,false);
        formula = register.getDefValueFormula();
        defFormulaValid  = calculator.isFormula(formula);
        if (defFormulaValid)
            defValue=calculator.calc(formula,false);
        formula = register.getMinValueFormula();
        minFormulaValid = calculator.isFormula(formula);
        if (minFormulaValid)
            minValue = calculator.calc(formula,true);
        }
    public void parseAndWrite(String svalue, boolean checkLimits) throws UniException{
        double val=0;
        try {
            val = Double.parseDouble(svalue);
            } catch (Exception ee){
                throw UniException.user("Ошибка формата уставки");
                }
        if (checkLimits && (minFormulaValid && val<minValue || maxFormulaValid && val>maxValue)){
            throw UniException.user("Превышены границы диапазона уставки");
            }
        int vv = 0;
        if (power==0)
            vv = (int)val;
        if (power<0)
            vv = (int)(val*Math.pow(10,-power));
        if (power>0)
            vv = (int)(val/Math.pow(10,power));
        //---------- TODO
        ESS2Device device = meta.getDevices().get(0);
        device.getDriver().writeRegister(device.getShortName(),0,register.getRegNum(),vv);
        }
    public double getValue() {
        return value; }
    public boolean isMaxFormulaValid() {
        return maxFormulaValid; }
    public boolean isDefFormulaValid() {
        return defFormulaValid; }
    public boolean isMinFormulaValid() {
        return minFormulaValid; }
    public double getMaxValue() {
        return maxValue; }
    public double getDefValue() {
        return defValue; }
    public double getMinValue() {
        return minValue; }
    public int getPower() {
        return power; }
}
