/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import romanow.abc.core.DBRequest;
import romanow.abc.core.ErrorList;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.subject2area.ESS2Config;
import romanow.abc.core.utils.OwnDateTime;
import retrofit2.Call;

/**
 *
 * @author romanow
 */
public class ESSSettingsConfigSelector extends javax.swing.JPanel {
    private OwnDateTime time1=new OwnDateTime(false);
    private OwnDateTime time2=new OwnDateTime(false);
    private ArrayList<ESS2Config> configs = new ArrayList<>();
    private ESS2Config selected=null;
    private int selectedIdx=-1;
    private ESSClient main;
    /**
     * Creates new form ESSStreamDataSelector
     */
    public ESSSettingsConfigSelector(){}
    public void init(ESSClient base0) {
        initComponents();
        main = base0;
        refreshList();
        }

    private void refreshList(){
        selected=null;
        Config.removeAll();
        new APICall<ArrayList<DBRequest>>(main){
            @Override
            public Call<ArrayList<DBRequest>> apiFun() {
                return main.getService().getEntityList(main.getDebugToken(),"ESS2Config", Values.GetAllModeActual,2);
            }
            @Override
            public void onSucess(ArrayList<DBRequest> oo) {
                configs.clear();
                for(DBRequest request : oo) {
                    try {
                        configs.add((ESS2Config) request.get(main.gson));
                        } catch (UniException e) {
                            System.out.println("Ошибка конвертирования json: "+e.toString());
                            }
                        }
                for(ESS2Config vv : configs)
                    Config.add(vv.getTitle());
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
        Set = new javax.swing.JButton();
        Add = new javax.swing.JButton();
        Remove = new javax.swing.JButton();
        Comment = new javax.swing.JTextField();
        Title = new javax.swing.JTextField();
        User = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

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
        CreateDate.setBounds(10, 65, 130, 25);

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
        Add.setBounds(360, 40, 30, 30);

        Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/remove.png"))); // NOI18N
        Remove.setBorderPainted(false);
        Remove.setContentAreaFilled(false);
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });
        add(Remove);
        Remove.setBounds(360, 80, 30, 30);

        Comment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CommentKeyPressed(evt);
            }
        });
        add(Comment);
        Comment.setBounds(10, 95, 260, 25);

        Title.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TitleKeyPressed(evt);
            }
        });
        add(Title);
        Title.setBounds(10, 35, 260, 25);

        User.setEnabled(false);
        add(User);
        User.setBounds(150, 65, 190, 25);

        jLabel2.setText("Коммент...");
        add(jLabel2);
        jLabel2.setBounds(280, 100, 70, 16);

        jLabel3.setText("Название");
        add(jLabel3);
        jLabel3.setBounds(280, 40, 70, 16);
    }// </editor-fold>//GEN-END:initComponents

    private void refresh(){
        Title.setText("");
        CreateDate.setText("");
        User.setText("");
        if (configs.size()==0)
            return;
        if (selectedIdx<0)
            selectedIdx=0;
        if (selectedIdx >=configs.size())
            selectedIdx=configs.size()-1;
        Config.select(selectedIdx);
        selected = configs.get(selectedIdx);
        Title.setText(selected.getTitle());
        Comment.setText(selected.getComment());
        CreateDate.setText(selected.getCreateDate().toString2());
        User.setText(selected.getAuthor().getTitle());
        }

    private void ConfigItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ConfigItemStateChanged
        selectedIdx = Config.getSelectedIndex();
        refresh();
    }//GEN-LAST:event_ConfigItemStateChanged

    private void SetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetActionPerformed
        if (configs.size()==0)
            return;
        new OK(200,200,"Развернуть конфигурацию уставок",new I_Button() {
            @Override
            public void onPush() {
                new APICall<ErrorList>(main) {
                    @Override
                    public Call<ErrorList> apiFun() {
                        return main.service2.deploySettingsConfig(main.getDebugToken(), selected.getOid());
                        }
                    @Override
                    public void onSucess(ErrorList ss) {
                        System.out.println(ss);
                        }
                    };
                }
            });
    }//GEN-LAST:event_SetActionPerformed

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        if (configs.size()==0)
            return;
        new OK(200,200,"Удалить конфигурацию уставок",new I_Button() {
            @Override
            public void onPush() {
                new APICall<JEmpty>(main) {
                    @Override
                    public Call<JEmpty> apiFun() {
                        return main.service2.removeSettingsConfig(main.getDebugToken(), selected.getOid());
                        }
                    @Override
                    public void onSucess(JEmpty ss) {
                        selectedIdx--;
                        refreshList();
                        }
                };
            }
        });
    }//GEN-LAST:event_RemoveActionPerformed

    private void procPressedString(KeyEvent evt){
        new APICall<JEmpty>(main) {
            @Override
            public Call<JEmpty> apiFun() {
                return main.getService().updateEntity(main.getDebugToken(),new DBRequest(selected,main.gson));
                }
            @Override
            public void onSucess(JEmpty oo) {
                refreshList();
                if (evt!=null)
                    main.viewUpdate(evt,true);
                }
            };
        }

    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        new OK(200,200,"Добавить текущую конфигурацию уставок",new I_Button() {
            @Override
            public void onPush() {
                Comment.setText("...");
                Title.setText("Новая конфигурация уставок");
                new APICall<ErrorList>(main) {
                    @Override
                    public Call<ErrorList> apiFun() {
                        return main.service2.addSettingsConfig(main.getDebugToken(), Comment.getText(), Title.getText());
                        }
                    @Override
                    public void onSucess(ErrorList ss) {
                        System.out.println(ss);
                        selectedIdx = configs.size();
                        refreshList();
                        }
                    };
               }
            });
    }//GEN-LAST:event_AddActionPerformed

    private void CommentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CommentKeyPressed
        if(evt.getKeyCode()!=10)
            return;
        if (configs.size()==0)
            return;
        selected.setComment(Comment.getText());
        procPressedString(evt);
    }//GEN-LAST:event_CommentKeyPressed

    private void TitleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TitleKeyPressed
        if(evt.getKeyCode()!=10)
            return;
        if (configs.size()==0)
            return;
        selected.setTitle(Title.getText());
        procPressedString(evt);
    }//GEN-LAST:event_TitleKeyPressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JTextField Comment;
    private java.awt.Choice Config;
    private javax.swing.JTextField CreateDate;
    private javax.swing.JButton Remove;
    private javax.swing.JButton Set;
    private javax.swing.JTextField Title;
    private javax.swing.JTextField User;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
