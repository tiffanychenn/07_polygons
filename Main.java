import java.util.*;

public class Main {

    public static void main(String[] args){
        Image i = new Image(new int[] {255, 255, 255});
        EdgeMatrix edge = new EdgeMatrix(4, 4);
        Matrix trans = new Matrix(4, 4);
        PolygonMatrix poly = new PolygonMatrix(4, 4);
        ArrayList<String> commands = Parser.parse("script1");
        Parser.execute(edge, trans, poly, commands, i);
    }

}