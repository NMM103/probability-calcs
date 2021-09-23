import java.math.BigDecimal;
import java.math.BigInteger;

public class BinomialDistribution implements Distribution {
	private int n, x;
	private double p;
	private double probabilities[];
	private boolean probArray;
	
	public BinomialDistribution ()
	{
		n=0;
		x=0;
		p=0;
	}
	public BinomialDistribution(int successes, int trials, double probability) {
		x=successes;
		n=trials;
		p=probability;
		probabilities = new double[n];
		createArrayOfProbabilities();
	}
	
	public double calculate(int type)
	{
		double prob=0;
		
		switch (type) {
		case 0:
			prob=binomProbLessThan();
			break;
		case 1:
			prob=binomProb();
			break;
		case 2:
			prob=binomProbAtLeast();
		default:
		}
		
		return prob;
	}
	
	private double binomProbLessThan()
	{
		return 1-binomProbAtLeast();
	}
	
	
	private double binomProbAtLeast()
	{
		double sum=0;
		for(int i=x; i<n; i++)
			sum+=probabilities[i];
		return sum;
	}
	
	
	
	private double binomProb()
	{
		return new BigDecimal(MathPlus.nCr(n,x)).multiply(new BigDecimal(Double.toString(Math.pow(p,x)*Math.pow(1-p, n-x)))).doubleValue();
	}
	
	private double binomProb(int x)
	{
		return new BigDecimal(MathPlus.nCr(n,x)).multiply(new BigDecimal(Double.toString(Math.pow(p,x)*Math.pow(1-p, n-x)))).doubleValue();
	}
	
	
	
	
	public void createArrayOfProbabilities()
	{
		//TEMPORARY SOLUTION
		//(Works, is inefficient)
		for(int i=0; i<n; i++)
			probabilities[i]=binomProb(i);
		
		//probabilities[0]=binomProb(0);
		//for(int i=0+1; i<n; i++)
			//probabilities[i]=(p*(n-i-1)/(i*1-p))*probabilities[0];
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
