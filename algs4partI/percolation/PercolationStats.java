/*************************************************************************
 * Name: Dongyu Wang Email: wwr1227@gmail.com
 * 
 * Compilation: javac PercolationStats.java Execution: Dependencies:
 * Percolation.java
 * 
 * Description: Class in charge of running the simulation.
 * 
 *************************************************************************/
public class PercolationStats {
 private Percolation percolation;
 private int T;
 private double[] threshold;

 public PercolationStats(int N, int T) // perform T independent computational
 {
  if (N <= 0 || T <= 0)
   throw new IllegalArgumentException("IllegalArgumentException");
  else {
   this.T = T;
   this.threshold = new double[T];
   for (int i = 0; i < T; i++) {
    percolation = new Percolation(N);
    int openCnt = 0; // number of open sites
    while (openCnt < N * N && !percolation.percolates()) {
     int x = StdRandom.uniform(N) + 1;
     int y = StdRandom.uniform(N) + 1;
     if (!percolation.isOpen(x, y)) {
      percolation.open(x, y);
      openCnt++;
     }
    }
    threshold[i] = (double) openCnt / (double) (N * N);
   }
  }
 }

 public double mean() // sample mean of percolation threshold
 {
  return StdStats.mean(threshold);
 }

 public double stddev() // sample standard deviation of percolation threshold
 {
  return StdStats.stddev(threshold);
 }

 public double confidenceLo() // returns lower bound of the 95% confidence
 // interval
 {
  return mean() - 1.96 * stddev() / Math.sqrt(T);
 }

 public double confidenceHi() // returns upper bound of the 95% confidence
 // interval
 {
  return mean() + 1.96 * stddev() / Math.sqrt(T);
 }

 public static void main(String[] args) // test client, described below
 {
  int N = StdIn.readInt();
  int T = StdIn.readInt();
  PercolationStats percolationStats = new PercolationStats(N, T);
  StdOut.println("mean = " + percolationStats.mean());
  StdOut.println("stddev = " + percolationStats.stddev());
  StdOut.println("95% confidence interval "
    + percolationStats.confidenceLo() + ", "
    + percolationStats.confidenceHi());
 }
}