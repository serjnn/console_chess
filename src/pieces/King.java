package pieces;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected boolean isMoveInvalidForThisType(Piece piece, Coordinates to) {
        int fileFrom = piece.coordinates.file.ordinal();
        int fileTo = to.file.ordinal();
        int rankFrom = piece.coordinates.rank;
        int rankTo = to.rank;

        return !((fileFrom - 1 == fileTo ||
                fileFrom + 1 == fileTo ||
                fileFrom == fileTo) &&
                (
                        rankFrom - 1 == rankTo ||
                                rankFrom + 1 == rankTo ||
                                rankFrom == rankTo
                ));
    }

    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to)
            throws RuntimeException {
        if (isMoveInvalidForThisType(piece, to)) throw new RuntimeException(
                "king does not move like that");

        return new ArrayList<>(List.of(to));

    }


}
