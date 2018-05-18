import java.util.ArrayList;

import org.opensourcephysics.frames.PlotFrame;
//myhe he he
public class Chord {
	//another rounding function for the bungee class:
	public double round (double value, int precision) {
		int scale = (int) Math.pow(10, precision+3);
		return (double) Math.round(value * scale) / scale;
	}
	//initializing variables:
	double numSprings;
	double mass;
	double lengthPerSpring;
	double k;
	int uncoiledBits = 1;
	double startHeight;
	int precision;
	double time;
	double timeStep;
	//initializing the two arrayList which hold all of the springs - equal to the number 
	//specified by the user - and the same goes for the masses, but + 1 because we have
	//to include the mass which is considered the person himself
	ArrayList<Spring> Springs;
	ArrayList<Particle> Masses;
	boolean slack = true; //tests to see if the bungee cord that not fallen yet and has "slack"
	double amplitude;
	double frequency;
	//our constructor:
	public Chord(String shape, double numSprings, double length, double K, double springMass, double timeStep, PlotFrame pFrame, double x, double y, double amplitude, double frequency) {//mass is the mass of each individual spring. Not really tho, the particles next to each. Same ish wit da K

		if(shape.equals("String")){
			Double d = length;

			//finds the specified precision based on the user input, type: string
			String[] splitter = d.toString().split("\\.");	
			this.precision = splitter[1].length(); 
			this.timeStep = timeStep;
			this.time = 0;
			this.amplitude = amplitude;
			this.frequency = frequency;
			//setting all variables equal to specified parameters:
			this.startHeight = y;
			this.k = K/numSprings;
			this.numSprings = numSprings;
			this.mass = springMass * numSprings;
			this.lengthPerSpring = length;
			Masses = new ArrayList<Particle>();
			Springs = new ArrayList<Spring>();
			Masses.add(new Particle(springMass, timeStep, x, y)); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
			pFrame.addDrawable(Masses.get(0)); //adds the individual particle
			pFrame.setVisible(true);
			for(int i = 0; i < numSprings-1; i++) {
				Masses.add(new Particle(springMass, timeStep, x+(i+1)*(length), y)); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
				pFrame.addDrawable(Masses.get(i)); //adds the individual particle to the frame
				Spring s = new Spring((length), K, Masses.get(i),Masses.get(i+1)); //creates a new spring to go along with each particle
				this.Springs.add(s); //adds the spring to the arrayList of springs 
			}
			Masses.get(Masses.size()-1).fixed = true;
			pFrame.addDrawable(Masses.get(Masses.size()-1));
			continueMoving = true;
		}
		
		else if(shape.toLowerCase().equals("circle")){
			Double d = length*numSprings;
;			double radius = d/Math.PI/2;
			//finds the specified precision based on the user input, type: string
			String[] splitter = d.toString().split("\\.");	
			this.precision = splitter[1].length(); 
			this.timeStep = timeStep;
			this.time = 0;
			this.amplitude = amplitude;
			this.frequency = frequency;
			//setting all variables equal to specified parameters:
			this.startHeight = y;
			this.k = K/numSprings;
			this.numSprings = numSprings;
			this.mass = springMass * numSprings;
			this.lengthPerSpring = length;
			Masses = new ArrayList<Particle>();
			Springs = new ArrayList<Spring>();
			double theta = Math.PI/2;
			Masses.add(new Particle(springMass, timeStep, x+(radius*Math.cos(theta)), y + (radius*Math.sin(theta)) )); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
			pFrame.addDrawable(Masses.get(0)); //adds the individual particle
			
			pFrame.setVisible(true);
			for(int i = 0; i < numSprings-1; i++) {
				theta += 2*Math.PI/(numSprings-1);
				Masses.add(new Particle(springMass, timeStep, x+(radius*Math.cos(theta)), y + (radius*Math.sin(theta)))); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
				pFrame.addDrawable(Masses.get(i)); //adds the individual particle to the frame
				Spring s = new Spring((length), K, Masses.get(i),Masses.get(i+1)); //creates a new spring to go along with each particle
				this.Springs.add(s); //adds the spring to the arrayList of springs 
			}
			Spring s = new Spring((length), K, Masses.get(Masses.size()-1),Masses.get(0)); //creates a new spring to go along with each particle
			this.Springs.add(s);
			continueMoving = true;
		}

		
		
	}

	//first of three update functions - this one updates all of the springs in the arrayList of springs
	public void updateSprings() {
		//for each spring, it will simply call the update function in the spring class 
		for(int i = 0; i < Springs.size(); i ++) {
			Springs.get(i).update();
		}		
	}
	int count = 0;
	boolean continueMoving;
	//next update function updates all of the particles
	public void updateParticles() {
		//		Masses.get(0).setY(Math.sin(2 * Math.PI*frequency*time)*amplitude);
		if(continueMoving)
			Masses.get(0).setY(Masses.get(0).yInit + Math.sin(frequency*time)*amplitude);
		if(roundYurd(Masses.get(0).getY()) == 0) {
			count++;
			if(count >= 3) {
				continueMoving = false;
			}
		}

		//for each of the particles, it will call the update function in the particle class 
		for(int i = 1; i < Masses.size()-1; i++) {
			Masses.get(i).updateAcc();
		}
		for(int i = 1; i < Masses.size()-1; i++) {
			Masses.get(i).updateVelocity();
		}
		for(int i = 1; i < Masses.size()-1; i++) {
			Masses.get(i).updatePosition();
		}
	}
	public double roundYurd(double num) {

		if(Math.abs(Math.round(num) - num) < .000001) num = Math.round(num);
		return num;
	}

	//this is the master update function
	public void update(){
		this.time += this.timeStep;
		this.updateParticles(); //gets the new displacement of the particles first so that we can properly evaluate the new forces of the springs
		this.updateSprings();
	}
}



