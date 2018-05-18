import java.util.ArrayList;
import java.util.List;

import org.opensourcephysics.display.Trail;

public class Particle extends Coordinate{
	double lastVx;
	double lastVy;

	Trail trl = new Trail();
	double Xa;
	double Ya;
	double Xv;
	double Yv;

	double yInit;
		
	boolean fixed;
	//x and y are part of the circle class
	double timeStep;

	double mass;
	
	List<Force> forces;
	public Particle(double mass, double timeStep, double xInit, double yInit) {
		super(xInit, yInit);
		this.pixRadius = 3;
		this.yInit = yInit;
		this.timeStep = timeStep;
		this.mass = mass;
		forces = new ArrayList<Force>();
	}
	public void updateAcc() {
		double YForce= 0;
		double XForce= 0;
		for(Force force:forces) {
			XForce += (force.getXForce(this));
			YForce += (force.getYForce(this));
		}

		this.Xa = XForce/this.mass;
		this.Ya = YForce/this.mass;


	}
	public void updateVelocity() {
		this.lastVy = this.Yv;
		this.lastVx = this.Xv;
		this.Yv += this.Ya*this.timeStep;	
		this.Xv += this.Xa*this.timeStep;	
	}
	public void updatePosition() {
		this.y += (this.Yv+this.lastVy)/2*this.timeStep;//uses rectangle rule
		this.x += (this.Xv+this.lastVx)/2*this.timeStep;//uses rectangle rule

	}	
	public void Step() {
		if(!fixed) {
			this.updateAcc();
			this.updateVelocity();
			this.updatePosition();
			this.updateTrail();
		}
	}

	public void updateTrail() {
		trl.addPoint(this.getX(), this.getY());
	}//please work im not quite sure how to do this
}
