package pieces;

import pieces.enums.Color;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    public boolean isMoveValidForThisType(Coordinates to) {
        if (this.coordinates.equals(to)) {
            return false;
        }
        int fileDist = this.coordinates.getFileDistance(to);
        int rankDist = this.coordinates.getRankDistance(to);
        int direction = (this.color == Color.WHITE) ? 1 : -1;
        int rankDiff = to.rank - this.coordinates.rank;

        // Straight move 1 or 2 squares
        if (fileDist == 0) {
            if (rankDiff == direction) {
                return true;
            }
            if (rankDiff == 2 * direction && !hasMoved) {
                return true;
            }
        }
        // Diagonal capture 1 square
        if (fileDist == 1 && rankDiff == direction) {
            return true;
        }
        return false;
    }

    @Override
    public List<Coordinates> everyStepToPoint(Coordinates to) {
        return new ArrayList<>(List.of(to));
    }
}
