package pieces;

import java.util.List;

public class Queen extends Piece {
    public Queen(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    protected boolean pieceTypeAbilityToMove(Piece piece, Coordinates to) {
        return true;
    }
    @Override
    public List<Coordinates> everyStepToPoint(Piece piece, Coordinates to)
            throws RuntimeException {
        if (!pieceTypeAbilityToMove(piece,to)) throw new RuntimeException(
                "queen does not move like that");
        if (piece.coordinates.file == to.file || piece.coordinates.rank == to.rank){

        }
return null ;
    }


}
