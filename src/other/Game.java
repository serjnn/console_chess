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

    public static int moveCount= 0 ;
    public static Color moveColor = Color.WHITE;
    public static boolean underCheck;
    public static boolean checkRelease = false;
    private boolean selfCheck;
    private Coordinates prevWhiteKingCoords;
    private Coordinates prevBlackKingCoords;

    /*
    if (Color) isUnderCheckk result == крайний moveColor {dont change move color}

    или

    make some check that determines if this move wont be reason of check

    првоерка на то объявляется ли после своего же хожа шах, если да, то такой ход невозможен


     */


    public void gameLoop(Board board) {


        while (true) {
            if ( board.isBlackKingUnderCheck() || board.isWhiteKingUnderCheck()){
                checkRelease = true;
            }


             moveColor = moveCount % 2 == 0 ? Color.WHITE : Color.BLACK;
            System.out.println();


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
                    prevWhiteKingCoords = whiteKingCoords;
                    whiteKingCoords = to;


                } else {
                    prevBlackKingCoords = blackKingCoords;
                    blackKingCoords = to;

                }

            }

//            if (moveColor == Color.WHITE && )





            board.removePieceFromSquare(from);
            board.setPiece(to, piece);
            if (checkRelease){
                if (board.isWhiteKingUnderCheck() || board.isBlackKingUnderCheck()) {
                    System.out.println(ANSI_RED + "U ARE UNDER CHECK" + ANSI_RESET);
                    board.setPiece(from,piece); // move cancel
                    board.removePieceFromSquare(to);
                    blackKingCoords = prevBlackKingCoords;
                    whiteKingCoords = prevWhiteKingCoords;

                    continue;
                }
                else {checkRelease = false;
                selfCheck = false;}
            }

            board.addToActive(to);
            moveCount ++;











        }


    }

}







