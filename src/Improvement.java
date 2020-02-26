import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Improvement extends SS_OAS
{
	public static void ImprovementMethod()
	{
		for(int i=0;i<pop.size();i++)
		{
			Improves(pop.get(i));
		}
	}
	
	public static void Improves(Solution k) 
	{
		Solution s;
		s=Improve2(k);
		SolutionGeneration.IncUpdate(s,4);
		/*s=Improve3(s);
		SolutionGeneration.IncUpdate(s,5);*/
		s=Improve4(s);
		SolutionGeneration.IncUpdate(s,7);
		while(s.obj>k.obj && !DiversificationGeneration.isEqual(pop,k))
		{
			k.obj=s.obj;
			for(int j=0;j<num;j++)
			{
				k.sol.get(j).beg=s.sol.get(j).beg;
				k.sol.get(j).fin=s.sol.get(j).fin;
				k.sol.get(j).in=s.sol.get(j).in;
				k.sol.get(j).seq=s.sol.get(j).seq;
			}
			s=Improve2(s);
			SolutionGeneration.IncUpdate(s,4);
			/*s=Improve3(s);
			SolutionGeneration.IncUpdate(s,5);*/
			s=Improve4(s);
			SolutionGeneration.IncUpdate(s,7);
		}
	}
	
	public static void Improves2(Solution k) 
	{
		Solution s;
		s=Improve(k);
		SolutionGeneration.IncUpdate(s,8);
		while(s.obj>k.obj)
		{
			k.obj=s.obj;
			for(int j=0;j<num;j++)
			{
				k.sol.get(j).beg=s.sol.get(j).beg;
				k.sol.get(j).fin=s.sol.get(j).fin;
				k.sol.get(j).in=s.sol.get(j).in;
				k.sol.get(j).seq=s.sol.get(j).seq;
			}
			s=Improve(s);
			SolutionGeneration.IncUpdate(s,8);
		}
	}
	
	public static Solution Improve(Solution s)
	{
		ArrayList<Solution> impsol=new ArrayList<>();
		impsol.add(s);
		for(int i=0;i<num-1;i++)
		{
			if(s.sol.get(i).in!=s.sol.get(i+1).in)
			{
				Solution isol = new Solution();
				for(int j=0;j<num;j++)
				{
					isol.sol.add((Order) s.sol.get(j).clone());
					isol.sol.get(j).beg=0;
					isol.sol.get(j).fin=0;
				}
				int k=isol.sol.get(i).in;
				isol.sol.get(i).in=isol.sol.get(i+1).in;
				isol.sol.get(i+1).in=k;
				impsol.add(Feasible(isol));
			}
		}
		if(s.sol.get(num-1).in!=s.sol.get(0).in)
		{
			Solution isol = new Solution();
			for(int j=0;j<num;j++)
			{
				isol.sol.add((Order) s.sol.get(j).clone());
				isol.sol.get(j).beg=0;
				isol.sol.get(j).fin=0;
			}
			int k=isol.sol.get(num-1).in;
			isol.sol.get(num-1).in=isol.sol.get(0).in;
			isol.sol.get(0).in=k;
			impsol.add(Feasible(isol));
		}
		Collections.sort(impsol,new comp());
		for(int i=0;i<impsol.size();i++)
		{
			Improve3(impsol.get(i));
			Improve4(impsol.get(i));
		}
		Collections.sort(impsol,new comp());
		return impsol.get(0);
	}
	
	public static Solution Improve2(Solution s)
	{
		ArrayList<Solution> impsol=new ArrayList<>();
		impsol.add(s);
		for(int i=0;i<num-1;i++)
		{
			for(int j=i+1;j<num;j++) 
			{
				if(s.sol.get(i).in!=s.sol.get(j).in)
				{
					Solution isol = new Solution();
					
					for(int k=0;k<num;k++)
					{
						isol.sol.add((Order) s.sol.get(k).clone());
						isol.sol.get(k).beg=0;
						isol.sol.get(k).fin=0;
					}
					int k=isol.sol.get(i).in;
					isol.sol.get(i).in=isol.sol.get(j).in;
					isol.sol.get(j).in=k;
					impsol.add(Feasible(isol));
				}
			}
		}
		Collections.sort(impsol,new comp());
		return impsol.get(0);
	}
	
	public static Solution Improve3(Solution s)
	{
		ArrayList<Solution> impsol=new ArrayList<>();
		impsol.add(s);
		for(int i=0;i<num-1;i++)
		{
			Solution isol = new Solution();
			for(int j=0;j<num;j++)
			{
				isol.sol.add((Order) s.sol.get(j).clone());
				isol.sol.get(j).beg=0;
				isol.sol.get(j).fin=0;
			}
			for(int j=1;j<num;j++)
			{
				int k = isol.sol.get(i).seq;
				isol.sol.get(i).seq=isol.sol.get(j).seq;
				isol.sol.get(j).seq=k;
				Collections.sort(isol.sol,new comp2());
				impsol.add(Feasible(isol));
			}
		}
		Collections.sort(impsol,new comp());
		return impsol.get(0);
	}
	
	public static Solution Improve4(Solution s)
	{
		ArrayList<Solution> impsol=new ArrayList<>();
		impsol.add(s);
		for(int i=0;i<num;i++)
		{
			if(s.sol.get(i).in!=1)
			{
				Solution isol = new Solution();
				for(int j=0;j<num;j++)
				{
					isol.sol.add((Order) s.sol.get(j).clone());
					isol.sol.get(j).beg=0;
					isol.sol.get(j).fin=0;
				}
				isol.sol.get(i).in=1;
				impsol.add(Feasible(isol));
			}
		}
		Collections.sort(impsol,new comp());
		while(impsol.get(0).obj>s.obj)
		{
			s=(Solution) impsol.get(0).clone();
			impsol=new ArrayList<>();
			impsol.add(s);
			for(int i=0;i<num;i++)
			{
				if(s.sol.get(i).in!=1)
				{
					Solution isol = new Solution();
					for(int j=0;j<num;j++)
					{
						isol.sol.add((Order) s.sol.get(j).clone());
						isol.sol.get(j).beg=0;
						isol.sol.get(j).fin=0;
					}
					isol.sol.get(i).in=1;
					impsol.add(Feasible(isol));
				}
			}
			Collections.sort(impsol,new comp());
		}
		return impsol.get(0);
	}
	
	
	public static Solution Feasible(Solution isol)
	{
		double time=0;
		int h=0;
		for(int i=0;i<num;i++)
		{
			if(isol.sol.get(i).in==1)
			{
				if(isol.sol.get(i).r>=time)
				{
					isol.sol.get(i).beg=isol.sol.get(i).r;
					isol.sol.get(i).fin=isol.sol.get(i).beg+isol.sol.get(i).p;
					isol.sol.get(i).seq=h++;
					time=isol.sol.get(i).p+isol.sol.get(i).r;					
				}
				else if(time+isol.sol.get(i).p<=isol.sol.get(i).b)
				{
					isol.sol.get(i).beg=time;
					isol.sol.get(i).fin=time+isol.sol.get(i).p;
					isol.sol.get(i).seq=h++;
					time=time+isol.sol.get(i).p;
				}
				else
				{
					isol.sol.get(i).in=0;
				}
			}
		}
		isol.obj=SolutionGeneration.ObjectiveCalculator(isol.sol);
		return isol;
	}
	
	
	static class comp implements Comparator<Solution> 
	{
		@Override
	    public int compare(Solution s1, Solution s2) 
	    {
	        return Double.compare(1/s1.obj,1/s2.obj);
	    }
	}
	
	static class comp2 implements Comparator<Order> 
	{
		@Override
	    public int compare(Order o1, Order o2) 
	    {
	        return Double.compare(o1.seq,o2.seq);
	    }
	}
}
