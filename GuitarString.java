/* *****************************************************************************
 *
 *  Description: a client of RingBuffer.
 *  Create a data type to model a vibrating guitar string using RingBuffer object
 *
 **************************************************************************** */

public class GuitarString {
    // INSTANCE VARIABLES
    private static final int SAMPLE_RATE = 44100;
    private static final double DECAY_FACTOR = 0.996;
    private int n; // size of RingBuffer object
    private RingBuffer buffer; // a RingBuffer object preresent guitar string

    /**
     * creates a RingBuffer of the desired capacity n (the sampling rate 44,100/ frequency) and
     * initializes it to represent a guitar string at rest.
     *
     * @param frequency input frequency
     */
    public GuitarString(double frequency) {

        n = (int) Math.ceil(SAMPLE_RATE / frequency);
        buffer = new RingBuffer(n);
        for (int i = 0; i < n; i++)
            buffer.enqueue(0.0);
    }

    /**
     * creates a guitar string whose size and initial values are given by the specified array
     *
     * @param init given input array
     */
    public GuitarString(double[] init) {

        n = init.length;
        buffer = new RingBuffer(n);
        for (int i = 0; i < n; i++) {
            buffer.enqueue(init[i]);
        }

    }

    // returns the number of samples in the ring buffer
    public int length() {
        return n;
    }

    // plucks the guitar string
    // Replace the n items in the ring buffer with n random values between −0.5 and +0.5.
    public void pluck() {
        for (int i = 0; i < n; i++) {
            double r = StdRandom.uniform(-0.5, 0.5);
            buffer.dequeue();
            buffer.enqueue(r);
        }
    }

    // advances the Karplus-Strong simulation one time step
    public void tic() {
        double off = buffer.dequeue();
        double first = sample();
        double add = DECAY_FACTOR * (0.5) * (off + first);
        buffer.enqueue(add);
    }

    // returns the current sample
    public double sample() {

        return buffer.peek();

    }


    // tests and calls every constructor and instance method in this class
    public static void main(String[] args) {
        double[] samples = { 0.2, 0.4, 0.5, 0.3, -0.2, 0.4, 0.3, 0.0, -0.1, -0.3 };
        GuitarString testString = new GuitarString(samples);
        int m = 25; // 25 tics
        for (int i = 0; i < m; i++) {
            double sample = testString.sample();
            StdOut.printf("%6d %8.4f\n", i, sample);
            testString.tic();
        }
    }

}
