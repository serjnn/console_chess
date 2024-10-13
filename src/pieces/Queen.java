package pieces;

import java.util.List;

public class Queen extends Piece {
    public Queen(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    public boolean isMoveValidForThisType(Coordinates to) {
        int fileFrom = this.coordinates.file.ordinal(),
                fileTo = to.file.ordinal(),
                rankFrom = this.coordinates.rank,
                rankTo = to.rank;
        return (Math.abs(fileFrom - fileTo)
                == Math.abs(rankFrom - rankTo))
                ||
                ( fileFrom == fileTo || rankFrom == rankTo);

    }

    @Override
    public List<Coordinates> everyStepToPoint(Coordinates to) {

        return this.coordinates.file == to.file || this.coordinates.rank == to.rank ?
                straightMoveSteps(to) : diagonalMoveSteps(to);
    }


}
