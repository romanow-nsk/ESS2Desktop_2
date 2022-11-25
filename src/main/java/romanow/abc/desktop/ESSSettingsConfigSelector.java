/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import java.util.ArrayList;

import romanow.abc.core.DBRequest;
import romanow.abc.core.UniException;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.baseentityes.JLong;
import romanow.abc.core.entity.subjectarea.MetaExternalSystem;
import romanow.abc.core.entity.subjectarea.PLMConfig;
import romanow.abc.core.utils.OwnDateTime;
import retrofit2.Call;

/**
 *
 * @author romanow
 */
public class ESSSettingsConfigSelector extends javax.swing.JPanel {
    private OwnDateTime time1=new OwnDateTime(false);
    private OwnDateTime time2=new OwnDateTime(false);
    private MetaExternalSystem meta;
    private ArrayList<PLMConfig> configs;
    private PLMConfig selected=null;
    private ESSClient main;
    /**
     * Creates new form ESSStreamDataSelector
     */
    public ESSSettingsConfigSelector(){}
    public void init(ESSClient base0) {
        initComponents();
        main = base0;
        meta = base0.meta;
        refreahList();
        }

    private void refreahList(){
        selected=null;
        Config.removeAll();
        new APICall<ArrayList<PLMConfig>>(main){
            @Override
            public Call<ArrayList<PLMConfig>> apiFun() {
                return main.service2.getConfigList(main.debugToken);
            }
            @Override
            public void onSucess(ArrayList<PLMConfig> oo) {
                configs = oo;
                for(PLMConfig vv : configs)
                    Config.add(vv.getTitle());
                if (configs.size()==0)
                    return;
                selected = configs.get(0);
                refresh();
            }
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Config = new java.awt.Choice();
        CreateDate = new javax.swing.JTextField();
        Comment = new javax.swing.JTextField();
        Set = new javax.swing.JButton();
        Add = new javax.swing.JButton();
        Remove = new javax.swing.JButton();
        Title = new javax.swing.JTextField();
        CommentCur = new javax.swing.JTextField();
        User = new javax.swing.JTextField();

        jLabel1.setText("jLabel1");

        setLayout(null);

        Config.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ConfigItemStateChanged(evt);
            }
        });
        add(Config);
        Config.setBounds(10, 10, 340, 20);

        CreateDate.setEnabled(false);
        add(CreateDate);
        CreateDate.setBounds(10, 70, 140, 25);
        add(Comment);
        Comment.setBounds(10, 130, 420, 25);

        Set.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/settings.png"))); // NOI18N
        Set.setBorderPainted(false);
        Set.setContentAreaFilled(false);
        Set.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetActionPerformed(evt);
            }
        });
        add(Set);
        Set.setBounds(360, 5, 30, 30);

        Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        Add.setBorderPainted(false);
        Add.setContentAreaFilled(false);
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });
        add(Add);
        Add.setBounds(360, 100, 30, 30);

        Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        Remove.setBorderPainted(false);
        Remove.setContentAreaFilled(false);
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });
        add(Remove);
        Remove.setBounds(400, 5, 30, 30);
        add(Title);
        Title.setBounds(10, 100, 340, 25);

        CommentCur.setEnabled(false);
        add(CommentCur);
        CommentCur.setBounds(10, 40, 420, 25);

        User.setEnabled(false);
        add(User);
        User.setBounds(160, 70, 270, 25);
    }// </editor-fold>//GEN-END:initComponents

    private void refresh(){
        if (selected==null) return;
        CommentCur.setText(selected.getComment());
        CreateDate.setText(selected.getCreateDate().toString2());
        User.setText(selected.getAuthor().getTitle());
        }

    private void ConfigItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ConfigItemStateChanged
        CommentCur.setText("");
        CreateDate.setText("");
        User.setText("");
        if (configs.size()==0) return;
        selected = configs.get(Config.getSelectedIndex());
        refresh();
    }//GEN-LAST:event_ConfigItemStateChanged

    private void SetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetActionPerformed
        if (selected==null)
            return;
        new OK(200,200,"Выставить конфигурацию уставок",new I_Button() {
            @Override
            public void onPush() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.service2.setConfig(main.debugToken, selected.getOid());
                        }
                    @Override
                    public void onSucess(JEmpty oo) {}
                };
            }
        });

    }//GEN-LAST:event_SetActionPerformed

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        if (selected==null)
            return;
        new OK(200,200,"Удалить конфигурацию уставок",new I_Button() {
            @Override
            public void onPush() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.service2.removeConfig(main.debugToken, selected.getOid());
                        }
                    @Override
                    public void onSucess(JEmpty oo) {
                        main.meta.getConfigs().removeById(selected.getOid());   // Удалить у клиента
                        refreahList();
                        }
                };
            }
        });
    }//GEN-LAST:event_RemoveActionPerformed

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        new OK(200,200,"Сохранить конфигурацию уставок",new I_Button() {
            @Override
            public void onPush() {
                new APICall<JLong>(main) {
                    @Override
                    public Call<JLong> apiFun() {
                        return main.service2.addConfig(main.debugToken, Title.getText(),Comment.getText());
                        }
                    @Override
                    public void onSucess(final JLong oo) {
                        new APICall<DBRequest>(main){
                            @Override
                            public Call<DBRequest> apiFun() {
                                return main.service.getEntity(main.debugToken,"PLMConfig",oo.getValue(),0);
                                }
                            @Override
                            public void onSucess(DBRequest oo) {
                                try {
                                    main.meta.getConfigs().add((PLMConfig)oo.get(main.gson));
                                    refreahList();
                                    } catch (UniException ee){
                                        main.popup(ee.toString());
                                        }
                            }
                        };
                    }
                };
            }
        });
    }//GEN-LAST:event_AddActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JTextField Comment;
    private javax.swing.JTextField CommentCur;
    private java.awt.Choice Config;
    private javax.swing.JTextField CreateDate;
    private javax.swing.JButton Remove;
    private javax.swing.JButton Set;
    private javax.swing.JTextField Title;
    private javax.swing.JTextField User;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
