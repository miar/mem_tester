import java.util.Random;

public class TestSearchHashLinear {
    // configurator
    private static int size = 16;  // size of each hash level
    private static int depth = 2000000; // number of hash levels
    private static int threads = 4; // 0 - main thread only |  n - n threads
    private static int n_datasets = 4; // must be equal to threads if threads > 1
    private static int runs = 5;
    private static int second_touch = size - 1;
    private static int dataset[][];

    private static int touches = 2; // 1 - one touch | 2 - two touches
    
    public static void main(final String[] args) {
	System.out.println("creating dataset");
	createDataset();
	
	System.out.println("creating hash structure");	
	HashLinear hash = new HashLinear(size, depth);
	System.out.println("running tests");
	if (threads == 0)
	    if (touches == 1)
		runSingleThreadOneTouch(hash, 0);
	    else
		runSingleThreadTwoTouch(hash, 0);	
	else
	    runMultiThread(hash);

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

    private static void check_one_touch(HashLinear hash, int thr) {
	for (int ds = 0; ds < n_datasets; ds++) 
	    hash.check_entry_one_touch(ds, dataset);
    }


    private static void check_one_touch_mt(HashLinear hash, int thr) {
	hash.check_entry_one_touch(thr, dataset);
    }


    private static void check_two_touch(HashLinear hash, int thr) {
	for (int ds = 0; ds < n_datasets; ds++) 
	    hash.check_entry_two_touch(ds, dataset, second_touch);
    }

    private static void check_two_touch_mt(HashLinear hash, int thr) {
	hash.check_entry_two_touch(thr, dataset, second_touch);	
    }

    
    private static void runSingleThreadOneTouch(HashLinear hash, int thr) {
	long start =  System.nanoTime();
	check_one_touch(hash, thr);
	long end = System.nanoTime();
	long first_time = (end - start) / 1000000L;
	long avg_first_time = first_time;
	long avg_second_time = 0;
	for (int r = 1; r < runs; r++) {
	    start =  System.nanoTime();	    
	    check_one_touch(hash, thr);
	    end = System.nanoTime();
	    long time = (end - start) / 1000000L;
	    avg_first_time += time;
	    avg_second_time += time;
	}

	System.out.println("Main - First Run Time = " + first_time + " ms");
	System.out.println("Main - Avg with First Run Time = " 
			 +  avg_first_time /runs + " ms");

	if (runs > 1)
	    System.out.println("Main - Avg without first Run Time = "
			       + avg_second_time / (runs - 1) + " ms");
    }

    private static void runSingleThreadTwoTouch(HashLinear hash, int thr) {
	long start =  System.nanoTime();
	check_two_touch(hash, thr);
	long end = System.nanoTime();
	long first_time = (end - start) / 1000000L;
	long avg_first_time = first_time;
	long avg_second_time = 0;
	for (int r = 1; r < runs; r++) {
	    start =  System.nanoTime();
	    check_two_touch(hash, thr);
	    end = System.nanoTime();
	    long time = (end - start) / 1000000L;
	    avg_first_time += time;
	    avg_second_time += time;
	}

	System.out.println("Main - First Run Time = " + first_time + " ms");
	System.out.println("Main - Avg with First Run Time = " 
			 +  avg_first_time /runs + " ms");
	if (runs > 1)
	    System.out.println("Main - Avg without first Run Time = "
			       + avg_second_time / (runs - 1) + " ms");

    }

    private static void InitThreads(HashLinear hash, Thread thr_arr[]) {
	if (threads == 1) {
	    final int tid = 0;
	    if (touches == 1) {
		thr_arr[0] = new Thread(new Runnable() {
			@Override
			public void run() {
			     check_one_touch(hash, tid);
			    //System.out.println("aaaaa...");
			    
			}
		    });	   
		
	    } else {
		thr_arr[0] = new Thread(new Runnable() {
			@Override
			public void run() {
			    check_two_touch(hash, tid);
			}
		    });				
	    }	    
	} else {
	    if (touches == 1) {
		for (int t = 0; t < threads; t++) {
		    final int tid = t;
		    thr_arr[t] = new Thread(new Runnable() {
			    @Override
			    public void run() {
				check_one_touch_mt(hash, tid);
			    }
			});
		}
	    } else {
		for (int t = 0; t < threads; t++) {
		    final int tid = t;
		    thr_arr[t] = new Thread(new Runnable() {
			    @Override
			    public void run() {
				check_two_touch_mt(hash, tid);
			    }
			});
		}
	    }
	}
    }
    
    
    
    private static void runMultiThread(HashLinear hash){
	// run multi thread

	Thread thr_arr[] = new Thread[threads];

	InitThreads(hash, thr_arr);
		
	long start =  System.nanoTime();
	
	for (int t = 0; t < threads; t++)
	    thr_arr[t].start();
	
	for (int t = 0; t < threads; t++)
	    try{
		thr_arr[t].join();
	    } catch(InterruptedException e){
		System.out.println(e);
	    }
	
	long end = System.nanoTime();
	long first_time = (end - start) / 1000000L;
	long avg_first_time = first_time;
	long avg_second_time = 0;
	for (int r = 1; r < runs; r++) {
	    InitThreads(hash, thr_arr);
	    start =  System.nanoTime();
	    for (int t = 0; t < threads; t++)
		thr_arr[t].start();
	    
	    for (int t = 0; t < threads; t++)
		try{
		    thr_arr[t].join();
		} catch(InterruptedException e){
		    System.out.println(e);
		}
	    end = System.nanoTime();
	    long time = (end - start) / 1000000L;
	    avg_first_time += time;
	    avg_second_time += time;
	}

	System.out.println("Threads " + threads + " - First Run Time = " + first_time + " ms");
	System.out.println("Threads " + threads + " - Avg with First Run Time = " 
			 +  avg_first_time /runs + " ms");
	if (runs > 1)
	    System.out.println("Threads " + threads + " - Avg without first Run Time = "
			       + avg_second_time / (runs - 1) + " ms");

	// run single thread
	if (touches == 1)
	    runSingleThreadOneTouch(hash, 0);
	else
	    runSingleThreadTwoTouch(hash, 0);	

    } 
}

