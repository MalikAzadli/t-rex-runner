import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import acm.graphics.*;
import acm.util.SwingTimer;

/**
 * 
 * @author Malik Ibadov
 * 
 *         This class creates the TRex which is GImage and simulates the bend
 *         over, run, and jump actions.
 *
 */
public class TRex extends GImage {
	// private variables
	private SwingTimer runTimer, bendTimer, jumpTimer, gameOverTimer;
	private int ttick = 0;
	private int jtick = 1;

	/**
	 * Constructor: creates the picture of the TRex
	 */
	public TRex() {
		super("t-rex stopped.png");
		// timer to simulate run
		/*
		 * Link for SwingTimer:
		 * https://docs.oracle.com/javase/tutorial/uiswing/misc/timer.html
		 */
		runTimer = new SwingTimer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ttick % 2 == 0)
					setImage("run2.png");
				else
					setImage("run1.png");
				ttick++;
			}
		});
		// timer to simulate bend over
		bendTimer = new SwingTimer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (ttick % 2 == 0)
					setImage("bendOver2.png");
				else
					setImage("bendOver1.png");
				ttick++;
			}
		});
		// timer to simulate jump
		jumpTimer = new SwingTimer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jump();
				jtick++;

			}
		});
		// when game is over, this timer will be started
		gameOverTimer = new SwingTimer(100, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * Before starting bend over action all other timers will be stopped and
	 * then bendTimer will be started
	 */
	public void bendOver() {
		// if the game is over, bend over action can not be started
		if (!gameOverTimer.isRunning()) {
			runTimer.stop();
			jumpStop();
			setImage("bendOver1.png");
			setLocation(getX(), 198 - getHeight());
			bendTimer.start();

		}
	}

	/**
	 * Before TRex start run action , bendTimer will be stopped and then
	 * runTimer will be started
	 */
	public void trexRun() {
		// if the game is over, run action can not be started
		if (!gameOverTimer.isRunning()) {
			bendTimer.stop();
			setImage("run1.png");
			setLocation(getX(), 198 - getHeight());
			runTimer.start();
		}
	}

	/**
	 * If the game is over, all actions will be stopped
	 */
	public void gameOver() {
		runTimer.stop();
		jumpStop();
		if (bendTimer.isRunning()) {
			// specific image for crash of TRex with any obstacle
			setImage("crash.png");
			// position while bendOver
			setLocation(getX(), 198 - getHeight());
		} else {
			// specific image for crash of TRex with any obstacle
			setImage("crash.png");
		}
		bendTimer.stop();
		gameOverTimer.start();
	}

	/**
	 * This method simulates the jump action with the help of gravity and
	 * initial speed.
	 */
	public void jump() {
		double vIn = 1;
		double grav = 0.005;
		double y = 152 - vIn * jtick + 0.5 * grav * jtick * jtick;
		// after some point, TRex will start falling when y is less than zero
		setLocation(getX(), y);
		if (y == 152) {
			jumpTimer.stop();
			bendTimer.start();
			trexRun();
			jtick = 1;
		}
	}

	/**
	 * If JumpTimer is not running and game is not over, run process will be
	 * stopped and jumpTimer will be started
	 */
	public void jumpStart() {
		if (!jumpTimer.isRunning() && !gameOverTimer.isRunning()) {
			runTimer.stop();
			// specific image for jump
			setImage("jump.png");
			jumpTimer.start();
		}
	}

	/**
	 * Jump process will be stopped and tick will be reset to 1
	 */
	public void jumpStop() {
		jumpTimer.stop();
		jtick = 1;
	}

}
