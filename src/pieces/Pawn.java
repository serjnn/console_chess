package pieces;

import pieces.enums.Color;

import java.util.ArrayList;
import java.util.List;

import static managers.PawnManager.pawnPeacefulMove;

public class Pawn extends Piece {
    public Pawn(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    public boolean isMoveValidForThisType(Coordinates to) {
        if (pawnPeacefulMove) {
            pawnPeacefulMove = false;
            return true;
        }

        int fileFrom = this.coordinates.file.ordinal(),
                fileTo = to.file.ordinal(),
                rankFrom = this.coordinates.rank,
                rankTo = to.rank;
        Color color = this.color;

        Boolean fileCheck = fileTo == fileFrom - 1 ||
                fileTo == fileFrom + 1;

        Boolean rankCheck =
                color == Color.WHITE ?
                        rankTo == rankFrom + 1 :
                        rankTo == rankFrom - 1;
        return fileCheck && rankCheck;

    }


    @Override

    public List<Coordinates> everyStepToPoint(Coordinates to) {

        return new ArrayList<>(List.of(to));
    }


}
