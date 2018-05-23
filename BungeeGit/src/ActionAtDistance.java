

public abstract class ActionAtDistance extends Force{

	Particle[] particles = new Particle[2];
	 double constant;

	public void update() {
		double displacement = this.particles[0].distanceBetween(this.particles[1]);
		this.setNewtons(this.constant * particles[0].mass * particles[1].mass/Math.pow(displacement, 2));
	}
	public double getXForce(Particle p) {
		if(p.equals(this.particles[1])) {
			return (this.getNewtons()*(p.xDistanceBetween(this.particles[0])/p.distanceBetween(this.particles[0])));
		}
		else if(p.equals(this.particles[0])){
			return (this.getNewtons()*(p.xDistanceBetween(this.particles[1])/p.distanceBetween(this.particles[1])));
		}
		else return 0;
	}
	public double getYForce(Particle p) {
		if(p.equals(this.particles[1])) {
			return (this.getNewtons()*(p.yDistanceBetween(this.particles[0])/p.distanceBetween(this.particles[0])));
		}
		else if(p.equals(this.particles[0])){
			return (this.getNewtons()*(p.yDistanceBetween(this.particles[1])/p.distanceBetween(this.particles[1])));
		}
		else return 0;
	}

}
