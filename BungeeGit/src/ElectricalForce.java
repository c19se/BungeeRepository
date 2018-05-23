

public class ElectricalForce extends ActionAtDistance{
	public ElectricalForce(Particle[] particles) {
		this.particles = particles;
		for(int i = 0; i < particles.length; i++) {
			this.particles[i].forces.add(this);
		}
		this.constant = 8.9875e9;
	}

	public void update() {//add if statement about the forces for the ish about the ya know how it be with the pos pos neg neg. 
		double displacement = this.particles[0].distanceBetween(this.particles[1]);
		this.setNewtons(this.constant * particles[0].charge * particles[1].charge/Math.pow(displacement, 2));
	}
	
	public double getXForce(Particle p) {
		double xForce = super.getXForce(p);
		if(this.particles[0].charge*this.particles[1].charge > 0) {
			xForce = -xForce;
		}
		return xForce;
	}
	public double getYForce(Particle p) {
		double yForce = super.getYForce(p);
		if(this.particles[0].charge*this.particles[1].charge > 0) {
			yForce = -yForce;
		}
		return yForce;
	}

}
