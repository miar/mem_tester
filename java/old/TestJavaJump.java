
public class TestJavaJump {

    private static final int NUM_JUMPS = 10000000;
    private static final int BASE_HASH_BUCKETS = 16;
 
    private static Object[] HN;


    public static void main(final String[] args) {

        for (int i = 0; i < 1; i++) {
	    //System.gc();
	    perfRun(i);
	}
    }
 
    public static void init () {
	HN = new Object[BASE_HASH_BUCKETS];
	Object[] curr_hash = HN;
	for (int i = 0; i < NUM_JUMPS -1; i++) {
	    Object[] next_hash = new Object[BASE_HASH_BUCKETS];
	    curr_hash[0] = next_hash;
	    curr_hash = next_hash;	    
	}
	curr_hash[3] = new Long(7433);
    }

    private static void perfRun(final int runNum)
    {
	init();
        long start =  System.nanoTime();

	Object[] curr_hash = HN;
	for (int i = 0; i < NUM_JUMPS -1; i++) {
	    curr_hash = (Object []) curr_hash[0];
	}
	long kk = (long) curr_hash[3];
	
        long duration = (System.nanoTime() - start) / 1000000L;
        System.out.println(runNum + " - duration " + duration + "ms");
	System.out.println("kk =" + kk);
    }
}
