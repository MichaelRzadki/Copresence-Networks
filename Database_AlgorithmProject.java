package algorithmsProject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Database_AlgorithmProject {
	/*Variable Declaration*/
	private ArrayList<int[]> dataSet = new ArrayList<int[]>(); //Holds an ArrayList of integer arrays containing a list of number personnel
	private int minCoOccurances; //number of minimum co-occurances before the algorithm will post it
	private int totalEventNum = 0; //number of total events
	private int maximumIndividualNumber = 0; //greatest number assigned to an individual (i.e. for 100 individuals, the highest number will be 100)
	

/*1. Constructors*/
	/*1. Empty Constructor*/
	public Database_AlgorithmProject(){
		this.minCoOccurances = 0;
		this.totalEventNum = 0;
		this.maximumIndividualNumber = 0;
	} //End Empty Constructor


	/*2. Co-Occurance Input from Console Constructor*/
	public Database_AlgorithmProject(String inputFileName){
		this.minCoOccurances = this.getCoOccuranceNum();
		
		try{
			this.getDataSet(inputFileName);
		}catch(IOException e){
			System.out.println("Error: The inputted file with name " + inputFileName + " does not exist.");
		};
	} //End Co-Occurance Input from Console Constructor


	/*3. All Input Constructor*/
	public Database_AlgorithmProject(int inMinCoOccurances, String inputFileName){
		this.minCoOccurances = inMinCoOccurances;
		
		try{
			this.getDataSet(inputFileName);
		}catch(IOException e){
			System.out.println("Error: The inputted file with name " + inputFileName + " does not exist.");
		};
	} //End All Input Constructor



/*2. Getters*/
	/*1. getDataSet: Returns the DataSet*/
	public ArrayList<int[]> getDataSet(){
		return this.dataSet;
	}//end getDataSet


	/*2. getEvent: Returns the Event at the Provided Event Number*/
	public int[] getEvent(int eventNum){
		return this.dataSet.get(eventNum);
	} //End getEvent


	/*3. getMinCoOccurances: Returns the Number of Minimum Co-Occurances*/
	public int getMinCoOccurances(){
		return this.minCoOccurances;
	} //End getMinCoOccurances


	/*4. getTotalEventNum: Returns the Number of Total Events being Considered*/
	public int getTotalEventNum(){
		return this.totalEventNum;
	}//end getTotalEventNum

	
	/*5. getMaximumIndividualNumber: Returns the maximum number of any individual in the dataset*/
	public int getMaximumIndividualNumber(){
		return this.maximumIndividualNumber;
	}//end getMaximumIndividualNumber



/*3. Setters*/
	/*1. setDataSet: Sets the DataSet*/
	public void setDataSet(ArrayList<int[]> inDataSet){
		 this.dataSet.clear();
		 for(int i = 0; i < inDataSet.size() ; i++){
			 this.dataSet.add(inDataSet.get(i));
		 }//end for
	}//end setDataSet


	/*2. setEvent: Sets the Event at the Provided Event Number*/
	public void setEvent(int eventNum, int[] event){
		if (event.length > 1){
			if((eventNum > 0)&&(eventNum < this.totalEventNum)){
				this.dataSet.add(eventNum, event);
			}else{ //if the provided event number is out of bounds, add to the end and put an error if the number is not equal to one greater than the current number of events (i.e. if there are 100 events, there won't be an error message if the eventNum is 100, because that is one past the end)
				this.dataSet.add(event); //add the event to the end
				this.totalEventNum++; //increase the total number of events
				
				if(eventNum > this.totalEventNum){
					System.out.println("The inputed event number was out of bounds, at the place of " + eventNum + ". It has been placed at the end of the ArrayList, at the place numbered " + this.totalEventNum + ".");
				}//end internal if
			}//end middle if
		}//end external if
	} //End setEvent


	/*3. setMinCoOccurances: Sets the Number of Minimum Co-Occurances*/
	public void setMinCoOccurances(int inMinCoOccurances){
		this.minCoOccurances = inMinCoOccurances;
	} //End setMinCoOccurances


	/*4. setTotalEventNum: Sets the Number of Total Events being Considered*/
	public void setTotalEventNum(int inTotalEventNum){
		this.totalEventNum = inTotalEventNum;
	}//end setTotalEventNum



/*4. External Input Methods*/
	/*1. getDataSet: Uses a StringTokenizer to Process the File Input*/
	public void getDataSet(String inputFileName) throws IOException{
		/*Variable Declaration*/
		String line; //string to hold the string from the inputed line
		int[] intArray;
		int count = 0; //counter variable
		
		/*Buffered Reader Creation and Initial */		
		BufferedReader br = new BufferedReader(new FileReader(inputFileName));
		line = br.readLine();

		/*Check to see if the file is empty*/
		if (line == null) { //if so, return
			System.out.println("Error: Empty File");
			br.close();
			return;
		}//end if

		/*Event Array Assignment*/	
		do{
			StringTokenizer stringToken = new StringTokenizer(line, ",");
			intArray = new int[stringToken.countTokens()];
			
			if(intArray.length > 1){ //ensures all events include a minimum of two people
				this.totalEventNum++; //increments the total number of events
				
				for (count = 0; count < intArray.length; count++){
					intArray[count] = Integer.parseInt(stringToken.nextElement().toString());
					
					if ((count == (intArray.length - 1))&&(this.maximumIndividualNumber < intArray[count])){
						this.maximumIndividualNumber = intArray[count]; //keeps track of the highest number assigned to an individual
					}//end if
				}//end for
				
				this.dataSet.add(intArray); //adds the new array to the ArrayList
			}//end if
		}while((line = br.readLine()) != null);
		
		br.close();//close the buffered reader
	}//end getDataSet


	/*2. getCoOccuranceNum: Uses a Scanner to get the minimum number of Co-Occurances from the Command Line*/
	public int getCoOccuranceNum(){
		/*Variable Initialization*/
		Scanner iScanner = new Scanner(System.in); //Input Scanner
		boolean test = false; //ensures that there is a valid input
		int readInt = 0; //The input minimum number of co-occurances
		
		/*Input*/
		System.out.println("Please enter the number of minimum co-occurances (positive integer value): "); //output declaration
		
		do{
			test = iScanner.hasNextInt(); //if there is an integer in the input line
		}while(test == false); //repeat until the above condition is true
		readInt = iScanner.nextInt(); //set  to equal the inputed value

		iScanner.close(); //closes the scanner
		
		return readInt; //returns the readInt
	}//end getCoOccuranceNum



/*5. Sorting Methods*/
	/*1. determineSuspiciousCoOccurances: Returns a list of people who have Co-Occured more times than the threshold value*/
	public ArrayList<int[]> determineSuspiciousCoOccurances(){
		/*Variable List*/
		int eventListCounter = 0; //the current event, used in the middle for loop
		int eventPlaceCounter = 0; //the current place in the current event, used in the internal for loop
		ArrayList<int[]> coOccuranceList = new ArrayList<int[]>(); //Holds an int[3] ArrayList
		
		for(int curIndividual = 1; curIndividual < this.maximumIndividualNumber; curIndividual++){ //Checks the individuals from 1 to n-1, where n is the maximum. n doesn't need to be checked as all potential combinations would already be checked by previous searches.
			int[] individualEventList = this.createIndividualEventList(curIndividual); //Creates a list of events that an individual was present for
			
			if(individualEventList.length >= this.minCoOccurances){ //if the minimum number of co-occurances is more than the number of events, then it won't waste time by searching for potential pairs
			
				int[] cooccuranceNum = new int[this.maximumIndividualNumber]; //holds an array used to incremented when an individual is present for events multiple times
								
				for(eventListCounter = 0; eventListCounter < individualEventList.length; eventListCounter++){ //event list array movement
					
					int[] curEvent = this.dataSet.get(individualEventList[eventListCounter]);
					
					for(eventPlaceCounter = 0; eventPlaceCounter < curEvent.length; eventPlaceCounter++){ //place in event array movement
						
						if (curEvent[eventPlaceCounter] > curIndividual){//Check the current event for individuals numbering greater than the current individual
							
							if (presentAtEvent(curEvent[eventPlaceCounter], individualEventList[eventListCounter]) == true){
								cooccuranceNum[curEvent[eventPlaceCounter] - 1]++;
							}//end internal if
						}//end external if
					}//end internal for
				}//end middle for	

				for(int count = curIndividual; count < this.maximumIndividualNumber; count++){
					if(cooccuranceNum[count] > this.minCoOccurances){
						int[] outputArray = new int[3];
						outputArray[0] = curIndividual;
						outputArray[1] = count + 1;
						outputArray[2] = cooccuranceNum[count];
						coOccuranceList.add(outputArray);
					}//end if
				}//end count for
			}//end if	
		}//end external for, current individual
		
		return coOccuranceList;
	}//end determineSuspiciousCoOccurances


	/*2. createIndividualEventList: Creates an List of Events that the Specified Individual was Present for*/
	public int[] createIndividualEventList(int individualNum){
		ArrayList<Integer> eventList = new ArrayList<Integer>();
		
		for (int countEvent = 0; countEvent < this.totalEventNum; countEvent++){
			if(this.presentAtEvent(individualNum, countEvent) == true){
				eventList.add(countEvent); //Store the current event in the arraylist, as the individual participated in it
			}//end if
		}//end for
		
		int[] individualEventList = new int[eventList.size()]; //changes to array for easier future use
		
		for(int count = 0; count < eventList.size(); count++){
			individualEventList[count] = eventList.get(count).intValue();
		}//end for
		
		return individualEventList; //returns the list
	}//end createIndividualEventList


	/*3. presentAtEvent: Returns true if an individual was present at a given event, false otherwise*/
	public boolean presentAtEvent(int individualNum, int eventNum){
		for (int place = 0; place < this.dataSet.get(eventNum).length; place++){
			if(this.dataSet.get(eventNum)[place] == individualNum){ 
				return true; //individual present at event
			} else if(this.dataSet.get(eventNum)[place] > individualNum){
				return false;
			}//end if
		}//end for
		
		return false;//not present at event, should only be hit for the highest numbers
	}//end presentAtEvent
}//end class Database_AlgorithmProject