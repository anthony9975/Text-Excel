/*Anthony Ma
 * This project is used to create a spreadsheet and do simple calculations.
 * 3/29/2024
 */
package textExcel;

public class PercentCell extends RealCell{//Defines a cell that stores a percent
	
	public PercentCell(String number) {//Constructor
		super(number.substring(0, number.length()-1));
	}
	
	public String abbreviatedCellText() {//Returns text to be printed out in spreadsheet, exactly 10 spaces long
		String wholeNum;
		
		if (super.fullCellText().contains(".")) {
			wholeNum = super.fullCellText().substring(0, super.fullCellText().indexOf("."));
			String output = wholeNum + "%";
			output += "          ";
			return output.substring(0, 10);
		}
		
		String output = fullCellText() + "%         ";
		return output.substring(0, 10);
	}
	
	public String fullCellText() {//Returns the text for cell inspection, no truncating or padding
		double number = getDoubleValue()/100.0;
		return number+"";
	}
	
	public double getDoubleValue() {//Returns the value of the percentage as a double
		 return Double.parseDouble(super.fullCellText().substring(0, super.fullCellText().length()));
	}
}
