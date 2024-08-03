import pieces.*;

import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
    Board board = new Board();
    BoardConsoleView view = new BoardConsoleView();
//    view.render(board);


        Coordinates from = new Coordinates(File.B,2);
        Coordinates to = new Coordinates(File.E,5);

        Rook rook = new Rook(Color.BLACK,new Coordinates(File.C,2));
        List<Coordinates> list =
                (rook.everyStepToPoint(rook,new Coordinates(File.B,2)));
        System.out.println("ROOK:");
        for (Coordinates c : list) {
            System.out.print(c.file);
            System.out.print(c.rank);
            System.out.println();
        }

        Bishop bishop = new Bishop(Color.BLACK,new Coordinates(File.F,6));
        List<Coordinates> list2 =
                (bishop.everyStepToPoint(bishop,new Coordinates(File.C,3)));
        System.out.println("BISHOP:");
        for (Coordinates c : list2) {
            System.out.print(c.file);
            System.out.print(c.rank);
            System.out.println();
        }



    }
    // пишется в кнсоль координаты из которых мы хотим, и кординаты куда мы хотим,
    // проверяется, есть ли в этих координатах фигура, и если есть, то можем ли мы
        /*
        переместить фигуру на те координаты, если первые коориданты в мап
        обретают значение null, а вторые обретают фигуру


         */
}