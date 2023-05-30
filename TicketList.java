/* 
	File Name: TicketList.java
	Author: Claire Shen, Elsie Oh, Junhee Jung, Thomson Ly
	Description: To create TicketList objects and perform functions regarding the customer's list of tickets
*/

   public class TicketList {
    
   //FIELDS
   private int numTicket = 0;
   private Ticket[] tickets;
   private static final int MAX_TICKET = 20;
   
   
   //CONSTRUCTOR
   /* 
      Parameter(s): N/A
      Return value: TicketList object
      Purpose: to construct a TicketList object
   */
   public TicketList() {
      tickets = new Ticket[MAX_TICKET];
   }
   
   //ACCESSORS
   /* 
      Parameter(s): N/A
      Return value: int number of tickets in the ticket list
      Purpose: allow controlled access to the private field numTicket
   */
   public int getNumTicket() {
      return numTicket;
   }
   
   /* 
      Parameter(s): N/A
      Return value: ticket array containing all the tickets in the list
      Purpose: allow controlled access to the private field tickets
   */
   public Ticket[] getTickets() {
      return tickets;
   }
   
   //MUTATORS
   /*
      Parameter(s): int of the new number of tickets in the list
      Return value: void
      Purpose: changes private field numTickets
   */
   public void setNumTicket(int nm) {
      numTickets = nm;
   }
   
   /*
      Parameter(s): updated ticket array containing all tickets
      Return value: void
      Purpose: changes private field tickets
   */
   public void setTickets(Ticket[] t) {
      tickets = t;
   }
   
   
   //METHODS
   /* 
      Parameter(s): int of the index of the ticket that is to be removed
      Return value: void
      Purpose: to remove a ticket from the array
    */
    public void removeTicket(int i){
      //removes the reserved status of the seat
      (tickets[i].getFlight()).removeSeat(tickets[i].getSeatNum());
      
      //shifts all tickets up
      for (int x = i+1; x < numTicket; x++){
         tickets[x-1] = tickets[x];
      }
      
      //updates number of tickets in the list
      numTicket = numTicket-1;
   
   }
   
   /* 
      Parameter(s): Flight object of the flight that the ticket is for, 
                    String of the seat number which the ticket is for, double of final cost of ticket
      Return value: void
      Purpose: to add a new ticket to the list of tickets
    */
       public void addTicket(Flight flight, String seatNum, double totalCost){
         //checks if the max tickets has been reached, if not
         if (numTicket < MAX_TICKET){
            //new ticket is constructed and added
            tickets[numTicket] = new Ticket(flight, seatNum, totalCost);
            //number of tickets is updated to include the new ticket
            numTicket = numTicket+1;
         } 
         else {
            //maximum tickets has been reached, no more can be added message
            System.out.println("Maximum tickets reached. Cannot add ticket.");
         }
      
      }
   
   /* 
      Parameter(s): N/A
      Return value: void
      Purpose: to print all tickets in the ticket list
    */
       public void printAllTickets(){
      //if there is no ticket in the ticket list, print message
         if (numTicket == 0) {
            System.out.println("There is no current bookings.");
         } 
         //if there is a list of tickets, print all of the tickets' information
         else {
            for (int i = 0; i < numTicket; i++){
               System.out.println((i+1)+"."+tickets[i].printOut() + "\n");
            }
         }
      }
   
   
   /* 
      Parameter(s): int of the index of the specified ticket so the information can be printed
      Return value: void
      Purpose: to print the information on the specified ticket
    */
       public void printTicket(int index){
         //ticket list starts at 0, and displayed tickets starts at 1
         index = index-1;
         //print the information of the ticket
         System.out.println((tickets[index]).printOut());
      }
   
   /* 
      Parameter(s): N/A
      Return value: String of the information of the ticket
      Purpose: to return a string with information of all the tickets for saving use
    */
       public String toString() {
         String s = "" + numTicket;
      
         for (int i = 0; i < tickets.length; i++){
            s += tickets[i];
            s += "\n";
         }
      
         return s;
      }
   
   }