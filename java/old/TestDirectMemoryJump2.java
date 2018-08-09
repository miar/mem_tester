import sun.misc.Unsafe;
import java.lang.reflect.Field;

public class TestDirectMemoryJump2 {

    private static final int NUM_JUMPS = 10000000;
    private static final int BASE_HASH_BUCKETS = 16;
    private static final int hash_cell_size = 8; // sizeof a reference...
 
    private static long HN;

    private static final Unsafe unsafe;
    static {
        try {
	    Field field = Unsafe.class.getDeclaredField("theUnsafe");
	    field.setAccessible(true);
	    unsafe = (Unsafe)field.get(null);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	
    }

    public static void init () {

        long requiredHeap = (BASE_HASH_BUCKETS) * hash_cell_size * NUM_JUMPS;
        HN = unsafe.allocateMemory(requiredHeap);
	long curr_hash = HN;
	for (int i = 0; i < NUM_JUMPS -1; i++) {
	    long next_hash = curr_hash + hash_cell_size * BASE_HASH_BUCKETS ;
	    unsafe.putLong(curr_hash, next_hash);
	    curr_hash = next_hash;
	}
	unsafe.putLong(curr_hash + 3 * hash_cell_size, 7433);
    }


    public static void main(final String[] args) {

        for (int i = 0; i < 1; i++) {
	    //System.gc();
	    perfRun(i);
	}
    }
 

    private static void perfRun(final int runNum)
    {
	init();
	long start =  System.nanoTime();
	long curr_hash = HN;
	for (int i = 0; i < NUM_JUMPS - 1; i++) {
	    curr_hash = unsafe.getLong(curr_hash);
	}
	long kk = unsafe.getLong(curr_hash + 3 * hash_cell_size);

	long duration = (System.nanoTime() - start) / 1000000L;
        System.out.println(runNum + " - duration " + duration + "ms");
	System.out.println("kk =" + kk);
    }
}
