package other;

import pieces.Color;
import pieces.Coordinates;
import pieces.File;
import pieces.Piece;

import java.util.Scanner;

public class Game {

    public static Coordinates whiteKingCoords;
    public static Coordinates blackKingCoords;

    static {
        whiteKingCoords = new Coordinates(File.E, 1);
        blackKingCoords = new Coordinates(File.E, 6);
    }

    public static short moveCount = 0;
    public static Color moveColor;




    public void gameLoop(Board board) {


        while (true) {


            moveColor = moveCount % 2 == 0 ? Color.WHITE : Color.BLACK;
            BoardConsoleView view = new BoardConsoleView();
            view.render(board);
            Scanner scanner = new Scanner(System.in);


            String move = scanner.nextLine();
            Coordinates from = new Coordinates(File
                    .valueOf((move.charAt(0) + "")
                            .toUpperCase())
                    , Character.getNumericValue(move.charAt(1)));
            Coordinates to = new Coordinates(File
                    .valueOf((move.charAt(3) + "")
                            .toUpperCase())
                    , Character.getNumericValue(move.charAt(4)));
            Piece piece = board.getPiece(from);

        if (piece.isMoveInvalidForThisType(to)){
            System.out.println(Board.ANSI_RED + piece.getClass().getSimpleName() +
                    " can't move like that" + Board.ANSI_RESET);
            continue;
        }



            try {
                board.isMoveValid(from, to);
            }
//            catch (NumberFormatException ne) {
//                System.out.print("");
//            }
            catch (RuntimeException re) {
                System.out.println(re.getMessage());
                continue;

            }

            // tracking king's coords
            if (piece.getClass().getSimpleName().equals("King")) {
                if (moveColor == Color.WHITE) {
                    whiteKingCoords = to;
                } else {
                    blackKingCoords = to;
                }

            }
            board.removePieceFromSquare(from);
            board.setPiece(to, piece);

            if ( board.isUnderCheck(to)){
                System.out.println(Board.ANSI_RED + "U ARE BEING UNDER CHECK" + Board.ANSI_RESET);
            }



            moveCount++;


        }


    }

}







