package WRSN;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CalFit {
    public static void main(String args[]) throws IOException {
    	String[] fileName = new String[] {"/home/tuan/Documents/lap/WRSN/result.txt"};
    	String content = new String(Files.readAllBytes(Paths.get(fileName[0])),StandardCharsets.UTF_8);
		String[] str = content.split("\\s+");
		double[] result = new double[30];
		int[] index = new int[30];
		double average_fitness = 0,max_fitness = Double.MIN_VALUE,min_fitness = Double.MAX_VALUE;
		int index_max_fitness = 0,index_min_fitness = 0;
		for(int i = 0 ; i < 30 ; i++) {
			index[i] = Integer.parseInt(str[4* i + 4]);
			result[i] = Double.parseDouble(str[4*i + 6]);
			average_fitness += result[i];
			min_fitness = Math.min(min_fitness, result[i]);
			max_fitness = Math.max(max_fitness, result[i]);
			System.out.println(index[i]+ "|" + result[i]);
		}
		System.out.println(average_fitness/30 +"|"+ min_fitness +"|"+ max_fitness);
		for(int i = 0; i < 30; i++) {
			if(result[i] == max_fitness) {
				index_max_fitness = i;
			}
			if(result[i] == min_fitness) {
				index_min_fitness = i;
			}
		}
		System.out.println(index_max_fitness +"|"+ index_min_fitness);
    }
}

