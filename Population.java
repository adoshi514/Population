import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *	Population - This program has 6 different choices to sort a databse of cities by names or population. It uses different sorting algorithms such as selection sort, insertion sort, or merge sort.
 *
 *	Requires FileUtils and Prompt classes.
 *
 *	@author	Aaryan Doshi
 *	@since	1/12/23
 */
public class Population {
	
	// List of cities
	private List<City> cities;
	
	// US data file
	private final String DATA_FILE = "usPopData2017.txt";

	/*initialize fields*/
	public Population(){
		cities = new ArrayList<City>();
	}
	public static void main (String [] args){
		Population obj = new Population();
		obj.printIntroduction();
		obj.readFile();
	}
	
	
	/**	Prints the introduction to Population */
	public void printIntroduction() {
		System.out.println("   ___                  _       _   _");
		System.out.println("  / _ \\___  _ __  _   _| | __ _| |_(_) ___  _ __ ");
		System.out.println(" / /_)/ _ \\| '_ \\| | | | |/ _` | __| |/ _ \\| '_ \\ ");
		System.out.println("/ ___/ (_) | |_) | |_| | | (_| | |_| | (_) | | | |");
		System.out.println("\\/    \\___/| .__/ \\__,_|_|\\__,_|\\__|_|\\___/|_| |_|");
		System.out.println("           |_|");
		System.out.println();
	}
	
	/**	Print out the choices for population sorting */
	public void printMenu() {
		System.out.println("1. Fifty least populous cities in USA (Selection Sort)");
		System.out.println("2. Fifty most populous cities in USA (Merge Sort)");
		System.out.println("3. First fifty cities sorted by name (Insertion Sort)");
		System.out.println("4. Last fifty cities sorted by name descending (Merge Sort)");
		System.out.println("5. Fifty most populous cities in named state");
		System.out.println("6. All cities matching a name sorted by population");
		System.out.println("9. Quit");
	}

	/*Read the input files using a delimeter and store the information in an arraylist of city objects*/
	public void readFile(){
		/*useDelimeter to read the input easily*/
		Scanner inputFile = FileUtils.openToRead(DATA_FILE).useDelimiter("[\t\n]");

		int iterate = 0;
		String state, city, designation;
		state = city = designation = "";
		int population = 0;
		int numCities = 0;

		/*After every space update state, city, designation, and population*/
		/*If counter is 4, then add new City object with according information*/
		while(inputFile.hasNext()){
			if(iterate % 4 == 0) state = inputFile.next();
			else if (iterate % 4 == 1) city = inputFile.next();
			else if(iterate % 4 == 2) designation = inputFile.next();
			else if(iterate % 4 == 3) {
				population = Integer.parseInt(inputFile.next().trim());
				cities.add(new City(state, city, designation, population));
				numCities++;
			}

			iterate++;
		}

		System.out.println("\n" + numCities + " cities in database\n");
		handleInput();
	}

	/*Determine next steps based on users choice*/
	public void handleInput(){		
		int choice =  0;
		int quitNumber = 9;
		long startMillisec;
		long endMillisec;
		
		/*As long as the user does hit 9, keep going*/
		while(choice != quitNumber){
			printMenu();
			choice = Prompt.getInt("Enter Selection ");

			/*If user doesn't enter one of given numbers, have them enter again*/
			while((choice < 1 && choice > 6) && choice != quitNumber){
				choice = Prompt.getInt("Enter Selction ");
			}

			boolean isSortName = false;
			String message = "";

			/*Call selection sort for fifty least populous cities*/
			if(choice == 1) {
				startMillisec = System.currentTimeMillis();
				selectionSort(cities);
				endMillisec = System.currentTimeMillis();
				message = "Fifty least populous cities";
				printResult(message, cities);
				System.out.println("\nElapsed Time " + (endMillisec-startMillisec) + " milliseconds");
			}

			/*Call merge sort for Fifty most populous cities*/
			else if(choice == 2) {
				startMillisec = System.currentTimeMillis();
				mergeSort(cities, isSortName);
				endMillisec = System.currentTimeMillis();
				message = "Fifty most populous cities";
				printResult(message, cities);
				System.out.println("\nElapsed Time " + (endMillisec-startMillisec) + " milliseconds");
			}

			/*Call insertion sort for fifty cities sorted by name*/
			else if(choice == 3){
				startMillisec = System.currentTimeMillis();
				insertionSort(cities);
				endMillisec = System.currentTimeMillis();
				message = "Fifty cities sorted by name";
				printResult(message, cities);
				System.out.println("\nElapsed Time " + (endMillisec-startMillisec) + " milliseconds");
			}

			/*Call merge sort for fifty cities sorted by name (descending)*/
			else if(choice == 4){
				isSortName = true;
				startMillisec = System.currentTimeMillis();
				mergeSort(cities, isSortName);
				endMillisec = System.currentTimeMillis();
				message = "Fifty cities sort by name descending";
				printResult(message, cities);
				System.out.println("\nElapsed Time " + (endMillisec-startMillisec) + " milliseconds");
			}

			/*for choice 5, filter for whatever the user chooses*/
			else if(choice == 5){
				filter(choice);
			}

			/*for choice 6, filter for whatever the user chooses*/
			else if(choice == 6){
				filter(choice);
			}

			else if(choice == 9){
				System.out.println("\nThanks for using Population!");
			}

			System.out.println();
		}
	}

	/** Method when the user chooses to enter a state or city and filter based on that
	 * @param choice the number that user enters
	 */
	public void filter(int choice){
		boolean isThere = false;
		String nameState = "";
		String nameCity = "";

		/*Keep having them enter a name as long as the name doesn't exist*/
		System.out.println();
		while(!isThere){
			if(choice == 5) nameState = Prompt.getString("Enter state name (ie. Alabama) ");
			else nameCity = Prompt.getString("Enter city name ");
			
			/*iterate through cities arraylist to check for existence*/
			if(choice == 5){
				for(int i = 0; i < cities.size() && !isThere; i++){
					if(cities.get(i).state.equals(nameState)){
						isThere = true;
					}
				}
			}

			/*iterate through cities arraylist to check for existence*/
			else{
				for(int i = 0; i < cities.size() && !isThere; i++){
					if(cities.get(i).name.equals(nameCity)){
						isThere = true;
					}
				}
			}
			

			if(choice == 5 && !isThere) System.out.println("ERROR: " + nameState + " is not valid");
			if(choice == 6 && !isThere) System.out.println("ERROR: " + nameCity + " is not valid");
		}

		System.out.println();

		List<City> citiesInState = new ArrayList<City>();
		List<City> statesWithCity = new ArrayList<City>();

		/*Go through cities arraylist and check when the state/city matches what the user entered*/
		if(choice == 5){
			for(int i = 0; i < cities.size(); i++){
				if(cities.get(i).state.equals(nameState)){
					citiesInState.add(cities.get(i));
				}
			}
		}

		else{
			for(int i = 0; i < cities.size(); i++){
				if(cities.get(i).name.equals(nameCity)){
					statesWithCity.add(cities.get(i));
				}
			}
		}

		/*if choice is 5, print out the fifty most populous cities sorted by name*/
		String message = "";
		boolean isSortName = false;
		if(choice == 5) {
			message = "Fifty most populous cities in " + nameState;
			long startMillisec = System.currentTimeMillis();
			mergeSort(citiesInState, isSortName);
			long endMillisec = System.currentTimeMillis();
			printResult(message, citiesInState);
			System.out.println("\nElapsed Time " + (endMillisec-startMillisec) + " milliseconds");
		}

		/*if choice is 6, print out the city by population*/
		else {
			message = "City " + nameCity + " by population";
			long startMillisec = System.currentTimeMillis();
			mergeSort(statesWithCity, isSortName);
			long endMillisec = System.currentTimeMillis();
			printResult(message, statesWithCity);
			System.out.println("\nElapsed Time " + (endMillisec-startMillisec) + " milliseconds");
		}

		
	}

	/** This is used to print out the cities based on the choice that the user enters
	 *  @param message the message passed depending on the choice
	 *  @param currCities the arraylist containing the cities that will be printed
	*/
	public void printResult(String message, List<City> currCities){
		System.out.println("\n" + message);
		System.out.println("    State\t\t   City\t\t\t  Type\t\t Population");
		int numPrint = 0;
		if(currCities.size() >= 50) numPrint = 50;
		else numPrint = currCities.size();

		for(int i = 1; i <= numPrint; i++){
			if((i+"").length() == 1) System.out.print(" " + i + ": ");
			else System.out.print(i + ": ");
			System.out.println(currCities.get(i-1));
		}
	}


	/** Swap method used during selection sort
	 * @param arr the array where the swap is occurring
	 * @param x the first index involved in swap
	 * @param y the second index involved in swap
	 */
	public void swap(List<City> arr, int x, int y) {
		City temp = arr.get(x);
		arr.set(x, arr.get(y));
		arr.set(y, temp);
	}

	/**
	 *	Insertion Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of Integer objects to sort
	 */
	public void insertionSort(List<City> arr) {
		for(int i = 1; i < arr.size(); i++){
			City current = arr.get(i);
			int pointer = i-1;
			
			while(pointer >= 0 && (current.compareToNames(arr.get(pointer)) < 0)){
				arr.set(pointer+1, arr.get(pointer));
				pointer--;
			}		
			arr.set(pointer+1, current);	
		}
	}

	/**
	 *	Selection Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of Integer objects to sort
	 */
	public void selectionSort(List<City> arr) {
		int indexMin = -1;
		// default city with the maximum population
		City cityDefault = new City("", "", "", Integer.MAX_VALUE);
		for(int i = 0; i < arr.size(); i++){
			for(int j = i; j < arr.size(); j++){
				City city = arr.get(j);
				if(city.compareTo(cityDefault) < 0){
					cityDefault = new City(city.state, city.name, city.designation, city.population);
					indexMin = j;
				}
			}

			swap(arr, i, indexMin);
			//keep re-updating the city object
			cityDefault = new City("", "", "", Integer.MAX_VALUE);
		}
	}

	/**
	 *	Merge Sort algorithm - in ascending order (you implement)
	 *	@param arr		array of Integer objects to sort
	 *  @param isSortNames whether or not name are being sorted or population
	 */
	public void mergeSort(List<City> arr, boolean isSortNames) {
		recurseMethod(arr, 0, arr.size()-1, isSortNames);
	}

	/** The recursive method of the merge sort algorithm. Sort in descending order in divide-conquer nature
	 * @param arr the array of cities that needs to be sorted
	 * @param L the left pointer
	 * @param R the right pointer
	 * @param isSortNames whether or not the names are being sorted or the population
	 */
	public void recurseMethod(List<City> arr, int L, int R, boolean isSortNames){
		if(L>=R) return;
		int mid = (L+R)/2;
		recurseMethod(arr, L, mid, isSortNames);
        recurseMethod(arr, mid+1, R, isSortNames);
        mergeMethod(arr, L, R, mid, isSortNames);
	}

	/** Use two pointers strategy to merge the parts of the arraylist
	 * @param arr the array to be merged in descending manner
	 * @param L the left pointer
	 * @param R the right pointer
	 * @param mid the midway marker of the left and right pointer
	 * @param isSortNames whether or not population is being sorted or name is being sorted
	 */
	public void mergeMethod(List<City> arr, int L, int R, int mid, boolean isSortNames){
		int i = L;
		int j = mid+1;
		int k = L;

		City [] temp = new City[arr.size()];

		while(i<=mid && j<=R){
            if(!isSortNames && arr.get(i).compareTo(arr.get(j)) >= 0){
                temp[k] = arr.get(i);
                i++;
                k++;
            }

			else if(isSortNames && arr.get(i).compareToNames(arr.get(j)) >= 0){
				temp[k] = arr.get(i);
                i++;
                k++;
			}
            
            else{
                temp[k] = arr.get(j);
                j++;
                k++;
            }
        }

        for(int t = i; t<=mid; t++){
            temp[k] = arr.get(t);
            k++;
        }
        
        for(int t = j; t<=R; t++){
            temp[k] = arr.get(t);
            k++;
        }

        for(int t = L; t<=R; t++){
            arr.set(t, temp[t]);
        }
    }
	
}