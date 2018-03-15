package ballsofwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Draw {
	
	public static Graphics g;
	
	public static void setColor(Color c) {
		g.setColor(c);
	}
	
	public static void setGraphics(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g = graphics;
	}

	public static void circle(int x, int y, int r) {
		g.drawOval(x-r, y-r, r*2, r*2);
	}
	
	public static void circle(double x, double y, double r) {
		g.drawOval((int)(x-r), (int)(y-r), (int)(r*2), (int)(r*2));
	}
	
	public static void dot(int x, int y) {
		g.drawLine(x, y, x, y);
	}
	
	public static void line(int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
	}
	
	public static void line(double x1, double y1, double x2, double y2) {
		g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
	}

	public static void text(String s, int x, int y) {
		g.drawString(s,x,y);
	}
	
	public static void text(String s, double x, double y) {
		g.drawString(s, (int) x, (int) y);
	}
	
	public static void centerText(String s, double x, double y) {
		int width = s.length()*7;
		int height = 13;
		g.drawString(s, (int) (x-width/2+1), (int) (y+height/2-1));
	}
	
	public static void box(double left, double top, double right, double bottom) {
		int width = (int)(right-left);
		int height = (int)(bottom-top);
		g.drawRect((int) left, (int) top, width, height);
	}
	
	public static void button(int left, int top, int right, int bottom, boolean value) {
		g.fill3DRect(left+1, top+1, right-left, bottom-top, value);
	}
	
	public static void button(double left, double top, double right, double bottom, boolean value) {
		g.fill3DRect((int) (left+1), (int) (top+1), (int) (right-left), (int) (bottom-top), value);
	}
	
}
