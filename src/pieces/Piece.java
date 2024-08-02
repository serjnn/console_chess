package pieces;


import java.util.List;

public abstract class Piece {
     public Color color;
    public  Coordinates coordinates;

    public Piece(Color color, Coordinates coordinates) {
        this.color = color;
        this.coordinates = coordinates;
    }


protected abstract boolean pieceTypeAbilityToMove(Piece piece,Coordinates to);


    public abstract List<Coordinates> everyStepToPoint(Piece piece, Coordinates to)
            throws RuntimeException;
}
