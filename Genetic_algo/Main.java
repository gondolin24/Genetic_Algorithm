package Genetic_algo;
import java.util.*;
import java.io.*;
public class Main {


	public static void main(String[] args) {

		double crossRate = .90; // must be from 0 to 1;
		double mutationRate = .15; // must be from 0 to 1;
		int POP_size = 443; // empircally found 
		int Number_of_Cities = 49; // depening on the data set
		int max_generation_size = 700; // generation size
		
		/*
		 * GA_version:
		 * if 1>>runs the normal GA
		 * if 0>>runs personal GA
		 * 
		 */
		int Ga_version = 1;
		
		/*
		 * xover:
		 * if 1>>runs uniform crossover on default
		 * if 0>>runs two point cross over on default
		 * 
		 */
		int xover = 1;	
		// call on new program 				
	// DATA SETS are "set1" and "set2"
			
		int count = 0;		
		Main program  = new Main();
		program.read_data("set2",Number_of_Cities,crossRate,mutationRate,POP_size,max_generation_size,Ga_version,xover);
			
		
///***PRINT PARAMETERS FOR OUTPUT**//
		System.out.println("GA PARAMETERS");
		System.out.println("Cross over Rate: "+ crossRate);
		System.out.println("Mutaion Rate: "+ mutationRate);
		System.out.println("Population Size: "+ POP_size);
		System.out.println("Max number of generations: "+ max_generation_size);
		if(Ga_version==1){
		if(xover==0){
				System.out.println("two point cross over");	
			}else{
				System.out.println("Uniform crossover");	
			}
		}
		else{
			System.out.println("Personal GA");
		}
	}
	
	
	public void read_data(String file_name, int NumberOfCities,double crossRate,double mutationRate,
			int pop_size, int max_gen,int ga, int crossx){
		// THIS READED IN THE DATA SET & calls on the GA CLASS
		try {
			
			
			int[][] cityArray = new int[NumberOfCities][2]; // array of points
			String line;
			int count = 0;
			String ArrayofStrings[];
			//
			Scanner in = new Scanner(new FileReader(file_name));
			while(in.hasNext()){
				
				line = in.nextLine();
				ArrayofStrings = line.split("\\s+");
				//end of file
				if(ArrayofStrings[0].equals("EOF")){
					break;
				}else{
					double d2 = Double.parseDouble(ArrayofStrings[2]);
					double d1 = Double.parseDouble(ArrayofStrings[1]);
					//cityArray[count][0] = Integer.valueOf(ArrayofStrings[1]);
					cityArray[count][0]  = (int)d1;
					cityArray[count][1] = (int)d2;
				}
				
			count ++;
			}
			// call genetic class
			Genetic_Class genetics = new Genetic_Class();
			// based on the GA selected(personal or standard) it will pick one
			genetics.makeMatrix(cityArray);
			
			
			if(ga==0){
				// calls class
				genetics.makeMatrix(cityArray);
				genetics.GA_own(pop_size,max_gen,crossRate,mutationRate);
			}
			else{
				//calls class
				genetics.makeMatrix(cityArray);
				genetics.GA_1(pop_size,max_gen,crossRate,mutationRate,crossx);
			}
			
	
		} catch (FileNotFoundException e) {
			// Error Handling
			System.out.println("No such file found");
		}
		
	}
	
	


}
