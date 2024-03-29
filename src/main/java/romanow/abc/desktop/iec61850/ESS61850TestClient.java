package romanow.abc.desktop.iec61850;

import org.openmuc.openiec61850.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ESS61850TestClient {
    public static void main(String ss[]){
        ClientAssociation association;
        ServerModel serverModel;
        ClientSap clientSap = new ClientSap();
        try {
            association = clientSap.associate(InetAddress.getByName("127.0.0.1"), 6500, null, new ClientEventListener(){
                @Override
                public void newReport(Report report) {

                    }
                @Override
                public void associationClosed(IOException e) {

                    }
            });
        } catch (IOException e) {
            System.out.println("Unable to connect to remote host.");
            return;
            }
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                association.close();
                }
            });
        System.out.println("successfully connected");
        System.out.println("retrieving model...");
            try {
                serverModel = association.retrieveModel();
                } catch (ServiceError e) {
                    System.out.println("Service error: " + e.getMessage());
                    return;
                } catch (IOException e) {
                    System.out.println("Fatal error: " + e.getMessage());
                    return;
                    }
        System.out.println("successfully read model");
        }
    }
