import java.util.Random;


public class TestSearchHashLinear {
    // configurator
    private static int size = 8;  // size of each hash level
    private static int depth = 20; // number of hash levels
    private static int threads = 0; // 0 - main thread only |  n - n threads
    private static int runs = 1;
    private static int first_touch = 0;
    private static int second_touch = size - 1;
    private static int dataset[][];
    private static int n_datasets = 1;
    private static int touches = 1; // 1 - one touch | 2 - two touches
    
    public static void main(final String[] args) {
	System.out.println("creating dataset");
	createDataset();
	
	System.out.println("creating hash structure");	
	HashLinear hash = new HashLinear(size, depth);
	System.out.println("running tests");
	if (threads == 0)
	    if (touches == 1)
		runSingleThreadOneTouch(hash);
	    else
		runSingleThreadTwoTouch(hash);	
	//	else
	//    runMultiThread(hash);
    }

    private static void createDataset() {	
	dataset = new int[n_datasets][depth];
	Random randomGenerator = new Random();
	
	for (int ds = 0; ds < n_datasets; ds++) 
	    for (int d = 0; d < depth; d++) {
		dataset[ds][d] = (int)(randomGenerator.nextDouble()*((size - 2) - 0)); // size - 2 because of second touch
		//System.out.println(dataset[ds][d]);
	    }
	
    }

    private static void check_one_touch(HashLinear hash) {
	for (int ds = 0; ds < n_datasets; ds++) 
	    hash.check_entry_one_touch(ds, dataset);	    
    }

    private static void check_two_touch(HashLinear hash) {
	for (int ds = 0; ds < n_datasets; ds++) 
	    hash.check_entry_two_touch(ds, dataset, second_touch);	    
    }

    
    private static void runSingleThreadOneTouch(HashLinear hash) {
	System.out.println("time (run)");
	long start =  System.nanoTime();
	check_one_touch(hash);
	long end = System.nanoTime();
	long time = (end - start) / 1000000L;
	long avg_first_time = time;
	long avg_second_time = 0;
	for (int r = 1; r < runs; r++) {
	    start =  System.nanoTime();	    
	    check_one_touch(hash);
	    end = System.nanoTime();
	    time = (end - start) / 1000000L;
	    avg_first_time += time;
	    avg_second_time += time;
	}

	System.out.println("Main - First Run Time = " + time + " ms");

	System.out.println("Main - Avg with first Run Time = " 
			 +  avg_first_time /runs + " ms");

	if (runs > 1)
	    System.out.println("Main - Avg without first Run Time = "
			       + avg_second_time / (runs - 1) + " ms");
	else
	    System.out.println("Main - Avg without first Run Time = "
			       + avg_second_time / runs + " ms");
    }

    private static void runSingleThreadTwoTouch(HashLinear hash) {
	System.out.println("time (run)");
	long start =  System.nanoTime();
	check_two_touch(hash);
	long end = System.nanoTime();
	long time = (end - start) / 1000000L;
	long avg_first_time = time;
	long avg_second_time = 0;
	for (int r = 1; r < runs; r++) {
	    start =  System.nanoTime();
	    check_two_touch(hash);
	    end = System.nanoTime();
	    time = (end - start) / 1000000L;
	    avg_first_time += time;
	    avg_second_time += time;
	}
	System.out.println("Main - First Run Time = " + time + " ms");
	if (runs > 1)
	    System.out.println("Main - Avg without first Run Time = "
			       + avg_second_time / (runs - 1) + " ms");
	else
	    System.out.println("Main - Avg without first Run Time = "
			       + avg_second_time / runs + " ms");
    }
}

