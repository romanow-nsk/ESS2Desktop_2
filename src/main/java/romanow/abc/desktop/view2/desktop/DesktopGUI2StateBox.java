package romanow.abc.desktop.view2.desktop;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.entity.metadata.Meta2Bit;
import romanow.abc.core.entity.metadata.Meta2BitRegister;
import romanow.abc.core.entity.metadata.Meta2Register;
import romanow.abc.core.entity.metadata.view.Meta2GUI;
import romanow.abc.core.entity.metadata.view.Meta2GUI2StateBox;
import romanow.abc.core.entity.subject2area.ESS2Architecture;
import romanow.abc.desktop.I_Button;
import romanow.abc.desktop.Message;
import romanow.abc.desktop.OK;
import romanow.abc.desktop.view2.FormContext2;
import romanow.abc.desktop.view2.I_GUI2Event;
import romanow.abc.desktop.view2.View2BaseDesktop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static romanow.abc.core.entity.metadata.Meta2Entity.toHex;

public class DesktopGUI2StateBox extends View2BaseDesktop {
    protected JComponent textField;
    private int bitNum=0;
    private JButton cmdButton=null;     // Кнопка
    private int lastBitValue=-1;        // Последнее значение разряда
    private int lastValue=0;            //
    public DesktopGUI2StateBox(){
        setType(Values.GUI2StateBox);
        }
    //----------------------------------------------------------------------
    protected JComponent createComponent(){
        return new JButton();
        }
    protected void viewComponent(){
        JButton bb = (JButton)textField;
        bb.setIcon(new ImageIcon(getClass().getResource("/ballwhite.png"))); // NOI18N
        bb.setBorderPainted(false);
        bb.setContentAreaFilled(false);
        }
    protected void putValueOwn(int cc){
        JButton bb = (JButton)textField;
        bb.setIcon(new ImageIcon(getClass().getResource(getColorIconName(cc))));
        }
    protected int getSize(){
        return 24;
        }
    protected int getOffset(){
        return 0;
    }
    //-----------------------------------------------------------------------
    private String getColorIconName(int color){
        switch (color){
            case 0x00C0C0C0: return "/balllightgray.png";
            case 0x00FF0000: return "/ballred.png";
            case 0x0000FF00: return "/ballgreen.png";
            case 0x0000FFFF: return "/ballyellow.png";
            }
        return "/ballwhite.png";
        }
    @Override
    public void addToPanel(JPanel panel) {
        Meta2GUI2StateBox element = (Meta2GUI2StateBox) getElement();
        int hh = element.getH();
        if (hh==0) hh=25;
        if (element.getDx()!=0){
            setLabel(panel);
            }
        textField = createComponent();
        int dd = element.getW2();
        if (dd==0) dd=50;
        int sz = getSize();
        int offset = (25-sz)/2;
        FormContext2 context = getContext();
        int xx = element.getX()+element.getDx()+getDxOffset()+dd-5+offset;
        int yy = element.getY()+getDyOffset()+(hh-15)/2-5+offset;
        textField.setBounds(
                context.x(xx),
                context.y(yy),
                context.x(sz),
                context.y(sz));
        viewComponent();
        setInfoClick(textField);
        panel.add(textField);
        bitNum = element.getBitNum();
        int bSize = element.getButtonSize();
        if (bSize==0)
            return;
        final boolean remoteDisable = !context.isSuperUser() &&  !context.isLocalUser() && !element.isRemoteEnable();
        cmdButton = new JButton();
        cmdButton.setBounds(
                context.x(xx+sz+5),
                context.y(yy),
                context.x(bSize),
                context.y(sz));
        cmdButton.setFont(new Font("Arial Cyr", Font.PLAIN, context.y(12)));
        cmdButton.setHorizontalAlignment(JTextField.CENTER);
        cmdButton.setText("");
        cmdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (context.isInfoMode()){
                    showInfoMessage();
                    return;
                    }
                if (remoteDisable){
                    new Message(300,300,"Запрет удаленного управления", Values.PopupMessageDelay);
                    return;
                    }
                if (!context.isActionEnable()){
                    new Message(300,300,"Недостаточен уровень доступа",Values.PopupMessageDelay);
                    return;
                    }
                if (lastBitValue==-1){
                    new Message(300,300,"Разряд еще не прочитан",Values.PopupMessageDelay);
                    return;
                    }
                new OK(200, 200, element.getTitle()+" "+(lastBitValue!=0 ? "ОТКЛ" : "ВКЛ"), new I_Button() {
                    @Override
                    public void onPush() {
                        try {
                            int vv = lastValue ^ (1<<bitNum);       // Инвертировать разряд
                            writeMainRegister(vv);
                            } catch (UniException ex) {
                                String ss = "Ошибка изменения разряда: "+ex.toString();
                                context.popup(ss);
                                System.out.println(ss);
                                }
                            }
                        });
                    }
            });
        panel.add(cmdButton);

        }
    public void showInfoMessage() {
        Meta2GUI2StateBox element = (Meta2GUI2StateBox) getElement();
        int bitNumElem = element.getBitNum();
        Meta2BitRegister set = (Meta2BitRegister) getRegister();
        Meta2Bit bit = set.getBits().getByCode(bitNumElem);
        String ss = "Разряд регистра "+toHex(set.getRegNum()+getRegOffset())+" ["+toHex(set.getRegNum())+"]("+bitNum+") "+set.getShortName()+"$"+set.getTitle()+"$";
        ss+=bit==null ? " не найден " : bit.getTitle();
        new Message(300,300,ss,Values.PopupMessageDelay);
        }
    @Override
    public void putValue(int vv) throws UniException {
        Meta2GUI2StateBox element = (Meta2GUI2StateBox) getElement();
        lastValue = vv;
        lastBitValue = (vv>>bitNum) & 01;
        if (cmdButton!=null)
            cmdButton.setText(lastBitValue!=0 ? "ОТКЛ" : "ВКЛ");
        int cc = (lastBitValue!=0 ? element.getColorYes() : element.getColorNo()) & 0x00FFFFFF;
        putValueOwn(cc);
        }

    @Override
    public String setParams(FormContext2 context0, ESS2Architecture meta0, Meta2GUI element0, I_GUI2Event onEvent0) {
        super.setParams(context0,meta0, element0,onEvent0);
        Meta2Register register = (Meta2Register) getRegister();
        if (!(register instanceof Meta2BitRegister))
            return "Недопустимый "+register.getTypeName()+" для "+getTypeName();
        return null;
        }
}
