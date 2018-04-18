import java.util.BitSet;
import java.util.Random;

public class BloomFilterRan {
	
	private final BitSet data;
	private final int filterSize;
	private final int bitsPerElement;
	private final int setSize;
	private final int maxNoHashFuntions;
	private final int prime;
	private final int[][] kHash;
	private int noElements;
	
	public BloomFilterRan(int setSize, int bitsPerElement)
	{
		 this.setSize = setSize;
		 this.bitsPerElement = bitsPerElement;
		 this.prime = findNextPrime();
		 this.filterSize = this.prime;
		 this.data = new BitSet(this.filterSize);
		 this.maxNoHashFuntions = (int)(Math.log(2.0)*this.bitsPerElement);
		 this.kHash = new int[this.maxNoHashFuntions][2];
		 setHashParameters();
		 this.noElements = 0;
		 
	}
	
	public boolean isPrime(int x)
	{
		for(int i=2; i<x; i++)
		{
			if(x%i == 0)
				return false;
		}
		return true;
	}
	
	
	public int findNextPrime()
	{
		int nextPrime = this.setSize*this.bitsPerElement;
		boolean found = isPrime(nextPrime);
		
		while(!found)
		{
			nextPrime++;
			found = isPrime(nextPrime);
		}
		
		return nextPrime;
	}
	
	public boolean findDuplicates(int a, int b)
	{
	       
        for(int i=0; i<this.maxNoHashFuntions; i++)
        {
        	if((this.kHash[i][0] == a) && (this.kHash[i][1] == b))
        	{
        		return true;
        	}
        		
        }
        
        return false;
	}
	
	public void setHashParameters() {
       
        Random rand = new Random();
        int a = 0;
        int b = 0;
        
        for(int i=0; i<this.maxNoHashFuntions; i++)
        {
        	a = rand.nextInt(this.prime-1);
        	b = rand.nextInt(this.prime-1);
            boolean duplicates = findDuplicates(a,b);
            
            while(duplicates)
            {
            	a = rand.nextInt(this.prime-1);
            	b = rand.nextInt(this.prime-1);
            	duplicates = findDuplicates(a,b);
            }
            
            this.kHash[i][0] = a;
            this.kHash[i][1] = b;
        }
        
    }
	
	public int getHashRandom(String s,int currentNoHash) {
        int rv = s.hashCode();
        int a = this.kHash[currentNoHash][0];
        int b = this.kHash[currentNoHash][1];
        rv = (int)(a*rv + b)%this.prime;
        return rv;
    }
	
	public void display()
	{
		System.out.println(this.prime);
		for(int i=0; i<this.maxNoHashFuntions; i++ )
		{
			System.out.println(this.kHash[i][0]+","+this.kHash[i][1]);
		}
		
	}
	
	public void add(String s)
	{
		int kHashValue = 0;
		int bitNo = 0;
	
		
		for(int i=0; i<this.maxNoHashFuntions; i++)
		{
			kHashValue = getHashRandom(s.toLowerCase(),i);
			bitNo =  kHashValue%this.filterSize;
			this.data.set(Math.abs(bitNo));
		}
		
		this.noElements++;
		
	}
	
	public boolean appears(String s)
	{
		int kHashValue = 0;
		int bitNo = 0;
		boolean found = true;
		
		
		for(int i=0; i<this.maxNoHashFuntions; i++)
		{
			kHashValue = getHashRandom(s.toLowerCase(),i);
			bitNo =  kHashValue%this.filterSize;
			found = found && this.data.get(Math.abs(bitNo));
		}
		
		return found;
	}
	
	public int filterSize()
	{
		return this.filterSize;
	}
	
	public int dataSize()
	{
		return this.noElements;
	}
	
	public int numHashes()
	{
		return this.maxNoHashFuntions;
	}

}
