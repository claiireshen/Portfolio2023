/*
	Class Name: Airline
	Author: Claire Shen, Elsie Oh, Junhee Jung, Thomson Ly
	Purpose: To store flight objects and airplane objects belonging to the specific airline
*/

class Airline{

	//FIELDS
   //constant
	private static final int MAX_FLIGHT = 10;
   //arrays
   private Flight[] flightList;
	private Airplane[] airplaneList;
   //variables
	private String airlineName;
	private int numFlight;
	private String accessCode;


	//CONSTRUCTOR
	/* 
   	Parameter(s): separate String variables for the airline's name and access code, int of the number of flights, 
                    and arrays containing all flights and all airplanes belonging to the airline
      Return value: Airline object
   	Purpose:	to construct an Airline object		 
	*/
	public Airline(String airlineName, int numFlight, String accessCode, Flight[] flightList,Airplane[] airplaneList){
      this.airlineName = airlineName;
		this.numFlight = numFlight;
		this.accessCode = accessCode;
		this.flightList = flightList;
		this.airplaneList = airplaneList;
	}


	//ACCESSORS
   /*
      Parameter(s): N/A
      Return value: String containing the airline name
      Purpose: allow controlled access to the private field airlineName
	*/
	public String getAirlineName(){
		return airlineName;
	}

   /*
      Parameter(s): N/A
      Return value: String containing the access code to the airline
      Purpose: allow controlled access to the private field accessCode
	*/
	public String getAccessCode(){
		return accessCode;
	}

   /*
      Parameter(s): N/A
      Return value: int number of flights belonging to the airline
      Purpose: allow controlled access to the private field numFlight
	*/
	public int getNumFlight(){
		return numFlight;
	}
   
   /*
      Parameter(s): N/A
      Return value: an array containing all flights belonging to the airline
      Purpose: allow controlled access to the private array flightList
	*/
   public Flight[] getFlightList(){
      return flightList;
   }


	//MUTATORS
   /*
      Parameter(s): String of the new airline name
      Return value: void
      Purpose: changes private field airlineName
   */
	public void setAirlineName(String name){
		airlineName = name;
	}

   /*
      Parameter(s): String of the new access code for the airline
      Return value: void
      Purpose: changes private field accessCode
   */
	public void setAccessCode(String code){
		accessCode = code;
	}

   /*
      Parameter(s): int of the new number of flights belonging to the airline 
      Return value: void
      Purpose: changes private field numFlight
   */
	public void setNumFlight(int num){
		if(num > MAX_FLIGHT){
			numFlight = MAX_FLIGHT;
		}else if(num < 0){
			numFlight = 0;
		}else{
			numFlight = num;
		}
	}

	/*
   	Parameter(s): separate String variables for the flight's id, airplane's id, origin, destination, departure date,
                    departure time, arrival date, arrival time, and a double representing the cost of a ticket
   	Return value: a new Flight object
   	Purpose:	to add flight into flight list, returns flight object to be added to the 
               program's entire comprehensive list of flights
	*/
	public Flight addFlight(String flightId, String airplaneId, String origin, String destination, String departureDate, String departureTime, String arrivalDate, String arrivalTime, double cost){
		//if the airline has reached the maximum number of flights, null is returned as the flight could not be added
		if(numFlight == MAX_FLIGHT){
			return null;
		}else{
			Airplane airplane = this.searchByAirplaneID(airplaneId);

			flightList[numFlight] = new Flight(flightId, this.airlineName, airplane, origin, destination, departureDate, departureTime, arrivalDate, arrivalTime, cost);
			numFlight++;
			return flightList[numFlight-1];
		}
	}

	/*
   	Parameter(s): String of the ID of the flight
   	Return value: boolean true if successful in removing flight
   	Purpose:	to remove flight in the flight list		 
	*/
	public boolean removeFlight(String flightId){
		int removeInd = this.flightIndex(flightId);

		//-1 signifies that the flight does not exist in the flight list
		if(removeInd == -1){
			return false;
		}else{
         Flight flight = flightList[removeInd];
			//if the  flight exists, it is removed from the list and remaining flights are shifted up in the array
			flightList[removeInd] = null;
			while(removeInd+1 < MAX_FLIGHT && flightList[removeInd+1] != null){
				flightList[removeInd] = flightList[removeInd+1];
				flightList[removeInd+1] = null;
				removeInd++;
			}
			numFlight--;
         return true;
		}

	}

	/*
   	Parameter(s): String of the ID of the flight that is to be found
   	Return value: the Flight object represented by the specific ID
   	Purpose:	to search for the flight with given flight ID and return the flight 
   */
	public Flight searchByFlightID(String id){
		for(int i = 0;i < numFlight;i++){
			//if the flight has been found, the flight object is returned
			if(flightList[i].getId().equals(id)){
				return flightList[i];
			}
		}

		//if no flight object was found with the specific ID, null is returned
		return null;
	}

	/*
   	Parameter(s): String of the ID of the airplane that is to be found
   	Return value: the Airplane object represented with the specific ID
   	Purpose:	to search for the airplane with given ID and return the airplane
   */ 
	public Airplane searchByAirplaneID(String id){
		for(int i = 0;i < airplaneList.length;i++){
			//if the airplane has been found, the airplane object is returned
			if(airplaneList[i].getId().equals(id)){
				return airplaneList[i];
			}
		}

		//if no airplane object was found with the specific ID, null is returned
		return null;
	}

	/*
   	Parameter(s): String of the ID of the flight
   	Return value: int of the index of the flight with the given flight ID
   	Purpose:	to search for the flight with given flight ID and return the index of said flight
   */    
	private int flightIndex(String id){
		for(int i = 0;i < numFlight;i++){
			if(flightList[i].getId().equals(id)){
				return i;
			}
		}

		//to indicate that the flight has not been found
		return -1;
	}
   
   /*
      Parameter(s): String of the ID of the flight
      Return value: boolean true if there is a duplicate flight, false if no duplicate flight exists
      Purpose: to find if there is a duplicate of the indicated flight in the flight list
   */
   public boolean checkDuplicatesFlightId(String id){
      for(int i = 0;i < numFlight;i++){
         if(id.equals(flightList[i].getId())){
            return true;
         }
      }
      return false;
   }

	/*
   	Parameter(s): None
   	Return value: void
   	Purpose:	to displays all the information of each flight in an organized fashion for the users
   */
	public void displayAllFlights(){
		String [] item = new String [] {"Flight ID:","From:","To:","Departure:","Arrival:","Cost:","Airline:"};      

		//for converting time into a more user friendly format
		String DeTimeFormat;
		String ArTimeFormat;

		String info = "";

		for(int i = 0;i < numFlight;i++){

			//displaying 
			String header = String.format("%-20s%-30s%-30s%-30s%-30s%-15s%s",item[0],item[1],item[2],item[3],item[4],item[5],item[6]);
			System.out.println(header);

			//converting time to the user friendly format eg. 1500 into 15:00
			DeTimeFormat = flightList[i].getDepartureTime().substring(0,2) + ":" + flightList[i].getDepartureTime().substring(2);
			ArTimeFormat = flightList[i].getArrivalTime().substring(0,2) + ":" + flightList[i].getArrivalTime().substring(2);

			info = String.format("%-20s%-30s%-30s%-30s%-30s%-15s%s", flightList[i].getId(), flightList[i].getOrigin(), flightList[i].getDestination(), (flightList[i].getDepartureDate()+" "+DeTimeFormat), (flightList[i].getArrivalDate()+" "+ArTimeFormat),(flightList[i].getCost()+""),this.airlineName);

			System.out.println(info);
			System.out.println("___________________________________________________________________________________________________________________________________________________________");
		}

	}
}