package webtictactoe;

import java.util.Arrays;
import java.util.Map;
import java.lang.Math;

public class MNKBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );


    private final Cell[][] cells;
    private Cell turn;
    private final int N = 3;
    private final int M = 3;
    private int EmptyCellsCounter = N * M;


    public MNKBoard() {
        this.cells = new Cell[N][M];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        // :NOTE: * Whole board will be serialized
        return this;
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    @Override
    public Result makeMove(final Move move, final MoveType prevMove) {

        if (!isValid(move, prevMove)) {
            return Result.LOSE;
        }

        if (move.getMoveType() == MoveType.Agree){
            return Result.DRAW;
        }
        if (move.getMoveType() == MoveType.Disagr){
            turn = turn == Cell.X ? Cell.O : Cell.X;
            return Result.UNKNOWN;
        }

        if (move.getMoveType() == MoveType.Sur){
            return Result.LOSE;
        }

        if (move.getMoveType() == MoveType.Offer){
            turn = turn == Cell.X ? Cell.O : Cell.X;
            return Result.OFFER;
        }

        cells[move.getRow()][move.getColumn()] = move.getValue();
        EmptyCellsCounter--;
        if (EmptyCellsCounter == 0){
            return Result.DRAW;
        }

        int col = 0;
        int k = 3;
        for(int u = Math.max(0, move.getRow() - k + 1); u < Math.min(N, move.getRow() + k); u++){
            if (cells[u][move.getColumn()] == turn){
                col++;
                if (col == k){
                    return Result.WIN;
                }
            } else {
                col = 0;
            }
        }
        col = 0;
        for(int v = Math.max(0, move.getColumn() - k + 1); v < Math.min(M, move.getColumn() + k); v++){
            if (cells[move.getRow()][v] == turn){
                col++;
                if (col == k){
                    return Result.WIN;
                }
            } else {
                col = 0;
            }
        }
        col = 0;
        int col1 = 0;
        for(int del = -(k - 1); del <= (k - 1); ++del) {

            if (0 <= move.getColumn() - del && move.getColumn() - del < M &&
                    0 <= move.getRow() + del && move.getRow() + del < N &&
                    cells[move.getRow() + del][move.getColumn() - del] == turn){
                col++;
                if (col == k){
                    return Result.WIN;
                }
            } else {
                col = 0;
            }

            if (0 <= move.getColumn() + del && move.getColumn() + del < M &&
                    0 <= move.getRow() + del && move.getRow() + del < N &&
                    cells[move.getRow() + del][move.getColumn() + del] == turn){
                col1++;
                if (col1 == k){
                    return Result.WIN;
                }
            } else {
                col1 = 0;
            }
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move, final MoveType prevMove) {
        return (0 <= move.getRow() && move.getRow() < N
                && 0 <= move.getColumn() && move.getColumn() < M
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell()) || (move.getMoveType() == MoveType.Sur)
                || (move.getMoveType() == MoveType.Offer && prevMove != MoveType.Offer)
                || (move.getMoveType() == MoveType.Agree && prevMove == MoveType.Offer)
                || (move.getMoveType() == MoveType.Disagr && prevMove == MoveType.Offer);
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public final int getN() {
        return N;
    }

    @Override
    public final int getM() {
        return M;
    }

    public void addSymbol(StringBuilder builder, String s) {
        builder.append(" ".repeat(Math.max(0, String.valueOf(M).length() - s.length() + 1)));
        builder.append(s);
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        addSymbol(sb, "");
        for (int r = 0; r < M; r++){
            addSymbol(sb, String.valueOf(r + 1));
        }
        for (int c = 0; c < N; c++) {
            sb.append("\n");
            addSymbol(sb, String.valueOf(c + 1));
            for( int r = 0; r < M; r++){
                addSymbol(sb, SYMBOLS.get(cells[r][c]).toString());
            }
        }

        return sb.toString();
    }
}
