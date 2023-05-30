/* 
	File Name: Ticket.java
	Group: Claire Shen, Elsie Oh, Junhee Jung, Thomson Ly
	Description: To create Ticket objects and perform functions regarding tickets
*/

public class Ticket{
   //FIELDS
   private double totalCost;
   private String seatNum;
   private Flight flight;
   
   //CONSTRUCTOR
   /* 
	   Parameter(s): double of the final cost of the ticket, String of the seatNum reserved for the user, 
                    Flight object of the flight that the ticket is booked for
      Return value: Ticket object
      Purpose: to construct a Ticket object
   */
   public Ticket (Flight flight, String seatNum, double totalCost){
      this.totalCost = totalCost;
      this.seatNum = seatNum;
      this.flight = flight;
   }
   
   //ACCESSORS
   /*
      Parameter(s): N/A
      Return value: double of the final cost of the ticket
      purpose: allow controlled access to the private field totalCost
   */
   public double getTotalCost(){
      return totalCost;
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the seat number on the user's ticket
      purpose: allow controlled access to the private field seatNum
   */
   public String getSeatNum(){
      return seatNum;
   }
   
   /*
      Parameter(s): N/A
      Return value: Flight object of the flight that the ticket is for
      purpose: allow controlled access to the private field flight
   */
   public Flight getFlight(){
      return flight;
   }
   
   
   //MUTATORS
   /*
      Parameter(s): double of the new final cost of the ticket
      Return value: void
      Purpose: changes private field totalCost
   */
   public void setTotalCost(double tc){
      totalCost = tc;
   }
   
   /*
      Parameter(s): String of the new seat number of the ticket
      Return value: void
      Purpose: changes private field seatNum
   */
   public void setSeatNum(String sn){
      seatNum = sn;
   }
   
   /*
      Parameter(s): Flight object of the new flight of the ticket
      Return value: void
      Purpose: changes private field flight
   */
   public void setFlight(Flight f){
      flight = f;
   }
   
   
   //METHODS
   /* 
	   Parameter(s): N/A 
      Return value: String of the ticket's information
      Purpose: to print out information in a user friendly format
   */
   public String printOut(){
      String s = "";
      
      s += "\tFlight ID: " + flight.getId();
      s += "\n\tFrom: " + flight.getOrigin() + " To " + flight.getDestination();
      s += "\n\tDeparture: " + flight.getDepartureDate() + " " + (flight.getDepartureTime()).substring(0,2) + ":" + (flight.getDepartureTime()).substring(2);
      s += "\n\tProjected Arrival: " + flight.getArrivalDate() + " " + (flight.getArrivalTime()).substring(0,2) + ":" + (flight.getArrivalTime()).substring(2); 
      s += "\n\tSeat Number: " + seatNum;
      s += "\n\tFinal Cost: $" + totalCost;
      
      return s;
   }

   
   /* 
	   Parameter(s): N/A
      Return value: String of the ticket's information
      Purpose: to print out information for saving
   */
   public String toString(){
      String s = "";
      
      s += flight.getId() + "\n";
      s += seatNum + "\n";
      s += totalCost + "\n";
      
      
      return s;
   }
}