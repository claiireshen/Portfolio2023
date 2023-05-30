/*
	Class Name: CJETBookerRunner
	Author: Claire Shen, Elsie Oh, Junhee Jung, Thomson Ly
	Purpose: Contains main method that runs the CJETbooker class methods
*/

//IMPORT FILES
import java.util.*;

public class CJETBookerRunner {

   //main method
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      
   
      //variables
      final char EXIT = 'e';
      final char MAIN = 'm';
      char choice = 'm';
      boolean successful = false;
      boolean goodData = false;
      boolean validInput = false;
      User currentUser;
   
      //constructing a CJETBooker object named booker
      CJETBooker booker = new CJETBooker("userList.txt", "flightList.txt", "airlineList.txt", "airplaneList.txt", "seatList.txt");
   
      //uploads all the information needed for the program to run
      booker.upload();
   
      //display login page (cannot proceed to next step unless user wants to exit or login to the page)
      while (!successful && choice != EXIT) {
         choice = booker.loginPage();
         
         //if choice equals to 1, displays login page
         if(choice == '1') {
            booker.login();
            successful = true;
         }
          
         //if choice equals to 2, displays create new account page
         else if (choice == '2') {
            booker.createAccount();
         }
      }
   
   	//to find the current user
      currentUser = booker.getCurrentUser();
   
      while (choice != EXIT && (choice == MAIN || (choice >= '1' && choice <= '7')) && successful) {  	
      	
         //if current user is admin, displays admin main menu
         if (booker.currentUserisAdmin()) { 
            choice = booker.menuAdminPage();
            if (choice == '1') {
               booker.addFlight((Admin)(currentUser));
            } 
            else if (choice == '2') {
               booker.removeFlight((Admin)(currentUser));
            } 
            else if (choice == '3') {
               ((Admin)currentUser).updateFlightInfo();
            } 
            else if (choice == '4') {
               ((Admin)currentUser).displayFlights();
            } 
            else if (choice == '5') {
               currentUser.updateInfo();
            } 
                  
         //if current user is customer, displays customer main menu   
         } 
         else {
            choice = booker.menuCustomerPage();
            if(choice == '1') {
               booker.displayAllFlight();
            } 
            else if(choice == '2') {
               booker.searchForFlight();
            } 
            else if(choice == '3') {
               ((Customer)currentUser).printAllTickets();
            } 
            else if(choice == '4') {
               ((Customer)currentUser).cancelBooking();
            } 
            else if(choice == '5') {
               ((Customer)currentUser).checkPoints();
            } 
            else if(choice == '6') {
               booker.reserveFlight((Customer)currentUser);
            } 
            else if (choice == '7')
               ((Customer)currentUser).updateInfo();
         }
      
         goodData = false;
         validInput = false;
         
         //prompts user for the option
         while(!goodData && choice != EXIT) {
            
            while(!validInput){
               try{
                  System.out.print("\nEnter 'e' to exit flight booker or 'm' to return to main menu: ");
                  choice = sc.nextLine().charAt(0);
                  validInput = true;
               }catch(StringIndexOutOfBoundsException obex){
                  System.out.println("\nYou must enter 'e' or 'm'. Please try again.");
               }
            }
            
            //checks for valid input
            if(choice == EXIT || choice == MAIN) {
               goodData = true;
            } 
            else {
               System.out.println("Invalid input. Please try again.");
            }
         }
      }
   
      //displays exit page and exits the program
      booker.exitPage();
   
      //saves all of the information
      booker.save();
   }
}