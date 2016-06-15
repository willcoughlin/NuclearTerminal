/*
 * Nuclear Terminal: A game based on the computer hacking minigame from 
 * 		Fallout 3 and Fallout: New Vegas.
 *
 * NTApp.java
 * Copyright (c) 2016 Will Coughlin
 *
 * "Fallout" and all related marks are copyright (c) Bethesda Softworks, LLC.
 *
 * See "LICENSE.txt" for full details.
 */

import java.io.FileNotFoundException;

/**
 * Command line application.
 *
 * @author Will Coughlin
 * @version 06/13/2016
 */
public class NTApp {
	
	/**
	* CLI app requires an integer value for difficulty and accepts an optional
	* flag to play the game with no guess limit.
	*
	* @param args <level> [options]
	*/
	public static void main(String[] args) {
		int difficulty = 0;
		boolean hasUnlimited = false;

		switch (args.length) {
			case 0:
				usage();
			case 2:
				if (args[1].equals("-u") || args[1].equals("--unlim")) {
					hasUnlimited = true;
				} else {
					usage();
				}
				// fall through
			case 1:
				try {
					difficulty = Integer.parseInt(args[0]);
				} catch (NumberFormatException e) {
					System.out.println(e);
					usage();
				}

				if (difficulty < 1 || difficulty > 5) {
					usage();
				}

				break;
			default:
				usage();
		}

		// Retrieve passwords according to difficulty
		NTPasswords p = new NTPasswords(difficulty);

		try {
			p.readFile();
		} catch (FileNotFoundException e) {
			System.out.println(e);
			System.exit(-1);
		}

		NTSession session = new NTSession(p, hasUnlimited);
		session.display();

	}

	/** Shows proper usage details in case of bad command arguments. */ 
	private static void usage() {
		System.out.println("Usage: NTApp level [options]");
		System.out.println("\tlevel:\n" 
			+ "\t\t1 - Very Easy\n" 
			+ "\t\t2 - Easy\n" 
			+ "\t\t3 - Average\n"
			+ "\t\t4 - Hard\n"
			+ "\t\t5 - Very Hard");
		System.out.println("\toptions:\n\t\t-u --unlim - Unlimited Tries");
		System.exit(-1);
	}
}