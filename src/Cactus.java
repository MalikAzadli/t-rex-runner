import java.util.Random;

import acm.graphics.*;

/**
 * 
 * @author Malik Ibadov
 * 
 *         This class creates the compound of cactuses
 *
 */
public class Cactus extends GCompound implements Runnable {

	private String[] names = { "C1.png", "cac1.png", "C2.png", "cac2.png", "cac3.png", "cactusGroup.png" };
	private Random rand = new Random();

	/**
	 * Constructor: adds two cactuses to the random points of the compound
	 */
	public Cactus() {
		// to hold the height of the compound fixed, one invisible rectangle
		// added to the compound.
		GRect handmade = new GRect(0, 0, 0, 200);
		handmade.setVisible(false);
		add(handmade);
		c1 = new GImage(names[rand.nextInt(CACTUS_INDEX)]);
		c2 = new GImage(names[rand.nextInt(CACTUS_INDEX)]);
		add(c1, 30, getHeight() - c1.getHeight());
		add(c2, 400, getHeight() - c2.getHeight());

	}

	@Override
	public void run() {
		GImage temp;
		while (true) {
			// if the first cactus is out of the window, it will be removed and
			// new cactus will be assigned to it.
			if (getX() == -c1.getX() - c1.getWidth()) {
				remove(c1);
				c1 = new GImage(names[rand.nextInt(CACTUS_INDEX)]);
				// this new cactus will be added to the place after second
				// cactus randomly.
				add(c1, c2.getX() + rand.nextInt(100) + 500, getHeight() - c1.getHeight());
				// and names of this cactuses will be swapped to check whether
				// next cactus is out of window or not
				temp = c1;
				c1 = c2;
				c2 = temp;
			}

			// move the compound with specific velocity and PAUSE_TIME
			move(-VELOCITY, 0);
			pause(Road.PAUSE_TIME);
		}
	}

	// variables
	private static final double VELOCITY = 1;
	public static int CACTUS_INDEX = 5;
	public static GImage c1, c2;

}
