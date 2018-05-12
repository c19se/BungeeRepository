
import org.opensourcephysics.display.Circle;

public class Coordinate extends Circle{
//comment to test if i can do git work outside of eclipse
	public Coordinate(double x, double y) {
		this.setXY(x, y);
	}
	public double xDistanceBetween(Coordinate c) {
		return (c.x - this.x);
	}
	public double yDistanceBetween(Coordinate c) {
		return (c.y - this.y);
	}
	public double distanceBetween(Coordinate c) {
		return (Math.sqrt(Math.pow(this.xDistanceBetween(c), 2) + Math.pow(this.yDistanceBetween(c), 2)));
	}
}
//////////
