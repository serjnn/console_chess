package pieces;

import java.util.List;

public class Rook extends Piece {
    public Rook(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }
// // any_0 0_any
//    //D8 - D1
//    // == 3_8 - 3_1
    @Override
    protected boolean pieceTypeAbilityToMove(Piece piece, Coordinates to) {
        return false;
    }

    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to) throws RuntimeException {
        return null;
    }


}
