/*  
    REMEMBER!!!
    EACH POINT IS A COLUMN, NOT A ROW!
    CHANGE THINGS ACCORDINGLY SHOULD MORE THINGS BE IMPLEMENTED
*/

public class Matrix {

    private double[][] matrix;
    private int point_number;

    public Matrix(int rows, int cols){
        matrix = new double[rows][cols];
    }

    public void ident(){
        if (matrix.length != matrix[0].length){
            System.out.println("This ain't a square matrix!");
            return;
        }
        for (int i = 0; i < matrix.length; i ++){
            for (int j = 0; j < matrix[0].length; j ++){
                if (i == j){
                    matrix[i][j] = 1.0;
                }
                else {
                    matrix[i][j] = 0.0;
                }
            }
        }
        point_number = matrix.length;
    }

    public void addPoint(double[] point){
        if (point.length != matrix.length){
            System.out.println("Point isn't the same size as the matrix.");
            return;
        }
        if (point_number >= matrix[0].length){
            resize();
        }
        for (int i = 0; i < point.length; i ++){
            matrix[i][point_number] = point[i];
        }
        point_number ++;
    }

    public void addEdge(double[] point0, double[] point1){
        addPoint(point0);
        addPoint(point1);
    }

    public void addCircle(double cx, double cy, double cz, double r){
        double step = 0.01;
        for (double t = 0; t < 1; t += step){
            double x0 = r * Math.cos(2 * Math.PI * t) + cx;
            double y0 = r * Math.sin(2 * Math.PI * t) + cy;
            double x1 = r * Math.cos(2 * Math.PI * (t + step)) + cx;
            double y1 = r * Math.sin(2 * Math.PI * (t + step)) + cy;
            addEdge(new double[] {x0, y0, cz, 1}, new double[] {x1, y1, cz, 1});
        }
    }

    public void addCurve(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3, String type){
        Matrix curve = new Matrix(4, 4);
        if (type.equals("hermite")){
            curve.addEdge(new double[] {2, -3, 0, 1}, new double[] {-2, 3, 0, 0});
            curve.addEdge(new double[] {1, -2, 1, 0}, new double[] {1, -1, 0, 0});
        }
        else {
            curve.addEdge(new double[] {-1, 3, -3, 1}, new double[] {3, -6, 3, 0});
            curve.addEdge(new double[] {-3, 3, 0, 0}, new double[] {1, 0, 0, 0});
        }
        Matrix x = new Matrix(4, 4);
        x.addPoint(new double[] {x0, x1, x2, x3});
        Matrix y = new Matrix(4, 4);
        y.addPoint(new double[] {y0, y1, y2, y3});
        Matrix xcoefs = multi(curve, x);
        Matrix ycoefs = multi(curve, y);
        double step = 0.01;
        for (double t = 0; t < 1; t += step){
            double x4 = xcoefs.getNum(0, 0) * Math.pow(t, 3) + xcoefs.getNum(1, 0) * Math.pow(t, 2) + xcoefs.getNum(2, 0) * t + xcoefs.getNum(3, 0);
            double y4 = ycoefs.getNum(0, 0) * Math.pow(t, 3) + ycoefs.getNum(1, 0) * Math.pow(t, 2) + ycoefs.getNum(2, 0) * t + ycoefs.getNum(3, 0);
            double x5 = xcoefs.getNum(0, 0) * Math.pow(t + step, 3) + xcoefs.getNum(1, 0) * Math.pow(t + step, 2) + xcoefs.getNum(2, 0) * (t + step) + xcoefs.getNum(3, 0);
            double y5 = ycoefs.getNum(0, 0) * Math.pow(t + step, 3) + ycoefs.getNum(1, 0) * Math.pow(t + step, 2) + ycoefs.getNum(2, 0) * (t + step) + ycoefs.getNum(3, 0);
            addEdge(new double[] {x4, y4, 0, 1}, new double[] {x5, y5, 0, 1});
        }
    }

    public void add3D(Matrix points){
        for (int i = 0; i < points.getCols(); i += 2){
            addEdge(points.getPointDouble(i), points.getPointDouble(i));
        }
    }

    private void resize(){
        int new_size = point_number + 10;
        double[][] temp = new double[matrix.length][new_size];
        for (int i = 0; i < matrix.length; i ++){
            for (int j = 0; j < matrix[0].length; j ++){
                temp[i][j] = matrix[i][j];
            }
        }
        matrix = temp;
    }

    public double getNum(int row, int col){
        return matrix[row][col];
    }

    public int[] getPoint(int col){
        int[] temp = new int[matrix.length];
        for (int i = 0; i < matrix.length; i ++){
            temp[i] = (int)matrix[i][col];
        }
        return temp;
    }

    public double[] getPointDouble(int col){
        double[] temp = new double[matrix.length];
        for (int i = 0; i < matrix.length; i ++){
            temp[i] = matrix[i][col];
        }
        return temp;
    }

    public int getRows(){
        return matrix.length;
    }

    public int getCols(){
        return point_number;
    }

    public String toString(){
        String temp = new String();
        for (int i = 0; i < matrix.length; i ++){
            for (int j = 0; j < point_number; j ++){
                String tempp = String.valueOf(matrix[i][j]);
                temp += tempp + " ";
            }
            temp += "\n";
        }
        return temp;
    }

    public static Matrix generateSphere(double cx, double cy, double cz, double r){
        Matrix points = new Matrix(4, 4);
        for (double phi = 0; phi < 1; phi += 0.01){
            for (double theta = 0; theta < 1; theta += 0.01){
                double x = r * Math.cos(Math.PI * 2 * theta) + cx;
                double y = r * Math.sin(Math.PI * 2 * theta) * Math.cos(phi * Math.PI) + cy;
                double z = r * Math.sin(Math.PI * 2 * theta) * Math.sin(phi * Math.PI) + cz;
                points.addPoint(new double[] {x, y, z, 1});
            }
        }
        return points;
    }

    public static Matrix generateTorus(double cx, double cy, double cz, double r1, double r2){
        Matrix points = new Matrix(4, 4);
        for (double phi = 0; phi < 1; phi += 0.01){
            for (double theta = 0; theta < 1; theta += 0.01){
                double x = Math.cos(2 * Math.PI * phi) * (r1 * Math.cos(2 * Math.PI * theta) + r2) + cx;
                double y = r1 * Math.sin(2 * Math.PI * theta) + cy;
                double z = -1 * Math.sin(2 * Math.PI * phi) * (r1 * Math.cos(2 * Math.PI * theta) + r2) + cz;
                points.addPoint(new double[] {x, y, z, 1});
            }
        }
        return points;
    }

    public static Matrix multi(Matrix m0, Matrix m1){
        Matrix temp = new Matrix(m0.getRows(), m1.getCols());
        int n = m0.getCols();
        //System.out.println("Multiplying\n" + m0 + " with\n" + m1);
        for (int i = 0; i < m1.getCols(); i ++){
            double[] nums = new double[m0.getRows()];
            for (int j = 0; j < m0.getRows(); j ++){
                double sum = 0;
                for (int k = 0; k < n; k ++){
                    //System.out.println(i + " " + j + " " + k);
                    sum += m0.getNum(j, k) * m1.getNum(k, i);
                }
                nums[j] = sum;
                //System.out.println(sum);
            }
            temp.addPoint(nums);
            //System.out.println("Stage " + i + "\n" + temp + "\n");
        }
        return temp;
    }

}