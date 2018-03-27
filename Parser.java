import java.util.*;
import java.io.*;
import java.lang.*;

public class Parser {

    public static ArrayList<String> parse(String filename){
        try {
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            ArrayList<String> stuff = new ArrayList<String>();
            while (sc.hasNext()){
                stuff.add(sc.nextLine());
            }
            sc.close();
            //System.out.println(stuff);
            return stuff;
        }
        catch (Exception e) {
            System.out.println(e);
            return new ArrayList<String>();
        }
    }

    public static void execute(EdgeMatrix edge, Matrix trans, PolygonMatrix poly, ArrayList<String> commands, Image i){
        int n = 0;
        while (n < commands.size()){
            String c = commands.get(n);
            System.out.println(c);
            //System.out.println(trans);
            if (c.equals("line")){
                n ++;
                String[] args = commands.get(n).split(" ");
                double[] point0 = new double[] {Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), 1};
                double[] point1 = new double[] {Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]), 1};
                edge.addEdge(point0, point1);
            }
            else if (c.equals("circle")){
                n ++;
                String[] args = commands.get(n).split(" ");
                edge.addCircle(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
            }
            else if (c.equals("bezier")){
                n ++;
                String[] args = commands.get(n).split(" ");
                edge.addCurve(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]), Double.parseDouble(args[7]), "bezier");
            }
            else if (c.equals("hermite")){
                n ++;
                String[] args = commands.get(n).split(" ");
                edge.addCurve(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]), Double.parseDouble(args[7]), "hermite");
            }
            // START CHANGING STUFF HERE!
            else if (c.equals("box")){
                n ++;
                String[] args = commands.get(n).split(" ");
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);
                double w = Double.parseDouble(args[3]);
                double h = Double.parseDouble(args[4]);
                double d = Double.parseDouble(args[5]);
                poly.addPolygon(new double[] {x, y, z, 1}, new double[] {x + w, y, z, 1}, new double[] {x, y - h, z, 1});
                poly.addPolygon(new double[] {x, y, z - d, 1}, new double[] {x + w, y, z - d, 1}, new double[] {x, y - h, z - d, 1});
                poly.addPolygon(new double[] {x + w, y - h, z, 1}, new double[] {x + w, y, z, 1}, new double[] {x, y - h, z, 1});
                poly.addPolygon(new double[] {x + w, y - h, z - d, 1}, new double[] {x + w, y, z - d, 1}, new double[] {x, y - h, z - d, 1});
                poly.addPolygon(new double[] {x, y, z, 1}, new double[] {x, y, z - d, 1}, new double[] {x, y - h, z, 1});
                poly.addPolygon(new double[] {x, y, z - d, 1}, new double[] {x, y - h, z, 1}, new double[] {x, y - h, z - d, 1});
                poly.addPolygon(new double[] {x + w, y, z, 1}, new double[] {x + w, y, z - d, 1}, new double[] {x + w, y - h, z, 1});
                poly.addPolygon(new double[] {x + w, y, z - d, 1}, new double[] {x + w, y - h, z, 1}, new double[] {x + w, y - h, z - d, 1});
                poly.addPolygon(new double[] {x, y, z, 1}, new double[] {x + w, y, z - d, 1}, new double[] {x + w, y, z, 1});
                poly.addPolygon(new double[] {x, y, z, 1}, new double[] {x, y, z - d, 1}, new double[] {x + w, y, z - d, 1});
                poly.addPolygon(new double[] {x, y - h, z, 1}, new double[] {x + w, y - h, z - d, 1}, new double[] {x + w, y - h, z, 1});
                poly.addPolygon(new double[] {x, y - h, z, 1}, new double[] {x, y - h, z - d, 1}, new double[] {x + w, y - h, z - d, 1});
            }
            /*else if (c.equals("sphere")){
                n ++;
                String[] args = commands.get(n).split(" ");
                Matrix points = Matrix.generateSphere(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                edge.add3D(points);
            }
            else if (c.equals("torus")){
                n ++;
                String[] args = commands.get(n).split(" ");
                Matrix points = Matrix.generateTorus(Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
                edge.add3D(points);
            }*/
            // END CHANGING STUFF HERE!
            else if (c.equals("clear")){
                edge = new EdgeMatrix(4, 4);
                poly = new PolygonMatrix(4, 4);
            }
            else if (c.equals("ident")){
                trans.ident();
            }
            else if (c.equals("scale")){
                n ++;
                String[] args = commands.get(n).split(" ");
                Matrix scale = new Matrix(4, 4);
                scale.addPoint(new double[] {Double.parseDouble(args[0]), 0, 0, 0});
                scale.addPoint(new double[] {0, Double.parseDouble(args[1]), 0, 0});
                scale.addPoint(new double[] {0, 0, Double.parseDouble(args[2]), 0});
                scale.addPoint(new double[] {0, 0, 0, 1});
                trans = Matrix.multi(scale, trans);
                //System.out.println(trans);
            }
            else if (c.equals("move")){
                n ++;
                String[] args = commands.get(n).split(" ");
                Matrix scale = new Matrix(4, 4);
                scale.addPoint(new double[] {1, 0, 0, 0});
                scale.addPoint(new double[] {0, 1, 0, 0});
                scale.addPoint(new double[] {0, 0, 1, 0});
                scale.addPoint(new double[] {Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), 1});
                trans = Matrix.multi(scale, trans);
                //System.out.println(trans);
            }
            else if (c.equals("rotate")){
                n ++;
                String[] args = commands.get(n).split(" ");
                Matrix scale = new Matrix(4, 4);
                double theta = Math.toRadians(Double.parseDouble(args[1]));
                double sin = Math.sin(theta);
                double cos = Math.cos(theta);
                if (args[0].equals("x")){
                    scale.addPoint(new double[] {1, 0, 0, 0});
                    scale.addPoint(new double[] {0, cos, sin, 0});
                    scale.addPoint(new double[] {0, -1 * sin, cos, 0});
                    scale.addPoint(new double[] {0, 0, 0, 1});
                }
                else if (args[0].equals("y")){
                    scale.addPoint(new double[] {cos, 0, -1 * sin, 0});
                    scale.addPoint(new double[] {0, 1, 0, 0});
                    scale.addPoint(new double[] {sin, 0, cos, 0});
                    scale.addPoint(new double[] {0, 0, 0, 1});
                }
                else {
                    scale.addPoint(new double[] {cos, sin, 0, 0});
                    scale.addPoint(new double[] {-1 * sin, cos, 0, 0});
                    scale.addPoint(new double[] {0, 0, 1, 0});
                    scale.addPoint(new double[] {0, 0, 0, 1});
                }
                trans = Matrix.multi(scale, trans);
                //System.out.println(trans);
            }
            else if (c.equals("apply")){
                edge = Matrix.multi(trans, edge);
                poly = Matrix.multi(trans, poly);
                //System.out.println(edge);
            }
            else if (c.equals("display")){
            	i = new Image();
                Random rand = new Random();
                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);
                Drawing.drawlines(edge, i, new int[] {r,g,b});
                Drawing.drawpolygons(poly, i, new int[] {r,g,b});
                i.draw();
                Runtime run = Runtime.getRuntime();
                try {
                    run.exec("display image.ppm");
                    Thread.sleep(1000);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
            else if (c.equals("save")){
            	n ++;
            	i = new Image();
                Random rand = new Random();
                int r = rand.nextInt(255);
                int g = rand.nextInt(255);
                int b = rand.nextInt(255);
                Drawing.drawlines(edge, i, new int[] {r,g,b});
                i.draw();
                Runtime run = Runtime.getRuntime();
                try {
                    run.exec("convert image.ppm " + commands.get(n));
                    Thread.sleep(1000);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
            else if (c.equals("quit")){
            	return;
            }
            n ++;
        }
    }

}
