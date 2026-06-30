package utils;

import pieces.enums.Color;
import pieces.Coordinates;
import pieces.enums.File;
import exceptions.ChessException;

import java.util.Scanner;

public class Game {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    private int moveCount = 0;
    private Color moveColor = Color.WHITE;

    public Game() {
    }

    @Deprecated
    public Game(Object kingManager, Object pawnManager) {
        // Kept for backward compatibility
    }

    public void gameLoop(Board board) {
        Scanner scanner = new Scanner(System.in);
        BoardConsoleRenderer view = new BoardConsoleRenderer();

        while (true) {
            view.render(board, moveColor);

            String move = scanner.nextLine().trim();
            if (move.equalsIgnoreCase("exit") || move.equalsIgnoreCase("quit")) {
                break;
            }

            Coordinates from;
            Coordinates to;
            try {
                // Normalize input by removing all spaces, tabs, and hyphens, and convert to lowercase.
                String cleanMove = move.replaceAll("\\s+", "").replace("-", "").toLowerCase();
                if (cleanMove.length() != 4) {
                    throw new ChessException("Please enter your move in format 'e2 e4' or 'e2e4'");
                }

                from = parseCoordinates(cleanMove.charAt(0), cleanMove.charAt(1));
                to = parseCoordinates(cleanMove.charAt(2), cleanMove.charAt(3));
            } catch (ChessException ce) {
                System.out.println(ANSI_RED + ce.getMessage() + ANSI_RESET);
                continue;
            } catch (Exception e) {
                System.out.println(ANSI_RED + "Please enter correct coordinates" + ANSI_RESET);
                continue;
            }

            try {
                // Validate move rule correctness using stateless validator
                MoveValidator.validateMove(board, moveColor, from, to);

                // Verify if move puts or leaves current player's King in check
                if (board.wouldMovePutKingInCheck(from, to, moveColor)) {
                    System.out.println(ANSI_RED + "You are under check" + ANSI_RESET);
                    continue;
                }

                // If valid, commit move (updates piece location, handles castling rook and pawn promotion)
                board.commitMove(from, to);

                // Increment move and alternate turn color
                moveCount++;
                moveColor = (moveCount % 2 == 0) ? Color.WHITE : Color.BLACK;

            } catch (ChessException ce) {
                System.out.println(ANSI_RED + ce.getMessage() + ANSI_RESET);
            }
        }
    }

    private Coordinates parseCoordinates(char fileChar, char rankChar) {
        String fileStr = String.valueOf(fileChar).toUpperCase();
        File file = File.valueOf(fileStr);
        int rank = Character.getNumericValue(rankChar);
        if (rank < 1 || rank > 8) {
            throw new IllegalArgumentException();
        }
        return new Coordinates(file, rank);
    }
}
