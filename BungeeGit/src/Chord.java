import java.awt.Color;
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

	double time;
	double timeStep;
	//initializing the two arrayList which hold all of the springs - equal to the number 
	//specified by the user - and the same goes for the masses, but + 1 because we have
	//to include the mass which is considered the person himself
	ArrayList<Force> Forces;
	ArrayList<Particle> Masses;
	boolean slack = true; //tests to see if the bungee cord that not fallen yet and has "slack"
	double amplitude;
	double frequency;
	double tension;
	String shape;
	//our constructor:
	public Chord(String shape, double numSprings, double length, double K, double mass, double timeStep, PlotFrame pFrame, double x, double y, double amplitude, double frequency, double tension) {//mass is the mass of each individual spring. Not really tho, the particles next to each. Same ish wit da K
		this.shape = shape;
		this.tension = tension;
		this.timeStep = timeStep;
		this.time = 0;
		this.amplitude = amplitude;
		this.frequency = frequency;
		this.k = K;
		this.numSprings = numSprings;
		this.mass = mass;
		double springMass = mass/numSprings;
		this.lengthPerSpring = length/numSprings;
		Masses = new ArrayList<Particle>();
		Forces = new ArrayList<Force>();
		double dX = tension/k;

		if(shape.toLowerCase().equals("string")){
			//finds the specified precision based on the user input, type: string

			//setting all variables equal to specified parameters:

			Masses.add(new Particle(springMass, timeStep, x, y)); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
			pFrame.addDrawable(Masses.get(0)); //adds the individual particle
			pFrame.setVisible(true);
			for(int i = 0; i < numSprings; i++) {
				Masses.add(new Particle(springMass, timeStep, x+(i+1)*(lengthPerSpring), y)); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
				pFrame.addDrawable(Masses.get(i)); //adds the individual particle to the frame
				Spring s = new Spring(lengthPerSpring-dX, K, Masses.get(i),Masses.get(i+1)); //creates a new spring to go along with each particle
				this.Forces.add(s); //adds the spring to the arrayList of springs 
			}
			Masses.get(Masses.size()-1).fixed = true;
			pFrame.addDrawable(Masses.get(Masses.size()-1));
			continueMoving = true;
			moveAll = false;
		}
		else if(shape.toLowerCase().equals("cstring")){
			Masses.add(new Particle(springMass, timeStep, x, y)); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
			pFrame.addDrawable(Masses.get(0)); //adds the individual particle
			pFrame.setVisible(true);
			for(int i = 0; i < numSprings; i++) {
				Masses.add(new Particle(springMass, timeStep, x+(i+1)*(lengthPerSpring), y)); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
				pFrame.addDrawable(Masses.get(i)); //adds the individual particle to the frame
				Spring s = new Spring(lengthPerSpring-dX, K, Masses.get(i),Masses.get(i+1)); //creates a new spring to go along with each particle
				this.Forces.add(s); //adds the spring to the arrayList of springs 
			}
			Masses.get(Masses.size()-1).fixed = true;
			pFrame.addDrawable(Masses.get(Masses.size()-1));
			continueMoving = false;
			moveAll = false;
		}


		else if(shape.toLowerCase().equals("charged circle")){

			double radius = length/Math.PI/2;
			double theta = Math.PI/2;
			for(int i = 0; i < numSprings; i++) {
				System.out.println(theta);
				Masses.add(new Particle(springMass, timeStep, x + (radius*Math.cos(theta)), y + (radius*Math.sin(theta)))); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
				pFrame.addDrawable(Masses.get(i)); //adds the individual particle to the frame
				theta += 2*Math.PI/(numSprings);

			}
			pFrame.setVisible(true);

			Particle[] ps = new Particle[2];

			ps[0] = new Particle(0, timeStep, x, y);
			ps[0].charge = .0001;
			for(int i = 0; i < numSprings-1; i++) {
				ps[1] = Masses.get(i);
				ps[1].charge = .0001;
				//				System.out.println(Masses.get(i).distanceBetween(Masses.get(i+1)));
				Spring s = new Spring(lengthPerSpring-dX, K, Masses.get(i),Masses.get(i+1)); //creates a new spring to go along with each particle
				this.Forces.add(s); //adds the spring to the arrayList of springs 
				Forces.add(new ElectricalForce(ps.clone()));
			}

			ps[1]=Masses.get(Masses.size()-1);
			ps[1].charge = .0001;
			//			ps[1].charge = 00001;

			Forces.add(new ElectricalForce(ps.clone()));
			pFrame.addDrawable(Masses.get(Masses.size()-1));

			Masses.get(0).color = Color.GREEN;
			Masses.get(Masses.size()-1).color = Color.BLUE;

			Spring s = new Spring(lengthPerSpring-dX, K, Masses.get(Masses.size()-1), Masses.get(0)); //creates a new spring to go along with each particle
			this.Forces.add(s);

			moveAll= false;
		}
	}

	public Chord(double radiusMultiple, double numSprings, double length, double K, double mass, double timeStep, PlotFrame pFrame, double x, double y, double amplitude, double frequency) {//mass is the mass of each individual spring. Not really tho, the particles next to each. Same ish wit da K
		this.shape = "charged circle";

		double radius = radiusMultiple*length/Math.PI/2;
		//finds the specified precision based on the user input, type: string
		this.timeStep = timeStep;
		this.time = 0;
		this.amplitude = amplitude;
		this.frequency = frequency;
		this.k = K;
		this.numSprings = numSprings;
		this.mass = mass ;

		this.lengthPerSpring = length/numSprings;
		Masses = new ArrayList<Particle>();
		Forces = new ArrayList<Force>();
		double theta = Math.PI/2;


		double springMass = mass/numSprings;

		double dX = tension/k;

		pFrame.setVisible(true);
		for(int i = 0; i < numSprings; i++) {
			Masses.add(new Particle(springMass, timeStep, x + (radius*Math.cos(theta)), y + (radius*Math.sin(theta)))); //adds a new particle for the amount specified by the user, each particle is identical and varies only in position
			pFrame.addDrawable(Masses.get(i)); //adds the individual particle to the frame
			theta += 2*Math.PI/(numSprings);
		}

		Particle[] ps = new Particle[2];

		ps[0] = new Particle(0, timeStep, x, y);
		ps[0].charge = .00005;
		for(int i = 0; i < numSprings-1; i++) {
			ps[1] = Masses.get(i);
			ps[1].charge = .00005;
			Spring s = new Spring(lengthPerSpring-dX, K, Masses.get(i),Masses.get(i+1)); //creates a new spring to go along with each particle
			this.Forces.add(s); //adds the spring to the arrayList of springs 
			Forces.add(new ElectricalForce(ps.clone()));
		}

		ps[1]=Masses.get(Masses.size()-1);
		ps[1].charge = .00005;
		//		ps[1].charge = .00005;

		Forces.add(new ElectricalForce(ps.clone()));
		pFrame.addDrawable(Masses.get(Masses.size()-1));

		Masses.get(0).color = Color.GREEN;
		Masses.get(Masses.size()-1).color = Color.BLUE;

		Spring s = new Spring(lengthPerSpring - dX, K, Masses.get(Masses.size()-1), Masses.get(0)); //creates a new spring to go along with each particle
		this.Forces.add(s);
		moveAll= false;
		moveTopAsWave = true;
	}
	boolean moveTopAsWave = false;
	boolean moveAll;
	//first of three update functions - this one updates all of the springs in the arrayList of springs
	public void updateForces() {
		//for each spring, it will simply call the update function in the spring class 
		for(int i = 0; i < Forces.size(); i ++) {
			Forces.get(i).update();
		}		
	}
	int count = 0;
	boolean continueMoving;
	//next update function updates all of the particles

	public double roundYurd(double num) {

		if(Math.abs(Math.round(num) - num) < .00001) num = Math.round(num);
		return num;
	}
	boolean hitOne = false;
	boolean hitNegOne = false;

	public void updateParticles() {
		//		Masses.get(0).setY(Math.sin(2 * Math.PI*frequency*time)*amplitude);
		if(Math.abs(Masses.get(0).getY() -  amplitude) < .00000001 ) {
			hitOne = true;
			//			System.out.println("hit one");
		}else if(Math.abs(Masses.get(0).getY() + amplitude )< .00000001) {
			hitNegOne = true;
			//			System.out.println("hit negative one");
		}

		if(shape.toLowerCase().equals("circle")||(shape.toLowerCase().equals("string")&&continueMoving) || moveTopAsWave || shape.toLowerCase().equals("cstring"))
//			Masses.get(0).setY(Masses.get(0).yInit + Math.sin(frequency*time)*amplitude + Math.sin(.515*frequency*time)*amplitude);
//			Masses.get(0).setY(Masses.get(0).yInit + Math.sin(frequency*time)*amplitude + Math.sin(1015/2000*frequency*time)*amplitude);
			Masses.get(0).setY(Masses.get(0).yInit + Math.sin(frequency*time)*amplitude);

		if(continueMoving && roundYurd(Masses.get(0).getY()) == 0 && hitOne && hitNegOne) {

			continueMoving = false;

		}

		if(moveAll) {
			Masses.get(0).updateAcc();
		}
		for(int i = 1; i < Masses.size(); i++) {
			//if(i!=49)
			if(!Masses.get(i).fixed)
				Masses.get(i).updateAcc();
		}
		if(moveAll) {
			Masses.get(0).updateVelocity();
		}
		for(int i = 1; i < Masses.size(); i++) {
			//if(i!=49)
			if(!Masses.get(i).fixed)

				Masses.get(i).updateVelocity();
		}	
		if(moveAll) {
			Masses.get(0).updatePosition();
		}
		for(int i = 1; i < Masses.size(); i++) {
			//if(i!=49)
			if(!Masses.get(i).fixed)

				Masses.get(i).updatePosition();
		}
	}
	//	public double roundYurd(double num) {
	//
	//		if(Math.abs(Math.round(num) - num) < .000001) num = Math.round(num);
	//		return num;
	//	}

	//this is the master update function
	public void update(){
		this.time += this.timeStep;
		this.updateForces();
		this.updateParticles(); //gets the new displacement of the particles first so that we can properly evaluate the new forces of the springs
	}
}



