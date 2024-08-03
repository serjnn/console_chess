package pieces;

import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    protected boolean isMoveInvalidForThisType(Piece piece, Coordinates to) {
        return Math.abs(piece.coordinates.file.ordinal() - to.file.ordinal()) != Math.abs(piece.coordinates.rank - to.rank);
    }


    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to)
            throws RuntimeException {
        if (isMoveInvalidForThisType(piece, to)) throw new RuntimeException(
                "bishop does not move like that");
        return diagonalMoveSteps(piece,to);
    }

}
