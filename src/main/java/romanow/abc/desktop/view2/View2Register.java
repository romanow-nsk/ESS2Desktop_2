package romanow.abc.desktop.view2;

import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUIReg;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.view.I_GUIEvent;

public abstract class View2Register extends View2Base{
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        return super.setParams(context0,meta0,element0,onEvent0);
        }
    public void showInfoMessage(){
        Meta2GUIReg register = (Meta2GUIReg) element;
        String ss = "Регистр "+(register.getRegNum()+regOffset)+"->"+register.toString();
        new Message(200,200,ss);
        }
    public String valueWithPower(int vv){
        Meta2GUIReg register = (Meta2GUIReg) element;
        int power = register.getRegLink().getRegister().getPower();
        if (power<0)
            return ""+vv/Math.pow(10,-power);
        if (power>0)
            return ""+vv*Math.pow(10,power);
        return ""+vv;
        }
    public double doubleWithPower(int vv){
        Meta2GUIReg register = (Meta2GUIReg) element;
        int power = register.getRegLink().getRegister().getPower();
        if (power<0)
            return vv/Math.pow(10,-power);
        if (power>0)
            return vv*Math.pow(10,power);
        return vv;
        }
    }
