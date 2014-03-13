import java.util.Iterator;

/*************************************************************************
 * 
 * Name: Dongyu Wang Email: wwr1227@gmail.com Compilation: javac Subset.java
 * 
 *************************************************************************/
public class Subset {
 public static void main(String[] args) {
  int k = (int) Integer.parseInt(args[0]);
  RandomizedQueue<String> q = new RandomizedQueue<String>();

  while (!StdIn.isEmpty())
   q.enqueue(StdIn.readString());

  Iterator<String> iter = q.iterator();

  for (int i = 0; i < k; i++) {
   if (!iter.hasNext()) // if k > q.size(), we'll need to restart iter
    iter = q.iterator();
   StdOut.println(iter.next());
  }
 }
}
