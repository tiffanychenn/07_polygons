import java.util.*;

public class Main {

    public static void main(String[] args){
        Image i = new Image(new int[] {255, 255, 255});
        Matrix edge = new Matrix(4, 4);
        Matrix trans = new Matrix(4, 4);
        ArrayList<String> commands = Parser.parse("script");
        Parser.execute(edge, trans, commands, i);
        edge = new Matrix(4, 4);
        trans = new Matrix(4, 4);
        commands = Parser.parse("script1");
        Parser.execute(edge, trans, commands, i);
    }

}