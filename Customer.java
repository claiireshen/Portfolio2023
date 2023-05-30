/*
	Class Name: Customer
	Author: Claire Shen, Elsie Oh, Junhee Jung, Thomson Ly
	Purpose: To record and track the current customer's information perform different functions for the customer
*/

//IMPORT FILES
import java.util.*;
import java.io.*;

//Customer class is a subclass of User class
class Customer extends User {
   
   //FIELDS
   private int points;
   private TicketList ticketList;
   
   //CONSTRUCTOR
   /*
      Parameter(s): separate String variables containing the user's username, email, password      
      Return value: Customer object
      Purpose: to create a Customer object using only basic informatin
   */
   public Customer(String username, String email, String password) {
      //sets username, email, and password with the constructor of the superclass, User
      super(username, email, password);
      
      //sets customer's points balance to 0
      points = 0;
      
      //constructs an empty ticket list
      ticketList = new TicketList();
   }
   
   /*
      Parameter(s): separate String variables containing the user's username, email, password      
      Return value: Customer object
      Purpose: to create a Customer object of a pre-existing customer
   */
   public Customer(String username, String email, String password, int points) {
      //sets username, email, and password with the constructor of the superclass, User
      super(username, email, password);
      
      //sets customer's points balance to the given pre-existing balance
      this.points = points;
   }
   
   //ACCESSOR
   /*
      Parameter(s): N/A
      Return value: int of the customer's points balance
      Purpose: allow controlled access to the private field points
	*/
   public int getPoints(){
      return points;
   }
   
   //MUTATOR
   /*
      Parameter(s): int of the customer's new points balance
      Return value: void
      Purpose: changes private field points
   */
   public void setPoints(int points){
      this.points = points;
   }
   
   //METODS
   /*
      Parameter(s): points that customer received
      Return value: boolean true if the points were added successfully
      Purpose: to update the customer's points balance with the additional points
   */
   public boolean addPoints(int pointGot){
   
      //checks if pointGot is a valid number
      if (pointGot >= 0) {
         //updates the customer's points balance by adding the new points
         points = points + pointGot;
         return true;
      } 
      else {
         //if pointGot is not a valid value, false is returned
         return false;
      }
   }
   
   /*
      Parameter(s): int points that the customer wishes to use
      Return value: boolean true if user has successfully used points
      Purpose: to update the customer's points balance after using points
   */
   public boolean usePoints(int pointUsed) {
      //checks if pointUsed is a valid number
      if (pointUsed > points || pointUsed < 0) {
         return false;
      } 
      else {
         //if pointUsed is valid, the points are deducted, and true is returned
         points = points - pointUsed;
         return true;
      }
   }
   
   /*
      Parameter(s): N/A
      Return value: void
      Purpose: to allow Customer to cancel a flight booking
   */
public void cancelBooking() {
      //variables
      int bookingID = 0;
      boolean successful = false;
      boolean validInput = false;
      String choice;
      
      //prints all of the bookings belonging to the customer
      printAllTickets();
      
      //if there are no tickets in the customer's ticket list, no function is performed
      if (ticketList.getNumTicket() > 0) {         
         //checks for valid input
         while(!validInput) {
            //prompts user for the ID of booking that they want to cancel
            System.out.print("Enter ID of booking to cancel: ");
            try {   
               bookingID = sc.nextInt();
               sc.nextLine();
               validInput = true;
            } catch(InputMismatchException imex) {
               System.out.println("Invalid input! Please try again.");
            }
         }
         
         while(!successful) {
            //if the given bookingID is valid
            if (bookingID >= 1 && bookingID <= ticketList.getNumTicket()) {
               //prints the ticket
               printTicket(bookingID-1);
            
               //asks for confirmation on if they wish to cancel the flight
               System.out.print("\nAre you sure you want to cancel? (yes/no): " );
               choice = sc.nextLine();
            
               //if the customer has approved the removal, the ticket is removed
               if (choice.equals("yes")) {
                  ticketList.removeTicket(bookingID-1);
                  //success message
                  System.out.println("Booking " + bookingID + " has been cancelled.");
               } else {
                  //failure to cancel the booking message
                  System.out.println("Booking " + bookingID + " has not been cancelled.");
               }
            
               //sets successful to true
               successful = true;
            } else {
               //outputs error message as the booking did not exist
               System.out.println("The ID you just entered did not exist. Please try again: ");
               bookingID = sc.nextInt();
            }
         }
      }      
   }

   
   /*
      Parameter(s): N/A
      Return value: void
      Purpose: to print all of the tickets currently belonging to the current customer
   */
   public void printAllTickets() {
      //calls the method printAllTickets from the TicketList to display all the tickets
      ticketList.printAllTickets();
   }
   
   /*
      Parameter(s): int of the index of the ticket that wants to be printed
      Return value: void
      Purpose: to print the specified ticket belonging to the customer
   */
   public void printTicket(int index) {
      //Call printTicket from TicketList class
      ticketList.printTicket(index);
   }
   
   /*
      Parameter(s): N/A
      Return value: void
      Purpose: to upload the ticket information of the customer
   */
   public void uploadTicket(TicketList list) {
      //sets ticketList with the list that was uploaded
      this.ticketList = list;
   }
   
   /*
      Parameter(s): N/A
      Return value: void
      Purpose: to save the tickets that Customer has for future reference
   */
   public void saveTicket() {
      //variables
      String fileName = this.username + ".txt";
      boolean goodData = false;
      
      Ticket [] tickets = this.ticketList.getTickets();    
      int numTickets = this.ticketList.getNumTicket();
      
      
      while(!goodData) {
         try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            
            //if BufferedWriter was successful, goodData is set to true
            goodData = true;
            out.write(numTickets+"");
            out.newLine();
            for(int i = 0;i < numTickets;i++){
               out.write(tickets[i].getFlight().getId());
               out.newLine();
               out.write(tickets[i].getSeatNum());
               out.newLine();
               out.write(tickets[i].getTotalCost()+"");
               out.newLine();
            }        
            
            out.close();
         } 
         catch (IOException iox) {
            //prints error message
            System.out.println("Error reading in file");
         }
      }
   }
   
   /*
      Parameter(s): Flight object of the flight that was reserved, String of the seatID that was reserved,
                    double value of the cost of the flight that was reserved
      Return value: void
      Purpose: adds the customer's recently reserved flight ticket to their list of tickets
   */
   public void reserveFlight(Flight flight, String seatID, double cost) {
      //calls the addTicket method from the TicketList class to add the ticket to the customer's ticket list
      ticketList.addTicket(flight, seatID, cost);
   }
   
   /*
      Parameter(s): N/A
      Return value: void
      Purpose: to allow the customer to check their current points balance
   */
   public void checkPoints() {     
      //prints the customer's points balance
      System.out.println("You have " + points + " points in your account!");
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the information of the customer
      Purpose: to print the information of the Customer
   */
   public String toString() {
      //variable
      String s = "";
      s = s + super.toString() + "false\n" + points + "\n";
      return s;
   }
}