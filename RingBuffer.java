/* *****************************************************************************
 *  Description:
 * To create a RingBuffer API which simulate Karplus-Strong algorithm.
 * The Karplus–Strong algorithm simulates string's vibration
 * by maintaining a ring buffer of the n samples.
 *
 * The algorithm repeatedly deletes the first sample from the ring buffer and
 * adds to the end of the ring buffer the average of the deleted sample and the first sample,
 * scaled by an energy decay factor of 0.996.
 *
 **************************************************************************** */

public class RingBuffer {
    // INSTANCE VARIABLES
    private double[] rb;          // items in the buffer
    private int first;            // index for the next dequeue or peek
    private int last;             // index for the next enqueue
    private int size;             // number of items in the buffer

    /**
     * creates an empty ring buffer with the specified capacity
     *
     * @param capacity the size of the ring buffer
     */
    public RingBuffer(int capacity) {
        rb = new double[capacity];
        first = 0; // initally at 0
        last = 0;  // initally at 0
        size = 0; // size initially is 0
    }

    // a helper to help debugging
    // print out index first, last and the size
    private void testRB() {
        int f = first;
        int l = last;
        int s = size();
        StdOut.println(f + " " + l + " " + s + " ");
    }

    // return the capacity of this ring buffer
    public int capacity() {
        return rb.length;
    }

    // return number of items currently in this ring buffer
    public int size() {
        return size;
    }

    // is this ring buffer empty (size equals zero)?
    public boolean isEmpty() {
        return size == 0;
    }

    // is this ring buffer full (size equals capacity)?
    public boolean isFull() {
        return size == capacity();
    }

    /**
     * adds item x to the end of this ring buffer
     *
     * @param x item that is added to buffer
     */
    public void enqueue(double x) {
        if (isFull()) {
            throw new RuntimeException("Ring Buffer is full");
        }
        size++; // increase size
        rb[last] = x;
        last++;
        if (last == rb.length) last = 0; // wrapped the buffer around
    }

    // deletes and returns the item at the front of this ring buffer
    public double dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring Buffer is empty");
        }
        size--; // decrease size
        double remove = rb[first];
        first++;
        if (first == rb.length) first = 0; // wrapped the buffer around
        return remove;
    }

    // returns the item at the front of this ring buffer
    public double peek() {
        return rb[first];
    }

    // tests and calls every instance method in this class
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        RingBuffer buffer = new RingBuffer(n);
        for (int i = 1; i <= n; i++) {
            buffer.enqueue(i);
        }
        buffer.testRB();
        double t = buffer.dequeue();
        buffer.testRB();
        buffer.enqueue(t);
        buffer.testRB();
        StdOut.println("Size after wrap-around is " + buffer.size());
        while (buffer.size() >= 2) {
            double x = buffer.dequeue();
            double y = buffer.dequeue();
            buffer.enqueue(x + y);
        }
        StdOut.println(buffer.peek());
    }

}
