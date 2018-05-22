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
public class Simulation extends AbstractSimulation {
	public Simulation() {

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
	}

	//our reset function 
	public void reset() {
		i=0;
		xyFrame.clearDrawables(); //clears everything from the frame so what we see is only the acting spring 
		//initializes all of the variable prompts 
		control.setValue("x", 0);
		control.setValue("y", 0);
		control.setValue("Time Step", .000001);
		control.setValue("Number of Springs", 100);
//		control.setValue("K", 1.23e7);
		control.setValue("Mass of bungee cord", .15);
		control.setValue("Length of bungee", 1);
		control.setValue("amplitude", .001);
//		control.setValue("frequency", 314.159265359);
		control.setValue("tension", 100);
		control.setValue("shape", "circle");

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
	String shape;

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
		k = tension/(length/numSprings);
		frequency = Math.sqrt(tension*length/mass)/(2*length);
		shape = control.getString("shape");
		
		
		

		//changes the frame
//		xyFrame.setPreferredMinMaxY(-2*amplitudeConstant, 2*amplitudeConstant);
//		xyFrame.setPreferredMinMaxX(x, x+length);
		xyFrame.setPreferredMinMaxY(-1.5, 1.5);
		xyFrame.setPreferredMinMaxX(-1.5, 1.5);
		
		boolean checkIfAmplitudeIsConstant = true;
		if(checkIfAmplitudeIsConstant) {
			Trail topLine = new Trail();
			Trail bottomLine = new Trail();
			topLine.addPoint(x, y+amplitudeConstant);
			topLine.addPoint(x+length, y+amplitudeConstant);
			bottomLine.addPoint(x, y-amplitudeConstant);
			bottomLine.addPoint(x+length, y-amplitudeConstant);
			xyFrame.addDrawable(topLine);
			xyFrame.addDrawable(bottomLine);

		}

		//clears everything from the frame again 
		xyFrame.clearData();
		//initializes our bungee cord 
		cord = new Chord(shape, numSprings, length/numSprings, k, mass/numSprings, timeStep, xyFrame, x, y, amplitudeConstant, frequency);
	}

	public static void main(String[] args) {
		//Creating a new simulation 
		SimulationControl.createApp(new Simulation());

	}
}
