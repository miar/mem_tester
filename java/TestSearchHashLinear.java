

public class TestSearchHashLinear {
    // configurator
    private static int size = 8;  // size of each hash level
    private static int depth = 2000000; // number of hash levels
    private static int threads = 0; // 0 - main thread only / n - n threads
    private static int entry_i = 0;
    private static int runs = 1;
    private static int second_touch = 7; 
    
    public static void main(final String[] args) {
	System.out.println("time (setup)");
	HashLinear hash = new HashLinear(size, depth, second_touch); 
	if (threads == 0)
	    //	    runSingleThreadOneTouch(hash);
	runSingleThreadTwoTouch(hash);	
	//	else
	//    runMultiThread(hash);
    }

    private static void runSingleThreadOneTouch(HashLinear hash) {
	System.out.println("time (run)");
	long start =  System.nanoTime();
	hash.check_entry_one_touch(entry_i);
	long end = System.nanoTime();
	long time = (end - start) / 1000000L;
	long avg_first_time = time;
	long avg_second_time = 0;
	for (int r = 1; r < runs; r++) {
	    start =  System.nanoTime();
	    hash.check_entry_one_touch(entry_i);
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
	hash.check_entry_two_touch(entry_i, second_touch);
	long end = System.nanoTime();
	long time = (end - start) / 1000000L;
	long avg_first_time = time;
	long avg_second_time = 0;
	for (int r = 1; r < runs; r++) {
	    start =  System.nanoTime();
	    hash.check_entry_two_touch(entry_i, second_touch);
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
/* 
machine laptop (two touch):
    2000000:
       first run: 107
       2nd run: 0
    3000000:
       out of memory
*/
