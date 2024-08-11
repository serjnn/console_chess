package pieces;

import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }


    @Override
    protected boolean isMoveInvalidForThisType(Coordinates to) {
        return Math.abs(this.coordinates.file.ordinal() - to.file.ordinal())
                != Math.abs(this.coordinates.rank - to.rank);
    }


    @Override
    public List<Coordinates> everyStepToPoint(Coordinates to)
            throws RuntimeException {
        if (isMoveInvalidForThisType( to)) throw new RuntimeException(
                "bishop does not move like that");
        return diagonalMoveSteps(to);
    }

}
