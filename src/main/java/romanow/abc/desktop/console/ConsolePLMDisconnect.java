package romanow.abc.desktop.console;

import romanow.abc.core.UniException;

import java.util.ArrayList;

public class ConsolePLMDisconnect extends ConsoleCommand {
    public ConsolePLMDisconnect() {
        super("dconn");
        }
    @Override
    public String exec(ConsoleClient client, ArrayList<String> command) throws UniException {
        return "";
        /*
        if (!client.connected){
            return "Нет соединения с ПЛК\n";
            }
        try {
            client.plm.closeConnection();
            } catch (UniException ex){
                return ex.toString()+"\n";
                }
        client.connected=false;
        return "Соединение с ПЛК разорвано\n";
         */
        }

    @Override
    public String help() {
        return cmd+"\t- отключение от ПЛК\n";
    }
}
