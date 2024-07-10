import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Map<Integer,Integer> map = new HashMap<>();
        map.put(3,0);
        map.put(4,0);
        System.out.println(map);

        map.put(3, map.get(3) + 1 );
        System.out.println(map);

    }
    // пишется в кнсоль координаты из которых мы хотим, и кординаты куда мы хотим,
    // проверяется, есть ли в этих координатах фигура, и если есть, то можем ли мы
        /*
        переместить фигуру на те координаты, если первые коориданты в мап
        обретают значение null, а вторые обретают фигуру


         */
}