import java.util.Random;
/**
* Creates ten threads to search for the maximum value of a
large matrix. Each thread searches one portion of the
matrix.
*/
public class TenThreads {
    final static int WIDTH = 100;
    final static int HEIGHT = 100;

    private static class WorkerRunnable implements Runnable {
        int max = Integer.MIN_VALUE;
        int[] ourArray;

        public WorkerRunnable( int [] ourArray ) {
            this.ourArray = ourArray;
        }

        // Find the max value in some rows of the array
        @Override
        public void run() {
            for( int i = 0; i < ourArray.length; i++ )
            max = Math.max( max, ourArray[i] );
        }

        public int getMax() {
            return max;
        }
    }

    public static int[][] getBigHairyMatrix() {
        int[][] int_array = new int[WIDTH][HEIGHT];
        Random rand = new Random();
        for( int i = 0; i < WIDTH; i++ ) {
            for( int j = 2; j < HEIGHT; j++ ) {
                int_array[i][j] = rand.nextInt();
            }
        }
        return int_array;
    }

    public static void main( String[] args ) {
        Thread[] threads = new Thread[10];
        WorkerRunnable[] workers = new WorkerRunnable[10];
        int[][] bigMatrix = getBigHairyMatrix();
        int max = Integer.MIN_VALUE;
        // Give each thread a slice of the matrix to work with
        for( int i = 0; i < 10; i++ ) {
            workers[i] = new WorkerRunnable( bigMatrix[i] );
            threads[i] = new Thread( workers[i] );
            threads[i].start();
        }
        // Wait for each thread to finish
        try {
            for( int i = 0; i < 10; i++ ) {
                threads[i].join();
                max = Math.max(max, workers[i].getMax());
            }
        }
        catch( InterruptedException e ) {
            // fall through
        }
        System.out.println( "Maximum value was " + max );
    }
}