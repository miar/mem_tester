
// All buckets from H_i refer to the same H_i+1 

#include <new>
#include <iostream>
#include <stdio.h>
#include <stdlib.h> 


using namespace std;


class HashLinear {
public:
  int size;  // size of each hash level
  int depth; // number of hash levels 
  HashLinear *roots;
  
  HashLinear() {}
  
  HashLinear(int s, int d) {
    //root = new HashLinear(10,19);
    //roots = malloc (s * sizeof(*HashLinear));
    int *ss = (int *) malloc (s * sizeof(int *));
    
    //    roots[3].size = 10;
    for (int i = 0; i < s; i++)
      cout << "value = " << roots[i].size << '\n';

    size = s;
    depth = d;
  }  
};


int main() {

  //HashLinear *root = new HashLinear(10,10);

  HashLinear *root = (HashLinear *) malloc (10 * sizeof(HashLinear *));

  cout << root->size << "ola" <<'\n';


  return 0;
}



/*
public class HashLinear {


    HashLinear(int size, int depth) {
	
	this.root = new Object[size];
	this.size = size;
	this.depth = depth;
	Object[] curr_hash = root;
	
	for (int d = 1; d < depth; d++) {
	    Object[] next_hash = new Object[size];
	    for (int s = 0; s < size; s++)
		curr_hash[s] = next_hash;
	    curr_hash = next_hash;   
	}
	// last hash entries remain with 'null' values 
    }

    void check_entry_one_touch(int ds, int dataset[][]) {
	//System.out.println("one_touch");
	Object[] curr_hash = root;	
	for (int d = 0; d < depth; d++) {
	    int i = dataset[ds][d];
	    Object h = curr_hash[i];
	    curr_hash = (Object []) h;
	}
    }

    void check_entry_two_touch(int ds, int dataset[][],  int second_touch) {
	Object[] curr_hash = root;	
	for (int d = 0; d < depth; d++) {
	    // first touch
	    int first_touch = dataset[ds][d];
	    Object h = curr_hash[first_touch];	    
	    h = curr_hash[second_touch];
	    curr_hash = (Object []) h;
	}
    }   
}


*/
