package ga_tsp;

public class City {
	double x;
    double y;
//    public City(){
//        this.x = (int)(Math.random()*200);
//        this.y = (int)(Math.random()*200);
//    }
    public City(double x, double y){
        this.x = x;
        this.y = y;
    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }
    //tinh khoang cach 2 thanh pho
    public double distanceTo(City city){
        double xDistance = Math.abs(getX() - city.getX());
        double yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );
        
        return distance;
    }
    
    @Override
    public String toString(){
        return getX()+", "+getY();
    }
}
