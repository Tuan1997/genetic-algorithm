package mfea_wrsn;

public class Sensors {
	int numOfTask = 3;
	double[] x = new double [numOfTask];
	double[] y = new double [numOfTask];
	double[] p = new double [numOfTask];
	double[] to_i = new double[numOfTask];
	int id;
	public Sensors(int id) {
		this.id = id;
	}
	public double getX(int flag) {
		return this.x[flag];
	}
	public double getY(int flag) {
		return this.y[flag];
	}
	public int getId() {
		return this.id;
	}
	public double getP(int flag) {
		return this.p[flag];
	}
	public double getToI(int flag) {
		return this.to_i[flag];
	}
	public double distanceTo(Sensors sensor,int flag) {
		double xDistance = Math.abs(getX(flag) - sensor.getX(flag));
		double yDistance = Math.abs(getY(flag) - sensor.getY(flag));
		double distance = Math.sqrt((xDistance*xDistance) + (yDistance*yDistance));
		
		return distance;
	}
	@Override
	public String toString(){
//        return getX()+", "+getY();
		return getId() + "|";
    }
}
