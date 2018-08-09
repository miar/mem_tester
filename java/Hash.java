public class Hash {
    private int size;  // size of each hash level
    private int middle;
    private int depth; // number of hash levels 
    private static Object[] root;
    
    Hash(int size, int depth) {
	if (size == 1)
	    middle = 0;
	else
	    middle = size / 2; // more or less this value...
	
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

    void check_first_entry() {
	Object[] curr_hash = root;
	for (int d = 1; d < depth; d++)
	    curr_hash = curr_hash[0];	
    }

    void check_middle_entry() {
	Object[] curr_hash = root;
	for (int d = 1; d < depth; d++)
	    curr_hash = curr_hash[middle];	
    }
  
    void check_last_entry() {
	Object[] curr_hash = root;
	for (int d = 1; d < depth; d++)
	    curr_hash = curr_hash[size -1];	
    }

}
