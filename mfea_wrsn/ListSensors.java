package mfea_wrsn;

import java.util.ArrayList;

public class ListSensors {
	ArrayList<Sensors> list = new ArrayList<Sensors>();
	public  void addSensor(Sensors sensor) {
		list.add(sensor);
	}
	public  Sensors getSensor(int index) {
		return (Sensors) list.get(index);
	}
	public  int numberOfSensor() {
		return list.size();
	}
	public void setSensor(int posittion, Sensors sensor) {
		list.set(posittion, sensor);
	}
	public void removeSensor() {
		list.remove(numberOfSensor() - 1);
	}
	public void removeAllSensor() {
		list.removeAll(list);
	}
	public  String display() {
		String listCus = "List :";
		for(int i = 0; i < numberOfSensor(); i++ ) {
			listCus += list.get(i).toString();
		}
		return listCus;
	}
}
