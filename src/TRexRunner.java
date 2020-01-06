import java.awt.event.KeyEvent;
import acm.graphics.*;
import acm.program.*;

/**
 * @author Malik Ibadov
 * 
 *         This class is the copy of Google Chrome's T-Rex Runner game.
 */
public class TRexRunner extends GraphicsProgram {
	private static final long serialVersionUID = 1L;
	// window size
	public static final int APPLICATION_WIDTH = 600;
	public static final int APPLICATION_HEIGHT = 250;

	@Override
	public void init() {

		initMethod();

		waitForClick();

	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			tRex.bendOver();
			break;
		case KeyEvent.VK_UP:
			tRex.jumpStart();
			break;
		case KeyEvent.VK_SPACE:
			tRex.jumpStart();
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			tRex.trexRun();
			break;

		}

	}

	@Override
	public void run() {

		threadStart();

		while (true) {

			if (gameOver()) {
				gameStop();
				// prevScore will be determined
				Counter.prevScore = counter.getScore() > Counter.prevScore ? counter.getScore() : Counter.prevScore;
				waitForClick();
				// all objects are removed
				removeAll();
				// all objects will be added again
				initMethod();
				// HiScore will be written
				counter.getHiScore();
				// game will be started
				threadStart();
			}

			// at specific point, birdThread will be added after second cactus
			if (counter.getScore() == 300)
				add(bird, cactus.getCanvasPoint(Cactus.c2.getLocation()).getX() + 200, 110);

		}

	}

	/**
	 * This method will stop all threads.
	 */
	@SuppressWarnings("deprecation")
	public void gameStop() {
		birdThread.stop();
		cactusThread.stop();
		roadThread.stop();
		cloudThread.stop();
		counterThread.stop();
		tRex.gameOver();
		bird.gameOver();
		counter.getHiScore();
		finish();
	}

	/**
	 * All objects that are needed to play the game are created in this method.
	 * For the sake of using all objects easily while replaying game. All of
	 * them are stored in one method
	 */
	public void initMethod() {
		// objects
		counter = new Counter();
		cloud = new Cloud();
		way = new Road();
		tRex = new TRex();
		cactus = new Cactus();
		bird = new Bird();
		// threads
		roadThread = new Thread(way);
		cactusThread = new Thread(cactus);
		birdThread = new Thread(bird);
		cloudThread = new Thread(cloud);
		counterThread = new Thread(counter);
		// adding all of them specific points
		add(counter, getWidth() - counter.getWidth() - 20, 20);
		add(cloud, 600, 0);
		add(way, 0, 200 - way.getHeight());
		add(cactus, 680, 200 - cactus.getHeight());
		add(tRex, 10, 198 - tRex.getHeight());
		add(counter, APPLICATION_WIDTH - counter.getWidth(), 0);
		// adding KeyListeners
		addKeyListeners();
	}

	/**
	 * This method start all the Threads which are stored in
	 * <code>initMethod()</code>.
	 */
	public void threadStart() {
		tRex.jumpStart();
		roadThread.start();
		cactusThread.start();
		cloudThread.start();
		counterThread.start();
	}

	/**
	 * This method prints the "Game Over" sign and replay button on the screen
	 * when game is over.
	 */
	public void finish() {
		// since this two objects are used together, they are in the one
		// compound
		fin = new GCompound();
		GImage over = new GImage("gameOver.png");
		GImage replay = new GImage("replayButton.png");
		over.sendToFront();
		replay.sendToFront();
		fin.add(replay, getWidth() / 2 - replay.getWidth() / 2, 150 - replay.getHeight());
		fin.add(over, getWidth() / 2 - over.getWidth() / 2, 100 - over.getHeight());
		add(fin);

	}

	/**
	 * This method checks whether game is over or not.
	 * 
	 * @return <code>true</code> if the game is over, otherwise
	 *         <code>false</code>
	 */
	private boolean gameOver() {
		// datas that are used to check the collision of TRex with cactus
		GPoint cPoint = cactus.getCanvasPoint(Cactus.c1.getLocation());
		double cactusX = cPoint.getX();
		double cactusY = cPoint.getY();
		double cactusW = Cactus.c1.getWidth();
		double cactusH = Cactus.c1.getHeight();
		// datas that are used to check the collision of TRex with bird
		GPoint bPoint = bird.getCanvasPoint(bird.bird.getLocation());
		double birdX = bPoint.getX();
		double birdY = bPoint.getY();
		double birdW = bird.bird.getWidth();
		double birdH = bird.bird.getHeight();
		/*
		 * This calculations checks whether TRex is in the bounding box of the
		 * obstacles or not Some numbers are added and subtracted because
		 * objects are the Image. In order to somehow ignore the transparent
		 * parts of the objects, these numbers are added to the calculations
		 */
		if (tRex.getX() + 10 < cactusX + cactusW && tRex.getX() + tRex.getWidth() - 15 > cactusX
				&& tRex.getY() + 10 < cactusY + cactusH && tRex.getHeight() + tRex.getY() - 15 > cactusY) {
			return true;
		} else if (tRex.getX() + 10 < birdX + birdW && tRex.getX() + tRex.getWidth() - 15 > birdX
				&& tRex.getY() + 10 < birdY + birdH && tRex.getHeight() + tRex.getY() - 15 > birdY) {
			return true;
		}
		return false;

	}

	public static void main(String[] args) {

		new TRexRunner().start();

	}

	// instance and class variables
	private TRex tRex;
	private Road way;
	private Cloud cloud;
	private Counter counter;
	private GCompound fin;
	// since positions of the cactus and bird can be overlapped, they are
	// declared as a public static variables in order to use them while adding
	// obstacles in the Threads.
	public static Cactus cactus;
	public static Bird bird;
	public static Thread birdThread;
	private Thread roadThread;
	private Thread cactusThread;
	private Thread cloudThread;
	private Thread counterThread;

}
