
import java.lang.reflect.Field;
import sun.misc.Unsafe;
import org.ObjectLayout.ReferenceArray;

public class TestJavaJump4 {

    private static final int NUM_JUMPS = 10000000;
    private static final int BASE_HASH_BUCKETS = 16;
 
    private static long[] HN;

    private static final Unsafe unsafe;
    private static final int base;
    private static final int scale;

    static {
        /* initialize the unsafe */
        try {
            Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (sun.misc.Unsafe) field.get(null);
        } catch (IllegalAccessException | IllegalArgumentException |
                 NoSuchFieldException | SecurityException e) {
            throw new AssertionError(e);
        }

        base = unsafe.arrayBaseOffset(long[].class);
        scale  = unsafe.arrayIndexScale(long[].class);
    }

    public static void main(final String[] args) {

	//long raw_index = base + i * scale;
        //return unsafe.compareAndSwapObject(hash, raw_index, expect, update);

        for (int i = 0; i < 1; i++) {
	    //System.gc();
	    perfRun(i);
	}
    }
 
    public static void init () {
	HN = new long[BASE_HASH_BUCKETS];
	long[] curr_hash = HN;
	for (int i = 0; i < NUM_JUMPS -1; i++) {
	    long[] next_hash = new long[BASE_HASH_BUCKETS];
	    unsafe.compareAndSwapObject(curr_hash, base + scale, 0, next_hash);
	    //unsafe.curr_hash[0] = (long) next_hash;
	    curr_hash = next_hash;	    
	}
	curr_hash[3] = 7433;
    }

    private static void perfRun(final int runNum)
    {
	init();
        long start =  System.nanoTime();

	long[] curr_hash = HN;
	for (int i = 0; i < NUM_JUMPS -1; i++) {	    
	    curr_hash =  curr_hash[0];
	}
	long kk = curr_hash[3];
	
        long duration = (System.nanoTime() - start) / 1000000L;
        System.out.println(runNum + " - duration " + duration + "ms");
	System.out.println("kk =" + kk);
    }
}
