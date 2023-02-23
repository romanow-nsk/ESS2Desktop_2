/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;


import romanow.abc.core.entity.metadata.StreamRegisterData;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.core.entity.subjectarea.DataSet;

/**
 *
 * @author romanow
 */
public class ESSStreamDataView extends javax.swing.JFrame {
    private I_Value<StreamRegisterData> back;
    /**
     * Creates new form ESSStreamDataView
     */
    public ESSStreamDataView(ESSClient client, I_Value<StreamRegisterData> back0) {
        initComponents();
        back = back0;
        setBounds(200,200,500,270);
        ESSStreamDataSelector selector = new ESSStreamDataSelector();
        selector.init(client, client.deployed, new I_Value<StreamRegisterData>() {
            @Override
            public void onEnter(StreamRegisterData value) {
                back.onEnter(value);
                dispose();
                }
            });
        setTitle("Потоковые данные");
        selector.setBounds(0,0,500,220);
        add(selector);
        setVisible(true);
        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
