package MatrixMath;


/*
 * As of 5/26/16, MatrixMultiplier finds the dot product of two matrices.
 * It has a simple dot product method which is appropriate for smaller matrices
 * in single-thread situations, and it has a thread-safe (?) multithreaded
 * method, which runs faster for larger matrices.
 * 
 */

public class MatrixMultiplier {
	private double[][] m1,m2;
	private boolean canDot;
	private int n,d1,d2; 
	
	public MatrixMultiplier(double[][] mat1, double[][] mat2){
		this.setM1M2(mat1, mat2);
	}
	
	public synchronized void setM1M2(double[][] mat1, double[][] mat2){
		m1=mat1;
		m2=mat2;
		canDot=checkDimensions();
	}
	
	private boolean checkDimensions(){
		int c1=m1[0].length;
		int c2=m2.length;
		if(c1==c2){  //warning: old values remain if new values can't be dotted
			this.n=c1;
			this.d1=m1.length;
			this.d2=m2[0].length;
			return true;
		}
		return false;
	}
	
	private double findEntry(int i,int j){  // assuming i is row and j is column
		double a=0.0;
		for(int k=0;k<n;k++){
			a+=m1[i][k]*m2[k][j];
		}
		return a;
	}
	
	public double[][] dotProduct(){
		if(this.canDot==false){
			double[][] badOut={{0.0}};
			return badOut;
		}
		int i,j;
		double[][] out=this.makeZeroOutMatrix();
		for(i=0;i<d1;i++)
			for(j=0;j<d2;j++){
				out[i][j]=findEntry(i,j);
			}
		
		return out;
	}
	
	public synchronized double[][] syncDotProduct(MatrixMultiplier mm){
		if(this.canDot==false){
			double[][] badOut={{0.0}};
			return badOut;
		}
		System.out.println("d1="+d1+", d2="+d2);
		double[][] out=this.makeZeroOutMatrix();
		ThreadThisRow[] threadPool=new ThreadThisRow[d1];
		for(int i=0;i<d1;i++){
			threadPool[i]=new ThreadThisRow(i,m1,m2,out);
			threadPool[i].start();
		}
		
		for(int i=0;i<d1;i++){
			try{
				threadPool[i].join();
			}
			catch(InterruptedException e){
				
			}
		}
		
		return out;
	}
	
	private double[][] makeZeroOutMatrix(){
		double[][] out=new double[d1][d2];
		for(int i=0;i<d1;i++)
			for(int j=0;j<d2;j++)
				out[i][j]=0.0;
		return out;
	}
	
	private class ThreadThisRow extends Thread{
		private int index;
		private double[][] m1,m2,out;
		
		public ThreadThisRow(int index, double[][] m1, double[][] m2, double[][] out){
			this.index=index;
			this.m1=m1;
			this.m2=m2;
			this.out=out;
			
		}
		
		public void run(){
			for(int i=0;i<d2;i++)
				for(int j=0;j<n;j++){
					out[index][i]+=m1[index][j]*m2[j][i];
				}
		}
		
	}
	
	public void printMatrix(double[][] m){
		for(int i=0;i<m.length;i++){
			for(int j=0;j<m[0].length;j++){
				System.out.print(m[i][j] + " ");
			}
			System.out.println("");
		}
			
	}

}
