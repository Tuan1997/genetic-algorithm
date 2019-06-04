package ga_tsp;

import java.util.ArrayList;
import java.util.Collections;

public class Individual{

    private ArrayList<City> individual = new ArrayList<City>();
    private double fitness = 0;
    private double distance = 0;
    //constructor
    public Individual(){
        for (int i = 0; i < CityList.numberOfCities(); i++) {
        	individual.add(null);
        }
    }
    
    public Individual(ArrayList<City> individual){
        this.individual = individual;
    }

    // khoi tao individual
    public void generateIndividual() {
        for (int cityIndex = 0; cityIndex < CityList.numberOfCities(); cityIndex++) {
          setCity(cityIndex, CityList.getCity(cityIndex));
        }
        //hoan vi cac city de thanh 1 individual
        Collections.shuffle(individual);
    }

    public City getCity(int tourPosition) {
        return (City)individual.get(tourPosition);
    }

    public void setCity(int tourPosition, City city) {
        individual.set(tourPosition, city);
        fitness = 0;
        distance = 0;
    }
    // tinh fitness
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/(double)getDistance();
        }
        return fitness;
    }
    // tinh distance
    public double getDistance(){
        double tourDistance = 0;
        for (int cityIndex=0; cityIndex < numCity(); cityIndex++) {
            City fromCity = getCity(cityIndex);
            City destinationCity;
            if(cityIndex+1 < numCity()){
                destinationCity = getCity(cityIndex+1);
            }
            else{
                break;
            }
            tourDistance += fromCity.distanceTo(destinationCity);
        }
        distance = tourDistance;
        City city_0 = new City(0.0,0.0);
        double d1 = city_0.distanceTo(getCity(0));
        double d2 = getCity(numCity() - 1).distanceTo(city_0);
        return distance + d1 + d2;
    }
    // so city
    public int numCity() {
        return individual.size();
    }
    public boolean containsCity(City city){
        return individual.contains(city);
    }
    @Override
    public String toString() {
        String geneString = "Gen :";
        for (int i = 0; i < numCity(); i++) {
            geneString += getCity(i)+" | ";
        }
        return geneString;
    }
}