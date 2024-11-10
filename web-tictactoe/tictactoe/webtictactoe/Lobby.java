package webtictactoe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Lobby extends Remote {

    int play(Player player) throws RemoteException;
}
