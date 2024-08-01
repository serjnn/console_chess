package pieces;

import java.util.List;

public class Bishop extends Piece {
    public Bishop(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    public List<Coordinates> moveOptions(Piece piece) {
        return null;
    }
}
