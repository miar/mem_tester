public class TestSearch {
    // configurator
    private static int size = 10;  // size of each hash level
    private static int depth = 10; // number of hash levels
    private static int threads = 0; // 0 - main thread only / n - n threads
    private static int entry_i = 0;
    
    public static void main(final String[] args) {
	Hash hash = new Hash(size, depth); 
	if (threads == 0)
	    runSingleThread(hash);	
	//	else
	//    runMultiThread(hash);
    }

    private static void runSingleThread(Hash hash) {
	long start =  System.nanoTime();
	hash.check_entry(entry_i);
	long time = (System.nanoTime() - start) / 1000000L;
	System.out.println("Time (Main) = " + time + " ms");
    }
      
    
}
