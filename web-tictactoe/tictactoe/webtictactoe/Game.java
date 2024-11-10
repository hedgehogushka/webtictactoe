package webtictactoe;

import java.rmi.Remote;
import java.rmi.RemoteException;

public class Game implements Remote {
    private final boolean log;
    private final Player player1, player2;
    private MoveType prevMove = MoveType.Unknown;
    public boolean isFinished = false;


    public Game(final boolean log, final Player player1, final Player player2) {
        this.log = log;
        this.player1 = player1;
        this.player2 = player2;
    }

    public int play(Board board) throws RemoteException {
        try {
            while (true) {

                final int result1 = move(board, player1, 1, prevMove);
                if (result1 >= 0) {
                    prevMove = prevMove == MoveType.Offer ? MoveType.PutAfterOffer : MoveType.Put;
                    return result1;
                } else if (result1 == -2) {
                    prevMove = MoveType.Offer;
                } else {
                    prevMove = prevMove == MoveType.Offer ? MoveType.PutAfterOffer : MoveType.Put;
                }

                final int result2 = move(board, player2, 2, prevMove);
                if (result2 >= 0) {
                    prevMove = prevMove == MoveType.Offer ? MoveType.PutAfterOffer : MoveType.Put;
                    return result2;
                } else if (result2 == -2) {
                    prevMove = MoveType.Offer;
                } else {
                    prevMove = prevMove == MoveType.Offer ? MoveType.PutAfterOffer : MoveType.Put;
                }
            }
        } finally {
            isFinished = true;
        }
    }

    private int move(final Board board, final Player player, final int no, final MoveType prevMove) throws RemoteException {
        Position pos = MakePosition.getPosition(board);
        Cell cell = MakePosition.getCell(board);
        final Move move = player.move(pos, cell, prevMove);
        final Result result = MakePosition.makeMove(board, move, prevMove);
        log("Player " + no + " move: " + move);
        log("Position:\n" + board);
        if (result == Result.WIN) {
            log("Player " + no + " won");
            return no;
        } else if (result == Result.LOSE) {
            log("Player " + no + " lose");
            return 3 - no;
        } else if (result == Result.DRAW) {
            log("Draw");
            return 0;
        } else if (result == Result.OFFER) {
            log("Offer");
            return -2;
        } else {
            return -1;
        }
    }

    private void log(final String message) throws RemoteException {
        if (log) {
            System.out.println(message);
        }
    }
}
