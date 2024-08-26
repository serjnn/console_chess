package pieces;

import other.Game;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    public Pawn(Color color, Coordinates coordinates) {
        super(color, coordinates);
    }

    @Override
    protected boolean isMoveInvalidForThisType(Coordinates to) {


        int fileFrom = this.coordinates.file.ordinal();
        int fileTo = to.file.ordinal();
        int rankFrom = this.coordinates.rank;
        int rankTo = to.rank;
        Color color = this.color;

        boolean fileCheck = fileFrom - 1 == fileTo ||
                fileFrom + 1 == fileTo ||
                fileFrom == fileTo;
    int possiblePawnDistanceMove = Game.moveCount < 2 ? 2 : 1;

    boolean rankCheck = color == Color.WHITE ? (rankTo == rankFrom +
            possiblePawnDistanceMove || rankTo == rankFrom + 1) :
            (rankTo == rankFrom - possiblePawnDistanceMove || rankTo == rankFrom
                    - 1);
        return!(fileCheck &&rankCheck);
}



@Override

public List<Coordinates> everyStepToPoint(Coordinates to) throws RuntimeException {
    if (isMoveInvalidForThisType(to)) throw new RuntimeException(
            "pawn does not move like that");
    return new ArrayList<>(List.of(to));
}


}
