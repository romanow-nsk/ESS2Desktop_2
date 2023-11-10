package romanow.abc.desktop.script.functions;

import romanow.abc.core.Pair;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subject2area.ESS2Device;
import romanow.abc.core.script.CallContext;
import romanow.abc.core.script.OperationStack;
import romanow.abc.core.script.ScriptException;
import romanow.abc.core.types.TypeFace;
import romanow.abc.core.types.TypeLong;
import romanow.abc.core.types.TypeString;

public class FESS2WriteRegGUI extends ESS2LocalFunction {
    public FESS2WriteRegGUI() {
        super("writeRegGUI", "ess2:запись регистра");
    }
    @Override
    public int getResultType() {
        return ValuesBase.DTVoid;
    }
    @Override
    public int[] getParamTypes() {
        return new int[]{ ValuesBase.DTString,ValuesBase.DTLong,ValuesBase.DTLong,ValuesBase.DTLong  };
    }
    @Override
    public void call(CallContext context, boolean trace) throws ScriptException {
        Object env = context.getCallEnvironment();
        if (env==null)
            throw new ScriptException(ValuesBase.SEIllegalFunEnv,"Объект окружения = null");
        if (!(env instanceof ESS2Architecture))
            throw new ScriptException(ValuesBase.SEIllegalFunEnv,"Объект окружения не ESS2Architecture");
        ESS2Architecture architecture = (ESS2Architecture) env;
        OperationStack stack = context.getStack();
        TypeString devName;
        TypeLong unit;
        TypeLong regNum;
        TypeLong regValue;
        try {
            TypeFace par3 = stack.pop();
            TypeFace par2 = stack.pop();
            TypeFace par1 = stack.pop();
            TypeFace par0 = stack.pop();
            devName = (TypeString) par0;
            unit = (TypeLong) par1;
            regNum = (TypeLong) par2;
            regValue = (TypeLong) par3;
            } catch (Exception ee){
                throw new ScriptException(ValuesBase.SEBug,"Исключение: "+ee.toString());
                }
        try {
            Pair<ESS2Device,Integer> res = findDevice(architecture,devName.formatTo(),(int)unit.toLong());
            res.o1.getDriver().writeRegister(res.o1.getShortName(),res.o2,(int)regNum.toLong(),(int)regValue.toLong());
            } catch (UniException ee){
                String ss = "Ошибка записи регистра Modbus: "+regNum.toLong()+ "\n"+ee.toString();
                throw new ScriptException(ValuesBase.SEConfiguration, "Ошибки оборудования "+devName.formatTo()+ ": "+ss);
                }
            }
    }
