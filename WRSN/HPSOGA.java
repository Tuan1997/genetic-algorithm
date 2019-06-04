package WRSN;


public class HPSOGA {

    private static final double mutationRate = 0.015;
    private static final int matingPoolSize = 50;
    private static final  double crossRate = 0.1;
    private static final boolean elitism = true;

    // qua trinh tien hoa
    public static Population evolvePopulation(Population pop) {
        Population newPopulation = new Population(pop.populationSize(), false,pop.list);

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
            //crossover
            Individual child;
            //tinh f higher
            double  f_higher = 0;
            if(parent1.getFitness() >= parent2.getFitness()) {
            	f_higher = parent1.getFitness();
            }else {
            	f_higher = parent2.getFitness();
            }
            //tinh f average
            double f_average = pop.getAverageFitness();
            double k1 = 0.9, k2= 0.3,Pc = 0;
            double f_max = pop.getFittest().getFitness();
            //kiem tra rang buoc (33)
            if(f_higher > f_average) {
            	Pc = k1 - k2*(f_max - f_higher)/(f_max - f_average);
            }else {
            	Pc = k1;
            }
            if(Pc > crossRate) {
            	child = crossover(parent1, parent2);
            	newPopulation.saveIndividual(i, child);
            }else {
            	child = crossover(parent2, parent1);
            	newPopulation.saveIndividual(i, child);
            }
        }
        // Mutate 
        for (int i = elitismOffset; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getIndividual(i));
        }
        return newPopulation;
    }

    public static Individual crossover(Individual parent1, Individual parent2) {
//		Individual child = new Individual(parent1.numSensor());
//		 int crossPoint1,crossPoint2;
//	        do {
//	        	crossPoint1 = (int) (Math.random() * parent1.numSensor());
//	        	crossPoint2 = (int) (Math.random() * parent1.numSensor());
//	        }while(crossPoint1 == crossPoint2);
//	      
//	      	int startPos = Math.min(crossPoint1, crossPoint2);
//			int endPos = Math.max(crossPoint1, crossPoint2);
//			int lengthInterval = endPos - startPos + 1;
//			for(int i = 0; i < child.numSensor(); i++ ) {
//				if(i >= startPos && i < endPos ) {
//					child.setSensor(i, parent2.getSensor(i));
//				}
//				else if(i >= (startPos - lengthInterval + 1) &&(i < startPos) ) {// doan gen dich trai
//					child.setSensor(i, parent1.getSensor(i));
//				}
//			}
//			for(int i = 0; i < child.numSensor(); i++) {// dien nhung gen bi trong
//				if(!child.containsSensor(parent1.getSensor(i))) {
//					for(int j = 0; j < child.numSensor(); j++) {
//						if(child.getSensor(j) == null) {
//							child.setSensor(j, parent1.getSensor(i));
//						}
//					}
//				}
//			}
//			return child;
        Individual child = new Individual(parent1.numSensor());
        // crossover 2 point
        int crossPoint1,crossPoint2;
        do {
        	crossPoint1 = (int) (Math.random() * parent1.numSensor());
        	crossPoint2 = (int) (Math.random() * parent1.numSensor());
        }while(crossPoint1 == crossPoint2);
      
      	int startPos = Math.min(crossPoint1, crossPoint2);
		int endPos = Math.max(crossPoint1, crossPoint2);

        for (int i = 0; i < child.numSensor(); i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                child.setSensor(i, parent1.getSensor(i));
            } 
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setSensor(i, parent1.getSensor(i));
                }
            }
        }
        for (int i = 0; i < parent2.numSensor(); i++) {
            if (!child.containsSensor(parent2.getSensor(i))) {
                for (int ii = 0; ii < child.numSensor(); ii++) {
                    if (child.getSensor(ii) == null) {
                        child.setSensor(ii, parent2.getSensor(i));
                        break;
                    }
                }
            }
        }
        return child;
    }
    public static void mutate(Individual individual) {
    	   for(int Pos1=0; Pos1 < individual.numSensor(); Pos1++){
               if(Math.random() < mutationRate){
                   int Pos2 = (int) (individual.numSensor() * Math.random());
                   Sensors sen_1 = individual.getSensor(Pos1);
                   Sensors sen_2 = individual.getSensor(Pos2);
                   individual.setSensor(Pos2, sen_1);
                   individual.setSensor(Pos1, sen_2);
               }
           }
    }
    public static Individual Selection(Population pop) {
    	//tap ung cu vien
        Population matingPool = new Population(matingPoolSize, false,pop.list);
        for (int i = 0; i < matingPoolSize; i++) {
            int randomId = (int) (Math.random() * pop.populationSize());
            matingPool.saveIndividual(i, pop.getIndividual(randomId));
        }
        //chon lay ca the tot nhat trong ung cu vien
        Individual fittest = matingPool.getFittest();
        return fittest;
    }
}
