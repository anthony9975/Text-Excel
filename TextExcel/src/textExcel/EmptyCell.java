/*Anthony Ma
 * This project is used to create a spreadsheet and do simple calculations.
 * 3/29/2024
 */
package textExcel;

public class EmptyCell implements Cell {//Defines a cell with nothing in it
	
	public EmptyCell() {//Constuctor
		
	}
	
	public String abbreviatedCellText() { //text for spreadsheet cell display, must be exactly length 10
		return "          ";
	}
	
	public String fullCellText() { //text for individual cell inspection, not truncated or padded
		return "";
	}
}
