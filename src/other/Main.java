package other;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {


//        Bishop bishop = new Bishop(Color.WHITE,new Coordinates(File.B, 3));
//        System.out.println(bishop.everyStepToPoint(new Coordinates(File.D,5)));
//
//
//Pawn pawn = new Pawn(Color.WHITE,new Coordinates(File.B, 3));
//        System.out.println(pawn.everyStepToPoint(new Coordinates(File.B,4)));
        Board board = new Board();
        board.setupDefault();
        Game game = new Game();
        game.gameLoop(board);

//        Pawn pawn = new Pawn(Color.BLACK,new Coordinates(File.A,2));
//        List<Coordinates> list =
//                (pawn.everyStepToPoint(pawn,new Coordinates(File.B,1)));
//        System.out.println("PAWN:");
//        for (Coordinates c : list) {
//            System.out.print(c.file);
//            System.out.print(c.rank);
//            System.out.println();
//        }


//        King king = new King(Color.BLACK,new Coordinates(File.A,2));
//        List<Coordinates> list =
//                (king.everyStepToPoint(king,new Coordinates(File.B,3)));
//        System.out.println("KING:");
//        for (Coordinates c : list) {
//            System.out.print(c.file);
//            System.out.print(c.rank);
//            System.out.println();
//        }

//        Knight knight = new Knight(Color.BLACK,new Coordinates(File.G,5));
//        List<Coordinates> list2 =
//                (knight.everyStepToPoint(knight,new Coordinates(File.H,7)));
//        System.out.println("KNIGHT:");
//        for (Coordinates c : list2) {
//            System.out.print(c.file);
//            System.out.print(c.rank);
//            System.out.println();
//        }


//
//
//
//        Rook rook = new Rook(Color.BLACK,new Coordinates(File.A,6));
//        List<Coordinates> list =
//                (rook.everyStepToPoint(rook,new Coordinates(File.A,2)));
//        System.out.println("ROOK:");
//        for (Coordinates c : list) {
//            System.out.print(c.file);
//            System.out.print(c.rank);
//            System.out.println();
//        }
//
//        Bishop bishop = new Bishop(Color.BLACK,new Coordinates(File.F,6));
//        List<Coordinates> list2 =
//                (bishop.everyStepToPoint(bishop,new Coordinates(File.C,3)));
//        System.out.println("BISHOP:");
////        for (Coordinates c : list2) {
////            System.out.print(c.file);
////            System.out.print(c.rank);
////            System.out.println();
////        }
//        Queen queen = new Queen(Color.BLACK,new Coordinates(File.A,5));
//        List<Coordinates> list3 =
//                (queen.everyStepToPoint(queen,new Coordinates(File.A,1)));
//        System.out.println("QUEEN:");
//        for (Coordinates c : list3) {
//            System.out.print(c.file);
//            System.out.print(c.rank);
//            System.out.println();
//        }
//
////King king = new King(Color.BLACK, new Coordinates(File.H,8));
////List<Coordinates> res = king.everyStepToPoint(king,new Coordinates(File.H,7));
////        System.out.println(res);
//
    }

}