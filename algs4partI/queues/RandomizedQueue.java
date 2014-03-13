import java.util.Iterator;
import java.util.NoSuchElementException;

/*************************************************************************
 * 
 * Name: Dongyu Wang Email: wwr1227@gmail.com Compilation: javac
 * RandomizedQueue.java
 * 
 * Throw a NullPointerException if the client attempts to add a null item; throw
 * a java.util.NoSuchElementException if the client attempts to sample or
 * dequeue an item from an empty randomized queue; throw an
 * UnsupportedOperationException if the client calls the remove() method in the
 * iterator; throw a java.util.NoSuchElementException if the client calls the
 * next() method in the iterator and there are no more items to return.
 *************************************************************************/
public class RandomizedQueue<Item> implements Iterable<Item> {
 private int size;
 private Item[] items;

 public RandomizedQueue() // construct an empty randomized queue
 {
  size = 0;
  items = (Item[]) new Object[2];
 }

 public boolean isEmpty() // is the queue empty?
 {
  return size == 0;
 }

 public int size() // return the number of items on the queue
 {
  return size;
 }

 private void resize(int sz) {
  Item[] temp = (Item[]) new Object[sz];
  for (int i = 0; i < size; i++) {
   temp[i] = items[i];
  }
  items = temp;
 }

 public void enqueue(Item item) // add the item
 {
  if (item == null) {
   throw new NullPointerException();
  }
  if (size == items.length) {
   resize(2 * items.length);
  }
  items[size++] = item;
 }

 public Item dequeue() // delete and return a random item
 {
  if (isEmpty()) {
   throw new NoSuchElementException();
  }
  int randomIndex = StdRandom.uniform(size);
  Item item = items[randomIndex];
  if (size > 1) {
   items[randomIndex] = items[size - 1];
   items[size - 1] = null;
  } else { // N == 1, randomIndex == 0
   items[0] = null;
  }
  size--;

  if (size > 0 && size == items.length / 4) {
   resize(items.length / 2);
  }
  return item;
 }

 public Item sample() // return (but do not delete) a random item
 {
  if (isEmpty()) {
   throw new NoSuchElementException();
  }
  return items[StdRandom.uniform(size)];
 }

 public Iterator<Item> iterator() // return an independent iterator over
 // items in random order
 {
  return new RandomizedQueueIterator();
 }

 private class RandomizedQueueIterator implements Iterator<Item> {
  private int index;
  private int[] shuffledIndices;

  public RandomizedQueueIterator() {
   shuffledIndices = new int[size];
   index = size;
   for (int i = 0; i < size; i++)
    shuffledIndices[i] = i;

   StdRandom.shuffle(shuffledIndices);
  }

  public void remove() {
   throw new UnsupportedOperationException();
  }

  public Item next() {
   if (!hasNext()) {
    throw new NoSuchElementException();
   }
   return items[shuffledIndices[--index]];
  }

  public boolean hasNext() {
   return index > 0;
  }
 }

 public static void main(String[] args) // unit testing
 {
 }
}