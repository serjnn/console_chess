package pieces;

import java.util.List;

public class Queen extends Piece {
    public Queen(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    protected boolean isMoveInvalidForThisType(Coordinates to) {
        return false;
    }
    @Override
    public List<Coordinates> everyStepToPoint( Coordinates to)
            throws RuntimeException {
        if (isMoveInvalidForThisType(to)) throw new RuntimeException(
                "queen does not move like that");

return this.coordinates.file == to.file || this.coordinates.rank == to.rank ?
        straightMoveSteps(to): diagonalMoveSteps(to);
    }


}
