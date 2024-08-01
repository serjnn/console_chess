package pieces;

import java.util.List;

public class Knight extends Piece {
    public Knight(Color color, Coordinates coordinates) {
        super(color,coordinates);
    }

    @Override
    public List<Coordinates> moveOptions(Piece piece) {
        return null;
    }
}
