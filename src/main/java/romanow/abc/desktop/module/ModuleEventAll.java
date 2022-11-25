package romanow.abc.desktop.module;

import com.google.gson.Gson;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.DBRequest;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2GUIForm;
import romanow.abc.core.entity.subjectarea.ArchESSEvent;
import romanow.abc.desktop.APICall2;
import romanow.abc.desktop.Client;
import romanow.abc.desktop.MainBaseFrame;
import romanow.abc.core.utils.OwnDateTime;
import retrofit2.Call;
import romanow.abc.desktop.view2.FormContext2;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class ModuleEventAll extends Module {
    protected ArrayList<ArchESSEvent> events = new ArrayList<>();
    protected ArrayList<ArchESSEvent> selected = new ArrayList<>();
    protected JTable table;
    private ListSelectionListener listener;
    public ModuleEventAll(){}
    @Override
    public void init(MainBaseFrame client, JPanel panel, RestAPIBase service, RestAPIESS2 service2, String token, Meta2GUIForm form, FormContext2 formContext) {
        super.init(client, panel, service, service2,token, form, formContext);
        repaintView();
        }
    private String[] columnsHeader = new String[] {"дата","время", "тип","событие"};
    private int sizes[] = {100,80,120,700};
    private void showTable(){
            Vector<Vector<String>> data = new Vector<Vector<String>>();
            Vector<String> header = new Vector<String>();
            for(String ss : columnsHeader)
                header.add(ss);
            for(ArchESSEvent essEvent : selected){
                Vector<String> row = new Vector<String>();
                OwnDateTime dd = essEvent.getArrivalTime();
                row.add(dd.dateToString());
                row.add(dd.timeFullToString());
                row.add(Values.title("EventType",essEvent.getType()));
                row.add(essEvent.getTitle());
                data.add(row);
                }
            if (table!=null){
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(selected.size());
                model.setDataVector(data,header);
                }
            else{
                table = new JTable(data, header);
                table.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
                table.setRowHeight(context.y(20));
                table.setSelectionForeground(Color.blue);
                table.setSelectionBackground(Color.yellow);
                table.setVisible(true);
                JScrollPane scroll = new JScrollPane(table);
                scroll.setBounds(
                        context.x(10),
                        context.y(100),
                        context.x(Client.PanelW-20),
                        context.y(Client.PanelH-150));
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                table.setAutoscrolls(true);
                table.setShowGrid(true);
                listener = new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting())
                            return;
                        int[] selectedRows = table.getSelectedRows();
                        for(int i = 0; i < selectedRows.length; i++) {
                            System.out.println(selected.get(selectedRows[i]));
                            }
                        }
                    };
                table.getSelectionModel().addListSelectionListener(listener);
                panel.add(scroll);
                }
            for(int i=0;i<sizes.length;i++)
                table.getColumnModel().getColumn(i).setPreferredWidth(sizes[i]);
            //panel.repaint();
        }

    @Override
    public void repaintValues() {
        super.repaintValues();
        try {
            ArrayList<DBRequest> res = new APICall2<ArrayList<DBRequest>>(){
                @Override
                public Call<ArrayList<DBRequest>> apiFun() {
                    return service.getEntityListLast(token,"ArchESSEvent",100,0);
                    }
                }.call(client);
            System.out.println("Прочитано событий "+res.size());
            events.clear();
            Gson gson = new Gson();
            for(DBRequest request : res)
                events.add((ArchESSEvent) request.get(gson));
            selected.clear();
            for(int idx=events.size()-1;idx>=0;idx--){
                ArchESSEvent essEvent = events.get(idx);
                if (typeFilter(essEvent.getType()))
                    selected.add(essEvent);
                }
            System.out.println("Выбрано событий "+selected.size());
            showTable();
            } catch (UniException e) {
                System.out.println(e.toString());
                }
        }
    public boolean typeFilter(int type) {
        return true;
        }
}
