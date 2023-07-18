package romanow.abc.desktop.module;

import romanow.abc.core.constants.Values;

public class ModuleEventSetting extends ModuleEventAll {
    public ModuleEventSetting(){ super(false);}
    public boolean typeFilter(int type) {
        return type== Values.EventSetting || type==Values.EventCommand;
        }
    private int[] eTypes = {Values.EventSetting};
    public int[] eventTypes(){ return eTypes; }
}
