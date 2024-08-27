package other;

import pieces.Color;
import pieces.Coordinates;
import pieces.File;
import pieces.Piece;

import static other.Game.ANSI_RESET;

public class BoardConsoleView {
    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLACK_SQUARE_BACKGROUND = "\u001B[41m";
    public static final String ANSI_BLACK = "\u001B[30m";


    private static final String WHITE_BACK = ANSI_WHITE_SQUARE_BACKGROUND + "   " + ANSI_RESET;
    public static final String BLACK_BACK = ANSI_BLACK_SQUARE_BACKGROUND + "   " + ANSI_RESET;


    public void render(Board board) {
        System.out.print("  ");

        for (File file : File.values()) System.out.print(" " + file + " ");
        System.out.println();

        for (int rank = 8; rank > 0; rank--) {
            String line = rank + " ";

            for (File file : File.values()) {
                Coordinates coordinates = new Coordinates(file, rank);

                if (board.isSquareWhite(coordinates)) {
                    line += board.isSquareEmpty(coordinates) ? WHITE_BACK :
                            ANSI_WHITE_SQUARE_BACKGROUND + getSymbolForPiece(board.getPiece(coordinates)) + ANSI_RESET;


                } else {
                    line += board.isSquareEmpty(coordinates) ? BLACK_BACK :
                            ANSI_BLACK_SQUARE_BACKGROUND + getSymbolForPiece(board.getPiece(coordinates)) + ANSI_RESET;
                }


            }


            System.out.println(line);
        }
        System.out.print("  ");
        for (File file : File.values()) System.out.print(" " + file + " ");
        System.out.println();
        System.out.println(Game.moveColor.toString() + "' turn");
    }


    private String getSymbolForPiece(Piece piece) {
        String res = switch (piece.getClass().getSimpleName()) {
            case "Pawn" -> "p";
            case "Knight" -> "h";
            case "Bishop" -> "b";
            case "Rook" -> "r";
            case "Queen" -> "q";
            case "King" -> "k";
            default -> "";
        };

        return piece.color == Color.WHITE ? " " + res.toUpperCase() + " " : ANSI_BLACK + " " + res.toUpperCase() + " " + ANSI_RESET;
    }


}
