package webtictactoe;

public interface Position {
    boolean isValid(Move move, MoveType prevMove);
    int getN();
    int getM();

    Cell getCell(int r, int c);
}
