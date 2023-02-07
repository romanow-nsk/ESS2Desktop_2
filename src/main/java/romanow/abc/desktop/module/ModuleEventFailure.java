package romanow.abc.desktop.module;


import romanow.abc.core.constants.Values;

public class ModuleEventFailure extends ModuleEventAll {
    public ModuleEventFailure(){}
    public boolean typeFilter(int type) {
        return type== Values.EventFailure || type==Values.EventFailBitReg || type==Values.EventFailSettingReg;
        }
    private int[] eTypes = {Values.EventFailure, Values.EventFailBitReg, Values.EventFailSettingReg};
    public int[] eventTypes(){ return eTypes; }
}
