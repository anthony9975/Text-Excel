/*Anthony Ma
 * This project is used to create a spreadsheet and do simple calculations.
 * 3/29/2024
 */
package textExcel;

public class ValueCell extends RealCell {//Defines a cell that stores a plain number
	
	public ValueCell (String number) {//Constructor
		super(number);
	}

	public double getDoubleValue() {//Returns number as a double
		return Double.parseDouble(super.fullCellText());
	}
}
