/*************************************************************************
 * Name:Dongyu Email:wwr1227@1227.com
 * 
 * Compilation: javac Brute.java
 * 
 * Description: Brute force. Write a program Brute.java that examines 4 points
 * at a time and checks whether they all lie on the same line segment, printing
 * out any such line segments to standard output and drawing them using standard
 * drawing. To check whether the 4 points p, q, r, and s are collinear, check
 * whether the slopes between p and q, between p and r, and between p and s are
 * all equal. * The order of growth of the running time of your program should
 * be N4 in the worst case and it should use space proportional to N.
 * 
 * 
 *************************************************************************/
import java.util.Arrays;

public class Brute {
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
  for (int i = 0; i < N; i++) {
   for (int j = i + 1; j < N; j++) {
    for (int k = j + 1; k < N; k++) {
     if (pointList[i].slopeTo(pointList[j]) != pointList[i]
       .slopeTo(pointList[k]))
      continue;
     for (int p = k + 1; p < N; p++) {
      if (pointList[i].slopeTo(pointList[j]) == pointList[i]
        .slopeTo(pointList[p])) {
       StdOut.println(pointList[i] + " -> " + pointList[j]
         + " -> " + pointList[k] + " -> "
         + pointList[p]);
       pointList[i].drawTo(pointList[p]);
      }

     }
    }
   }
  }
  // display to screen all at once
  StdDraw.show(0);
 }
}