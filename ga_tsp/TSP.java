package ga_tsp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TSP {
	static int D = 0;
	String[] str ;
	public void loadData(String[] flieName) throws IOException{
		String content = new String(Files.readAllBytes(Paths.get(flieName[0])),StandardCharsets.UTF_8);
		str = content.split("\\s+");
		D = str.length/4;
	}
	public void initialization(double [] coordinates_X, double[] coordinates_Y) {
		for(int i =0; i < D; i++) {
			coordinates_X[i] = Double.parseDouble(str[4*i]);
			coordinates_Y[i] = Double.parseDouble(str[4*i + 1]);
		}
	}
	public void initializationList(double[] coordinates_X,double[] coordinates_Y) {
		for(int i = 0; i < D; i++) {
			City city = new City(coordinates_X[i], coordinates_Y[i]);
			CityList.addCity(city);
		}
	}
	public void show(Population pop) {
		System.out.println("display : "+pop.getFittest().toString());
		System.out.println("distance : "+ pop.getFittest().getDistance());
		System.out.println("fitness : "+ pop.getFittest().getFitness());
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TSP tsp = new TSP();
		String[] fileName = new String[] {"/home/tuan/Documents/lap/WRSN/data.txt"};
		tsp.loadData(fileName);
		double[] coordinates_X = new double[D];
		double[] coordinates_Y = new double[D];
		tsp.initialization(coordinates_X, coordinates_Y);
		tsp.initializationList(coordinates_X, coordinates_Y);
		int populationSize = 100;
		Population pop = new Population(populationSize, true);
		tsp.show(pop);
		pop = GA.evolvePopulation(pop);
		for(int i = 0; i < 100; i++) {
			pop = GA.evolvePopulation(pop);
		}
		System.out.println("\nSOLUTION : ");
		tsp.show(pop);
		
	}

}
