package other;

import pieces.Color;
import pieces.Coordinates;
import pieces.File;
import pieces.Piece;

public class BoardConsoleView {
    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLACK_SQUARE_BACKGROUND = "\u001B[41m";
    public static final String ANSI_BLACK = "\u001B[30m";



    public static final String ANSI_RESET = "\u001B[0m";


    public void render(Board board) {
        System.out.print("  ");

        for (File file : File.values()) System.out.print(" " + file + " ");
        System.out.println();

        for (int rank = 8; rank > 0; rank--) {
            String line = rank + " ";

            for (File file : File.values()) {
                Coordinates coordinates = new Coordinates(file, rank);
                if (board.isSquareEmpty(coordinates)) {
                    if (!board.isSquareDark(coordinates)) {
                        line +=
                                ANSI_WHITE_SQUARE_BACKGROUND + "   " + ANSI_RESET;
                    } else {
                        line += ANSI_BLACK_SQUARE_BACKGROUND+"   " + ANSI_RESET;
                    }
                } else {
                    line +=
                            !board.isSquareDark(coordinates) ?
                                    ANSI_WHITE_SQUARE_BACKGROUND +
                                            getSymbolForPiece(board.getPiece(coordinates))
                                            + ANSI_RESET :
                                    ANSI_BLACK_SQUARE_BACKGROUND+ getSymbolForPiece(
                                            board.getPiece(coordinates))
                     + ANSI_RESET;

                }
            }


            System.out.println(line);
        }
        System.out.print("  ");
        for (File file : File.values()) System.out.print(" " + file + " ");
        System.out.println();
        System.out.println(Game.moveCount % 2 == 0 ? "White's turn" : "Black's turn");
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

            case "Queen":
                res = "q";
                break;

            case "King":
                res = "k";
                break;
        }

        return piece.color == Color.WHITE ?
                " " + res.toUpperCase() + " " :
                ANSI_BLACK +  " " + res.toUpperCase() + " " + ANSI_RESET;
    }


}
