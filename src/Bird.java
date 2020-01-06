import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import acm.graphics.*;
import acm.util.RandomGenerator;
import acm.util.SwingTimer;

/**
 * 
 * @author Malik Ibadov
 * 
 *         This class creates a compound of the birds and simulates the fly
 *         action of the bird.
 *
 */
public class Bird extends GCompound implements Runnable {
	// private instance variables
	private SwingTimer timer;
	private int ttick;
	private RandomGenerator rand = RandomGenerator.getInstance();

	/**
	 * Constructor: add the bird picture to the compound and creates a timer to
	 * simulate the flying
	 */
	public Bird() {
		ttick = 0;
		bird = new GImage("bird1.png");
		timer = new SwingTimer(200, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ttick % 2 == 0)
					bird.setImage("bird2.png");
				else
					bird.setImage("bird1.png");
				ttick++;
			}
		});
		add(bird, 0, 0);
	}

	/**
	 * When the game is over, fly effect will be stopped
	 */
	public void gameOver() {
		timer.stop();
	}

	@Override
	public void run() {
		// when the birdThread is started, fly effect will be started
		timer.start();
		// specific heights for the bird
		int[] birdHeight = { 110, 130, 160 };
		/*
		 * if the position of the compound is negative 100 bird will be
		 * invisible and added to the other position and will be visible again
		 */
		while (true) {
			if (getX() == -100) {
				setVisible(false);
				// bird will be randomly added
				if (rand.nextBoolean(0.8)) {
					pause(rand.nextInt(1500, 2000));
					continue;
				}
				setLocation(Cactus.c2.getX() + TRexRunner.cactus.getX() + 200 + rand.nextInt(56),
						birdHeight[rand.nextInt(3)]);
				setVisible(true);
			}
			// move the compound with specific velocity and PAUSE_TIME
			move(-VELOCITY, 0);
			// PAUSE_TIME will be taken from Road class because obstacles and
			// road move with the same speed
			pause(Road.PAUSE_TIME);
		}
	}

	public GImage bird;
	private static final int VELOCITY = 1;
}
