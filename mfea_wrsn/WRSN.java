package mfea_wrsn;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WRSN {
	static int numOfTask = 3;
	static int D_max = 0;
	String[] str_1,str_2,str_3;
	public void loadData(String[] fileName)throws IOException {
		String conten_1 = new String(Files.readAllBytes(Paths.get(fileName[0])),StandardCharsets.UTF_8);
		String conten_2 = new String(Files.readAllBytes(Paths.get(fileName[1])),StandardCharsets.UTF_8);
		String conten_3 = new String(Files.readAllBytes(Paths.get(fileName[2])),StandardCharsets.UTF_8);
		str_1 = conten_1.split("\\s+");
		str_2 = conten_2.split("\\s+");
		str_3 = conten_3.split("\\s+");
		D_max = Math.max(Math.max(str_1.length, str_2.length), str_3.length)/4;
		
	}
	
	public void initialization(double [][] coordinates_X, double [][] coordinates_Y,double [][] p,int D_max) {
		for(int i = 0; i< D_max; i++) {
			if(i < str_1.length/4) {
				coordinates_X[0][i] = Double.parseDouble(str_1[4*i]);
				coordinates_Y[0][i] = Double.parseDouble(str_1[4*i + 1]);
				p[0][i] = Double.parseDouble(str_1[4*i + 2]);
			}else {
				coordinates_X[0][i] = 0;coordinates_Y[0][i] = 0;p[0][i] = 0;	
			}
			if(i < str_2.length/4) {
				coordinates_X[1][i] = Double.parseDouble(str_2[4*i]);
				coordinates_Y[1][i] = Double.parseDouble(str_2[4*i + 1]);
				p[1][i] = Double.parseDouble(str_2[4*i + 2]);
			}else {
				coordinates_X[1][i] = 0;coordinates_Y[1][i] = 0;p[1][i] = 0;
			}
			if(i < str_3.length/4) {
				coordinates_X[2][i] = Double.parseDouble(str_3[4*i]);
				coordinates_Y[2][i] = Double.parseDouble(str_3[4*i + 1]);
				p[2][i] = Double.parseDouble(str_3[4*i + 2]);
			}else {
				coordinates_X[2][i] = 0;coordinates_Y[2][i] = 0;p[2][i] = 0;
			}	
		}
	}
	public void initializationMFEA(Population pop,double [][] coordinates_X,double [][] coordinates_Y,double [][] p) {
		for(int i = 0; i < numOfTask; i++) {
			pop.assignCoordinates(pop.getIndividual(0), coordinates_X[i],coordinates_Y[i],p[i],i);
		}
		pop.calFactorialCost();
		for(int i = 0; i < numOfTask; i++) {
			pop.calFactorialRank(i);
		}
		pop.calSKillFactorial();
		pop.calScalarFitness();
	}
	public void initializationList(ListSensors list,int D_max) {
		for(int i =0; i < D_max; i++) {
			Sensors sen = new Sensors(i + 1);
			list.addSensor(sen);
		}
	}
	public Individual decode(Individual individual,int flag,int D_max) {
		int len = 0;
		switch (flag) {
		case 0:
			len = str_1.length/4;
			break;
		case 1 :
			len = str_2.length/4;
			break;
		case 2:
			len = str_3.length/4;
			break;
		default:
			break;
		}
		ListSensors listsen = new ListSensors();
		for(int i = 0; i < D_max;i++) {
			if(individual.getSensor(i).getId() < len) {
				listsen.addSensor(individual.getSensor(i));
			}
		}
		Individual indiDecode = new Individual(listsen.list);
		return indiDecode;
	}
	public double split(Individual indi,int flag,double [][] coordinates_X,double [][] coordinates_Y,double [][] p) {
		int[] hamiltonCycle = new int[indi.numSensor()];
		for(int i = 0;i < hamiltonCycle.length;i++) {
			hamiltonCycle[i] = indi.getSensor(i).getId();
		}
		int maxcycle = 5;
		Individual[] individuals = new Individual[maxcycle];
		int n = 0, i = 1;
		double to_I = 0, to_tsp = 0;
		double T_cycle = indi.calTcycle(flag);
		while(n < maxcycle) {
			i--;
			individuals[n]  = null;
			ListSensors listsen = new ListSensors();
			Sensors sen_start = new Sensors(hamiltonCycle[i]);
			listsen.addSensor(sen_start);
			individuals[n] = new Individual(listsen.list);
			individuals[n].assignCoordinates(coordinates_X[flag], coordinates_Y[flag], p[flag], flag);
			i++;
			while(i < indi.numSensor()) {
				if(individuals[n].testConstraint(flag)) {
					Sensors sen = new Sensors(hamiltonCycle[i]);
					listsen.addSensor(sen);
					individuals[n] = new Individual(listsen.list);
					individuals[n].assignCoordinates(coordinates_X[flag], coordinates_Y[flag], p[flag], flag);
					i++;
				}else {
					break;
				}
			}
			if(i < indi.numSensor()) {
				listsen.removeSensor();
			}
			individuals[n] = new Individual(listsen.list);
			individuals[n].assignCoordinates(coordinates_X[flag], coordinates_Y[flag], p[flag], flag);
//			System.out.println("\ndisplay hamilton Cycle[" +n+ "] : " + individuals[n].toString());
//			System.out.println("Distance  :" + individuals[n].getFactorialCost(flag));
//			System.out.print("to i : ");
			double T = individuals[n].calTcycle(flag);
			for(int j = 0; j< individuals[n].numSensor();j++) {
				to_I += individuals[n].calToI(j, T, flag);
//				System.out.print("| " + Math.round(individuals[n].calToI(j,T_cycle,flag)*100.0)/100.0);
			}
			//tinh to tsp chu trinh con
			to_tsp += individuals[n].getFactorialCost(flag)/5;
			listsen.removeAllSensor();
			if(i == indi.numSensor()) {
				break;
			}
			n++;
		}
		double To_vac = T_cycle - to_I - to_tsp;
//		System.out.println("\n\nsolution:" + indi.toString());
//		System.out.println("distance:" + indi.getFactorialCost(flag));
//		System.out.println("to tsp:" + to_tsp);
//		System.out.println("T cycle :" + T_cycle);
//		System.out.println("To i :" + to_I);
//		System.out.println("T vac :" + To_vac);
//		System.out.println("Fitness :" + To_vac/T_cycle);
		double fitness = To_vac/T_cycle;
		return fitness;
		
		
	}
	public double[]run(String[] fileName) throws IOException {
		loadData(fileName);
		double [][] coordinates_X = new double[numOfTask][D_max];
		double [][] coordinates_Y = new double[numOfTask][D_max];
		double [][] p = new double[numOfTask][D_max];
		initialization(coordinates_X, coordinates_Y, p, D_max);
		ListSensors list = new ListSensors();
		initializationList(list,D_max);
		int populationSize = 100;
		Population pop = new Population(populationSize, true,list);
		initializationMFEA(pop, coordinates_X, coordinates_Y, p);
//		vcrp.show(pop);
		pop = MFEA.evolvePopulation(pop);
		for(int i = 0 ; i< 100;i++) {
			pop = MFEA.evolvePopulation(pop);
		}	
//		vcrp.show(pop);
		double[] fitness = new double[numOfTask];
		for(int i = 0 ; i < numOfTask; i++) {
			fitness[i] = split(decode(pop.getFittestWithFlag(i), i,D_max), i,coordinates_X,coordinates_Y,p);
		}
		return fitness;
	}
	public static void main(String[] args) throws IOException {
		String[] fileName = new String[] {"/home/tuan/Documents/lap/WRSN/u20.txt", 
				"/home/tuan/Documents/lap/WRSN/u30.txt",
				"/home/tuan/Documents/lap/WRSN/u40.txt"};
		WRSN wrsn = new WRSN();
		
		int loop = 30;
		System.out.println("Start...");
		try {
            FileWriter fw = new FileWriter("/home/tuan/Documents/lap/WRSN/result_MFEA.txt");
            double[][] fit = new double[loop][numOfTask];
            for(int i = 0; i < loop; i++) {
            	 fit[i] = wrsn.run(fileName);
            }
            for(int i = 0; i< fileName.length;i++ ) {
            	fw.write("\n************************************************");
                fw.write("\nResult for u"+(i+1)+"0.txt");
                double[] result = new double[loop];
        		int[] index = new int[loop];
        		double average_fitness = 0,max_fitness = Double.MIN_VALUE,min_fitness = Double.MAX_VALUE;
        		int index_max_fitness = 0,index_min_fitness = 0;
                for(int ii = 0; ii < loop; ii++) {
             		index[ii] = ii;
             		result[ii] = fit[ii][i];
             		fw.write("\nLoop "+ii+" : " + result[ii]);
             		average_fitness += result[i];
        			min_fitness = Math.min(min_fitness, result[ii]);
        			max_fitness = Math.max(max_fitness, result[ii]);
             	}
        		for(int ii = 0; ii < 30; ii++) {
        			if(result[ii] == max_fitness) {
        				index_max_fitness = ii;
        			}
        			if(result[ii] == min_fitness) {
        				index_min_fitness = ii;
        			}
        		}
        		fw.write("\nMax fitness = " + max_fitness +" ( loop "+ index_max_fitness+
        				"); Min fitness = "+ min_fitness+"( loop "+index_min_fitness+")\nAverage fitness = "+ average_fitness/30);
            }
            
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Success...");
	}
	
}
