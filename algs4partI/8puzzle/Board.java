public class Board {

 private int[][] blocks;

 public Board(int[][] blocks) // construct a board from an N-by-N array of
 // blocks (where blocks[i][j] = block in row
 // i, column j)
 {
  this.blocks = copySquareArray(blocks);
 }

 private int[][] copySquareArray(int[][] original) {
  int len = original.length;
  int[][] copy = new int[len][len];
  for (int row = 0; row < len; row++) {
   for (int col = 0; col < len; col++)
    copy[row][col] = original[row][col];
  }
  return copy;
 }

 public int dimension() // board dimension N
 {
  return this.blocks.length;
 }

 public int hamming() // number of blocks out of place
 {
  int hamming = 0;
  for (int i = 0; i < dimension(); i++) {
   for (int j = 0; j < dimension(); j++) {
    if (blocks[i][j] == 0)
     continue;
    if (blocks[i][j] != i * dimension() + j + 1)
     hamming++;
   }
  }
  return hamming;
 }

 public int manhattan() // sum of Manhattan distances between blocks and goal
 {
  int manhattan = 0;
  for (int i = 0; i < dimension(); i++) {
   for (int j = 0; j < dimension(); j++) {
    if (blocks[i][j] == 0)
     continue;
    int cur = blocks[i][j] - 1;
    // StdOut.println(cur+";"+Math.abs(cur / dimension() -
    // i)+";"+Math.abs(cur % dimension() - j));
    manhattan += (Math.abs(cur / dimension() - i) + Math.abs(cur
      % dimension() - j));
   }
  }
  return manhattan;
 }

 public boolean isGoal() // is this board the goal board?
 {
  if (blocks[dimension() - 1][dimension() - 1] != 0)
   return false;
  int value = 1;
  for (int row = 0; row < dimension(); row++)
   for (int col = 0; col < dimension(); col++)
    if (blocks[row][col] != value++
      && (row != dimension() - 1 || col != dimension() - 1))
     return false;
  return true;
 }

 public Board twin() // a board obtained by exchanging two adjacent blocks in
 // the same row
 {
  int[][] copy = copySquareArray(blocks);
  // if (dim <= 1)
  // return new Board(copy);
  // Find zero so that we don't exchange with the blank
  // This looks like a O(dim^2) algorithm, but on average it should finish
  // in O(1).
  int row = 0;
  int col = 0;
  int value = 0;
  int lastValue = blocks[0][0];
  zerosearch: for (row = 0; row < dimension(); row++) {
   for (col = 0; col < dimension(); col++) {
    value = blocks[row][col];
    // Check col>0 because swap must occur on same row
    if (value != 0 && lastValue != 0 && col > 0)
     break zerosearch;
    lastValue = value;
   }
  }
  copy[row][col] = lastValue;
  copy[row][col - 1] = value;
  return new Board(copy);
 }

 public boolean equals(Object y) // does this board equal y?
 {
  if (y == this)
   return true;
  if (y == null)
   return false;
  if (y.getClass() != this.getClass())
   return false;
  Board that = (Board) y;
  if (this.blocks.length != that.blocks.length)
   return false;
  for (int i = 0; i < dimension(); i++)
   for (int j = 0; j < dimension(); j++)
    if (this.blocks[i][j] != that.blocks[i][j])
     return false;
  return true;
 }

 public Iterable<Board> neighbors() // all neighboring boards
 {
  Queue<Board> q = new Queue<Board>();
  for (int i = 0; i < dimension(); i++) {
   for (int j = 0; j < dimension(); j++) {
    if (blocks[i][j] == 0) {
     if (i - 1 >= 0)
      q.enqueue(new Board(swap(blocks, i, j, i - 1, j)));
     if (j - 1 >= 0)
      q.enqueue(new Board(swap(blocks, i, j, i, j - 1)));
     if (i + 1 < dimension())
      q.enqueue(new Board(swap(blocks, i, j, i + 1, j)));
     if (j + 1 < dimension())
      q.enqueue(new Board(swap(blocks, i, j, i, j + 1)));
     break;
    }
   }
  }
  return q;
 }

 private int[][] swap(int[][] org, int i, int j, int swapI, int swapJ) {
  int[][] copy = copySquareArray(org);
  int temp = copy[i][j];
  copy[i][j] = copy[swapI][swapJ];
  copy[swapI][swapJ] = temp;
  return copy;
 }

 public String toString() // string representation of the board (in the
 // output format specified below)
 {
  StringBuilder s = new StringBuilder();
  s.append(dimension() + "\n");
  for (int i = 0; i < dimension(); i++) {
   for (int j = 0; j < dimension(); j++) {
    s.append(String.format("%2d ", blocks[i][j]));
   }
   s.append("\n");
  }
  return s.toString();
 }
}