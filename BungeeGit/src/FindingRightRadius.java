import org.opensourcephysics.controls.*;  //needed for the simulation
import org.opensourcephysics.display.Trail;
import org.opensourcephysics.frames.*;   //needed to use a DisplayFrame
//
/**
 * 
 * @author Stephen Eisner & Ethan Hellman
 * 
 * This is our simulation class which brings together all of the objects previously created.
 * This class is both capable of finding the proper spring constant to get the person to land
 * just above/on the water. It is also capable of modeling the motion of a bungee cord given a
 * certain spring constant, starting height, number of springs, and timestep. Because we used the
 * rectangle rule in our calculations of, changing the timestep to something small yields a better
 * simulation. Otherwise, what will be witnessed is the "oscillation" effect which gives a particle
 * more or less energy based on the size of the timestep and the direction of its acceleration, resulting
 * in the particle being either higher or lower than it should be. That is why a smaller timestep is
 * recommended when running our simulation
 *
 */
public class FindingRightRadius extends AbstractSimulation {
	public FindingRightRadius() {

	}
	int i = 0;

	//variable initialization
	double time;
	double xInit;
	double yInit;
	double timeStep;
	PlotFrame xyFrame = new PlotFrame("x", "y", "Trajectory");
	//	PlotFrame aFrame = new PlotFrame("x", "y", "Trajectory"); //this frame plots the acceleration of the person at any given time 
	Chord cord;
	int j = 0;

	//our doStep function which runs until told otherwise:
	protected void doStep() {
		for(int i = 0; i < 3000; i++)
			cord.update();
		// System.out.println(cord.Masses.get(25).Xa);
	}

	//our reset function 
	public void reset() {
		i=0;
		xyFrame.clearDrawables(); //clears everything from the frame so what we see is only the acting spring 
		//initializes all of the variable prompts 
		control.setValue("x", 0);
		control.setValue("y", 0);
		control.setValue("Time Step", .0000001);
		control.setValue("Number of Springs", 100);
		//		control.setValue("K", 1.23e7);
		control.setValue("Mass of bungee cord", .15);
		control.setValue("Length of bungee", 3);
		control.setValue("amplitude", .01);
		//		control.setValue("frequency", 314.159265359);
		control.setValue("tension", 100);
		control.setValue("radius multiplier", 1.30655847);
		control.setValue("k", 1e5);
		control.setValue("frequency", 2000);


		xyFrame.clearData();
		this.setDelayTime(1);
		//		this.stepsPerDisplay = 1000;
	}

	//parameters for our bungee cord 
	double numSprings;
	double length;
	double k;
	double mass;
	double massPerson;
	double x;
	double y;
	double amplitudeConstant;
	double frequency;
	double tension;
	double radiusMultiplier;

	//our initialize function
	public void initialize() {
		//sets all of the parameters equal to the inputs from the user 
		numSprings = control.getDouble("Number of Springs");
		length = control.getDouble("Length of bungee");
		tension  = control.getDouble("tension");
		mass = control.getDouble("Mass of bungee cord");
		timeStep = control.getDouble("Time Step");
		x =  control.getDouble("x");
		y = control.getDouble("y");
		amplitudeConstant = control.getDouble("amplitude");
		//		frequency = control.getDouble("frequency");
		k = control.getDouble("k");
		frequency = control.getDouble("frequency");


		radiusMultiplier = control.getDouble("radius multiplier");





		double radius = radiusMultiplier*length/Math.PI/2;
		xyFrame.setPreferredMinMaxY(y-1.3*radius, y+1.3*radius);
		xyFrame.setPreferredMinMaxX(x-1.3*radius, x+1.3*radius);





		//clears everything from the frame again 
		xyFrame.clearData();
		//initializes our bungee cord 
		cord = new Chord(radiusMultiplier, numSprings, length, k, mass, timeStep, xyFrame, x, y, amplitudeConstant, frequency);
	}

	public static void main(String[] args) {
		//Creating a new simulation 
		SimulationControl.createApp(new FindingRightRadius());

	}
}
