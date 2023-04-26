import java.util.*;

// A priority queue.
public class PriorityQueue<E> {
    private ArrayList<E> heap = new ArrayList<E>();
    private Comparator<E> comparator;

    public PriorityQueue(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private Map <E, Integer > map = new HashMap<>();

    // Returns the size of the priority queue.
    public int size() {
        return heap.size();
    }



    // Adds an item to the priority queue.
    public void add(E x) {
        heap.add(x);
        //map.put(x, heap.size()-1);
        siftUp(heap.size() -1);
    }

    private int getBidValue(E item){
        if(item instanceof Bid) {
            Bid tempBidder = (Bid) item;
            return tempBidder.getBid();
        }
        else
            return -1;
    }

/*
    public void updatePrice(E item){
        for(int i = 0; i <heap.size(); i++){
            if(heap.get(i).equals(item)){
                E oldItem = heap.get(i);
                heap.set(i, item);
                if(comparator.compare(oldItem, item)< 0){
                    siftDown(i);
                }
                else {
                    siftUp(i);
                }
                //heap.remove(oldItem);
                break;
            }
        }
    }

 */

    public void updatePrice(E newItem, E oldItem){
        int oldItemIndex = map.get(oldItem);
        heap.set(oldItemIndex, newItem);
        map.put(newItem, oldItemIndex);

        if (comparator.compare(oldItem, newItem) < 0){
            siftDown(oldItemIndex);
        }
        else {
            siftUp(oldItemIndex);
        }
    }


    // Returns the smallest item in the priority queue.
    // Throws NoSuchElementException if empty.
    public E minimum() {
        if (size() == 0)
            throw new NoSuchElementException();

        return heap.get(0);
    }

    // Removes the smallest item in the priority queue.
    // Throws NoSuchElementException if empty.
    public void deleteMinimum() {
        if (size() == 0)
            throw new NoSuchElementException();

        heap.set(0, heap.get(heap.size()-1));
        heap.remove(heap.size()-1);
        if (heap.size() > 0) siftDown(0);
    }

    // Sifts a node up.
    // siftUp(index) fixes the invariant if the element at 'index' may
    // be less than its parent, but all other elements are correct.


    public void siftUp(int index) {
        // undviker index 0
        E value = heap.get(index);

        while (index > 0) {
            int parentTo = parent(index);
            E parentValue = heap.get(parentTo);

            // If parent is smaller than child

            if (comparator.compare(value, parentValue) < 0) {
                swap(index, parentTo);
                index = parentTo;

            }
            else {
                break;
            }

        }

        heap.set(index, value);
        map.put(value, index);
    }
    private void swap(int x, int y) {
        E temp = heap.get(x);
        heap.set(x, heap.get(y));
        heap.set(y, temp);
        map.put(temp, y);
        map.put(heap.get(y), x);
    }

    // Sifts a node down.
    // siftDown(index) fixes the invariant if the element at 'index' may
    // be greater than its children, but all other elements are correct.
    private void siftDown(int index) {
        E value = heap.get(index);

        // Stop when the node is a leaf.
        while (leftChild(index) < heap.size()) {
            int left    = leftChild(index);
            int right   = rightChild(index);

            // Work out whether the left or right child is smaller.
            // Start out by assuming the left child is smaller...
            int child = left;
            E childValue = heap.get(left);

            // ...but then check in case the right child is smaller.
            // (We do it like this because maybe there's no right child.)
            if (right < heap.size()) {
                E rightValue = heap.get(right);
                if (comparator.compare(childValue, rightValue) > 0) {
                    child = right;
                    childValue = rightValue;
                }
            }

            // If the child is smaller than the parent,
            // carry on downwards.
            if (comparator.compare(value, childValue) > 0) {
                heap.set(index, childValue);
                index = child;
            } else break;
        }

        map.put(value, index);
        heap.set(index, value);
    }

    // Helper functions for calculating the children and parent of an index.
    private final int leftChild(int index) {
        return 2*index+1;
    }

    private final int rightChild(int index) {
        return 2*index+2;
    }

    private final int parent(int index) {
        return (index-1)/2;
    }
}
