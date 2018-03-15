package ballsofwar;

import java.awt.Color;

public abstract class Interface {
	
	//static variables
	public static Point2D mousepos = new Point2D();
	public static int mouseclick = 0;
	
	//non-static variables
	boolean active = true;
	double 	x = 0, y = 0, left = 0, top = 0, right = 0, bottom = 0;
	String 	name = "";
	Color	textcolor = Color.white;
	Color	linecolor = Color.lightGray;
	Color	rectcolor = Color.gray;
	long 	inputdelay = 0;
	
	public void enable() {
		this.active = true;
	}
	
	public void disable() {
		this.active = false;
	}
	
	public abstract void draw();
	
	public abstract void run();
	
	public abstract void setTextColor(Color c);
	
	public abstract void setLineColor(Color c);
	
	public abstract void setRectColor(Color c);
	
	public abstract String toString();
	
	public static String round(double value, int decimals) {
		String ret = String.valueOf(value);
		int length = 0;
		if(decimals > 0) {
			length = String.valueOf((int) value).length()+1+decimals;
		} else {
			length = String.valueOf((int) value).length();
		}
		return ret.substring(0, length);
	}

	public static void registerClick(int click, Point2D mouse) {
		mouseclick = click;
		mousepos = mouse;
	}
	
}
