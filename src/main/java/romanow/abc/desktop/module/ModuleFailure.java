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
import romanow.abc.core.entity.subjectarea.Failure;
import romanow.abc.core.entity.subjectarea.FailureSetting;
import romanow.abc.desktop.*;
import romanow.abc.desktop.view.MultiTextButton;
import romanow.abc.core.utils.OwnDateTime;

import retrofit2.Call;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;



public class ModuleFailure extends Module {
    protected ArrayList<Failure> events = new ArrayList<>();
    protected ArrayList<Failure> events1 = new ArrayList<>();
    protected ArrayList<Failure> events2 = new ArrayList<>();
    protected JTable table;
    private ListSelectionListener listener;
    public ModuleFailure(){}
    private void addQuitedButton(){
        //JButton bb = new MultiTextButton(new Font("Arial Cyr", Font.PLAIN, context.dy(12)));
        JButton bb = new JButton();
        View2BaseDesktop.setButtonParams(bb,"Квитировать всё",false,context);
        bb.setBounds(
                context.x(10),
                context.y(form.getModuleDY()),
                context.dx(Values.MenuButtonW*3),
                context.dy(Values.MenuButtonH));
        bb.setVisible(true);
        bb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OK(200, 200, "Квитировать всё", new I_Button() {
                    @Override
                    public void onPush() {
                        try {
                            new APICall2<JEmpty>(){
                                @Override
                                public Call<JEmpty> apiFun() {
                                    return service2.quitAllFailures(token);
                                }
                            }.call(client);
                            repaintValues();
                        } catch (UniException ex) {
                            System.out.println("Ошибка API: "+ex.toString());
                            }
                        }
                    });
                }
            });
        panel.add(bb);
        panel.revalidate();
        panel.repaint();
        }
    @Override
    public void init(MainBaseFrame client, JPanel panel, RestAPIBase service, RestAPIESS2 service2, String token, Meta2GUIForm form, FormContext2 formContext) {
        super.init(client,panel, service, service2,token, form, formContext);
        repaintView();
        }
    //------------------------------------------------------------------------------------
    public void setQuited(final Failure failure){
        if (failure.isQuited()) return;
        new OK(200, 200, "Квитировать "+failure.getTitle(), new I_Button() {
            @Override
            public void onPush() {
                try {
                    new APICall2<JEmpty>(){
                        @Override
                        public Call<JEmpty> apiFun() {
                            return service2.quitFailure(token,failure instanceof FailureSetting,
                                    failure.getEquipName(), failure.getLogUnit(), failure.getRegNum(),failure.getBitNum());
                        }
                    }.call(client);
                    repaintValues();
                } catch (UniException ex) {
                    System.out.println("Ошибка API: "+ex.toString());
                }
            }
        });
        }
    //------------------------------------------------------------------------------------
    @Override
    public void repaintView() {
        super.repaintView();
        }

    private String[] columnsHeader = new String[] {"дата","начало", "оконч.","тип","событие"};
    private int sizes[] = {100,80,80,120,700};
    private void showTable(){
            Vector<Vector<String>> data = new Vector<Vector<String>>();
            Vector<String> header = new Vector<String>();
            for(String ss : columnsHeader)
                header.add(ss);
            for(ArchESSEvent essEvent : events){
                Vector<String> row = new Vector<String>();
                OwnDateTime dd = essEvent.getArrivalTime();
                row.add(dd.dateToString());
                row.add(dd.timeFullToString());
                row.add(essEvent.getEndTime().timeFullToString());
                row.add(Values.title("EventType",essEvent.getType()));
                row.add(essEvent.getTitle());
                data.add(row);
                }
            if (table!=null) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(events.size());
                model.setDataVector(data,header);
                }
            else{
                addQuitedButton();
                table = new JTable(data,header);
                JScrollPane scroll = new JScrollPane(table);
                scroll.setBounds(
                       context.x(form.getModuleX0()),
                       context.y(form.getModuleY0()),
                       context.dx(form.getModuleDX()),
                       context.dy(form.getModuleDY()-50));
                panel.add(scroll);
                table.setFont(new Font("Arial Cyr", Font.PLAIN, context.dy(12)));
                table.setRowHeight(context.dy(20));
                table.setSelectionForeground(Color.blue);
                table.setSelectionBackground(Color.yellow);
                table.setVisible(true);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
                table.setAutoscrolls(true);
                table.setShowGrid(true);
                listener = new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent e) {
                        if (e.getValueIsAdjusting())
                            return;
                        int[] selectedRows = table.getSelectedRows();
                        for(int i = 0; i < selectedRows.length; i++) {
                            System.out.println(events.get(selectedRows[i]));
                            setQuited(events.get(selectedRows[i]));
                            }
                        }
                    };
                table.getSelectionModel().addListSelectionListener(listener);
                table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        c.setBackground(events.get(row).getColor());
                        return c;
                        }
                    });
                }
            for(int i=0;i<sizes.length;i++)
                table.getColumnModel().getColumn(i).setPreferredWidth(sizes[i]);
            panel.repaint();
            panel.revalidate();
        }

    @Override
    public void repaintValues() {
        super.repaintValues();
        try {
            ArrayList<DBRequest> res = new APICall2<ArrayList<DBRequest>>(){
                @Override
                public Call<ArrayList<DBRequest>> apiFun() {
                    return service.getEntityListLast(token,"FailureBit",50,0);
                    }
                }.call(client);
            System.out.println("Прочитано событий "+res.size());
            events1.clear();
            Gson gson = new Gson();
            for(DBRequest request : res){
                Failure ff = (Failure) request.get(gson);
                if (ff.getEndTime().dateTimeValid())
                    System.out.println(ff);
                events1.add(ff);
                }
            res = new APICall2<ArrayList<DBRequest>>(){
                @Override
                public Call<ArrayList<DBRequest>> apiFun() {
                    return service.getEntityListLast(token,"FailureSetting",50,0);
                    }
                }.call(client);
            System.out.println("Прочитано событий "+res.size());        // Слияние по времени
            events2.clear();
            for(DBRequest request : res)
                events2.add((Failure) request.get(gson));
            events.clear();
            int idx1=events1.size()-1,idx2=events2.size()-1;
            while (!(idx1<0 && idx2<0)){
                if (idx2<0 || idx1>=0 && events1.get(idx1).getArrivalTime().timeInMS() > events2.get(idx2).getArrivalTime().timeInMS())
                    events.add(events1.get(idx1--));
                else
                    events.add(events2.get(idx2--));
                }
            showTable();
            } catch (UniException e) {
                System.out.println(e.toString());
                }
        }
    public boolean typeFilter(int type) {
        return true;
        }
}
