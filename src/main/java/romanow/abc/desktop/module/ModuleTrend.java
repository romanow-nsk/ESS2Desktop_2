package romanow.abc.desktop.module;

import romanow.abc.core.API.RestAPIBase;
import romanow.abc.core.API.RestAPIESS2;
import romanow.abc.core.entity.metadata.Meta2GUIForm;
import romanow.abc.core.entity.metadata.StreamRegisterData;
import romanow.abc.core.entity.subjectarea.DataSet;
import romanow.abc.core.entity.subjectarea.MetaGUIForm;
import romanow.abc.desktop.*;
import romanow.abc.desktop.view.FormContext;
import romanow.abc.desktop.view2.FormContext2;

import javax.swing.*;

public class ModuleTrend extends Module {
    private final static String buttonAdd = "/drawable/add.png";
    private TrendPanel trend;
    @Override
    public void init(MainBaseFrame client, JPanel panel, RestAPIBase service, RestAPIESS2 service2, String token, Meta2GUIForm form, FormContext2 formContext){
        super.init(client, panel, service, service2,token, form, formContext);
        trend = new TrendPanel();
        trend.init(context,after);
        }
    private Runnable after = new Runnable() {
        @Override
        public void run() {
            trend.setBounds(
                    context.x(form.getModuleX0()),
                    context.y(form.getModuleY0()),
                    context.dx(form.getModuleDX()),
                    context.dy(form.getModuleDY()));
            panel.add(trend);
            panel.revalidate();
            trend.setBack(new I_Success() {
                @Override
                public void onSuccess() {
                    new ESSStreamDataView((ESSClient) client,new I_Value<StreamRegisterData>() {
                        @Override
                        public void onEnter(StreamRegisterData value) {
                            if (value.getValueList().size()==0){
                                System.out.print("Нет данных тренда");
                                return;
                                }
                            trend.addTrendView(value);
                        }
                    });
                }
            });
            /*
            JButton toMain = trend.getRefreshButton();
            toMain.setIcon(new javax.swing.ImageIcon(getClass().getResource(buttonAdd))); // NOI18N
            toMain.setBorderPainted(false);
            toMain.setContentAreaFilled(false);
            toMain.removeActionListener(toMain.getActionListeners()[0]);
            toMain.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new ESSStreamDataView(client, true,new I_Value<DataSet>() {
                        @Override
                        public void onEnter(DataSet value) {
                            if (value.getData().size()==0){
                                System.out.print("Нет данных тренда");
                                return;
                            }
                            trend.addTrendView(value);
                        }
                    });
                }
            });
             */
        }
    };
}
