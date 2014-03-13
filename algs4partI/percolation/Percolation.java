/*************************************************************************
 * Name: Dongyu Wang Email: wwr1227@gmail.com
 * 
 * Compilation: javac Percolation.java Execution: Dependencies:
 * WeightedQuickUnionUF.java
 * 
 * Description: A data type representing the state of the simulation
 * 
 *************************************************************************/
public class Percolation {

 private final int N; // size of sites: N*N
 private boolean[] sites; // store the status of site which is open or block.
 private WeightedQuickUnionUF uf;
 private final int[][] vector = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
 private final int virtTopId; // Virtual Top
 private final int virtBotId; // Virtual Bottom

 public Percolation(int N) // create N-by-N grid, with all sites blocked
 {
  this.N = N;
  this.sites = new boolean[N * N + 2];
  this.uf = new WeightedQuickUnionUF(N * N + 2);
  this.virtTopId = N * N;
  this.virtBotId = N * N + 1;
 }

 public void open(int i, int j) // open site (row i, column j) if it is not
 // already
 {
  isValidIndex(i, j);
  int x = i - 1;
  int y = j - 1;
  if (sites[x * N + y])
   return;
  sites[x * N + y] = true;
  if (x == 0) {
   uf.union(x * N + y, virtTopId);
  } else if (x == N - 1) {
   uf.union(x * N + y, virtBotId);
  }
  for (int v = 0; v < vector.length; v++) {
   int row = x + vector[v][0];
   int column = y + vector[v][1];
   if (row < 0 || column < 0 || row >= N || column >= N)
    continue;
   if (sites[row * N + column])
    uf.union(x * N + y, row * N + column);
  }
 }

 public boolean isOpen(int i, int j) // is site (row i, column j) open?
 {
  isValidIndex(i, j);
  return sites[(i - 1) * N + (j - 1)];
 }

 public boolean isFull(int i, int j) // is site (row i, column j) full?
 {
  isValidIndex(i, j);
  return uf.connected(virtTopId, (i - 1) * N + (j - 1)) 
    && sites[(i - 1) * N + (j - 1)];
 }

 public boolean percolates() // does the system percolate?
 {
  return uf.connected(N * N, N * N + 1);
 }

 /**
  * is row & column a valid site index.
  * 
  * @param i
  *            row
  * @param j
  *            column
  * @return is valid or not
  */
 private void isValidIndex(final int i, final int j) {
  if (i < 1 || j < 1 || i > N || j > N) {
   throw new java.lang.IndexOutOfBoundsException("( " + i + ", " + j
     + " ) out of bounds");
  }
 }

}