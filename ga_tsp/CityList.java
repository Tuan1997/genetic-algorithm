package ga_tsp;

import java.util.ArrayList;

public class CityList {
	private static ArrayList<City> destinationCities = new ArrayList<City>();

    // them 1 city vao list
    public static void addCity(City city) {
        destinationCities.add(city);
    }
    
    // Get a city
    public static City getCity(int index){
        return (City)destinationCities.get(index);
    }
    
    // so city trong list
    public static int numberOfCities(){
        return destinationCities.size();
    }
}
