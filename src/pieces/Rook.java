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
    protected boolean isMoveInvalidForThisType(Piece piece, Coordinates to) {

        return piece.coordinates.file != to.file && piece.coordinates.rank != to.rank;
    }

    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to) throws RuntimeException {
        if (isMoveInvalidForThisType(piece, to)) throw new RuntimeException(
                "rook does not move like that");

        return straightMoveSteps(piece,to);
        }
    }



