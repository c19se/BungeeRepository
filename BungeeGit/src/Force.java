////this class is simple, it holds the basics of a force
//public class Force {
//	double Newtons; // the magnitude of the force
//	double radians; // the direcet oc the force
//	public boolean top(Particle p) {//this exists so that we can ovveride it in the spring class. 
//		return false;
//	};
//}
////


public abstract class Force {
	abstract public double getXForce(Particle p);
	abstract public double getYForce(Particle p);
	private double Newtons;
	private double radians;
	public double getNewtons() {
		return Newtons;
	}
	public double getRadians() {
		return radians;
	}
	public void setNewtons(double newtons) {
		Newtons = newtons;
	}
	public void setRadians(double Radians) {
		radians = Radians;
	}
}