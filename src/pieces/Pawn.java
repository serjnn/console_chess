package pieces;

import java.util.List;

public class Pawn extends  Piece{
    public Pawn(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    protected boolean pieceTypeAbilityToMove(Piece piece, Coordinates to) {
        return false;
    }

    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to) throws RuntimeException {
        return null;
    }


}
