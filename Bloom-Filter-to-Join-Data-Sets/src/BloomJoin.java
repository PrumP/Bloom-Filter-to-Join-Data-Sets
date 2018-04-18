import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class BloomJoin {
	private static BloomFilterDet BFD;
	private static int table1joinfield=1; //join column of table1
	private static int table2joinfield=1; //join column of table2
	private static ArrayList<String[]> a1a2;
	
	
	
	private static void createBloomFilter(int bitsPerWord,String table1) throws FileNotFoundException
	{
		
		File inputFile = new File(table1);

		Scanner fileIn= new Scanner(inputFile);
		
		int numRecords=0;
		ArrayList<String> joinAttribute=new ArrayList<String>();
		
		while(fileIn.hasNextLine())
		{
			String line[]=fileIn.nextLine().split("\\s+");
			joinAttribute.add(line[table1joinfield-1]); 
			numRecords++;
		}
		 
		fileIn.close();
		
		System.out.println("Number of records in Relation1: "+numRecords);
		
		BFD = new BloomFilterDet(numRecords,bitsPerWord);
		
		for(int i=0;i<joinAttribute.size();i++)
			BFD.add(joinAttribute.get(i));
		
		joinAttribute.clear();
		
		System.out.println("Done creating Bloom Filter!!");
		
	}
	
	private static void searchSecondRel(String table2) throws FileNotFoundException
	{
		File inputFile = new File(table2);

		Scanner fileIn= new Scanner(inputFile);
		
		a1a2=new ArrayList<String[]>();
		
		while(fileIn.hasNextLine())
		{
			String line[]=fileIn.nextLine().split("\\s+");
			
			if(BFD.appears(line[table2joinfield-1])) 
			{
				
				String[] dual=new String[2];
				dual[0]=line[table2joinfield-1]; //always join field is <a1>
				dual[1]=line[2-table2joinfield];
				
				a1a2.add(dual);
			
			}
			
		}
		 
		fileIn.close();
		
		System.out.println("Done creating relation<a1,a2>!!");
	
	}
	
	private static void joinReslt(String table1,String table3) throws IOException
	{
		
		File inputFile = new File(table1);

		Scanner fileIn= new Scanner(inputFile);
		
		FileWriter fos = new FileWriter(table3);
	    PrintWriter dos = new PrintWriter(fos);
	    int count=0;
		while(fileIn.hasNextLine())
		{
			System.out.println("table1 record number: " +count++);
			String line[]=fileIn.nextLine().split("\\s+");
			for(int j=0;j<a1a2.size();j++)
			{
				if(line[table1joinfield-1].equals(a1a2.get(j)[0]))
				{
					System.out.println("Writing to Relation3.txt....");
					dos.print(line[2-table1joinfield]+"\t"); 
					dos.print(a1a2.get(j)[0]+"\t"); 
					dos.print(a1a2.get(j)[1]+"\t"); 
					dos.println();
							
				}
			}
			
		}
		dos.close();
	    fos.close();
		 
		fileIn.close();
		
		System.out.println("Done writing Relation3!!");
		
		
	}
	
	
	public static void main(String args[])throws IOException 
	{
		String table1="Relation1.txt";
		String table2="Relation2.txt";
		String table3="Relation3.txt";
		
		createBloomFilter(8,table1);
		
		searchSecondRel(table2);
		joinReslt(table1,table3);
		
		
        
	}

}
