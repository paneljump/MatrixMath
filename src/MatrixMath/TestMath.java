package MatrixMath;
import MatrixMath.MatrixMultiplier;
import java.util.Random;

public class TestMath {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] m1={{1.0,2.0,3.0},{4.0,5.0,6.0}};
		double[][] m2={{7.0,8.0,9.0,10.0},{11.0,12.0,13.0,14.0},
				{15.0,16.0,17.0,18.0}};
		
		double[][] m3;
		
		MatrixMultiplier mm=new MatrixMultiplier(m1,m2);
		m3=mm.dotProduct();
		mm.printMatrix(m3);
		m3=mm.syncDotProduct(mm);
		System.out.println("");
		mm.printMatrix(m3);
		
		long startTime,endTime;
		
		int i,j;
		Random r=new Random();
		double[][] m1big= new double[150][5000];
		for(i=0;i<150;i++)
			for(j=0;j<5000;j++){
				m1big[i][j]=(double) (r.nextInt(20));
			}
		double[][] m2big= new double[5000][300];
		for(i=0;i<5000;i++)
			for(j=0;j<300;j++){
				m2big[i][j]=(double) (r.nextInt(20));
			}
		
		/*
		mm.printMatrix(m1big);
		System.out.println("");
		mm.printMatrix(m2big);
		System.out.println("");
		*/
		
		mm.setM1M2(m1big, m2big);
		
		startTime=System.currentTimeMillis();
		m3=mm.syncDotProduct(mm);
		endTime=System.currentTimeMillis();
		//mm.printMatrix(m3);
		System.out.println("Synced dotprod took "+ (endTime-startTime) + " milliseconds.");
		
		startTime=System.currentTimeMillis();
		m3=mm.dotProduct();
		endTime=System.currentTimeMillis();
		//mm.printMatrix(m3);
		System.out.println("Dotprod took "+ (endTime-startTime) + " milliseconds.");

	}

}
