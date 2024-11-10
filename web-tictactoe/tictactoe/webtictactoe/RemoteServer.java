package webtictactoe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RemoteServer implements Server {
    private final int port;
    private final ConcurrentMap<String, Lobby> lobbies = new ConcurrentHashMap<>();

    RemoteServer(int port) {
        this.port = port;
    }

    @Override
    public Lobby createLobby(String id, Board board) throws RemoteException {
        System.out.println("Creating lobby " + id);
        Lobby lobby = new RemoteLobby(board, port);
        UnicastRemoteObject.exportObject(lobby, port);
        lobbies.put(id, lobby);
        return lobby;
    }

    @Override
    public Lobby getLobby(String id) throws RemoteException {
        System.out.println("Retrieving lobby " + id);
        return lobbies.get(id);
    }
}
