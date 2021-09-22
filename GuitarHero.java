/* *****************************************************************************
 *  Description: a GuitarString client that plays the guitar in real
 *  time, using the keyboard to input 37 notes. Each note i represeneds by
 * a key on keyboard. The ith key corespond to a frequency of 440*2^(i-24/12).
 *
 **************************************************************************** */

public class GuitarHero {
    static final int NOTES = 37;
    static final double CONCERT_A = 440.0;

    public static void main(String[] args) {
        // Initialize the keyboard's keys
        String keyboard1 = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        // Array of 37 notes
        double[] concert = new double[NOTES];
        for (int i = 0; i < concert.length; i++) {
            concert[i] = CONCERT_A * Math.pow(2, (i - 24) / 12.0);
        }
        // Array of GuitarString objects correspond to each note
        GuitarString[] stringPluck = new GuitarString[NOTES];
        for (int i = 0; i < stringPluck.length; i++) {
            stringPluck[i] = new GuitarString(concert[i]);
        }
        // the main input loop
        Keyboard keyboard = new Keyboard();
        while (true) {
            double sample = 0;
            // check if the user has typed a key, and, if so, process it
            if (keyboard.hasNextKeyPlayed()) {

                // the user types this character
                char key = keyboard.nextKeyPlayed();
                int index = keyboard1.indexOf(key);
                // pluck the correspond string
                for (int j = 0; j < stringPluck.length; j++) {
                    if (index == j) {
                        stringPluck[j].pluck();
                    }
                }
            }
            // compute the superposition of the samples
            for (int i = 0; i < stringPluck.length; i++) {
                sample += stringPluck[i].sample();
            }
            StdAudio.play(sample);
            // advance the simulation of each guitar string by one step
            for (int i = 0; i < stringPluck.length; i++) {
                stringPluck[i].tic();
            }
        }


    }
}
