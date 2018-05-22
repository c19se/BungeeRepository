
//this is our spring class 
public class Spring extends Force {
	Particle[] particles= new Particle[2]; //creates an array of particles, holds only the two directly above and below the spring 
	//vairable initialization:
	double length;
	double K;
	double initialDistancex;
	double initialDistancey;
	
	//constructor:
	//leaves room for the two particles above and below the spring 
	public Spring(double length, double K, Particle... particles) {
		//adds the particles to the particle array in the spring class 
		for(int i = 0; i < particles.length; i++) {
			this.particles[i] = particles[i];
			this.particles[i].forces.add(this);
		}
		this.length = length;
		this.K = K;
		
		initialDistancex = this.particles[0].xDistanceBetween(this.particles[1]);
		initialDistancey = this.particles[0].yDistanceBetween(this.particles[1]);
	}

	//the update function 
	public void update() {
		//gets the displacement between the two springs:
		double displacement = this.particles[0].distanceBetween(this.particles[1]);
		//finds the current force of the spring based on the displacement evaluated above
		//the Particles which are connected to this spring will be able to access these new
		//values because this exact spring is stored in their arrayList of forces acting on said
		//particle so there is no need to update
		this.setNewtons(this.K*(displacement- this.length));
		//finds the angle between the particles
		//again no need to update the this information direction to the particles
	}


	@Override
	public double getXForce(Particle p) {
		if(p.equals(this.particles[1])) {
			return (this.getNewtons()*((p.xDistanceBetween(this.particles[0]))/p.distanceBetween(this.particles[0])));
//			return (this.getNewtons()*((p.xDistanceBetween(this.particles[0]))));
		}
		else if(p.equals(this.particles[0])){
			return (this.getNewtons()*((p.xDistanceBetween(this.particles[1]))/p.distanceBetween(this.particles[1])));
//			return (this.getNewtons()*((p.xDistanceBetween(this.particles[1]))));
		}
		else return 0;
	}

	@Override
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
//
