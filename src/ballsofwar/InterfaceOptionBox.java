package ballsofwar;

import java.awt.Color;

public class InterfaceOptionBox extends Interface {

	int value, max, min;
	InterfaceButton[] button = new InterfaceButton[] {null, null};
	InterfaceText title = null;
	String[] options = {""};
	
	public InterfaceOptionBox(String name, String[] options, int x, int y) {
		//construction
		this.name = name;
		this.value = 1;
		this.max = options.length;
		this.min = 1;
		this.options = options;
		this.x = x;
		this.y = y;
		//calculate dimensions
		int width = 0;
		for(int c = 0; c < options.length; c++) {
			if(options[c].length() > width) width = options[c].length();
		}
		width = width*7+6;
		int height = 13;
		this.left = x-width/2+1;
		this.top = y-height/2;
		this.right = this.left + width;
		this.bottom = this.top + height - 1;
		InterfaceGroup.suppressCreations();
		this.button = new InterfaceButton[] {new InterfaceButton(name, "<", (int)(left-7), y).setHeight(height), new InterfaceButton(name, ">", (int)(right+6), y).setHeight(height)};
		this.title = new InterfaceText(this.name, (int) this.x, (int) this.y-13);
		InterfaceGroup.activateCreations();
		InterfaceGroup.reportInterfaceCreation(this);
	}
	
	public void draw() {
		Draw.setColor(this.linecolor);
		Draw.box(left, top, right, bottom);
		Draw.setColor(this.textcolor);
		Draw.centerText(options[this.value-1], x, y-1);
		this.button[0].draw();
		this.button[1].draw();
		this.title.draw();
	}

	public void run() {
		this.button[0].run();
		this.button[1].run();
		if(button[0].getValue()) {
			this.value -= 1;
			if(this.value < this.min) this.value = this.min;
		}
		if(button[1].getValue()) {
			this.value += 1;
			if(this.value > this.max) this.value = this.max;
		}
	}

	public void setValue(int i) {
		this.value = i;		
	}
	
	public String toString() {
		return "ValueBox("+String.valueOf((int)x)+","+String.valueOf((int)y)+","+options[this.value-1]+")";
	}

	public void setLineColor(Color c) {
		this.linecolor = c;
		this.button[0].setLineColor(c);
		this.button[1].setLineColor(c);
		this.title.setLineColor(c);
	}

	public void setRectColor(Color c) {
		this.rectcolor = c;
		this.button[0].setRectColor(c);
		this.button[1].setRectColor(c);
		this.title.setRectColor(c);
	}

	public void setTextColor(Color c) {
		this.textcolor = c;
		this.button[0].setTextColor(c);
		this.button[1].setTextColor(c);
		this.title.setTextColor(c);
	}
	
}
