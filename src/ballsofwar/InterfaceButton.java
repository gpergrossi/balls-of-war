package ballsofwar;

import java.awt.Color;

public class InterfaceButton extends Interface {

	public boolean value = false;
	public int mouseclick = 0;
	public String text;
	
	public InterfaceButton(String name, String text, int x, int y) {
		//construction
		this.name = name;
		this.text = text;
		this.x = x;
		this.y = y-1;
		this.value = false;
		this.mouseclick = 0;
		//calculate dimensions
		int width = text.length()*7+7;
		int height = 15;
		this.left = x-width/2;
		this.top = y-height/2-1;
		this.right = this.left + width;
		this.bottom = this.top + height;
		InterfaceGroup.reportInterfaceCreation(this);
	}
	
	public InterfaceButton setHeight(int height) {
		int width = (text.length()*7+3)+4;
		this.left = x-width/2;
		this.top = y-height/2;
		this.right = this.left + width;
		this.bottom = this.top + height;
		return this;
	}
	
	public void draw() {
		Draw.setColor(this.rectcolor);
		Draw.button(left, top, right, bottom, !value);
		Draw.setColor(this.textcolor);
		Draw.centerText(this.text, this.x, this.y);
	}

	public void run() {
		if(Interface.mouseclick > 0 && Main.mouseFocus == null) {
			if(Interface.mousepos.x >= left && Interface.mousepos.x <= right) {
				if(Interface.mousepos.y >= top && Interface.mousepos.y <= bottom) {
					this.value = true;
					this.mouseclick = Interface.mouseclick;
					Main.mouseFocus = this;
				}
			}
		}

		if(this.value) {
			this.inputdelay -= (Main.frameTime*this.mouseclick);
			if(Interface.mouseclick != this.mouseclick) {
				this.value = false;
				if(Main.mouseFocus == this) Main.mouseFocus = null;
			}
		} else {
			this.inputdelay = 0;
		}
	}

	public boolean getValue() {
		if(this.inputdelay <= 0 && this.value) {
			this.inputdelay = Main.oneSecond/5;
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "Button("+String.valueOf((int)x)+","+String.valueOf((int)y)+","+String.valueOf(this.value)+")";
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
