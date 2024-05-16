/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;


import romanow.abc.core.DBRequest;
import romanow.abc.core.ErrorList;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.ConstValue;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.baseentityes.JBoolean;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.StreamDataValue;
import romanow.abc.core.entity.metadata.StreamRegisterData;
import romanow.abc.core.entity.metadata.StreamRegisterGroup;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subjectarea.ArchStreamDataSet;
import romanow.abc.core.entity.subjectarea.DataSet;
import romanow.abc.core.entity.subjectarea.ESSNode;
import romanow.abc.core.entity.subjectarea.MetaDataRegister;
import romanow.abc.core.utils.OwnDateTime;
import retrofit2.Call;
import romanow.abc.core.utils.Pair;

/**
 *
 * @author romanow
 */
public class ESSStreamDataSelectorBig extends javax.swing.JPanel {
    private boolean withError=true;
    private OwnDateTime time1=new OwnDateTime(false);
    private OwnDateTime time2=new OwnDateTime(false);
    private ESS2Architecture architecture;
    private StreamRegisterData data = null;
    private ESSClient main;
    private I_Value<StreamRegisterData> back;
    private ArrayList<ConstValue> modeList;
    private ConstValue selectedMode;
    private HashMap<Integer,ConstValue> compressModes;
    /**
     * Creates new form ESSStreamDataSelector
     */
    public ESSStreamDataSelectorBig(){
        initComponents();
        }
    public void init(ESSClient base0, ESS2Architecture arch0, I_Value<StreamRegisterData> back0) {
        back = back0;
        main = base0;
        architecture = arch0;
        modeList = Values.constMap().getGroupList("DataStream");
        StreamType.removeAll();
        for(ConstValue cc : modeList)
            StreamType.addItem(cc.title());
        StreamRegister.removeAll();
        compressModes = Values.constMap().getGroupMapByValue("CompressMode");
        long ct = System.currentTimeMillis()-3*24*60*60*1000;
        TIME2.setText(new OwnDateTime().toString2());
        TIME1.setText(new OwnDateTime(ct).toString2());
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
        TIME1 = new javax.swing.JTextField();
        TIME2 = new javax.swing.JTextField();
        ShowGraph = new javax.swing.JButton();
        SetTIME2 = new javax.swing.JButton();
        SetTIME1 = new javax.swing.JButton();
        StreamType = new javax.swing.JComboBox<>();
        StreamRegister = new javax.swing.JComboBox<>();

        jLabel1.setText("jLabel1");

        setPreferredSize(new java.awt.Dimension(550, 180));
        setRequestFocusEnabled(false);
        setLayout(null);

        TIME1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TIME1.setEnabled(false);
        TIME1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TIME1MouseClicked(evt);
            }
        });
        add(TIME1);
        TIME1.setBounds(60, 90, 180, 30);

        TIME2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TIME2.setEnabled(false);
        TIME2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TIME2MouseClicked(evt);
            }
        });
        add(TIME2);
        TIME2.setBounds(310, 90, 180, 30);

        ShowGraph.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/statistic.png"))); // NOI18N
        ShowGraph.setBorderPainted(false);
        ShowGraph.setContentAreaFilled(false);
        ShowGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowGraphActionPerformed(evt);
            }
        });
        add(ShowGraph);
        ShowGraph.setBounds(450, 40, 40, 40);

        SetTIME2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/calendarBig.png"))); // NOI18N
        SetTIME2.setBorderPainted(false);
        SetTIME2.setContentAreaFilled(false);
        SetTIME2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetTIME2ActionPerformed(evt);
            }
        });
        add(SetTIME2);
        SetTIME2.setBounds(250, 80, 50, 50);

        SetTIME1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/calendarBig.png"))); // NOI18N
        SetTIME1.setBorderPainted(false);
        SetTIME1.setContentAreaFilled(false);
        SetTIME1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SetTIME1ActionPerformed(evt);
            }
        });
        add(SetTIME1);
        SetTIME1.setBounds(0, 80, 54, 50);

        StreamType.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        StreamType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                StreamTypeItemStateChanged(evt);
            }
        });
        add(StreamType);
        StreamType.setBounds(10, 10, 140, 30);

        StreamRegister.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add(StreamRegister);
        StreamRegister.setBounds(10, 50, 430, 30);
    }// </editor-fold>//GEN-END:initComponents


    private void ShowGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowGraphActionPerformed
        showData();
    }//GEN-LAST:event_ShowGraphActionPerformed
    private void refreshDataSet(){
        /*
        StreamDataValue value = data.getValueList().get(DataSetList.getSelectedIndex());
        new APICall<DBRequest>(main){
            @Override
            public Call<DBRequest> apiFun() {
                return main.getService().getEntity(main.getDebugToken(),"ArchStreamDataSet",value.setOid,0);
                }
            @Override
            public void onSucess(DBRequest oo) {
                try {
                    ArchStreamDataSet set  = (ArchStreamDataSet) oo.get(main.gson);
                    Meta2Register register = data.getRegister();
                    Pair<String,Integer> value = set.getValue(data.getStreamDataOffset(),register.savedDoubleSize() ? 4 : 2);
                    if (value.o1!=null){
                        System.out.println(value.o1);
                        }
                    int v1 = set.getPackedByteSize();
                    int v2 = set.getSourceByteSize();
                    } catch (UniException e) {
                        System.out.println(e.toString());
                        }
                }
            };
         */
        }

    private void setTime1(){
        new CalendarView("Начало периода", new I_CalendarTime() {
            @Override
            public void onSelect(OwnDateTime time) {
                time1 = time;
                TIME1.setText(time1.toString2());
                }
            });
        }

    private void setTime2(){
        new CalendarView("Начало периода", new I_CalendarTime() {
            @Override
            public void onSelect(OwnDateTime time) {
                time2 = time;
                TIME2.setText(time2.toString2());
            }
        });
    }

    private void TIME1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TIME1MouseClicked
        new CalendarView("Окончание периода", new I_CalendarTime() {
            @Override
            public void onSelect(OwnDateTime time) {
                time2 = time;
                TIME2.setText(time2.toString2());
            }
        });
    }//GEN-LAST:event_TIME1MouseClicked

    private void TIME2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TIME2MouseClicked
        if (evt.getClickCount()<2) return;
        if (evt.getButton()==1){
            setTime2();
        }
    }//GEN-LAST:event_TIME2MouseClicked

    private String showData2(){
        String res = new APICall3<Pair<ErrorList,ArrayList<StreamDataValue>>>(){
            @Override
            public Call<Pair<ErrorList,ArrayList<StreamDataValue>>> apiFun() {
                return main.service2.getStreamData2(main.getDebugToken(),selectedMode.value(),StreamRegister.getSelectedIndex(),time1.timeInMS(),time2.timeInMS());
                }
            @Override
            public void onSucess(Pair<ErrorList,ArrayList<StreamDataValue>> ans) {
                ErrorList errors = ans.o1;
                if (!errors.valid())
                    System.out.print(errors);
                data.setValueList(ans.o2);
                back.onEnter(data);
                }
            }.call(main);
        return res;
        }
    private String showData1(){
        String res = new APICall3<ArrayList<StreamDataValue>>(){
            @Override
            public Call<ArrayList<StreamDataValue>> apiFun() {
                return main.service2.getStreamData(main.getDebugToken(),selectedMode.value(),StreamRegister.getSelectedIndex(),time1.timeInMS(),time2.timeInMS());
                }
            @Override
            public void onSucess(ArrayList<StreamDataValue> oo) {
                data.setValueList(oo);
                back.onEnter(data);
                }
            }.call(main);
        return  res;
        }

        private void showData(){
        if (StreamRegister.getItemCount()==0)
            return;
        final  int logNum = StreamRegister.getSelectedIndex();
        data = architecture.getStreamRegisterByLogNum(selectedMode.value(),logNum);
        if (data==null)
            return;
        String ss = showData2();
        if (ss !=null){
            if (ss.indexOf("404")!=-1) {
                ss = showData1();
                if (ss!=null)
                    System.out.println(ss);
                }
            else
                System.out.println(ss);
            }
        }

    private void SetTIME2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetTIME2ActionPerformed
        setTime2();
    }//GEN-LAST:event_SetTIME2ActionPerformed

    private void SetTIME1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SetTIME1ActionPerformed
        setTime1();
    }//GEN-LAST:event_SetTIME1ActionPerformed

    private void StreamTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_StreamTypeItemStateChanged
        int idx = StreamType.getSelectedIndex();
        selectedMode = modeList.get(idx);
        StreamRegister.removeAll();
        if (selectedMode.value()==Values.DataStreamNone){
            return;
        }
        ArrayList<StreamRegisterGroup> list = architecture.getStreamRegisterList(selectedMode.value());
        for(StreamRegisterGroup group : list)
        for(StreamRegisterData registerData : group.getList()){
            registerData.setUnitIdx(group.getLogUnit());
            StreamRegister.addItem(registerData.getTitle());
            }
        refreshDataSet();
    }//GEN-LAST:event_StreamTypeItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SetTIME1;
    private javax.swing.JButton SetTIME2;
    private javax.swing.JButton ShowGraph;
    private javax.swing.JComboBox<String> StreamRegister;
    private javax.swing.JComboBox<String> StreamType;
    private javax.swing.JTextField TIME1;
    private javax.swing.JTextField TIME2;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
