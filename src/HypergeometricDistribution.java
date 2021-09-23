import java.math.BigDecimal;
import java.math.RoundingMode;

public class HypergeometricDistribution implements Distribution {
	public int x, n, N, M;
	public double probabilities[];
	
	public HypergeometricDistribution() {
		
	}

	public HypergeometricDistribution(int successes, int trials, int totalEle, int successEle)
	{
		x=successes;
		n=trials;
		N=totalEle;
		M=successEle;
		probabilities = new double[n];
		createArrayOfProbabilities();
	}
	
	
	public double hypgeoProb() {
		BigDecimal num = new BigDecimal(MathPlus.nCr(M,x).multiply(MathPlus.nCr(N-M,n-x)));
		BigDecimal denum = new BigDecimal(MathPlus.nCr(N,n));
		
		try {
			return num.divide(denum).doubleValue();
		}
		catch (ArithmeticException e)
		{
			return num.divide(denum, 10, RoundingMode.HALF_UP).doubleValue();
		}
	}
	
	
	private double hypgeoProb(int x)
	{
		BigDecimal num = new BigDecimal(MathPlus.nCr(M,x).multiply(MathPlus.nCr(N-M,n-x)));
		BigDecimal denum = new BigDecimal(MathPlus.nCr(N,n));
		
		try {
			return num.divide(denum).doubleValue();
		}
		catch (ArithmeticException e)
		{
			return num.divide(denum, 10, RoundingMode.HALF_UP).doubleValue();
		}
	}
	
	
	public void createArrayOfProbabilities()
	{
		for(int i=0; i<n; i++)
			probabilities[i]=hypgeoProb(i);
	}


	@Override
	public double calculate(int type) {
		double prob=0;
		switch (type) {
		case 0:
			prob=hypgeoProbLessThan();
			break;
		case 1:
			prob=probabilities[x];
			break;
		case 2:
			prob=hypgeoProbAtLeast();
		default:
		}
		return prob;
	}
	
	
	
	private double hypgeoProbLessThan() {
		return 1-hypgeoProbAtLeast();
	}
	
	private double hypgeoProbAtLeast()
	{
		double sum=0;
		for(int i=x; i<n; i++)
			sum+=probabilities[i];
		return sum;
	}
	

	@Override
	public double varience() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double mean() {
		// TODO Auto-generated method stub
		return 0;
	}
}
