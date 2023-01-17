/**
 *	City data - the city name, state name, location designation,
 *				and population est. 2017
 *
 *	@author	Aaryan Doshi
 *	@since	1/12/23
 */
public class City implements Comparable<City> {
	
	// fields
	public String state;
	public String name;
	public String designation;
	public int population;
	
	// constructor
	public City(String state, String name, String designation, int population){
		this.state = state;
		this.name = name;
		this.designation = designation;
		this.population = population;
	}
	
	/**	Compare two cities populations
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If populations are different, then returns (this.population - other.population)
	 *		else if states are different, then returns (this.state - other.state)
	 *		else returns (this.name - other.name)
	 */

	 public int compareTo(City other){
		 if(this.population != other.population) return (this.population - other.population);
		 else if(!(this.state.equals(other.state))) return (this.state.compareTo(other.state));
		 else return (this.name.compareTo(other.name));
	 }

	 /** Compare two cities names
	 *	@param other		the other City to compare
	 *	@return				the following value:
	 *		If the names are different, then returns (this.name.compareTo(other.name))
	 *		else returns (other.population - this.population)
	 */
	 public int compareToNames(City other) {
		int nameComparison = this.name.compareTo(other.name);
		if (nameComparison != 0) {
			return nameComparison;
		} else {
			return other.population - this.population;
		}
	}
		 
	
	/**	Equal city name and state name
	 *	@param other		the other City to compare
	 *	@return				true if city name and state name equal; false otherwise
	 */

	 public boolean equals(City other){
		 return (this.state.compareTo(other.name) == 0);
	 }
	
	/**	Accessor methods */

	public String getState() {return state;}

	public String getName() {return name;}

	public String getDesignation() {return designation;}
	
	public int getPopulation() {return population;}

	
	/**	toString */
	@Override
	public String toString() {
		return String.format("%-22s %-22s %-12s %,12d", state, name, designation,
						population);
	}
}