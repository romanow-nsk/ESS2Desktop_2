package romanow.abc.desktop.view;

import romanow.abc.core.UniException;
import romanow.abc.core.constants.Values;
import romanow.abc.core.drivers.I_ModbusGroupDriver;
import romanow.abc.core.entity.subjectarea.*;

import javax.swing.*;

public class GUILabel extends GUIElement {
    public GUILabel(){
        type = Values.GUILabel;
        }

    @Override
    public void addToPanel(JPanel panel) {
        setLabel(panel,element.getPower()==0);          // Power==1 - с цветом
        //JLabel ll = new JLabel();
        //ll.setBounds(element.getX(),element.getY(),element.getDx(),25);
        //ll.setFont(new Font(Values.FontName, Font.PLAIN, element.getFontSize()));
        //ll.setText("  "+element.getTitle());
        //panel.add(ll);
        //Color color=new Color(element.getColor());
        //ll.setBackground(color);
        }
    @Override
    public boolean needRegister() {
        return false;
        }

    @Override
    public String setParams(FormContext context, MetaExternalSystem meta, MetaRegister register0, MetaGUIElement element0, I_ModbusGroupDriver plm0, I_GUIEvent onEvent0) {
        super.setParams(context,meta,register0, element0,plm0,onEvent0);
        return null;
        }
    @Override
    public void putValue(long vv) throws UniException {}
}
