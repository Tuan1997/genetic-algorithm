package WRSN;

public class Population {

    Individual[] individuals;
    ListSensors list;
    //constructor khoi tao 1 population
    public Population(int populationSize, boolean initialise,ListSensors list) {
        this.list = list;
    	individuals = new Individual[populationSize];
        if (initialise) {
            for (int i = 0; i < populationSize(); i++) {
                Individual newTour = new Individual(list.numberOfSensor());
                newTour.generateIndividual(list);
                saveIndividual(i, newTour);
            }
        }
    }
    //dua 1 individual vao 1 populaion
    public void saveIndividual(int index, Individual individual) {
        individuals[index] = individual;
    }
    public Individual getIndividual(int index) {
        return individuals[index];
    }
    // dua individual tot nhat
    public Individual getFittest() {
        Individual fittest = individuals[0];
        for (int i = 1; i < populationSize(); i++) {
            if (fittest.getFitness() <= getIndividual(i).getFitness()) {
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }
    public double getAverageFitness() {
    	double averageFit = 0;
    	for(int i = 0; i < populationSize(); i++) {
    		averageFit += individuals[i].getFitness();
    	}
    	return averageFit/populationSize();
    }
    public int populationSize() {
        return individuals.length;
    }
}

