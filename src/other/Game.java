package other;

import pieces.Color;
import pieces.Coordinates;
import pieces.File;
import pieces.Piece;

import java.util.List;
import java.util.Scanner;

public class Game {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static short moveCount = 0;
    private Color moveColor = Color.WHITE;

    private Color getMoveColor(short moveCount) {
        return moveCount % 2 == 0 ? Color.WHITE : Color.BLACK;
    }

    public void gameLoop(Board board) {


        while (true) {
            moveColor = getMoveColor(moveCount);
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


            if (board.getPieceColor(from) != moveColor) {
                System.out.println(ANSI_RED + "that piece isn't yours" + ANSI_RESET);
                continue;
            }
            if (!board.isSquareEmpty(to) && !board.isItEnemy(to, moveColor)) {

                    System.out.println(ANSI_RED + "u cant beat ur pieces" + ANSI_RESET);
                    continue;
                }

            
            if (to.rank < 1 || to.rank > 8) {
                System.out.println(ANSI_RED + "u cant go outside the map" + ANSI_RESET);
                continue;
            } else {
                Piece piece = board.getPiece(from);
//                        System.out.println(from);
//            System.out.println(to);
//            System.out.println(piece);
//            System.out.println(piece.getClass().getSimpleName());
                if (piece.getClass().getSimpleName().equals("Pawn") &&
                 from.file != to.file && !(board.isItEnemy(to, moveColor))){
                    System.out.println(ANSI_RED + "u cant move like that " +
                            "cuz pawn is not attacking " + ANSI_RESET);
                            continue;
                }
                List<Coordinates> steps = piece.everyStepToPoint(to);
                board.removePieceFromSquare(from);
                board.setPiece(to, piece);

                moveCount++;

            }


        }

    }



}
