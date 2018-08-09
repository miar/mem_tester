
public class TestJavaJump3 {

    private static final int NUM_JUMPS = 10000000;
    private static final int BASE_HASH_BUCKETS = 16;
 
    private static long[] HN;


    public static void main(final String[] args) {

        for (int i = 0; i < 1; i++) {
	    //System.gc();
	    perfRun(i);
	}
    }
 
    public static void init () {
	HN = new long[BASE_HASH_BUCKETS * NUM_JUMPS];
	//HN[3] = (long) new Object();

	long curr_hash_i = 0;
	for (int i = 0; i < NUM_JUMPS -1; i++) {
	    long next_hash_i = curr_hash_i + BASE_HASH_BUCKETS;
	    HN[(int)curr_hash_i] = next_hash_i;
	    curr_hash_i = next_hash_i;
	}
	HN[(int)(curr_hash_i + 3)] = 7433;
    }

    private static void perfRun(final int runNum)
    {
	init();
        long start =  System.nanoTime();

	long curr_hash_i = 0;
	for (int i = 0; i < NUM_JUMPS -1; i++) {
	    curr_hash_i = (long) HN[(int)curr_hash_i];
	}
	long kk =  (long) HN[(int)(curr_hash_i + 3)];
	
        long duration = (System.nanoTime() - start) / 1000000L;
        System.out.println(runNum + " - duration " + duration + "ms");
	System.out.println("kk =" + kk);
    }
}
