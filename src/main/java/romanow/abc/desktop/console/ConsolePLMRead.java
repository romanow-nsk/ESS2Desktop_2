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
        }
    @Override
    public String help() {
        return cmd+" reg <offset>\t- чтение регистра ПЛК "+(hex ? "(hex)" : "")+ "\n";
    }
}
