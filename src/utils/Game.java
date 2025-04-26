package utils;

import managers.KingManager;
import managers.PawnManager;
import pieces.enums.Color;
import pieces.Coordinates;
import pieces.enums.File;
import pieces.Piece;

import java.util.Scanner;


public class Game {


    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static boolean haveWhiteKingMoved = false;
    public static boolean haveBlackKingMoved = false;

    public static int moveCount = 0;
    public static Color moveColor = Color.WHITE;

    private final KingManager kingManager;

    private final PawnManager pawnManager;

    public Game(KingManager kingManager, PawnManager pawnManager) {
        this.kingManager = kingManager;
        this.pawnManager = pawnManager;
    }

    public void gameLoop(Board board) {


        while (true) {

            BoardConsoleRenderer view = new BoardConsoleRenderer();
            view.render(board);

            Scanner scanner = new Scanner(System.in);
            String move = scanner.nextLine();
            Coordinates from;
            Coordinates to;
            try {
                from = new Coordinates(File
                        .valueOf(String.valueOf(move.charAt(0))
                                .toUpperCase())
                        , Character.getNumericValue(move.charAt(1)));
                to = new Coordinates(File
                        .valueOf(String.valueOf(move.charAt(3))
                                .toUpperCase())
                        , Character.getNumericValue(move.charAt(4)));
            } catch (StringIndexOutOfBoundsException se) {
                System.out.println(ANSI_RED + "Please enter you're move" + ANSI_RESET);
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_RED + "Please enter correct coordinates" + ANSI_RESET);
                continue;
            }
            Piece piece = board.getPiece(from);
            pawnManager.checkForPeacefulMove(piece, to, board);
            kingManager.checkForCastling(piece, to, board);
            try {
                if (!piece.isMoveValidForThisType(to)) {
                    System.out.println(ANSI_RED + piece.getClass().getSimpleName() +
                            " can't move like that" + ANSI_RESET);

                    continue;
                }
            } catch (NullPointerException ne) {
                System.out.println(ANSI_RED + "Choose correct piece" + ANSI_RESET);

                continue;
            }
            try {
                board.isMoveValidOnBoard(piece, to);
            } catch (RuntimeException re) {
                System.out.println(ANSI_RED + re.getMessage() + ANSI_RESET);
                continue;

            }


            if (board.amIUnderCheck(piece, from, to)) {
                System.out.println(ANSI_RED + "You are under check" + ANSI_RESET);
                continue;
            }


            board.commitMove(piece, from, to);


            pawnManager.checkForUpgrade(piece, to, board);
            kingManager.checkForChangeKingCoords(piece, to);


        }


    }


}







