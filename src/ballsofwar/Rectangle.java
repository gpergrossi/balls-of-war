package ballsofwar;

public class Rectangle extends Shape {

	Point2D anchor, plusheight, pluswidth, plusboth;
	Vector width, height;
	
	public Rectangle() {}
	public Rectangle(Point2D anchor, double width, double height) {
		this.anchor = anchor;
		this.width = Vector.gridVector(width, 0);
		this.height = Vector.gridVector(0, height);
		this.setupCorners();
	}
	
	private void setupCorners() {
		this.plusheight = anchor.displace(height);
		this.pluswidth = anchor.displace(width);
		this.plusboth = plusheight.displace(width);
	}
	
	public LineSegment[] getEdgeArray() {
		return new LineSegment[] {new LineSegment(anchor, plusheight), new LineSegment(plusheight, plusboth),
								  new LineSegment(plusboth, pluswidth), new LineSegment(pluswidth, anchor)};
	}
	
	@Override
	public boolean intersects(Shape s) {
		LineSegment[] edges = this.getEdgeArray();
		for(int x = 0; x < edges.length; x++) {
			if(edges[x].intersects(s)) return true;
		}
		return false;
	}

}
