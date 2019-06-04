package mfea_tsp;

import java.util.ArrayList;
import java.util.Collections;


public class Individual{
	private ArrayList<City> individual = new ArrayList<City>();
	int numOfTask = 2;
    double [] factorialCost = new double[2];
    int[]rank = new int[2] ;
    int skillFactor  = 0;
    double scalarFitness = 0;
    
  
    public Individual(){
        for (int i = 0; i < CityList.numberOfCities(); i++) {
        	individual.add(null);
        }
    }
    public Individual(ArrayList<City> individual){
        this.individual = individual;
    }
    public void setCity(int index, City city) {
        individual.set(index, city);
        for(int i = 0; i < numOfTask; i++) {
			factorialCost[i] = 0;
		}
    }
    public City getCity(int position) {
        return (City)individual.get(position);
    }  

    public void initialazeIndividual() {
        for (int i = 0; i < individual.size(); i++) {	
          setCity(i,CityList.getCity(i));	
        }
        Collections.shuffle(individual);
    }
    public boolean containsCity(City city){
        return individual.contains(city);
    }
    public double getFactorialCost(int flag) {
		double sumDistance = 0;
		for(int cityIndex = 0; cityIndex < individual.size(); cityIndex++) {
			if(individual.get(cityIndex).getX(flag) == 0 && individual.get(cityIndex).getY(flag) == 0) {
				continue;
			}else {
				City fromCity = individual.get(cityIndex);
    			City destinationcity;
    			if(cityIndex + 1 < individual.size()){
    				destinationcity = individual.get(cityIndex + 1); 
    			}else {
    				destinationcity = new City(0);
    			}
    			sumDistance += fromCity.distanceTo(destinationcity,flag);
			}
		}
		factorialCost[flag] = sumDistance;
    	City city_0 = new City(0);
		double d = city_0.distanceTo(getCity(0),flag);
		factorialCost[flag]+= d;
    	return factorialCost[flag];
    }
    public void restart() {
    	skillFactor = 0;
    	scalarFitness = 0;
    	for(int i = 0; i < numOfTask; i++) {
    		rank[i] = 0;
    		factorialCost[i] = Double.MAX_VALUE;
    	}
    }
    public int numCity() {
    	return individual.size();
    }  
    public String toString() {
        String geneString = "Gen :";
        for (int i = 0; i < individual.size(); i++) {
            geneString += individual.get(i).toString()+" | ";
        }
        return geneString;
    }

}