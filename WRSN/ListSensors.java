package WRSN;

import java.util.ArrayList;

public class ListSensors {
	ArrayList<Sensors> listSensor = new ArrayList<Sensors>();

    public  void addSensor(Sensors sensor) {
    	listSensor.add(sensor);
    }
    public  Sensors getSensor(int index){
        return (Sensors)listSensor.get(index);
    }
    public void setSensor(int position, Sensors sensor) {
    	listSensor.set(position, sensor);
    }
    public  int numberOfSensor(){
        return listSensor.size();
    }
    public void removeSensor() {
    	listSensor.remove(numberOfSensor() - 1);
    }
    public void removeAllSensors() {
    	listSensor.removeAll(listSensor);
    }
    
}
