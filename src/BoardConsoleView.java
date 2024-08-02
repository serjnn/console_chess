import pieces.Color;
import pieces.Coordinates;
import pieces.File;
import pieces.Piece;

public class BoardConsoleView {


    public void render(Board board) {
        System.out.print("  ");
        for (File file : File.values()) System.out.print(file);
        System.out.println();
        for (int rank = 8; rank > 0; rank--) {
            String line = rank + " ";

            for (File file : File.values()) {
                Coordinates coordinates = new Coordinates(file, rank);
                if (board.isSquareEmpty(coordinates)) {
                    line += "_";
                } else {
                    line += getSymbolForPiece(board.getPiece(coordinates));
                }
            }


            System.out.println(line);
        }
    }

    private String getSymbolForPiece(Piece piece) {
        String res = "";
        switch (piece.getClass().getSimpleName()) {
            case "Pawn":
                res = "p";
                break;

            case "Knight":
                res = "h";
                break;

            case "Bishop":
                res = "b";
                break;

            case "Rook":
                res = "r";
                break;

            case "pieces.Queen":
                res = "q";
                break;

            case "King":
                res = "k";
                break;
        }

        return piece.color == Color.WHITE ? res : res.toUpperCase();
    }


}
