package mfea_tsp;

import java.util.ArrayList;
import java.util.Collections;

public class Population {
    Individual[] individuals;
    int numOfTask = 2;
    //constructor
    public Population(int populationSize, boolean initialise) {
    	individuals = new Individual[populationSize];
        if (initialise) {
            for (int i = 0; i < populationSize; i++) {
                Individual newIndi = new Individual();
                newIndi.initialazeIndividual();
                saveIndividual(i, newIndi);
            }
        }
    }
    public void calFactoricalCost() {
    	for(int i = 0; i < populationSize();i++) {
    		for(int j = 0; j < numOfTask; j++) {
    			individuals[i].getFactorialCost(j);
    		}
    	}
    }
    public void calFactoricalRank(int flag) {
    	ArrayList<Double> factorical_cost = new ArrayList<Double>();
    	for(int i = 0; i < populationSize();i++) {
    		factorical_cost.add(individuals[i].factorialCost[flag]);
    	}
    	Collections.sort(factorical_cost);
    	for(int i = 0; i < populationSize();i++) {
    		for(int j = 0;j< populationSize();j++ ) {
    			if(factorical_cost.get(i) == individuals[j].factorialCost[flag]) {
    				individuals[j].rank[flag] = i+1;
    			}
    		}
    		
    	}
    }
   public void calSkillFactor() {
	   for(int i =0; i< populationSize();i++) {
		   if(individuals[i].rank[0] < individuals[i].rank[1]) {
			   individuals[i].skillFactor = 1;
		   }else {
			   individuals[i].skillFactor = 2;
		   }
	   }
   }
   public void calScalarFitness() {
	   for(int i = 0;i < populationSize();i++ ) {
		   individuals[i].scalarFitness = Math.min(individuals[i].rank[0], individuals[i].rank[1]);
	   }
   }
   public int getIndex(Individual individual) {
	   int index = 0;
	   for(int i = 0; i < populationSize();i++) {
		   if(individual == individuals[i]) {
			   index = i;
			   break;
		   }
	   }
	   return index;
   }
    public int populationSize() {
        return individuals.length;
    }
    //dua 1 individual vao population
    public void saveIndividual(int index, Individual individual) {
        individuals[index] = individual;
    }
    // tra ve 1 individual
    public Individual getIndividual(int index) {
        return individuals[index];
    }
    public Individual getFittest() {
        Individual fittest = individuals[0];
        for(int i = 0; i < populationSize();i++) {
        	if(fittest.scalarFitness > individuals[i].scalarFitness) {
        		fittest = individuals[i];
        	}
        }
        return fittest;
    }
    
    public Individual getFitestWithFlag(int flag) {
    	Individual fittest = null;
    	for(int i = 0; i < populationSize();i++) {
    		if(individuals[i].skillFactor == flag + 1) {
    			fittest = individuals[i];
    			break;
    		}
    	}
    	for(int i = 0; i < populationSize();i++) {
    		if((fittest.scalarFitness >= individuals[i].scalarFitness) && (individuals[i].skillFactor == flag + 1)) {
        		fittest = individuals[i];
        	}
		}
    	return fittest;
    }
    public void assignCoordinates(Individual individual,double []X, double [] Y,int flag) {
    	for(int i = 0; i< X.length;i++) {
    		individual.getCity(i).x[flag] = X[individual.getCity(i).getIndex() - 1];
    		individual.getCity(i).y[flag] = Y[individual.getCity(i).getIndex() - 1];
    	}
    }
}