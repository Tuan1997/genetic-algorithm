package ga_tsp;


public class GA {

    private static final double mutationRate = 0.015;
    private static final int matingPoolSize = 50;
    private static final boolean elitism = true;

    // qua trinh tien hoa
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.populationSize(), false);

        // giu 1 individual tot nhat
        int elitismOffset = 0;
        if (elitism) {
            newPopulation.saveIndividual(0, pop.getFittest());
            elitismOffset = 1;
        }
         //cac vong lap select va crossover
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            // Select parents
            Individual parent1 = Selection(pop);
            Individual parent2 = Selection(pop);
//             Crossover parents
            Individual child = crossover(parent1, parent2);
//             Add child to new population
            Individual offspring;
            if(parent1.getFitness() > parent2.getFitness()) {
            	offspring = parent1;
            }else {
            	offspring = parent2;
            }
            if(offspring.getFitness() > child.getFitness()) {
            	newPopulation.saveIndividual(i,offspring);
            }else {
            	newPopulation.saveIndividual(i,child);
            }
            
        }

        // Mutate 
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    public static Individual crossover(Individual parent1, Individual parent2) {
        Individual child = new Individual();
        // crossover 2 point
        int crossPoint1,crossPoint2;
        do {
        	crossPoint1 = (int) (Math.random() * parent1.numCity());
        	crossPoint2 = (int) (Math.random() * parent1.numCity());
        }while(crossPoint1 == crossPoint2);
      
      	int startPos = Math.min(crossPoint1, crossPoint2);
		int endPos = Math.max(crossPoint1, crossPoint2);

        for (int i = 0; i < child.numCity(); i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setCity(i, parent1.getCity(i));
            } 
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }
        for (int i = 0; i < parent2.numCity(); i++) {
            if (!child.containsCity(parent2.getCity(i))) {
                for (int ii = 0; ii < child.numCity(); ii++) {
                    if (child.getCity(ii) == null) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
    }
    public static void mutate(Individual individual) {
    	   for(int Pos1=0; Pos1 < individual.numCity(); Pos1++){
               if(Math.random() < mutationRate){
                   int Pos2 = (int) (individual.numCity() * Math.random());
                   City city1 = individual.getCity(Pos1);
                   City city2 = individual.getCity(Pos2);
                   individual.setCity(Pos2, city1);
                   individual.setCity(Pos1, city2);
               }
           }
    }
//    public static Individual Selection(Population pop) {
//    	//tap ung cu vien
//        Population matingPool = new Population(matingPoolSize, false);
//        for (int i = 0; i < matingPoolSize; i++) {
//            int randomId = (int) (Math.random() * pop.populationSize());
//            matingPool.saveIndividual(i, pop.getIndividual(randomId));
//        }
//        //chon lay ca the tot nhat trong ung cu vien
//        Individual fittest = matingPool.getFittest();
//        return fittest;
//    }
    public static Individual Selection(Population pop) {
    	double sumFit = 0;
    	double current = 0;
    	int index = 0;
    	for(int i =0; i < pop.populationSize(); i++) {
    		sumFit += pop.getIndividual(i).getFitness();
    	}
    	double pick = Math.random()*( sumFit + 1);
    	for(int i = 0; i < pop.populationSize(); i++) {
    		current += pop.getIndividual(i).getFitness();
    		if(current > pick) {
    			index = i;
    			break;
    		}
    	}
    	Individual fit = pop.getIndividual(index);
    	return fit;
    }
}
