package pieces;

import pieces.enums.Color;
import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    public boolean isMoveValidForThisType(Coordinates to) {
        if (this.coordinates.equals(to)) {
            return false;
        }
        return this.coordinates.getFileDistance(to) == this.coordinates.getRankDistance(to);
    }

    @Override
    public List<Coordinates> everyStepToPoint(Coordinates to) {
        return diagonalMoveSteps(to);
    }
}
