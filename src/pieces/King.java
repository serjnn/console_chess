package pieces;

import pieces.enums.Color;

import java.util.ArrayList;
import java.util.List;

import static managers.KingManager.castling;

public class King extends Piece {
    public King(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    public boolean isMoveValidForThisType( Coordinates to) {
        if (castling) {
            return true;
        }

        int fileFrom = this.coordinates.file.ordinal(),
                fileTo = to.file.ordinal(),
                rankFrom = this.coordinates.rank,
                rankTo = to.rank;

        return (fileFrom - 1 == fileTo ||
                fileFrom + 1 == fileTo ||
                fileFrom == fileTo) &&
                (
                        rankFrom - 1 == rankTo ||
                                rankFrom + 1 == rankTo ||
                                rankFrom == rankTo
                );
    }

    @Override
    public List<Coordinates> everyStepToPoint(Coordinates to)
            {

        return new ArrayList<>(List.of(to));

    }


}
