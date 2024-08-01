package pieces;


import java.util.List;

public abstract class Piece {
     public Color color;
    public  Coordinates coordinates;

    public Piece(Color color, Coordinates coordinates) {
        this.color = color;
        this.coordinates = coordinates;
    }

    public abstract List<Coordinates> moveOptions(Piece piece);




}
