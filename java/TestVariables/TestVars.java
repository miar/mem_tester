public class TestVars {
    
    public static void main(final String[] args) {
	//HashMt HN = new HashMt();
	Hash HN = new Hash();
        for (int i = 0; i < 3; i++) {
	    //System.gc();
	    HN.perfRun(i);
	}
    }   
}

