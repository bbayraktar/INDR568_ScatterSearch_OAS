import java.util.ArrayList;
import java.util.Random;

public class GRASP extends SS_OAS
{
	static int[] freq=new int[num];
	static int[] sfre=new int[num];
	static int maxfreq=0;
	static int maxsfre=0;
	static double[] score = new double[num];
	static Random rnd = new Random(0);
	static Solution seed = new Solution();
	static ArrayList<Solution> dpop = new ArrayList<>();
	
	static ArrayList<Order> Rem;
	static ArrayList<Order> RCL;

	static double rc;
	static double smin;
	static double smax;
	static double alpha=0.5;
	
	public static void GRASPm() 
	{
		dpop = new ArrayList<>();
		seed = new Solution();
		Incumbent=new Solution();
		for(int i=0;i<num;i++)
		{
			Order o = new Order();
			seed.sol.add(o);
		}
		Incumbent.obj=seed.obj;
		for(int i=0;i<num;i++)
		{
			Incumbent.sol.add((Order) seed.sol.get(i).clone());
		}
		Freq(seed.sol);
		SminSmax();
		while(dpop.size()<popsize)
		{
			rc=smin+alpha*(smax-smin);
			RCL=new ArrayList<>();
			Rem=new ArrayList<>();
			Construct();
			SminSmax();
		}
		pop=dpop;
	}
	
	public static void GRASPm2() 
	{
		dpop = new ArrayList<>();
		Freq(seed.sol);
		SminSmax();
		while(dpop.size()<popsize)
		{
			rc=smin+alpha*(smax-smin);
			RCL=new ArrayList<>();
			Rem=new ArrayList<>();
			Construct();
			SminSmax();
		}
		pop=dpop;
	}
	
	
	public static void Construct()
	{
		for(int i=0;i<num;i++)
		{
			if(score[seed.sol.get(i).id]>=rc)
			{
				RCL.add((Order) seed.sol.get(i).clone());
			}
			else
			{
				Rem.add((Order) seed.sol.get(i).clone());
			}
		}
		int seq=0;
		ArrayList<Integer> ind=new ArrayList<>();
		for(int i=0;i<num;i++){ind.add(i);}
		Solution nsol = new Solution();
		
		loop:
		while(ind.size()>0)
		{
			int k=rnd.nextInt(ind.size());
			for(int i=0;i<RCL.size();i++)
			{
				if(RCL.get(i).id==ind.get(k))
				{
					RCL.get(i).in=1;
					RCL.get(i).seq=seq++;
					nsol.sol.add((Order) RCL.get(i).clone());
					RCL.remove(i);
					ind.remove(k);
					continue loop;
				}
			}
			for(int i=0;i<Rem.size();i++)
			{
				if(Rem.get(i).id==ind.get(k))
				{
					Rem.get(i).seq=DataSize+1;
					nsol.sol.add((Order) Rem.get(i).clone());
					Rem.remove(i);
					ind.remove(k);
					continue loop;
				}
			}
		}
		nsol=Improvement.Feasible(nsol);
		if(!DiversificationGeneration.isEqual(dpop,nsol))
		{
			SolutionGeneration.IncUpdate(nsol,2);
			dpop.add(nsol);
		}
		Freq(nsol.sol);
	}
	
	
	public static void SminSmax()
	{
		smin=999999999;
		smax=-1;
		for(int i=0;i<num;i++)
		{
			if(score[i]<smin)
			{
				smin=score[i];
			}
			if(score[i]>smax)
			{
				smax=score[i];
			}
		}
	}
	
	public static void Freq(ArrayList<Order> sol)
	{
		for(int i=0;i<sol.size();i++)
		{
			sfre[sol.get(i).id]+=sol.get(i).seq;
			if(sfre[sol.get(i).id]>maxsfre)
			{
				maxsfre=sfre[sol.get(i).id];
			}
			if(sol.get(i).in==1)
			{
				freq[sol.get(i).id]++;
			}
			if(freq[sol.get(i).id]>maxfreq)
			{
				maxfreq=freq[sol.get(i).id];
			}
			score[sol.get(i).id]+=((maxfreq+1)*(sfre[sol.get(i).id]+1)/(freq[sol.get(i).id]+1)*(maxsfre+1));
		}
		
	}
	
}
