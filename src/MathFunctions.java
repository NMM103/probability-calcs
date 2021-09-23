
public class MathFunctions {

	public static void main (String[] args)
	{
		System.out.println(factorial(20));
		//System.out.println(nCr(100,88));
	}
	
	public static long nCr(int n, int r)
	{
		long numerator=1;
		for (int i=n; i>n-r; i--)
			numerator*=i;
		return numerator/factorial(r);
	}
	
	public static long factorial(int n)
	{
		long ans=1;
		for(int i=n; i>1; i--)
			ans*=i;
		return ans;
	}
}
