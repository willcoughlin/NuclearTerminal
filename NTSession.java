/*
 * NTSession.java
 * Copyright (c) 2016 Will Coughlin
 *
 * See "LICENSE.txt" for full details.
 */

import java.io.IOException;
import java.util.Scanner;

/**
 * Manages an individual hacking session.
 *
 * @author Will Coughlin
 * @version 06/15/2016
 */
public class NTSession {

	private NTPasswords passwdObj;
	private boolean hasUnlimited;

	private int tries = 5;

	private String[] tried = new String[5];

	/**
	 * Constructor: Creates new instance of a hacking session. 
	 *
	 * @param passwords Passwords object to use for session
	 * @param unlimited true or false for unlimited or limited tries
	 */
	public NTSession(NTPasswords passwords, boolean unlimited) {
		this.passwdObj = passwords;
		this.hasUnlimited = unlimited;
	}

	/**
	 * To be called from application. Controls display while session
	 * is in progress.
	 */
	public void display() {
		Scanner input = new Scanner(System.in);
		
		while (this.tries > 0) {
			clear();
			prompt();

			guess(input.nextLine());
		}

		System.out.println("\nTERMINAL LOCKED");
	}

	/** Clears screen with the method approptiate to user's operating system. */
	private void clear() {
		String os = System.getProperty("os.name");

		if (os.contains("Windows")) {
			try {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} catch (IOException e) {
				System.out.println(e);
				System.exit(-1);
			} catch (InterruptedException e) {
				System.out.println(e);
				System.exit(-1);
			}
		} else {
			try {
				Runtime.getRuntime().exec("clear");
			} catch (IOException e) {
				System.out.println(e);
				System.exit(-1);
			}
		}
	}

	/** Main prompt. */
	private void prompt() {
		System.out.println("NUKETERM V.1.0\nENTER PASSWORD NOW\n");
		System.out.println(passwdObj.toString().toUpperCase());

		compareGuesses();
		System.out.println("Tries Remaining: " + (this.hasUnlimited ? "UNLIMITED" : this.tries));

		System.out.print("\n> ");
	}

	/** 
	* Compare guess to actual password.
	*
	* @param guess Password guess (case insensitive)
	*/
	private void guess(String guess) {
		if (guess.equalsIgnoreCase(this.passwdObj.getCorrectPass())) {
			System.out.println("\nACCESS GRANTED");
			System.exit(0);
		} else {
			this.tried[5 - this.tries] = guess;
			this.tries = hasUnlimited ? 5 : this.tries - 1;
		}
	}

	/**
	* Generates list of previous guesses and how they compare 
	* to the actual password.
	*/
	private void compareGuesses() {
		String actual = passwdObj.getCorrectPass();

		for (String guess : tried) {
			int correct = 0;

			if (guess != null) {
				for (int i = 0; i < actual.length(); i++) {
					try {
						correct = (actual.charAt(i) == guess.charAt(i)) ? correct + 1 : correct;
					} catch (StringIndexOutOfBoundsException e) {
						// silently catch this
					}
				}

				System.out.println(guess.toUpperCase() + "(" + correct + "/" 
					+ actual.length() + " CORRECT)");
			}
		}
	}
}