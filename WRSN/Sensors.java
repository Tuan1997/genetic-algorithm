package WRSN;

public class Sensors {
	double x;
    double y;
    int index;
	double p;
	double to_i;
    public Sensors(double x, double y,int index,double p, double to_i){
        this.x = x;
        this.y = y;
        this.index = index;
        this.p = p;
        this.to_i = to_i;
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    public int getIndex() {
    	return this.index;
    }
    public double getP() {
    	return this.p;
    }
    public double getToI() {
    	return this.to_i;
    }
    public double distanceTo(Sensors sensor){
        double xDistance = Math.abs(getX() - sensor.getX());
        double yDistance = Math.abs(getY() - sensor.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return distance;
    }
    
    @Override
    public String toString(){
//        return getX()+", "+getY();
    	return getIndex() + "";
    }
}
