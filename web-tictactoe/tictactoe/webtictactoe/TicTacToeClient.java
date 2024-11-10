package webtictactoe;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TicTacToeClient {
    private static final String SERVER_URL = "//localhost/server";

    private static Board getBoard() {
        return new MNKBoard();
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: TicTacToeClient port [lobbyId]");
            return;
        }
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port");
            System.out.println("Usage: TicTacToeClient port [lobbyId]");
            return;
        }
        String lobbyId = args.length > 1 ? args[0] : "TicTacToe1";
        try {
            Server server = (Server) Naming.lookup(SERVER_URL);
            Lobby lobby = server.getLobby(lobbyId);
            if (lobby == null) {
                lobby = server.createLobby(lobbyId, getBoard());
            }
            Player player = new HumanPlayer();
            UnicastRemoteObject.exportObject(player, port);

            int result = lobby.play(player);
            System.out.println("Game finished with result: " + result);
            UnicastRemoteObject.unexportObject(player, true);
        } catch (final NotBoundException e) {
            System.err.println("Server is not bound");
        } catch (final MalformedURLException e) {
            System.err.println("Server URL is invalid");
        } catch (final RemoteException e) {
            System.err.println("Error while working with remote objects: " + e.getMessage());
        }
    }
}
