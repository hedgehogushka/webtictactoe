package webtictactoe;

public class MakePosition {
    static Position getPosition(final Board board){
        return board.getPosition();
    }

    static Cell getCell(final Board board) {
        return board.getCell();
    }

    static Result makeMove(final Board board, Move move, MoveType prevMove){
        return board.makeMove(move, prevMove);
    }
}
