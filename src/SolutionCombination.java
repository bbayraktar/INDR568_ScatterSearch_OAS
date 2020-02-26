import java.util.ArrayList;
import java.util.Collections;


public class SolutionCombination extends SS_OAS
{
	public static void SolutionCombinationMethod()
	{
		pop=new ArrayList<>();
		for(int i=0;i<Subsets.size();i++)
		{
			Combine(Subsets.get(i));
		}
		Collections.sort(pop, new Improvement.comp());
		for(int i=pop.size();i>popsize;i=pop.size())
		{
			pop.remove(Math.round(popsize/2));
		}
	}
	
	public static void Combine(ArrayList<Solution> Subset)
	{
		ArrayList<Integer> diff=DiffId(Subset.get(0),Subset.get(1));
		ArrayList<Solution> pop2=new ArrayList<>();
		if(diff.size()>1)
		{
			pop2.add(Subset.get(1));
			pop2.add(Subset.get(0));
			
			while(diff.size()>1) 
			{
				Solution s = new Solution();
				s.obj=pop2.get(pop2.size()-1).obj;
				for(int i=0;i<num;i++)
				{
					s.sol.add((Order) pop2.get(pop2.size()-1).sol.get(i).clone());
				}
				s=newSol(pop2,s,Subset.get(1));
				SolutionGeneration.IncUpdate(s,6);
				Improvement.Improves(s);
				pop2.add(s);
				diff=DiffId(s,Subset.get(1));
			}
		}
		else
		{
			pop2.add(Subset.get(0));
			pop2.add(Subset.get(1));
		}
		
		for(int i=0;i<pop2.size();i++)
		{
			Improvement.Feasible(pop2.get(i));
			if(!DiversificationGeneration.isEqual(pop, pop2.get(i)))
			{
				SolutionGeneration.IncUpdate(pop2.get(i),6);
				pop.add(pop2.get(i));
			}
		}
	}
	
	public static ArrayList<Integer> DiffId(Solution s1, Solution s2)
	{
		ArrayList<Integer> diff=new ArrayList<>();
		
		for(int i=0;i<num;i++)
		{
			for(int j=0;j<num;j++)
			{
				if(s1.sol.get(i).id==s2.sol.get(j).id && s1.sol.get(i).in!=s2.sol.get(j).in)
				{
					diff.add(s1.sol.get(i).id);
					break;
				}
			}
		}
		return diff;
	}
	
	public static Solution newSol(ArrayList<Solution> pop2, Solution s1, Solution s2)
	{
		outerloop:
		for(int i=0;i<num;i++)
		{
			for(int j=0;j<num;j++)
			{
				if(s1.sol.get(i).id==s2.sol.get(j).id && s1.sol.get(i).in!=s2.sol.get(j).in)
				{
					s1.sol.get(i).in=s2.sol.get(j).in;
					break outerloop;
				}
			}
		}
		return s1;
	}
}













