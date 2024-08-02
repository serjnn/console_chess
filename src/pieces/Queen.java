package pieces;

import java.util.List;

public class Queen extends Piece {
    public Queen(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    protected boolean pieceTypeAbilityToMove(Piece piece, Coordinates to) {
        return false;
    }//// any_0 0_any
    //D8 - D1
    // == 3_8 - 3_1

    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to) throws RuntimeException {
        return null;
    }


}
