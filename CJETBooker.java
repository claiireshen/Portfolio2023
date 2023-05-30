/*
Class Name: CJETBooker
Author: Claire Shen, Elsie Oh, Junhee Jung, Thomson Ly
Purpose: To keep track of the information of the CJETBooker and to perform main function for the software 
 */

//Import files
import java.io.*;
import java.util.*;

class CJETBooker {
   Scanner sc = new Scanner(System.in);

//FIELDS
   private static final double POINT_PERCENT = 10;
   private static final int MAX_AIRLINE = 25;
   private static final int MAX_AIRPLANE = 70;
   private static final int MAX_FLIGHT = 70;
   private static final int MAX_USER = 50;

//text files names
   private String userTxt;
   private String flightTxt;
   private String airlineTxt;
   private String airplaneTxt;
   private String seatTxt;

//List
   private Airline[] airlineList;
   private Airplane[] airplaneList;
   private Flight[] flightList;
   private User[] userList;
//other
   private User currentUser;
   private int numAirline;
   private int numAirplane;
   private int numFlight; 
   private int numUser;


//CONSTRUCTOR
/* 
Parameter(s): textfile names of user, flight, airline, airplane and seating layout
Return value: CJETBooker Object
Purpose: To construct the CJETBooker Object
*/
   public CJETBooker(String userTxt, String flightTxt, String airlineTxt, String airplaneTxt, String seatTxt) {
   //Sets all the field
      this.userTxt = userTxt;
      this.flightTxt = flightTxt;
      this.airlineTxt = airlineTxt;
      this.airplaneTxt = airplaneTxt;
      this.seatTxt = seatTxt;
   
   //Construct an array
      airlineList = new Airline[MAX_AIRLINE];
      airplaneList = new Airplane[MAX_AIRPLANE];
      flightList = new Flight[MAX_FLIGHT];
      userList = new User[MAX_USER];
   }

//ASSESSORS
/*
Parameter(s): N/A
Return value: userTxt field
Purpose: to allow accessing to userTxt field
*/
   public String getUserTxt() {
      return userTxt;
   }

/*
Parameter(s): N/A
Return value: flightTxt field
Purpose: to allow accessing to flightTxt field
*/
   public String getFlightTxt() {
      return flightTxt;
   }

/*
Parameter(s): N/A
Return value: airlineTxt field
Purpose: to allow accessing to airlineTxt field
*/
   public String getAirlineTxt() {
      return airlineTxt;
   }

/*
Parameter(s): N/A
Return value: airplaneTxt field
Purpose: to allow accessing to airplaneTxt field
*/
   public String getAirplaneTxt() {
      return airplaneTxt;
   }

/*
Parameter(s): N/A
Return value: currentUser
Purpose: to allow accessing to currentUser field
*/
   public User getCurrentUser() {
      return currentUser;
   }

/*
Parameter(s): N/A
Return value: numAirline
Purpose: to allow accessing to numAirline field
*/
   public int getNumAirline() {
      return numAirline;
   }


//MUTATORS
/*
Parameter(s): another userTxt file name
Return value: N/A
Purpose: to allow changing userTxt field
*/
   public void setUserTxt(String other) {
      this.userTxt = other;
   }

/*
Parameter(s): another flightTxt file name
Return value: N/A
Purpose: to allow changing flightTxt field
*/
   public void setFlightTxt(String other) {
      this.flightTxt = other;
   }

/*
Parameter(s): another airlineTxt file name
Return value: N/A
Purpose: to allow changing airlineTxt field
*/
   public void setAirlineTxt(String other) {
      this.airlineTxt = other;
   }

/*
Parameter(s): another airplane file name
Return value: N/A
Purpose: to allow changing airplaneTxt field
*/
   public void setAirplaneTxt(String other) {
      this.airplaneTxt = other;
   }

/*
Parameter(s): number of airline
Return value: N/A
Purpose: to allow changing numAirline field
*/
   public void setNumAirline(int other) {
      this.numAirline = other;
   }


//METHODS - PAGE
/*
Parameter(s): N/A
Return value: choice of the user 1 - sign in, and 2 - create new account
Purpose: to welcome the user and allow user to log into their account or create a new account
*/
   public char loginPage() {
   //variable
      char choice = '\0';
      String choiceLine = "";
      boolean goodData = false;
   
   //Display welcoming message
      System.out.println("Welcome to the C-JET Flight Booker!\n");
   
      while(!goodData) {
      //Prompt user for the choice
         System.out.print("Press 1 to sign in, 2 to create a new account or e to exit: ");
         choiceLine = sc.nextLine();
         //check for valid input
         choice = choiceLine.charAt(0);
         if (choiceLine.length() == 1 && (choice == '1' || choice == '2' || choice == 'e')) {
            goodData = true;
         }
         //print error message for wrong input
         else {
            System.out.println("Invalid input. Please try again.");
            System.out.println();
         }
      }
      return choice;
   }

/*
Parameter(s): N/A
Return value: void
Purpose: to allow user to enter login information
*/
   public void login() {
   //variable
      boolean found = false;
      boolean valid = false;
      User triedUser = null;
      String id, password;
   
      System.out.println("Please enter your username/email and password to sign in. \n");
   //search for the username or email
      while (!found) {
      //prompt for username or email
         System.out.print("Username/email: ");
         id = sc.nextLine();
      
      //check if it exists
         for (int i = 0; i < numUser && !found; i++) {
            if (id.equals(userList[i].getUsername()) || id.equals(userList[i].getEmail())) {
            //set triedUser with this found user
               triedUser = userList[i];
               found = true;
            }
         }
      
         if (!found) {
         //Print error message
            System.out.println("Username/email does not exist. Please try again.");
         }
      }
   
      while(!valid) {
      //Prompt for password
         System.out.print("Password: ");
         password = sc.nextLine();
      
      //check if it exist in the database
         if(password.equals(triedUser.getPassword())) {
         //set currentUser with this triedUser
            currentUser = triedUser;
         //upload all of the tickets that user has
            if(currentUser instanceof Customer) {
               uploadUserTicket();
            }
         //prints welcoming messages
            System.out.println("Hi " + currentUser.getUsername() + "! Welcome to the C-JET Flight Booker!");
            valid = true;
         } 
         else { 
         //prints error message
            System.out.println("Incorrect password. Please try again.\n");
         }
      }
   }

/*
      Parameter(s): N/A
      Return value: void
      Purpose: to allow user to create new account
   */
   public void createAccount() {
   //variable
      String email, username = null;
      String password, adminChoice, accessCode;
      boolean valid = false;
      boolean validCode = false;
      Airline currentAirline = null;
      User user;
   
   //Prompt user for the information
      System.out.println("Please enter the following information to create a new account.");
      System.out.print("Email: ");
      email = sc.nextLine();
   
   //loop until there is no duplicate username
      while(!valid) {
         System.out.print("Username: ");
         username = sc.nextLine();
         valid = true;
         for (int i = 0; i < numUser; i++) {
            if (username.equals(userList[i].getUsername())) {
               valid = false;
               System.out.println(username + " already exists. Please enter different user name.");
            }
         }
      }
   
   //Prompt user for password
      System.out.print("Password: ");
      password = sc.nextLine();
   //Prompt user for airline info
      System.out.print("Airline administrator (yes/no): ");
      adminChoice = sc.nextLine();
   
      valid = false;
      while (!valid) {
      //if admin
         if(adminChoice.equals("yes")) {
            valid = true;
         
            while (!validCode) {
            //Prompt user for access code
               System.out.print("Please enter your airline access code: ");
               accessCode = sc.nextLine();
            
            //check for valid code
               for (int i = 0; i < numAirline; i++) {
                  if (accessCode.equals(airlineList[i].getAccessCode())) {
                     validCode = true;
                     currentAirline = airlineList[i];
                  }
               }
               
               //If accessCode entered is not a valid code
               if (!validCode) {
                  System.out.println("Invalid input. Please try again");
               }
            }
         
         //create Admin user and add it to user list
            user = new Admin(username, email, password, currentAirline);
            addUser(user);
         
         //print successful message
            System.out.println("You have successfully created new account!");            
         
         } 
         else if (adminChoice.equals("no")) {
            valid = true;
         //Create Customer user and add it to user list
            user = new Customer(username, email, password);
            boolean possible = addUser(user);
         
            if(!possible) {
            //print not successful message
               System.out.println("We no longer accept more new account");
            }
            else {
            //print successful message
               System.out.println("You have successfully created new account!");
               try {
                  BufferedWriter out = new BufferedWriter(new FileWriter(username + ".txt"));
                  out.write("0");
                  out.newLine();
                  out.close();
               } 
               catch (IOException iox) {
                  System.out.println("Error creating file!");
               }
            
            
            }
         } 
         else {
            System.out.print("Invalid input. Please choose between \"yes\" or \"no\": ");
            adminChoice = sc.nextLine();
         }
      }
   }
/*
Parameter(s): N/A
Return value: char choice
Purpose: to allow administrator to choose a function that they want to perform. 
*/   
   public char menuAdminPage(){
   //variable
      boolean valid = false;
      boolean validInput = false;
      char choice = '\0';
   
   //displaying all the possible actions
      System.out.println("\nMenu\n1.Add new available flight\n2.Remove flight\n3.Change departure/arrival of a flight\n4.Display all the current flights\n5.Change personal information");
      
      while(!validInput){
         try{
            System.out.print("\nEnter a number between 1 to 5: ");
            choice = sc.nextLine().charAt(0);
            validInput = true;
         }catch(StringIndexOutOfBoundsException obex){
            System.out.println("\nYou must enter a number between 1 to 5. Please try again.");
         }
      }
      while(!valid){
      //validation
         if(choice >= '1' && choice <= '5'){
            valid = true;
         }
         else{
            System.out.print("Invalid input. Please try again.");
            choice = sc.nextLine().charAt(0);
         }
      }
      return choice;
   }

/*
Parameter(s): N/A
Return value: char choice
Purpose: to allow user to choose a function that they want to perform. 
*/   
   public char menuCustomerPage(){
   //variable
      boolean valid = false;
      char choice = '\0';
      boolean validInput = false;
   
   //displaying all the possible actions
      System.out.println("\nMenu\n1.Display all Flights \n2.Search for flights\n3.Check/Print current bookings\n4.Cancel booking\n5.Check points\n6.Reserve a ticket\n7.Change personal information");
      
      while(!validInput){
         try{
            System.out.print("\nEnter a number between 1 to 7: ");   
            choice = sc.nextLine().charAt(0);
            validInput = true;
         //there will be an error if user types in nothing
         //this catch is for that
         }catch(StringIndexOutOfBoundsException obex){
            System.out.println("\nYou must enter a number between 1 to 7. Please try again.");   
         }
      }
      while(!valid){
         if(choice >= '1' && choice <= '7'){
            valid = true;
         }
         else{
            System.out.print("Invalid input. Please try again: ");
            choice = sc.nextLine().charAt(0);
         }
      }
      return choice;
   }


/*
Parameter(s): N/A
Return value: void
Purpose: to display exit page
*/
   public void exitPage() {
   //Print out exit message
      System.out.println("\nThank you for using C-JET Flight Booker!");
      System.out.println("We hope to see you again soon in the future!");
   }

//EXTRA METHODS
/*
Parameter(s): N/A
Return value: boolean of whether creating new account was successful
Purpose: to allow user to create new account
*/
   public boolean addUser(User newUser) {
      if (numUser == MAX_USER) {
         return false;
      } 
      else {
         userList[numUser] = newUser;
         numUser++;
         return true;
      }
   }

/*
Parameter(s): N/A
Return value: boolean
Purpose: To tell if the currentUser is Admin or customer
*/
   public boolean currentUserisAdmin() {
   //if current user is admin, return true
      if (currentUser instanceof Admin) {
         return true;
      } 
      else {
         return false;
      }
   }

/*
Parameter(s): N/A
Return value: void
Purpose: To upload all of the files that is necessary for the software to work [Wrapper method]
*/
   public void upload() {
   //call each of the upload methods
      uploadAllAirplane();
      uploadAllFlights();
      uploadAllAirlines();
      uploadAllUsers();
      uploadAllSeatsInfo();
   }

/*
Parameter(s): N/A
Return value: void
Purpose: To upload informations of all of the users in the software
*/
   public void uploadAllUsers() {
   //variable
      String username, email, password, airlineName;
      boolean admin;
      int point;
      Airline airline;
   
      try {
         BufferedReader in = new BufferedReader(new FileReader(userTxt));
      //first line indicates the total number of users
         numUser = Integer.parseInt(in.readLine());
      
         for(int i = 0; i < numUser; i++) {
         //gets information
            username = in.readLine();
            email = in.readLine();
            password = in.readLine();
            admin = Boolean.parseBoolean(in.readLine());
         
         //Create two different object based on whether it is admin or not
            if (admin) {
            //for admin, it reads in airline name
               airlineName = in.readLine();
            //search for airline with given airline name
               airline = searchAirline(airlineName);
            //construct admin object and add it to the array
               userList[i] = new Admin(username, email, password, airline);
            } 
            else {
            //For customer, it reads in point
               point = Integer.parseInt(in.readLine());
            //construct customer object and add it to the array of users
               userList[i] = new Customer(username, email, password, point);
            }
         }
         in.close();
      } 
      catch(IOException iox) {
         System.out.println("Error reading in file!");
      }      
   }

/*
Parameter(s): N/A
Return value: void
Purpose: To upload informations of all of the flights 
*/
   public void uploadAllFlights() {
      String flightID,airlineName,origin,destination,departureDate,departureTime,arrivalDate,arrivalTime,airplaneId,cost;
      try {
         BufferedReader in = new BufferedReader(new FileReader(flightTxt));
      //first line is the total number of flight
         numFlight = Integer.parseInt(in.readLine());
      
         for (int i = 0; i < numFlight; i++) {
            flightID = in.readLine();
            airlineName = in.readLine();
            origin = in.readLine();
            destination = in.readLine();
            departureDate = in.readLine();
            departureTime = in.readLine();
            arrivalDate = in.readLine();
            arrivalTime = in.readLine();
            airplaneId = in.readLine();
            cost = in.readLine();
            Airplane airplane = searchAirplane(airplaneId);
            flightList[i] = new Flight(flightID,airlineName,airplane,origin,destination,departureDate,departureTime,arrivalDate,arrivalTime,Double.parseDouble(cost));
         
         }
         in.close();
      }
      catch (IOException iox) {
         System.out.println("Error reading in file!");
      }
   }

/*
Parameter(s): N/A
Return value: void
Purpose: To upload airplane information
*/
   public void uploadAllAirplane() {
   //variables
      String airplaneID;
      char size;
   
      try {
         BufferedReader in = new BufferedReader(new FileReader(airplaneTxt));
      //first line is the number of airplanes
         numAirplane = Integer.parseInt(in.readLine());
      
         for (int i = 0; i < numAirplane; i++) {
            airplaneID = in.readLine();
         //first letter defines the size of the airplane
            size = airplaneID.charAt(0);
         
         //constructs airplane according to the size and add to the array
            if (size == 'S') {
               airplaneList[i] = new Airplane_S(airplaneID);
            } 
            else if (size == 'M') {
               airplaneList[i] = new Airplane_M(airplaneID);
            } 
            else {
               airplaneList[i] = new Airplane_L(airplaneID);
            }
         }
         in.close();
      } 
      catch(IOException iox) {
         System.out.println("Error reading in file!");
      }
   }

/*
Parameter(s): N/A
Return value: void
Purpose: To upload informations of the airlines
*/
   public void uploadAllAirlines() {
      String airlineName,accesscode;
      int numFlight;
      try {
         BufferedReader in = new BufferedReader(new FileReader(airlineTxt));
      //first line is the number of airlines
         numAirline = Integer.parseInt(in.readLine());
      
      //for each airlines, reads in the information and insert it into the array
         for (int i = 0; i < numAirline; i++) {
            airlineName = in.readLine();
            accesscode = in.readLine();
            numFlight = Integer.parseInt(in.readLine());
            Flight[] flightList = new Flight[MAX_FLIGHT];
            for(int j = 0; j < numFlight;j++) {
               flightList[j] = searchFlight(in.readLine());
            }
            airlineList[i] = new Airline(airlineName,numFlight,accesscode,flightList,airplaneList);
         }
         in.close();
      } 
      catch(IOException iox) {
         System.out.println("Error reading in file!");
      }
   }

/*
Parameter(s): N/A
Return value:void
Purpose: To upload seats of each flight
*/
   public void uploadAllSeatsInfo() {    
   //variable
      String flightID;
      Flight flight;
      Airplane airplane;
      char[][] seat;
      int row, col;
   
      try {
         BufferedReader in = new BufferedReader(new FileReader(seatTxt));
      
      //loop for amount of flight
         for (int i = 0; i < numFlight; i++) {
         //read in flight id
            flightID = in.readLine();
         //find flight with flightID
            flight = searchFlight(flightID);
         
         //get the size of flight's airplane
            airplane = flight.getAirplane();
         //If it's small airplane row = 4 col = 25 
            if (airplane instanceof Airplane_S) {
               row = ((Airplane_S)airplane).getNumRowS();
               col = ((Airplane_S)airplane).getNumColS();
            } 
            //If it's medium sized airplane, row = 7 col = 25
            else if (airplane instanceof Airplane_M) {
               row = ((Airplane_M)airplane).getNumRowM();
               col = ((Airplane_M)airplane).getNumColM();
            } 
            //If it's large sized airplane, row = 10 col = 25
            else {
               row = ((Airplane_L)airplane).getNumRowL();
               col = ((Airplane_L)airplane).getNumColL();
            }
         //construct array of seat
            seat = new char [row][col];
            String seatLine;
         //read in the file
            for(int x = 0; x < row; x++){
               seatLine = in.readLine();
               for(int j = 0; j < col; j++){
                  seat[x][j] = seatLine.charAt(j);
               }
            }
            flight.uploadSeat(seat);
         }
         in.close();
      } 
      catch(IOException iox) {
         System.out.println("Error reading infile");
      }
   }

/*
Parameter(s):N/A
Return value: void
Purpose:To upload currentUser's ticket
*/
   private void uploadUserTicket() {
   //variable
      boolean goodData = false;
      int numTicket;
      String flightID, seatNum;
      double cost;
      TicketList ticketList = null;
   
      while(!goodData) {
         try {
         //Customer's file name is username.txt
            String fileName = currentUser.getUsername() + ".txt";
            BufferedReader in = new BufferedReader(new FileReader(fileName));
         
         //If BufferedReader was successful, set goodData as true
            goodData = true;
         
         //set numFlight with the first line of the file
            numTicket = Integer.parseInt(in.readLine());
         
         //Construct ticketList 
            ticketList = new TicketList();
         
         //go through each flight and add it to ticketList
            for (int i = 0; i < numTicket; i++) {
               flightID = in.readLine();
            //find corresponding flight id
               Flight flight = searchFlight(flightID);
               seatNum = in.readLine();
               cost = Double.parseDouble(in.readLine());
            
            //Insert it into the array of Ticket
               ticketList.addTicket(flight, seatNum, cost);
            }
         
            in.close();            
         } 
         catch(IOException iox) {
            //Print error message
            System.out.println("Wrong file entered");
         }
      }
   //call customer's uploadTicket()
      ((Customer)currentUser).uploadTicket(ticketList);   
   }

/*
Parameter(s): N/A
Return value: void
Purpose: To save all of the information [Wrapper methods]
*/
   public void save() {
   //call all of the save methods
      saveUsers();
      saveCurrentUserInfo();
      saveFlights();
      saveSeats();
      saveAirlines();
   }

/*
Parameter(s): N/A
Return value: void
Purpose: To save all of the user's information
*/
   public void saveUsers() {
      try {
         BufferedWriter out = new BufferedWriter(new FileWriter(userTxt));
      //first line is the number of users
         out.write(numUser + "");
         out.newLine();
      
      //loop through each customer and write it on the file
         for (int i = 0; i < numUser; i++) {
            out.write(userList[i].getUsername()+"");
            out.newLine();
            out.write(userList[i].getEmail()+"");
            out.newLine();
            out.write(userList[i].getPassword()+"");
            out.newLine();
            if(userList[i] instanceof Customer){
               out.write("false");
               out.newLine();
               out.write(((Customer)userList[i]).getPoints()+"");
               out.newLine();
            }
            else if(userList[i] instanceof Admin){
               out.write("true");
               out.newLine();
               out.write(((Admin)userList[i]).getAirline().getAirlineName());
               out.newLine();
            }
         }
         out.close();
      } 
      catch(IOException iox) {
         System.out.println("Error reading in file!");
      }
   }

/*
Parameter(s): N/A
Return value: void
Purpose: To save current Customer's ticket booking informations
*/
   public void saveCurrentUserInfo() {
   //check if the currentUser is Customer
      if (currentUser instanceof Customer) {
      //if it is a Customer, call saveTicket
         ((Customer)currentUser).saveTicket();
      }
   }

/*
Parameter(s): N/A
Return value: void
Purpose: To save all of the flight informations
*/
   public void saveFlights() {
      try {
         BufferedWriter out = new BufferedWriter(new FileWriter(flightTxt));
      //first line is the number of flights
         out.write(numFlight + "");
         out.newLine();
      
      //go through each flight and write all of the information on file
         for (int i = 0; i < numFlight; i++) {
            out.write(flightList[i].getId());
            out.newLine();
            out.write(flightList[i].getAirlineName());
            out.newLine();
            out.write(flightList[i].getOrigin());
            out.newLine();
            out.write(flightList[i].getDestination());
            out.newLine();
            out.write(flightList[i].getDepartureDate());
            out.newLine();
            out.write(flightList[i].getDepartureTime());
            out.newLine();
            out.write(flightList[i].getArrivalDate());
            out.newLine();
            out.write(flightList[i].getArrivalTime());
            out.newLine();
            out.write((flightList[i].getAirplane()).getId());
            out.newLine();
            out.write(flightList[i].getCost() + "");
            out.newLine();
         }
         out.close();
      } 
      catch(IOException iox) {
         System.out.println("Error reading in file!");
      }
   }

/*
Parameter(s): N/A
Return value: void
Purpose:	to save all of the airline information
*/
   public void saveAirlines() {
   //variable
      Flight[] flights;
   
      try {
         BufferedWriter out = new BufferedWriter(new FileWriter(airlineTxt));
      //first line is number of airlines
         out.write(numAirline + "");
         out.newLine();
      
      //go through each airlines and print their informations
         for(int i = 0; i < numAirline; i++) {
            out.write(airlineList[i].getAirlineName());
            out.newLine();
            out.write(airlineList[i].getAccessCode());
            out.newLine();
         
         //output number of flight
            out.write(airlineList[i].getNumFlight()+"");
            out.newLine();
         
            flights = airlineList[i].getFlightList();
            for (int j = 0; j < airlineList[i].getNumFlight(); j++) {
               out.write(flights[j].getId());
               out.newLine();
            }
         }
         out.close();
      }
      catch (IOException iox) {
         System.out.println("Error reading in file!");
      }
   }






/*
Parameter(s): N/A
Return value: void
Purpose:	to save all of the seats information
*/
   public void saveSeats() {
   //variable
      char[][] seats = null;
   
      try {
         BufferedWriter out = new BufferedWriter(new FileWriter(seatTxt));
      
      //go through each flight
         for (int i = 0; i < numFlight; i++) {
            out.write(flightList[i].getId());
            out.newLine();
         //get seat information from current flight
            seats = flightList[i].saveSeat();
         //print on the file
            for (int x = 0; x < seats.length; x++) {
               for (int j = 0; j < seats[x].length; j++) {
                  out.write(seats[x][j]+"");
               }
               out.newLine();
            }
         }
         out.close();
      } 
      catch(IOException iox) {
         System.out.println("Error reading in file");
      }
   }


//METHODS - PRIVATE
/*
Parameter(s): the name of the airline
Return value: Airline object
Purpose: to find and return the airline object with the given airline name
*/
   private Airline searchAirline(String airlineName) {
      for(int i = 0; i < numAirline; i++) {
         if(airlineList[i].getAirlineName().equals(airlineName)) {
            return airlineList[i];
         }
      }
      return null;
   }

/*
Parameter(s): the airplane ID
Return value: Airplane object
Purpose: to find and return the airplane object with the given airplane ID
*/
   private Airplane searchAirplane(String airplaneID) {
      for(int i = 0; i < numAirplane; i++) {
         if(airplaneList[i].getId().equals(airplaneID)) {
            return airplaneList[i];
         }
      }
      return null;
   }

/*
Parameter(s): the flight ID
Return value: Flight object
Purpose: to find and return the flight object with the given flight ID
*/
   private Flight searchFlight(String flightID) {
      for(int i = 0; i < numFlight; i++) {
         if(flightList[i].getId().equals(flightID)) {
            return flightList[i];
         }
      }
      return null;
   }

/*
Parameter(s): the airplane id
Return value: boolean 
Purpose: to find and return the boolean of whether there is a duplicate airplane id
*/

//true means there is another airplane id that is same as the given id
   public boolean checkDuplicatesAirplaneId(String id){
      for(int i = 0;i < numFlight;i++){
         if(id.equals(flightList[i].getAirplane().getId())){
            return true;
         }
      }
      return false;
   }

   private void shiftDownList(int startIndex, int numItems, String [] list){
      for(int i = startIndex; i < numItems-1 && list[i] != null;i++){
         list[i] = list[i+1];
      }
      list[numItems-1] = null;
   }

//METHODS - SEARCHING AND SORTING
/*
Parameter(s): N/A
Return value: void
Purpose: to display search by flight menu for the customer
*/
   public void searchForFlight() {
      boolean validInput = false;
      boolean toContinue = false;
      
      Flight [] list = null;
      
      int choice;
      
      while(!toContinue){
         System.out.println("\nSearch options");
         System.out.println("1. Search for flight by airline");
         System.out.println("2. Search for flight by cost");
         System.out.println("3. Search for flight by origin and destination");
         System.out.println("4. Search for flight by departure date");
         while(!validInput){
            System.out.print("\nPlease enter an option: ");
            choice = sc.nextLine().charAt(0);
            if(choice == '1'){
               list = searchByAirline(flightList);
               validInput = true;
            
               if(list != null){
                  System.out.println();
                  displayFlightList(list);
                  toContinue = sortFlights(list);
               }else{
                  System.out.print("\nNo match found. Press enter to leave search menu.");
                  sc.nextLine();
                  toContinue = true;
               }
            
            }else if(choice == '2'){
               list = searchByCost(flightList);
               validInput = true;
               if(list != null){
                  System.out.println();
                  displayFlightList(list);
                  toContinue = sortFlights(list);
               }else{
                  System.out.print("No match found. Press enter to leave search menu.");
                  sc.nextLine();
                  toContinue = true;
               }
            }else if(choice == '3'){
               list = searchByOrgDes(flightList);
               validInput = true;
               if(list != null){
                  System.out.println();
                  displayFlightList(list);
                  toContinue = sortFlights(list);
               }else{
                  System.out.print("No match found. Press enter to leave search menu.");
                  sc.nextLine();
                  toContinue = true;
               }
            }else if(choice == '4'){
               list = searchByDeparture(flightList);
               validInput = true;
               if(list != null){
                  System.out.println();
                  displayFlightList(list);
                  toContinue = sortFlights(list);
               }else{
                  System.out.print("No match found. Press enter to leave search menu.");
                  sc.nextLine();
                  toContinue = true;
               }
            }else{
               System.out.println("Please enter a number between 1 to 4.");
            }
         
         
         }
         validInput = false;
      }
   }

/*
Parameter(s): N/A
Return value: array of Flight object
Purpose: to search through flight list with different options (eg. airline, destination, etc.). This method returns array of flights that matches with user's need
         first option when search for flight menu is entered
*/

   public boolean sortFlights(Flight [] list){
      char choice;
      boolean validInput = false;
      boolean toContinue = false;
      
      while(!toContinue){
         
         System.out.println("\nSort options");
         System.out.println("1. Sort by cost");
         System.out.println("2. Sort by departure");
         System.out.println("3. Sort by destination");
         System.out.print("\nPlease enter an option or enter 'e' to leave search menu: ");
         choice = sc.nextLine().charAt(0);
      
         while(!validInput){
            if(choice == '1'){
               sortByCost(list);
               displayFlightList(list);
               validInput = true;
            }else if(choice == '2'){
               sortByDeparture(list);
               displayFlightList(list);
               validInput = true;
            }else if(choice == '3'){
               sortByDestination(list);
               displayFlightList(list);
               validInput = true;
            }else if(choice == 'e'){
               validInput = true;
               toContinue = true;
            }else{
               System.out.println("\nInvalid input. Please try again.");
            }
         }
         validInput = false;
      }
      return toContinue;
      
   }

/*
Parameter(s): String name, array of Flight
Return value: Flight []
Purpose: to search through flight list by airline. Returns array of flights with given airline name
*/

   public Flight[] searchByAirline(Flight[] flightList){
   //variable
      int count = 0;
      
      System.out.print("\nEnter the name of the airline: ");
      String name = sc.nextLine();
      
   //first loop is to count how many flights in flight list that matches with given criteria
      for(int i = 0;i < flightList.length;i++){
         if(flightList[i] != null && name.equals(flightList[i].getAirlineName())){
            count++;
         }     
      }
   
   //If there is no match, returns null
      if(count == 0){
         return null;
      }
   
   //create an array of Flight with the amount of match found
      Flight[] flightsOf = new Flight [count];
      count = 0;
   
   //second for loop is to insert flights into new flight array that matches with given criteria
      for(int i = 0;i < flightList.length; i++){
         if(flightList[i] != null && name.equals(flightList[i].getAirlineName())){
            flightsOf[count] = flightList[i];
            count++;
         } 
      }
      return flightsOf;
   }

/*
Parameter(s): double cost
Return value: Flight []
Purpose: to search through flight list by cost. Returns array of flights with given cost. 
*/

   public Flight[] searchByCost(Flight[] flightList){
   //variable
      int count = 0;
      double cost = 0;
      boolean validInput = false;
      
      while(!validInput){
         System.out.print("\nEnter the cost: ");
         try{
            cost = sc.nextDouble();
            validInput = true;
         //dummy line
            sc.nextLine();
         }catch(InputMismatchException imex){
            System.out.println("Invalid input. Please try again.");
            //flush
            sc.next();
         }
      }
   //first loop is to count how many flights in flight list that matches with given criteria
      for(int i = 0;i < flightList.length;i++){
         if(flightList[i] != null && cost >= flightList[i].getCost()){
            count++;
         }
      }
   
   //If there is no match, returns null
      if(count == 0){
         return null;
      }
   
   //create an array of Flight with the amount of match found
      Flight[] flightsOf = new Flight[count];
      count = 0;
   
   //second for loop is to insert flights into new flight array that matches with given criteria
      for(int i = 0;i < flightList.length;i++){
      //if the flightList[i] is cheaper than or equals to given cost, it adds into array.
         if(flightList[i] != null && cost >= flightList[i].getCost()){
            flightsOf[count] = flightList[i];
            count++;
         }
      }
      return flightsOf;
   }

/*
Parameter(s): String origin, String destination
Return value: Flight []
Purpose: to search through flight list by origin and destination. Returns array of flights with given origin and destination. 
*/

   public Flight[] searchByOrgDes(Flight[] flightList){
   //variable
      int count = 0;
      
      System.out.print("\nEnter the name of the origin (city, country): ");
      String origin = sc.nextLine();
      System.out.print("\nEnter the name of the destination (city, country): ");
      String destination = sc.nextLine();
      
   //first loop is to count how many flights in flight list that matches with given criteria      
      for(int i = 0;i < flightList.length;i++){
         if(flightList[i] != null && origin.equals(flightList[i].getOrigin()) && destination.equals(flightList[i].getDestination())){
            count++;
         }     
      }
   
   //If there is no match, returns null
      if(count == 0){
         return null;
      }
   
   //create an array of Flight with the amount of match found  
      Flight [] flightsOf = new Flight [count];
      count = 0;
   
   //second for loop is to insert flights into new flight array that matches with given criteria
      for(int i = 0;i < flightList.length;i++){
      //if origin and destination matches given criteria, it adds to the array
         if(flightList[i] != null && origin.equals(flightList[i].getOrigin()) && destination.equals(flightList[i].getDestination())){
            flightsOf[count] = flightList[i];
            count++;
         } 
      }
      return flightsOf;
   }

/*
Parameter(s): String date
Return value: Flight []
Purpose: to search through flight list by departure date. Returns array of flights with given departure date.
*/

   public Flight[] searchByDeparture(Flight[] flightList){
   //variables
      final int YEARINDAYS = 365;
      int count = 0;
      int numDays = 0;
      int numDays2 = 0;
      
      //year, month and day of user input
      int year = 0;
      int month = 0;
      int day = 0;
      
      int year2 = 0;
      int month2 = 0;
      int day2 = 0;
      
      //to store year, month and day values
      String [] ymd2 = null;
      
      String date = "";
      boolean validInput = false;
      boolean validInput2 = false;
      
      while(!validInput2){
         while(!validInput){
            System.out.print("\nEnter desirable departure date in YYYY/MM/DD with slashes: ");
            date = sc.nextLine();
         
            if(date.indexOf('/') == -1){
               System.out.println("\nPlease enter the date with slashes");
            }else if(date.length() != 10){
               System.out.println("\nPlease follow the YYYY/MM/DD");
            }else{
               validInput = true;
            }
         }
         //y = year
         //m = month
         //d = day
         String [] ymd = date.split("/");
         
         try{
            year = Integer.parseInt(ymd[0]);
            month = Integer.parseInt(ymd[1]);
            day = Integer.parseInt(ymd[2]);
            
            validInput2= true;
            
         }catch(InputMismatchException imex){
            System.out.println("\nEnter number and slashes only");
            //flush
            sc.next();
         }
      }
   //converting date into days
      if(year > 2018){
         numDays = (year - 2018)*YEARINDAYS;
      }
   
   //number of days differ by months
      for(int i = 1;i < month;i++){
         if(i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12){
            numDays += 31;
         }
         else if(i == 2){
            numDays += 28;
         }
         else{
            numDays += 30;
         }
      }
   
      numDays += day;
   
      for(int i = 0;i < flightList.length;i++){
         if(flightList[i] != null){
            ymd2 = flightList[i].getDepartureDate().split("/");
            year2 = Integer.parseInt(ymd2[0]);
            month2 = Integer.parseInt(ymd2[1]);
            day2 = Integer.parseInt(ymd2[2]);
            if(year2 > 2018){
               numDays2 = (year2 - 2018)*YEARINDAYS;
            }
         
            for(int j = 1;j < month2;j++){
               if(j == 1 || j == 3 || j == 5 || j == 7 || j == 8 || j == 10 || j == 12){
                  numDays2 += 31;
               }
               else if(j == 2){
                  numDays2 += 28;
               }
               else{
                  numDays2 += 30;
               }
            }
         
            numDays2 += day2;
         
         //if flight departs at within 2 days of given date
            if(numDays <= numDays2+2 && numDays >= numDays2-2){
               count++;
            }
            numDays2 = 0;  
         }
      }
   
      if(count == 0){
         return null;
      }
   
      Flight [] flightsOf = new Flight[count];
      count = 0;
      
      for(int i = 0;i < flightList.length;i++){
         if(flightList[i] != null){
            ymd2 = flightList[i].getDepartureDate().split("/");
            year2 = Integer.parseInt(ymd2[0]);
            month2 = Integer.parseInt(ymd2[1]);
            day2 = Integer.parseInt(ymd2[2]);
            if(year2 > 2018){
               numDays2 = (year2 - 2018)*YEARINDAYS;
            }
         
            for(int j = 1;j < month2;j++){
               if(j == 1 || j == 3 || j == 5 || j == 7 || j == 8 || j == 10 || j == 12){
                  numDays2 += 31;
               }
               else if(j == 2){
                  numDays2 += 28;
               }
               else{
                  numDays2 += 30;
               }
            }
         
            numDays2 += day2;
         
            if(numDays <= numDays2+2 && numDays >= numDays2-2){
               flightsOf[count] = flightList[i];
               count++;
            }
            numDays2 = 0;  
         }
      }
      return flightsOf;
   }


/*
Parameter(s): Flight[] list
Return value: Flight[] 
Purpose: to sort given array of flight by low priced to high priced
*/
   public void sortByCost(Flight[] list){
   //INSERTION SORT
      for(int i = 1;i < list.length;i++){
      //setting the key element to the second element in the list
         Flight x = list[i];
         int j = i;
         while(j > 0 && x.compareToCost(list[j-1]) < 0){
            list[j] = list[j-1];
            j--;
         }
         list[j] = x;
      }
   }

/*
Parameter(s): Flight[] list
Return value: Flight[]
Purpose: to sort destinations of given array of flight alphabetically
*/
   public void sortByDestination(Flight[] list){
   //SELECTION SORT
      for(int i = list.length-1;i >= 1;i--){
      //set the initial max item to the element at the top
         int max = i;
         for(int j = 0;j < i;j++){
            if(list[max].compareToDestination(list[j]) < 0){
            //set the max to the j
               max = j;
            }
         }
      //swapping the max. element to the right spot
         Flight temp = list[max];
         list[max] = list[i];
         list[i] = temp;   
      }
   }

/*
Parameter(s): Flight [] list
Return value: Flight[]
Purpose: to sort of given array of flight by most upcoming to latest
*/
   public void sortByDeparture(Flight [] list){
   //BUBBLE SORT
   //setting the upperbound to list.length-1
      int upperbound = list.length-1; 
   //beginning, set the sorted to be false
      boolean sorted = false;
      for(int i = upperbound;i >= 1 && !sorted;i--){
      //if no element has been swapped, it means list is sorted
         sorted = true;
         for(int j = 0;j < i;j++){
         //if element at j is bigger than the element one ahead, that means 
         //list has to be sorted
            if(list[j].compareToDeparture(list[j+1]) > 0){
            //sorted becomes false
               sorted = false;
            //swapping elements
               Flight temp = list[j];
               list[j] = list[j+1];
               list[j+1] = temp;
            }
         }
      }
   }

/*
Parameter(s): array of Flight object
Return value: array of sorted Flight object
Purpose: To sort the Flight list with arriving/departing date
*/
   private Flight[] sortByDate(Flight[] list) {
   //BUBBLE SORT
   //setting the upperbound to list.length-1
      int upperbound = list.length-1; 
   //beginning, set the sorted to be false
      boolean sorted = false;
      for(int i = upperbound;i >= 1 && !sorted;i--){
      //if no element has been swapped, it means list is sorted
         sorted = true;
         for(int j = 0;j < i;j++){
         //if the time at j is bigger than the element one ahead, that means list has to be sorted
            if(list[j+1].isBefore(list[j])){
            //sorted becomes false
               sorted = false;
            //swapping elements
               Flight temp = list[j];
               list[j] = list[j+1];
               list[j+1] = temp;
            }
         }
      }
      return list;
   }

/*
Parameter(s): N/A
Return value: void
Purpose: To display all of the flights
*/
   public void displayAllFlight() {
   //variable
      char choice;
      boolean valid = false;
   
   //display all of the flight
      System.out.println("Here are the list of flights that are available");
      for (int i = 0; i < numFlight; i++) {
         flightList[i].displayFlight();
      }
   
   //Display option that are available
      System.out.println("Options");
      System.out.println("1. Possible round trips");
      System.out.println("2. Possible multidestination trips");
      System.out.println("3. None of the above");
   
   //loop until valid input is entered
      while(!valid) {
      //Prompt user for the option
         System.out.print("\nEnter a choice of option: ");
         choice = sc.nextLine().charAt(0);
      
      //perform different function for given options
         if (choice == '1') {
            valid = true;
            listAllRoundTrip();
         }
         else  if(choice == '2') {
            valid = true;
            listAllMultipleTrip();
            
         //doesn't perform any function
         }else if(choice == '3'){
            valid = true;
         } 
         //invalid input
         else {
            System.out.println("Invalid input. Please try again.");
         }
      }
   }

   public void displayFlightList(Flight [] list){
      for(int i = 0;i < list.length && list[i] != null;i++){
         list[i].displayFlight();
      }
   }

   /*
      Parameter(s): int numberOfStops, String [] flightIds, String finalDestination, String startingPoint, int tripNum
      Return value: void
      Purpose: To print out flight id and information of all possible routes to destination in the number of flights that user input
   */
   public void multipleDestinationOut(String [] flightIds, String finalDestination, String startingPoint, int tripNum){
      //current flight is not yet found
      Flight currentFlight = null;
      
      //if the startingPoint is equal to the final destination and tripNum is equal to flightIds.length, that means 
      //I have reached the end of my search
      if(startingPoint.equals(finalDestination) && tripNum == flightIds.length){
         //display flights
         for(int i = 0;i < flightIds.length && flightIds[i] != null;i++){
            searchFlight(flightIds[i]).displayFlight();
         }
         System.out.println();
      //if tripNum is less than flightIds.length, I habe still have to find flights
      }else if(tripNum < flightIds.length){
      //the beginning of the search
         if(tripNum == 0){
            for(int i = 0;i < numFlight;i++){
            //in the beginning, all I have to do is find a flight that its origin is equal to the starting point
            //and its destination is not equal to the final destination
               if(startingPoint.equals(flightList[i].getOrigin()) && !(flightList[i].getDestination().equals(finalDestination))){
                  //add flight id to the flightIds
                  flightIds[tripNum] = flightList[i].getId();
                  //update tripNum
                  tripNum++;
                  //update startingPoint
                  startingPoint = flightList[i].getDestination();
                  //call multipleDestinationOut
                  multipleDestinationOut(flightIds, finalDestination, startingPoint, tripNum);
                  //if no match found, subtract -1 from tripNum
                  tripNum--;
               }
            }  
         //if tripNum is equal to the flightIds.length-1
         //I just need to find one more flight.   
         }else if(tripNum == flightIds.length-1){
         //get current flight
            currentFlight = searchFlight(flightIds[tripNum-1]);      
         
            for(int i = 0;i < numFlight;i++){
            //the last flight has to connect with the final destination
               if(currentFlight.getDestination().equals(flightList[i].getOrigin()) && flightList[i].getDestination().equals(finalDestination)){
                  if(flightList[i].compareToDepArr(currentFlight) > 0){
                     flightIds[tripNum] = flightList[i].getId();
                     tripNum++;
                     startingPoint = flightList[i].getDestination();
                     multipleDestinationOut(flightIds, finalDestination, startingPoint, tripNum);
                     tripNum--;
                  }
               }
            }
            tripNum--;
         //for other situation, all I need to worry about is
         //to make sure destination of currentFlight matches with the next flight's origin (connect)
         //and the destination of next flight is not equal to the final destination
         }else{
            currentFlight = searchFlight(flightIds[tripNum-1]);     
            for(int i = 0;i < numFlight;i++){
               if(currentFlight.getDestination().equals(flightList[i].getOrigin()) && !(flightList[i].getDestination().equals(finalDestination))){
                  if(flightList[i].compareToDepArr(currentFlight) > 0){
                     flightIds[tripNum] = flightList[i].getId();
                     tripNum++;
                     startingPoint = flightList[i].getDestination();
                     multipleDestinationOut(flightIds, finalDestination, startingPoint, tripNum);
                     tripNum--;
                  }
               }
            }
         }
      }
   }
/*
Parameter(s): Customer
Return value: void
Purpose: To display option for reserving a flight
*/
   public void reserveFlight(Customer c) {
   //variable
      final int MULTIPLE = 1;
      final int ROUND = 2;
      final int SINGLE = 3;
      int typeOfFlight;
      boolean valid = false;
   
   //loop until valid input is entered
      while(!valid) {
      //display flight options
         System.out.println("\nFlight Options: ");
         System.out.print("1. Multiple Destination\n2. Round Trip\n3. Single trip\n\nPlease enter an option: ");
         typeOfFlight = sc.nextInt();
         sc.nextLine();
      
      //if type of flight is single, call reserveSingle
         if (typeOfFlight == SINGLE) {
            valid = true;
            reserveSingle((Customer)currentUser);
         }
         //if type of flight is round, call reserveRound
         else if (typeOfFlight == ROUND) {
            valid = true;
            reserveRound((Customer)currentUser);
         } 
         //if type of flight is multiple, call reserveMultipleDest
         else if (typeOfFlight == MULTIPLE) {
            valid = true;
            reserveMultipleDest((Customer)currentUser);
         }
         //Otherwise, for not valid input, output error message
         else {
            System.out.println("Invalid option.");
         }
      }
   }

/*
Parameter(s): Customer c
Return value: void
Purpose: calls methods to find flight by input id, book tickets, and find final price, for single trip
*/
   private void reserveSingle(Customer currentUser) {
   //variable
   //findCorrectFlight() prompts uer for flight ID and gets corresponding flight
      Flight currentFlight = findCorrectFlight(); 
      int numTickets = 0;
      double totalCost = 0;
      String[] seatNum;
   
   //Prompt user for the number of ticket that user wants to purchase
      System.out.print("How many tickets would you like to purchase? ");
      numTickets = sc.nextInt();
      sc.nextLine();
   
   //construct seatNum array
      seatNum = new String[numTickets];
   
   //For given number of tickets, reserver seats
      for (int i = 0; i < numTickets; i++) {
         seatNum[i] = reserveSeat(currentFlight);
      }
   
   //calculate the final cost
      totalCost = findFinalCost(currentUser, calculateCost(currentFlight, numTickets));
   
   //reserve flight
      for (int i = 0; i < numTickets; i++) {
         currentUser.reserveFlight(currentFlight, seatNum[i], totalCost/numTickets);
      }
   
   //update point
      currentUser.addPoints((int)(totalCost*(POINT_PERCENT/100)));
   
   //print successful message
      System.out.println("Your flight has been reserved.");
   }

/*
   Parameter(s): Customer c
   Return value: void
   Purpose: calls methods to find flight by input id, book tickets, and find final price, for round trip
*/    
   private void reserveRound(Customer currentUser) {
   //variable
      double cost;
      double finalCost = 0;
      double[] totalCost = null;
      int numTickets = 0;
      Flight[] list = new Flight[2];
      String[][] seatNum = null;
      boolean validInput = false;
      boolean valid = false;
   
   //Ask user for the flightId and find corresponding flight object
      for (int i = 0; i < list.length; i++) {
         System.out.print("\nFlight #" + (i+1));
         list[i] = findCorrectFlight();
      }
      
   //if there is no possible round trip, ask user for another flight ID
      while (!(checkIfRound(list[0], list[1]))) {
         System.out.println("Not a valid round trip flight. Please try again!");
      //Ask user for the flightId and find corresponding flight object
         for (int i = 0; i < list.length; i++) {
            System.out.println("Flight #" + (i+1));
            list[i] = findCorrectFlight();
         }
      }
   
      while(!validInput) {
      //reset info       
         valid = false;
         while(!valid) {   
         //Prompt user for the number of ticket that user wants to purchase
            System.out.print("How many tickets would you like to purchase? ");
            try {
               numTickets = sc.nextInt();
               valid = true;
            } 
            catch(InputMismatchException imex) {
               System.out.println("Invalid input. Please try again.");
            }
         //flush
            sc.nextLine();
         }
      //when customer wants 0 number of ticket, outputs message and exit
         if (numTickets == 0) {
            validInput = true;
            System.out.println("No flight has been reserved.");
         } 
         else if (numTickets < 0) {
            System.out.println("Invalid number input. Please try again.");
         } 
         else {
            validInput = true;
         //construct array
            seatNum = new String[list.length][numTickets];
            totalCost = new double[list.length];
         
         //reserve seat 
            for (int i = 0; i < list.length; i++) {
               for (int j = 0; j < numTickets; j++) {
                  seatNum[i][j] = reserveSeat(list[i]);
               }
            }
         }
      
      //calculate total cost 
         for (int i = 0; i < list.length; i++) {
            cost = calculateCost(list[i], numTickets); 
            totalCost[i] = findFinalCost(currentUser, cost);
            finalCost = finalCost + totalCost[i];
         }
      
      //reserve flight
         for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < numTickets; j++) {
               currentUser.reserveFlight(list[i], seatNum[i][j], totalCost[i]/numTickets);
            }
         }
      
      //update point
         currentUser.addPoints((int)((finalCost) * (POINT_PERCENT/100)));
      
      //print successful message
         System.out.println("Your flights have been reserved.");
      }
   }

/*
	Parameter(s): Customer c
   Return value: void
   Purpose: calls methods to find flight by input id, book tickets, and find final price, for multiple destination trip
*/
   private void reserveMultipleDest (Customer currentUser) {
   //variable
      Flight[] flights = null;
      int numTickets = 0;
      double cost = 0;
      double finalCost = 0;
      double[] totalCost = null;
      String[][] seatNum = null;
      boolean validInput = false;
      boolean valid = false;
   
   //Promt user for the number of flight
      System.out.print("\nEnter the number of flights: ");
      int numFlights = sc.nextInt();
   //flush
      sc.nextLine();
   
   //Construc array of flights
      flights = new Flight[numFlights];
   
   //Prompt user for the flight ID
      for (int i = 0; i < numFlights; i++) {
         System.out.println("Flight #" + (i+1));
         flights[i] = findCorrectFlight();
      }
   
   //if there is no possible round trip, ask user for another flight ID
      while (checkIfMultiple(flights) == false) {
         System.out.println("This is not a valid multiple destination flight routes Please try again.");
      //Prompt user for the flight ID
         for (int i = 0; i < numFlights; i++) {
            System.out.println("Flight #" + (i+1));
            flights[i] = findCorrectFlight();
         }
      }
   
      while(!validInput) {
      //reset info       
         valid = false;
         while(!valid) {   
         //Prompt user for the number of ticket that user wants to purchase
            System.out.print("How many tickets would you like to purchase? ");
            try {
               numTickets = sc.nextInt();
               valid = true;
            } 
            catch(InputMismatchException imex) {
               System.out.println("Invalid number is entered! Please try again.");
            }
         //flush
            sc.nextLine();
         }
      
      //when customer wants 0 number of ticket, outputs message and exit
         if (numTickets == 0) {
            validInput = true;
            System.out.println("No flight has been reserved!");
         } 
         else if (numTickets < 0) {
            System.out.println("Invalid number is entered! Please try again.");
         } 
         else {
            validInput = true;
         //construct arrays
            seatNum = new String[numFlights][numTickets];
            totalCost = new double[numFlights];
         
         //reserve seat for each flight and each tickets
            for (int i = 0; i < numFlights; i++) {
               for (int j = 0; j < numTickets; j++) {
                  seatNum[i][j] = reserveSeat(flights[i]);
               }    
            }
         
         //calculate total cost for each flights
            for (int i = 0; i < numFlights; i++) {
               cost = calculateCost(flights[i], numTickets); 
               totalCost[i] = findFinalCost(currentUser, cost);
               finalCost = finalCost + totalCost[i];
            }
         
         //reserve flight
            for (int i = 0; i < numFlights; i++) {
               for (int j = 0; j < numTickets; j++) {
                  currentUser.reserveFlight(flights[i], seatNum[i][j], totalCost[i]/numTickets);
               }
            }
         
         //update point
            currentUser.addPoints((int)((finalCost) * (POINT_PERCENT/100)));
         
         //print successful message
            System.out.println("Your flights have been reserved.");   
         }
      }
   }


/*
Parameter(s): list of flight
Return value: boolean of whether it is a valid multiple destination trip
Purpose: To identify if the list of flight is a multiple destination trip
*/
   private boolean checkIfMultiple(Flight[] list) {
   //variable
      boolean valid = true;
   
   //sort the list by date
      list = sortByDate(list);
   
   //loop through the list and exit right away if one of them is not valid
      for (int i = 0; i < list.length - 1 && valid; i++) {
      //if the destination of i does not equal origin of i+1 it is no longer valid
         if (!((list[i].getDestination()).equals(list[i+1].getOrigin()))) {
            valid = false;
         }
      }
      return valid;
   }

/*
Parameter(s): N/A
Return value: N/A
Purpose: List all possible round trips available
*/
   private void listAllRoundTrip() {
   //variable
      int count = 0;
   //sort the flightList by the date from earliest to latest
   
   //check all possibility
      System.out.println("Possible round trips");
      for (int i = 0; i < numFlight; i++) {
         for (int j = i+1; j < numFlight; j++) { 
            if(checkIfRound(flightList[i], flightList[j])){
               count++;
               System.out.println(count+".");
               flightList[i].displayFlight();
               System.out.println();
               flightList[j].displayFlight();
               System.out.println("\n");
            }
         
         }
      }
   
   //IF no result found
      if (count == 0) {
         System.out.println("There is no possible round trips available right now.");
      }
   }

  /*
	Parameter(s): Flight f
   Return value: boolean
   Purpose: checks if two flights are round trip. flight1 is the departing trip, and flight2 is the returning trip
*/ 	
   private boolean checkIfRound(Flight flight1, Flight flight2){
      if(flight1.getOrigin().equals(flight2.getDestination()) && flight1.getDestination().equals(flight2.getOrigin())){
         if(flight1.compareToDepArr(flight2) < 0){
            return true;
         }
      }
      return false;
   }


/*
Parameter(s): N/A
Return value: N/A
Purpose: To list all of the possible multiple destination trips available
*/
   private void listAllMultipleTrip() {
   //variable
      int numStop = 0;
      String from = "";
      String to = "";
      boolean valid = false;
   
   //check for valid input
      while(!valid) {
      //Prompt user for the number of stops
         System.out.print("\nEnter the number of stops you want: ");
         try {
            numStop = sc.nextInt();
            valid = true;
            sc.nextLine();
         } 
         catch(InputMismatchException imex){
            System.out.println("Invalid input. Please try again.");
         }
         //prompt user for starting point and their final destination
         System.out.print("\nEnter your first departure (city, country): ");
         from = sc.nextLine();
         System.out.print("\nEnter your final destination (city, country): ");
         to = sc.nextLine();
      }
   //print all possibilty
      System.out.println("Here are possible trips with " + numStop + " stops");
      
      String [] list = new String [numStop];
      
      multipleDestinationOut(list, to, from, 0);
   
   }

/*
Parameter(s): N/A
Return value: Flight
Purpose: asks user for flight id and finds flight with the id
*/
   private Flight findCorrectFlight() { 
   //variable
      Flight currentFlight = null;
      String id = null;
      boolean notWant = true;
      boolean valid = false;
      String choice = "";
   
   //loop until customer enters Flight ID that customer wants
      while (notWant) {
      //Prompt user for the Flight ID 
         System.out.print("\nPlease enter the ID of the flight you would like to book: ");
         id = sc.nextLine();
      
      //loop until flightID is found
         while (currentFlight == null) {
            currentFlight = searchFlight(id);
         
         //If flightID is not found, it asks for flightId again
            if (currentFlight == null) {
               System.out.println("Flight does not exist.");
               System.out.print("Please enter the ID of the flight you would like to book: ");
               id = sc.nextLine();
            }
         }
      
      //check for valid input
         while(!valid) {
         //Prompt user if it is the flight they wanted
            currentFlight.displayFlight();
            System.out.print("Is this the flight you wanted (yes/no)? ");
            choice = sc.nextLine();
         
            if (choice.equals("yes")) {
               valid = true;
               notWant = false;
            } 
            else if (choice.equals("no")) {
               valid = true;
            } 
            else {
               System.out.println("Wrong input! Please try again");
            }
         }
      }  
      return currentFlight;
   }
/*
   Parameter(s): Admin object
   Return value: N/A
   Purpose: to add new flight to the flightList and flightList in airline
*/
   public void addFlight(Admin user) {
   //variable
      Flight flight = null;
   
   //call admin's addFlight
      flight = user.addFlight();
   
   //add into the flightList
      flightList[numFlight] = flight;
   
   //update number of flight
      numFlight++;
   }
   /*
   Parameter(s): Admin object
   Return value: N/A
   Purpose to remove Flight from the fligthList
*/
   public void removeFlight(Admin user) {
      //variable
      Flight flight;
      String id;
      boolean successful = false;
      
      //find and store flight that user wants to remove
      flight = user.removeFlight();
      
      //find in the flightList and remove it
      for (int i = 0; i < numFlight && !successful; i++) {
         if((flight.getId()).equals(flightList[i].getId())) {
            successful = true;
            //if the  flight exists, than remove that flight and shift all the other flights by one
            flightList[i] = null;
            while(i+1 < MAX_FLIGHT && flightList[i+1] != null){
               flightList[i] = flightList[i+1];
               flightList[i+1] = null;
               i++;
            }
            //update number of flight
            numFlight--;
         }
      } 
   }


/*
Parameter(s): Flight f
Return value: seat number
Purpose: reserves the seat of user¡¯s choice
*/
   private String reserveSeat(Flight currentFlight) {
   //variable
      String desiredSeat = null;
      String choice = "";
      boolean valid = false;
      boolean validSeatNum = false;
      boolean notWant = true;
      boolean reserveSeat = false;
   
   //display seats
      currentFlight.displaySeat();
   
   //loop until user enters the seat they want
      while(notWant || !reserveSeat) {
      //loop until valid seat number is entered
         while(!validSeatNum) {
         //Prompt user for the seat they like
            System.out.print("Which seat would you like? ");
            desiredSeat = sc.nextLine();
         
         //check for valid seat number
            validSeatNum = currentFlight.validSeatNum(desiredSeat);
         
         //if seat number is not valid, output error message
            if (!validSeatNum) {
               System.out.println("Wrong seat number! Please try again!");
            }
         }
      //loop through until valid input is entered
         while(!valid) {
            System.out.print("Are you sure you want to book this seat (yes/no)? ");
            choice = sc.nextLine();
            System.out.println();
         
         //check for valid input
            if(choice.equals("yes")) {
               notWant = false;
               valid = true;
            } 
            else if(choice.equals("no")) {
               valid = true;
            } 
            else {
               System.out.println("Invalid input! Please try again!");
            }
         }
      
         reserveSeat = currentFlight.reserveSeat(desiredSeat);
      //IF reserveSeat is not possible ask for another seat
         if (reserveSeat) {
            notWant = false;
            reserveSeat = true;
         }
      }
   
      return desiredSeat;   
   }
/*
   Parameter(s): Flight f, int nt
   Return value: double
   Purpose: calculate cost with number of tickets and flight
*/
   private double calculateCost(Flight f, int nt) {
      return f.getCost() * nt;
   }

/*
Parameter(s): Customer c, double tc
Return value: total cost
Purpose: calculate final cost with user option to use points
*/
   private double findFinalCost(Customer currentUser, double totalCost) {
   //variable
      String usePoints = "";
      int usingPoints = 0;
      boolean valid = false;
      boolean usePoint = false;
   
   //Display cost and points
      System.out.println("\nTotal cost is $" + totalCost);
      currentUser.checkPoints();
   
   //loop until valid input is entered
      while(!valid) {
         System.out.print("Would you like to use your points (yes/no): ");
         usePoints = sc.nextLine();
      
      //check for valid
         if(usePoints.equals("yes")) {
            valid = true;
         //loop until usePoint is valid
            while(!usePoint) {
            //prompt user for the amount
               System.out.print("How many points would you like to use: ");
               usingPoints = sc.nextInt();
            //use point
               usePoint = currentUser.usePoints(usingPoints);
            //if usePoint failed, prompt for amount of point again
               if (!usePoint) {
                  System.out.println("There is not enough point. Please try again!");
               }
            }
         //deduct total cost with the amount of points
            totalCost = totalCost - usingPoints;
         
         } 
         else if(usePoints.equals("no")) {
            valid = true;
         } 
         else {
            System.out.println("Wrong input! Please try again!");
         }
      }
   
   //prints total cost
      System.out.print("\nTotal cost is $");
      System.out.println(totalCost);
   
      return totalCost;
   }
}



