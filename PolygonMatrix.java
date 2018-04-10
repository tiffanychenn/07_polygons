public class PolygonMatrix extends Matrix {

	public PolygonMatrix(int rows, int cols){
		super(rows, cols);
	}

	public void addPolygon(double[] point0, double[] point1, double[] point2){
        addPoint(point0);
        addPoint(point1);
        addPoint(point2);
    }

    public void addSphere(Matrix points){
        //System.out.println(points.getCols());
        int step = 50;
        int lat_start = 0;
        int lat_stop = 50;
        int longt_start = 0;
        int longt_stop = 50;
        for (int lat = lat_start; lat < lat_stop; lat ++) {
            for (int longt = longt_start; longt < longt_stop; longt ++){
                int index = lat * step + longt;
                if ((lat + 1) * step + longt + 1 <= points.getCols()){
                    int indexBotL = index + 1;
                    int indexBotR = index;
                    int indexTopL = (lat + 1) * step + longt + 1;
                    int indexTopR = (lat + 1) * step + longt;
                    //System.out.println(indexBotR + " " + indexBotL + " " + indexTopL + " " + indexTopR);
                    double[] topL = points.getPointDouble(indexTopL);
                    double[] topR = points.getPointDouble(indexTopR);
                    double[] botL = points.getPointDouble(indexBotL);
                    double[] botR = points.getPointDouble(indexBotR);
                    if ((index + 1) % (step) != 0){
                        addPolygon(topL, botL, botR);
                        addPolygon(topR, topL, botR);
                    }
                }
                else if (index >= (step) * (step - 1) && index < points.getCols() - 1){
                    int indexBotR = index;
                    int indexBotL = index + 1;
                    int indexTopL = (indexBotL) % (step);
                    int indexTopR = (indexBotR) % (step);
                    //System.out.println(indexBotR + " " + indexBotL + " " + indexTopL + " " + indexTopR);
                    double[] topL = points.getPointDouble(indexTopL);
                    double[] topR = points.getPointDouble(indexTopR);
                    double[] botL = points.getPointDouble(indexBotL);
                    double[] botR = points.getPointDouble(indexBotR);
                    addPolygon(topL, botL, botR);
                    addPolygon(topR, topL, botR);
                }
            }
        } 
    }

    public void addTorus(Matrix points){
        System.out.println(points.getCols());
        int step = 20;
        int lat_start = 0;
        int lat_stop = step;
        int longt_start = 0;
        int longt_stop = step;
        for (int lat = lat_start; lat < lat_stop; lat ++){
            for (int longt = longt_start; longt < longt_stop; longt ++){
                int index = lat * step + longt;
                if ((lat + 1) * step + longt + 1 <= points.getCols()){
                    int indexBotL = index + 1;
                    int indexBotR = index;
                    int indexTopL = (lat + 1) * step + longt + 1;
                    int indexTopR = (lat + 1) * step + longt;
                    if (index % (step + 1) == 0){
                        int subBy = (int)((index / (step + 1)) + 1);
                        int indexTopLL = indexTopL - subBy; 
                        int indexTopRR = indexTopR - subBy;
                        int indexBotLL, indexBotRR;
                        if (subBy > 1){
                            indexBotLL = indexBotL - (subBy - 1);
                            indexBotRR = indexBotR - (subBy - 1);
                        }
                        else {
                            indexBotRR = indexBotR;
                            indexBotLL = indexBotL;
                        }
                        //System.out.println(indexBotRR + " " + indexBotLL + " " + indexTopLL + " " + indexTopRR);
                        double[] topRR = points.getPointDouble(indexTopRR);
                        double[] botRR = points.getPointDouble(indexBotRR);
                        double[] botLL = points.getPointDouble(indexBotLL);
                        double[] topLL = points.getPointDouble(indexTopLL);
                        addPolygon(topLL, botLL, botRR);
                        addPolygon(topRR, topLL, botRR);
                    }
                    if (indexTopL == step * step) indexTopL = 0;
                    //System.out.println(indexBotR + " " + indexBotL + " " + indexTopL + " " + indexTopR);
                    double[] topR = points.getPointDouble(indexTopR);
                    double[] botR = points.getPointDouble(indexBotR);
                    double[] botL = points.getPointDouble(indexBotL);
                    double[] topL = points.getPointDouble(indexTopL);
                    if ((index + 1) % (step) != 0){
                        addPolygon(topL, botL, botR);
                        addPolygon(topR, topL, botR);
                    }
                }
                else if (index >= (step) * (step - 1) && index <= points.getCols() - 1){
                    int indexBotR = index;
                    int indexBotL = index + 1;
                    int indexTopL = (indexBotL) % (step);
                    int indexTopR = (indexBotR) % (step);
                    if (indexBotL == step * step){
                        indexBotL = 0;
                        indexTopL = step;
                    }
                    //System.out.println(indexBotR + " " + indexBotL + " " + indexTopL + " " + indexTopR);
                    double[] topL = points.getPointDouble(indexTopL);
                    double[] topR = points.getPointDouble(indexTopR);
                    double[] botL = points.getPointDouble(indexBotL);
                    double[] botR = points.getPointDouble(indexBotR);
                    addPolygon(topL, botL, botR);
                    addPolygon(topR, topL, botR);
                }
            }
        }
    }

}