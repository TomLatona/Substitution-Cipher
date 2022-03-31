//Author: Thomas Latona
//Cryptanalysis attack on substitution-cipher encrypted text
//Generates letter frequencies and partially decrypted text for further analysis

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CryptoanalysisAttack {

	public static void main(String[] args) throws FileNotFoundException {
		//This program will accept an encrypted file created with substitution-cipher program
		//It will generate a list of letter frequencies and display them next to the real frequencies (sourced letter frequency analysis of the Concise Oxford Dictionary (9th Edition, 1995)
		//It will also use the list of frequencies and create a partially decrypted version of the text by comparing and swapping with the reference frequencies
		
		//welcome statement
		System.out.println("~~ Welcome to Cryptanalysis Attack by Thomas Latona ~~");
		System.out.println("This program will generate a partially decrypted text by analyzing letter frequencies.");
		System.out.println("Import a file encrypted by Substitution Cipher, and the program will take care of the rest\n by utilizing a native reference library for letter frequencies created from an analysis of the Oxford English Dictionary.\n");
		
		String enc = getFile(); //user specifies file, returns contents as a string
		String newText = getNewText(analyze(enc), refLibrary(), enc); //gets partially decrypted text
		
		System.out.println("Encrypted text:		 " + enc);
		System.out.println("Partialy decrypted text: " + newText);
		
		System.out.println("\nThis text is obviously not fully decrypted. However by utilizing the letter frequencies\nyou can manually get closer and closer to a fully decrypted version.");
	}
	
	public static Letter[] refLibrary() {
		//actual letter frequencies 
		Letter[] ref = new Letter[26];
		ref[0] = new Letter('a', 8.5);
		ref[1] = new Letter('b', 2.07);
		ref[2] = new Letter('c', 4.54);
		ref[3] = new Letter('d', 3.38);
		ref[4] = new Letter('e', 11.16);
		ref[5] = new Letter('f', 1.81);
		ref[6] = new Letter('g', 2.47);
		ref[7] = new Letter('h', 3.0);
		ref[8] = new Letter('i', 7.54);
		ref[9] = new Letter('j', 0.19);
		ref[10] = new Letter('k', 1.1);
		ref[11] = new Letter('l', 5.49);
		ref[12] = new Letter('m', 3.01);
		ref[13] = new Letter('n', 6.65);
		ref[14] = new Letter('o', 7.16);
		ref[15] = new Letter('p', 3.16);
		ref[16] = new Letter('q', 0.19);
		ref[17] = new Letter('r', 7.58);
		ref[18] = new Letter('s', 5.73);
		ref[19] = new Letter('t', 6.95);
		ref[20] = new Letter('u', 3.63);
		ref[21] = new Letter('v', 1.0);
		ref[22] = new Letter('w', 1.28);
		ref[23] = new Letter('x', 0.29);
		ref[24] = new Letter('y', 1.77);
		ref[25] = new Letter('z', 0.27);
		return ref;
	}
	
	public static String getFile() throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		String encText="";
		
		while(true) { //in case file doesn't exist/isn't typed correctly
			System.out.println("Encrypted file name: ");
			String encName = in.next();
			File file = new File(encName);
			
			if(!file.exists()) {
				System.out.println("\nFile not found!");
				System.out.println("Please make sure it is typed correct and includes .txt extension\n");
			}
			
			else {
				//turn file into a string
				StringBuilder fileContents = new StringBuilder((int)file.length());
				try (Scanner scanner = new Scanner(file)) {
			        while(scanner.hasNextLine()) {
			            fileContents.append(scanner.nextLine() + System.lineSeparator());
			        }
			        encText = fileContents.toString();
			    }
				break;
			}
		}
		return encText; //file contents as string
	}
	
	
	public static ArrayList<Letter> analyze(String enc) {
		//WILL RETURN ARRAYLIST OF LETTERS OBJECTS CONTAINING ALL UNIQUE CHARS AND THEIR FREQUENCY
		
		//gets rid of spaces and characters
		ArrayList<Character> letters = new ArrayList<Character>();
		for(int i=0; i<enc.length(); i++) {
			if(Character.isLetter(enc.charAt(i))) { 
				letters.add(enc.charAt(i));
			}
		}
		
		//iterate through letters and add up how many time each occurs, 
		//new arraylist that maps indexes for a letter and its frequency
		ArrayList<Integer> frequency = new ArrayList<Integer>(letters.size());
		for(int j = 0; j<letters.size(); j++) {
			int counter = 0;
			for(int u = 0; u<letters.size(); u++) {
				if(letters.get(j).charValue() == letters.get(u).charValue()) {
					counter++;
				}
			}
			frequency.add(j, counter);	
		}
		
		//get rid of duplicates
		ArrayList<Character> letterSorted = new ArrayList<Character>();
		ArrayList<Integer> freqSorted = new ArrayList<Integer>();
		for(int a = 0; a<letters.size(); a++) {
			if(!letterSorted.contains(letters.get(a))) {
				letterSorted.add(letters.get(a));
				freqSorted.add(frequency.get(a));
			}
		}
		
		//new empty arraylist of type Letter, 
		//iterate through filtered letters array and initialize any non duplicate letter objects
		int total = letters.size();
		ArrayList<Letter> let = new ArrayList<Letter>();
		for(int s=0; s<letterSorted.size(); s++) {
			Letter x = new Letter(letterSorted.get(s), (double)freqSorted.get(s)/total*100);
			let.add(x);
		}
		return let; //arraylist containing all letters and their frequencies
	}
	
	public static String getNewText(ArrayList<Letter> let, Letter[] ref, String enc) {

		//print reference and text letter-frequencies
		System.out.println("Reference frequencies:");
		for(Letter j : ref) {
			j.print();
		}
		System.out.println("Encrypted text frequencies:");
		for(Letter q : let) {
			q.print();
		}
		
		//used to create partial decryption
		StringBuilder newtext = new StringBuilder(enc);
		
		//iterate through encrypted text
		for(int k=0; k<enc.length(); k++){
			
			double f = 0.0; //stores frequency	
			if(Character.isLetter(enc.charAt(k))) {

				//find the letter object in let with matching char and save its frequency
				for(Letter x : let) {
					if(x.getCh() == enc.charAt(k)) {
						f = x.getFr();
					}
				}
				
				//find matching frequency (within range of .5) in ref and swap char in newtext
				for(Letter y : ref) {
					if(y.getFr() > (f-.5) && y.getFr() < (f+.5)) {
						newtext.setCharAt(k, y.getCh());
					}
				}
			}
		}
		return newtext.toString(); //partial decryption
	}
} 

//Letter object: can be initialized with char for the letter and a double for the frequency
//Contains three methods: print to display current char and frequency
//and two get methods, for each value
class Letter {
	private char letter;
	private double frequency;
	
	public Letter(char let, double freq){
		this.letter = let;
		this.frequency = freq;
	}
	
	public void print() {
		System.out.println(this.letter + ": " + Math.round(this.frequency*100.0)/100.0 + "%");
	}
	
	public char getCh() {
		return this.letter;
	}
	
	public double getFr() {
		return Math.round(this.frequency*100.0)/100.0;
	}
}