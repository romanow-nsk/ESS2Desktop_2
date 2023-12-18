package romanow.abc.desktop;

import com.google.gson.Gson;
import retrofit2.Call;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.DBRequest;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.baseentityes.JLong;
import romanow.abc.core.entity.baseentityes.JString;
import romanow.abc.core.entity.metadata.Meta2XStream;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subject2area.ESS2MetaFile;
import romanow.abc.core.entity.subject2area.ESS2View;
import romanow.abc.core.utils.Pair;
import romanow.abc.desktop.APICallSync;
import romanow.abc.desktop.ClientContext;
import romanow.abc.desktop.I_LoginBack;
import romanow.abc.desktop.Login;

import javax.swing.*;
import java.awt.*;

public class ESSExportNode {
    private ESS2Architecture architecture;
    private ClientContext exportContext;
    private ClientContext importContext;
    private String passWord;
    private long tt0;
    private String debugToken;
    private RestAPIBase service;
    private Login loginForm;
    public ESSExportNode(ESS2Architecture architecture0, ClientContext importContex0){
        architecture = architecture0;
        importContext = importContex0;
        exportNodeLogin();
        }
    private void timeMes(String ss){
        System.out.println(String.format("%5.3f ",(System.currentTimeMillis()-tt0)/1000.)+ss);
        }
    private void exportExit(String text){
        timeMes(text);
        Pair<String, JEmpty> res = new APICallSync<JEmpty>(){
            @Override
            public Call<JEmpty> apiFun() {
                return exportContext.getService().logoff(exportContext.getDebugToken());
            }}.call();
        }
    private void exportNode() {
        tt0 = System.currentTimeMillis();
        Gson gson = new Gson();
        Meta2XStream xStream = new Meta2XStream();
        Pair<String, JString> res1 = new APICallSync<JString>() {
            @Override
            public Call<JString> apiFun() {
                return exportContext.getService().clearDB(exportContext.getDebugToken(), passWord);
                }
            }.call();
        if (res1.o1 != null) {
            exportExit(res1.o1);
            return;
            }
        timeMes("Очистка БД: " + res1.o2);
        Pair<String, JLong> res2 = new APICallSync<JLong>() {
            @Override
            public Call<JLong> apiFun() {
                return exportContext.getService().addEntity(exportContext.getDebugToken(), new DBRequest(architecture, gson), 0);
                }
            }.call();
        if (res2.o1 != null) {
            exportExit(res2.o1);
            return;
            }
        long archOid = res2.o2.getValue();
        timeMes("architecture oid=" + archOid);
        for (ESS2View view : architecture.getViews()) {
            view.getESS2Architecture().setOid(archOid);
            ESS2MetaFile metaFile = view.getMetaFile().getRef();
            //------------- Скопировать MetaFile c привязанны артефактом
            Pair<String, Long> res3 = addMetaFile(metaFile);
            if (res3.o1 != null) {
                exportExit(res3.o1 + " " + metaFile.getTitle());
                return;
                }
            view.getMetaFile().setOid(res3.o2);
            //------------ Скопировать ESS2View
            Pair<String, JLong> res6 = new APICallSync<JLong>() {
                @Override
                public Call<JLong> apiFun() {
                    return exportContext.getService().addEntity(exportContext.getDebugToken(), new DBRequest(view, gson), 0);
                    }
                }.call();
            if (res6.o1 != null) {
                exportExit(res6.o1);
                return;
                }
            timeMes("view oid=" + res6.o2+" "+view.getTitle());
            }
        exportExit("Экспорт конфигурации "+architecture.getTitle()+" завершен");
        }
    private Pair<String,Long> addMetaFile(ESS2MetaFile metaFile){
        //-------- Загрузить старый артефакт как текст
        Pair<String, String> res2 = importContext.loadFileAsStringSync(metaFile.getFile().getRef());
        if (res2.o1!=null){
            return new Pair<>(res2.o1,null);
            }
        //--------- Выгрузить в новый артефакт
        Pair<String, Artifact> res5 = uploadArtifact(metaFile.getFile().getRef(),res2.o2);
        if (res5.o1 != null) {
            exportExit(res5.o1 + " " + metaFile.getTitle());
            return new Pair<>("",null);
            }
        //-------- Скопировать MetaFile с новым артефактом
        metaFile.getFile().setOidRef(res5.o2);
        timeMes("artifact oid=" + res5.o2.getOid()+" "+res5.o2.getTitle());
        Pair<String, JLong> res3 = new APICallSync<JLong>(){
            @Override
            public Call<JLong> apiFun() {
                return exportContext.getService().addEntity(exportContext.getDebugToken(),new DBRequest(metaFile, new Gson()),0);
                }
            }.call();
        if (res3.o1!=null){
            return new Pair<>(res3.o1,null);
            }
        long metaFileOid = res3.o2.getValue();
        timeMes("metaFile oid="+metaFileOid+" "+metaFile.getTitle());
        return new Pair<>(null,res3.o2.getValue());
        }
    private Pair<String,Artifact> uploadArtifact(Artifact src, String text){
        String fname = src.getOriginalName();
        Pair<String, Artifact> res2 = new APICallSync<Artifact>(){
            @Override
            public Call<Artifact> apiFun() {            // TODO - не берет длинный текст
                return exportContext.getService().createArtifactFromString2(exportContext.getDebugToken(),fname,new JString(text));
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
                if (loginForm.getClientIP().equals(importContext.getServerIP()) && loginForm.getPort().equals(importContext.getServerPort())){
                    exportExit("Экспорт в текущий узел");
                    return;
                    }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        exportNode();
                    }
                }).start();
            }
            @Override
            public void sendPopupMessage(JFrame parent, Container button, String text) {
                System.out.println("Экспорт конфигурации: "+text);
            }
        });
        loginForm.setLoginName(importContext.getLoginUser().getLogin());
        loginForm.setPassword(importContext.getLoginUser().getPassword());
    }

}
