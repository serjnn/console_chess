package pieces;

import java.util.List;

public class King extends Piece {
    public King(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    public List<Coordinates> moveOptions(Piece piece) {
        return null;
    }
}
