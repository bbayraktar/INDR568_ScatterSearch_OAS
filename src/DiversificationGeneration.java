import java.util.ArrayList;

public class DiversificationGeneration extends SS_OAS
{
	public static void DiversificationGenerationMethod()
	{
		Incumbent=new Solution();
		ArrayList<Solution> dpop = new ArrayList<>();
		Solution seed = new Solution();
		for(int i=0;i<num;i++)
		{
			Order o = new Order();
			seed.sol.add(o);
		}
		seed.sol=SolutionGeneration.SeedGenerator(seed.sol);
		seed.obj=SolutionGeneration.ObjectiveCalculator(seed.sol);
		Incumbent.obj=seed.obj;
		for(int i=0;i<num;i++)
		{
			Incumbent.sol.add((Order) seed.sol.get(i).clone());
		}
		dpop.add(seed);
		int i=0;
		while(i<popsize)
		{
			Solution gsol = DiversifiedSolution(dpop.get(i));
			if(!isEqual(dpop,gsol) && !isObj(dpop,gsol))
			{
				SolutionGeneration.IncUpdate(gsol,1);
				dpop.add(gsol);
				i++;
			}
		}
		pop=dpop;
	}
	
	public static void DiversificationGenerationMethod2()
	{
		ArrayList<Solution> dpop = new ArrayList<>();
		Solution seed = new Solution();
		seed.obj=Incumbent.obj;
		for(int i=0;i<num;i++)
		{
			seed.sol.add(Incumbent.sol.get(i));
		}
		dpop.add(seed);
		int i=0;
		while(i<popsize)
		{
			Solution gsol = DiversifiedSolution(dpop.get(i));
			if(!isEqual(dpop,gsol) && !isObj(dpop,gsol))
			{
				SolutionGeneration.IncUpdate(gsol,2);
				dpop.add(gsol);
				i++;
			}
		}
		pop=dpop;
	}

	public static void DiversificationGenerationMethod3()
	{
		ArrayList<Solution> dpop = new ArrayList<>();
		Solution seed = new Solution();
		seed.obj=pop.get(pop.size()/2).obj;
		for(int i=0;i<num;i++)
		{
			seed.sol.add(pop.get(pop.size()/2).sol.get(i));
		}
		dpop.add(seed);
		int i=0;
		while(i<popsize)
		{
			Solution gsol = DiversifiedSolution(dpop.get(i));
			if(!isEqual(dpop,gsol) && !isObj(dpop,gsol))
			{
				SolutionGeneration.IncUpdate(gsol,3);
				dpop.add(gsol);
				i++;
			}
		}
		pop=dpop;
	}
	
	public static Solution DiversifiedSolution(Solution seed)
	{
		Solution gsol = new Solution();
		for(int i=0;i<num;i++)
		{
			gsol.sol.add((Order) seed.sol.get(i).clone());
			gsol.sol.get(i).beg=0;
			gsol.sol.get(i).fin=0;
			gsol.sol.get(i).in=0;
		}
		gsol.sol=SolutionGeneration.SolutionGenerator(gsol.sol);
		gsol.obj=SolutionGeneration.ObjectiveCalculator(gsol.sol);
		return gsol;
	}
	
	public static boolean isEqual(ArrayList<Solution> dpop, Solution gsol)
	{
		boolean k=false;
		int h=0;
		for(int i=0;i<dpop.size();i++)
		{
			for(int j=0;j<num;j++)
			{
				if((dpop.get(i).sol.get(j).id==gsol.sol.get(j).id && 
				   dpop.get(i).sol.get(j).beg==gsol.sol.get(j).beg &&
				   dpop.get(i).sol.get(j).fin==gsol.sol.get(j).fin &&
				   dpop.get(i).sol.get(j).in==gsol.sol.get(j).in) || dpop.get(i).obj==gsol.obj
				  )
				{
					h++;
				}
				else
				{
					break;
				}
			}
			if(h==num)
			{
				k=true;
				break;
			}
			h=0;
		}
		
		return k;
	}
	
	public static boolean isObj(ArrayList<Solution> dpop, Solution gsol)
	{
		boolean k=false;
		
		for(int i=0;i<dpop.size();i++)
		{
			k=(dpop.get(i).obj==gsol.obj);
			if(k==true) {break;}
		}
		
		return k;
	}
	
	public static boolean isEqualS(Solution s, Solution gsol)
	{
		boolean k=false;
		int h=0;
		
		for(int j=0;j<num;j++)
		{
			if((s.sol.get(j).id==gsol.sol.get(j).id && 
			   s.sol.get(j).beg==gsol.sol.get(j).beg &&
			   s.sol.get(j).fin==gsol.sol.get(j).fin) || s.obj==gsol.obj
			  )
			{
				h++;
			}
			else
			{
				break;
			}
		}
		if(h==num)
		{
			k=true;
		}
		h=0;
		
		return k;
	}
}