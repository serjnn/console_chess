package pieces;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected boolean isMoveInvalidForThisType(Piece piece, Coordinates to) {
        int fileFrom = piece.coordinates.file.ordinal();
        int fileTo = to.file.ordinal();
        int rankFrom = piece.coordinates.rank;
        int rankTo = to.rank;
        boolean forwardOrBeat = fileFrom - 1 == fileTo ||
                fileFrom + 1 == fileTo ||
                fileFrom == fileTo;
        if (piece.color == Color.WHITE) {
            return !(forwardOrBeat && rankTo == rankFrom + 1);
        } else {
            return !(forwardOrBeat && rankTo == rankFrom -1);
        }
    }

    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to) throws RuntimeException {
        if (isMoveInvalidForThisType(piece, to)) throw new RuntimeException(
                "pawn does not move like that");
        return new ArrayList<>(List.of(to));
    }


}
