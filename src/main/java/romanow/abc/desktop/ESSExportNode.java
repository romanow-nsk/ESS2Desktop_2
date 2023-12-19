package romanow.abc.desktop;

import com.google.gson.Gson;
import retrofit2.Call;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.DBRequest;
import romanow.abc.core.ErrorList;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.baseentityes.JLong;
import romanow.abc.core.entity.baseentityes.JString;
import romanow.abc.core.entity.metadata.Meta2XStream;
import romanow.abc.core.entity.subject2area.*;
import romanow.abc.core.utils.Pair;
import romanow.abc.desktop.APICallSync;
import romanow.abc.desktop.ClientContext;
import romanow.abc.desktop.I_LoginBack;
import romanow.abc.desktop.Login;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ESSExportNode {
    private ESS2Architecture architecture;
    private ClientContext exportContext;
    private ClientContext importContext;
    private String passWord;
    private long tt0;
    private String debugToken;
    private RestAPIBase service;
    private Login loginForm;
    private Gson gson = new Gson();
    private ErrorList errors = new ErrorList();
    private HashMap<Long,Long> deviceIdConvert = new HashMap<>();   // Конвертация oid для Device
    private HashMap<Long,Long> equipIdConvert = new HashMap<>();    // Конвертация oid для Device
    public ESSExportNode(ESS2Architecture architecture0, ClientContext importContex0){
        architecture = architecture0;
        importContext = importContex0;
        exportNodeLogin();
        }
    private void addError(String ss){
        timeMes(true,ss);
        }
    private void timeMes(String ss){
        timeMes(false,ss);
        }
    private void timeMes(boolean error, String ss){
        ss = String.format("%5.3f ",(System.currentTimeMillis()-tt0)/1000.)+ss;
        if (error)
            errors.addError(ss);
        else
            errors.addInfo(ss);
        }
    private void exportExit(boolean error,String ss){
        timeMes(error,ss);
        new APICallSync<JEmpty>(){
            @Override
            public Call<JEmpty> apiFun() {
                return exportContext.getService().logoff(exportContext.getDebugToken());
            }}.call();
        System.out.println(errors.toString());
        }
    private boolean exportExit(Pair<String,?> res){
        if (res.o1==null)
            return false;
        exportExit(true,res.o1);
        return true;
        }
    private void exportNode() {
        tt0 = System.currentTimeMillis();
        debugToken = exportContext.getDebugToken();
        service = exportContext.getService();
        Pair<String, JString> res1 = new APICallSync<JString>() {
            @Override
            public Call<JString> apiFun() {
                return service.clearDB(exportContext.getDebugToken(), passWord);
                }
            }.call();
        if (exportExit(res1))
            return;
        timeMes("Очистка БД: " + res1.o2);
        long oid = addEntity(architecture);
        if (oid==-1){
            exportExit(true,"Экспорт прерван");
            return;
            }
        long archOid = oid;
        timeMes("architecture oid=" + archOid);
        for (ESS2View view : architecture.getViews()) {
            view.getESS2Architecture().setOid(archOid);
            ESS2MetaFile metaFile = view.getMetaFile().getRef();
            oid = addMetaFile(metaFile);
            if (oid==-1)
                continue;
            view.getMetaFile().setOid(oid);
            oid = addEntity(view);
            if (oid==-1)
                continue;
            title(view,oid);
            }
        for (ESS2Device device : architecture.getDevices()) {
            long oldOid = device.getOid();
            device.getESS2Architecture().setOid(archOid);
            oid = addEntity(device);
            if (oid==-1)
                continue;
            deviceIdConvert.put(oldOid,oid);
            timeMes("view oid=" + oid+" "+device.getTitle());
            }
        for (ESS2EnvValue envValue : architecture.getEnvValues()) {
            envValue.getESS2Architecture().setOid(archOid);
            oid = addEntity(envValue);
            if (oid==-1)
                continue;
            title(envValue,oid);
            }
        for (ESS2EquipEmulator emulator : architecture.getEmulators()) {
            emulator.getESS2Architecture().setOid(archOid);
            ESS2MetaFile metaFile = emulator.getMetaFile().getRef();
            if (metaFile==null)
                emulator.getMetaFile().setOid(0);
            else{
                oid = addMetaFile(metaFile);
                if (oid==-1)
                    continue;
                emulator.getMetaFile().setOid(oid);
                }
            oid = addEntity(emulator);
            if (oid==-1)
                continue;
            title(emulator,oid);
            }
        for (ESS2ProfilerModule profile : architecture.getProfilers()) {
            profile.getESS2Architecture().setOid(archOid);
            Artifact artifact = profile.getProfileData().getRef();
            oid = addArtifact(artifact);
            if (oid==-1)
                continue;
            profile.getProfileData().setOid(oid);
            oid = addEntity(profile);
            if (oid==-1)
                continue;
            title(profile,oid);
            }
        for (ESS2ScriptFile script : architecture.getScripts()) {
            script.getESS2Architecture().setOid(archOid);
            Artifact artifact = script.getFile().getRef();
            oid = addArtifact(artifact);
            if (oid==-1)
                continue;
            script.getFile().setOid(oid);
            oid = addEntity(script);
            if (oid==-1)
                continue;
            title(script,oid);
            }
        for (ESS2ModBusGate gate : architecture.getGates()) {
            gate.getESS2Architecture().setOid(archOid);
            Long newOid = deviceIdConvert.get(gate.getDevice().getOid());
            if (newOid==null){
                addError(gate.getTitle()+" oid="+gate.getOid()+" не найден ESS2Device.oid="+gate.getDevice().getOid());
                continue;
                }
            gate.getDevice().setOid(newOid);
            oid = addEntity(gate);
            if (oid==-1)
                continue;
            title(gate,oid);
            }
        for (ESS2Equipment equipment : architecture.getEquipments()) {
            long oldOid = equipment.getOid();
            equipment.getESS2Architecture().setOid(archOid);
            ESS2MetaFile metaFile = equipment.getMetaFile().getRef();
            oid = addMetaFile(metaFile);
            if (oid==-1)
                continue;
            equipment.getMetaFile().setOid(oid);
            oid = addEntity(equipment);
            if (oid==-1)
                continue;
            equipIdConvert.put(oldOid,oid);
            title(equipment,oid);
            }
        ArrayList<ESS2LogUnit> units = new ArrayList<>();
        for(ESS2Equipment equipment : architecture.getEquipments())
            for(ESS2LogUnit logUnit : equipment.getLogUnits())
                units.add(logUnit);
        for (ESS2LogUnit logUnit : units) {
            Long newOid = equipIdConvert.get(logUnit.getESS2Equipment().getOid());
            if (newOid==null){
                addError(logUnit.getTitle()+" oid="+logUnit.getOid()+" не найден ESS2Equipment.oid="+logUnit.getESS2Equipment().getOid());
                continue;
                }
            logUnit.getESS2Equipment().setOid(newOid);
            newOid = deviceIdConvert.get(logUnit.getDevice().getOid());
            if (newOid==null){
                addError(logUnit.getTitle()+" oid="+logUnit.getOid()+" не найден ESS2Device.oid="+logUnit.getDevice().getOid());
                continue;
                }
            logUnit.getDevice().setOid(newOid);
            oid = addEntity(logUnit);
            if (oid==-1)
                continue;
            title(logUnit,oid);
            }
        exportExit(false,"Экспорт конфигурации "+architecture.getTitle()+" завершен");
        }
    private void title(ESS2Entity entity, long oid){
        timeMes(entity.getClass().getSimpleName()+" oid=" + oid+" "+entity.getTitle());
        }
    private long addArtifact(Artifact artifact) {
        //-------- Загрузить старый артефакт как текст
        Pair<String, String> res2 = importContext.loadFileAsStringSync(artifact);
        if (res2.o1 != null) {
            addError(res2.o1 + " " + artifact.getTitle());
            return -1;
            }
        //--------- Выгрузить в новый артефакт
        Pair<String, Artifact> res5 = uploadArtifact(artifact, res2.o2);
        if (res5.o1 != null) {
            addError(res5.o1 + " " + artifact.getTitle());
            return -1;
            }
        return res5.o2.getOid();
        }
    private long addMetaFile(ESS2MetaFile metaFile){
        //-------- Загрузить старый артефакт как текст
        Pair<String, String> res2 = importContext.loadFileAsStringSync(metaFile.getFile().getRef());
        if (res2.o1!=null){
            addError(res2.o1 + " " + metaFile.getTitle());
            return -1;
            }
        //--------- Выгрузить в новый артефакт
        Pair<String, Artifact> res5 = uploadArtifact(metaFile.getFile().getRef(),res2.o2);
        if (res5.o1 != null) {
            addError(res5.o1 + " " + metaFile.getTitle());
            return -1;
            }
        //-------- Скопировать MetaFile с новым артефактом
        metaFile.getFile().setOidRef(res5.o2);
        timeMes("artifact oid=" + res5.o2.getOid()+" "+res5.o2.getTitle());
        Pair<String, JLong> res3 = new APICallSync<JLong>(){
            @Override
            public Call<JLong> apiFun() {
                return service.addEntity(exportContext.getDebugToken(),new DBRequest(metaFile, new Gson()),0);
                }
            }.call();
        if (res3.o1!=null){
            addError(res3.o1 + " " + metaFile.getTitle());
            return -1;
            }
        long metaFileOid = res3.o2.getValue();
        timeMes("metaFile oid="+metaFileOid+" "+metaFile.getTitle());
        return metaFileOid;
        }
    private Pair<String,Artifact> uploadArtifact(Artifact src, String text){
        String fname = src.getOriginalName();
        Pair<String, Artifact> res2 = new APICallSync<Artifact>(){
            @Override
            public Call<Artifact> apiFun() {
                return service.createArtifactFromString(exportContext.getDebugToken(),fname,new JString(text));
                }
            }.call();
        if (res2.o1!=null){
            return new Pair<>(res2.o1,null);
            }
        long artifactOid = res2.o2.getOid();
        timeMes("artifact oid="+artifactOid+" "+src.getTitle());
        return new Pair<>(null,res2.o2);
        }
    private void exportNodeLogin(){
        exportContext = new ClientContext();
        loginForm = new Login(exportContext, new I_LoginBack() {
            @Override
            public void onPush() {
            }
            @Override
            public void onLoginSuccess(String passWord0) {
                passWord = passWord0;
                debugToken = exportContext.getDebugToken();
                service = exportContext.getService();
                tt0 = System.currentTimeMillis();
                if (loginForm.getClientIP().equals(importContext.getServerIP()) && loginForm.getPort().equals(importContext.getServerPort())){
                    exportExit(true,"Экспорт в текущий узел");
                    return;
                    }
                new OK(200, 200, "Экспорт " + architecture.getTitle() + " в " + loginForm.getClientIP() + ":" + loginForm.getPort(), new I_Button() {
                    @Override
                    public void onPush() {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                exportNode();
                            }
                        }).start();
                        }
                    });
                }
            @Override
            public void sendPopupMessage(JFrame parent, Container button, String text) {
                System.out.println("Экспорт конфигурации: "+text);
                }
            });
        loginForm.setLoginName(importContext.getLoginUser().getLogin());
        loginForm.setPassword(importContext.getLoginUser().getPassword());
        }
    public long addEntity(ESS2Entity entity){
        String name=entity.getClass().getSimpleName();
        if (name.startsWith("ESS2"))
            name = name.substring(4);
        Pair<String, JLong> res6 = new APICallSync<JLong>() {
            @Override
            public Call<JLong> apiFun() {
                return service.addEntity(exportContext.getDebugToken(), new DBRequest(entity, gson), 0);
                }
            }.call();
        if (res6.o1!=null){
            addError(name+": "+res6.o1);
            return -1;
            }
        timeMes(name+" oid=" + res6.o2+" "+entity.getTitle());
        return res6.o2.getValue();
        }
    }


