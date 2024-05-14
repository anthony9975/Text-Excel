/*Anthony Ma
 * This project is used to create a spreadsheet and do simple calculations.
 * 3/29/2024
 */
package textExcel;

import java.util.Arrays;

public class Spreadsheet implements Grid{//Defines a spreadsheet object that stores the cells and processes commands
	Cell[][] grid;
	
	
	public Spreadsheet() {//Constructor, creates a 2D array of cells
		grid = new Cell[20][12];
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 12; j++) {
				grid[i][j] = new EmptyCell();
			}
		}
	}
	
	
	public String processCommand(String command){//Reads input and does the actions: cell inspection, cell clearing, clearing grid, cell assignment
		String [] commands = command.split(" ", 3);
		String output;
		if (!command.contains("\"") && command.toUpperCase().contains("CLEAR")) {//need to clear something
			
			if (commands.length == 1) {//clear whole grid
				for (int i = 0; i < 20; i++) {
					for (int j = 0; j < 12; j++) {
						grid[i][j] = new EmptyCell();
					}
				}
				output = getGridText();
			
			} else if(commands.length == 2 && legalReference(commands[1])){//clear a cells
				Location loc = new SpreadsheetLocation(commands[1]);
				grid[loc.getRow()][loc.getCol()] = new EmptyCell();
				output = getGridText();
			
			} else//Command is invalid
				output = "ERROR: Invalid command.";
			
		}else {//Cell assignment/cell inspection
			if (commands[0].isEmpty())//Blank command
				return "ERROR: Invalid command.";
			
			if (commands.length == 1 && legalReference(commands[0])){//Cell inspection
				Location loc = new SpreadsheetLocation(commands[0]);
				output = grid[loc.getRow()][loc.getCol()].fullCellText();	
				
			} else if(legalReference(commands[0])){
				Location loc = new SpreadsheetLocation(commands[0].toUpperCase());
				if (commands[2].endsWith("\"")&&commands[2].startsWith("\"")) {//Text cell
					grid[loc.getRow()][loc.getCol()] = new TextCell(commands[2]);
					output = getGridText();
					
				}else if(commands[2].endsWith("%")){//Percent Cell
					grid[loc.getRow()][loc.getCol()] = new PercentCell(commands[2]);
					output = getGridText();
					
				}else if(commands[2].endsWith(")")&&commands[2].startsWith("(")){//Formula cell
					String test = commands[2].substring(2, commands[2].length()-2);
					String[] testArr = test.split(" ");
					int operCount = 0;
					boolean cont = true;
					for (String elem: testArr) {
						if (!isNumeric(elem) && !Character.isAlphabetic(elem.charAt(0)))
							operCount++;
						if (Character.isAlphabetic(elem.charAt(0)) && isNumeric(""+elem.charAt(1))) {
							if (elem.indexOf("-") != -1) {
								if (!legalReference(elem.substring(elem.indexOf("-")+1)))
									cont = false;
								if (!legalReference(elem.substring(0, elem.indexOf("-"))))
									cont = false;
							}else if (!legalReference(elem)) {
								cont = false;
							}
						} 	
					}
					if (cont && (testArr.length/2 == operCount || testArr[0].toUpperCase().equals("AVG") || testArr[0].toUpperCase().equals("SUM"))) {
						grid[loc.getRow()][loc.getCol()] = new FormulaCell(commands[2], this);
						output = getGridText();
					}else
						output = "ERROR: Invalid command.";
					
				}else{//Value cell
					if (isNumeric(commands[2])) {
						grid[loc.getRow()][loc.getCol()] = new ValueCell(commands[2]);
						output = getGridText();
					} else
						output = "ERROR: Invalid command.";
				}
				
			} else//Invalid command
				output = "ERROR: Invalid command.";
		}
		return output;
	}
	public int getRows(){//Returns number of rows in the spreadsheet
		return 20;
	}
	public int getCols(){//Returns number of columns in spreadsheet
		return 12;
	}
	public Cell getCell(Location loc){//Returns a cell at some location
		return grid[loc.getRow()][loc.getCol()];
	}
	public Cell getCell(int col, int row){//Returns a cell at some location
		return grid[row][col];
	}
	
	public String getGridText(){//Prints out the 2D array grid as a grid
		String output = "   |";
		for (char ch = 'A'; ch <= 'L'; ch++) {
			output += ch + "         |";
		}
		output += "\n";
		for (int i = 0; i < 20; i++) {
			if (i < 9)
				output += (i+1) + "  |";
			else
				output += (i+1) + " |";
			for (int j = 0; j < 12; j++) {
				if (grid[i][j] instanceof FormulaCell) {
					output += ((FormulaCell)grid[i][j]).abbreviatedCellText() + "|";
				} else
				output += grid[i][j].abbreviatedCellText() + "|";
			}
			output += "\n";
		}
		return output;
	}
	
	private boolean legalReference (String str) {//Tests if a string is a legal reference
		String upStr = str.toUpperCase();
		if (upStr.length()<2)
			return false;
		if (!Character.isAlphabetic(upStr.charAt(0)) || !Character.isDigit(upStr.charAt(1)))
			return false;
		for (int i = 1; i < upStr.length(); i++) {
			if (!Character.isDigit(upStr.charAt(i)))
				return false;
		}
		if (upStr.charAt(0) >= 'M' || Integer.parseInt(upStr.substring(1, upStr.length())) > 21 || Integer.parseInt(upStr.substring(1, upStr.length())) < 1)
			return false;
		return true;
	}

	private static boolean isNumeric (String str) {//Tests if a string is a number
		try {
			Double.parseDouble(str);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
}