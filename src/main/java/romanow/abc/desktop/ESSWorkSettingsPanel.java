/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.ESS2ExportKotlin;
import romanow.abc.core.DBRequest;
import romanow.abc.core.entity.baseentityes.JEmpty;
import romanow.abc.core.entity.subjectarea.WorkSettings;
import retrofit2.Response;
import romanow.abc.core.utils.FileNameExt;
import romanow.abc.core.utils.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 *
 * @author romanow
 */
public class ESSWorkSettingsPanel extends ESSBasePanel {
    private WorkSettings ws;
    public ESSWorkSettingsPanel() {
        initComponents();
        }
    public void initPanel(MainBaseFrame main0){
        super.initPanel(main0);
        }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GUIrefreshPeriod = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        ArchiveDepthInDay = new javax.swing.JTextField();
        StreamDataPeriod = new javax.swing.JTextField();
        StreamDataLongPeriod = new javax.swing.JTextField();
        FailureTestPeriod = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        EventsPeriod = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        RegisterAge = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        FileScanPeriod = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        StreamDataCompressMode = new javax.swing.JTextField();
        UserSilenceTime = new javax.swing.JTextField();
        WaitForMainServer = new javax.swing.JCheckBox();
        jLabel25 = new javax.swing.JLabel();
        MainServerPort = new javax.swing.JTextField();
        MainServerIP = new javax.swing.JTextField();
        SnapShotPeriod = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        MainServerMode = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        MainServerPeriod = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        MainServerConnectPeriod = new javax.swing.JTextField();
        ToKotlinJS = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        IEC61850Port = new javax.swing.JTextField();
        ClockAcrossAPI = new javax.swing.JCheckBox();
        PriorityDispatcher = new javax.swing.JCheckBox();
        ProfilerOn = new javax.swing.JCheckBox();
        ProfilerPort = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        ProfilerScale = new javax.swing.JTextField();
        ProfilerFileSelect = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        ProfilerFilesPath = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        ProfilerBundle = new javax.swing.JCheckBox();
        ProfilerTrace = new javax.swing.JCheckBox();
        InterruptRegisterOn = new javax.swing.JCheckBox();
        jLabel35 = new javax.swing.JLabel();
        EventsQueuePeriod = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        IEC60870Port = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        IEC60870ASDU = new javax.swing.JTextField();

        setLayout(null);

        GUIrefreshPeriod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GUIrefreshPeriodKeyPressed(evt);
            }
        });
        add(GUIrefreshPeriod);
        GUIrefreshPeriod.setBounds(310, 130, 70, 25);

        jLabel18.setText("Цикл опроса актуальных ПД  (сек)");
        add(jLabel18);
        jLabel18.setBounds(20, 50, 230, 16);

        jLabel19.setText("Вид компрессии потоковых данных");
        add(jLabel19);
        jLabel19.setBounds(20, 290, 260, 16);

        jLabel20.setText("Цикл обнаружения аварий (сек)");
        add(jLabel20);
        jLabel20.setBounds(20, 110, 230, 16);

        ArchiveDepthInDay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ArchiveDepthInDayKeyPressed(evt);
            }
        });
        add(ArchiveDepthInDay);
        ArchiveDepthInDay.setBounds(310, 10, 70, 25);

        StreamDataPeriod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StreamDataPeriodKeyPressed(evt);
            }
        });
        add(StreamDataPeriod);
        StreamDataPeriod.setBounds(310, 40, 70, 25);

        StreamDataLongPeriod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StreamDataLongPeriodKeyPressed(evt);
            }
        });
        add(StreamDataLongPeriod);
        StreamDataLongPeriod.setBounds(310, 70, 70, 25);

        FailureTestPeriod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FailureTestPeriodKeyPressed(evt);
            }
        });
        add(FailureTestPeriod);
        FailureTestPeriod.setBounds(310, 100, 70, 25);

        jLabel21.setText("Период обновления форм ЧМИ (сек)");
        add(jLabel21);
        jLabel21.setBounds(20, 140, 210, 16);

        EventsPeriod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EventsPeriodKeyPressed(evt);
            }
        });
        add(EventsPeriod);
        EventsPeriod.setBounds(310, 160, 70, 25);

        jLabel22.setText("\"Возраст\" регистра в кэше (мс)");
        add(jLabel22);
        jLabel22.setBounds(20, 260, 250, 16);

        RegisterAge.setEnabled(false);
        RegisterAge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegisterAgeKeyPressed(evt);
            }
        });
        add(RegisterAge);
        RegisterAge.setBounds(310, 250, 70, 25);

        jLabel23.setText("Адрес ASDU МЭК 60870");
        add(jLabel23);
        jLabel23.setBounds(410, 200, 160, 16);

        jLabel24.setText("Глубина архива в днях");
        add(jLabel24);
        jLabel24.setBounds(20, 20, 180, 16);

        FileScanPeriod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FileScanPeriodKeyPressed(evt);
            }
        });
        add(FileScanPeriod);
        FileScanPeriod.setBounds(310, 220, 70, 25);

        jLabel33.setText("Цикл опроса фоновых ПД  (сек)");
        add(jLabel33);
        jLabel33.setBounds(20, 80, 280, 16);

        jLabel34.setText("Цикл опроса очередей событий (сек)");
        add(jLabel34);
        jLabel34.setBounds(20, 380, 260, 16);

        StreamDataCompressMode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                StreamDataCompressModeKeyPressed(evt);
            }
        });
        add(StreamDataCompressMode);
        StreamDataCompressMode.setBounds(310, 280, 70, 25);

        UserSilenceTime.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UserSilenceTimeKeyPressed(evt);
            }
        });
        add(UserSilenceTime);
        UserSilenceTime.setBounds(310, 310, 70, 25);

        WaitForMainServer.setText("Ждать отправки потоковых данных в интегратор");
        WaitForMainServer.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                WaitForMainServerItemStateChanged(evt);
            }
        });
        add(WaitForMainServer);
        WaitForMainServer.setBounds(410, 90, 340, 20);

        jLabel25.setText("Цикл опроса мгновенных значений  (сек)");
        add(jLabel25);
        jLabel25.setBounds(20, 200, 290, 16);

        MainServerPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MainServerPortKeyPressed(evt);
            }
        });
        add(MainServerPort);
        MainServerPort.setBounds(720, 60, 60, 25);

        MainServerIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MainServerIPKeyPressed(evt);
            }
        });
        add(MainServerIP);
        MainServerIP.setBounds(600, 60, 110, 25);

        SnapShotPeriod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SnapShotPeriodKeyPressed(evt);
            }
        });
        add(SnapShotPeriod);
        SnapShotPeriod.setBounds(310, 190, 70, 25);

        jLabel26.setText("Цикл опроса дискретных событий (сек)");
        add(jLabel26);
        jLabel26.setBounds(20, 170, 250, 16);

        MainServerMode.setText("Режим интегратора");
        MainServerMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                MainServerModeItemStateChanged(evt);
            }
        });
        add(MainServerMode);
        MainServerMode.setBounds(410, 10, 170, 20);

        jLabel2.setText("Время \"молчания\" оператора (мин)");
        add(jLabel2);
        jLabel2.setBounds(20, 320, 240, 16);

        jLabel28.setText("Цикл сервера интегратора  (сек)");
        add(jLabel28);
        jLabel28.setBounds(420, 40, 200, 16);

        MainServerPeriod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MainServerPeriodKeyPressed(evt);
            }
        });
        add(MainServerPeriod);
        MainServerPeriod.setBounds(640, 30, 70, 25);

        jLabel29.setText("Цикл проверки соединения  с интегратором (сек)");
        add(jLabel29);
        jLabel29.setBounds(20, 350, 290, 16);

        MainServerConnectPeriod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MainServerConnectPeriodKeyPressed(evt);
            }
        });
        add(MainServerConnectPeriod);
        MainServerConnectPeriod.setBounds(310, 340, 70, 25);

        ToKotlinJS.setText("Экспорт Kotlis/JS");
        ToKotlinJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToKotlinJSActionPerformed(evt);
            }
        });
        add(ToKotlinJS);
        ToKotlinJS.setBounds(410, 120, 150, 25);

        jLabel27.setText("Каталог профилирования");
        add(jLabel27);
        jLabel27.setBounds(520, 350, 180, 16);

        IEC61850Port.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IEC61850PortKeyPressed(evt);
            }
        });
        add(IEC61850Port);
        IEC61850Port.setBounds(600, 140, 70, 25);

        ClockAcrossAPI.setText("События таймеров через API");
        ClockAcrossAPI.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ClockAcrossAPIItemStateChanged(evt);
            }
        });
        add(ClockAcrossAPI);
        ClockAcrossAPI.setBounds(410, 260, 200, 20);

        PriorityDispatcher.setText("Диспетчер приоритетов для запросов к ModBus");
        PriorityDispatcher.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                PriorityDispatcherItemStateChanged(evt);
            }
        });
        add(PriorityDispatcher);
        PriorityDispatcher.setBounds(410, 230, 340, 20);

        ProfilerOn.setText("Профилирование ");
        ProfilerOn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ProfilerOnItemStateChanged(evt);
            }
        });
        add(ProfilerOn);
        ProfilerOn.setBounds(410, 290, 170, 20);

        ProfilerPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProfilerPortKeyPressed(evt);
            }
        });
        add(ProfilerPort);
        ProfilerPort.setBounds(600, 290, 70, 25);

        jLabel30.setText("IP/порт  сервера интегратора");
        add(jLabel30);
        jLabel30.setBounds(420, 70, 180, 16);

        ProfilerScale.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProfilerScaleKeyPressed(evt);
            }
        });
        add(ProfilerScale);
        ProfilerScale.setBounds(690, 290, 70, 25);

        ProfilerFileSelect.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/statistic.png"))); // NOI18N
        ProfilerFileSelect.setBorderPainted(false);
        ProfilerFileSelect.setContentAreaFilled(false);
        ProfilerFileSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProfilerFileSelectActionPerformed(evt);
            }
        });
        add(ProfilerFileSelect);
        ProfilerFileSelect.setBounds(680, 360, 40, 40);

        jLabel31.setText("Порт");
        add(jLabel31);
        jLabel31.setBounds(610, 270, 60, 16);

        ProfilerFilesPath.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProfilerFilesPathKeyPressed(evt);
            }
        });
        add(ProfilerFilesPath);
        ProfilerFilesPath.setBounds(410, 370, 260, 25);

        jLabel32.setText("Масштаб времени");
        add(jLabel32);
        jLabel32.setBounds(690, 270, 110, 16);

        ProfilerBundle.setText("Bundle");
        ProfilerBundle.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ProfilerBundleItemStateChanged(evt);
            }
        });
        add(ProfilerBundle);
        ProfilerBundle.setBounds(410, 330, 120, 20);

        ProfilerTrace.setText("Трассировка");
        ProfilerTrace.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ProfilerTraceItemStateChanged(evt);
            }
        });
        add(ProfilerTrace);
        ProfilerTrace.setBounds(410, 310, 170, 20);

        InterruptRegisterOn.setText("Опрос регистров прерываний");
        InterruptRegisterOn.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                InterruptRegisterOnItemStateChanged(evt);
            }
        });
        add(InterruptRegisterOn);
        InterruptRegisterOn.setBounds(20, 410, 280, 20);

        jLabel35.setText("Цикл опроса источников файлов (сек)");
        add(jLabel35);
        jLabel35.setBounds(20, 230, 260, 16);

        EventsQueuePeriod.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EventsQueuePeriodKeyPressed(evt);
            }
        });
        add(EventsQueuePeriod);
        EventsQueuePeriod.setBounds(310, 370, 70, 25);

        jLabel36.setText("Порт  сервера МЭК 61850");
        add(jLabel36);
        jLabel36.setBounds(410, 150, 160, 16);

        IEC60870Port.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IEC60870PortKeyPressed(evt);
            }
        });
        add(IEC60870Port);
        IEC60870Port.setBounds(600, 170, 70, 25);

        jLabel37.setText("Порт  сервера МЭК 60870");
        add(jLabel37);
        jLabel37.setBounds(410, 170, 160, 16);

        IEC60870ASDU.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IEC60870ASDUKeyPressed(evt);
            }
        });
        add(IEC60870ASDU);
        IEC60870ASDU.setBounds(600, 200, 70, 25);
    }// </editor-fold>//GEN-END:initComponents

    private void GUIrefreshPeriodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GUIrefreshPeriodKeyPressed
        procPressedInt(evt, GUIrefreshPeriod,"GUIrefreshPeriod");
    }//GEN-LAST:event_GUIrefreshPeriodKeyPressed

    private void ArchiveDepthInDayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ArchiveDepthInDayKeyPressed
        procPressedInt(evt, ArchiveDepthInDay,"archiveDepthInDay");
    }//GEN-LAST:event_ArchiveDepthInDayKeyPressed

    private void StreamDataPeriodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StreamDataPeriodKeyPressed
        procPressedInt(evt, StreamDataPeriod,"streamDataPeriod");
    }//GEN-LAST:event_StreamDataPeriodKeyPressed

    private void StreamDataLongPeriodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StreamDataLongPeriodKeyPressed
        procPressedInt(evt, StreamDataLongPeriod,"streamDataLongPeriod");
    }//GEN-LAST:event_StreamDataLongPeriodKeyPressed

    private void FailureTestPeriodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FailureTestPeriodKeyPressed
        procPressedInt(evt, FailureTestPeriod,"failureTestPeriod");
    }//GEN-LAST:event_FailureTestPeriodKeyPressed

    private void EventsPeriodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EventsPeriodKeyPressed
        procPressedInt(evt, EventsPeriod,"eventTestPeriod");
    }//GEN-LAST:event_EventsPeriodKeyPressed

    private void RegisterAgeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RegisterAgeKeyPressed
        procPressedInt(evt, RegisterAge,"maxRegisterAge");
    }//GEN-LAST:event_RegisterAgeKeyPressed


    private void FileScanPeriodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FileScanPeriodKeyPressed
        procPressedInt(evt, FileScanPeriod,"fileScanPeriod");
    }//GEN-LAST:event_FileScanPeriodKeyPressed


    private void StreamDataCompressModeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_StreamDataCompressModeKeyPressed
        procPressedInt(evt, StreamDataCompressMode,"compressMode");
    }//GEN-LAST:event_StreamDataCompressModeKeyPressed

    private void UserSilenceTimeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UserSilenceTimeKeyPressed
        procPressedInt(evt, UserSilenceTime,"userSilenceTime");
    }//GEN-LAST:event_UserSilenceTimeKeyPressed

    private void WaitForMainServerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_WaitForMainServerItemStateChanged
        procPressedBoolean(WaitForMainServer,"waitForMainServer");
    }//GEN-LAST:event_WaitForMainServerItemStateChanged

    private void MainServerIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MainServerIPKeyPressed
        procPressedString(evt, MainServerIP,"mainServerIP");
    }//GEN-LAST:event_MainServerIPKeyPressed

    private void MainServerPortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MainServerPortKeyPressed
        procPressedInt(evt, MainServerPort,"mainServerPort");
    }//GEN-LAST:event_MainServerPortKeyPressed

    private void SnapShotPeriodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SnapShotPeriodKeyPressed
        procPressedInt(evt, SnapShotPeriod,"snapShotPeriod");
    }//GEN-LAST:event_SnapShotPeriodKeyPressed

    private void MainServerModeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_MainServerModeItemStateChanged
        procPressedBoolean(MainServerMode,"mainServerMode");
        refreshMainServerParams();
    }//GEN-LAST:event_MainServerModeItemStateChanged

    private void MainServerPeriodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MainServerPeriodKeyPressed
        procPressedInt(evt, MainServerPeriod,"mainServerPeriod");
    }//GEN-LAST:event_MainServerPeriodKeyPressed

    private void MainServerConnectPeriodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MainServerConnectPeriodKeyPressed
        procPressedInt(evt, MainServerConnectPeriod,"mainServerConnectPeriod");
    }//GEN-LAST:event_MainServerConnectPeriodKeyPressed

    private void ToKotlinJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToKotlinJSActionPerformed
        FileNameExt fname = main.getOutputFileName("Каталог исходников Kotlin/JS","aaa","aaa");
        if (fname==null)
            return;
        System.out.println("Исходники Kotlis/JS: экспорт в "+fname.getPath());
        ESS2ExportKotlin.exportKotlin(fname.getPath());
    }//GEN-LAST:event_ToKotlinJSActionPerformed

    private void IEC61850PortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IEC61850PortKeyPressed
        procPressedInt(evt, IEC61850Port,"iec61850Port");
    }//GEN-LAST:event_IEC61850PortKeyPressed

    private void PriorityDispatcherItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_PriorityDispatcherItemStateChanged
        procPressedBoolean(PriorityDispatcher,"priorityDispatcher");
    }//GEN-LAST:event_PriorityDispatcherItemStateChanged

    private void ClockAcrossAPIItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ClockAcrossAPIItemStateChanged
        procPressedBoolean(ClockAcrossAPI,"clockAcrossAPI");
    }//GEN-LAST:event_ClockAcrossAPIItemStateChanged

    private void ProfilerOnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ProfilerOnItemStateChanged
        procPressedBoolean(ProfilerOn,"profilerOn");
    }//GEN-LAST:event_ProfilerOnItemStateChanged

    private void ProfilerPortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProfilerPortKeyPressed
        procPressedInt(evt, ProfilerPort,"profilerPort");
    }//GEN-LAST:event_ProfilerPortKeyPressed

    private void ProfilerScaleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProfilerScaleKeyPressed
        procPressedInt(evt, ProfilerScale,"profilerScale");
    }//GEN-LAST:event_ProfilerScaleKeyPressed

    private void ProfilerFileSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfilerFileSelectActionPerformed
        FileNameExt fileNameExt = main.getOutputFileName("Каталог файлов профилирования","aaa","aaa");
        if (fileNameExt==null)
            return;
        ProfilerFilesPath.setText(fileNameExt.getPath());
        ws.setProfilerPath(fileNameExt.getPath());
        updateSettings(ProfilerFilesPath,"profilerPath",fileNameExt.getPath());
    }//GEN-LAST:event_ProfilerFileSelectActionPerformed

    private void ProfilerFilesPathKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProfilerFilesPathKeyPressed
        if(evt.getKeyCode()!=10) return;
        ws.setProfilerPath(ProfilerFilesPath.getText());
        updateSettings(evt,"profilerPath",ProfilerFilesPath.getText());
    }//GEN-LAST:event_ProfilerFilesPathKeyPressed

    private void ProfilerBundleItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ProfilerBundleItemStateChanged
        procPressedBoolean(ProfilerBundle,"profilerBundle");
    }//GEN-LAST:event_ProfilerBundleItemStateChanged

    private void ProfilerTraceItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ProfilerTraceItemStateChanged
        procPressedBoolean(ProfilerTrace,"profilerTrace");
    }//GEN-LAST:event_ProfilerTraceItemStateChanged

    private void InterruptRegisterOnItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_InterruptRegisterOnItemStateChanged
        procPressedBoolean(InterruptRegisterOn,"interruptRegisterOn");
    }//GEN-LAST:event_InterruptRegisterOnItemStateChanged

    private void EventsQueuePeriodKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EventsQueuePeriodKeyPressed
        procPressedInt(evt, EventsQueuePeriod,"eventsQueuePeriod");
    }//GEN-LAST:event_EventsQueuePeriodKeyPressed

    private void IEC60870PortKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IEC60870PortKeyPressed
        procPressedInt(evt, IEC60870Port,"iec60870Port");
    }//GEN-LAST:event_IEC60870PortKeyPressed

    private void IEC60870ASDUKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IEC60870ASDUKeyPressed
        procPressedInt(evt, IEC60870ASDU,"iec60870ASDUAddress");
    }//GEN-LAST:event_IEC60870ASDUKeyPressed

    private void procPressedInt(KeyEvent evt, JTextField text, String name){
        if(evt.getKeyCode()!=10) return;
        int vv=0;
        try {
            vv = Integer.parseInt(text.getText());
        } catch (Exception ee){
            popup("Недопустимый формат целого");
            return;
        }
        updateSettings(evt,name,vv);
        refresh();
        }
    private void procPressedString(KeyEvent evt, JTextField text, String name){
        if(evt.getKeyCode()!=10) return;
        updateSettings(evt,name,text.getText());
        refresh();
        }
    private void procPressedBoolean(JCheckBox box, String name){
        updateSettings(null,name,box.isSelected());
        refresh();
        }

    @Override
    public void refresh() {
        try {
            if (!main.getWorkSettings())
                return;
            ws = (WorkSettings)main.workSettings;
            GUIrefreshPeriod.setText(""+ws.getGUIrefreshPeriod());
            StreamDataPeriod.setText(""+ws.getStreamDataPeriod());
            StreamDataLongPeriod.setText(""+ws.getStreamDataLongPeriod());
            FailureTestPeriod.setText(""+ws.getFailureTestPeriod());
            EventsPeriod.setText(""+ws.getEventTestPeriod());
            RegisterAge.setText(""+ws.getMaxRegisterAge());
            ArchiveDepthInDay.setText(""+ws.getArchiveDepthInDay());
            FileScanPeriod.setText(""+ws.getFileScanPeriod());
            StreamDataCompressMode.setText(""+ws.getCompressMode());
            UserSilenceTime.setText(""+ws.getUserSilenceTime());
            SnapShotPeriod.setText(""+ws.getSnapShotPeriod());
            //---------------------------------------------------------
            WaitForMainServer.setSelected(ws.isWaitForMainServer());
            MainServerIP.setText(""+ws.getMainServerIP());
            MainServerPort.setText(""+ws.getMainServerPort());
            MainServerPeriod.setText(""+ws.getMainServerPeriod());
            MainServerConnectPeriod.setText(""+ws.getMainServerConnectPeriod());
            MainServerMode.setSelected(ws.isMainServerMode());
            IEC61850Port.setText(""+ws.getIec61850Port());
            IEC60870Port.setText(""+ws.getIec60870Port());
            IEC60870ASDU.setText(""+ws.getIec60870ASDUAddress());
            ClockAcrossAPI.setSelected(ws.isClockAcrossAPI());
            PriorityDispatcher.setSelected(ws.isPriorityDispatcher());
            ProfilerOn.setSelected(ws.isProfilerOn());
            ProfilerBundle.setSelected(ws.isProfilerBundle());
            ProfilerTrace.setSelected(ws.isProfilerTrace());
            ProfilerPort.setText(""+ws.getProfilerPort());
            ProfilerScale.setText(""+ws.getProfilerScale());
            ProfilerFilesPath.setText(ws.getProfilerPath());
            InterruptRegisterOn.setSelected(ws.isInterruptRegisterOn());
            EventsQueuePeriod.setText(""+ws.getEventsQueuePeriod());
            refreshMainServerParams();
            } catch (Exception e) { popup(e.toString()); }
        }
    private void refreshMainServerParams(){
        boolean disable = !MainServerMode.isSelected();
        MainServerIP.setEnabled(disable);
        MainServerPort.setEnabled(disable);
        WaitForMainServer.setEnabled(disable);
        MainServerPeriod.setEnabled(!disable);
        GUIrefreshPeriod.setEnabled(disable);
        StreamDataPeriod.setEnabled(disable);
        StreamDataLongPeriod.setEnabled(disable);
        FailureTestPeriod.setEnabled(disable);
        EventsPeriod.setEnabled(disable);
        RegisterAge.setEnabled(disable);
        ArchiveDepthInDay.setEnabled(disable);
        FileScanPeriod.setEnabled(disable);
        StreamDataCompressMode.setEnabled(disable);
        SnapShotPeriod.setEnabled(disable);
        UserSilenceTime.setEnabled(disable);
        MainServerConnectPeriod.setEnabled(disable);
    }


    @Override
    public void eventPanel(int code, int par1, long par2, String par3,Object oo) {
        if (code==EventRefreshSettings){
            refresh();
            main.sendEventPanel(EventRefreshSettingsDone,0,0,"");
            }
        }

    @Override
    public void shutDown() {
    }

    private void updateSettings(){
        updateSettings(null);
        }
    private void updateSettings(KeyEvent evt){
        Response<JEmpty> wsr = null;
        try {
            wsr = main.service.updateWorkSettings(main.debugToken,new DBRequest(ws,main.gson)).execute();
            if (!wsr.isSuccessful()){
                popup("Ошибка обновления настроек  " + httpError(wsr),true);
                return;
                }
            popup("Настройки обновлены",true);
            if (evt!=null)
                main.viewUpdate(evt,true);
            main.sendEventPanel(EventRefreshSettings,0,0,"");
            } catch (IOException e) {
                main.viewUpdate(evt,false);
                popup(e.toString(),true);
                }
        }
    private void updateSettings(KeyEvent evt, String name, int val){
        Response<JEmpty> wsr = null;
        try {
            wsr = main.service.updateWorkSettings(main.debugToken,name,val).execute();
            if (!wsr.isSuccessful()){
                popup("Ошибка обновления настроек  " + httpError(wsr),true);
                return;
                }
            popup("Настройки обновлены",true);
            if (evt!=null)
                main.viewUpdate(evt,true);
            main.sendEventPanel(EventRefreshSettings,0,0,"");
            } catch (IOException e) {
                main.viewUpdate(evt,false);
                popup(e.toString(),true);
                }
        }
    private void updateSettings(KeyEvent evt, String name, boolean val){
        Response<JEmpty> wsr = null;
        try {
            wsr = main.service.updateWorkSettings(main.debugToken,name,val).execute();
            if (!wsr.isSuccessful()){
                popup("Ошибка обновления настроек  " + httpError(wsr),true);
                return;
                }
            popup("Настройки обновлены",true);
            if (evt!=null)
                main.viewUpdate(evt,true);
            main.sendEventPanel(EventRefreshSettings,0,0,"");
            } catch (IOException e) {
                main.viewUpdate(evt,false);
                popup(e.toString(),true);
                }
        }
    private void updateSettings(KeyEvent evt, String name, String val){
        Response<JEmpty> wsr = null;
        try {
            wsr = main.service.updateWorkSettings(main.debugToken,name,val).execute();
            if (!wsr.isSuccessful()){
                popup("Ошибка обновления настроек  " + httpError(wsr),true);
                return;
                }
            popup("Настройки обновлены",true);
            if (evt!=null)
                main.viewUpdate(evt,true);
            main.sendEventPanel(EventRefreshSettings,0,0,"");
            } catch (IOException e) {
                main.viewUpdate(evt,false);
                popup(e.toString(),true);
                }
            }
        private void updateSettings(Component evt, String name, String val){
            Response<JEmpty> wsr = null;
            try {
                wsr = main.service.updateWorkSettings(main.debugToken,name,val).execute();
                if (!wsr.isSuccessful()){
                    System.out.println("Ошибка обновления настроек  " + httpError(wsr));
                    popup("Ошибка обновления настроек, см. панель трассировки",true);
                    return;
                    }
                popup("Настройки обновлены",true);
                if (evt!=null)
                    main.viewUpdate(evt,true);
                main.sendEventPanel(EventRefreshSettings,0,0,"");
            } catch (IOException e) {
                main.viewUpdate(evt,false);
                popup(e.getMessage(),true);
            }
        }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ArchiveDepthInDay;
    private javax.swing.JCheckBox ClockAcrossAPI;
    private javax.swing.JTextField EventsPeriod;
    private javax.swing.JTextField EventsQueuePeriod;
    private javax.swing.JTextField FailureTestPeriod;
    private javax.swing.JTextField FileScanPeriod;
    private javax.swing.JTextField GUIrefreshPeriod;
    private javax.swing.JTextField IEC60870ASDU;
    private javax.swing.JTextField IEC60870Port;
    private javax.swing.JTextField IEC61850Port;
    private javax.swing.JCheckBox InterruptRegisterOn;
    private javax.swing.JTextField MainServerConnectPeriod;
    private javax.swing.JTextField MainServerIP;
    private javax.swing.JCheckBox MainServerMode;
    private javax.swing.JTextField MainServerPeriod;
    private javax.swing.JTextField MainServerPort;
    private javax.swing.JCheckBox PriorityDispatcher;
    private javax.swing.JCheckBox ProfilerBundle;
    private javax.swing.JButton ProfilerFileSelect;
    private javax.swing.JTextField ProfilerFilesPath;
    private javax.swing.JCheckBox ProfilerOn;
    private javax.swing.JTextField ProfilerPort;
    private javax.swing.JTextField ProfilerScale;
    private javax.swing.JCheckBox ProfilerTrace;
    private javax.swing.JTextField RegisterAge;
    private javax.swing.JTextField SnapShotPeriod;
    private javax.swing.JTextField StreamDataCompressMode;
    private javax.swing.JTextField StreamDataLongPeriod;
    private javax.swing.JTextField StreamDataPeriod;
    private javax.swing.JButton ToKotlinJS;
    private javax.swing.JTextField UserSilenceTime;
    private javax.swing.JCheckBox WaitForMainServer;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    // End of variables declaration//GEN-END:variables
}
