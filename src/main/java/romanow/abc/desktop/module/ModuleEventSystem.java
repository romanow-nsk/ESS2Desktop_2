package romanow.abc.desktop.module;

import romanow.abc.core.constants.Values;

public class ModuleEventSystem extends ModuleEventAll {
    public ModuleEventSystem(){}
    public boolean typeFilter(int type) {
        return type== Values.EventSystem || type == Values.EventFile;
        }
}
