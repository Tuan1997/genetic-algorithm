package mfea_cvrp;

public class Customer {
	int numOfTask = 3;
	int[] x = new int [numOfTask];
	int[] y = new int [numOfTask];
	int[] demand = new int [numOfTask];
	int id;
	public Customer(int id) {
		this.id = id;
	
	}
	public int getX(int flag) {
		return this.x[flag];
	}
	public int getY(int flag) {
		return this.y[flag];
	}
	public int getId() {
		return this.id;
	}
	public int getDemand(int flag) {
		return this.demand[flag];
	}
	public double distanceTo(Customer customer,int flag) {
		int xDistance = Math.abs(getX(flag) - customer.getX(flag));
		int yDistance = Math.abs(getY(flag) - customer.getY(flag));
		double distance = Math.sqrt((xDistance*xDistance) + (yDistance*yDistance));
		
		return distance;
	}
	@Override
	public String toString(){
//        return getX()+", "+getY();
		return getId() + "|";
    }
}
