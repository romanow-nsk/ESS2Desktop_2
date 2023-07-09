package romanow.abc.desktop.module;

import romanow.abc.core.constants.Values;

public class ModuleEventExternal extends ModuleEventAll {
    public ModuleEventExternal(){}
    public boolean typeFilter(int type) {
        return type==Values.EventExternal || type==Values.EventEquipMes;
    }
    private int[] eTypes = {Values.EventExternal,Values.EventEquipMes};
    public int[] eventTypes(){ return eTypes; }
}
