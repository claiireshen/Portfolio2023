/*
   Class Name: Flight
   Author: Claire Shen, Elsie Oh, Junhee Jung, Thomson Ly
   Purpose: To create Flight objects and perform functions regarding Flight objects
*/

//IMPORT FILES
import java.util.*;
import java.io.*;

class Flight{
   Scanner sc = new Scanner(System.in);
   
   //FIELDS
   private String id;
   private String airlineName;
   private Airplane airplane;
   private String origin;
   private String destination;
   private String departureDate;
   private String departureTime;
   private String arrivalDate;
   private String arrivalTime;
   private double cost;

   //CONSTRUCTOR
  /*
      Parameter(s): separate String variables containing the flight's ID, airline name, origin, destination, 
                    departure and arrival date and time, an Airplane object of the airplane used in the flight,
                    and a double of the cost to reserve one seat on the flight
      Return value: Flight object
      Purpose: to construct a Flight object
	*/
   public Flight(String id, String airlineName, Airplane airplane, String origin, String destination, String departureDate, String departureTime, String arrivalDate, String arrivalTime, double cost){
      this.id = id;
      this.airlineName = airlineName;
      this.airplane = airplane;
      this.origin = origin;
      this.destination = destination;
      this.departureDate = departureDate;
      this.departureTime = departureTime;
      this.arrivalDate = arrivalDate;
      this.arrivalTime = arrivalTime;
      this.cost = cost;
   }
   
   /*
      Parameter(s): N/A
      Return value: Flight object
      Purpose: to construct a Flight using on only the ID
  	*/
   public Flight(String id){
      this.id = id;
      this.airlineName = null;
      this.airplane = null;
      this.origin = null;
      this.destination = null;
      this.departureDate = null;
      this.departureTime = null;
      this.arrivalDate = null;
      this.arrivalTime = null;
      this.cost = 0;
   }

   //ACCESSORS
   /*
      Parameter(s): N/A
      Return value: String of the flight's ID
      Purpose: allow controlled access to the private field ID
	*/
   public String getId(){
      return id;
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the airline name
      Purpose: allow controlled access to the private field airlineName
	*/
   public String getAirlineName(){
      return airlineName;
   }
   
   /*
      Parameter(s): N/A
      Return value: the Airplane object being used for the flight
      Purpose: allow controlled access to the private field airplane
	*/
   public Airplane getAirplane(){
      return airplane;
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the origin location of the flight
      Purpose: allow controlled access to the private field origin
	*/
   public String getOrigin(){
      return origin;
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the destination location of the flight
      Purpose: allow controlled access to the private field destination
	*/
   public String getDestination(){
      return destination;
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the departure date of the flight
      Purpose: allow controlled access to the private field departureDate
	*/
   public String getDepartureDate(){
      return departureDate;
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the departure time of the flight
      Purpose: allow controlled access to the private field departureTime
	*/
   public String getDepartureTime(){
      return departureTime;
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the arrival date of the flight
      Purpose: allow controlled access to the private field arrivalDate
	*/
   public String getArrivalDate(){
      return arrivalDate;
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the arrival time of the flight
      Purpose: allow controlled access to the private field arrivlTime
	*/
   public String getArrivalTime(){
      return arrivalTime;
   }
   
   /*
      Parameter(s): N/A
      Return value: double of the cost to reserve one seat on the flight
      Purpose: allow controlled access to the private field cost
	*/
   public double getCost(){
      return cost;
   }

   //MUTATORS
   /*
      Parameter(s): String of the new ID for the flight
      Return value: void
      Purpose: changes private field id
   */
   public void setId(String id){
      this.id = id;
   }
   
   /*
      Parameter(s): String of the new airline name for the flight
      Return value: void
      Purpose: changes private field airlineName
   */
   public void setAirlineName(String airlineName){
      this.airlineName = airlineName;
   }
   
   /*
      Parameter(s): Airplane object of the new airplane being used for the flight
      Return value: void
      Purpose: changes private field airplane
   */
   public void setAirplane(Airplane airplane){
      this.airplane = airplane;
   }
   
   /*
      Parameter(s): String of the flight's new origin
      Return value: void
      Purpose: changes private field origin
   */
   public void setOrigin(String origin){
      this.origin = origin;
   }
   
   /*
      Parameter(s): String of the flight's new destination
      Return value: void
      Purpose: changes private field destination
   */
   public void setDestination(String destination){
      this.destination = destination;
   }
   
   /*
      Parameter(s): String of the flight's new departure date
      Return value: void
      Purpose: changes private field departureDate
   */
   public void setDepartureDate(String departureDate){
      this.departureDate = departureDate;
   }
   
   /*
      Parameter(s): String of the flight's new departure time
      Return value: void
      Purpose: changes private field departureTime
   */
   public void setDepartureTime(String departureTime){
      this.departureTime = departureTime;
   }
   
   /*
      Parameter(s): String of the flight's new arrival date
      Return value: void
      Purpose: changes private field arrivalDate
   */
   public void setArrivalDate(String arrivalDate){
      this.arrivalDate = arrivalDate;
   }
   
   /*
      Parameter(s): String of the flight's new arrival time
      Return value: void
      Purpose: changes private field arrivalTime
   */
   public void setArrivalTime(String arrivalTime){
      this.arrivalTime = arrivalTime;
   }
   
   /*
      Parameter(s): double of the updated cost to reserve a seat on the flight
      Return value: void
      Purpose: changes private field cost
   */
   public void setCost(double cost){
      if(cost >= 0){
         this.cost = cost;
      //if the cost is negative, the price is set to 0
      }else{
         this.cost = 0;
      }
   }
   

   //METHODS
   /*
	   Parameter(s): N/A
	   Return value: void
	   Purpose:	to change departure and arrival of the flight	  
   */
   public void updateFlightInfo(){
      //prompts user for the new information    
      System.out.print("Enter new departure date (yyyy/mm/dd): ");
      String newDepartureDate = sc.nextLine();
      System.out.print("Enter new departure time (hhmm): ");
      String newDepartureTime = sc.nextLine();
      System.out.print("Enter new arrival date (yyyy/mm/dd): ");
      String newArrivalDate = sc.nextLine();
      System.out.print("Enter new arrival time (hhmm): ");
      String newArrivalTime = sc.nextLine();
   	//updates information of the flight
      this.setDepartureDate(newDepartureDate);
      this.setDepartureTime(newDepartureTime);
      this.setArrivalDate(newArrivalDate);
      this.setArrivalTime(newArrivalTime);	
   }

   /*
	   Parameter(s): String of the name of the file that contains the current seating
	   Return value: void
	   Purpose:	to upload seating of the flight 
   */
   public void uploadSeat(char[][] seat){
      //sets seats
      airplane.setSeats(seat);
   }
   
   /*
      Parameter(s): String of the seat id that is to be available
      Return value: boolean true if availability was successfully changed
      Purpose: to change a reserved seat to an available seat
   */
   public boolean removeSeat(String seatID) {
      //cancels seat from the airplane
      return airplane.cancelSeat(seatID);
   }
   
   /*
	   Parameter(s): Flight object that is to be compared to this flight
	   Return value: double, negative if implicit flight is cheaper than the explicit flight
                            positive if implicit flight is more expensive than the explicit flight
                            0 if the two flights have same cost
	   Purpose:	to compare cost of two flights
   */
   public double compareToCost(Flight other){
      return this.cost = other.cost;
   }
   
   /*
      Parameter(s): Flight object that is to be compared to this flight
      Return value: double, negative if implicit flight is alphabetically before the explicit flight
                            positive if implicit flight is alphabetically after the explicit flight
                            0 if the two flights have same destination
      Purpose:	to compare the destination of two flights
   */
       public double compareToDestination(Flight other){
         return this.destination.compareTo(other.destination);
      }
   
   /*
      Parameter(s): Flight object that is to be compared to this flight
      Return value: double, positive if implicit flight is before the explicit flight
                            negative if if implicit flight is after the explicit flight
                            0 if two flights have the same departure date
      Purpose:	to compare departure date of two flights
   */
       public double compareToDeparture(Flight other){
      
      //YMD = year month date
         String [] YMD = this.departureDate.split("/");
         String [] otherYMD = other.departureDate.split("/");
      
      //combining year, month and date into one
         String thisDate = YMD[0] + YMD[1] + YMD[2];
         String otherDate = otherYMD[0] + otherYMD[1] + otherYMD[2];
      
         thisDate += this.departureTime;
         otherDate += other.departureTime;
      
      //converting it to an integer
         long numThisDate = Long.parseLong(thisDate);
         long numOtherDate = Long.parseLong(otherDate);
      
         return (double)numThisDate - numOtherDate;
      }
   
   /*
      Parameter(s): Flight object that is to be compared to this flight
      Return value: double, positive if implicit flight is before the explicit flight
                            negative if if implicit flight is after the explicit flight
                            0 if two flights have the same date
      Purpose:	to compare departure date of implicit to arrival date of explicit flights
   */
       public double compareToDepArr(Flight other){
      //YMD = year month date
         String [] YMD = this.departureDate.split("/");
         String [] otherYMD = other.arrivalDate.split("/");
      
      //combining year, month and date into one
         String thisDate = YMD[0] + YMD[1] + YMD[2];
         String otherDate = otherYMD[0] + otherYMD[1] + otherYMD[2];
      
         thisDate += this.departureTime;
         otherDate += other.arrivalTime;
      
      //then converting it to integer
         long numThisDate = Long.parseLong(thisDate);
         long numOtherDate = Long.parseLong(otherDate);
      
         return (double)numThisDate - numOtherDate;
      }
   
   /*
      Parameter(s): Flight object that is to be compared to this flight
      Return value: boolean true if the arrival time and departure time of implicit flight object is before the departure date of explicit flight object
      Purpose: to check if the flight arrives before the other flight
   */
       public boolean isBefore(Flight other){
      
      //YMD = year month date
         String[] YMDArrive = this.arrivalDate.split("/");
         String[] otherYMDDeaprt = other.departureDate.split("/");
      
      //combining year, month and date into one
         String thisArriveDate = YMDArrive[0] + YMDArrive[1] + YMDArrive[2];
         String otherDepartDate = otherYMDDeaprt[0] + otherYMDDeaprt[1] + otherYMDDeaprt[2];
      
      //add the time to the date
         thisArriveDate += this.arrivalTime;
         otherDepartDate += other.departureTime;
      
      //then converting it to integer
         long numThisArriveDate = Long.parseLong(thisArriveDate);
         long numOtherDepartDate = Long.parseLong(otherDepartDate);
      
      //return value
         if (numThisArriveDate <= numOtherDepartDate) {
            return true;
         } 
         else {
            return false;
         }
      }
   
   
   /*
      Parameter(s): N/A
      Return value: void
      Purpose:	to display departure/arrival of a flight
   */
       public void displayTime(){
         System.out.println("Departure date: " + departureDate);
         System.out.println("Departure time: " + departureTime);
         System.out.println("Arrival date: " + arrivalDate);
         System.out.println("Arrival time: " + arrivalTime);
      }
   
   
   /*
      Parameter(s): N/A
      Return value: void
      Purpose:	to display the flight information for customer in an organized and user friendly format
   */
       public void displayFlight(){
         String [] item = new String [] {"Flight ID:","From:","To:","Departure:","Arrival:","Cost:","Airline:"};
         
         String header = String.format("%-20s%-30s%-30s%-30s%-30s%-15s%s",item[0],item[1],item[2],item[3],item[4],item[5],item[6]);
         System.out.println(header);
      
         String DeTimeFormat = this.departureTime.substring(0,2) + ":" + this.departureTime.substring(2);
         String ArTimeFormat = this.arrivalTime.substring(0,2) + ":" + this.arrivalTime.substring(2);
         
         String info = String.format("%-20s%-30s%-30s%-30s%-30s%-15s%s", this.id, this.origin, this.destination, (this.departureDate+" "+DeTimeFormat), (this.arrivalDate+" "+ArTimeFormat),(this.cost+""),this.airlineName);
         System.out.println(info);
         System.out.println("_______________________________________________________________________________________________________________________________________________________________________________");
      
      }
   
   /*
      Parameter(s): String of the ID to verify
      Return value: boolean if ID is valid
      Purpose:	to check if inputted ID is valid or not 
      (a valid flight id contatins of two alphabet characters and 4 numbers with the alphabet coming first)
   */
       public boolean validFlightID(String verifyId){
      
      //valid flight id has a length of 6
         if(verifyId.length() != 6){
            return false;
         }
      
         try{
         //divides the id into characters and numbers
            char c1 = verifyId.charAt(0);
            char c2 = verifyId.charAt(1);
         //if the flight id is valid, then the last 4 characters should be a number
            String num = verifyId.substring(2, 6);
         
            if(c1 >= 'A' && c1 <= 'Z'){
               if(c2 >= 'A' && c2 <= 'Z'){
               //if converted into an integer, there would be no problem
                  int num2 = Integer.parseInt(num);
                  return true;
               }
               else{
                  return false;
               }
            }
            else{
               return false;
            }
         }
             catch(NumberFormatException numFormat){
            //if there is a problem, the flight id is invalid therefore return false
               return false;
            }
      }
   
   /*
      Parameter(s): String of the seat number
      Return value: boolean true if given number is valid
      Purpose:	validates the given seat number
   */   
       public boolean validSeatNum(String num){
         boolean valid = false;
         try{
            //format of seat number is 1 alphabet and an integer excluding 0. Eg. A4, E2, T6
            String aisleNum = num.substring(1);
         
            if(num.charAt(0) >= 'A' && num.charAt(0) <= 'Z'){
               Integer.parseInt(aisleNum);
               valid = true;
            }
         }
             catch(NumberFormatException nfex){
               valid = false;
            }
         return valid;
      }
   
   /*
   	Parameter (s): N/A
   	Return value: void
   	Purpose: displays the seating layout of the airplane
   */ 
       public void displaySeat(){
         airplane.displaySeat();
      }
   
   /*
   	Parameter (s): N/A
   	Return value: a char array of the airplane's seating plan
   	Purpose: to obtain the char array representing the seating layout of airplane to be saved into a file
   */
       public char[][] saveSeat() {
         return airplane.getSeats();
      }
   
   /*
   	Parameter (s): String of the seat number/id
   	Return value: boolean true if the seat was successfully reserved
   	Purpose: to reserve the seat
   */
       public boolean reserveSeat(String seatNum){
         return airplane.reserveSeat(seatNum);
      }
   /*
      Parameter(s): String of the seat number/id
      Return value: boolean true if the seat is available
      Purpose: to determine the availability of the seat
   */
       public boolean seatIsOpen(String seatNum) {
         return airplane.isOpen(seatNum);
      }
   
   
   /*
      Parameter(s): String of the seat number/id
      Return value: boolean of whether remove seat was successful
      Purpose: to change a reserved seat to an available seat
   */
       public boolean removeSeat(String seatID) {
         //cancel seat from the Airplane
         return airplane.cancelSeat(seatID);
      }
   
   /*
   Parameter(s): String of the file name that will be written containing the updated seating
   Return value: void
   Purpose:	to output updated seating plan of this flight 
   */
       public void outputSeat(String fileName){
         try{
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
         
            out.write(airplane.seats.length);
            out.newLine();
         
            out.write(airplane.seats[0].length);
            out.newLine();
         
            for(int i = 0;i < airplane.seats.length;i++){
               for(int j = 0;j < airplane.seats[0].length;j++){
                  out.write(airplane.seats[i][j]);
               }
               out.newLine();
            }
         
            out.close();
         }
             catch(IOException iox){
               System.out.println("Error Reading File");
            }
      }
   
   /*
      Parameter(s): N/A
      Return value: String of the flight's information
      Purpose:	returns all the information of flight in one string (to be used for an output file)
   */
       public String toString(){
      
         String s = id+"\n"+airlineName+"\n"+origin+"\n"+destination+"\n"+departureDate;
         s += "\n"+departureTime+"\n"+arrivalDate+"\n"+arrivalTime+"\n"+airplane.getId() + "\n" + cost + "\n";
      
         return s;
      }
   }