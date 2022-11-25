package romanow.abc.desktop.console;

import romanow.abc.core.UniException;

import java.util.ArrayList;

public class ConsolePLMTest extends ConsoleCommand {
    public ConsolePLMTest() {
        super("tconn");
        }
    @Override
    public String exec(ConsoleClient client, ArrayList<String> command) throws UniException {
        return "";
        /*
        client.connected = client.plm.isReady();
        return client.connected ? "Соединение с ПЛК установлено\n" : "Соединение с ПЛК не установлено\n" ;
         */
        }

    @Override
    public String help() {
        return cmd+"\t- проверка соединения с ПЛК\n";
    }
}
