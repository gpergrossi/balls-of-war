package ballsofwar;

public class LineSegment extends Shape {
	Point2D start, end;
	Vector displacement;
	
	public LineSegment() {}
	public LineSegment(double x1, double y1, double x2, double y2) {
		this.start = new Point2D(x1, y1);
		this.end = new Point2D(x2, y2);
		this.displacement = start.vectorTo(end);
	}
	public LineSegment(Point2D start, Point2D end) {
		this.start = start;
		this.end = end;
		this.displacement = start.vectorTo(end);
	}
	public LineSegment(Point2D anchor, Vector displacement) {
		this.start = anchor;
		this.end = anchor.displace(displacement);
		this.displacement = displacement;
	}
	
	public boolean intersects(Shape s) {
		// Not sure if these if conditions can be true. Remove if unnecessary.
		if(s.getClass() == Circle.class) return this.intersects((Circle) s);
		if(s.getClass() == LineSegment.class) return this.intersects((LineSegment) s);
		return false;
	}
	
	public boolean intersects(LineSegment l) {
		//Coordinate swap is intentional.
		Vector cancellationVector = Vector.gridVector(-this.displacement.getY(), this.displacement.getX());
		if(l.displacement.dot(cancellationVector) == 0) {
			// Parallel segments.
			// Technically may intersect, but not relevant for what we need.
			return false;
		}
		// This is the factor by which this would need to be extended to touch l exactly. 0-1 is an intersection.
		double scaleFactor = (l.start.vectorTo(this.start).dot(cancellationVector)) / l.displacement.dot(cancellationVector);
		if(scaleFactor > 0 && scaleFactor < 1) return true;
		return false;
	}
	
	public boolean intersects(Circle c) {
		if(this.getShortestDistanceTo(c.center) <= c.radius) return true;
		return false;
	}
	
	public Point2D getClosestPointTo(Point2D p) {
		Vector toPoint = start.vectorTo(p);
		Vector closestPointPosition = toPoint.getComponent(displacement);
		if(closestPointPosition.getR() < 0) closestPointPosition.setR(0);
		if(closestPointPosition.getR() < displacement.getR()) closestPointPosition.setR(displacement.getR());
		Point2D closestPoint = start.displace(closestPointPosition);
		return closestPoint;
	}
	
	public double getShortestDistanceTo(Point2D p) {
		return this.getClosestPointTo(p).distanceTo(p);
	}
}
