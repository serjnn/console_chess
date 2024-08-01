package pieces;

import java.util.List;

public class Rook extends Piece {
    public Rook(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    public List<Coordinates> moveOptions(Piece piece) {
        return null;
    }
}
