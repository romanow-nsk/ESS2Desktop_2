/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop.wizard;

import java.util.ArrayList;

import com.google.gson.Gson;
import okhttp3.MultipartBody;
import retrofit2.Call;
import romanow.abc.core.API.RestAPICommon;
import romanow.abc.core.DBRequest;
import romanow.abc.core.OidString;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.artifacts.Artifact;
import romanow.abc.core.entity.baseentityes.JBoolean;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.subject2area.ESS2Entity;
import romanow.abc.core.entity.subject2area.ESS2Equipment;
import romanow.abc.core.entity.subject2area.ESS2MetaFile;
import romanow.abc.core.entity.subject2area.ESS2Node;
import romanow.abc.core.utils.FileNameExt;
import romanow.abc.desktop.*;

import static romanow.abc.desktop.BasePanel.EventLogToFront;

/**
 *
 * @author romanow0
 */
public class WizardESS2Equipment extends WizardBaseViewDB {

    private ESS2Node node;
    private ESS2Equipment equipment;
    private boolean onStart=false;
    private void selectChoice(){
        if (equipment.getMetaFile().getOid()==0){
            MetaFile.select(0);
            return;
            }
        long oid = equipment.getMetaFile().getRef().getOid();
        for(int i=0;i<panel.getMetaData().size();i++){
            if (panel.getMetaData().get(i).getOid()==oid){
                MetaFile.select(i+1);
                return;
                }
            MetaFile.select(0);
            }
        }
    public WizardESS2Equipment(ESSMetaPanel frame0, ESS2Entity entity0, I_Wizard back0) {
        super("Оборудование",frame0,entity0,back0);
        initComponents();
        setSize(750,300);
        resizeHeight(300);
        onStart = true;
        equipment = (ESS2Equipment)entity0;
        if (equipment.getIec61850LNN0().getOid()!=0){
            LNN0_61850.setText(equipment.getIec61850LNN0().getRef().getOriginalName());
            }
        if (equipment.getIec61850LNN0Template().getOid()!=0){
            LNN0_61850.setText(equipment.getIec61850LNN0Template().getRef().getOriginalName());
            }
        if (equipment.getIec61850Template().getOid()!=0){
            LNN0_61850.setText(equipment.getIec61850Template().getRef().getOriginalName());
            }
        MultiUnit.setSelected(equipment.isMultiUnit());
        IEC61850LNType.setText(equipment.getIec61850LNType());
        MetaFile.removeAll();
        MetaFile.add("...");
        for(ESS2MetaFile metaFile : panel.getMetaData())
            MetaFile.add(metaFile.toString());
        selectChoice();
        onStart = false;
        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        MetaFile = new java.awt.Choice();
        SetMetaFile = new javax.swing.JButton();
        MultiUnit = new javax.swing.JCheckBox();
        LNN0_61850 = new javax.swing.JTextField();
        DownLoadLNN0_61850 = new javax.swing.JButton();
        UploadLNN0_61851 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        IEC61850LNType = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        DeleteLNN0_61850 = new javax.swing.JButton();
        LNN0Template61850 = new javax.swing.JTextField();
        DownLoadLNN0Template_61850 = new javax.swing.JButton();
        UploadLNN0Template_61850 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        DeleteLNN0Template_61850 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        Template61850 = new javax.swing.JTextField();
        DownLoadTemplate_61850 = new javax.swing.JButton();
        UploadTemplate_61850 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        DeleteTemplate_61850 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(jSeparator1);
        jSeparator1.setBounds(60, 123, 480, 10);
        getContentPane().add(MetaFile);
        MetaFile.setBounds(10, 90, 400, 20);

        SetMetaFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/save.png"))); // NOI18N
        SetMetaFile.setBorderPainted(false);
        SetMetaFile.setContentAreaFilled(false);
        SetMetaFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetMetaFileActionPerformed(evt);
            }
        });
        getContentPane().add(SetMetaFile);
        SetMetaFile.setBounds(420, 80, 30, 30);

        MultiUnit.setText("Мульти-Unit");
        MultiUnit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MultiUnitItemStateChanged(evt);
            }
        });
        getContentPane().add(MultiUnit);
        MultiUnit.setBounds(460, 90, 120, 20);

        LNN0_61850.setEnabled(false);
        getContentPane().add(LNN0_61850);
        LNN0_61850.setBounds(200, 130, 220, 25);

        DownLoadLNN0_61850.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        DownLoadLNN0_61850.setBorderPainted(false);
        DownLoadLNN0_61850.setContentAreaFilled(false);
        DownLoadLNN0_61850.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownLoadLNN0_61850ActionPerformed(evt);
            }
        });
        getContentPane().add(DownLoadLNN0_61850);
        DownLoadLNN0_61850.setBounds(470, 130, 30, 30);

        UploadLNN0_61851.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        UploadLNN0_61851.setBorderPainted(false);
        UploadLNN0_61851.setContentAreaFilled(false);
        UploadLNN0_61851.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadLNN0_61851ActionPerformed(evt);
            }
        });
        getContentPane().add(UploadLNN0_61851);
        UploadLNN0_61851.setBounds(430, 130, 30, 30);

        jLabel1.setText("(группа узлов)");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(10, 155, 90, 16);

        jLabel2.setText("LNType");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(10, 135, 60, 16);

        jLabel3.setText("LNN0");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(150, 135, 60, 16);

        IEC61850LNType.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IEC61850LNTypeKeyPressed(evt);
            }
        });
        getContentPane().add(IEC61850LNType);
        IEC61850LNType.setBounds(60, 130, 80, 25);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setText("61850");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(10, 110, 60, 20);

        DeleteLNN0_61850.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        DeleteLNN0_61850.setBorderPainted(false);
        DeleteLNN0_61850.setContentAreaFilled(false);
        DeleteLNN0_61850.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteLNN0_61850ActionPerformed(evt);
            }
        });
        getContentPane().add(DeleteLNN0_61850);
        DeleteLNN0_61850.setBounds(510, 130, 30, 30);

        LNN0Template61850.setEnabled(false);
        getContentPane().add(LNN0Template61850);
        LNN0Template61850.setBounds(200, 160, 220, 25);

        DownLoadLNN0Template_61850.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        DownLoadLNN0Template_61850.setBorderPainted(false);
        DownLoadLNN0Template_61850.setContentAreaFilled(false);
        DownLoadLNN0Template_61850.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownLoadLNN0Template_61850ActionPerformed(evt);
            }
        });
        getContentPane().add(DownLoadLNN0Template_61850);
        DownLoadLNN0Template_61850.setBounds(470, 160, 30, 30);

        UploadLNN0Template_61850.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        UploadLNN0Template_61850.setBorderPainted(false);
        UploadLNN0Template_61850.setContentAreaFilled(false);
        UploadLNN0Template_61850.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadLNN0Template_61850ActionPerformed(evt);
            }
        });
        getContentPane().add(UploadLNN0Template_61850);
        UploadLNN0Template_61850.setBounds(430, 160, 30, 30);

        jLabel4.setText("Template");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(130, 175, 60, 16);

        DeleteLNN0Template_61850.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        DeleteLNN0Template_61850.setBorderPainted(false);
        DeleteLNN0Template_61850.setContentAreaFilled(false);
        DeleteLNN0Template_61850.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteLNN0Template_61850ActionPerformed(evt);
            }
        });
        getContentPane().add(DeleteLNN0Template_61850);
        DeleteLNN0Template_61850.setBounds(510, 160, 30, 30);

        jLabel5.setText("LNN0");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(150, 160, 60, 16);

        Template61850.setEnabled(false);
        getContentPane().add(Template61850);
        Template61850.setBounds(200, 190, 220, 25);

        DownLoadTemplate_61850.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/download.png"))); // NOI18N
        DownLoadTemplate_61850.setBorderPainted(false);
        DownLoadTemplate_61850.setContentAreaFilled(false);
        DownLoadTemplate_61850.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DownLoadTemplate_61850ActionPerformed(evt);
            }
        });
        getContentPane().add(DownLoadTemplate_61850);
        DownLoadTemplate_61850.setBounds(470, 190, 30, 30);

        UploadTemplate_61850.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/upload.png"))); // NOI18N
        UploadTemplate_61850.setBorderPainted(false);
        UploadTemplate_61850.setContentAreaFilled(false);
        UploadTemplate_61850.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadTemplate_61850ActionPerformed(evt);
            }
        });
        getContentPane().add(UploadTemplate_61850);
        UploadTemplate_61850.setBounds(430, 190, 30, 30);

        jLabel7.setText("Template");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(130, 200, 60, 16);

        DeleteTemplate_61850.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        DeleteTemplate_61850.setBorderPainted(false);
        DeleteTemplate_61850.setContentAreaFilled(false);
        DeleteTemplate_61850.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteTemplate_61850ActionPerformed(evt);
            }
        });
        getContentPane().add(DeleteTemplate_61850);
        DeleteTemplate_61850.setBounds(510, 190, 30, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SetMetaFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetMetaFileActionPerformed
        if (MetaFile.getSelectedIndex()==0){
            main.popup("Не выбран мета-файл");
            return;
            }
        ESS2MetaFile metaFile = panel.getMetaData().get(MetaFile.getSelectedIndex()-1);
        if (!Values.isEquipmentType(metaFile.getMetaType())) {
            main.popup("Недопустимый тип для мета-файла оборудования");
            return;
            }
        equipment.getMetaFile().setOid(metaFile.getOid());
        oneUpdate("Изменено metaFile: "+metaFile.toString());
    }//GEN-LAST:event_SetMetaFileActionPerformed

    private void MultiUnitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MultiUnitItemStateChanged
        if (onStart)
            return;
        equipment.setMultiUnit(MultiUnit.isSelected());
        oneUpdate("Изменено multiUnit: "+MultiUnit.isSelected());
    }//GEN-LAST:event_MultiUnitItemStateChanged

    private void DownLoadLNN0_61850ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownLoadLNN0_61850ActionPerformed
        if (equipment.getIec61850LNN0().getOid()!=0)
            main.loadFile(equipment.getIec61850LNN0().getRef());
    }//GEN-LAST:event_DownLoadLNN0_61850ActionPerformed


    private void upload61850LNN0(){
        FileNameExt fname = main.getInputFileName("Импорт описания LNN0 для 61850", "*.xml", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(), "Meta-Data import", fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact oo) {
                equipment.getIec61850LNN0().setOidRef(oo);
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntityField(main.getDebugToken(),"iec61850LNN0", new DBRequest(equipment, new Gson()));
                        }
                    @Override
                    public void onSucess(JEmpty oo) {
                        LNN0_61850.setText(equipment.getIec61850LNN0().getRef().getOriginalName());
                        main.popup("Импорт LNN0 для 61850");
                        }
                    };
                }
            };
        }

    private void upload61850LNN0Template(){
        FileNameExt fname = main.getInputFileName("Импорт описания LNN0 в template для 61850", "*.xml", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(), "Meta-Data import", fname.fileName(), body);
                }
            @Override
            public void onSucess(final Artifact oo) {
                equipment.getIec61850LNN0Template().setOidRef(oo);
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntityField(main.getDebugToken(),"iec61850LNN0Template", new DBRequest(equipment, new Gson()));
                        }
                    @Override
                    public void onSucess(JEmpty oo) {
                        LNN0Template61850.setText(equipment.getIec61850LNN0Template().getRef().getOriginalName());
                        main.popup("Импорт LNN0 в template для 61850");
                        }
                    };
                }
            };
        }

    private void upload61850Template(){
        FileNameExt fname = main.getInputFileName("Импорт template для 61850", "*.xml", null);
        final MultipartBody.Part body = RestAPICommon.createMultipartBody(fname);
        new APICall<Artifact>(main) {
            @Override
            public Call<Artifact> apiFun() {
                return main.getService().upload(main.getDebugToken(), "Meta-Data import", fname.fileName(), body);
            }
            @Override
            public void onSucess(final Artifact oo) {
                equipment.getIec61850Template().setOidRef(oo);
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntityField(main.getDebugToken(),"iec61850Template", new DBRequest(equipment, new Gson()));
                    }
                    @Override
                    public void onSucess(JEmpty oo) {
                        Template61850.setText(equipment.getIec61850Template().getRef().getOriginalName());
                        main.popup("Импорт template для 61850");
                        }
                    };
                }
            };
        }
    private void UploadLNN0_61851ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadLNN0_61851ActionPerformed
        if (equipment.getIec61850LNN0().getOid()!=0){
            new APICall<JEmpty>(main) {
                @Override
                public Call<JEmpty> apiFun() {
                    return main.getService().removeArtifact(main.getDebugToken(), equipment.getIec61850LNN0().getOid());
                    }
                @Override
                public void onSucess(JEmpty oo) {
                    upload61850LNN0();
                    }
                };
            }
        else
            upload61850LNN0();
    }//GEN-LAST:event_UploadLNN0_61851ActionPerformed

    private void IEC61850LNTypeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IEC61850LNTypeKeyPressed
        onStringKeyPressed("iec61850LNType", IEC61850LNType, evt, new I_WizardActionString() {
            @Override
            public void onAction(String value) {
                equipment.setIec61850LNType(value);
            }
        });
    }//GEN-LAST:event_IEC61850LNTypeKeyPressed



    private void deleteLNN0(){
        new APICall<JEmpty>(main) {
            @Override
            public Call<JEmpty> apiFun() {
                return main.getService().removeArtifact(main.getDebugToken(), equipment.getIec61850LNN0().getOid());
            }
            @Override
            public void onSucess(JEmpty oo) {
                equipment.getIec61850LNN0().setOid(0);
                equipment.getIec61850LNN0().setRef(null);
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntityField(main.getDebugToken(),"iec61850LNN0", new DBRequest(equipment, new Gson()));
                        }
                    @Override
                    public void onSucess(JEmpty oo) {
                        LNN0_61850.setText("");
                        main.popup("Удаление LNN0 для 61850");
                        }
                    };
                }
            };
        }

    private void DeleteLNN0_61850ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteLNN0_61850ActionPerformed
        if (equipment.getIec61850LNN0().getOid()==0)
            return;
        new OK(200, 200, "Удалить " + equipment.getIec61850LNN0().getTitle(), new I_Button() {
            @Override
            public void onPush() {
                deleteLNN0();
                };
            });
    }//GEN-LAST:event_DeleteLNN0_61850ActionPerformed

    private void DownLoadLNN0Template_61850ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownLoadLNN0Template_61850ActionPerformed
        if (equipment.getIec61850LNN0Template().getOid()!=0)
            main.loadFile(equipment.getIec61850LNN0Template().getRef());
    }//GEN-LAST:event_DownLoadLNN0Template_61850ActionPerformed

    private void UploadLNN0Template_61850ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadLNN0Template_61850ActionPerformed
        if (equipment.getIec61850LNN0Template().getOid()!=0){
            new APICall<JEmpty>(main) {
                @Override
                public Call<JEmpty> apiFun() {
                    return main.getService().removeArtifact(main.getDebugToken(), equipment.getIec61850LNN0Template().getOid());
                }
                @Override
                public void onSucess(JEmpty oo) {
                    upload61850LNN0Template();
                    }
                };
            }
        else
            upload61850LNN0Template();
    }//GEN-LAST:event_UploadLNN0Template_61850ActionPerformed

    private void deleteLNN0Template(){
        new APICall<JEmpty>(main) {
            @Override
            public Call<JEmpty> apiFun() {
                return main.getService().removeArtifact(main.getDebugToken(), equipment.getIec61850LNN0Template().getOid());
            }
            @Override
            public void onSucess(JEmpty oo) {
                equipment.getIec61850LNN0Template().setOid(0);
                equipment.getIec61850LNN0Template().setRef(null);
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntityField(main.getDebugToken(),"iec61850LNN0Template", new DBRequest(equipment, new Gson()));
                    }
                    @Override
                    public void onSucess(JEmpty oo) {
                        LNN0Template61850.setText("");
                        main.popup("Удаление LNN0 в template для 61850");
                    }
                };
            }
        };
    }

    private void DeleteLNN0Template_61850ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteLNN0Template_61850ActionPerformed
        if (equipment.getIec61850LNN0Template().getOid()==0)
            return;
        new OK(200, 200, "Удалить " + equipment.getIec61850LNN0Template().getTitle(), new I_Button() {
            @Override
            public void onPush() {
                deleteLNN0Template();
            };
        });

    }//GEN-LAST:event_DeleteLNN0Template_61850ActionPerformed

    private void DownLoadTemplate_61850ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DownLoadTemplate_61850ActionPerformed
        if (equipment.getIec61850Template().getOid()!=0)
            main.loadFile(equipment.getIec61850Template().getRef());
    }//GEN-LAST:event_DownLoadTemplate_61850ActionPerformed

    private void UploadTemplate_61850ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadTemplate_61850ActionPerformed
        if (equipment.getIec61850Template().getOid()!=0){
            new APICall<JEmpty>(main) {
                @Override
                public Call<JEmpty> apiFun() {
                    return main.getService().removeArtifact(main.getDebugToken(), equipment.getIec61850Template().getOid());
                }
                @Override
                public void onSucess(JEmpty oo) {
                    upload61850Template();
                }
            };
        }
        else
            upload61850Template();
    }//GEN-LAST:event_UploadTemplate_61850ActionPerformed


    private void deleteTemplate(){
        new APICall<JEmpty>(main) {
            @Override
            public Call<JEmpty> apiFun() {
                return main.getService().removeArtifact(main.getDebugToken(), equipment.getIec61850Template().getOid());
            }
            @Override
            public void onSucess(JEmpty oo) {
                equipment.getIec61850Template().setOid(0);
                equipment.getIec61850Template().setRef(null);
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.getService().updateEntityField(main.getDebugToken(),"iec61850Template", new DBRequest(equipment, new Gson()));
                    }
                    @Override
                    public void onSucess(JEmpty oo) {
                        LNN0Template61850.setText("");
                        main.popup("Удаление template для 61850");
                    }
                };
            }
        };
    }

    private void DeleteTemplate_61850ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteTemplate_61850ActionPerformed
        if (equipment.getIec61850Template().getOid()==0)
            return;
        new OK(200, 200, "Удалить " + equipment.getIec61850Template().getTitle(), new I_Button() {
            @Override
            public void onPush() {
                deleteTemplate();
            };
        });

    }//GEN-LAST:event_DeleteTemplate_61850ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WizardESS2Equipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WizardESS2Equipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WizardESS2Equipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WizardESS2Equipment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new WizardESS2Connector().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteLNN0Template_61850;
    private javax.swing.JButton DeleteLNN0_61850;
    private javax.swing.JButton DeleteTemplate_61850;
    private javax.swing.JButton DownLoadLNN0Template_61850;
    private javax.swing.JButton DownLoadLNN0_61850;
    private javax.swing.JButton DownLoadTemplate_61850;
    private javax.swing.JTextField IEC61850LNType;
    private javax.swing.JTextField LNN0Template61850;
    private javax.swing.JTextField LNN0_61850;
    private java.awt.Choice MetaFile;
    private javax.swing.JCheckBox MultiUnit;
    private javax.swing.JButton SetMetaFile;
    private javax.swing.JTextField Template61850;
    private javax.swing.JButton UploadLNN0Template_61850;
    private javax.swing.JButton UploadLNN0_61851;
    private javax.swing.JButton UploadTemplate_61850;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
}
