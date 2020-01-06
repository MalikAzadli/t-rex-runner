import acm.graphics.*;

/**
 * 
 * @author Malik Ibadov
 * 
 *         This class creates and runs a counter which is used to compute the
 *         score of the player while playing game.
 *
 */
public class Counter extends GCompound implements Runnable {

	/**
	 * Constructor: creates counter which is only consist of 0
	 */
	public Counter() {

		scoreStart = printScore(0, 0, 0);
		add(scoreStart);
	}

	@Override
	public void run() {
		// while starting thread of counter, previous result which is "00000" is
		// removed.
		remove(scoreStart);
		score = 1;

		// every time score is incremented, printed, added, and removed to the
		// screen
		while (true) {
			scoree = printScore(score, 0, 0);

			add(scoree);

			pause(100);
			score++;
			/*
			 * for every score which can be divided by 10 and less than or equal
			 * to 1000, speed of the game is increased. Speed of all objects
			 * have the relation with the Road.PAUSE_TIME; therefore, changing
			 * one of them is enough to speed up the game
			 */
			if (score % 10 == 0 && score <= 1000) {
				Road.PAUSE_TIME = (PAUSE_TIME -= 0.021);
				// if the score is greater than 200, group of cactus is allowed
				// to be added to the screen
				Cactus.CACTUS_INDEX = score == 200 ? 6 : 5;
			}

			// if the score is equal to the 300, birdThread is started to
			// increase the difficulty level of the game
			if (score == 300) {
				TRexRunner.birdThread.start();
			}
			remove(scoree);
		}

	}

	/**
	 * This method creates a GCompound which shows the current score of the
	 * player.
	 * 
	 * @param s
	 *            - current score
	 * @param x
	 *            - where to add in x axis of the compound
	 * @param y
	 *            - where to add in y axis of the compound
	 * @return GCompound
	 */
	private GCompound printScore(int s, double x, double y) {
		GCompound com = new GCompound();
		String sc = s + "";
		int leng = sc.length();
		for (int i = 0; i < 5; i++) {
			// specific number of zeros are added to create a table with the
			// same size.
			if (i < 5 - leng) {
				GImage numbs = new GImage(0 + ".png");
				com.add(numbs, x, y);

			} else {
				// this for loop will add the specific numbers according to the
				// score that is passed to the method
				for (int j = 0; j < leng; j++) {
					char n = sc.charAt(j);
					GImage bal = new GImage(n + ".png");
					com.add(bal, x, y);
					x += 10;
				}
				break;
			}
			x += 10;

		}
		return com;
	}

	/**
	 * This method add the Hi Score to the window. and before printing it,
	 * oldest Hi Score is removed.
	 */
	public void getHiScore() {
		remove(hiScore);
		GImage hi = new GImage("hi.png");
		add(hi, -100, 0);
		if (score > prevScore) {
			hiScore = printScore(score, -70, 0);
			// if the current score is greater than previous one, it is added as
			// a hi score, and previous Score are changed
			prevScore = score;
		} else {
			hiScore = printScore(prevScore, -70, 0);
		}
		add(hiScore);
	}

	/**
	 * It is used to remove the score table and reset it.
	 */
	public void resetScore() {
		remove(scoree);
		score = 0;
		PAUSE_TIME = 4;
	}

	/**
	 * This method returns the current score
	 * 
	 * @return
	 */
	public int getScore() {
		return score;
	}

	// instance and class variables
	private int score;
	private GCompound hiScore = new GCompound();
	private GCompound scoree;
	private GCompound scoreStart;
	private double PAUSE_TIME = 4;
	public static int prevScore = 0;

}
