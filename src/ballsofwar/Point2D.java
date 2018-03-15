package ballsofwar;

import java.awt.Image;

public class Point2D
{
    protected double x;
    protected double y;
    
    public double getX() {
    	return x;
    }
    
    public double getY() {
    	return y;
    }
    
    public Point2D() {
    }
    
    public Point2D(double x2, double y2) {
    	this.x = x2;
    	this.y = y2;
    }
    
    public static Point2D center(int x, int y, int width, int height) {
    	return new Point2D(x - width / 2, y - height / 2);
    }
    
    public static Point2D centerImage(Image i, int x, int y) {
    	return center(x, y, i.getWidth(null), i.getHeight(null));
    }
    
    public boolean equals(Point2D p) {
    	return ((int) this.x == (int) p.x && (int) this.y == (int) p.y);
    }
    
    public String toString() {
    	return (int) this.x + ", " + (int) this.y;
    }
    
    public double distanceTo(Point2D p) {
    	return Math.sqrt(((this.x - p.x) * (this.x - p.x)) + ((this.y - p.y) * (this.y - p.y)));
    }
    
    public double angleTo(Point2D p) {
    	return Vector.gridVector(p.x - this.x, p.y - this.y).getA();
    }
    
    public Vector vectorTo(Point2D p) {
    	return Vector.gridVector(p.x - this.x, p.y - this.y);
    }
    
    public Vector getVectorOf() {
    	return Vector.gridVector(this.x, this.y);
    }

	public Point2D displace(Vector displacement) {
		return new Point2D(this.x + displacement.getX(), this.y + displacement.getY());
	}
	
	public Point2D clone() {
		return new Point2D(this.x, this.y);
	}
}

