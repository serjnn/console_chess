package other;

import pieces.*;

import java.util.*;
import java.util.stream.Collectors;

import static other.Game.blackKingCoords;

public class Board {


    private final Map<Coordinates, Piece> map = new HashMap<>();

    private Set<Coordinates> whites = new HashSet<>();

    private Set<Coordinates> blacks = new HashSet<>();


    public Set<Coordinates> getWhites() {
        return whites;
    }

    public Set<Coordinates> getBlacks() {
        return blacks;
    }

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

        //rooks
        setPiece(new Coordinates(File.A, 1), new Rook(Color.WHITE, new Coordinates(File.A, 1)));
        setPiece(new Coordinates(File.H, 1), new Rook(Color.WHITE, new Coordinates(File.H, 1)));
        setPiece(new Coordinates(File.A, 8), new Rook(Color.BLACK, new Coordinates(File.A, 8)));
        setPiece(new Coordinates(File.D, 6), new Rook(Color.BLACK, new Coordinates(File.H, 8)));

        //knights
        setPiece(new Coordinates(File.B, 1), new Knight(Color.WHITE, new Coordinates(File.B, 1)));
        setPiece(new Coordinates(File.F, 1), new Knight(Color.WHITE, new Coordinates(File.G, 1)));
        setPiece(new Coordinates(File.B, 8), new Knight(Color.BLACK, new Coordinates(File.B, 8)));
        setPiece(new Coordinates(File.E, 5), new Knight(Color.BLACK, new Coordinates(File.G, 8)));

        //bishops
        setPiece(new Coordinates(File.C, 1), new Bishop(Color.WHITE, new Coordinates(File.C, 1)));
        setPiece(new Coordinates(File.F, 1), new Bishop(Color.WHITE, new Coordinates(File.F, 1)));
        setPiece(new Coordinates(File.C, 8), new Bishop(Color.BLACK, new Coordinates(File.C, 8)));
        setPiece(new Coordinates(File.F, 8), new Bishop(Color.BLACK, new Coordinates(File.F, 8)));

        //queens
        setPiece(new Coordinates(File.D, 3), new Queen(Color.WHITE, new Coordinates(File.D, 1)));
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
        if (Game.moveColor == Color.WHITE) {
            whites.add(to);
            blacks.remove(to);

        } else {
            blacks.add(to);
            whites.remove(to);
        }

    }


    public boolean isWhiteKingUnderCheck() {

        boolean typeFlag = false, boardFlag = true;

        blacks = blacks.stream().filter(map::containsKey).collect(Collectors.toSet());
        for (Coordinates cords : blacks) {
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
        whites = whites.stream().filter(map::containsKey).collect(Collectors.toSet());
        for (Coordinates cords : whites) {
            boolean typeFlag = false, boardFlag = true;

            Piece piece = map.get(cords);
            if (!piece.isMoveInvalidForThisType(blackKingCoords)) {
                typeFlag = true;
            }
            try {
                isMoveValidOnBoard(cords, blackKingCoords);
            } catch (ArithmeticException ae) {
                System.out.print("");
            } catch (RuntimeException re) {
                System.out.println("The problem putting black in check is " + re.getMessage());
                boardFlag = false;
            }
            System.out.println(piece);
            System.out.println(piece.everyStepToPoint(blackKingCoords));
            System.out.println(typeFlag + "  type");
            System.out.println(boardFlag + "  board");
            if (boardFlag && typeFlag) {
                return true;
            }
        }
        return false;

    }


    public boolean didIPutMyselfInCheck(Piece piece, Coordinates from, Coordinates to) {
        Piece standedPiece = map.get(to);


        map.remove(from);
        setPiece(to, piece);


        boolean result = Game.moveColor == Color.WHITE ?
                isWhiteKingUnderCheck() : isBlackKingUnderCheck();

        map.remove(to);
        setPiece(from, piece);
        if (standedPiece != null) {
            setPiece(to, standedPiece);
        }
        return result;

    }
}
