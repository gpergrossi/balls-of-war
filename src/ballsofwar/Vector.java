package ballsofwar;

import java.awt.Graphics2D;

/* Vector - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */

public class Vector {
    private double x = 0.0;
    private double y = 0.0;
    private double a = 0.0;
    private double r = 0.0;
    public static final Vector nullVector = gridVector(0.0, 0.0);

    public Vector() {}
    
    public Vector(Point2D coords) {
		this.x = coords.x;
		this.setY(coords.y);
	}

	public static double angleToPositive(double a) {
    	double r = a % (2.0 * Math.PI);
    	if (r < 0.0) r += (2.0 * Math.PI);
    	return r;
    }
    
    public Vector angleToPositive() {
    	this.setA(Vector.angleToPositive(this.a));
    	return this;
    }
    
    public String toString() {
    	String xs = String.valueOf(x);
    	String ys = String.valueOf(y);
    	int len = 5;
    	if (xs.length() > len) xs = xs.substring(0, len);
    	if (ys.length() > len) ys = ys.substring(0, len);
    	return "<" + xs + "," + ys + ">";
    }
    
    public Vector perp() {
    	setX(x);
    	Vector v = radialVector(a + Math.PI/2.0, r);
    	return v;
    }
    
    public Vector reflect(Vector normal) {
    	Vector normalComponent = getComponent(normal);
    	Vector tangenitalComponent = getComponent(normal.perp());
    	return addGridVectors(radialVector((normalComponent.a + Math.PI),normalComponent.r),tangenitalComponent);
    }
    
    public Vector reflect(Vector normal, double bounceFactor) {
    	Vector normalComponent = getComponent(normal);
    	normalComponent.setR(normalComponent.r * bounceFactor);
    	Vector tangenitalComponent = getComponent(normal.perp());
    	return addGridVectors(radialVector((normalComponent.a + Math.PI),normalComponent.r),tangenitalComponent);
    }
    
    public static Vector addGridVectors(Vector v, Vector w) {
    	return gridVector(v.x + w.x, v.y + w.y);
    }
    
    public Vector copy() {
    	Vector v = new Vector();
    	v.x = x;
    	v.setY(y);
    	return v;
    }
    
    public Vector getComponent(Vector v) {
    	Vector vhat = unit(v);
    	return radialVector(v.a, dot(vhat));
    }
    
    public boolean colinear(Vector v) {
    	return false;
    }
    
    public static Vector radialVector(double a, double r) {
    	Vector v = new Vector();
    	v.a = a;
    	v.setR(r);
    	return v;
    }
    
    public static Vector directionless(double magnitude) {
		Vector v = new Vector();
		v.setR(magnitude);
		return v;
    }
    
    public void pointMe(Vector v) {
	    setA(v.a);
    }
    
    public static Vector gridVector(double x, double y) {
		Vector v = new Vector();
		v.x = x;
		v.setY(y);
		return v;
    }
    
    public double dot(Vector v) {
		return x * v.x + y * v.y;
    }
    
    public static Vector unit(Vector v) {
		Vector vhat = new Vector();
		vhat.x = v.x;
		vhat.setY(v.y);
		vhat.setR(1.0);
		return vhat;
    }
    
    public Vector setX(double x) {
		this.x = x;
		calcRadial();
		return this;
    }
    
    public Vector setY(double y) {
		this.y = y;
		calcRadial();
		return this;
    }
    
    private void calcRadial() {
		r = Math.sqrt(x * x + y * y);
		a = getAngle(x, y);
    }
    
    public static double getAngle(double x, double y) {
		double a = Math.atan2(y, x);
		return a;
    }
    
    public static double getAngle(int x, int y) {
		return getAngle((double) x, (double) y);
    }
    
    public static double getAngle(double[] xy) {
		return getAngle(xy[0], xy[1]);
    }
    
    public static double getAngle(int[] xy) {
		return getAngle((double) xy[0], (double) xy[1]);
    }
    
    public void rotate(double angle) {
		setA(a + angle);
    }
    
    public Vector setA(double a) {
		this.a = a;
		calcGrid();
		return this;
    }
    
    public Vector setR(double r) {
		this.r = r;
		calcGrid();
		return this;
    }
    
    private void calcGrid() {
		x = r * Math.cos(a);
		y = r * Math.sin(a);
    }
    
    public double getX() {
    	return x;
    }
    
    public double getY() {
    	return y;
    }
    
    public double getA() {
    	return a;
    }
    
    public double getR() {
    	return r;
    }
    
	public void draw(Graphics2D g, Point2D startPos) {
		if(this.r == 0) {
			g.drawOval((int) startPos.getX() - 2, (int) startPos.getY() - 2, 5, 5);
			return;
		}
		Point2D endPos = new Point2D(startPos.getX() + this.x, startPos.getY() + this.y * -7.0 / 8.0);
		g.drawLine((int) startPos.getX(), (int) startPos.getY(), (int) endPos.getX(), (int) endPos.getY());
	}

	public static double getDifference(double a1, double a2) {
		a1 = angleToPositive(a1);
		a2 = angleToPositive(a2);
		double ret = a2 - a1;
		if(ret > Math.PI) ret = 2*Math.PI - ret;
		return ret;
	}

	public Vector scale(double horizontalScale, double verticalScale) {
		return gridVector(this.x * horizontalScale, this.y * verticalScale);
	}

	public Vector shift(double xOffset, double yOffset) {
		return gridVector(this.x + xOffset, this.y + yOffset);
	}
}

