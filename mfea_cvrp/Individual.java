package mfea_cvrp;

import java.util.ArrayList;
import java.util.Collections;

public class Individual {
	int numOfTask = 3;
	private ArrayList<Customer> individual = new ArrayList<Customer>();
	int[] rank = new int[numOfTask];
	int skillFactor = 0;
	double scalarFitness = 0;
	double[] factorialCost = new double[numOfTask];
	int Q = 50;
	
	public Individual() {
		for(int i = 0; i< ListCustomer.numberOfCustomer(); i++ ) {
			individual.add(null);
		}
	}
	public Individual(ArrayList<Customer> individual) {
		this.individual = individual;
	}
	public void setCustomer(int index,Customer customer) {
		individual.set(index, customer);	
		for(int i = 0; i < numOfTask; i++) {
			factorialCost[i] = 0;
		}
		
	}
	public Customer getCustomer(int posittion) {
		return (Customer) individual.get(posittion);
	}
	public void initialazeIndividual() {
		for(int  i =0; i < individual.size(); i++) {
			setCustomer(i, ListCustomer.getCustomer(i));
		}
		Collections.shuffle(individual);
	}
	public double getFactorialCost(int flag) {
		//Initialize F and P		
		factorialCost[flag] = 0;
		double[] F = new double[numCustomer() + 1];
		int[] P = new int[numCustomer() + 1];
		for(int i = 0; i < F.length; i++) {
			F[i] = Double.MAX_VALUE;
			P[i] = 0;
		}
		F[0] = 0;
		int demand;
		double cost;
		for(int i = 1; i < numCustomer() + 1; i++ ) {
			demand = 0;
			cost = 0;
			int j = i;
			Customer depot = new Customer(0);
			depot.x[flag] = 0;depot.y[flag] = 0; depot.demand[flag]= 0; // gia su depot co toa do (0;0)
			while(j < numCustomer() && demand < Q) {
				demand += getCustomer(j).demand[flag];
				if(i == j) {
					cost = depot.distanceTo(getCustomer(j),flag) + getCustomer(j).distanceTo(depot,flag);
				}else {
					cost += getCustomer(j-1).distanceTo(getCustomer(j),flag) - getCustomer(j-1).distanceTo(depot,flag) + getCustomer(j).distanceTo(depot,flag);
				}
				if(demand <= Q) {
					if(F[i- 1] + cost < F[j]) {
						F[j] = F[i-1] + cost;
						P[j] = individual.get(i - 1).getId();
//						P[j] = i - 1;
					}
				}
				j++;
			}
			factorialCost[flag] += cost;
		}
		return factorialCost[flag];
	}
	public void split(int flag) {
		//Initialize F and P		
		factorialCost[flag] = 0;
		double[] F = new double[numCustomer() + 1];
		int[] P = new int[numCustomer() + 1];
		for(int i =0; i < F.length; i++) {
			F[i] = Double.MAX_VALUE;
			P[i] = 0;
		}
		F[0] = 0;
		int demand;
		double cost;
		for(int i = 1; i < numCustomer() + 1; i++ ) {
			demand = 0;
			cost = 0;
			int j = i;
			Customer depot = new Customer(0);
			depot.x[flag] = 0;depot.y[flag] = 0; depot.demand[flag]= 0; // gia su depot co toa do (0;0)
			while(j < numCustomer() && demand < Q) {
				demand += getCustomer(j).demand[flag];
				if(i == j) {
					cost = depot.distanceTo(getCustomer(j),flag) + getCustomer(j).distanceTo(depot,flag);
				}else {
					cost += getCustomer(j-1).distanceTo(getCustomer(j),flag) - getCustomer(j-1).distanceTo(depot,flag) + getCustomer(j).distanceTo(depot,flag);
				}
				if(demand <= Q) {
					if(F[i - 1] + cost < F[j]) {
						F[j] = F[i-1] + cost;
						P[j] = i - 1;

					}
				}
				j++;
			}
		}
		int[] solution = new int[numCustomer()];
		for(int i = 0; i< numCustomer();i++) {
			solution[i] = getCustomer(i).getId();
		}
		int it = numCustomer();
		solution = insert(solution, it, 0);
		while(it > 0) {
			solution = insert(solution, it, 0);
			it = P[it-1];
		}
		for(int i =0; i <P.length;i++) {
			System.out.print(P[i]+" ");
		}
		System.out.println("\n");
		for(int i =0; i < solution.length; i++) {
			System.out.print(solution[i]+" ");
		}
		
	}
	public int[] insert(int[] array, int index, int value) {
		int[] result = new int[array.length + 1];
		for(int i = 0; i < result.length; i++) {
			if(i == index) {
				result[i] = value;
			}else if(i < index){
				result[i] = array[i];
			}else {
				result[i] = array[i -1];
			}
		}
		return result;
	}
	public void restart() {
		skillFactor = 0;
		scalarFitness = 0;
		for(int i = 0; i< numOfTask;i++) {
			rank[i] = 0;
			factorialCost[i] = Double.MAX_VALUE;
			
		}
	}
	public boolean containsCustomer(Customer customer) {
		return individual.contains(customer);
	}
	public int numCustomer() {
		return individual.size();
	}
	public String toString() {
		String chromosome = "chromosome :";
		for(int i = 0;i < individual.size();i++) {
			chromosome += individual.get(i).toString()+"|";
		}
		return chromosome;
	}
}
