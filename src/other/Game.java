package other;

import pieces.*;

import java.util.Scanner;

public class Game {

    public static Coordinates whiteKingCoords;
    public static Coordinates blackKingCoords;

    static {
        whiteKingCoords = new Coordinates(File.E, 1);
        blackKingCoords = new Coordinates(File.E, 8);
        lastPiece = new Pawn(Color.WHITE, new Coordinates(File.A,1));
    }

    public static short moveCount = 0;
    public static Color moveColor;

    static Piece lastPiece;


    public void gameLoop(Board board) {


        while (true) {
            System.out.println(lastPiece + " last piece");

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


            try {
                board.isUnderCheck(lastPiece);
            } catch (RuntimeException re) {
                System.out.println(re.getMessage());
                continue;
            }


            try {
                board.isMoveValid(from, to);
            } catch (RuntimeException re) {
                System.out.println(re.getMessage());
                continue;

            }

            Piece piece = board.getPiece(from);
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
            lastPiece = piece;
            moveCount++;

        }


    }

}







