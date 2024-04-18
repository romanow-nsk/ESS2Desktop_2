package romanow.abc.desktop.iec61850;

//import org.openmuc.openiec61850.app.ConsoleClient;
import com.beanit.iec61850bean.app.ConsoleClient;

public class ESS61850ConsoleClient {
    public static void main(String ss[]){
        ConsoleClient.main(new String[]{"-h","127.0.0.1"});
    }
}
