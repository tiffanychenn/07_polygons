public class PolygonMatrix extends Matrix {

	public PolygonMatrix(int rows, int cols){
		super(rows, cols);
	}

	public void addPolygon(double[] point0, double[] point1, double[] point2){
        addPoint(point0);
        addPoint(point1);
        addPoint(point2);
    }

    /*public void add3D(Matrix points){
        for (int i = 0; i < points.getCols(); i += 2){
            addEdge(points.getPointDouble(i), points.getPointDouble(i));
        }
    }*/

}