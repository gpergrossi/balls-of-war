package ballsofwar;

import java.awt.Color;

public class InterfaceText extends Interface {

	String text = "";
	
	public InterfaceText(String s, int x, int y) {
		this.text = s;
		this.x = x;
		this.y = y;
		InterfaceGroup.reportInterfaceCreation(this);
	}
	
	public void draw() {
		Draw.centerText(text, x, y);
	}

	public void run() {
		//nothing yet?
	}

	public String toString() {
		return "Text("+String.valueOf((int)x)+","+String.valueOf((int)y)+",\""+this.text+"\")";
	}
	
	public void setLineColor(Color c) {
		this.linecolor = c;
	}

	public void setRectColor(Color c) {
		this.rectcolor = c;
	}

	public void setTextColor(Color c) {
		this.textcolor = c;
	}
	
}
