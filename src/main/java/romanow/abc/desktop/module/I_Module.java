package romanow.abc.desktop.module;

import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.entity.metadata.Meta2GUIForm;
import romanow.abc.core.entity.subjectarea.MetaGUIForm;
import romanow.abc.desktop.MainBaseFrame;
import romanow.abc.desktop.view.FormContext;
import romanow.abc.desktop.view2.FormContext2;

import javax.swing.*;

public interface I_Module {
    public void init(MainBaseFrame client, JPanel panel, RestAPIBase service, RestAPIESS2 service2, String token, Meta2GUIForm form, FormContext2 formContext);
    public void repaintView();
    public void repaintValues();
}
