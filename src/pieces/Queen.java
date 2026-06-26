package pieces;

import pieces.enums.Color;
import java.util.List;

public class Queen extends Piece {
    public Queen(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    public boolean isMoveValidForThisType(Coordinates to) {
        if (this.coordinates.equals(to)) {
            return false;
        }
        int fileDist = this.coordinates.getFileDistance(to);
        int rankDist = this.coordinates.getRankDistance(to);
        return (fileDist == rankDist) || (fileDist == 0 || rankDist == 0);
    }

    @Override
    public List<Coordinates> everyStepToPoint(Coordinates to) {
        return this.coordinates.file == to.file || this.coordinates.rank == to.rank ?
                straightMoveSteps(to) : diagonalMoveSteps(to);
    }
}
