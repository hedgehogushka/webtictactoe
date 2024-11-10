package webtictactoe;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Map;

public class HumanPlayer implements Player {


    private static final Map<String, MoveType> TYPE = Map.of(
            "Put", MoveType.Put,
            "Surrender", MoveType.Sur,
            "Offer", MoveType.Offer,
            "Agree", MoveType.Agree,
            "Disagree", MoveType.Disagr
    );


    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() {
        this(System.out, new Scanner(System.in));
    }

    public Move move(final Position position, final Cell cell, final MoveType prevMove) {
        out.println("Position");
        out.println(position);
        out.println(cell + "'s move");
        if (prevMove == MoveType.Offer) {
            while (true){
                out.println("Your opponent offered a draw. Write agree or disagree if you want to continue");
                final Move move = new Move(TYPE.get(in.next()), -1, -1, cell);

                if (position.isValid(move, prevMove)){
                    return move;
                }
                out.println("Move is invalid");
            }
        }
        else {
            while (true) {
                if (prevMove == MoveType.PutAfterOffer) {
                    out.println("Your opponent want to continue this game.");
                    out.println("You can not offer a draw two times in a raw");
                }
                out.println("Enter 'Put' and row and column if you want to make move. For example 'Put 1 1'");
                out.println("Enter 'Offer -1 -1' if you want to offer draw.");
                out.println("Enter 'Surrender -1 -1' if you want to give up.");
                Move move = new Move(TYPE.get(in.next()), in.nextInt() - 1,
                        in.nextInt() - 1, cell);
                if (!position.isValid(move, prevMove)) {
                    final int row = move.getRow() + 1;
                    final int column = move.getColumn() + 1;
                    out.println("Move " + move + " is invalid");
                    continue;
                }
                if (MoveType.PutAfterOffer == prevMove &&
                        move.getMoveType() == MoveType.Offer){
                    continue;
                }
                return move;
            }
        }
    }
}
