package mfea_cvrp;

public class VCRP {
	static int numOfTask = 3;
	static int [] X_1 = new int[] {50,590,450,100,650,560,100,520,800,300,750,360,850,980,470,100,420,400,200,800};
	static int [] Y_1 = new int[] {950,480,900,200,750,560,500,540,700,100,150,900,700,50,700,600,20,750,550,480};
	static int [] demand_1 = new int[] {18,26,11,30,21,19,15,16,29,26,37,16,12,31,8,19,20,13,15,22};
	
	static int[] X_2 = new int[] {35,41,35,55,15,25,20,10,55,30,20,50,30,15,40};
	static int[] Y_2 = new int[] {35,49,17,45,20,30,30,30,50,43,60,60,65,35,90};
	static int[] demand_2 = new int[] {10,7,13,19,24,23,12,6,19,30,23,17,19,31,17};
	
	static int[] X_3 = new int[] {4,4,3,6,1,2,5,2,9,8};
	static int[] Y_3 = new int[] {1,4,5,8,0,4,7,6,9,3};
	static int[] demand_3 = new int[] {12,25,17,28,25,16,9,14,30,20};
	
	static int D_max = Math.max(Math.max(X_1.length, X_2.length), X_3.length);
	
	public void initialization(int [][] coordinates_X,int [][] coordinates_Y,int [][] demand,int D_max) {
		for(int i = 0; i< D_max; i++) {
			if(i < X_1.length) {
				coordinates_X[0][i] = X_1[i];coordinates_Y[0][i] = Y_1[i];demand[0][i] = demand_1[i];
			}else {
				coordinates_X[0][i] = 0;coordinates_Y[0][i] = 0;demand[0][i] = 0;	
			}
			if(i < X_2.length) {
				coordinates_X[1][i] = X_2[i];coordinates_Y[1][i] = Y_2[i];demand[1][i] = demand_2[i];
			}else {
				coordinates_X[1][i] = 0;coordinates_Y[1][i] = 0;demand[1][i] = 0;
			}
			if(i < X_3.length) {
				coordinates_X[2][i] = X_3[i];coordinates_Y[2][i] = Y_3[i];demand[2][i] = demand_3[i];
			}else {
				coordinates_X[2][i] = 0;coordinates_Y[2][i] = 0;demand[2][i] = 0;
			}	
		}
	}
	public void initializationMFEA(Population pop,int [][] coordinates_X,int [][] coordinates_Y,int [][] demand) {
		for(int i = 0; i < numOfTask; i++) {
			pop.assignCoordinates(pop.getIndividual(0), coordinates_X[i],coordinates_Y[i],demand[i],i);
		}
		pop.calFactorialCost();
		for(int i = 0; i < numOfTask; i++) {
			pop.calFactorialRank(i);
		}
		pop.calSKillFactorial();
		pop.calScalarFitness();
	}
	public void initializationList() {
		for(int i =0; i < D_max; i++) {
			Customer cus = new Customer(i + 1);
			ListCustomer.addCustomer(cus);
		}
	}
	public void show(Population pop) {
		System.out.println("\nfittest task 1:" + pop.getFittestWithFlag(0));
		System.out.println("cost : " + pop.getFittestWithFlag(0).getFactorialCost(0));
		System.out.println("fittest task 2:" + pop.getFittestWithFlag(1).toString());
		System.out.println("cost : " + pop.getFittestWithFlag(1).getFactorialCost(1));	
		System.out.println("fittest task 3:" + pop.getFittestWithFlag(2).toString());
		System.out.println("cost : " + pop.getFittestWithFlag(2).getFactorialCost(2));	
	}
	public void decode(Individual indi,int flag) {
		//do somthing
	}
	public static void main(String[] args) {
		VCRP vcrp = new VCRP();
		int [][] coordinates_X = new int[numOfTask][D_max];
		int [][] coordinates_Y = new int[numOfTask][D_max];
		int [][] demand = new int[numOfTask][D_max];

		vcrp.initialization(coordinates_X, coordinates_Y, demand, D_max);
		vcrp.initializationList();
		int populationSize = 100;
		Population pop = new Population(populationSize, true);
		vcrp.initializationMFEA(pop, coordinates_X, coordinates_Y, demand);
//		vcrp.show(pop);
//		pop = MFEA.evolvePopulation(pop);
//		for(int i = 0 ; i< 100;i++) {
//			pop = MFEA.evolvePopulation(pop);
//		}
//		System.out.println("\n********************************************");	
//		vcrp.show(pop);
		System.out.println(pop.getIndividual(0).toString());
		pop.getIndividual(0).split(0);
	}
	
}
