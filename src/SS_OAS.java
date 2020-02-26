import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.EncryptedDocumentException;


public class SS_OAS 
{
	static int[] Data = {0,0,0,0,0};
	static double opt = 1;
	static int DataSize=50;
	static int rown=1;
	static double cts=System.currentTimeMillis();
	
	static ArrayList<Solution> pop;
	static ArrayList<Solution> ref=new ArrayList<>();
	static ArrayList<Solution> oldref=new ArrayList<>();
	static ArrayList<ArrayList<Solution>> Subsets=new ArrayList<>();
	static ArrayList<Solution> pool;
	static Solution Incumbent;

	
	
	static int popsize=50;
	static int refsize=10;
	static boolean cont=true;
	
	
	static int idg=0;
	static int num = DataSize;

	static class Order implements Cloneable
	{
		int id;
		double r;
		double p;
		double e;
		double d;
		double b;
		double w;
		
		int in;
		int seq;
		double beg;
		double fin;
		
		public Order()
		{
			id=idg++;
			r = GetData.r[id];
			p = GetData.p[id];
			e = GetData.e[id];
			d = GetData.d[id];
			b = GetData.b[id];
			w = GetData.w[id];
		}
		
		public Object clone(){  
		    try{  
		        return super.clone();  
		    }catch(Exception e){ 
		        return null; 
		    }
		}
	}

	static class Solution implements Cloneable
	{
		ArrayList<Order> sol = new ArrayList<>();
		double obj;
		double dist=0;
		
		public Object clone(){  
		    try{  
		        return super.clone();  
		    }catch(Exception e){ 
		        return null; 
		    }
		}
	}

	public static void main(String[] args) throws EncryptedDocumentException, IOException
	{
		for(int i=1;i<91;i++)
		{
			/*if(i==10 || i==64 || i==87)
			{
				popsize=50;
			}
			if(i==61 || i==86)
			{
				popsize=30;
			}*/
			
			/*if(i==70)
			{
				popsize=50;
			}
			
			if(i==69)
			{
				popsize=30;
			}*/
			System.out.println("Instance: "+i);
			rown=i;
			Begin();
			GetData.WhichData();
			GetData.Get_Data();
			ScatterSearch();
			GetData.WriteData();
		}
	}
	
	public static void ScatterSearch()
	{
		DiversificationGeneration.DiversificationGenerationMethod();
		//GRASP.GRASPm();
		Improvement.ImprovementMethod();
		ReferenceSetUpdate.ReferenceSetUpdateMethod();
		int i=0;
		while(i<10)
		{
			SubsetGeneration.SubsetGenerator();
			SolutionCombination.SolutionCombinationMethod();
			ReferenceSetUpdate.ReferenceSetUpdateMethod();
			ReferenceSetUpdate.isRef(ref, oldref);
			if(!cont)
			{
				DiversificationGeneration.DiversificationGenerationMethod2();
				//GRASP.GRASPm2();
				Improvement.ImprovementMethod();
				ReferenceSetUpdate.ReferenceSetUpdateMethod();
				i++;
			}
		}
	}
	
	public static void Begin()
	{
		idg=0;
		cts=System.currentTimeMillis();
		Incumbent=new Solution();
		pop=new ArrayList<>();
		ref=new ArrayList<>();
		oldref=new ArrayList<>();
		Subsets=new ArrayList<>();
	}
}