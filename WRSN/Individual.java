package WRSN;

import java.util.ArrayList;
import java.util.Collections;

public class Individual{

    ArrayList<Sensors> individual  = new ArrayList<Sensors>();
    double fitness = 0;
    double distance = 0;
    int numSen;
    double Emax = 10800,Emin = 540,EM = 1000,v = 5,U = 10,PM =1;
    double T_cycle = 0;
    //constructor
    public Individual(int numSen){
        for (int i = 0; i < numSen; i++) {
        	individual.add(null);
        }
    }
    public Individual(ArrayList<Sensors> individual) {
    	this.individual = individual;
    }
    
    // khoi tao individual
    public void generateIndividual(ListSensors listSensors) {
        for (int index = 0; index < individual.size(); index++) {
        	setSensor(index, listSensors.getSensor(index));
        }
        //hoan vi cac city de thanh 1 individual
        Collections.shuffle(individual);
    }

    public Sensors getSensor(int position) {
        return (Sensors)individual.get(position);
    }

    public void setSensor(int position, Sensors sensor) {
        individual.set(position, sensor);
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
        for (int index = 0; index < numSensor(); index++) {
        	Sensors fromSen = getSensor(index);
        	Sensors destinationSen;
            if(index + 1 < numSensor()){
            	destinationSen = getSensor(index+1);
            }
            else{
                destinationSen = new Sensors(0.0,0.0,0,0.0,0.0);
            }
            tourDistance += fromSen.distanceTo(destinationSen);
        }
        distance = tourDistance;
        Sensors sen_0 = new Sensors(0.0,0.0,0,0.0,0.0);
        double d = sen_0.distanceTo(getSensor(0));
        distance += d;
        return distance;
    }
    public int numSensor() {
        return individual.size();
    }
    public boolean containsSensor(Sensors sensor){
        return individual.contains(sensor);
    }
    public boolean testConstraint() {
    	double Em = PM * getDistance()/v;
    	if(Em > EM) {
    		return false;
    	}else{
    		return true;
    	}
    }
    public double calTcycle() {
    	double [] T = new double[numSensor()];
    	T_cycle = (Emax - Emin)/individual.get(0).getP();
    	for(int i = 1; i < numSensor(); i++) {
//    		T[i] = (Emax - Emin)/U + (Emax - Emin)/(U - individual.get(i).getP());
    		T[i] = (Emax - Emin)/individual.get(i).getP();
    		if(T_cycle >= T[i]) {
    			T_cycle = T[i];
    		}
    	}
    	return T_cycle;
    }
    public double calToTSP() {
    	double L_Tsp = getDistance();
    	double to_tsp = L_Tsp/v;
    	return to_tsp;
    }
    public double calToI(int index, double T_cycle) {
    	getSensor(index).to_i = getSensor(index).getP() * T_cycle /U;
    	return getSensor(index).to_i;
    }
    
    public String toString() {
        String geneString = "Gen :";
        for (int i = 0; i < numSensor(); i++) {
            geneString += getSensor(i)+" | ";
        }
        return geneString;
    }
}