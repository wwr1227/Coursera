public class Solver {

 private SearchNode result;

 private class SearchNode implements Comparable<SearchNode> {
  private Board board;
  private SearchNode previous;
  private int move;
  private int priority;

  public SearchNode(Board b, SearchNode pre) {
   board = b;
   previous = pre;
   if (previous == null)
    move = 0;
   else
    move = previous.move + 1;
   priority += board.manhattan() + move;
  }

  public int compareTo(SearchNode that) {
   return this.priority - that.priority;
  }
 }

 public Solver(Board initial) // find a solution to the initial board (using
         // the A* algorithm)
 {
  if (initial.isGoal())
   result = new SearchNode(initial, null);
  else
   result = solve(initial, initial.twin());
 }

 private SearchNode step(MinPQ<SearchNode> pq) {
  SearchNode least = pq.delMin();
  for (Board neighbor : least.board.neighbors()) {
   if (least.previous == null
     || !neighbor.equals(least.previous.board))
    pq.insert(new SearchNode(neighbor, least));
  }
  return least;
 }

 private SearchNode solve(Board initial, Board twin) {
  SearchNode last;
  MinPQ<SearchNode> mainpq = new MinPQ<SearchNode>();
  MinPQ<SearchNode> twinpq = new MinPQ<SearchNode>();
  mainpq.insert(new SearchNode(initial, null));
  twinpq.insert(new SearchNode(twin, null));
  while (true) {
   last = step(mainpq);
   if (last.board.isGoal())
    return last;
   if (step(twinpq).board.isGoal())
    return null;
  }
 }

 public boolean isSolvable() // is the initial board solvable?
 {
  return result != null;
 }

 public int moves() // min number of moves to solve initial board; -1 if no
      // solution
 {
  if (isSolvable())
   return result.move;
  return -1;
 }

 public Iterable<Board> solution() // sequence of boards in a shortest
          // solution; null if no solution
 {
  if (!isSolvable())
   return null;
  Stack<Board> solution = new Stack<Board>();
  for (SearchNode n = result; n != null; n = n.previous)
   solution.push(n.board);
  return solution;
 }

 public static void main(String[] args) // solve a slider puzzle (given
           // below)
 {
  // create initial board from file
  In in = new In(args[0]);
  int N = in.readInt();
  int[][] blocks = new int[N][N];
  for (int i = 0; i < N; i++)
   for (int j = 0; j < N; j++)
    blocks[i][j] = in.readInt();
  Board initial = new Board(blocks);

  // solve the puzzle
  Solver solver = new Solver(initial);

  // print solution to standard output
  if (!solver.isSolvable())
   StdOut.println("No solution possible");
  else {
   StdOut.println("Minimum number of moves = " + solver.moves());
   for (Board board : solver.solution())
    StdOut.println(board);
  }
 }
}