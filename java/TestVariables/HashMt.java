public class HashMt {
    
    private final int NUM_JUMPS = 1000000;
    private final int BASE_HASH_BUCKETS = 16;
    private static int threads = 24; 
    Thread thr_arr[] = new Thread[threads];    
    private Object[] HN;
    
    HashMt () {
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

    public void init_threads() {
	for (int t = 0; t < threads; t++) {
	    final int tid = t;
	    thr_arr[t] = new Thread(new Runnable() {
		    @Override
		    public void run() {
			run_thread();
		    }
		});
	}
    }

    public void run_thread() {
	Object[] curr_hash = HN;
	for (int i = 0; i < NUM_JUMPS -1; i++) {
	    if (curr_hash == null)
		break;		
	    curr_hash = (Object []) curr_hash[0];	    
	}	
    }

        
    public void perfRun(final int runNum) {
	init_threads();

        long start =  System.nanoTime();
	
	for (int t = 0; t < threads; t++)
	    thr_arr[t].start();
	
	for (int t = 0; t < threads; t++) {
	    try{
		thr_arr[t].join();
	    } catch(InterruptedException e){
		System.out.println(e);
	    }
	}
	
	
        long duration = (System.nanoTime() - start) / 1000000L;
        System.out.println("Time = " + duration + "ms");
    } 
    
}
