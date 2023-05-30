/*
	Class Name: User
	Author: Claire Shen, Elsie Oh, Junhee Jung, Thomson Ly
	Purpose: To keep track of the information of the current user and perform different functions
*/

//IMPORT FILES
import java.util.*;

abstract class User {
	Scanner sc = new Scanner(System.in);
	
	//FIELDS
	protected String username;
	protected String email;
	protected String password;

   //CONSTRUCTOR
	/* 
		Parameter(s): separate String for the username, email, and password
		Return value: User Object
		Purpuose: to construct a User object
	*/
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
   //ACCESSORS
   /*
      Parameter(s): N/A
      Return value: String of the user's username
      purpose: allow controlled access to the private field username
   */
   public String getUsername() {
      return this.username;
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the user's email
      Purpose: allow controlled access to the private field email
   */
   public String getEmail() {
      return this.email;
   }
   
   /*
      Parameter(s): N/A
      Return value: String of the user's password
      Purpose: allow controlled access to the private field password
   */
   public String getPassword() {
      return this.password;
   }
   
   
   //MUTATORS
   /*
      Parameter(s): String of the user's new username
      Return value: void
      Purpose: changes private field username
   */
   public void setUsername(String newUsername) {
      this.username = newUsername;
   }
   
   /*
      Parameter(s): String of the user's new amail
      Return value: void
      Purpose: changes private field email
   */
   public void setEmail(String newEmail) {
      this.email = newEmail;
   }
   
   /*
      Parameter(s): String of the user's new password
      Return value: void
      Purpose: changes private field password
   */
   public void setPassword(String newPassword) {
      this.password = newPassword;
   }
   
   
   //METHODS
	/*
		Parameter(s): String of another username to be compared
		Return value: boolean true if this user's username matches the username of the parameter
		Purpose: to find if the user's username matches the username given
	*/
	public boolean checkUsername(String username) {
		//if user's username matches the parameter, return true
		if ((this.username).equals(username)){
			return true;
		} 
		//if user's username doesn't match the parameter, return false
		else {
			return false;
		}
	}
	
	/*
		Parameter(s): String of another password to be compared
		Return value: boolean true if this user's password matches the password given
		Purpose: to find if the user's password matches the password given
	*/
	public boolean checkPassword(String password) {
		//if te user's password matches the parameter, return true
		if ((this.password).equals(password)) {
			return true;
		}
		//if the user's password does not match the parameter, return false
		else {
			return false;
		}
	}
	
	/*
		Parameter(s): String of another email to be compared
		Return value: boolean true if this user's email matches the email given
		Purpose: to find if the user's email matches the email given
	*/
	public boolean checkEmail(String email) {
		//if the user's email matches the parameter, return true
		if ((this.email).equals(email)) {
			return true;
		}
		//if the user's email does not match the parameter, return false
		else {
			return false;
		}
	}
	
	/*
		Parameter(s): N/A 
		Return value: void
		Purpose: to list the options for updating information and perform different updating fuctions accordingly
	*/
	public void updateInfo() {		
		//variable
		int choice;
		
		//prints the menu for user
		System.out.println("\nMenu for update information");
		System.out.println("1. Change my password");
		System.out.println("2. Change my username");
		
		//prompts user for the choice
		System.out.print("Choose an option: ");
		choice = sc.nextInt();
		
		//if the user wants to change their password
		if (choice == 1) {
			updatePassword();
		}
		//if the user wants to change their username
		else if(choice == 2) {
			updateUsername();
		}
	}
	
	/*
		Parameter(s): N/A 
		Return value: void
		Purpose: to update the password of the user 
	*/
	private void updatePassword() {		
		//variable
		String triedPassword, newPassword, confirmPassword;
		boolean successful = false;
		
		//prompts user to enter the current password
		System.out.print("\nType in your current password: ");
		triedPassword = sc.nextLine();
		
		//loops until the password is succesfully changed
		while (!successful) {
			//if triedPassword matches current user's password
			if (checkPassword(triedPassword)) {
				//prompts user to enter new password and confirm it
				System.out.print("Type in the new password: ");
				newPassword = sc.nextLine();
				System.out.print("Confirm your new password: ");
				confirmPassword = sc.nextLine();
				
				//if confirmPassword matches newPassword 
				if (confirmPassword.equals(newPassword)) {
					System.out.println("Password successfully changed.");
					//set user's password with newPassword
               password = newPassword;
               successful = true;
				}
				//if confirmPassword does not match newPassword
				else {
					System.out.println("The password did not match. Please try again.\n");
				}
			}
			//if it doesnt match, prompt user to enter the password again
			else {
				System.out.println("Password did not match!");
				System.out.print("Type in your current password again: ");
				triedPassword = sc.nextLine();
			}
		} 
	}
	
	/*
		Parameter(s): N/A
		Return value: void
		Purpose: to update the username of the user
	*/
	private void updateUsername() {
		//variable
		String triedPassword, newUsername, confirmUsername;
		boolean successful = false;
		
		//prompt user to enter the password
		System.out.print("Type in your password: ");
		triedPassword = sc.nextLine();
		
		//loop until updateUsername is successful
		while (!successful) {
			//if triedPassword matches user's password
			if (checkPassword(triedPassword)) {
				//prompt user to type in newUsername and confirmUsername
			   System.out.print("Type in the new Username: ");
            newUsername = sc.nextLine();
            System.out.print("Confirm your new Username: ");
            confirmUsername = sc.nextLine();
            
            //if the confirmUsername matches newUsername
            if (newUsername.equals(confirmUsername)) {
               System.out.println("Username succesffuly changed.");
               //set user's username with newUsername
               username = newUsername;
               successful = true;
            }
            //if the confirmUsername does not match newUsername
            else {
               System.out.println("The ID did not match. Please try again");
            }
         }
			//if triedPassword does not match user's password
			else { 
				System.out.println("You have entered wrong password.");
				System.out.print("Please try again: ");
				triedPassword = sc.nextLine();
			}
		}
	}
   
   /*
      Parameter(s): N/A
      Return value: String that contains information of the current user
      Purpose: to obtain a concise string that contains user's information for displaying
   */
   public String toString() {
      //creates new variable
      String s;
      
      //stores user's username, email, and password
      s = username + "\n" + email + "\n" + password + "\n";
      
      //returns a string containing all information
      return s;
   }
}