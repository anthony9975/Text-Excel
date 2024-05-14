/*Anthony Ma
 * This project is used to create a spreadsheet and do simple calculations.
 * 3/29/2024
 */
package textExcel;

public abstract class RealCell implements Cell {//Defines a cell that accepts any type of number
	
	private String number;
	
	public RealCell (String number) {// Constructor
		this.number = number;
	}
	
	public abstract double getDoubleValue();// Will implement to return the value of the number as a double
	
	public String abbreviatedCellText() {// Returns the number as a string of length 10 to put in the spreadsheet
		String number = getDoubleValue()+"          ";
		return number.substring(0, 10);
	}
	
	public String fullCellText() {// Returns the full string for cell inspection without truncating or padding
		return number;
	}

}
