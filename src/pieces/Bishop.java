package pieces;

import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }
protected boolean pieceTypeAbilityToMove(Piece piece,Coordinates to){
        return Math.abs(piece.coordinates.file.ordinal() - to.file.ordinal())
        == Math.abs(piece.coordinates.rank - to.rank);
}





    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to)
            throws RuntimeException {
        if (!pieceTypeAbilityToMove(piece,to)) throw new RuntimeException(
                "нельзя этой фигурой так ходить");


//        List<Integer> fileDist = IntStream.rangeClosed(
//               Math.max( piece.coordinates.file.ordinal(),
//                       to.file.ordinal())
//
//        )
        return null;
    }

}
