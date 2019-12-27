public class Hash {
    
    private final int NUM_JUMPS = 1000000;
    private final int BASE_HASH_BUCKETS = 16;
    
    private static Object[] HN;
    
    Hash () {
	HN = new Object[BASE_HASH_BUCKETS];
	Object[] curr_hash = HN;
	for (int i = 0; i < NUM_JUMPS - 1; i++) {
	    Object[] next_hash = new Object[BASE_HASH_BUCKETS];
	    curr_hash[0] = next_hash;
	    curr_hash = next_hash;	    
	}
	curr_hash[3] = new Long(7433);
	HN = new Object[BASE_HASH_BUCKETS];
    }
    
    public void perfRun(final int runNum) {
        long start =  System.nanoTime();
	Object[] curr_hash = HN;

	for (int i = 0; i < NUM_JUMPS -1; i++) {
	    if (curr_hash == null)
		break;
	    curr_hash = (Object []) curr_hash[0];
	}
	
        long duration = (System.nanoTime() - start) / 1000000L;
        System.out.println("Time = " + duration + "ms");
    } 
    
}
