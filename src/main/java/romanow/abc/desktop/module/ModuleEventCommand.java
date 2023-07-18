package romanow.abc.desktop.module;

import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2GUIForm;
import romanow.abc.desktop.MainBaseFrame;
import romanow.abc.desktop.view2.FormContext2;

import javax.swing.*;

public class ModuleEventCommand extends ModuleEventAll {
    public ModuleEventCommand(){ super(false);}
    @Override
    public void init(MainBaseFrame client, JPanel panel, RestAPIBase service, RestAPIESS2 service2, String token, Meta2GUIForm form, FormContext2 formContext) {
        super.init(client, panel, service, service2,token, form, formContext);
        }
    public boolean typeFilter(int type) {
        return type== Values.EventCommand;
        }
    private int[] eTypes = {Values.EventCommand};
    public int[] eventTypes(){ return eTypes; }
    }
