package webtictactoe;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Phaser;

public class RemoteLobby implements Lobby {
    private final Board board;
    private final int port;
    private final Phaser phaser;
    private final ConcurrentLinkedDeque<Player> players;
    private Game game;
    private int gameResult;

    RemoteLobby(Board board, int port) throws RemoteException {
        this.board = board;
        this.port = port;
        this.phaser = new Phaser(2);
        this.players = new ConcurrentLinkedDeque<>();
    }

    @Override
    public int play(Player player) throws RemoteException {
        players.add(player);
        phaser.arriveAndAwaitAdvance();
        startGame();
        synchronized (game) {
            while (!game.isFinished) {
                try {
                    game.wait();
                } catch (Exception ignored) {
                }
            }
        }
        return gameResult;
    }

    public synchronized void startGame() throws RemoteException {
        if (game != null) {
            return;
        }
        Player player1 = players.getFirst();
        Player player2 = players.getLast();
        game = new Game(false, player1, player2);
        UnicastRemoteObject.exportObject(game, port);
        synchronized (game) {
            gameResult = game.play(board);
            game.notifyAll();
        }
    }
}
