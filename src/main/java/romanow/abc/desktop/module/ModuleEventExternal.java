package romanow.abc.desktop.module;

import romanow.abc.core.constants.Values;

public class ModuleEventExternal extends ModuleEventAll {
    public ModuleEventExternal(){}
    public boolean typeFilter(int type) {
        return type== Values.EventExternal;
    }
}
