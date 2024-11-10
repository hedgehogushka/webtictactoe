package webtictactoe;

import java.io.Serializable;

public final class Move implements Serializable {
    private final int row;
    private final int column;
    private final Cell value;
    private final MoveType moveType;

    public Move(final MoveType moveType, final int row, final int column, final Cell value) {
        this.moveType = moveType;
        this.row = row;
        this.column = column;
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Cell getValue() {
        return value;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    @Override
    public String toString() {
        return "row=" + row + ", column=" + column + ", value=" + value;
    }
}
