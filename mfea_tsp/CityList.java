package mfea_tsp;

import java.util.ArrayList;

public class CityList {
	
	private static ArrayList<City> destinationCities = new ArrayList<City>();
    public static void addCity(City city) {
        destinationCities.add(city);
    }
    public static City getCity(int index){
        return (City)destinationCities.get(index);
    }
    //so thanh pho
    public static int numberOfCities(){
        return destinationCities.size();
    }
    public static String display() {
    	String geneString = "Gen :";
        for (int i = 0; i < numberOfCities(); i++) {
            geneString += destinationCities.get(i).toString()+" | ";
        }
        return geneString;
    }
}