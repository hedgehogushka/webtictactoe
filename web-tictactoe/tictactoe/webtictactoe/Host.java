package webtictactoe;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Host {
    private static final int SERVER_PORT = 8088;
    private static final String SERVER_URL = "//localhost/server";

    public static void main(String[] args) {
        try {
            Server server = new RemoteServer(SERVER_PORT);
            UnicastRemoteObject.exportObject(server, SERVER_PORT);
            Naming.rebind(SERVER_URL, server);
            System.out.println("Server started");
        } catch (final RemoteException e) {
            System.out.println("Cannot export object: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (final MalformedURLException e) {
            System.out.println("Malformed URL");
        }
    }
}
