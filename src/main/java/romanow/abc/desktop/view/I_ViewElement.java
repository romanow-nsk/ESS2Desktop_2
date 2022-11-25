package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.MetaExternalSystem;
import romanow.abc.core.entity.subjectarea.MetaGUIElement;
import romanow.abc.core.entity.subjectarea.MetaRegister;

import javax.swing.*;

public interface I_ViewElement {
    public int getType();
    public String getTypeName();
    public String getTitle();
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register, MetaGUIElement element, I_ModbusGroupDriver plm, I_GUIEvent onEvent);
    public void addToPanel(JPanel panel);
    public void putValue(int vv) throws UniException;
    public int []getRegNum();
    public void putValue(int vv[]) throws UniException;
    public boolean needRegister();
}
