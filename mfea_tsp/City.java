package mfea_tsp;

public class City {
	int numOfTask = 2;
    double[] x = new double[numOfTask];
    double[] y = new double[numOfTask];
    int index;
    
    public City(int index){
        this.index = index;
    }
    public double getX(int flag){
        return this.x[flag];
    }
    public double getY(int flag){
        return this.y[flag];
    }
    public int getIndex(){
        return this.index;
    }
//tinh khoang cach giua 2 thanh pho
    public double distanceTo(City city,int flag){
        double xDistance = Math.abs(getX(flag) - city.getX(flag));
        double yDistance = Math.abs(getY(flag) - city.getY(flag));
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return distance;
    }
    
    @Override
    public String toString(){
    	return "[" + getIndex()+ "]";
    }
}