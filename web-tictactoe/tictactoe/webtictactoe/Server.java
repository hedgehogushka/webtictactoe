package webtictactoe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    Lobby createLobby(String id, Board board) throws RemoteException;
    Lobby getLobby(String id) throws RemoteException;
}
