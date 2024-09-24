package managers;

import pieces.Coordinates;
import pieces.Piece;
import utils.Board;

public interface Manager {


    void processMove(Piece piece, Coordinates from, Coordinates to, Board board);
}
