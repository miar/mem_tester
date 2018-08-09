public class Hash {
    private int size;  // size of each hash level
    private int depth; // number of hash levels 
    private static Object[] root;
    
    Hash(int size, int depth) {	
	root = new Object[size];
	Object[] curr_hash = root;
	
	for (int d = 1; d < depth; d++) {
	    Object[] next_hash = new Object[size];
	    for (int s = 0; s < size - 1; s++)
		curr_hash[s] = next_hash;
	    curr_hash = next_hash;   
	}
	// last hash entries remain with 'null' values 
    }

    void 

}
