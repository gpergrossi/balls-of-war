package ballsofwar;

public class Circle extends Shape {
	Point2D center;
	double radius;
	
	public Circle() {}
	public Circle(double x, double y, double r) {
		this.center = new Point2D(x, y);
		this.radius = r;
	}
	public Circle(Point2D center, double r) {
		this.center = center;
		this.radius = r;
	}
	
	public boolean intersects(Shape s) {
		if(s.getClass() == LineSegment.class) return ((LineSegment) s).intersects(this);
		if(s.getClass() == Circle.class) return this.intersects((Circle) s);
		return false;
	}
	
	public boolean intersects(Circle c) {
		if(this.center.distanceTo(c.center) < this.radius + c.radius) return true;
		return false;
	}

}
