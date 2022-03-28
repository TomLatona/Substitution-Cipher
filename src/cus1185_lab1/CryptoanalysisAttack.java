package cus1185_lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CryptoanalysisAttack {

	public static void main(String[] args) throws FileNotFoundException {
		//This program will accept an encrypted file from the substitution-cipher
		//It will run it through a frequency assessment using a db I will build of letter frequencies
		//It will print the likelihood of what each letter could be, and assemble a partially decrypted version
		
		//This decryption attempt works by analyzing linguistic patters in the encrypted text
		//and matching it with known patterns of letter frequencies in words
		 
		//create array of letter objects and initialize each letter
		//of the alphabet with their coresponding frequency
		
		System.out.println("~~ Welcome to Cryptanalysis Attack by Thomas Latona ~~");
		System.out.println("This program will generate a partially decrypted text by analyzing letter frequencies.");
		System.out.println("Import a file encrypted by Substitution Cipher, and the program will take care of the rest\n by utilizing a native reference library for letter frequencies sourced by Oxford University.\n");
		
		String enc = getFile();
		String newText = getNewText(analyze(enc), refLibrary(), enc);
		System.out.println("Encrypted text:		 " + enc);
		System.out.println("Partialy decrypted text: " + newText);
		
		System.out.println("\nThis text is obviously not fully decrypted. However by utilizing the letter frequencies\nyou can manually get closer and closer to a fully decrypted version.");
	}
	
	public static Letter[] refLibrary() {
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
		
		while(true) {
			System.out.println("Encrypted file name: ");
			String encName = in.next();
			File file = new File(encName);
			
			if(!file.exists()) {
				System.out.println("\nFile not found!");
				System.out.println("Please make sure it is typed correct and includes .txt extension\n");
			}
			
			else {
				
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
		return encText;
	}
	
	
	public static ArrayList<Letter> analyze(String enc) {
		//brute force the string and count how often each letter is used
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
		return let;
	}
	
	public static String getNewText(ArrayList<Letter> let, Letter[] ref, String enc) {

		//print ref and let
		System.out.println("Reference frequencies:");
		for(Letter j : ref) {
			j.print();
		}
		
		System.out.println("Encrypted text frequencies:");
		for(Letter q : let) {
			q.print();
		}
		
		StringBuilder newtext = new StringBuilder(enc);
		
		//iterate string
		for(int k=0; k<enc.length(); k++){
			double f = 0.0;
			
			if(Character.isLetter(enc.charAt(k))) {

				//find this letter in let and save its freq
				for(Letter x : let) {
					if(x.getCh() == enc.charAt(k)) {
						f = x.getFr();
					}
				}
				
				//find matching freq in ref and swap char in string
				for(Letter y : ref) {
					if(y.getFr() > (f-.5) && y.getFr() < (f+.5)) {
						newtext.setCharAt(k, y.getCh());
					}
				}
			}
		}
		return newtext.toString();
	}
} 

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