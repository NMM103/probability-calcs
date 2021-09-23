import java.util.ArrayList;

public class PoissonDistribution implements Distribution {
	private int x;
	private double lambda;
	private double probabilities[];
	
	public PoissonDistribution()
	{
		x=0;
		lambda=0;
		//createArrayOfProbabilities();
	}
	
	public PoissonDistribution(int successes, double eCoeff){
		x=successes;
		lambda=eCoeff;
		//createArrayOfProbabilities();
	}

	@Override
	public void createArrayOfProbabilities() {
		ArrayList<Double> pList = new ArrayList<Double>();
		
		int i=0;
		do
		{
			//System.out.println("Loop iteration: "+i);
			pList.add(poissonProb(i));
			i++;
		}
		while(pList.get(pList.size()-1)>=0.0001);
		//System.out.println("Out of the loop");
		
		probabilities = new double[pList.size()];
		for (int j=0; j<probabilities.length; j++)
			probabilities[j]=pList.get(i);

	}

	@Override
	public double calculate(int type) {
		double prob=0;
		
		switch (type) {
		case 0:
			prob=poissonProbLessThan();
			break;
		case 1:
			prob=poissonProb();
			break;
		case 2:
			prob=poissonProbAtLeast();
		default:
		}
		
		return prob;
	}

	private double poissonProbLessThan() {
		return 1-poissonProbAtLeast();
	}
	
	private double poissonProb() {
		//return poissonProb(x);
		return Math.pow(lambda,x)*MathPlus.ePow(-1*lambda)/MathPlus.factorial(x).longValue();
	}
	
	private double poissonProbAtLeast() {
		// TODO Auto-generated method stub
		return 0;
	}

	private double poissonProb(int x){
		double numA=Math.pow(lambda,x);
		double numB=Math.pow(Math.E, -lambda);
		double denom=MathPlus.factorial(x).doubleValue();
		return numA*numB/denom;
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
