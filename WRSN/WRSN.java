package WRSN;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WRSN {
	String [] str;
	static int D = 0;
	
	public void loadData(String fileName) throws IOException{
		String content = new String(Files.readAllBytes(Paths.get(fileName)),StandardCharsets.UTF_8);
		str = content.split("\\s+");
		D = str.length/4;
	}
	public void initialization(double[] coordinates_X, double[] coordinates_Y,double[] p) {
		for(int i = 0; i <D;i++) {
			coordinates_X[i] = Double.parseDouble(str[4*i]);
			coordinates_Y[i] = Double.parseDouble(str[4*i + 1]);
			p[i] = Double.parseDouble(str[4*i + 2]);
		}
		
	}
	public double split(Individual indi, double[] coordinates_X,double [] coordinates_Y,double[] p) {
		int[] hamiltonCycle = new int[indi.numSensor()];
		for(int i = 0;i < hamiltonCycle.length; i++) {
			hamiltonCycle[i] = indi.getSensor(i).getIndex();
		}
		int maxcycle = 5;
		Individual[] individual = new Individual[5];
		int n = 0, i = 1;
		double To_I = 0,To_tsp = 0;
		double T_cycle = indi.calTcycle();
//		Chia thanh cac chu trinh con
		while(n < maxcycle) {
			i--;
			individual[n] = null;
			ListSensors listsen = new ListSensors();
			Sensors sen_start = new Sensors(coordinates_X[hamiltonCycle[i]-1],coordinates_Y[hamiltonCycle[i]-1],hamiltonCycle[i],p[hamiltonCycle[i]-1],0);
			listsen.addSensor(sen_start);
			individual[n] = new Individual(listsen.listSensor);
			i++;
			while(i < indi.numSensor()){
				if(individual[n].testConstraint()) {
					Sensors sen = new Sensors(coordinates_X[hamiltonCycle[i]-1],coordinates_Y[hamiltonCycle[i]-1],hamiltonCycle[i],p[hamiltonCycle[i]-1],0);
					listsen.addSensor(sen);
					individual[n] = new Individual(listsen.listSensor);
					i++;
				}
				else {
					break;
				}
			}
			if(i < indi.numSensor()) {
				listsen.removeSensor();
			}
			individual[n] = new Individual(listsen.listSensor);
//			System.out.println("\n\ndisplay hamilton Cycle[" +n+ "] : " + individual[n].toString());
//			System.out.println("Distance  :" + individual[n].getDistance() + "\nTo_tsp : " + individual[n].calToTSP());
			double T = individual[n].calTcycle();
			//tinh to i cua chu trinh con
//			System.out.print("to i : ");
			for(int j = 0; j< individual[n].numSensor();j++) {
				To_I += individual[n].calToI(j, T);
//				System.out.print("| " + Math.round(individual[n].calToI(j,T)*100.0)/100.0);
			}
			To_tsp += individual[n].getDistance()/5;
			
			listsen.removeAllSensors();
			if(i == indi.numSensor()) {
				break;
			}
			n++;
		}
		double To_vac = T_cycle - To_I - To_tsp;
		double fitness = To_vac/T_cycle;
//		System.out.println("\n\nsolution:" + indi.toString());
//		System.out.println("distance:" + indi.getDistance());
//		System.out.println("to tsp:" + To_tsp);
//		System.out.println("T cycle :" + T_cycle);
//		System.out.println("To i :"+ To_I);
//		System.out.println("T vac :" + To_vac);
//		System.out.println("Fitness :" + fitness);
		return fitness;
	}
	public double run(String fileName) throws IOException {
		
		loadData(fileName);
		double[] coordinates_X = new double[D];
     	double[] coordinates_Y = new double[D];
     	double[] p = new double[D];
     	initialization(coordinates_X, coordinates_Y, p);
     	ListSensors list = new ListSensors();
		for(int i= 0; i < D; i++) {
			Sensors sen = new Sensors(coordinates_X[i],coordinates_Y[i],i+1,p[i],0.0);
			list.addSensor(sen);
		}
        Population pop = new Population(100, true,list);
        pop = HPSOGA.evolvePopulation(pop);
        for (int ii = 0; ii < 1000; ii++) {
            pop = HPSOGA.evolvePopulation(pop);
        }
        double fit = split(pop.getFittest(), coordinates_X, coordinates_Y, p);
        return fit;
	}
    public static void main(String[] args) throws IOException {
     	WRSN wrsn = new WRSN();
     	String[] fileName = new String[] {"/home/tuan/Documents/lap/WRSN/u20.txt",
     									"/home/tuan/Documents/lap/WRSN/u20.txt",
     									"/home/tuan/Documents/lap/WRSN/u40.txt"};
     	System.out.println("Start...");
     	try {
            FileWriter fw = new FileWriter("/home/tuan/Documents/lap/WRSN/result_GA.txt");
            for(int i = 0; i< fileName.length;i++ ) {
            	fw.write("\n************************************************");
                fw.write("\nResult for u"+(i+1)+"0.txt");
                double[] result = new double[30];
        		int[] index = new int[30];
        		double average_fitness = 0,max_fitness = Double.MIN_VALUE,min_fitness = Double.MAX_VALUE;
        		int index_max_fitness = 0,index_min_fitness = 0;
                for(int ii = 0; ii < 30; ii++) {
             		index[ii] = ii;
             		result[ii] = wrsn.run(fileName[i]);
             		fw.write("\nLoop "+ii+" : " + result[ii]);
             		average_fitness += result[i];
        			min_fitness = Math.min(min_fitness, result[ii]);
        			max_fitness = Math.max(max_fitness, result[ii]);
             	}
        		for(int ii = 0; ii < 30; ii++) {
        			if(result[ii] == max_fitness) {
        				index_max_fitness = ii;
        			}
        			if(result[ii] == min_fitness) {
        				index_min_fitness = ii;
        			}
        		}
        		fw.write("\nMax fitness = " + max_fitness +" ( loop "+ index_max_fitness+
        				"); Min fitness = "+ min_fitness+"( loop "+index_min_fitness+")\nAverage fitness = "+ average_fitness/30);
            }
            
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Success...");
//     

    }
}