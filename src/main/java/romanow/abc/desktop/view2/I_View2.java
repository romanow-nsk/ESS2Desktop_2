package romanow.abc.desktop.view2;

import romanow.abc.core.UniException;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.metadata.Meta2RegLink;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subjectarea.MetaExternalSystem;
import romanow.abc.core.entity.subjectarea.MetaGUIElement;
import romanow.abc.core.entity.subjectarea.MetaRegister;
import romanow.abc.desktop.view.FormContext;
import romanow.abc.desktop.view.I_GUIEvent;

import javax.swing.*;

public interface I_View2 {
    public int getType();
    public String getTypeName();
    public String getTitle();
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0);
    public void putValue(int vv) throws UniException;
    public void putValue(Meta2Register register, int vv, int idx) throws UniException;
    public Meta2RegLink[] getDataLinks();
    public Meta2RegLink[] getSettingsLinks();
    public void repaintBefore();
    public void repaintValues();
}
