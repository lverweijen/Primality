
public class AKS2 {
	public static boolean aks(int n) {
		if (n < 2) return false;
		
		// 1. Check if n = a**b
		for(int b = 2; b <= (int)(Math.log(n)/Math.log(2)); b++) {
			double a = Math.pow(n,1d/b);
			if(Math.floor(a) == a) return false;
		}
		
		// 2. Find r such that o_r(n) > (log n)**2
		int logSquared = (int)Math.pow(Math.log(n) / Math.log(2), 2);
		//int logSquared = (int)Math.pow(log2(n), 2);
		
		// maybe not in order log n but seems to work
		int r = 2;
		while (multiplicativeOrder(n, r) <= logSquared) // && r < n)
			r++;
		
		// 3. 1 < gcd(a, n) < n for a < r, composite
		for(int a = 1; a <= r; a++) {
			int g = Utils.gcd(a, n);
			if (1 < g && g < n) 
				return false;
		}
		
		//return true;
		
		
		// 4
		if(n <= r)
			return true;
		
		// 5
		// No efficient way to calculate totient?
		//throw new RuntimeException("TODO");
		
		int upper = (int)(Math.sqrt(Utils.totient(r)) * (Math.log(n) / Math.log(2)));
		for (int a = 1; a < upper; a += 1) {
			if(!Polynomial.polynomialTest(a, n, r))
				return false;
		}
		return true;
	}
	
	private static int multiplicativeOrder(int a, int n) {
		if (Utils.gcd(a, n) != 1)
			return 0;
		
		for(int i = 1; i < n*10000000; i++) {
			//System.out.println("ain" + a + i + n + ": " + Utils.modular_pow(a, i, n));
			if(Utils.modular_pow(a, i, n) == 1)
				return i;
		}
		throw new RuntimeException(
				"Failed to compute multiplicative order");
	}
	
	public static int log2(int bits) {
		// untested
		return 31 - Integer.numberOfLeadingZeros(bits);
	}
	
	public static void main(String[] args) {
		for (int i = 0; i <= 1000; i++)
			System.out.println(i + ": " + AKS2.aks(i));
		
		/*
		// Crashes on i = 3955
		Exception in thread "main" java.lang.RuntimeException: Failed to compute multiplicative order
			at AKS2.multiplicativeOrder(AKS2.java:56)
			at AKS2.aks(AKS2.java:18)
			at AKS2.main(AKS2.java:73)
		 */
	}
}
