import java.util.ArrayList;

public class SubsetGeneration extends SS_OAS
{
	public static void SubsetGenerator()
	{
		for(int i=0;i<refsize;i++)
		{
			for( int j=0 ; j<refsize; j++ )
			{
				if( i!=j )
				{
					Solution s1 = new Solution();
					s1.obj=ref.get(i).obj;
					for(int k=0;k<num;k++)
					{
						s1.sol.add((Order) ref.get(i).sol.get(k).clone());
					}
				
					ArrayList<Solution> Subset=new ArrayList<>();
					Solution s2 = new Solution();
					s2.obj=ref.get(j).obj;
					for(int h=0;h<num;h++)
					{
						s2.sol.add((Order) ref.get(j).sol.get(h).clone());
					}
					Subset.add(s1); Subset.add(s2);
					Subsets.add(Subset);
				}
			}
		}
		
		pop=new ArrayList<>();
	}
}
