package romanow.abc.desktop.console;

import romanow.abc.core.UniException;
import romanow.abc.core.entity.subjectarea.MetaRegister;

import java.util.ArrayList;

public class ConsolePLMRead extends ConsoleCommand {
    private boolean hex;
    public ConsolePLMRead(boolean hex0) {
        super(hex0 ? "rdx" : "rd");
        hex = hex0;
        }
    @Override
    public String exec(ConsoleClient client, ArrayList<String> command) throws UniException {
        return "";
        /*
        try {
            int sz = command.size();
            if (!client.plm.isReady()){
                return  "Устройство не готово\n";
                }
            if (sz==1)
                return "Не хватает параметров\n";
            int regNum = Integer.parseInt(command.get(1));
            int regOffset = 0;
            if (sz>2)
                regOffset = Integer.parseInt(command.get(2));
            MetaRegister register = client.meta.getRegisters().get(regNum);
            if (register==null){
                return  "Регистр "+regNum+" не найден\n";
                }
            int val = client.plm.readRegister("",0,regNum+regOffset);
            String ss = hex ?  Integer.toString(val & 0xFFFF,16) : ""+val;
            return "Регистр прочитан "+(regNum+regOffset)+":"+ss+"\n";
            }
        catch(UniException e1){
            return  "Недопустимое значение параметра";
            }
        catch(Exception ee){
            return "Недопустимое значение параметра";
            }
         */
        }
    @Override
    public String help() {
        return cmd+" reg <offset>\t- чтение регистра ПЛК "+(hex ? "(hex)" : "")+ "\n";
    }
}
