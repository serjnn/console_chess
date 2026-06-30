package pieces;

import pieces.enums.Color;
import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    public boolean isMoveValidForThisType(Coordinates to) {
        if (this.coordinates.equals(to)) {
            return false;
        }
        int fileDist = this.coordinates.getFileDistance(to);
        int rankDist = this.coordinates.getRankDistance(to);
        return (fileDist == 1 && rankDist == 2) || (fileDist == 2 && rankDist == 1);
    }

    @Override
    public List<Coordinates> everyStepToPoint(Coordinates to) {
        return new ArrayList<>(List.of(to));
    }
}
