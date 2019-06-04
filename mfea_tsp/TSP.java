package mfea_tsp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class TSP {
	static int D_max = 0;
	String[] str_1;
	String[] str_2;
	static int numOfTask = 2;
	
	public void loadData(String[] fileName) throws IOException {
		String  content = new String(Files.readAllBytes(Paths.get(fileName[0])),StandardCharsets.UTF_8);
    	String  content_1 = new String(Files.readAllBytes(Paths.get(fileName[1])),StandardCharsets.UTF_8);
    	str_1 = content.split("\\s+");
    	str_2 = content_1.split("\\s+");
    	D_max = Math.max(str_1.length, str_2.length)/4;
	}
	public void initialization(double[][] coordinates_X,double [][]coordinates_Y) {
		for(int i = 0; i < D_max; i++) {
    		if(i < str_1.length /4) {
    			coordinates_X[0][i] = Double.parseDouble(str_1[4*i]);
    			coordinates_Y[0][i] = Double.parseDouble(str_1[4*i + 1]);
    		}else {
    			coordinates_X[0][i] = 0 ;coordinates_Y[0][i] = 0;
    		}
    		if(i < str_2.length /4) {
    			coordinates_X[1][i] = Double.parseDouble(str_2[4*i]);
    			coordinates_Y[1][i] = Double.parseDouble(str_2[4*i + 1]);
    		}else{
    			coordinates_X[1][i] = 0 ;coordinates_Y[1][i] = 0;
    		}
    	}
	}
	public void decode(Individual individual,int flag) {
		String [] string ;
		if(flag == 0) {
			string = str_1;
		}else {
			string = str_2;
		}
		for(int i = 0; i < D_max;i++) {
			if(individual.getCity(i).getIndex()< string.length/3) {
				System.out.print(individual.getCity(i) + " | ");
			}
		}
	}public Population initializationMFEA(Population pop,double[][] coordinates_X ,double[][] coordinates_Y) {
		for(int i = 0; i < numOfTask; i++) {
			pop.assignCoordinates(pop.getIndividual(0), coordinates_X[i], coordinates_Y[i],i);
		}
		pop.calFactoricalCost();
		for(int i = 0; i < numOfTask; i++) {
			pop.calFactoricalRank(i);
		}
		pop.calSkillFactor();
		pop.calScalarFitness();
		return pop;
	}
	public void show(Population pop) {
		decode(pop.getFitestWithFlag(0), 0);
		System.out.println("\ndistance task 0 :" + pop.getFitestWithFlag(0).getFactorialCost(0));
		decode(pop.getFitestWithFlag(1), 1);
		System.out.println("\ndistance task 1 :" + pop.getFitestWithFlag(1).getFactorialCost(1));	
	}
	public void initializationList() {
		for(int i = 0; i< D_max;i++) {
			City city = new City(i+1);
			CityList.addCity(city);
		}
	}
    public static void main(String[] args) throws IOException {
//    	String[] fileName = new String[] {"/home/tuan/Documents/lap/data/att48.txt", "/home/tuan/Documents/lap/data/a280.txt"};
    	String[] fileName = new String[] {"/home/tuan/Documents/lap/WRSN/u20.txt", "/home/tuan/Documents/lap/WRSN/u40.txt"};
    	TSP tsp = new TSP();
    	tsp.loadData(fileName);
    	double [][] coordinates_X = new double [numOfTask][D_max];
    	double [][] coordinates_Y = new double [numOfTask][D_max];
    	tsp.initialization(coordinates_X, coordinates_Y);
		tsp.initializationList();
		int populationSize = 100;
		Population pop = new Population(populationSize, true);
		pop = tsp.initializationMFEA(pop, coordinates_X, coordinates_Y);
		tsp.show(pop);
		pop = MFEA.evolvePopulation(pop);
		for(int i=0;i< 10000; i++) {
			pop = MFEA.evolvePopulation(pop);
		}
		System.out.println("\nSolution: ");
		tsp.show(pop);
    }
}