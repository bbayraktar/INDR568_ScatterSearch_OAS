import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReferenceSetUpdate extends SS_OAS
{
	public static void ReferenceSetUpdateMethod()
	{
		for(int i=0;i<oldref.size();i=0)
		{
			oldref.remove(i);
		}
		for(int i=0;i<ref.size();i=0)
		{
			oldref.add(ref.get(i));
			ref.remove(i);
		}
		Collections.sort(pop, new comp());
		for(int i=0;i<pop.size();i++)
		{
			pop.get(i).dist=0;
		}
		for(int i=0;i<refsize/2;i++)
		{
			ref.add(pop.get(0));
			pop.remove(0);
		}
		for(int i=0;i<pop.size();i++)
		{
			for(int j=0;j<refsize/2;j++)
			{
				Distance(pop.get(i),ref.get(j));
			}
		}
		Collections.sort(pop, new comp2());
		for(int i=0;i<refsize/2;i++)
		{
			ref.add(pop.get(0));
			pop.remove(0);
		}
		Collections.sort(ref, new comp());
	}
	
	public static void Distance(Solution s1, Solution s2)
	{
		int k=0;
		for(int i=0;i<num;i++)
		{
			if(s1.sol.get(i).id!=s2.sol.get(i).id)
			{
				k++;
			}
			if(s1.sol.get(i).in!=s2.sol.get(i).in)
			{
				k++;
			}
		}
		if(k<s1.dist)
		{
			s1.dist+=k;
		}
	}
	
	public static void isRef(ArrayList<Solution> ref,ArrayList<Solution> oldref) 
	{
		int k=0;
		for(int i=0;i<ref.size();i++)
		{
			if(DiversificationGeneration.isEqual(oldref,ref.get(i)))
			{
				k++;
			}
		}
		if(k==ref.size())
		{
			cont=false;
		}
	}
	
	static class comp implements Comparator<Solution> 
	{
		@Override
	    public int compare(Solution s1, Solution s2) 
	    {
	        return Double.compare(1/s1.obj,1/s2.obj);
	    }
	}
	
	static class comp2 implements Comparator<Solution> 
	{
		@Override
	    public int compare(Solution s1, Solution s2) 
	    {
	        return Double.compare(1/s1.dist,1/s2.dist);
	    }
	}
	
}
