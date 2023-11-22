package romanow.abc.desktop.module;

import romanow.abc.core.constants.Values;

public class ModuleEventState extends ModuleEventAll {
    public ModuleEventState(){ super(false);}
    public boolean typeFilter(int type) {
        return type== Values.EventState || type==Values.EventDEStateReg || type==Values.EventDEBitReg || type==Values.EventPLC;
        }
    private int[] eTypes = {Values.EventState, Values.EventDEStateReg, Values.EventDEBitReg,Values.EventPLC};
    public int[] eventTypes(){ return eTypes; }
}
