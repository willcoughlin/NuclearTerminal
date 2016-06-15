/*
 * NTPasswords.java
 * Copyright (c) 2016 Will Coughlin
 * 
 * The file "passwords.txt" was aquired from the dotnetperls-controls project
 * as "enable1.txt" under public domain.
 *
 * See "LICENSE.txt" for full details.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Handles dictionary file and retrieves passwords of length 
 * given by difficulty.
 *
 * @author Will Coughlin
 * @version 06/13/2016
 */
public class NTPasswords {

	/** Default path to passwords dictionary file. */
	public static final String DICTIONARY = "./passwords.txt";

	private int passLen;
	private String[] passwords;
	private String correctPass;

	/** @return Correct password */
	public String getCorrectPass() {
		return this.correctPass;
	}

	/** 
	* Constructor: Sets difficulty and length of password array.
	*
	* @param difficulty Determines length of individual passwords.
	*/
	public NTPasswords(int difficulty) {
		Random rand = new Random(System.nanoTime());
		int pLen = 0; // password length based on difficulty
		int listLen = rand.nextInt(6) + 10; // ArrayList length from 10 to 15

		switch (difficulty) { 
			case 1: // Very Easy: 4-5 characters
				pLen = rand.nextInt(2) + 4;
				break;
			case 2: // Easy: 6-8 characters
				pLen = rand.nextInt(3) + 6;
				break;
			case 3: // Average: 9-10 characters
				pLen = rand.nextInt(2) + 9;
				break;
			case 4: // Hard: 11-12 characters
				pLen = rand.nextInt(2) + 11;
				break;
			case 5: // Very Hard: 13 to 15 characters
				pLen = rand.nextInt(3) + 13;
				break;
			default:
				// This shouldn't be possible.
		}

		this.passLen = pLen;
		this.passwords = new String[listLen];
	}

	/**
	* Reads input file and selects passwords from randomized list.
	*
	* @throws FileNotFoundException for failure to read file
	*/
	public void readFile() throws FileNotFoundException {
		boolean success = false;

		BufferedReader fileRead = new BufferedReader(new FileReader(DICTIONARY));
		Scanner fileScan = new Scanner(fileRead);

		// Will contain all words of specified length
		ArrayList<String> all = new ArrayList<String>(); 

		while (fileScan.hasNext()) {
			String word = fileScan.nextLine();
			if (word.length() == this.passLen) {
				all.add(word);
			}
		}

		Random rand = new Random(System.nanoTime());
		Collections.shuffle(all, rand); // Randomize 

		for (int i = 0; i < this.passwords.length; i++) {
			this.passwords[i] = all.get(i); // Copy
		}

		// Select random correct password
		int randomIndex = rand.nextInt(this.passwords.length);
		this.correctPass = this.passwords[randomIndex];
	}

	/**
	* Override Object class toString.
	* 
	* @return String representation of object
	*/
	@Override public String toString() {
		String out = "";

		for (int i = 0; i < this.passwords.length - 1; i += 2) {
			out += passwords[i] + "\t" + passwords[i + 1] + "\n";
		}
		return out;
	}
}