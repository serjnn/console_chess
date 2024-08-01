package pieces;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends  Piece{
    public Pawn(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    public List<Coordinates> moveOptions(Piece piece) {
        return piece.color == Color.WHITE ?
                new ArrayList<>(List.of(new Coordinates(piece.coordinates.file,
                        piece.coordinates.rank +1))) :

                new ArrayList<>(List.of(new Coordinates(piece.coordinates.file,
                        piece.coordinates.rank -1)));
    }
}
