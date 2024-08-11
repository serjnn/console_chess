package pieces;

import java.util.List;

public class Rook extends Piece {
    public Rook(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    // // any_0 0_any
//    //D8 - D1
//    // == 3_8 - 3_1
    @Override
    protected boolean isMoveInvalidForThisType(Coordinates to) {

        return this.coordinates.file != to.file && this.coordinates.rank != to.rank;
    }

    @Override
    public List<Coordinates> everyStepToPoint(Coordinates to) throws RuntimeException {
        if (isMoveInvalidForThisType(to)) throw new RuntimeException(
                "rook does not move like that");

        return straightMoveSteps(to);
        }
    }



