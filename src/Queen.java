import pieces.Color;
import pieces.Coordinates;
import pieces.Piece;

import java.util.List;

public class Queen extends Piece {
    public Queen(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    public List<Coordinates> moveOptions(Piece piece) {
        return null;
    }
}
