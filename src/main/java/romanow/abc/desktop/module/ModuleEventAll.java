package romanow.abc.desktop.module;

import com.google.gson.Gson;
import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.DBRequest;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.metadata.Meta2GUIForm;
import romanow.abc.core.entity.subjectarea.ArchESSEvent;
import romanow.abc.core.mongo.*;
import romanow.abc.desktop.*;
import romanow.abc.core.utils.OwnDateTime;
import retrofit2.Call;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class ModuleEventAll extends Module {
    public final boolean endTime;
    protected ArrayList<ArchESSEvent> events = new ArrayList<>();
    protected ArrayList<ArchESSEvent> selected = new ArrayList<>();
    protected JTable table;
    private ListSelectionListener listener;
    public ModuleEventAll(boolean endTime0){
        endTime = endTime0;
        }
    public ModuleEventAll(){
        endTime = false;
        }
    private int types[] = {};
    public int[] eventTypes(){ return types; }
    private long lastDayClock = 0;
    @Override
    public void init(MainBaseFrame client, JPanel panel, RestAPIBase service, RestAPIESS2 service2, String token, Meta2GUIForm form, FormContext2 formContext) {
        super.init(client, panel, service, service2,token, form, formContext);
        OwnDateTime beginDate = new OwnDateTime();
        beginDate.setOnlyDate();
        lastDayClock = beginDate.timeInMS();
        JButton bb = new JButton();
        bb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/calendar.png")));
        bb.setBounds(
                context.x(form.getModuleDX()-80),
                context.y((int)(form.getModuleDY()*0.86)),
                context.dx((int)(Values.MenuButtonH*1.8)),
                context.dy((int)(Values.MenuButtonH*1.28)));
        bb.setVisible(true);
        bb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CalendarView("События", new I_CalendarTime() {
                    @Override
                    public void onSelect(OwnDateTime time) {
                        lastDayClock = time.timeInMS();
                        repaintView();
                        }
                    });
                }
            });
        panel.add(bb);
        repaintView();
        }
    private String[] columnsHeader = new String[] {"дата","время", "тип","событие"};
    private String[] columnsHeader2 = new String[] {"дата","начало", "оконч.","тип","событие"};
    private int sizes[] = {100,80,120,700};
    private int sizes2[] = {100,80,80,120,700};
    private void showTable(){
        Vector<Vector<String>> data = new Vector<Vector<String>>();
        Vector<String> header = new Vector<String>();
        for(String ss : endTime ? columnsHeader2 : columnsHeader)
            header.add(ss);
        for(ArchESSEvent essEvent : selected){
            Vector<String> row = new Vector<String>();
            OwnDateTime dd = essEvent.getArrivalTime();
            row.add(dd.dateToString());
            row.add(dd.timeFullToString());
            if (endTime)
                row.add(essEvent.getEndTime().timeFullToString());
            row.add(Values.title("EventType",essEvent.getType()));
            row.add(essEvent.getTitle());
            data.add(row);
            }
            /*
            int minRows=50;
            while (data.size()<minRows){
                    Vector<String> row = new Vector<String>();
                    row.add("");
                    row.add("");
                    row.add("");
                    row.add("");
                    if (endTime)
                        row.add("");
                    data.add(row);
                    }
             */
            if (table!=null){
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(data.size());
                model.setDataVector(data,header);
                }
            else{
                table = new JTable(data, header);
                table.setFont(new Font(Values.FontName, Font.PLAIN, context.dy(12)));
                table.setRowHeight(context.dy(20));
                table.setSelectionForeground(Color.blue);
                table.setSelectionBackground(Color.yellow);
                table.setVisible(true);
                JScrollPane scroll = new JScrollPane(table);
                scroll.setBounds(
                        context.x(form.getModuleX0()),
                        context.y(form.getModuleY0()),
                        context.dx(form.getModuleDX()),
                        context.dy(form.getModuleDY()-100));
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
            int ss[] = endTime ? sizes2 : sizes;
            for(int i=0;i<ss.length;i++)
                table.getColumnModel().getColumn(i).setPreferredWidth(ss[i]);
            panel.repaint();
        }

    @Override
    public void repaintValues() {
        super.repaintValues();
            DBQueryList query =  new DBQueryList().
                    add(new DBQueryLong(I_DBQuery.ModeGTE,"a_timeInMS", lastDayClock - Values.EventsDeepth*24*3600*1000)).
                    add(new DBQueryLong(I_DBQuery.ModeLTE,"a_timeInMS", lastDayClock + 24*3600*1000)).
                    add(new DBQueryBoolean("valid",true));
            int types[] = eventTypes();
            if (types.length!=0){
                DBQueryList query2 = new DBQueryList(I_DBQuery.ModeOr);
                for (int type : types)
                    query2.add(new DBQueryInt(I_DBQuery.ModeEQ,"type", type));
                query.add(query2);
                }
        final String xmlQuery = new DBXStream().toXML(query);
            new APICall<ArrayList<DBRequest>>(null){
                @Override
                public Call<ArrayList<DBRequest>> apiFun() {
                    return service.getEntityListByQuery(token,"ArchESSEvent",xmlQuery,0);
                    }
                @Override
                public void onSucess(ArrayList<DBRequest> oo) {
                    System.out.println("Прочитано событий "+oo.size());
                    events.clear();
                    Gson gson = new Gson();
                    for(DBRequest vv : oo){
                        try {
                            events.add((ArchESSEvent) vv.get(gson));
                            } catch (Exception ee){}
                        }
                    selected.clear();
                    for(int idx=events.size()-1;idx>=0;idx--){
                        ArchESSEvent essEvent = events.get(idx);
                        if (typeFilter(essEvent.getType()))
                            selected.add(essEvent);
                        }
                    System.out.println("Выбрано событий "+selected.size());
                    showTable();
                }
            };
        }
    public boolean typeFilter(int type) {
        return true;
        }
}
