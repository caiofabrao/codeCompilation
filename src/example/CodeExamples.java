package example;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.time.Clock;
import java.time.Duration;
import java.util.Random;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This Class has a compilation of sample codes for various applications and uses
 *
 * @author Caio Fabao
 */
public class CodeExamples {

	/**
	 * This method returns the current time in milliseconds
	 */
	public void timeMillis() {
		System.out.print("Current Time in milliseconds = ");
		System.out.println(System.currentTimeMillis());
	}

	/**
	 * Method to manipulate clock object based on the system date/time
	 */
	public static void clockTest() {
		final Random rand = new Random();
		final Clock baseClock = Clock.systemDefaultZone();

		final Clock clock1 = Clock.offset(baseClock, Duration.ZERO);
		final Clock clock2 = Clock.offset(baseClock, Duration.ofMillis((long) Math.pow((-1), rand.nextInt(6)) * rand.nextInt(2500)));
		final Clock clock3 = Clock.offset(baseClock, Duration.ofSeconds((long) Math.pow((-1), rand.nextInt(6)) * rand.nextInt(2500)));

		clock1.equals(clock2);
		clock2.equals(clock3);

	}
	
	public static void periodicCall() {
        Timer timer = new Timer();
        PeriodicClass periodic = new PeriodicClass();
        timer.schedule(periodic, 0, 3000); // Repeat the task every 5 seconds.
	}

	public static void putSleep() {
		try {
			Thread.sleep(10000);
		} catch (final InterruptedException ex) {
			Logger.getLogger(CodeExamples.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
