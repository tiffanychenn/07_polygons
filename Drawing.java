public class Drawing {

	public static void drawlines(Matrix m, Image i, int[] color){
		for (int j = 0; j + 1 < m.getCols(); j += 2){
			int[] point0 = m.getPoint(j);
			int[] point1 = m.getPoint(j + 1);
			drawline(point0[0], point0[1], point0[2], point1[0], point1[1], point1[2], i, color);
		}
	}

	public static void drawline(int x0, int y0, int z0, int x1, int y1, int z1, Image i, int[] color){
        if (x0 > x1){
            int temp = x1;
            x1 = x0;
            x0 = temp;
            temp = y1;
            y1 = y0;
            y0 = temp;
        }
        if (x0 == x1){
            vertical(x0, y0, y1, i, color);
        }
        else if (y0 == y1){
            horizontal(x0, x1, y0, i, color);
        }
        else {
            double slope = -1.0 * (y1 - y0) / (x1 - x0);
            //System.out.println(slope);
            if (slope == 1){
                slope1(x0, y0, x1, y1, i, color);
            }
            else if (slope == -1){
                slopenegative1(x0, y0, x1, y1, i, color);
            }
            else if (slope < -1){
                octant7(x0, y0, x1, y1, i, color);
            }
            else if (slope < 0){
                octant8(x0, y0, x1, y1, i, color);
            }
            else if (slope < 1){
                octant1(x0, y0, x1, y1, i, color);
            }
            else {
                octant2(x0, y0, x1, y1, i, color);
            }
        }
    }

    private static void horizontal(int x0, int x1, int y, Image im, int[] color){
        if (x0 > x1){
            int temp = x0;
            x0 = x1;
            x1 = temp;
        }
        for (int i = x0; i <= x1; i ++){
        	im.setPixel(i, y, color);
        }
    }

    private static void vertical(int x, int y0, int y1, Image im, int[] color){
        if (y0 > y1){
            int temp = y0;
            y0 = y1;
            y1 = temp;
        }
        for (int i = y0; i <= y1; i ++){
           	im.setPixel(x, i, color);
        }
    }

    private static void slope1(int x0, int y0, int x1, int y1, Image im, int[] color){
        while (x0 <= x1){
        	im.setPixel(x0, y0, color);
            x0 ++;
            y0 --;
        }
    }

    private static void slopenegative1(int x0, int y0, int x1, int y1, Image im, int[] color){
        while (x0 <= x1){
            im.setPixel(x0, y0, color);
            x0 ++;
            y0 ++;
        }
    }

    private static void octant1(int x0, int y0, int x1, int y1, Image im, int[] color){
        int a = y0 - y1;
        int b = -1 * (x1 - x0);
        int d = 2 * a + b;
        //System.out.println(a + " " + b);
        while (x0 < x1){
            im.setPixel(x0, y0, color);
            x0 ++;
            if (d > 0){
                y0 --;
                d += 2 * b;
            }
            d += 2 * a;
        }
        return;
    }

    private static void octant2(int x0, int y0, int x1, int y1, Image im, int[] color){
        int a = y0 - y1;
        int b = -1 * (x1 - x0);
        int d = a + 2 * b;
        //System.out.println(a + " " + b);
        while (y0 > y1){
            im.setPixel(x0, y0, color);
            y0 --;
            if (d < 0){
                x0 ++;
                d += 2 * a;
            }
            d += 2 * b;
        }
        return;
    }

    private static void octant7(int x0, int y0, int x1, int y1, Image im, int[] color){
        int a = y1 - y0;
        int b = -1 * (x1 - x0);
        int d = a - 2 * b;
        //System.out.println(a + " " + b);
        while (y0 < y1){
            im.setPixel(x0, y0, color);
            y0 ++;
            if (d < 0){
                x0 ++;
                d += 2 * a;
            }
            d += 2 * b;
        }
        return;
    }

    private static void octant8(int x0, int y0, int x1, int y1, Image im, int[] color){
        int a = y1 - y0;
        int b = -1 * (x1 - x0);
        int d = 2 * a - b;
        //System.out.println(a + " " + b);
        while (x0 < x1){
            im.setPixel(x0, y0, color);
            x0 ++;
            if (d > 0){
                y0 ++;
                d += 2 * b;
            }
            d += 2 * a;
        }
        return;
    }

}