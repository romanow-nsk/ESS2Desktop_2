package romanow.abc.desktop.module;

import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.entity.metadata.Meta2GUIForm;
import romanow.abc.core.entity.subjectarea.MetaGUIForm;
import romanow.abc.desktop.MainBaseFrame;
import romanow.abc.desktop.view.FormContext;
import romanow.abc.desktop.view2.FormContext2;

import javax.swing.*;

public class Module implements I_Module{
    protected JPanel panel;
    protected RestAPIBase service;
    protected RestAPIESS2 service2;
    protected Meta2GUIForm form;
    protected String token;
    protected FormContext2 context;
    protected MainBaseFrame client;
    public Module(){}
    @Override
    public void init(MainBaseFrame client0, JPanel panel, RestAPIBase service, RestAPIESS2 service2, String token, Meta2GUIForm form, FormContext2 formContext) {
        client = client0;
        this.form = form;
        this.panel = panel;
        this.service = service;
        this.service2 = service2;
        this.token = token;
        context = formContext;
        //System.out.println("Модуль формы "+form.getModuleName());
        }

    @Override
    public void repaintView() {
        }
    @Override
    public void repaintValues() {
        }

}
