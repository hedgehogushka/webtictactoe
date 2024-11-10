package webtictactoe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Player extends Remote {
    Move move(Position position, Cell cell, MoveType prevMove) throws RemoteException;
}

