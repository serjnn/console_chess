package pieces;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    public Knight(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    public boolean isMoveValidForThisType(Coordinates to) {
        int fileFrom = this.coordinates.file.ordinal(),
                fileTo = to.file.ordinal(),
                rankFrom = this.coordinates.rank,
                rankTo = to.rank;
        return ((fileTo == fileFrom + 1 || fileTo == fileFrom - 1) && (rankTo ==
                rankFrom + 2 || rankTo == rankFrom - 2))
                ||
                ((rankTo == rankFrom + 1 || rankTo == rankFrom - 1) && (fileTo ==
                        fileFrom + 2 || fileTo == fileFrom - 2));
    }

    @Override
    public List<Coordinates> everyStepToPoint(Coordinates to) {
        return new ArrayList<>(List.of(to));

    }


}
