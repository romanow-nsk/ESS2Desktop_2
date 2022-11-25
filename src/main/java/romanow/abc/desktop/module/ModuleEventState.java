package romanow.abc.desktop.module;

import romanow.abc.core.constants.Values;

public class ModuleEventState extends ModuleEventAll {
    public ModuleEventState(){}
    public boolean typeFilter(int type) {
        return type== Values.EventState || type==Values.EventDEStateReg || type==Values.EventDEBitReg;
        }
}
