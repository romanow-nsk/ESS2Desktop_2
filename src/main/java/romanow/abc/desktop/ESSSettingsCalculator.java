package romanow.abc.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.MetaExternalSystem;
import romanow.abc.core.entity.subjectarea.MetaSettingRegister;
import romanow.abc.dataserver.Calculator;

public class ESSSettingsCalculator {
    private double value=0;
    private int power;
    private boolean maxFormulaValid=false;
    private boolean defFormulaValid=false;
    private boolean minFormulaValid=false;
    private double maxValue=0;
    private double defValue=0;
    private double minValue=0;
    private I_ModbusGroupDriver plm;
    private MetaSettingRegister register;
    MetaExternalSystem meta;
    Calculator calculator;
    public ESSSettingsCalculator(){}
    I_Calculator result = new I_Calculator() {
        @Override
        public void parseAndWrite(String ss, boolean mode) throws UniException { }
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
        };
    public I_Calculator getResult() {
        return result;
    }

    public String getTitle(){
        return register.getTitle();
        }
    public void calculate(MetaExternalSystem meta0, MetaSettingRegister register0, I_ModbusGroupDriver pl0) throws UniException {
        meta = meta0;
        register = register0;
        plm = pl0;
        power = register.getPower();
        double val = plm.readRegister("",0,register.getRegNum());
        if (power==0)
            value=(int)val;
        if (power<0)
            value=val/Math.pow(10,-power);
        if (power>0)
            value = val*Math.pow(10,power);
        calculator = new Calculator(meta,false);
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
        plm.writeRegister("",0,register.getRegNum(),vv);
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
