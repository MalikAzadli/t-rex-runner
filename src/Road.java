
import acm.graphics.*;

/**
 * 
 * @author Malik Ibadov
 * 
 *         This class creates a compound of the road.
 *
 */
public class Road extends GCompound implements Runnable {

	/**
	 * Constructor: add the same road picture to the compound
	 */
	public Road() {
		w1 = new GImage("road.png");
		w2 = new GImage("road.png");
		add(w1);
		add(w2, getWidth(), 0);
	}

	@Override
	public void run() {
		GImage temp;
		/*
		 * While running roadThread, two pictures of the road will change the
		 * place.
		 */
		while (true) {
			if (getX() == -w1.getWidth()) {
				remove(w1);
				w2.setLocation(0, 0);
				setLocation(0, 200 - getHeight());
				add(w1, w1.getWidth(), 0);
				temp = w1;
				w1 = w2;
				w2 = temp;
			}
			// move the compound with specific velocity and PAUSE_TIME
			move(-VELOCITY, 0);
			pause(PAUSE_TIME);
		}
	}

	private static final int VELOCITY = 1;
	public static double PAUSE_TIME = 4;
	private GImage w1, w2;
}
