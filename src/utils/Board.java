package utils;

import managers.KingManager;
import pieces.*;

import java.util.*;
import java.util.stream.Collectors;

import static managers.KingManager.*;
import static utils.Game.moveCount;

public class Board {


    private final Map<Coordinates, Piece> map = new HashMap<>();

    private Set<Coordinates> activeWhites = new HashSet<>();

    private Set<Coordinates> activeBlacks = new HashSet<>();


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
        setPiece(new Coordinates(File.G, 3), new Knight(Color.WHITE, new Coordinates(File.B, 1)));
        setPiece(new Coordinates(File.F, 1), new Knight(Color.WHITE, new Coordinates(File.G, 1)));
        setPiece(new Coordinates(File.B, 8), new Knight(Color.BLACK, new Coordinates(File.B, 8)));
        setPiece(new Coordinates(File.E, 5), new Knight(Color.BLACK, new Coordinates(File.G, 8)));

        //bishops
        setPiece(new Coordinates(File.D, 3), new Bishop(Color.WHITE, new Coordinates(File.C, 1)));
        setPiece(new Coordinates(File.C, 1), new Bishop(Color.WHITE, new Coordinates(File.F, 1)));
        setPiece(new Coordinates(File.C, 8), new Bishop(Color.BLACK, new Coordinates(File.C, 8)));
        setPiece(new Coordinates(File.F, 8), new Bishop(Color.BLACK, new Coordinates(File.F, 8)));

        //queens
        setPiece(new Coordinates(File.H, 6), new Queen(Color.WHITE, new Coordinates(File.D, 1)));
        setPiece(new Coordinates(File.D, 8), new Queen(Color.BLACK, new Coordinates(File.D, 8)));

        //kings

        setPiece(new Coordinates(File.E, 1), new King(Color.WHITE, new Coordinates(File.E, 1)));
        setPiece(new Coordinates(File.E, 8), new King(Color.BLACK, new Coordinates(File.E, 8)));
    }


    private Color getPieceColor(Coordinates from) {
        return map.get(from).color;
    }

    private boolean isItEnemy(Coordinates to, Color moveColor) {
        return map.get(to).color != moveColor;

    }

    public void removePieceFromSquare(Coordinates coordinates) {
        map.remove(coordinates);

    }

    public void isMoveValidOnBoard(Piece piece, Coordinates to) throws
            RuntimeException {
        if (castling) {
            castling = false;
            return;
        }
        List<Coordinates> steps = piece.everyStepToPoint(to);
        if (!isWayToPointEmpty(steps)) {
            throw new RuntimeException("You cant go through piece");
        }

        if (!isSquareEmpty(to) && !isItEnemy(to, Game.moveColor)) {

            throw new ArithmeticException("You cant beat ur pieces");

        }


        if (to.rank < 1 || to.rank > 8) {
            throw new RuntimeException("You cant go outside the map");

        }


        try {
            if (piece.getClass().getSimpleName().equals("Pawn"))
                if (piece.coordinates.file != to.file && !(isItEnemy(to, Game.moveColor))
                        || piece.coordinates.file == to.file && !isSquareEmpty(to)) {
                    throw new RuntimeException("this pawn cant move that way");


                }
        } catch (NullPointerException np) {
            throw new RuntimeException("You cant move like that cuz pawn is not attacking");
        }

        try {
            if (getPieceColor(piece.coordinates) != Game.moveColor) {
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

    private void addToActive(Coordinates to) {
        Piece piece = map.get(to);
        if (piece.color == Color.WHITE) {
            activeWhites.add(to);
            activeBlacks.remove(to);

        } else {
            activeBlacks.add(to);
            activeWhites.remove(to);
        }

    }


    private boolean isWhiteKingUnderCheck() {

        boolean typeFlag = false, boardFlag = true;

        activeBlacks = activeBlacks.stream().filter(map::containsKey).collect(Collectors.toSet());
        for (Coordinates cords : activeBlacks) {
            Piece piece = map.get(cords);
            if (!piece.isMoveInvalidForThisType(whiteKingCoords)) {
                typeFlag = true;
            }

            try {
                isMoveValidOnBoard(piece,  whiteKingCoords);
            } catch (ArithmeticException ae) {
                System.out.print("");
            } catch (RuntimeException re) {
                boardFlag = false;
            }

            if (boardFlag && typeFlag) {
                return true;
            }
        }
        return false;
    }

    private boolean isBlackKingUnderCheck() {
        activeWhites = activeWhites.stream()
                .filter(map::containsKey)
                .collect(Collectors.toSet());
        for (Coordinates cords : activeWhites) {
            boolean typeFlag = false, boardFlag = true;

            Piece piece = map.get(cords);
            if (piece.color == Color.BLACK) {
                continue;
            }
            if (!piece.isMoveInvalidForThisType(blackKingCoords)) {
                typeFlag = true;
            }
            try {
                isMoveValidOnBoard(piece,  blackKingCoords);
            } catch (ArithmeticException ae) {
                System.out.print("");
            } catch (RuntimeException re) {
                boardFlag = false;
            }

            if (boardFlag && typeFlag) {
                return true;
            }
        }
        return false;

    }


    public boolean amIUnderCheck(Piece piece, Coordinates from, Coordinates to) {
        Piece previousPiece = map.get(to);

        if (piece.getClass().getSimpleName().equals("King")) {
            KingManager.mockKingCoords(piece.color, from, to);
        }

        mockMove(piece, from, to);


        boolean result = Game.moveColor == Color.WHITE ?
                isWhiteKingUnderCheck() : isBlackKingUnderCheck();


        if (piece.getClass().getSimpleName().equals("King")) {
            KingManager.rollbackKingCoords(piece.color);
        }
        rollback(piece, from, to);

        if (previousPiece != null) {
            setPiece(to, previousPiece); // revive piece that could be slain by mocking piece
        }
        return result;

    }


    private void rollback(Piece piece, Coordinates from, Coordinates to) {
        map.remove(to);
        setPiece(from, piece);
    }

    private void mockMove(Piece piece, Coordinates from, Coordinates to) {
        map.remove(from);
        setPiece(to, piece);
    }

    public void commitMove(Piece piece, Coordinates from, Coordinates to) {

        removePieceFromSquare(from);
        setPiece(to, piece);
        addToActive(to);
        moveCount++;
        Game.moveColor = moveCount % 2 == 0 ? Color.WHITE : Color.BLACK;
    }


}
