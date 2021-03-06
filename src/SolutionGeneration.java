import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SolutionGeneration extends SS_OAS
{
	static int[] freq=new int[num];
	static int[] sfre=new int[num];
	static int maxfreq=0;
	static int maxsfre=0;
	
	public static ArrayList<Order> SolutionGenerator(ArrayList<Order> sol)
	{
		Collections.sort(sol, new comp());
		double time=0;
		int h=0;
		for(int i=0;i<sol.size();i++)
		{
			if(sol.get(i).r>=time)
			{
				sol.get(i).in=1;
				sol.get(i).seq=h++;
				sol.get(i).beg=sol.get(i).r;
				sol.get(i).fin=sol.get(i).r+sol.get(i).p;
				time=sol.get(i).fin;
			}
			else if(time+sol.get(i).p<=sol.get(i).b)
			{
				sol.get(i).in=1;
				sol.get(i).seq=h++;
				sol.get(i).beg=time;
				sol.get(i).fin=time+sol.get(i).p;
				time=sol.get(i).fin=time+sol.get(i).p;
			}
			else
			{
				sol.get(i).seq=num+1;
			}
		}
		Freq(sol);
		return sol;
	}
	
	public static ArrayList<Order> SeedGenerator(ArrayList<Order> sol)
	{
		Collections.sort(sol, new compSeed());
		double time=0;
		int h=0;
		for(int i=0;i<sol.size();i++)
		{
			if(sol.get(i).r>=time)
			{
				sol.get(i).in=1;
				sol.get(i).seq=h++;
				sol.get(i).beg=sol.get(i).r;
				sol.get(i).fin=sol.get(i).r+sol.get(i).p;
				time=sol.get(i).fin;
			}
			else if(time+sol.get(i).p<=sol.get(i).b)
			{
				sol.get(i).in=1;
				sol.get(i).seq=h++;
				sol.get(i).beg=time;
				sol.get(i).fin=time+sol.get(i).p;
				time=sol.get(i).fin=time+sol.get(i).p;
			}
			else
			{
				sol.get(i).seq=num+1;
			}
		}
		Freq(sol);
		return sol;
	}
	
	public static double ObjectiveCalculator(ArrayList<Order> sol)
	{
		double obj=0;
		for(int i=0;i<sol.size();i++)
		{
			if(sol.get(i).in==1)
			{
				obj+=sol.get(i).e;
				if(sol.get(i).fin>=sol.get(i).d)
				{
					obj-=sol.get(i).w*(sol.get(i).fin-sol.get(i).d);
				}
			}
		}
		return obj;
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
		}
	}

	public static void IncUpdate(Solution s, int k) 
	{
		if(s.obj>Incumbent.obj)
		{
			Incumbent.obj=s.obj;
			double elapsed=System.currentTimeMillis()-cts;
			System.out.println(Incumbent.obj + " - "+ k + "\t\t\t Elapsed Time:" + elapsed);
			for(int i=0;i<num;i++)
			{
				Incumbent.sol.remove(0);
				Incumbent.sol.add((Order) s.sol.get(i).clone());
			}
		}
	}
	
	static class comp2 implements Comparator<Order> 
	{
		@Override
	    public int compare(Order o1, Order o2) 
	    {
	        return Double.compare((freq[o1.id]+1)*(maxsfre+1)/((maxfreq+1)/*o1.e*/*(sfre[o1.id]+1)),(freq[o2.id]+1)*(maxsfre+1)/((maxfreq+1)/*o2.e*/*(sfre[o2.id]+1)));
	    }
	}
	
	static class comp implements Comparator<Order> 
	{
		@Override
	    public int compare(Order o1, Order o2) 
	    {
	        return Double.compare((freq[o1.id]+1)*(maxsfre+1)/((maxfreq+1)*o1.e*(sfre[o1.id]+1)),(freq[o2.id]+1)*(maxsfre+1)/((maxfreq+1)*o2.e*(sfre[o2.id]+1)));
	    }
	}
	
	static class compSeed implements Comparator<Order> 
	{
		@Override
	    public int compare(Order o1, Order o2) 
	    {
			return Double.compare((freq[o1.id]+1)*(maxsfre+1)/((maxfreq+1)*o1.e*(sfre[o1.id]+1)),(freq[o2.id]+1)*(maxsfre+1)/((maxfreq+1)*o2.e*(sfre[o2.id]+1)));
	    }
	}
}
