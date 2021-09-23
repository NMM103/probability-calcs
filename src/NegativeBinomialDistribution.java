
public class NegativeBinomialDistribution implements Distribution {
	int x, n;
	double p;
	public NegativeBinomialDistribution(){
	}
	
	public NegativeBinomialDistribution(int successes, int trials, double probability) {
		x=successes;
		n=trials;
		p=probability;
	}

	@Override
	public void createArrayOfProbabilities() {
		// TODO Auto-generated method stub

	}

	@Override
	public double calculate(int type) {
		double prob=0;
		
		switch (type) {
		case 0:
			prob=negBinomProbLessThan();
			break;
		case 1:
			prob=negBinomProb();
			break;
		case 2:
			prob=negBinomProbAtLeast();
		default:
		}
		
		return prob;
	}

	private double negBinomProbAtLeast() {
		//TODO
		return 0;
	}

	private double negBinomProb() {
		// TODO Auto-generated method stub
		return 0;
	}
	private double negBinomProb(int x)
	{
		return 0;
	}

	private double negBinomProbLessThan() {
		return 1-negBinomProbAtLeast();
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
