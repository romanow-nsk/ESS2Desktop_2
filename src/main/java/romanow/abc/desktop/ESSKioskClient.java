/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package romanow.abc.desktop;

import romanow.abc.core.ErrorList;
import romanow.abc.core.I_Boolean;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.render.ScreenMode;
import romanow.abc.dataserver.CommandStringData;
import romanow.abc.dataserver.ESSCommandStringData;
import romanow.abc.desktop.console.ConsoleClient;
import romanow.abc.desktop.console.ConsoleLogin;
import romanow.abc.desktop.console.ConsoleSystemsList;

import java.awt.*;
import java.util.ArrayList;

import static romanow.abc.desktop.BasePanel.EventPLMOn;

/**
 *
 * @author romanow
 */
public class ESSKioskClient extends ESSBaseView {

    /**
     * Creates new form ESSServiceGUIScreen
     */
    public final static int FullScreenHightMinus=50;
    ConsoleClient client = new ConsoleClient();
    ESSClient main = new ESSClient(false,false);
    private int xC,yC;
    private String loginText=Values.KioskClientAutoLogin;
    private String passText=Values.KioskClientAutotPass;
    private String guiName="";
    private String host="localhost";
    private int port=4567;
    private ScreenMode screenMode;
    private ESSServiceGUIScreen screen=null;
    private final static int xMin=640;
    private final static int yMin=480;
    private boolean wasAutoLogin=true;
    public ESSKioskClient(String pars[]){
        this(false,true,pars);
    }
    public ESSKioskClient(){
        this(false,false,new String[]{});
        }
    public void mes(String ss){
        Mes.append(ss);
        System.out.println(ss);
        }
    public ESSKioskClient(boolean min, boolean auto,String pars[]){
        super(xMin,yMin);
        Values.init();
        setVisible(false);
        setUndecorated(true);
        initComponents();
        setTitle("СМУ СНЭЭ");
        Login.setText(loginText);
        Password.setText(passText);
        ESSCommandStringData data = new ESSCommandStringData();
        data.parse(pars);
        ErrorList errors = data.getErrors();
        if (!errors.valid()){
            System.out.println();
            mes("Ошибки командной строки:\n"+errors.toString());
            return;
            }
        if (!data.hasConf()){
            mes("Конфигурация клиента по умолчанию");
            }
        else
            guiName = data.getConf();
        if (data.getPort()!=0)
            port = data.getPort();
        if (data.getHost()!=null)
            host = data.getHost();
        mes("сервер:"+host+":"+port+" клиент:"+guiName);
        if (!min){;
            setExtendedState(MAXIMIZED_BOTH);
            Dimension sSize = Toolkit.getDefaultToolkit ().getScreenSize ();
            int hh = sSize.height - FullScreenHightMinus;
            screenMode = new ScreenMode(sSize.width,hh);
            xC = sSize.width/2;
            yC = hh/2;
            setWH(sSize.width,hh);
            setSize(sSize.width,hh);
            setVisible(true);
            mes(" screen:"+sSize.width+"x"+hh);
            }
        else{
            setSize(xMin, yMin);
            xC = xMin/2;
            yC = yMin/2;
            positionOn(400,200);
            }
        initComponentsCenter();
        wasAutoLogin = auto;
        if (auto) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    loginButtonClick();
                    }
                });
            /*
            delayIt(0,new Runnable(){
                @Override
                public void run() {
                    loginButtonClick();
                }
            });
             */
            }
        }

    private  void initComponentsCenter(){
        Mes.setBounds(xC-300, yC-120, 650, 300);
        jLabel1.setBounds(xC-200, yC-160, 60, 17);
        jLabel2.setBounds(xC-200, yC-200, 60, 17);
        Login.setBounds(xC-120, yC-200, 250, 32);
        jButton1.setBounds(xC-300, yC-200, 50, 50);
        LButton.setBounds(xC+140, yC-180, 50, 50);
        Password.setBounds(xC-120, yC-160, 250, 32);
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
        jLabel2 = new javax.swing.JLabel();
        Login = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        LButton = new javax.swing.JButton();
        Password = new javax.swing.JPasswordField();
        Mes = new java.awt.TextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Пароль");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(200, 200, 60, 17);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Логин");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(200, 160, 60, 17);

        Login.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LoginMouseClicked(evt);
            }
        });
        getContentPane().add(Login);
        Login.setBounds(280, 150, 250, 32);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/battery.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        getContentPane().add(jButton1);
        jButton1.setBounds(130, 140, 50, 50);

        LButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/drawable/login.png"))); // NOI18N
        LButton.setBorderPainted(false);
        LButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LButtonActionPerformed(evt);
            }
        });
        getContentPane().add(LButton);
        LButton.setBounds(540, 170, 50, 50);

        Password.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Password.setText("jPasswordField1");
        Password.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PasswordMouseClicked(evt);
            }
        });
        getContentPane().add(Password);
        Password.setBounds(280, 190, 250, 30);

        Mes.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        getContentPane().add(Mes);
        Mes.setBounds(130, 250, 600, 240);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    }//GEN-LAST:event_formWindowClosing

    private void loginButtonClick(){
        try {
            client.setClientIP(host);
            client.setClientPort(port);
            client.startClient();
            client.getWorkSettings();
            String out="";
            out += new ConsoleSystemsList().exec(client,null);
            loginText = Login.getText();
            passText = Password.getText();
            ArrayList<String> xx = new ArrayList<>();
            xx.add("login");
            xx.add(loginText);
            xx.add(passText);
            xx.add(guiName);
            out += new ConsoleLogin().exec(client,xx);
            Mes.append(out);
            //System.out.println(out);  // ПЕРЕХВАЧЕН - виснет ??????
            main.setVisible(false);
            main.getErrors().clear();
            client.setExternalData(main);
            main.refreshArchtectureState();
            main.setRenderingOn(guiName);
            ErrorList errors = main.getErrors();
            if (!errors.valid()){
                Mes.append("\n"+errors.toString());
                main.show();
                main.setVisible(false);
                return;
                }
            if (main.currentView(false)==null)
                return;
            screen = new ESSServiceGUIScreen(main, new I_Boolean() {
                @Override
                public void onEvent(boolean bb) {
                    if (screen!=null)
                        screen.dispose();
                    screen = null;
                    Login.setText(Values.KioskClientAutoLogin);
                    Password.setText(Values.KioskClientAutotPass);
                    if (!bb){
                        loginButtonClick();
                        }
                    else{
                        setVisible(true);
                        }
                    }
                });
            setVisible(false);
            screen.setVisible(true);
            screen.eventPanel(EventPLMOn,0,0,"",null);  // Здесь ScreenMode не нужен, уже установлен
            screen.refresh();
            } catch (UniException ee){
                Mes.setText(ee.toString());
                System.out.println(ee.toString());
                }
        }

    private void LButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LButtonActionPerformed
        loginButtonClick();
    }//GEN-LAST:event_LButtonActionPerformed

    private void LoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LoginMouseClicked
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Mes.setText("");
                new KeyBoardPanel(screenMode.ScreenW(), screenMode.ScreenH(),false,Login,"",new I_Value<String>() {
                    @Override
                    public void onEnter(String value) {
                         loginText = value;
                         Login.setText(loginText);
                    }
                }).setVisible(true);
            }
        });
    }//GEN-LAST:event_LoginMouseClicked

    private void PasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PasswordMouseClicked
        Mes.setText("");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KeyBoardPanel(screenMode.ScreenW(), screenMode.ScreenH(),false,Password,"",new I_Value<String>() {
                    @Override
                    public void onEnter(String value) {
                         passText = value;
                         Password.setText(passText);
                    }
                }).setVisible(true);
            }
        });
    }//GEN-LAST:event_PasswordMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DigitPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DigitPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DigitPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DigitPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ESSKioskClient(args).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton LButton;
    private javax.swing.JTextField Login;
    private java.awt.TextArea Mes;
    private javax.swing.JPasswordField Password;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
