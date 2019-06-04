package mfea_cvrp;

import java.util.ArrayList;
import java.util.Collections;


public class Population {
	Individual[] individuals;
	int numOfTask = 3;
	public Population(int populationSize, boolean initialize) {
		individuals = new Individual[populationSize];
		if(initialize) {
			for(int i = 0; i < populationSize;i++) {
				Individual newIndi = new Individual();
				newIndi.initialazeIndividual();
				saveIndividual(i,newIndi);
			}
		}
	}
	public int populationSize() {
		return individuals.length;
	}
	public int getIndex(Individual individual) {
		int index = 0;
		for(int i = 0; i< populationSize();i++) {
			if(individual == individuals[i]) {
				index = i;
				break;
			}
		}
		return index;
	}
	public void saveIndividual(int index, Individual individual) {
		individuals[index] = individual;
	}
	public Individual getIndividual(int index) {
		return individuals[index];
	}
	
	// Definition of MFEA
	public void calFactorialCost() {
		for(int i = 0; i < populationSize(); i++ ) {
			for(int j = 0; j < numOfTask;j++ ) {
				individuals[i].getFactorialCost(j);
			}
		}
	}
	public void calFactorialRank(int flag) {
		ArrayList<Double> factorial_cost = new ArrayList<Double>();
		for(int i =0 ; i < populationSize(); i++) {
			factorial_cost.add(individuals[i].factorialCost[flag]);
		}
		Collections.sort(factorial_cost);
		for(int i =0; i < populationSize(); i++) {
			for(int  j = 0; j < populationSize();j++) {
				if(factorial_cost.get(i) == individuals[j].factorialCost[flag]) {
					individuals[j].rank[flag] = i + 1;
				}
			}
		}
	}
	public void calSKillFactorial() {
		for(int i = 0; i< populationSize(); i++) {
			int minRank = Math.min(Math.min(individuals[i].rank[0], individuals[i].rank[1]), individuals[i].rank[2]);
			if(minRank == individuals[i].rank[0]) {
				individuals[i].skillFactor = 1;
			}else if(minRank == individuals[i].rank[1]) {
				individuals[i].skillFactor = 2;
			}else {
				individuals[i].skillFactor = 3;
			}
		}
	}
	public void calScalarFitness() {
		for(int i = 0; i < populationSize();i++) {
			individuals[i].scalarFitness = Math.min(Math.min(individuals[i].rank[0], individuals[i].rank[1]), individuals[i].rank[2]);
			// de scalar fitness = min(rank) de tien theo doi;
		}
	}
	public Individual getFittest() {
		calScalarFitness();
		Individual fittest = individuals[0];
		for(int i = 1;i < populationSize(); i++) {
			if(fittest.scalarFitness > individuals[i].scalarFitness) {
				fittest = individuals[i];
			}
		}
		return fittest;
	}
    public Individual getFittestWithFlag(int flag) {
    	Individual fittest = null;
    	for(int i = 0; i < populationSize();i++) {
    		if(individuals[i].skillFactor == flag + 1) {
    			fittest = individuals[i];
    			break;
    		}
    	}
    	for(int i = 1; i < populationSize();i++) {
    		if((fittest.scalarFitness >= individuals[i].scalarFitness) && (individuals[i].skillFactor == flag + 1)) {
        		fittest = individuals[i];
        	}
		}
    	return fittest;
    }
	public void assignCoordinates(Individual individual, int[] X, int []Y,int[] demand,int flag) {
		for(int i = 0; i < X.length; i++) {
			individual.getCustomer(i).x[flag] = X[individual.getCustomer(i).getId() - 1];
			individual.getCustomer(i).y[flag] = Y[individual.getCustomer(i).getId() - 1];
			individual.getCustomer(i).demand[flag] = demand[individual.getCustomer(i).getId() - 1];
		}
	}
	
}
