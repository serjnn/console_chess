import pieces.Coordinates;
import pieces.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
    Board board = new Board();
    BoardConsoleView view = new BoardConsoleView();
    view.render(board);


        Coordinates from = new Coordinates(File.B,2);
        Coordinates to = new Coordinates(File.E,5);




    }
    // пишется в кнсоль координаты из которых мы хотим, и кординаты куда мы хотим,
    // проверяется, есть ли в этих координатах фигура, и если есть, то можем ли мы
        /*
        переместить фигуру на те координаты, если первые коориданты в мап
        обретают значение null, а вторые обретают фигуру


         */
}