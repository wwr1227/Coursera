import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/*************************************************************************
 * Name:Dongyu Email:wwr1227@1227.com
 * 
 * Compilation: javac Fast.java
 * 
 * Description:The order of growth of the running time of your program should be
 * N2 log N in the worst case and it should use space proportional to N.
 * 
 * 
 *************************************************************************/

public class Fast {
 private Set<String> set = new HashSet<String>();

 public static void main(String[] args) {
  StdDraw.setXscale(0, 32768);
  StdDraw.setYscale(0, 32768);
  StdDraw.show(0);

  // read in the input
  String filename = args[0];
  In in = new In(filename);
  int N = in.readInt();
  Point[] pointList = new Point[N];
  for (int i = 0; i < N; i++) {
   Point p = new Point(in.readInt(), in.readInt());
   p.draw();
   pointList[i] = p;
  }
  Arrays.sort(pointList);
  Point[] auxPoints = new Point[N];
  Fast instance = new Fast();
  for (int i = 0; i < N - 3; i++) {
   Point cur = pointList[i];
   int sizeOfRemain = N - i - 1;
   System.arraycopy(pointList, i + 1, auxPoints, 0, sizeOfRemain);
   Arrays.sort(auxPoints, 0, sizeOfRemain, cur.SLOPE_ORDER);
   int base = 0;
   double baseSlope = cur.slopeTo(auxPoints[0]);
   for (int j = 1; j < sizeOfRemain; j++) {
    double curSlope = cur.slopeTo(auxPoints[j]);
    if (curSlope != baseSlope) {
     instance.draw(cur, auxPoints, base, j);
     base = j;
     baseSlope = cur.slopeTo(auxPoints[base]);
    }
   }
   instance.draw(cur, auxPoints, base, sizeOfRemain);
  }
  StdDraw.show(0);
 }

 private void draw(Point point, Point[] points, int start, int end) {
  if (null == point || points.length == 0 || end - start <= 2)
   return;
  String string = points[end - 1].toString() + points[end - 2].toString();
  if (set.contains(string))
   return;
  set.add(string);
  StdOut.print(point + " -> ");
  for (int i = start; i < end; i++) {
   StdOut.print(points[i]);
   if (i != end - 1)
    StdOut.print(" -> ");
   else
    StdOut.println();
  }
  point.drawTo(points[end - 1]);
 }
}