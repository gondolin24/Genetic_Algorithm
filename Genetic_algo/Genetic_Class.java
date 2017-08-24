package Genetic_algo;

import java.awt.*;
import java.util.*;

public class Genetic_Class {

	int[][] temp_buffer;
	int[][] weightMatrix;
	int[][] CurrentPopulation;
	int pop_size;
	int genM;
	double crossOverrate;
	double mutationRate;
	int minElite;// index of the elite in the population
	int cross_type;
	
	// This makes matrix of weights
	public void makeMatrix(int[][] cityArray){
		int n = cityArray.length;
		// Sets weight matrix with dimensions
	weightMatrix = new int[n][n];
	
	for(int i = 0; i<n;i++)	{	
		for(int j = i; j<n;j++){	
			int x0 = cityArray[i][0];
			int x1=cityArray[j][0];
			int y0 = cityArray[i][1];;
			int y1=cityArray[j][1];
			int d = distOfPoints(x0,x1,y0,y1);
			
			// sets the matrix with weights
			weightMatrix[i][j] = d;
			weightMatrix[j][i] = d;
			}
		
		}
	
	
	}
	
	public void GA_1(int Pop_size, int max_gen, double cross, double mutat_rate, int xs){		
		
		//sets glopbal variables
		pop_size = Pop_size;
		genM = max_gen;
		cross_type = xs;
		crossOverrate = cross;
		mutationRate = mutat_rate;
		temp_buffer = new int[pop_size][weightMatrix.length];
		
		// generates random population
		GenerateInitialPopulation(pop_size);
		//evaluates totaek distance
		evaluateDistance();
		
		//prints initial state
		Print(0);
		int count =1;
		//while loop for the generation
		while(count!=(genM)){
			// selects >>cross over>>mutate>>puts in temop buffer
		Selection_crossOver();
		//puts temp buffer into new array
		copyOver();
		//evaluates new pop distance
		evaluateDistance();
		//prints results
		Print(count);
		//loops back
		count++;
		}
	}
	
	public void GA_own(int Pop_size, int max_gen, double cross, double mutat_rate){
		//sets glopbal variables
		pop_size = Pop_size;
		genM = max_gen;
		crossOverrate = cross;
		mutationRate = mutat_rate;
		temp_buffer = new int[pop_size][weightMatrix.length];
		// generates random population
		GeneratePop(pop_size);
		//evaluates totaek distance
		evaluateDistance();
		//prints initial state
		Print(0);
		int count =1;
		//while loop for the generation
		while(count!=(genM)){
			// selects >>cross over>>mutate>>puts in temop buffer
		Selection_crossOver_own();
		//puts temp buffer into new array
		copyOver();
		//evaluates new pop distance
		evaluateDistance();
			
		//prints results
		Print(count);
		//loops back
		count++;
		}
	}
	
	public void GenerateInitialPopulation(int n){
		//set array parameters
		int len = weightMatrix.length;
		// makes population matrix
		CurrentPopulation = new int[n][len+1];
		// make unsorted genes
		int[] unsorted = new int[len];
		for(int i = 0; i<len;i++){
			unsorted[i] = i;
		}
				
		// set initial chromosomes for the population at random
		for(int i = 0;i<n;i++){
			RandomizeChromosomes(unsorted);
			for(int j = 0; j<len;j++){
				CurrentPopulation[i][j] = unsorted[j];	
			}
		}
		
		
	}
	
	public void GeneratePop(int n){
		//Genertate chromosomes
		
		
		int len = weightMatrix.length;
		CurrentPopulation = new int[n][len+1];
		// make unsorted genes
		int[] unsorted = new int[len];
		for(int i = 0; i<len;i++){
			unsorted[i] = i;
		}
		
		
		for(int i = 0;i<n;i++){
			Setchromes(unsorted); //randomizes genes
			for(int j = 0; j<len;j++){
				CurrentPopulation[i][j] = unsorted[j];	// sets genes
			}
		}	
		
	}
	
	public void Setchromes(int[] array){
		// create a bit mask
		int len = array.length;
		Random rand = new Random();
		int[][] bit_mask  = new int[3][array.length];
		
		
		for(int i = 0;i<len;i++){
			if(array[i]==0){
				bit_mask[2][i] = len+1;
			
			}else{
				bit_mask[2][i] = array[i];
			}
			
		}
		
		for(int i=0;i<array.length;i++){
			int switchy = rand.nextInt(10);
			if(switchy%2==0){
				bit_mask[0][i] = 0;
			}else{
				bit_mask[0][i] = 1; // passes from the 1 bit
				bit_mask[1][i] = bit_mask[2][i];
			}
			
		}
		
		

		
		
		// if bitmask is zero, we fill with infoirmation
		for(int j = 0; j <len;j++){
			if(bit_mask[1][j]==0){
				
				int temp = filler(2,1,bit_mask);
				bit_mask[1][j]=temp;
	
				
				
		}
	
		}
		
			
		// put zeros back
		for(int i = 0; i<3;i++){
			for(int j = 0; j<len;j++){
				
			if(bit_mask[i][j]==len+1){
				bit_mask[i][j]=0;
			}
				
				}			
			}
		

		//add diversity if begining is 0, put somehting else
		if(bit_mask[1][0]==0){
			int ind = rand.nextInt(len-2)+1;
			
			int temp = bit_mask[1][ind];
			 bit_mask[1][ind] = bit_mask[1][0];
			 bit_mask[1][0]=temp;
			 
		}
		
		for(int i = 0; i<len;i++){
			int temp =  bit_mask[1][i];
				array[i] = temp;	
			}
			
	
		
		
		
		
		
		
	}
	//fills in missing bits
	public int filler( int index, int index_2,int[][] mat){
		int[] arrayZero = new int[weightMatrix.length];
		
		for(int i = 0;i<weightMatrix.length;i++){
			arrayZero[i] = mat[2][i];
		}
		
		
		RandomizeChromosomes(arrayZero);
		
		int indexreturn = 0;
		int found  = 0;
		int count;
		for(int i = 0; i<weightMatrix.length;i++){
			int temp =arrayZero[i];
	count = 0;
	while(count<weightMatrix.length){
		if(temp == mat[index_2][count]){
			break;
		}
		     
		if(count==(weightMatrix.length-1)&&mat[index_2][count]!=temp){
			found = 1;
		}
		count++;
	}
			if(found ==1){
				indexreturn=temp;
				break;
			}
			
			}

		return indexreturn;
		
	}
	
	public void RandomizeChromosomes(int[] array){
		// know as the fisher yates shuffle to rnadomize chromosomes
		int n = array.length;
		for (int i = 0; i < array.length; i++) {
		    int random = i + (int) (Math.random() * (n - i));
		    int rand = array[random];
		    array[random] = array[i];
		    array[i] = rand;
		}	
	}
	
	
	
	public void Print(int gen){
		// THis DOes a best fit print out as well as 
		//picks the best chromosome.
	double min = CurrentPopulation[0][weightMatrix.length];
	double sum = 0;
	int max_index = 0;
	double avg;
		for(int i = 0; i<pop_size;i++)	{
			
			if(CurrentPopulation[i][weightMatrix.length]<min){
				min=CurrentPopulation[i][weightMatrix.length];
				max_index = i; // best chrom index
				
			}
		sum = sum+Math.sqrt(CurrentPopulation[i][weightMatrix.length]);			
		
	}
		minElite = max_index;// best chrom index
	   avg = sum/pop_size;
		min = Math.sqrt(min);
		
		System.out.print("GEN "+ gen);
	   System.out.printf(" Top fitness %.4f", min);
	   System.out.printf(", Average fitness %.4f%n", avg);
	   
	  if(gen == (genM-1)){
		  System.out.println("END RUN" );
		   System.out.printf("Best distance is %.4f%n", min);
	  
		  
		   System.out.println("Cities to travel in order: " );
	   for(int i = 0; i<weightMatrix.length;i++){
			   System.out.print(""+CurrentPopulation[max_index][i]+" ");
		   }
	   System.out.print(""+CurrentPopulation[max_index][0]);
		   System.out.println("");
	  }
	  
	}
	
	
	
	public void Selection_crossOver_own(){
		// own selctrion method
		
		
		double elite_p = .005;//fouin empirically
		int indexing  = (int) ((int)pop_size*elite_p); // get elit population number
		
		if(indexing%2==1){
				
				indexing--;
		
		}
		
		
		for(int j = 0; j<indexing;j++){
			Eiteism(j); // fills with eilits
		}
		
		//the rest of the population is filled with cross over
	for(int i = (indexing);i<pop_size;i++){
			
		
			
			if(i%2==0){
				RankedRoulette(i, i+1);
			}
		}
		
	}
	public void RankedRoulette(int index1,int index2){
		int parent_1;
		int parent_2;
		double rand_1 = Math.random();
		double rand_2 = Math.random();
		
		// we are allocatin 30% for the best chromosome
		//parent_1
		if(rand_1<.3){
			parent_1 = minElite; 
		}else{
			
			parent_1 = (int) (rand_2*(pop_size));
		}
		//parent_2
		if(rand_2<.3){
			parent_2 = minElite; 
		}else{
			
			parent_2 = (int) (rand_2*(pop_size));
		}
		
		//preform crossover
		Arithmic_crossOver(parent_1,parent_2,index1,index2);
		
	}
	
	
	
	
	public void Selection_crossOver(){
				
		double elite_p = .005; // fouind empirically
		int indexing  = (int) ((int)pop_size*elite_p); // slecects elit population
		
		if(indexing%2==1){
			
				indexing--;	//adjusts for better fit	
		}
				
		for(int j = 0; j<indexing;j++){
			Eiteism(j); // put elit in pop
		}
		
		
		//the rest of the population is filled with cross over
		for(int i = (indexing);i<pop_size;i++){
			
			
			if(i%2==0){
				TSelection(i, i+1);
			}
		}
		
		
	}
	
	
	public void TSelection(int place_1,int place_2){
		//tounremetn selection. 
		//picks 4 random. the thop 2 based on besft fir become parents
		//pick n numbers
		int parent1;
		int parent2;
		
		int nSelect = 4;
		Random rand = new Random();
int[] top = new int[nSelect];
for(int i = 0; i<nSelect;i++){
	top[i] = rand.nextInt(pop_size);
}
	parent1 = top[0];
	parent2 = top[1];
	
	// checks the top min;
	for(int i = 2; i<nSelect;i++){
		
		if(CurrentPopulation[i][weightMatrix.length]<CurrentPopulation[parent1][weightMatrix.length] || CurrentPopulation[parent1][weightMatrix.length]<CurrentPopulation[parent1][weightMatrix.length]){
			
			if(CurrentPopulation[parent1][weightMatrix.length]>CurrentPopulation[parent2][weightMatrix.length]){
				parent1 = top[i];
			}else{
				parent2 = top[i];
			}
			
			
		}
	}
	

	
	if(cross_type == 0){
		two_point_CrossOver(parent1, parent2,place_1,place_2);

	}
	else{
		UniformCrossOver(parent1, parent2,place_1,place_2);

	}
	
	}
	
	// This methos puts elites back into population
	public void Eiteism(int place){
		// place is index in the temp buffer;
		for(int i = 0; i<weightMatrix.length;i++){
			temp_buffer[place][i] =  CurrentPopulation[minElite][i];
		}
		
	}
	
	
public void Arithmic_crossOver(int par1, int par2,int place_1,int place_2 ){
	//does cross over and puts in temp buffer
	int len = weightMatrix.length;
	int[][] crossMatrix = new int[5][len];
	// create two bit masks(random and do an (and ) function. 
	

	int bit_mask_1[]  = new int[len]; // bit mask_1
	int bit_mask_2[]  = new int[len]; // bit mask_1
	int and_bit_mask_2[]  = new int[len]; // bit mask_1
	
	
	Random rand = new Random();
	int value;
	int ct = 0; // count
	// sets random bits;
	while(ct<2){
	for(int i = 0; i<len;i++){
		
		if((rand.nextInt(10))%2==0){
			value = 0;
		}else{
		value = 1;	
		}
		if(ct==0){
			 bit_mask_1[i] = value;
		}else{
			 bit_mask_2[i] = value;
		}
		
	}
	ct++;
	}// end while
	
	// do an and statment
	
	for(int i = 0;i<len;i++){
	if(bit_mask_2[i]==bit_mask_1[i]){
		and_bit_mask_2[i] = 1;
	}
	}
	
	
	
	//data put into temp array for easier crossover
	for(int i = 0; i<3;i++){
		for(int j = 0; j<len;j++){
			
			switch(i){
			case 0:
				//passes parent one
				if(CurrentPopulation[par1][j]==0){
					crossMatrix[i][j] = len+1;
					
				}else{
					crossMatrix[i][j] = CurrentPopulation[par1][j];
				}
				
				break;
			case 1:
				if(CurrentPopulation[par2][j]==0){
					crossMatrix[i][j] = len+1;
					
				}else{
					crossMatrix[i][j] = CurrentPopulation[par2][j];
				}
				break;
			case 2:
				//makes bit mask
			
				int r = and_bit_mask_2[i];
				if(r == 1){
				
					
					 crossMatrix[3][j]=crossMatrix[0][j] ;
					 crossMatrix[4][j]=crossMatrix[1][j] ;
				}
				crossMatrix[i][j] = r;
				break;
			
			}			
		}
		
		// od the uniforem crossover;
		
	
	}
	
	

	
	for(int j = 0; j <len;j++){
		if(crossMatrix[2][j]==0){
			
			int temp = findMEone(0,4,crossMatrix);
			crossMatrix[4][j]=temp;
			temp = findMEone(1,3,crossMatrix);
			crossMatrix[3][j]=temp;
			
			
	}

	}
	
	
	
	for(int i = 0; i<5;i++){
		for(int j = 0; j<len;j++){
			
		if(crossMatrix[i][j]==len+1){
			crossMatrix[i][j]=0;
		}
			
			}			
		}
		
		// od the uniforem crossover;
		
	double mut_1 = Math.random();
	double mut_2 = Math.random();
	int swap_1;
	int swap_2;
	int temp;
	//MUTATe using reciprical exchange
	if(mut_1<mutationRate){
		//mutat
		swap_1 = rand.nextInt(weightMatrix.length);
		swap_2 = rand.nextInt(weightMatrix.length);
		temp = crossMatrix[3][swap_1];
		crossMatrix[3][swap_1] = crossMatrix[3][swap_2];
		crossMatrix[3][swap_2] = temp;
		
	}
	if(mut_2<mutationRate){
		swap_1 = rand.nextInt(weightMatrix.length);
		swap_2 = rand.nextInt(weightMatrix.length);
		temp = crossMatrix[4][swap_1];
		crossMatrix[4][swap_1] = crossMatrix[4][swap_2];
		crossMatrix[4][swap_2] = temp;
		
	}

	//corss over threshold
	double cross_1 = Math.random();
	double cross_2 = Math.random();
	if(place_1 == pop_size-1){
		if(cross_1>crossOverrate){
			for(int i = 0; i<weightMatrix.length;i++){
							temp_buffer[place_1][i] = crossMatrix[0][i];
						}
						// no cross
					}
					else{
			for(int i = 0; i<weightMatrix.length;i++){
				temp_buffer[place_1][i] = crossMatrix[3][i];
						}
						// pass
						
					}
		
	}
	else{
		
		
		
		
	if(cross_1>crossOverrate){
		for(int i = 0; i<weightMatrix.length;i++){
			temp_buffer[place_1][i] = crossMatrix[0][i];
		}
		// no cross
	}
	
	
	
	
	else{
for(int i = 0; i<weightMatrix.length;i++){
temp_buffer[place_1][i] = crossMatrix[3][i];
		}
		// pass
		
	}
	
	
	
	if(cross_2>crossOverrate){
for(int i = 0; i<weightMatrix.length;i++){
temp_buffer[place_2][i] = crossMatrix[1][i];
		}
		// no cross
	}
	else{
		for(int i = 0; i<weightMatrix.length;i++){
			temp_buffer[place_2][i] = crossMatrix[4][i];
		}
		
	}
	
	
	
	}
			
	
}
	






	public void UniformCrossOver(int par1, int par2,int place_1,int place_2){
		
		//does cross over and puts in temp buffer
		int len = weightMatrix.length;
		int[][] crossMatrix = new int[5][len];
		
		

		
		Random rand = new Random();
		// puts dat ainto temp array for easier crossover.
		
		for(int i = 0; i<3;i++){
			for(int j = 0; j<len;j++){
				
				switch(i){
				case 0:
					//passes parent one
					if(CurrentPopulation[par1][j]==0){
						crossMatrix[i][j] = len+1;
						
					}else{
						crossMatrix[i][j] = CurrentPopulation[par1][j];
					}
					
					break;
				case 1:
					if(CurrentPopulation[par2][j]==0){
						crossMatrix[i][j] = len+1;
						
					}else{
						crossMatrix[i][j] = CurrentPopulation[par2][j];
					}
					break;
				case 2:
					//makes bit mask
				
					int r = rand.nextInt(100);
					if(r%2 == 1){
						r = 0;
					}
					else{
						r = 1;
						 crossMatrix[3][j]=crossMatrix[0][j] ;
						 crossMatrix[4][j]=crossMatrix[1][j] ;
					}
					crossMatrix[i][j] = r;
					break;
				
				}			
			}
			
			// od the uniforem crossover;
			
		
		}//end of for
		
		
	
		//fills in missing values into children
		for(int j = 0; j <len;j++){
			if(crossMatrix[2][j]==0){
				
				int temp = findMEone(0,4,crossMatrix);
				crossMatrix[4][j]=temp;
				temp = findMEone(1,3,crossMatrix);
				crossMatrix[3][j]=temp;
				
				
		}
	
		}
		
		
		
		for(int i = 0; i<5;i++){
			for(int j = 0; j<len;j++){
				
			if(crossMatrix[i][j]==len+1){
				crossMatrix[i][j]=0;
			}
				
				}			
			}
			
			// od the uniforem crossover;
			
		double mut_1 = Math.random();
		double mut_2 = Math.random();
		int swap_1;
		int swap_2;
		int temp;
		//MUTATe using reciprical exchange
		if(mut_1<mutationRate){
			//mutat
			swap_1 = rand.nextInt(weightMatrix.length);
			swap_2 = rand.nextInt(weightMatrix.length);
			temp = crossMatrix[3][swap_1];
			crossMatrix[3][swap_1] = crossMatrix[3][swap_2];
			crossMatrix[3][swap_2] = temp;
			
		}
		if(mut_2<mutationRate){
			swap_1 = rand.nextInt(weightMatrix.length);
			swap_2 = rand.nextInt(weightMatrix.length);
			temp = crossMatrix[4][swap_1];
			crossMatrix[4][swap_1] = crossMatrix[4][swap_2];
			crossMatrix[4][swap_2] = temp;
			
		}
	
		//cross over rate. if meets threshold, parent will pass. if not
		//chrildren do. 
		double cross_1 = Math.random();
		double cross_2 = Math.random();
		if(place_1 == pop_size-1){
			if(cross_1>crossOverrate){
				for(int i = 0; i<weightMatrix.length;i++){
								temp_buffer[place_1][i] = crossMatrix[0][i];
							}
							// no cross
						}
						else{
				for(int i = 0; i<weightMatrix.length;i++){
					temp_buffer[place_1][i] = crossMatrix[3][i];
							}
							// pass
							
						}
			
		}
		else{
		if(cross_1>crossOverrate){
for(int i = 0; i<weightMatrix.length;i++){
				temp_buffer[place_1][i] = crossMatrix[0][i];
			}
			// no cross
		}
		else{
for(int i = 0; i<weightMatrix.length;i++){
	temp_buffer[place_1][i] = crossMatrix[3][i];
			}
			// pass
			
		}
		if(cross_2>crossOverrate){
for(int i = 0; i<weightMatrix.length;i++){
	temp_buffer[place_2][i] = crossMatrix[1][i];
			}
			// no cross
		}
		else{
			for(int i = 0; i<weightMatrix.length;i++){
				temp_buffer[place_2][i] = crossMatrix[4][i];
			}
			
		}
		}
		
	}
	
	public void two_point_CrossOver(int par1, int par2,int place_1,int place_2){
		//does cross over and puts in temp buffer
		int len = weightMatrix.length;
		int[][] crossMatrix = new int[5][len];
		
				
		Random rand = new Random();
		//select two random points;
		//generates a point in the first 2/3
		int max = (int) (weightMatrix.length*(.66));
		int min = 0;
		int p1 = rand.nextInt((max - min));
		//genetate second point
		min = max;
		max = weightMatrix.length-1;
		int p2 = rand.nextInt((max - min))+min;
		
		// create bit mask bases on two points
		
		int[] bit_array  = new int[len];
		for(int i = 0; i<p1;i++){
			bit_array[i] = 1;
		}
		for(int i = p2; i<len;i++){
			bit_array[i] = 1;
		}
		
		
		
		// assigns value fo easier criss over to temp matrix
		for(int i = 0; i<3;i++){
			for(int j = 0; j<len;j++){
				
				switch(i){
				case 0:
					//passes parent one
					if(CurrentPopulation[par1][j]==0){
						crossMatrix[i][j] = len+1;
						
					}else{
						crossMatrix[i][j] = CurrentPopulation[par1][j];
					}
					
					break;
				case 1:
					if(CurrentPopulation[par2][j]==0){
						crossMatrix[i][j] = len+1;
						
					}else{
						crossMatrix[i][j] = CurrentPopulation[par2][j];
					}
					break;
				case 2:
					//makes bit mask
				
					int r = bit_array[j] ;
					crossMatrix[i][j] = r;
					if(r==1){
						 crossMatrix[3][j]=crossMatrix[0][j] ;
						 crossMatrix[4][j]=crossMatrix[1][j] ;
						
					}
						
					
					
					break;
				
				}			
			}
			
				
		}
		// fill in the missing 
		for(int j = 0; j <len;j++){
			if(crossMatrix[2][j]==0){
				
				int temp = findMEone(0,4,crossMatrix);
				crossMatrix[4][j]=temp;
				temp = findMEone(1,3,crossMatrix);
				crossMatrix[3][j]=temp;
				
				
		}
	
		}
		
		// put zeros back
		for(int i = 0; i<5;i++){
			for(int j = 0; j<len;j++){
				
			if(crossMatrix[i][j]==len+1){
				crossMatrix[i][j]=0;
			}
				
				}			
			}
		
		
		
		double mut_1 = Math.random();
		double mut_2 = Math.random();
		int swap_1;
		int swap_2;
		int temp;
		//MUTATe using reciprical exchange based on treshhold
		//mutation for both children
		if(mut_1<mutationRate){
			//mutat
			swap_1 = rand.nextInt(weightMatrix.length);
			swap_2 = rand.nextInt(weightMatrix.length);
			temp = crossMatrix[3][swap_1];
			crossMatrix[3][swap_1] = crossMatrix[3][swap_2];
			crossMatrix[3][swap_2] = temp;
			
		}
		if(mut_2<mutationRate){
			swap_1 = rand.nextInt(weightMatrix.length);
			swap_2 = rand.nextInt(weightMatrix.length);
			temp = crossMatrix[4][swap_1];
			crossMatrix[4][swap_1] = crossMatrix[4][swap_2];
			crossMatrix[4][swap_2] = temp;
			
		}
	
		
		double cross_1 = Math.random();
		double cross_2 = Math.random();
		//HERE there the cross over.
		//if it meets the threshold, it will be place=d in new pop.
		// if not, children will go one and not die.
		
		if(place_1 == pop_size-1){
			if(cross_1>crossOverrate){
				for(int i = 0; i<weightMatrix.length;i++){
								temp_buffer[place_1][i] = crossMatrix[0][i];
							}
							// no cross
						}
						else{
				for(int i = 0; i<weightMatrix.length;i++){
					temp_buffer[place_1][i] = crossMatrix[3][i];
							}
							// pass
							
						}
			
		}
		else{
		if(cross_1>crossOverrate){
for(int i = 0; i<weightMatrix.length;i++){
				temp_buffer[place_1][i] = crossMatrix[0][i];
			}
			// no cross
		}
		else{
for(int i = 0; i<weightMatrix.length;i++){
	temp_buffer[place_1][i] = crossMatrix[3][i];
			}
			// pass
			
		}
		if(cross_2>crossOverrate){
for(int i = 0; i<weightMatrix.length;i++){
	temp_buffer[place_2][i] = crossMatrix[1][i];
			}
			// no cross
		}
		else{
			for(int i = 0; i<weightMatrix.length;i++){
				temp_buffer[place_2][i] = crossMatrix[4][i];
			}
			
		}
		}
		
		
	}
	
	
	// THis fins and integer that does not exisit within a lsit
public int findMEone( int index, int index_2,int[][] mat){
	int indexreturn = 0;
	int found  = 0;
	int count;
	for(int i = 0; i<weightMatrix.length;i++){
		int temp =mat[index][i];
		count = 0;

while(count<weightMatrix.length){
	if(temp == mat[index_2][count]){
		break;
	}
	     
	if(count==(weightMatrix.length-1)&&mat[index_2][count]!=temp){
		found = 1;
	}
	count++;
}
		if(found ==1){
			indexreturn=temp;
			break;
		}
		
		}

	return indexreturn;
}
	
	// this method will find the distance of two points
	public int distOfPoints(int x0, int x1, int y0, int y1 ){
	
		int distance = ((x1-x0)*(x1-x0)) + ((y1-y0)*(y1-y0));
		/* since computing the square root is an expensive formula
		 we will not compute it , but it is still proportional 
		 the the distance. 
		*/
				return distance;
	}
	
	
	// buts temp buffer to current population
	public void copyOver(){
		for(int i = 0;i<pop_size;i++){
			for(int j = 0;j<weightMatrix.length;j++){
				CurrentPopulation[i][j] = temp_buffer[i][j];
			}
			
		}
	}
	
	//this method will check the total distance
	public void evaluateDistance(){
		int sum = 0;
		for(int i = 0;i<pop_size;i++){
			for(int j = 0; j<(weightMatrix.length);j++){
				if(j==(weightMatrix.length-1)){
					//nothing
				}else{
					int index1 = CurrentPopulation[i][j];
					int index2 = CurrentPopulation[i][j+1];
					
					// check the distance
					sum = sum+weightMatrix[index1][index2];
					
				}
					
				}
			CurrentPopulation[i][weightMatrix.length] = sum+weightMatrix[0][weightMatrix.length-1]; // adds the fists and last distance together.
			sum = 0;
		}
		
	
		
	}
	
}
