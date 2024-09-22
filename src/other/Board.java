package other;

import pieces.*;

import java.util.*;
import java.util.stream.Collectors;

public class Board {


    private final Map<Coordinates, Piece> map = new HashMap<>();

    private Set<Coordinates> activeWhites = new HashSet<>();

    private Set<Coordinates> activeBlacks = new HashSet<>();
    private Coordinates tempBlackKingCoords;
    private Coordinates tempWhiteKingCoords;






    public void setPiece(Coordinates coordinates, Piece piece) {
        piece.coordinates = coordinates;
        map.put(coordinates, piece);
    }


    public boolean isSquareEmpty(Coordinates coordinates) {
        return !map.containsKey(coordinates);
    }

    public Piece getPiece(Coordinates coordinates) {
        return map.get(coordinates);

    }


    public void setupDefault() {
        //set pawns
        for (File file : File.values()) {
            setPiece(new Coordinates(file, 2), new Pawn(Color.WHITE, new Coordinates(file, 2)));
            setPiece(new Coordinates(file, 7), new Pawn(Color.BLACK, new Coordinates(file, 7)));
        }
        setPiece(new Coordinates(File.F, 4), new Pawn(Color.WHITE, new Coordinates(File.F, 4)));


        //rooks
        setPiece(new Coordinates(File.E, 3), new Rook(Color.WHITE, new Coordinates(File.A, 1)));
        setPiece(new Coordinates(File.H, 1), new Rook(Color.WHITE, new Coordinates(File.H, 1)));
        setPiece(new Coordinates(File.A, 8), new Rook(Color.BLACK, new Coordinates(File.A, 8)));
        setPiece(new Coordinates(File.D, 6), new Rook(Color.BLACK, new Coordinates(File.H, 8)));

        //knights
        setPiece(new Coordinates(File.G, 3), new Knight(Color.WHITE, new Coordinates(File.B, 1)));
        setPiece(new Coordinates(File.F, 1), new Knight(Color.WHITE, new Coordinates(File.G, 1)));
        setPiece(new Coordinates(File.B, 8), new Knight(Color.BLACK, new Coordinates(File.B, 8)));
        setPiece(new Coordinates(File.E, 5), new Knight(Color.BLACK, new Coordinates(File.G, 8)));

        //bishops
        setPiece(new Coordinates(File.C, 1), new Bishop(Color.WHITE, new Coordinates(File.C, 1)));
        setPiece(new Coordinates(File.F, 1), new Bishop(Color.WHITE, new Coordinates(File.F, 1)));
        setPiece(new Coordinates(File.C, 8), new Bishop(Color.BLACK, new Coordinates(File.C, 8)));
        setPiece(new Coordinates(File.F, 8), new Bishop(Color.BLACK, new Coordinates(File.F, 8)));

        //queens
        setPiece(new Coordinates(File.H, 6), new Queen(Color.WHITE, new Coordinates(File.D, 1)));
        setPiece(new Coordinates(File.D, 8), new Queen(Color.BLACK, new Coordinates(File.D, 8)));

        //kings
        setPiece(new Coordinates(File.E, 1), new King(Color.WHITE, new Coordinates(File.E, 1)));
        setPiece(new Coordinates(File.E, 6), new King(Color.BLACK, new Coordinates(File.E, 8)));
    }


    public Color getPieceColor(Coordinates from) {
        return map.get(from).color;
    }

    public boolean isItEnemy(Coordinates to, Color moveColor) {
        return map.get(to).color != moveColor;

    }

    public void removePieceFromSquare(Coordinates coordinates) {
        map.remove(coordinates);

    }

    public void isMoveValidOnBoard(Coordinates from, Coordinates to) throws
            RuntimeException {
        Piece piece = map.get(from);
        List<Coordinates> steps = piece.everyStepToPoint(to);
        if (!isWayToPointEmpty(steps)) {
            throw new RuntimeException("u cant go through piece");
        }

        if (!isSquareEmpty(to) && !isItEnemy(to, Game.moveColor)) {

            throw new ArithmeticException("u cant beat ur pieces");

        }


        if (to.rank < 1 || to.rank > 8) {
            throw new RuntimeException("u cant go outside the map");

        }


        try {
            if (piece.getClass().getSimpleName().equals("Pawn"))
                if (from.file != to.file && !(isItEnemy(to, Game.moveColor))
                        || from.file == to.file && !isSquareEmpty(to)) {
                    throw new RuntimeException("this pawn cant move that way");


                }
        } catch (NullPointerException np) {
            throw new RuntimeException("u cant move like that cuz pawn is not attacking");
        }

        try {
            if (getPieceColor(from) != Game.moveColor) {
                throw new ArithmeticException("that piece isn't yours");

            }
        } catch (NullPointerException np) {
            throw new RuntimeException("there is empty from where u wanna move"
            );

        }


    }


    private boolean isWayToPointEmpty(List<Coordinates> steps) {
        steps = steps.subList(0, steps.size() - 1);
        for (Coordinates cords : steps) {
            if (map.containsKey(cords)) {
                System.out.println("The obstacle is " + map.get(cords));
                return false;

            }
        }
        return true;

    }


    public boolean isSquareWhite(Coordinates coordinates) {
        return ((coordinates.file.ordinal() + 1) % 2 == 0 && coordinates.rank % 2 == 0) || (
                (coordinates.file.ordinal() + 1) % 2 != 0 && coordinates.rank % 2 != 0
        );
    }

    public void addToActive(Coordinates to) {
        Piece piece = map.get(to);
        if (piece.color == Color.WHITE) {
            activeWhites.add(to);
            activeBlacks.remove(to);

        } else {
            activeBlacks.add(to);
            activeWhites.remove(to);
        }

    }


    public boolean isWhiteKingUnderCheck() {

        boolean typeFlag = false, boardFlag = true;

        activeBlacks = activeBlacks.stream().filter(map::containsKey).collect(Collectors.toSet());
        for (Coordinates cords : activeBlacks) {
            Piece piece = map.get(cords);
            if (!piece.isMoveInvalidForThisType(Game.whiteKingCoords)) {
                typeFlag = true;
            }

            try {
                isMoveValidOnBoard(cords, Game.whiteKingCoords);
            } catch (ArithmeticException ae) {
                System.out.print("");
            } catch (RuntimeException re) {
                System.out.println("The problem putting white in check is " + re.getMessage());
                boardFlag = false;
            }

            if (boardFlag && typeFlag) {
                return true;
            }
        }
        return false;
    }

    public boolean isBlackKingUnderCheck() {
        activeWhites = activeWhites.stream()
                .filter(map::containsKey)
                .collect(Collectors.toSet());
        System.out.println("WHITES    " + activeWhites);

        for (Coordinates cords : activeWhites) {
            boolean typeFlag = false, boardFlag = true;

            Piece piece = map.get(cords);
            if (piece.color == Color.BLACK) {continue;}
            if (!piece.isMoveInvalidForThisType(Game.blackKingCoords)) {
                typeFlag = true;
            }
            try {
                isMoveValidOnBoard(cords, Game.blackKingCoords);
            } catch (ArithmeticException ae) {
                System.out.print("");                //
            } catch (RuntimeException re) {
                System.out.println("The problem putting black in check is " + re.getMessage());
                boardFlag = false;
            }
            System.out.println(map.get(cords) + "    this");
            System.out.println(piece);
            System.out.println(cords);
//            System.out.println(piece.everyStepToPoint(blackKingCoords));
            System.out.println(typeFlag + "  type");
            System.out.println(boardFlag + "  board");
            if (boardFlag && typeFlag) {
                return true;
            }
        }
        return false;

    }



    //TODO refactor this shit
    public boolean didIPutMyselfInCheck(Piece piece, Coordinates from, Coordinates to) {
        Piece standedPiece = map.get(to);

        Piece movingPiece = map.get(from);
        if (movingPiece.getClass().getSimpleName().equals("King")) {
            if (movingPiece.color == Color.BLACK) {
                tempBlackKingCoords = from;
               Game.blackKingCoords = to;
                System.out.println("black KING " + Game.blackKingCoords );

            } else {
                tempWhiteKingCoords = from;
                Game.whiteKingCoords = to;

            }
        }

        map.remove(from);

        setPiece(to, piece);
        System.out.println("to      " + to);
        System.out.println("piece   " + piece);
        System.out.println("map get to   " + map.get(to));


        boolean result = Game.moveColor == Color.WHITE ?
                isWhiteKingUnderCheck() : isBlackKingUnderCheck();


        if (movingPiece.getClass().getSimpleName().equals("King")) {
            if (movingPiece.color == Color.BLACK) {
                Game.blackKingCoords = tempBlackKingCoords;
            } else {
                Game.whiteKingCoords = tempWhiteKingCoords;
            }
        }
        map.remove(to);
        setPiece(from, piece);
        if (standedPiece != null) {
            setPiece(to, standedPiece);
        }
        return result;

    }
}
