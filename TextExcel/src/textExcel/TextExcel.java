/*Anthony Ma
 * This project is used to create a spreadsheet and do simple calculations.
 * 3/29/2024
 */
package textExcel;

import java.io.FileNotFoundException;
import java.util.Scanner;

// Update this file with your own code.

public class TextExcel{//Client Code

	public static void main(String[] args){//Main method
		Spreadsheet excel = new Spreadsheet();
		System.out.println("Welcome to Text Excel, the best Excel sheet that I can code currently.");
	    Scanner scan = new Scanner(System.in);
	    System.out.print("Command(Type \"Quit\" to quit): ");
	    String input = scan.nextLine();
	    while (!input.toUpperCase().equals("QUIT")) {
	    	System.out.println(excel.processCommand(input));
	    	System.out.print("Command(Type \"Quit\" to quit): ");
	    	input = scan.nextLine();
	    }
	    System.out.println("ByeBye");
	}
}
