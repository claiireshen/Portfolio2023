/*
	Class Name: Admin
	Author: Claire Shen, Elsie Oh, Junhee Jung, Thomson Ly
	Purpose: To keep track of the admin user's information and perform different functions for the admin
*/

//IMPORT FILES
import java.util.*;

//Admin class is a subclass of the User class
class Admin extends User {
   Scanner sc = new Scanner(System.in);

	//FIELDS
   private Airline airline;

	//CONSTRUCTOR
	/*
      Parameter(s): separate String variables containing the user's username, email, password, and the Airline object that the admin belongs to
      Return value: Admin object
      Purpose: to construct an Admin object
	*/
   public Admin(String username, String email, String password, Airline airline) {
   	//sets username, email, and password with the constructor of the superclass, User
      super(username, email, password);
      this.airline = airline;
   }

	//ACCESSORS
	/*
      Parameter(s): N/A
      Return value: an Airline object
      Purpose: allow controlled access to the private field airline
	*/
   public Airline getAirline() {
      return airline;
   }

	//MUTATOR
   /*
      Parameter(s): Airline object of the new airline that the admin user belongs to
      Return value: void
      Purpose: changes private field airline
   */
   public void setAirline(Airline other) {
      airline = other;
   }

	//METHODS
	/*
      Parameter(s): N/A
      Return value: void
      Purpose: to update the specific flight information
	*/
   public void updateFlightInfo() {
   	//variables
      String flightID;
      Flight flight = null;
      String exit;
      boolean successful = false;
      int choice = 0;
   
   	//loop until valid input is entered
      while(!successful) {
         //prompt user for the ID of the flight
         System.out.print("\nPlease enter the ID of the flight that you want to change: ");
         flightID = sc.nextLine();
         //searches for the flight using the flightID and the searchByFlightID method
         flight = airline.searchByFlightID(flightID);
         
         if(flight == null) {
            System.out.println("Flight with ID " + flightID + " is not found. Please try again.");
         } else {
            successful = true;
         }
      }
      
   	//displays flight's current departure/arrival date/time
      flight.displayTime();
   
   	//calls method updateFlightInfo from the Flight class to update flight information
      flight.updateFlightInfo();
   
   	//prints success message
      System.out.println("Flight has been updated!");
      System.out.println("New informations of the flights are listed as follows:");
   
   	//display flight's new departure/arrival date/time
      flight.displayTime();
   }

   	/*
         Parameter(s): N/A
         Return value: a Flight object
         Purpose: to allow the admin user to add a new flight to the airline that the admin belongs to
   	*/
      public Flight addFlight() {
   	//variables
      String id, origin, destination, departureD, departureT, arrivalD, arrivalT, airplaneSize, airplaneID;
      double cost = 0;
      int choice;
      boolean goodData = false;
   
   	//prompts user for information of the new flight
      System.out.println("\nPlease enter the information of the new flight: ");
      System.out.print("Flight ID:");
      id = sc.nextLine();
      System.out.print("Origin: ");
      origin = sc.nextLine();
      System.out.print("Destination: ");
      destination = sc.nextLine();
      System.out.print("Departure Date (yyyy/mm/dd): ");
      departureD = sc.nextLine();
      System.out.print("Departure Time (hhmm): ");
      departureT = sc.nextLine();
      System.out.print("Arrival Date (yyyy/mm/dd): ");
      arrivalD = sc.nextLine();
      System.out.print("Arrival Time (hhmm): ");
      arrivalT = sc.nextLine();
      System.out.print("Airplane ID: ");
      airplaneID = sc.nextLine();
      System.out.print("Cost: ");
   	//checks for input mismatch
      while(!goodData) {
         try{
            cost = sc.nextDouble();
            goodData = true;
         } catch(InputMismatchException ex) {
            System.out.println("You entered bad data.");
            System.out.print("Please enter the cost again: ");
            String flush = sc.next();
         }
      }
        
   	//calls the addFlight method from the Airline class 
      Flight flight = airline.addFlight(id, airplaneID, origin, destination, departureD, departureT, arrivalD, arrivalT, cost);
      
   	//prints success message
      System.out.println("Flight has been created.");
      
      return flight;
   }


	/*
      Parameter(s): N/A
      Return value: a Flight object
      Purpose: To allow the admin user to remove a flight from the airline that the admin belongs to
	*/
   public Flight removeFlight() {
   	//variables
      String flightID, removeChoice;
      String choice;
      Flight removeFlight = null;
      boolean successful = false;
   
      while (!successful) {
         //prompts user to enter the flightID of the flight they want to remove
         airline.displayAllFlights();
         System.out.print("\nPlease enter the ID of the flight that you want to remove: ");
         flightID = sc.nextLine();
         //formatting
         System.out.println();
         removeFlight = airline.searchByFlightID(flightID);
         
         if(removeFlight != null){
            removeFlight.displayFlight();
            System.out.print("\nIs this the correct flight? (yes/no) ");
            choice = sc.nextLine();
            if(choice.equals("yes")){
               //calls the removeFlight method from the Airline class to remove flight
               airline.removeFlight(flightID);
               successful = true;
               
               //prints success message
               System.out.println("\nThe flight has been succefully removed.");
            }
         }else{
            //prints message that no flight was found with the given flight ID
            System.out.println("There is no flight with ID: "+flightID+". Please try again.\n");
         }
      }
      return removeFlight;
   }


   /*
      Parameter(s): N/A
      Return value: void
      Purpose: To allow the admin user to view the list of flights belonging to the airline
	*/
   public void displayFlights(){
   	//variables
      String choice;
      boolean goodData = false;
   
   	//prints the airline's name
      System.out.println("Flight list of " + airline.getAirlineName());
   
   	//prints all of the flights belonging to the airline
      airline.displayAllFlights();
   }

	/*
      Parameter(s): N/A
      Return value: String of admin's information
      Purpose: To allow printing of the admin's information
	*/
   public String toString() {
   	//variable
      String s = "";
   
   	//calls the superclass's toString method, storing it in the variable s
      s = s + super.toString();
   
   	//adds "true" to communicate the user's admin status
   	//also adds the airline name of the airline that the admin belongs to
      s = s + "true\n" + airline.getAirlineName() + "\n";
   
      return s;
   }
}