/*Anthony Ma
 * This project is used to create a spreadsheet and do simple calculations.
 * 3/29/2024
 */
package textExcel;

public class SpreadsheetLocation implements Location{//Defines a object that stores a cell reference
    int row;
    int col;
    
    public int getRow(){//Gets the row of the cell reference
    	return row;
    }

    public int getCol(){//Gets the column of the cell reference
        return col;
    }
    
    public SpreadsheetLocation(String reference){//Constructor, converts cell reference into it's column # and row #
    	int num = Integer.parseInt(reference.substring(1));
        reference = reference.toUpperCase();
        col = reference.charAt(0)-'A';
        row = num - 1;
    }

}
