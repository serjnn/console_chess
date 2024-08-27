package other;

import pieces.*;

import java.util.*;
import java.util.stream.Collectors;

public class Board {


    private final Map<Coordinates, Piece> map = new HashMap<>();

    private Set<Coordinates> whites = new HashSet<>();

    private Set<Coordinates> blacks = new HashSet<>();


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
        setPiece(new Coordinates(File.H, 8), new Rook(Color.BLACK, new Coordinates(File.H, 8)));

        //knights
        setPiece(new Coordinates(File.B, 1), new Knight(Color.WHITE, new Coordinates(File.B, 1)));
        setPiece(new Coordinates(File.F, 3), new Knight(Color.WHITE, new Coordinates(File.G, 1)));
        setPiece(new Coordinates(File.B, 8), new Knight(Color.BLACK, new Coordinates(File.B, 8)));
        setPiece(new Coordinates(File.G, 8), new Knight(Color.BLACK, new Coordinates(File.G, 8)));

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

    public void isMoveValid(Coordinates from, Coordinates to) throws
            RuntimeException {
        try {
            if (getPieceColor(from) != Game.moveColor) {
                throw new RuntimeException("that piece isn't yours");

            }
        } catch (NullPointerException np) {
            throw new RuntimeException("there is empty from where u wanna move"
            );

        }

        if (!isSquareEmpty(to) && !isItEnemy(to, Game.moveColor)) {

            throw new RuntimeException("u cant beat ur pieces");

        }


        if (to.rank < 1 || to.rank > 8) {
            throw new RuntimeException("u cant go outside the map");

        }
        Piece piece = getPiece(from);


        try {
            if (piece.getClass().getSimpleName().equals("Pawn"))
                if (from.file != to.file && !(isItEnemy(to, Game.moveColor))
                        || from.file == to.file && !isSquareEmpty(to)) {
                    throw new RuntimeException("this pawn cant move that way");


                }
        } catch (NullPointerException np) {
            throw new RuntimeException("u cant move like that cuz pawn is not attacking");
        }
        List<Coordinates> steps = piece.everyStepToPoint(to);
        if (!isWayToPointEmpty(steps)) {
            throw new RuntimeException("u cant go through piece" );
        }

//        throw new NumberFormatException();

    }


    private boolean isWayToPointEmpty(List<Coordinates> steps) {
        steps = steps.subList(0, steps.size() - 1);
        for (Coordinates cords : steps) {
            if (map.containsKey(cords)) {
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
            whites = whites.stream().filter(map::containsKey).collect(Collectors.toSet());

        }
        else {
            blacks.add(to);
            whites.remove(to);
            blacks = blacks.stream().filter(map::containsKey).collect(Collectors.toSet());
        }
        System.out.println(whites.size() + " whites " + whites);
        System.out.println("blacks " + blacks);
    }

    public boolean isUnderCheck() {
        boolean flag = false;
        if (Game.moveColor == Color.BLACK) {
            System.out.println("whiteeeeeeeeeeeeeeeeeeeeeeee");

            for (Coordinates cords : whites) {
                System.out.println(cords + " cooooords");
                Piece piece = map.get(cords);
                if (!piece.isMoveInvalidForThisType(Game.blackKingCoords)) flag = true;
                try {
                    Game.moveColor = Color.WHITE;
                    isMoveValid(cords, Game.blackKingCoords);
                    Game.moveColor = Color.BLACK;
                } catch (RuntimeException re) {
                    System.out.println(re.getMessage());
                    flag = false;
                }



            }
            return flag;
        } else {
            System.out.println("blackkkkkkkkkkkkkkkk");
            for (Coordinates cords : blacks) {
                Piece piece = map.get(cords);
                if (!piece.isMoveInvalidForThisType(Game.whiteKingCoords)) flag = true;

                try {
                    isMoveValid(cords, Game.whiteKingCoords);
                } catch (RuntimeException re) {
                    flag = false;
                }


            }


            return flag;
        }
    }

}
