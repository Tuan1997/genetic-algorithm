package mfea_wrsn;

import java.util.ArrayList;
import java.util.Collections;

public class Individual {
	int numOfTask = 3;
	 ArrayList<Sensors> individual = new ArrayList<Sensors>();
	int[] rank = new int[numOfTask];
	int skillFactor = 0;
	double scalarFitness = 0;
	double[] factorialCost = new double[numOfTask];
	double Emax = 10800,Emin = 540,EM = 1000,v = 5,U = 10,PM =1;
    double[] T_cycle = new double[numOfTask];
    
	public Individual(int numSen) {
		for(int i = 0; i< numSen; i++ ) {
			individual.add(null);
		}
	}
	public Individual(ArrayList<Sensors> individual) {
		this.individual = individual;
	}
	public void setSensor(int index,Sensors sensor) {
		individual.set(index, sensor);	
		for(int i = 0; i < numOfTask; i++) {
			factorialCost[i] = 0;
		}
	}
	public Sensors getSensor(int posittion) {
		return (Sensors) individual.get(posittion);
	}
	public void initialazeIndividual(ListSensors listSensors) {
		for(int  i = 0; i < individual.size(); i++) {
			setSensor(i, listSensors.getSensor(i));
		}
		Collections.shuffle(individual);
	}
	 public double getFactorialCost(int flag) {
			double sumDistance = 0;
			for(int index = 0; index < individual.size(); index++) {
				if(individual.get(index).getX(flag) == 0 && individual.get(index).getY(flag) == 0) {
					continue;
				}else {
					Sensors fromSen = individual.get(index);
					Sensors destinationSen;
	    			if(index + 1 < individual.size()){
	    				destinationSen = individual.get(index + 1); 
	    			}else {
	    				destinationSen = new Sensors(0);
	    			}
	    			sumDistance += fromSen.distanceTo(destinationSen,flag);
				}
			}
			factorialCost[flag] = sumDistance;
			Sensors sen_0 = new Sensors(0);
			double d = sen_0.distanceTo(getSensor(0),flag);
			factorialCost[flag]+= d;
	    	return factorialCost[flag];
	    }
	 public boolean testConstraint(int flag) {
	    	double Em = PM * getFactorialCost(flag)/v;
	    	if(Em > EM) {
	    		return false;
	    	}else{
	    		return true;
	    	}
	    }
	 public double calToTsp(int flag) {
		 double L_tsp = getFactorialCost(flag);
		 double to_tsp = L_tsp/v;
		 return to_tsp;
	 }
	 public double calToI(int index, double T_cycle,int flag) {
		 getSensor(index).to_i[flag] = getSensor(index).getP(flag) * T_cycle/U;
		 return getSensor(index).to_i[flag];
	 }
	public double calTcycle(int flag) {
		T_cycle[flag] = 0;
		double [] T = new double[numSensor()];
    	T_cycle[flag] = (Emax - Emin)/individual.get(0).getP(flag);
//		T_cycle[flag] = (Emax - Emin)/U + (Emax - Emin)/(U - individual.get(0).getP(flag));
    	for(int i = 1; i < numSensor(); i++) {
//    		T[i] = (Emax - Emin)/U + (Emax - Emin)/(U - individual.get(i).getP(flag));
    		T[i] = (Emax - Emin)/individual.get(i).getP(flag);
    		if(T_cycle[flag] >= T[i]) {
    			T_cycle[flag] = T[i];
    		}
    	}
    	return T_cycle[flag];
	}
	public void restart() {
		skillFactor = 0;
		scalarFitness = 0;
		for(int i = 0; i< numOfTask;i++) {
			rank[i] = 0;
			factorialCost[i] = Double.MAX_VALUE;	
		}
	}
	public boolean containsSensor(Sensors customer) {
		return individual.contains(customer);
	}
	public int numSensor() {
		return individual.size();
	}
	public void assignCoordinates(double[] coordinates_X, double[] coordinates_Y,double[] p,int flag) {
		for(int i = 0; i < numSensor();i++) {
			getSensor(i).x[flag] = coordinates_X[getSensor(i).getId() - 1];
			getSensor(i).y[flag] = coordinates_Y[getSensor(i).getId() - 1];
			getSensor(i).p[flag] = p[getSensor(i).getId() - 1];
		}
	}
	public String toString() {
		String chromosome = "chromosome :";
		for(int i = 0;i < individual.size();i++) {
			chromosome += individual.get(i).toString()+"|";
		}
		return chromosome;
	}
}
