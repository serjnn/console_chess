package pieces;

import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }


    @Override
    public boolean isMoveValidForThisType(Coordinates to) {
        return Math.abs(this.coordinates.file.ordinal() - to.file.ordinal())
                == Math.abs(this.coordinates.rank - to.rank);
    }


    @Override
    public List<Coordinates> everyStepToPoint(Coordinates to) {
        return diagonalMoveSteps(to);
    }

}
