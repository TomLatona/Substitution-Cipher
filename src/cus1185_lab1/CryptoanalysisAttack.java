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
		 
		analyze(getFile()); //get enc file and send it to be analyzed
	}
	
	public static String getFile() throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		String encText="";
		
		while(true) {
			System.out.println("encrypted file name: ");
			String encName = in.next();
			File file = new File(encName);
			
			if(!file.exists()) {
				System.out.println("File not found!");
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
			Letter x = new Letter(letterSorted.get(s), (double)freqSorted.get(s)/total);
			let.add(x);
		}
		//print results
		for(Letter q : let) {
			q.print();
		}
		
		return let;
	}
} 