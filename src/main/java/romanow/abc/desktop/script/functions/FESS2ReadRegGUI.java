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
import romanow.abc.core.types.TypeShort;
import romanow.abc.core.types.TypeString;

public class FESS2ReadRegGUI extends ESS2LocalFunction {
    public FESS2ReadRegGUI() {
        super("readRegGUI", "ess2:чтение регистра");
    }
    @Override
    public int getResultType() {
        return ValuesBase.DTShort;
    }
    @Override
    public int[] getParamTypes() {
        return new int[]{ ValuesBase.DTString,ValuesBase.DTLong,ValuesBase.DTLong  };
    }
    @Override
    public void call(CallContext context) throws ScriptException {
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
        try {
            TypeFace par2 = stack.pop();
            TypeFace par1 = stack.pop();
            TypeFace par0 = stack.pop();
            devName = (TypeString) par0;
            unit = (TypeLong) par1;
            regNum = (TypeLong) par2;
            } catch (Exception ee){
                throw new ScriptException(ValuesBase.SEBug,"Исключение: "+ee.toString());
                }
        try {
            Pair<ESS2Device,Integer> res = findDevice(architecture,devName.formatTo(),(int)unit.toLong());
            int vv = res.o1.getDriver().readRegister(res.o1.getShortName(),res.o2,(int)regNum.toLong());
            stack.push(new TypeShort((short) vv));
            } catch (UniException ee){
                String ss = "Ошибка чтения регистра Modbus: "+regNum.toLong()+ "\n"+ee.toString();
                throw new ScriptException(ValuesBase.SEConfiguration, "Ошибки оборудования "+devName.formatTo()+ ": "+ss);
                }
            }
    }
