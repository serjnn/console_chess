package utils;

import pieces.Color;
import pieces.Coordinates;
import pieces.File;
import pieces.Piece;

import java.util.Scanner;

import static utils.KingManager.blackKingCoords;
import static utils.KingManager.whiteKingCoords;


public class Game {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";




    public static int moveCount = 0;
    public static Color moveColor = Color.WHITE;

    private boolean movePermisson;


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
                        .valueOf((move.charAt(0) + "")
                                .toUpperCase())
                        , Character.getNumericValue(move.charAt(1)));
                to = new Coordinates(File
                        .valueOf((move.charAt(3) + "")
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

            if (piece.getClass().getSimpleName().equals("Pawn")
                    && board.isSquareEmpty(to) && this.canPawnRotate(piece, to)
            ) {
                movePermisson = true;
            }
            try {
                if (!movePermisson && piece.isMoveInvalidForThisType(to)) {
                    System.out.println(ANSI_RED + piece.getClass().getSimpleName() +
                            " can't move like that" + ANSI_RESET);
                    movePermisson = false;

                    continue;
                }
            } catch (NullPointerException ne) {
                System.out.println(ANSI_RED + "there is empty from where u wanna move" + ANSI_RESET);
                movePermisson = false;

                continue;
            }
            try {
                board.isMoveValidOnBoard(piece,from, to);
            } catch (RuntimeException re) {
                System.out.println(ANSI_RED + re.getMessage() + ANSI_RESET);
                movePermisson = false;
                continue;

            }


            if (board.didIPutMyselfInCheck(piece,from, to)) {
                System.out.println(ANSI_RED + "Mind ur king, trash" + ANSI_RESET);
                movePermisson = false;
                continue;
            }


            if (piece.getClass().getSimpleName().equals("King")) {
                if (moveColor == Color.WHITE) {
                    whiteKingCoords = to;
                } else {
                    blackKingCoords = to;

                }

            }

            board.commitMove(piece,from,to);

            System.out.println(whiteKingCoords);
            System.out.println(blackKingCoords);
            movePermisson = false;

            moveCount++;
            moveColor = moveCount % 2 == 0 ? Color.WHITE : Color.BLACK;


        }


    }

    private boolean canPawnRotate(Piece piece, Coordinates to) {
        int possibleDistance = moveCount < 2 ? 2 : 1;

        int fileFrom = piece.coordinates.file.ordinal(),
                fileTo = to.file.ordinal(),
                rankFrom = piece.coordinates.rank,
                rankTo = to.rank;
        Color color = piece.color;

        boolean fileCheck = fileTo == fileFrom;
        boolean rankCheck = color == Color.WHITE ?
                rankTo == rankFrom + possibleDistance ||
                        rankTo == rankFrom + 1 :
                rankTo == rankFrom - possibleDistance
                        || rankTo == rankFrom - 1;

        return fileCheck && rankCheck;


    }

}







