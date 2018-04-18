import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public class FalsePositives {
	
	private static String randomString() 
	{
		String characters = "abcdefghijklmnopqrstuvexyzABCDEFGHIJKLMNOPQRSTUVWYXZ0123456789";
		Random rand = new Random();
	
		int wordLen;
		
		do {
			    wordLen = 5 + 2 * (int) (rand.nextGaussian() + 0.5d);
		} while (wordLen < 1 || wordLen > 12);
		
		StringBuilder sb = new StringBuilder(wordLen);
		
		for (int i = 0; i < wordLen; i++) 
		{
			    char ch = characters.charAt(rand.nextInt(characters.length()));
			    sb.append(ch);
		}
		
		return new String(sb);
	}
	
	
	private static void testFalsePositive(int noWordsInFilter,int noWordsNotInFilter, int bits) 
	{
		ArrayList<String> wordsInFilter = new ArrayList<String>();
		ArrayList<String> randomWordList = new ArrayList<String>();
		
		
		for(int i=0; i<noWordsInFilter; i++)
		{
				wordsInFilter.add(randomString());
		}
		
		String s = "";
		int j=0;
		
		
		while(j<noWordsNotInFilter)
		{
			s = randomString();
			if(!wordsInFilter.contains(s))
			{
				randomWordList.add(s);
				j++;
			}
		}
		
		BloomFilterDet BFD = new BloomFilterDet(noWordsInFilter,bits);
		BloomFilterRan BFR = new BloomFilterRan(noWordsInFilter,bits);
		
		for(int k=0; k<noWordsInFilter; k++)
		{
			BFD.add(wordsInFilter.get(k));
			BFR.add(wordsInFilter.get(k));
		}
		
		int BFD_FPCount = 0;
		int BFR_FPCount = 0;
		
		for(int m=0; m<noWordsNotInFilter; m++)
		{
			if(BFD.appears(randomWordList.get(m)))
			{
				BFD_FPCount++;
			}
			if(BFR.appears(randomWordList.get(m)))
			{
				BFR_FPCount++;
			}	
			
		}
		
		System.out.printf("%d\t\t", noWordsInFilter);
		System.out.printf("%d\t\t\t", noWordsNotInFilter);
		System.out.printf("BloomFilterDet=%.5f\t\t", ((float)BFD_FPCount/(float)noWordsNotInFilter));
		System.out.printf("BloomFilterRan=%.5f\n", ((float)BFR_FPCount/(float)noWordsNotInFilter));
		
}
	
	
	public static void main(String args[])
	{
		
		int noWordsInFilter = 10000;
		int noWordsNotInFilter = 1000;
		double theoreticallyProbability = 0.0;
		
		System.out.println("When bitsPerElement are 4:");
		theoreticallyProbability = Math.pow(0.618,4) ;
		System.out.printf("Theoretically=%.5f\n", theoreticallyProbability);
		System.out.println("SetSize (|N|)\tTest_words (|T|)\tFalse Positive probabilities");
		
		for(int t=0; t<5; t++)
		{
			testFalsePositive(noWordsInFilter,noWordsNotInFilter,4) ;
			noWordsInFilter += 5000;
			noWordsNotInFilter += 500;
		}
		
		
		System.out.println("\nWhen bitsPerElement are 8:");
		theoreticallyProbability = Math.pow(0.618,8) ;
		System.out.printf("Theoretically=%.5f\n", theoreticallyProbability);
		System.out.println("SetSize (|N|)\tTest_words (|T|)\tFalse Positive probabilities");
		
		noWordsInFilter = 10000;
		noWordsNotInFilter = 1000;
		
		for(int t=0; t<5; t++)
		{
			testFalsePositive(noWordsInFilter,noWordsNotInFilter,8) ;
			noWordsInFilter += 5000;
			noWordsNotInFilter += 500;
		}
		
		System.out.println("\nWhen bitsPerElement are 10:");
		theoreticallyProbability = Math.pow(0.618,10) ;
		System.out.printf("Theoretically=%.5f\n", theoreticallyProbability);
		System.out.println("SetSize (|N|)\tTest_words (|T|)\tFalse Positive probabilities");
		
		noWordsInFilter = 10000;
		noWordsNotInFilter = 1000;
		
		for(int t=0; t<5; t++)
		{
			testFalsePositive(noWordsInFilter,noWordsNotInFilter,10) ;
			noWordsInFilter += 5000;
			noWordsNotInFilter += 500;
		}
		
		
	}
	
}
