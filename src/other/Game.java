package other;

import pieces.Color;
import pieces.Coordinates;
import pieces.File;
import pieces.Piece;

import java.util.Scanner;



public class Game {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static Coordinates whiteKingCoords;
    public static Coordinates blackKingCoords;

    static {
        whiteKingCoords = new Coordinates(File.E, 1);
        blackKingCoords = new Coordinates(File.E, 6);
    }

    public static int moveCount= -1 ;
    public static Color moveColor = Color.WHITE;
    public static boolean underCheck;
    public static boolean checkRelease = false;


    public void gameLoop(Board board) {


        while (true) {
            System.out.println(board.isUnderCheck() + "---------------------");

            if ( board.isUnderCheck()){

                checkRelease = true;
            }



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

            try {
                if (piece.isMoveInvalidForThisType(to)){
                    System.out.println(ANSI_RED + piece.getClass().getSimpleName() +
                            " can't move like that" + ANSI_RESET);
                    continue;
                }
            } catch (NullPointerException ne) {
                System.out.println(ANSI_RED + "there is empty from where u wanna move" + ANSI_RESET);
                continue;
            }


            try {
                board.isMoveValid(from, to);
            }
//            catch (NumberFormatException ne) {
//                System.out.print("");
//            }
            catch (RuntimeException re) {
                System.out.println(ANSI_RED +  re.getMessage() + ANSI_RESET);
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

            if (checkRelease){
                System.out.println(ANSI_RED + "CHECK RELEASE WORKED" + ANSI_RESET);
                if (board.isUnderCheck()) {
                    System.out.println(ANSI_RED + "U ARE UNDER CHECK" + ANSI_RESET);
                    board.setPiece(from,piece); // move cancel
                    board.removePieceFromSquare(to);

                    continue;
                }
                else {checkRelease = false;}
            }

            board.addToActive(to);
            moveColor = moveCount % 2 == 0 ? Color.WHITE : Color.BLACK;

            moveCount ++;

            System.out.println(moveCount + " move countttt");
            System.out.println(moveColor);
            System.out.println(checkRelease + " checkRelease");








        }


    }

}







