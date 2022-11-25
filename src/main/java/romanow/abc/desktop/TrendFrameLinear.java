/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import java.awt.Color;
import java.util.ArrayList;

import romanow.abc.core.entity.metadata.StreamDataValue;
import romanow.abc.core.entity.metadata.StreamRegisterData;
import romanow.abc.core.entity.subjectarea.DataSet;
import romanow.abc.core.utils.OwnDateTime;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;

/**
 *
 * @author romanow
 */
public class TrendFrameLinear extends javax.swing.JFrame  implements I_Trend{
    private ArrayList<StreamRegisterData> data = new ArrayList<>();
    private ArrayList<StreamRegisterData> view = new ArrayList<>();
    private OwnDateTime minTime;
    private Color backColor = new Color(240,240,240);
    private Color foreColor = Color.BLACK;
    ScatterChart<Number, Number> lineChart;
    private void runFX(final Runnable code){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                code.run();
                }
            });
        }
    public TrendFrameLinear() {
        initComponents();
        setVisible(true);
        final JFXPanel fxPanel = new JFXPanel();
        add(fxPanel);
        setBounds(100, 100, 1150, 700);
        setTitle("Тренд");
        fxPanel.setBounds(10, 50, 1100, 600);
        setVisible(true);
        StatList.add("...");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
                }
            });
        }

    private void initFX(JFXPanel fxPanel){
        /*
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Интервал");
        lineChart =  new ScatterChart<String, Number>(xAxis, yAxis);
        lineChart.setTitle("Распределение");
        Scene scene = new Scene(lineChart, 1100, 600);
        fxPanel.setSEne(scene);
        */
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Время");
        lineChart =  new ScatterChart<Number, Number>(xAxis, yAxis);
        lineChart.setTitle("Тренды");
        Scene scene = new Scene(lineChart, 1100, 600);
        fxPanel.setScene(scene);
        //----------- Пока подписи категорий так -------------------------------
        /*
        XYChart.Series series = new XYChart.Series();
        series.setName("Значение");
        ObservableList dd = series.getData();
        for(int j=0;j<valX.length;j++){   // Подпись значений факторов j-ой ячейки
            String ss = ""+(int)valX[j];
            series.getData().add(new XYChart.Data<String, Double>(ss, 0.0));
            }
        lineChart.getData().add(series);
        */
        }
    //--------------------------------------------------------------------------
    private String put6(double dd){ 
        String ss = ""+dd;
        if (ss.length()<4)
            return ss;
        return ss.substring(0,4);
        }    
    //--------------------------------------------------------------------------
    public void paintOne(StreamRegisterData stat){
        XYChart.Series series = new XYChart.Series();
        series.setName(stat.getRegister().getTitle());
        ObservableList dd = series.getData();
        for(StreamDataValue xx : stat.getValueList()){
            XYChart.Data<Double, Double> item = new XYChart.Data<Double, Double>((double)xx.timeStamp, xx.value);
            dd.add(item);
            }
        lineChart.getData().add(series);
        //----------- Node появляются только после добавления серии !!!!!!!!!!!!
        for(int ii=0;ii<dd.size();ii++){
            XYChart.Data<Double, Double> item = (XYChart.Data<Double, Double>)dd.get(ii);
            final Node node = item.getNode();
            final StreamDataValue gg = stat.getValueList().get(ii);
            final String x0 = new OwnDateTime(gg.timeStamp).toString2();
            //------------------ Наведение мыши ----------------------------
            String ss = put6(gg.value);
      		Tooltip.install(node, new Tooltip(gg.value +" "+x0));
            node.setOnMouseEntered(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    node.getStyleClass().add("onHover");
                    }
                });
            //Удаление класса после покидание мыши
            node.setOnMouseExited(new EventHandler<Event>() {
         	@Override
          	public void handle(Event event) {
                node.getStyleClass().remove("onHover");
                }});
            //--------------------------------------------------------------
            }
        }

    public void addTrend(final StreamRegisterData histo){
        data.add(histo);
        createStatList(); 
        }

    public void addTrendView(final StreamRegisterData histo){
        view.add(histo);
        createStatList(); 
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                paintOne(histo);
                }
            });
        }

    @Override
    public void close() {
        dispose();
    }

    public void createStatList(){
        StatList.removeAll();
        StatList.add("...");
        for(int i=data.size()-1;i>=0;i--)
            StatList.add(data.get(i).getRegister().getTitle());
        StatListView.removeAll();
        StatListView.add("...");
        for(int i=view.size()-1;i>=0;i--)
            StatListView.add(view.get(i).getRegister().getTitle());
        if (view.size()==0)
            return;
        long minStamp  = 0;
        for(StreamRegisterData dd : view) {
            long stamp  = dd.getMinTimeStamp();
            if (stamp==0)
                continue;
            if (minStamp==0 || stamp < minStamp)
                minStamp = stamp;
            }
        minTime = new OwnDateTime(minStamp);
        }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StatList = new java.awt.Choice();
        Remove = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Fore = new javax.swing.JButton();
        Back = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        StatListView = new java.awt.Choice();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        StatList.setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().add(StatList);
        StatList.setBounds(80, 10, 460, 30);

        Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/remove.png"))); // NOI18N
        Remove.setBorderPainted(false);
        Remove.setContentAreaFilled(false);
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });
        getContentPane().add(Remove);
        Remove.setBounds(40, 10, 30, 30);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/clear.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(10, 10, 30, 30);

        Fore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/right.PNG"))); // NOI18N
        Fore.setBorderPainted(false);
        Fore.setContentAreaFilled(false);
        Fore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForeActionPerformed(evt);
            }
        });
        getContentPane().add(Fore);
        Fore.setBounds(590, 10, 30, 30);

        Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/left.png"))); // NOI18N
        Back.setBorderPainted(false);
        Back.setContentAreaFilled(false);
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });
        getContentPane().add(Back);
        Back.setBounds(550, 10, 30, 30);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/refresh.png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(1100, 10, 40, 30);

        StatListView.setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().add(StatListView);
        StatListView.setBounds(630, 10, 460, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.hide();
    }//GEN-LAST:event_formWindowClosing

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        int idx = StatList.getSelectedIndex();
        if (idx==0)
            return;
        final int idx2 = StatList.getItemCount() - idx - 1;
        data.remove(idx2);
        createStatList();
    }//GEN-LAST:event_RemoveActionPerformed

    public void clearAll(){
        StatList.removeAll();
        StatList.add("...");
        final int sz = view.size();
        StatListView.removeAll();
        StatListView.add("...");
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<sz;i++)
                lineChart.getData().remove(0); 
                }
            });
        }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        clearAll();
        data.clear();
        view.clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ForeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForeActionPerformed
        final int idx = StatList.getSelectedIndex();
        if (idx==0)
            return;
        final int idx2 = StatList.getItemCount() - idx - 1;
        final StreamRegisterData zz = data.remove(idx2);
        view.add(zz);
        createStatList();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                paintOne(zz); 
                }
            });

    }//GEN-LAST:event_ForeActionPerformed

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed
       final int idx = StatListView.getSelectedIndex();
        if (idx==0)
            return;
        final int idx2 = StatListView.getItemCount() - idx - 1;
        final StreamRegisterData zz = view.remove(idx2);
        data.add(zz);
        createStatList();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lineChart.getData().remove(idx2); 
                }
            });
    }//GEN-LAST:event_BackActionPerformed
    
    public void repaint(){
        clearAll();
        createStatList(); 
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<view.size();i++){
                    StreamRegisterData zz = view.get(i);
                    paintOne(zz);
                    }
                }
            });
        }
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        repaint();
    }//GEN-LAST:event_jButton5ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TrendFrameLinear().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back;
    private javax.swing.JButton Fore;
    private javax.swing.JButton Remove;
    private java.awt.Choice StatList;
    private java.awt.Choice StatListView;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    // End of variables declaration//GEN-END:variables
}
