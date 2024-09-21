package other;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
//        Rook rook = new Rook(Color.BLACK, new Coordinates(File.A, 6));
//        List<Coordinates> list =
//                (rook.everyStepToPoint(new Coordinates(File.A, 2)));
//        System.out.println(list);



        /*
выводится доска

читаются координаты

вычисляется цвет хода

идут проверки

 board.removePieceFromSquare(from);
            board.setPiece(to, piece);
            board.addToActive(to);

movecount++

проверка на шах

ставится или не ставится checkRelease
         */

        //
//
//

//
//        Bishop bishop = new Bishop(Color.BLACK,new Coordinates(File.H,8));
//        List<Coordinates> list2 =
//                (bishop.everyStepToPoint(new Coordinates(File.A,1 )));
//        System.out.println(list2);


        Board board = new Board();
        board.setupDefault();
        Game game = new Game();
        game.gameLoop(board);
//

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



        //        for (Coordinates c : list2) {
//            System.out.print(c.file);
//            System.out.print(c.rank);
//            System.out.println();
//        }

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