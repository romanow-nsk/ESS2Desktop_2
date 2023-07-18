package romanow.abc.desktop.module;

import romanow.abc.core.constants.Values;

public class ModuleEventSystem extends ModuleEventAll {
    public ModuleEventSystem(){ super(false);}
    public boolean typeFilter(int type) {
        return type== Values.EventSystem || type == Values.EventFile;
        }
    private int[] eTypes = {Values.EventSystem, Values.EventFile};
    public int[] eventTypes(){ return eTypes; }
}
