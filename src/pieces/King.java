package pieces;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected boolean isMoveInvalidForThisType( Coordinates to) {
        int fileFrom = this.coordinates.file.ordinal();
        int fileTo = to.file.ordinal();
        int rankFrom = this.coordinates.rank;
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
    public List<Coordinates> everyStepToPoint(Coordinates to)
            throws RuntimeException {
        if (isMoveInvalidForThisType( to)) throw new RuntimeException(
                "king does not move like that");

        return new ArrayList<>(List.of());

    }


}
