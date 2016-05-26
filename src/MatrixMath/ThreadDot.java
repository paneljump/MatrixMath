package MatrixMath;

public class ThreadDot implements Runnable {
	private double[][] m1,m2,out;
	int i,j,n;
	
	public ThreadDot(double[][] m1, double[][] m2, double[][] out, int i, int j, int n){
		this.m1=m1;
		this.m2=m2;
		this.out=out;
		this.i=i;
		this.j=j;
		this.n=n;
	}
	
	private double findEntry(){  // assuming i is row and j is column
		double a=0.0;
		for(int k=0;k<n;k++){
			a+=m1[i][k]*m2[k][j];
		}
		return a;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		out[i][j]=findEntry();
	}

}
