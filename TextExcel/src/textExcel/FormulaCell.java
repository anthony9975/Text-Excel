/*Anthony Ma
 * This project is used to create a spreadsheet and do simple calculations.
 * 3/29/2024
 */
package textExcel;

import java.util.ArrayList;
import java.util.Arrays;

public class FormulaCell extends RealCell{//Defines a cell that can accept formulas and return the calculated value
	
	private Spreadsheet sheet;
	
	public FormulaCell(String number, Spreadsheet sheet) {//Constructor, removes the parenthesis before and after formula
		super(number.substring(2, number.length()-2));
		this.sheet = sheet;
	}
	
	public double getDoubleValue() {//performs the calculations inside the formula and returns it as a double value
		String[] temp = super.fullCellText().split(" ");
		
		if (temp[0].toUpperCase().equals("AVG") || temp[0].toUpperCase().equals("SUM")) {//Does calculations for sum and avg
			Location loc1 = new SpreadsheetLocation(temp[1].substring(0, temp[1].indexOf("-")));
			Location loc2 = new SpreadsheetLocation(temp[1].substring((temp[1].indexOf("-")+1), temp[1].length()));
			double sum = 0;
			int count = 0;
			for (int i = loc1.getRow(); i <= loc2.getRow(); i++) {
				for (int j = loc1.getCol(); j <= loc2.getCol(); j++) {
					if (sheet.getCell(j, i) instanceof RealCell) {
						sum += ((RealCell)sheet.getCell(j, i)).getDoubleValue();
						count++;
					}
				}
			}
			if (temp[0].toUpperCase().equals("AVG")) {
				Double output = sum / count;
				return output;
			}
			return sum;
		}
		
		ArrayList<String> formula = new ArrayList<>();
		
		for (String elem: temp) {//Convert array into array list, converts all cell references to double values
			if ( Character.isAlphabetic( elem.charAt(0) ) ) {
				Location loc = new SpreadsheetLocation(elem);
				if (sheet.getCell(loc) instanceof RealCell) {
					formula.add( ( (RealCell) sheet.getCell(loc) ).getDoubleValue() + "" );
				}
			}else
				formula.add(elem);
		}

		boolean addSub = false;//Do calculations
		for (int j = 0; j < 2; j++) {
			for (int i = 1; i < formula.size(); i+= 2) {
				boolean changed = false;
				Double num1 = Double.parseDouble(formula.get(i-1));
				Double num2 = Double.parseDouble(formula.get(i+1));
				if (formula.get(i).equals("*")) {
					num1 *= num2;
					changed = true;
				}if (formula.get(i).equals("/")) {
					num1 /= num2;
					changed = true;
				}if (formula.get(i).equals("+") && addSub) {
					num1 += num2;
					changed = true;
				}if (formula.get(i).equals("-") && addSub) {
					num1 -= num2;
					changed = true;
				}if (changed) {
					formula.set(i-1, num1+"");
					formula.remove(i);
					formula.remove(i);
					i -= 2;	
				}
			}
			addSub = true;
		}
		return Double.parseDouble(formula.get(0));
	}
	
	public String abbreviatedCellText() {//Returns the value of the cell and adds padding/truncating to make exactly length 10
		String output = getDoubleValue() + "          ";
		return output.substring(0,10);
	}
	public String fullCellText() {//Returns the full formula uncalculated
		return "( " + super.fullCellText() + " )";
	}
}
