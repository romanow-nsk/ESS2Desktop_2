package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.desktop.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIFormSelector extends GUIElement {
    private JButton list;
    private JButton up;
    private JButton prev;
    private JButton next;
    private int maxValue=0;
    private int idx=0;
    public GUIFormSelector(){
        type = Values.GUIFormSelector;
        }
    private boolean busy=false;
    @Override
    public void addToPanel(JPanel panel) {
        busy=true;
        setLabel(panel);
        prev = new JButton();
        prev.setBounds(
                context.x(element.getX()+element.getDx()+10),
                context.y(element.getY()),
                context.x(40),
                context.y(40));
        prev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/left.png"))); // NOI18N
        //textField.setBorderPainted(false);
        //textField.setContentAreaFilled(false);
        prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = element.getFormLevel();
                if (level==0) return;
                int pos = context.getIndex(level);
                if (pos==0) return;
                context.setIndex(level,pos-1);
                context.reOpenForm();
            }
        });
        panel.add(prev);
        list = new JButton();
        maxValue = context.getSize(element.getFormLevel());
        idx= context.getIndex(element.getFormLevel());
        list.setText(""+(idx+1));
        list.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new NumSelectorPanel(1, maxValue, new I_RealValue() {
                    @Override
                    public void onEvent(double value) {
                        int vv = (int)value-1;
                        context.setIndex(element.getFormLevel(),vv);
                        context.openForm(element.getClickFormName(),element.getFormLevel(),vv);
                        }
                    });
                }
            });
        list.setBounds(
                context.x(element.getX()+element.getDx()+50),
                context.y(element.getY()),
                context.x(45),
                context.y(40));
        list.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        panel.add(list);
        up = new JButton();
        up.setBounds(
                context.x(element.getX()+element.getDx()+95),
                context.y(element.getY()),
                context.x(40),
                context.y(40));
        up.setIcon(new javax.swing.ImageIcon(getClass().getResource("/down.png"))); // NOI18N
        //textField.setBorderPainted(false);
        //textField.setContentAreaFilled(false);
        up.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (busy) return;
                context.setIndex(element.getFormLevel(),0);
                context.openForm(element.getClickFormName(),element.getFormLevel(),idx);
                }
            });
        panel.add(up);
        next = new JButton();
        next.setBounds(
                context.x(element.getX()+element.getDx()+135),
                context.y(element.getY()),
                context.x(40),
                context.y(40));
        next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/right.PNG"))); // NOI18N
        //textField.setBorderPainted(false);
        //textField.setContentAreaFilled(false);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int level = element.getFormLevel();
                if (level==0) return;
                int pos = context.getIndex(level);
                if ((pos+1)>=context.getSize(element.getFormLevel()))
                    return;
                context.setIndex(level,pos+1);
                context.reOpenForm();
            }
        });
        panel.add(next);

        busy=false;
        }

    @Override
    public void putValue(long vv) throws UniException {}
    public boolean needRegister() {
        return false;
        }

}
