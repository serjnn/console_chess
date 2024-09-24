package utils;

import managers.KingManager;
import managers.PawnManager;
import pieces.Color;
import pieces.Coordinates;
import pieces.File;
import pieces.Piece;

import java.util.Scanner;


public class Game {


    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";


    private static int moveCount = 0;
    public static Color moveColor = Color.WHITE;

    private final KingManager  kingManager;

    private  final PawnManager pawnManager;

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
                System.out.println(ANSI_RED + "Please enter some" + ANSI_RESET);
                continue;
            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_RED + "Please enter correct coords" + ANSI_RESET);
                continue;
            }
            Piece piece = board.getPiece(from);


            pawnManager.checkForPeacefulMove(piece,from,to,board);

            try {
                if (piece.isMoveInvalidForThisType(to)) {
                    System.out.println(ANSI_RED + piece.getClass().getSimpleName() +
                            " can't move like that" + ANSI_RESET);

                    continue;
                }
            } catch (NullPointerException ne) {
                System.out.println(ANSI_RED + "Chose correct piece" + ANSI_RESET);

                continue;
            }
            try {
                board.isMoveValidOnBoard(piece, from, to);
            } catch (RuntimeException re) {
                System.out.println(ANSI_RED + re.getMessage() + ANSI_RESET);
                continue;

            }


            if (board.didIPutMyselfInCheck(piece, from, to)) {
                System.out.println(ANSI_RED + "Mind ur king, trash" + ANSI_RESET);
                continue;
            }


            board.commitMove(piece, from, to);


            moveCount++;
            moveColor = moveCount % 2 == 0 ? Color.WHITE : Color.BLACK;
            pawnManager.checkForUpgrade(piece, to, board);
            kingManager.checkForChangeKingCoords(piece,to);
            System.out.println(KingManager.blackKingCoords);


        }


    }


}







