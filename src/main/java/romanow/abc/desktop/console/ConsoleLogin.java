package romanow.abc.desktop.console;

import retrofit2.Response;
import romanow.abc.core.UniException;
import romanow.abc.core.constants.ValuesBase;
import romanow.abc.core.entity.baseentityes.JBoolean;
import romanow.abc.core.entity.users.User;

import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;

public class ConsoleLogin extends ConsoleCommand {
    String login="";
    String pass="";
    String guiName="";    public ConsoleLogin() {
        super("login");
        }
    @Override
    public String exec(ConsoleClient client, ArrayList<String> command) throws UniException {
        String out = "";
        int sz=command.size();
        switch (sz){
            case 2:
                System.out.print("password:");
                try {
                    pass = client.reader.readLine();
                    } catch (Exception ee){
                        return "pass: "+ee.toString()+"\n";
                        }
                System.out.print("guiName:");
                try {
                    guiName = client.reader.readLine();
                    } catch (Exception ee){
                        return "guiName: "+ee.toString()+"\n";
                        }
                break;
            case 3:
                login = command.get(1);
                guiName = command.get(2);
                System.out.print("password:");
                try {
                    pass = client.reader.readLine();
                    } catch (Exception ee){
                        return "pass: "+ee.toString()+"\n";
                        }
                break;
            case 4:
                login = command.get(1);
                pass = command.get(2);
                guiName = command.get(3);
                break;
            default:
                return "Недопустимое количество параметров\n";
                }
        client.startClient();
        String finalPass = pass;
        client.user = new APICallC<User>(){
            @Override
            public Call<User> apiFun() {
                return client.service.login(login, finalPass);
            }
        }.call();
        client.debugToken = client.user.getSessionToken();
        client.getWorkSettings();
        Response<ArrayList<String>> serverEnv = null;
        try {
            serverEnv = client.service.getSetverEnvironment(client.debugToken).execute();
            } catch (IOException e) {
                throw UniException.io(e);
                }
        if (!serverEnv.isSuccessful() || serverEnv.body()==null){
            out+="Сервер без проверки типа БД\n";
            }
        client.serverEnvironment = serverEnv.body();
        String ownSubjectArea = ValuesBase.env().applicationName(ValuesBase.AppNameSubjectArea);
        String serverSubjectArea = client.serverEnvironment.get(ValuesBase.AppNameSubjectArea);
        if (!serverSubjectArea.equals(ownSubjectArea)){
            out += "Другой тип сервера: "+ serverSubjectArea+"\n";
            }
        out += new ConsoleSystemsList().exec(client,null);
        return out;
        }
    @Override
    public String help() {
        return "login phone <password>\t- авторизация\n";
        }
}
