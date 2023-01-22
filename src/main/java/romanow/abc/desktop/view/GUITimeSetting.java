package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.desktop.ESSTimeSetting;
import romanow.abc.desktop.I_Success;
import romanow.abc.core.entity.subjectarea.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUITimeSetting extends GUIElement {
    private JTextField textField;
    public GUITimeSetting(){
        type = Values.GUITimeSetting;
        }
    private I_Success back=null;

    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        int w2=element.getW2();
        if (w2==0) w2=100;
        textField = new JTextField();
        textField.setBounds(
                context.x(element.getX()+element.getDx()+5),
                context.y(element.getY()),
                context.x(w2),
                context.y(25));
        textField.setEditable(false);
        textField.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        textField.setHorizontalAlignment(JTextField.CENTER);
        panel.add(textField);
        Color color=new Color(element.getColor());
        textField.setBackground(color);
        textField.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()<2)
                    return;
                new ESSTimeSetting(meta, (MetaSettingRegister) register, plm, new I_Success() {
                    @Override
                    public void onSuccess() {
                        if (back!=null)
                            back.onSuccess();
                    }
                });
                }
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) { }
            });
        }

    @Override
    public void putValue(long zz) throws UniException {
        textField.setText(String.format("%2d:%2d:%2d",zz/3600,zz%3600/60,zz%60));
        }
    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent back0) {
        super.setParams(context,meta,register0, element0,plm0,back0);
        if (!(register instanceof MetaSettingRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
    }
}
