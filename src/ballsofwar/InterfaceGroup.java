package ballsofwar;

import java.awt.Color;

public class InterfaceGroup extends Interface {

	public static Interface[] buffer = new Interface[] {};
	public static boolean recordOn = true;
	public static boolean suppressCreationReports = false;
	
	public Interface[] objects = new Interface[] {}; 
	
	public InterfaceGroup(String name, Interface[] objects) {
		this.name = name;
		this.objects = objects;
	}

	public void draw() {
		for(int c = 0; c < objects.length; c++) {
			if(objects[c] != null) objects[c].draw();
		}
		
	}

	public void run() {
		for(int c = 0; c < objects.length; c++) {
			if(objects[c] != null) objects[c].run();
		}
	}
	
	public static void beginGroupDeclaration() {
		buffer = new Interface[] {};
		recordOn = true;
	}
	
	public static void reportInterfaceCreation(Interface i) {
		if(recordOn && !suppressCreationReports) {
			//extend buffer by 1 position
			Interface[] update = new Interface[buffer.length+1];
			for(int c = 0; c < buffer.length; c++) {
				update[c] = buffer[c];
			}
			buffer = update;
			//fill new position
			buffer[buffer.length-1] = i;
		}
	}
	
	public static void endGroupDeclaration() {
		recordOn = false;
	}
	
	public static Interface[] getRecentGroup() {
		return buffer;
	}
	
	public String toString() {
		String list = "{";
		for(int c = 0; c < objects.length; c++) {
			if(objects[c] != null) {
				list += objects[c].toString();
			}
			if(c < objects.length-1) list += ", ";
		}
		list += "}";
		return "Group[] "+list;
	}
	
	public void setTextColor(Color color) {
		for(int c = 0; c < objects.length; c++) {
			if(objects[c] != null) {
				objects[c].setTextColor(color);
			}
		}
	}
	
	public void setLineColor(Color color) {
		for(int c = 0; c < objects.length; c++) {
			if(objects[c] != null) {
				objects[c].setLineColor(color);
			}
		}
	}
	
	public void setRectColor(Color color) {
		for(int c = 0; c < objects.length; c++) {
			if(objects[c] != null) {
				objects[c].setRectColor(color);
			}
		}
	}
	
	public static void suppressCreations() {
		suppressCreationReports = true;
	}
	
	public static void activateCreations() {
		suppressCreationReports = false;
	}
	
}
