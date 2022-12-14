/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;


import romanow.abc.core.entity.metadata.StreamDataValue;
import romanow.abc.core.entity.metadata.StreamRegisterData;
import romanow.abc.core.entity.subjectarea.DataSet;
import romanow.abc.core.utils.OwnDateTime;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import romanow.abc.desktop.view2.FormContext2;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author romanow
 */
public class TrendPanel extends javax.swing.JPanel implements I_Trend{
    private ArrayList<StreamRegisterData> data = new ArrayList<>();
    private ArrayList<StreamRegisterData> view = new ArrayList<>();
    private boolean viewReady=false;
    private JPanel chart=null;
    private I_Success back=null;
    TimeSeriesCollection dataset = new TimeSeriesCollection();
    public void setBack(I_Success bb){
        back = bb;
        Add.setVisible(back!=null);
        }
    ScatterChart<Number, Number> lineChart;
    public TrendPanel(){}
    public void init(FormContext2 context, Runnable after) {
        initComponents();
        StatList.add("...");
        setVisible(true);
        createJChartPanel(context);
        revalidate();
        repaint();
        after.run();
        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        StatList = new java.awt.Choice();
        Remove = new javax.swing.JButton();
        Clear = new javax.swing.JButton();
        Fore = new javax.swing.JButton();
        Back = new javax.swing.JButton();
        Add = new javax.swing.JButton();
        StatListView = new java.awt.Choice();

        setLayout(null);

        StatList.setBackground(new java.awt.Color(204, 204, 204));
        add(StatList);
        StatList.setBounds(80, 5, 300, 30);

        Remove.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/problem.png"))); // NOI18N
        Remove.setBorderPainted(false);
        Remove.setContentAreaFilled(false);
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });
        add(Remove);
        Remove.setBounds(40, 0, 30, 30);

        Clear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/clear.png"))); // NOI18N
        Clear.setBorderPainted(false);
        Clear.setContentAreaFilled(false);
        Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearActionPerformed(evt);
            }
        });
        add(Clear);
        Clear.setBounds(10, 0, 30, 30);

        Fore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/right.PNG"))); // NOI18N
        Fore.setBorderPainted(false);
        Fore.setContentAreaFilled(false);
        Fore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForeActionPerformed(evt);
            }
        });
        add(Fore);
        Fore.setBounds(420, 0, 40, 30);

        Back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/left.png"))); // NOI18N
        Back.setBorderPainted(false);
        Back.setContentAreaFilled(false);
        Back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackActionPerformed(evt);
            }
        });
        add(Back);
        Back.setBounds(380, 0, 40, 30);

        Add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/add.png"))); // NOI18N
        Add.setBorderPainted(false);
        Add.setContentAreaFilled(false);
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });
        add(Add);
        Add.setBounds(760, 0, 40, 30);

        StatListView.setBackground(new java.awt.Color(204, 204, 204));
        add(StatListView);
        StatListView.setBounds(460, 5, 300, 30);
    }// </editor-fold>//GEN-END:initComponents

    //--------------------------------------------------------------------------
    private String put6(double dd){
        String ss = ""+dd;
        if (ss.length()<4)
            return ss;
        return ss.substring(0,4);
        }
    //==============================================================================
    public void createJChartPanel(FormContext2 context){
        JFreeChart chart = createChart(dataset);
        chart.setPadding(new RectangleInsets(2, 2, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        if (context==null){
            panel.setPreferredSize(new Dimension(Client.PanelW - 75, Client.PanelH - 100));
            panel.setBounds(10, 35, Client.PanelW - 75, Client.PanelH - 100);
            }
        else {
            panel.setPreferredSize(new Dimension(context.x(Client.PanelW - 75), context.y(Client.PanelH - 130)));
            panel.setBounds(context.x(10), context.y(35), context.x(Client.PanelW - 75), context.y(Client.PanelH - 130));
            }
        add(panel);
        }
    private void refreshJChartPanel() {         // ???? ???????? ????????????????
        dataset.removeAllSeries();
        for(StreamRegisterData set : view){
            TimeSeries s1 = new TimeSeries(set.getTitle());
            for(StreamDataValue dataValue : set.getValueList()){
                OwnDateTime tt = new OwnDateTime(dataValue.timeStamp);
                Second sec = new Second(tt.second(),tt.minute(),tt.hour(),tt.day(),tt.month(),tt.year());
                s1.add(sec,dataValue.value);
                }
            dataset.addSeries(s1);
            }
        revalidate();
        repaint();
        }

    private JFreeChart createChart(XYDataset dataset){
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "",                  // title
            "",           // x-axis label
            "",          // y-axis label
             dataset,                 // data
            true,             // create legend
            true,            // generate tooltips
            false               // generate URLs
            );
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint    (Color.lightGray);
        plot.setDomainGridlinePaint(Color.white    );
        plot.setRangeGridlinePaint (Color.white    );
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setDefaultShapesFilled(true);
            renderer.setDefaultShapesVisible(true);
            //renderer.setBaseShapesVisible   (true);
            //renderer.setBaseShapesFilled    (true);
            renderer.setDrawSeriesLineAsPath(true);
            }
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM:k.m.s"));
        return chart;
        }
    //==========================================================================================
    private OwnDateTime minTime=new OwnDateTime();
    public void paintOne(DataSet stat){
        XYChart.Series series = new XYChart.Series();
        final double data[] = stat.convertY();
        final int xx[] = stat.convertXX(minTime);
        series.setName(stat.getTitle());
        ObservableList dd = series.getData();
        for(int j=0;j<data.length;j++){
            XYChart.Data<Double, Double> item = new XYChart.Data<Double, Double>((double)xx[j], data[j]);
            dd.add(item);
        }
        lineChart.getData().add(series);
        //----------- Node ???????????????????? ???????????? ?????????? ???????????????????? ?????????? !!!!!!!!!!!!
        for(int ii=0;ii<dd.size();ii++){
            XYChart.Data<Double, Double> item = (XYChart.Data<Double, Double>)dd.get(ii);
            final Node node = item.getNode();
            final double vv = data[ii];
            final String x0 = new OwnDateTime(minTime.timeInMS()+xx[ii]*1000).toString2();
            //------------------ ?????????????????? ???????? ----------------------------
            String ss = put6(vv);
            Tooltip.install(node, new Tooltip(vv +", "+x0));
            node.setOnMouseEntered(new EventHandler<Event>() {
                @Override
                public void handle(javafx.event.Event event) {
                    node.getStyleClass().add("onHover");
                }
            });
            //???????????????? ???????????? ?????????? ?????????????????? ????????
            node.setOnMouseExited(new EventHandler<javafx.event.Event>() {
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
        refreshJChartPanel();
        }

    public void addTrendView(final StreamRegisterData histo){
        view.add(histo);
        createStatList();
        refreshJChartPanel();
    }

    @Override
    public void close() {
        removeAll();
        }

    public void createStatList(){
        StatList.removeAll();
        StatList.add("...");
        for(int i=data.size()-1;i>=0;i--)
            StatList.add(data.get(i).getTitle());
        StatListView.removeAll();
        StatListView.add("...");
        for(int i=view.size()-1;i>=0;i--)
            StatListView.add(view.get(i).getTitle());
    }



    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        int idx = StatList.getSelectedIndex();
        if (idx==0)
        return;
        final int idx2 = StatList.getItemCount() - idx - 1;
        data.remove(idx2);
        createStatList();
        refreshJChartPanel();
    }//GEN-LAST:event_RemoveActionPerformed

    public void clearAll(){
        StatList.removeAll();
        StatList.add("...");
        final int sz = view.size();
        StatListView.removeAll();
        StatListView.add("...");
        }
    public void repaint(){
        if (!viewReady)
            return;
        clearAll();
        createStatList();
        }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        clearAll();
        data.clear();
        view.clear();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ClearActionPerformed(java.awt.event.ActionEvent evt) {                                         
        clearAll();
        data.clear();
        view.clear();
        refreshJChartPanel();
    }                                        
    private void AddActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if (back!=null)
            back.onSuccess();
    }

    private void ForeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForeActionPerformed
        final int idx = StatList.getSelectedIndex();
        if (idx==0)
        return;
        final int idx2 = StatList.getItemCount() - idx - 1;
        final StreamRegisterData zz = data.remove(idx2);
        view.add(zz);
        createStatList();
        refreshJChartPanel();
    }//GEN-LAST:event_ForeActionPerformed

    private void BackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackActionPerformed
        final int idx = StatListView.getSelectedIndex();
        if (idx==0)
        return;
        final int idx2 = StatListView.getItemCount() - idx - 1;
        final StreamRegisterData zz = view.remove(idx2);
        data.add(zz);
        createStatList();
        refreshJChartPanel();
    }//GEN-LAST:event_BackActionPerformed


    @Override
    public void toFront() { }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JButton Back;
    private javax.swing.JButton Clear;
    private javax.swing.JButton Fore;
    private javax.swing.JButton Remove;
    private java.awt.Choice StatList;
    private java.awt.Choice StatListView;
    // End of variables declaration//GEN-END:variables
}
