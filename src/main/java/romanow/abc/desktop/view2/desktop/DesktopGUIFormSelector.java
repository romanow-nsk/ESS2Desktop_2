package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.view.Meta2GUIFormButton;
import romanow.abc.core.entity.metadata.view.Meta2GUIFormSelector;
import romanow.abc.desktop.I_RealValue;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.NumSelectorPanel;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DesktopGUIFormSelector extends View2BaseDesktop {
    private JButton list;
    private JButton up;
    private JButton down;
    private JButton prev;
    private JButton next;
    private int maxValue=0;
    private int idx=0;
    private int level=0;
    public DesktopGUIFormSelector(){
        setType(Values.GUIFormSelector);
        }
    private boolean busy=false;
    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel);
        busy=true;
        setLabel(panel);
        prev = new JButton();
        FormContext2 context= getContext();
        final Meta2GUIFormSelector element = (Meta2GUIFormSelector) getElement();
        level = element.getFormLevel();
        prev.setBounds(
                context.x(element.getX()+element.getDx()+10),
                context.y(element.getY()),
                context.dx(40),
                context.dy(40));
        prev.setIcon(new ImageIcon(getClass().getResource("/left.png"))); // NOI18N
        //textField.setBorderPainted(false);
        //textField.setContentAreaFilled(false);
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //int level = context.getForm().getFormLevel();
                if (level==0) return;
                int pos = context.getIndex(level);
                if (pos==0) return;
                context.setIndex(level,pos-1);
                context.reOpenForm();
            }
        });
        panel.add(prev);
        list = new JButton();
        maxValue = context.getSize(context.getForm().getFormLevel());
        idx= context.getIndex(element.getFormLevel());            //?????????????????????????
        list.setText(""+(idx+1));
        list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NumSelectorPanel(1, maxValue, new I_RealValue() {
                    @Override
                    public void onEvent(double value) {
                        int vv = (int)value-1;
                        context.setIndex(level,vv);
                        context.reOpenForm();
                        //context.openForm(element.getFormName(),vv);
                        }
                    });
                }
            });
        list.setBounds(
                context.x(element.getX()+element.getDx()+50),
                context.y(element.getY()),
                context.dx(45),
                context.dy(40));
        list.setFont(new Font(Values.FontName, Font.PLAIN, context.y(12)));
        panel.add(list);
        next = new JButton();
        next.setBounds(
                context.x(element.getX()+element.getDx()+95),
                context.y(element.getY()),
                context.dx(40),
                context.dy(40));
        next.setIcon(new ImageIcon(getClass().getResource("/right.png"))); // NOI18N
        //textField.setBorderPainted(false);
        //textField.setContentAreaFilled(false);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (level==0) return;
                int pos = context.getIndex(level);
                if ((pos+1)>=context.getSize(level))
                    return;
                context.setIndex(level,pos+1);
                context.reOpenForm();
            }
        });
        panel.add(next);
        down = new JButton();
        down.setBounds(
                context.x(element.getX()+element.getDx()+135),
                context.y(element.getY()),
                context.dx(40),
                context.dy(40));
        down.setIcon(new ImageIcon(getClass().getResource("/down.png"))); // NOI18N
        //textField.setBorderPainted(false);
        //textField.setContentAreaFilled(false);
        down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (busy) return;
                context.openForm(element.getFormName());
                }
            });
        panel.add(down);
        up = new JButton();
        up.setBounds(
                context.x(element.getX()+element.getDx()+175),
                context.y(element.getY()),
                context.dx(40),
                context.dy(40));
        up.setIcon(new ImageIcon(getClass().getResource("/up.png"))); // NOI18N
        //textField.setBorderPainted(false);
        //textField.setContentAreaFilled(false);
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (busy) return;
                context.openForm(context.getForm().getParentName());
                }
            });
        panel.add(up);

        busy=false;
        }
    @Override
    public void putValue(long vv) throws UniException {}
    @Override
    public void showInfoMessage() {
        new Message(300,300, "Переход на форму "+((Meta2GUIFormButton)getElement()).getFormName(),Values.PopupMessageDelay);
        }
    public boolean needRegister() { return false; }

}
