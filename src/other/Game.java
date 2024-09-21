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

    public static int moveCount = 0;
    public static Color moveColor = Color.WHITE;
    public static boolean underCheck;
    public static boolean checkRelease = false;
    private boolean movePermisson;
    private Coordinates prevWhiteKingCoords;
    private Coordinates prevBlackKingCoords;


    public void gameLoop(Board board) {


        while (true) {


            /// проврка на шах, если есть то уменьшаем move count на 1
            System.out.println();


            BoardConsoleView view = new BoardConsoleView();
            view.render(board);
            Scanner scanner = new Scanner(System.in);


            String move = scanner.nextLine();

            Coordinates from = null;
            Coordinates to = null;
            try {
                from = new Coordinates(File
                        .valueOf((move.charAt(0) + "")
                                .toUpperCase())
                        , Character.getNumericValue(move.charAt(1)));
                to = new Coordinates(File
                        .valueOf((move.charAt(3) + "")
                                .toUpperCase())
                        , Character.getNumericValue(move.charAt(4)));
            } catch (IllegalArgumentException e) {
                System.out.println(ANSI_RED + "Please enter correct coords" + ANSI_RESET);
                continue;
            }

            Piece piece = board.getPiece(from);

            if (piece.getClass().getSimpleName().equals("Pawn")
            && board.isSquareEmpty(to) && this.canPawnRotate(piece,to)
            ) {
        movePermisson = true;
            }

            // first check
            try {
                if (!movePermisson &&  piece.isMoveInvalidForThisType(to)) {
                    System.out.println(ANSI_RED + piece.getClass().getSimpleName() +
                            " can't move like that" + ANSI_RESET);
                    continue;
                }
            } catch (NullPointerException ne) {
                System.out.println(ANSI_RED + "there is empty from where u wanna move" + ANSI_RESET);
                continue;
            }

            // second check
            try {
                board.isMoveValidOnBoard(from, to);
            } catch (RuntimeException re) {
                System.out.println(ANSI_RED + re.getMessage() + ANSI_RESET);
                continue;

            }



            if (board.didIPutMyselfInCheck(piece,from,to)) {
                System.out.println(ANSI_RED + "Mind ur king, trash" + ANSI_RESET);
                continue;
            }


            // fourth check
            if (piece.getClass().getSimpleName().equals("King")) {
                if (moveColor == Color.WHITE) {
                    prevWhiteKingCoords = whiteKingCoords;
                    whiteKingCoords = to;


                } else {
                    prevBlackKingCoords = blackKingCoords;
                    blackKingCoords = to;

                }

            }


            // COMMIT MOVE

            board.removePieceFromSquare(from);
            board.setPiece(to, piece);


//            if (checkRelease){
//                if (board.isWhiteKingUnderCheck() || board.isBlackKingUnderCheck()) {
//                    System.out.println(ANSI_RED + "U ARE UNDER CHECK" + ANSI_RESET);
//                    board.setPiece(from,piece); // move cancel
//                    board.removePieceFromSquare(to);
//                    blackKingCoords = prevBlackKingCoords;
//                    whiteKingCoords = prevWhiteKingCoords;
//
//                    continue;
//                }
//                else {checkRelease = false;
//                selfCheck = false;}
//            }

            board.addToActive(to);



            movePermisson = false;
            // проверка на шах

            moveCount++;
            moveColor = moveCount % 2 == 0 ? Color.WHITE : Color.BLACK;



        }


    }

    private boolean canPawnRotate(Piece piece, Coordinates to) {
        int possibleDistance = moveCount < 2 ? 2:1;

        int fileFrom = piece.coordinates.file.ordinal(),
                fileTo = to.file.ordinal(),
                rankFrom = piece.coordinates.rank,
                rankTo = to.rank;
        Color color = piece.color;

        boolean fileCheck = fileTo == fileFrom;
        boolean rankCheck = color == Color.WHITE ?
                rankTo == rankFrom + possibleDistance  ||
                        rankTo == rankFrom + 1 :
                rankTo == rankFrom - possibleDistance
                ||                rankTo == rankFrom - 1

                ;

        return fileCheck && rankCheck;


    }

}







