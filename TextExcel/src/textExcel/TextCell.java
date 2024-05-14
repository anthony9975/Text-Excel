package textExcel;

public class TextCell implements Cell{
	
	private String text;
	
	public TextCell (String text) {
		this.text = text;
	}
	
	public String abbreviatedCellText() {
		String temp = text.substring(1, text.length()-1);
		temp += "          ";
		return temp.substring(0, 10);
	}
	public String fullCellText() {
		return text;
	}
}
