package pieces;

import java.util.List;

public class Queen extends Piece {
    public Queen(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    protected boolean isMoveInvalidForThisType(Piece piece, Coordinates to) {
        return false;
    }
    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to)
            throws RuntimeException {
        if (isMoveInvalidForThisType(piece, to)) throw new RuntimeException(
                "queen does not move like that");

return piece.coordinates.file == to.file || piece.coordinates.rank == to.rank ?
        straightMoveSteps(piece,to): diagonalMoveSteps(piece,to);
    }


}
