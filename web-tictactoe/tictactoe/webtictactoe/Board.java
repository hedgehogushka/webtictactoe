package webtictactoe;

import java.io.Serializable;

public interface Board extends Serializable {
    Position getPosition();

    Cell getCell();

    Result makeMove(Move move, MoveType prevMove);
}
