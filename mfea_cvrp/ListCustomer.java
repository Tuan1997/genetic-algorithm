package mfea_cvrp;

import java.util.ArrayList;

public class ListCustomer {
	private static ArrayList<Customer> list = new ArrayList<Customer>();
	public static void addCustomer(Customer customer) {
		list.add(customer);
	}
	public static Customer getCustomer(int index) {
		return (Customer) list.get(index);
	}
	public static int numberOfCustomer() {
		return list.size();
	}
	public static String display() {
		String listCus = "List :";
		for(int i = 0; i < numberOfCustomer(); i++ ) {
			listCus += list.get(i).toString();
		}
		return listCus;
	}
}
