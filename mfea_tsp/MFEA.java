package mfea_tsp;

import java.util.Random;

public class MFEA {

	private static final double mutationRate = 0.005;
	private static final double rmp = 0.2;
	private static final int matingPoolSize = 50;
	private static final boolean elitism = true;
	private static final int numOfTask = 2;
	
	// thuat toan tien hoa 
	public static Population evolvePopulation(Population pop) {
        Population offspringPopulation = new Population(pop.populationSize(),false); 
        int elitismOffset = 0;
        if (elitism) {
        	for(int i = 0; i< numOfTask; i++) {
        		Individual indi = pop.getFitestWithFlag(i);
        		int sf = indi.skillFactor;
        		indi.restart();
        		indi.skillFactor = sf;;
        		for(int ii = 0; ii < numOfTask; ii++) {
        			if(indi.skillFactor == i + 1) {
        				indi.getFactorialCost(i);
        				break;
        			}
        		}
        		offspringPopulation.saveIndividual(i, pop.getFitestWithFlag(i));
        	}
        elitismOffset = numOfTask;
      }
        for (int i = elitismOffset; i < offspringPopulation.populationSize(); i++) {
            // Select parents
            Individual parent1 = Selection(pop);
            Individual parent2 = Selection(pop);
	        Individual child;
	        Random rn = new Random();
	        if(parent1.skillFactor == parent2.skillFactor && rn.nextDouble() > rmp) {
	        	child = crossover(parent1, parent2);
	         }else {
	        	  if(Math.random() < 0.5) {
	        		  child = parent1;
	        	  }else {
	        		  child = parent2;
	        	  }      	  
	        }
	        Individual offspring;
	        if(parent1.scalarFitness < parent2.scalarFitness) {
	        	offspring = parent1;
	        }else {
	        	offspring = parent2;
	        }
	        if(offspring.scalarFitness < child.scalarFitness) {
	        	offspringPopulation.saveIndividual(i, offspring);
	        }else {
	        	 offspringPopulation.saveIndividual(i, child);
	        }
	    }
        for(int i = elitismOffset; i < offspringPopulation.populationSize(); i++) {
        	mutate(offspringPopulation.getIndividual(i));
        }
        for(int i = 0; i < numOfTask; i++) {
			offspringPopulation.calFactoricalRank(i);
		}
        offspringPopulation.calScalarFitness();     
        return offspringPopulation;
    }
	//crossover 
    public static Individual crossover(Individual parent1, Individual parent2) {
        Individual child = new Individual();
      int crossPoint1,crossPoint2;
      do {
      	crossPoint1 = (int) (Math.random() * parent1.numCity());
      	crossPoint2 = (int) (Math.random() * parent1.numCity());
      }while(crossPoint1 == crossPoint2);
      
      int startPos = Math.min(crossPoint1, crossPoint2);
		int endPos = Math.max(crossPoint1, crossPoint2);
        for (int i = 0; i < parent1.numCity(); i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setCity(i, parent1.getCity(i));
            } 
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }
        for (int i = 0; i < parent1.numCity(); i++) {
            if (!child.containsCity(parent2.getCity(i))) {
                for (int ii = 0; ii < parent1.numCity(); ii++) {
                    if (child.getCity(ii) == null) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
     // cal skill factorial
        child.restart();
        if(Math.random() < 0.5) {
        	child.skillFactor = parent1.skillFactor;
        }else {
        	child.skillFactor = parent2.skillFactor;
        }
        //assign factorial cost 
        for(int i = 0; i < numOfTask; i++) {
        	if(child.skillFactor == i + 1) {
        		child.getFactorialCost(i);
        		break;
        	}
        }
        return child;
    }
    // mutation
    public static void mutate(Individual individual) {
    	int sf = individual.skillFactor;
 	   for(int Pos1=0; Pos1 < individual.numCity(); Pos1++){
            if(Math.random() < mutationRate){
                int Pos2 = (int) (individual.numCity() * Math.random());
                City city_1 = individual.getCity(Pos1);
                City city_2 = individual.getCity(Pos2);
                individual.setCity(Pos2, city_1);
                individual.setCity(Pos1, city_2);
            }
        }
 	   individual.restart();
 	   individual.skillFactor = sf;
 	   for(int i = 0; i < numOfTask; i++) {
        		if(individual.skillFactor == i + 1){
        			individual.getFactorialCost(i);
        			break;
        		}
        }
	   
    }
    //selection
    public static Individual Selection(Population pop) {
        Population matingPool = new Population(matingPoolSize, false);
        for (int i = 0; i < matingPoolSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            matingPool.saveIndividual(i, pop.getIndividual(randomId));
        }
        Individual fittest = matingPool.getFittest();
    	
        return fittest;
    }
//    public static Individual Selection(Population pop) {
//    	double sumFit = 0;
//    	double current = 0;
//    	int index = 0;
//    	for(int i = 0; i < pop.populationSize(); i++) {
//    		sumFit += 1/pop.getIndividual(i).scalarFitness;
//    	}
//    	double pick = Math.random()*( sumFit + 1);
//    	for(int i = 0; i < pop.populationSize(); i++) {
//    		current += 1/pop.getIndividual(i).scalarFitness;
//    		if(current > pick) {
//    			index = i;
//    			break;
//    		}
//    	}
//    	Individual fit = pop.getIndividual(index);
//    	return fit;
//    }

}
