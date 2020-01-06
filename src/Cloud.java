import java.util.Random;

import acm.graphics.*;

/**
 * 
 * @author Malik Ibadov
 * 
 *         This class will create the compound of clouds.
 *
 */
public class Cloud extends GCompound implements Runnable {

	/**
	 * Constructor: adds 5 clouds to the specific points the Compound
	 */
	public Cloud() {
		cloud = new GImage[] { new GImage("cloud.png"), new GImage("cloud.png"), new GImage("cloud.png"),
				new GImage("cloud.png"), new GImage("cloud.png") };
		// adding cloud to the specific point in compound
		add(cloud[0], 0, 60);
		add(cloud[1], 120, 90);
		add(cloud[2], 240, 50);
		add(cloud[3], 360, 110);
		add(cloud[4], 480, 70);
	}

	@Override
	public void run() {
		Random rand = new Random();
		int i = 0;
		while (true) {
			// if the first cloud is out of window, it will be removed and added
			// to the new place which is after the last cloud
			if (getX() == -cloud[i].getX() - cloud[i].getWidth()) {
				remove(cloud[i]);
				add(cloud[i], cloud[i == 0 ? 4 : i - 1].getX() + 200, rand.nextInt(70) + 40);
				// if i is equal to the 4, i will be reset to 0 because after 4,
				// it will give an ArrayIndexOutOfBOndsException
				i = (i == 4) ? 0 : i + 1;
			}
			// move the compound with specific velocity and PAUSE_TIME
			move(-VELOCITY, 0);
			// PAUSE_TIME has a relation with Road.PAUSE_TIME in order to
			// increase the Cloud speed
			pause(Road.PAUSE_TIME * 1.5);
		}
	}

	// private variables
	private static final double VELOCITY = 0.5;
	private GImage[] cloud;
}
