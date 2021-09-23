import java.math.BigInteger;

public class MathPlus {
	public static BigInteger nCr (int n, int k)
  	{
  		if(n==k || k==0)
  			return new BigInteger("1");
  		if(n-1==k || k==1)
  			return new BigInteger(Integer.toString(n));
  		BigInteger num = new BigInteger("1");
  		BigInteger denom;
  		for(BigInteger i = new BigInteger(Integer.toString(n)); i.longValue()>n-k; i=i.subtract(i.ONE))
  			num=num.multiply(i);
  		denom = factorial(k);
  		BigInteger result = num.divide(denom);
  		return result;
  	}

  	public static BigInteger factorial (int x)
  	{
  		if (x<=0)
  			return BigInteger.ONE;
  		BigInteger total = new BigInteger("1");
  		BigInteger i = new BigInteger(Integer.toString(x));
  		for (; !i.equals(i.ONE); i=i.subtract(i.ONE))
  			total=total.multiply(i);
  		return total;
  	}
  	
  	public static double ePow (double x){
  		return Math.pow(Math.E, x);
  	}
}
