
// All buckets from H_i refer to the same H_i+1 
public class HashLinear {
    private static int size;  // size of each hash level
    private static int depth; // number of hash levels 
    private static Object[] root;
    private static int second_touch;
    
    HashLinear(int size, int depth, int second_touch) {
	this.root = new Object[size];
	this.size = size;
	this.depth = depth;
	if (second_touch > (size - 1))
	    this.second_touch = size - 1;
	else
	    this.second_touch = second_touch;
	Object[] curr_hash = root;
	
	for (int d = 1; d < depth; d++) {
	    Object[] next_hash = new Object[size];
	    for (int s = 0; s < size; s++)
		curr_hash[s] = next_hash;
	    curr_hash = next_hash;   
	}
	// last hash entries remain with 'null' values 
    }

    void check_entry_one_touch(int i) {
	Object[] curr_hash = root;	
	for (int d = 0; d < depth; d++) {
	    Object h = curr_hash[i];
	    curr_hash = (Object []) h;
	}
	curr_hash[0] = (Object) 10;
    }


    void check_entry_two_touch(int i, int second_touch) {
	Object[] curr_hash = root;	
	for (int d = 0; d < depth; d++) {
	    // first touch
	    //System.out.println(curr_hash);
	    Object h = curr_hash[i];	    
	    h = curr_hash[second_touch];
	    curr_hash = (Object []) h;
	}
    }   
}
