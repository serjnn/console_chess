package pieces;

import java.util.List;

public class Knight extends Piece {
    public Knight(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    protected boolean pieceTypeAbilityToMove(Piece piece, Coordinates to) {
        return false;
    }

    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to) throws RuntimeException {
        return null;
    }
    //   // 2_1 1_2 = {2,1} and {1,2} - file and rank lists with differences of available
    //        //move for this type of piece
    //        //B1 - C3
    //        // == 1_1 - 2_3
    //
    //
    //        // int diffFile =  to.file.ordinal() - from.file.ordinal
    //        //int diffRank = to.rank - from.rank
    //        //if {2,1} contains diffFile  && {1,2}
    //        // contains diffRank -> that type of piece can move to this pos


}
