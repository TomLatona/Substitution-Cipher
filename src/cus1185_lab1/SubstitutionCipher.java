package cus1185_lab1;
import java.io.*;
import java.util.*;

public class SubstitutionCipher {

	public static void main(String[] args) throws FileNotFoundException {
		
		//** REMAINING TASKS **
		//implement checks to verify user entered choices (1 or 2 options, filename entries)
		//infinite loop for main method with optional escape sequence to quit program
		//properly fill out text fields with info about the program
		
		//SEQUENCE OF EVENTS
		//ask user if they want to encrypt or decrypt, run respective methods
		
		//if they choose encryption
			//ask if user wants to use existing key or make new one
			//if new key run keygen
			//if existing key, accept the filename and then search for it in directory
				//if found, send to encrypt method
		
		//if they choose decryption
			//ask for name of enc file, search for it
			//ask for name of key, search for it
			//run decrypt method
		
		Scanner in = new Scanner(System.in);
		System.out.println("Welcome ****");
		System.out.println("Would you like to encrypt or decrypt? Enter 1 for enc or 2 for dec");
		int choice = in.nextInt();
		
		//make system that checks entry is a 1 or 2, while loop
		if(choice == 1) {
			RunEncryption();
		}
		if(choice == 2) {
			RunDecryption();
		}	
	}
	
	public static void RunEncryption() throws FileNotFoundException {
		System.out.println("This is the encryption side of the program.");
		System.out.println("First you will need a key, then you may type your message to be encrypted with said key.");
		System.out.println("If you already created a key, you may use it. Simply put the file in this folder, and enter 2 at the next prompt.");
		System.out.println("If you would like to create a new key, enter 1.");
		String key = ""; //empty key string, will be defined later
		Scanner in = new Scanner(System.in); 
		int choice = in.nextInt();
		
		//*make system that checks entry is a 1 or 2, while loop*
		
		//create new key
		if(choice == 1) {
			char[] keyArray = keygen();
			key = keyArrayToString(keyArray);
			saveKeyFile(key);
		}
		
		//use existing key
		if(choice == 2) {
			System.out.println("Enter the exact name of the key file: "); //must include file extension .txt
			String keyName = in.next();
			File keyFile = new File(keyName);
			
			//gets file contents and writes it to a string, updates key variable
			StringBuilder fileContents = new StringBuilder((int)keyFile.length());
			try (Scanner scanner = new Scanner(keyFile)) {
		        while(scanner.hasNextLine()) {
		            fileContents.append(scanner.nextLine() + System.lineSeparator());
		        }
		        key = fileContents.toString();
		    }
		}
						
		String phrase = inputText(); //gets user input for text to encrypt
		String encString = encrypt(phrase, key); //encrypts user input
		System.out.println("Encrypted text: " + encString + "\n");		
		saveEncFile(encString);
	}
	
	public static void RunDecryption() throws FileNotFoundException {
		//get file names, write them to strings, send them to decrypt method
		Scanner in = new Scanner(System.in);
		System.out.println("Enter full name of key file");
		String userInputKey = in.next();
		System.out.println("Enter full name of encrypted file");
		String userInputFile = in.next();
		
		String key = "";
		String file = "";
		File keyFile = new File(userInputKey);
		File encFile = new File(userInputFile);
		
		StringBuilder fileContents = new StringBuilder((int)keyFile.length());
		try (Scanner scanner = new Scanner(keyFile)) {
	        while(scanner.hasNextLine()) {
	            fileContents.append(scanner.nextLine() + System.lineSeparator());
	        }
	        key = fileContents.toString();
	    }
		
		StringBuilder efileContents = new StringBuilder((int)encFile.length());
		try (Scanner escanner = new Scanner(encFile)) {
	        while(escanner.hasNextLine()) {
	            efileContents.append(escanner.nextLine() + System.lineSeparator());
	        }
	        file = efileContents.toString();
	    }
		
		String dec = decrypt(file, key);
		System.out.println("Here is your decrypted message: " + dec);
		saveDecFile(dec);
	}
	
	public static char[] keygen() {

		System.out.println("This program will generate a key to encrypt a file using a substitution cipher.");
		System.out.println("Enter a lowercase letter to map to each letter of the alphabet.\n");
		
		boolean found = false; //used to check for duplicates before adding value to key array
		Scanner in = new Scanner(System.in);
		
		char[] alphabet = new char[] 
				{'a','b','c','d','e','f','g','h','i','j','k','l','m',
				'n','o','p','q','r','s','t','u','v','w','x','y','z'};
		char[] key = new char[26];
		
		for(int i = 0; i < key.length; i++) {
			//iterate thru empty key array, adding user entered values each iteration
			
			found = false; //reset bool for duplicate check
			
			//print the alphabet array
			System.out.print("plaintext = ");
			for(int x=0; x<alphabet.length; x++) {
				System.out.print("(" + alphabet[x] + "), ");
			}
			System.out.println();
			
			//get character from user
			System.out.println("\n Enter a lowercase letter: ");
			char value = in.next().charAt(0);	
			
			//check for duplicate
			for(char z : key) {
				if(z == value) { found = true; }
			}

			//if value entered is duplicate
			while(found == true) {
				found = false;
				System.out.println("That letter has already been used, try another: ");
				value = in.next().charAt(0);
				
				//check for duplicate
				for(char z : key) {
					if(z == value) { found = true; }
				}
			}
			
			//if no duplicate, add to key
			if(found == false) {
				System.out.println(value);
				key[i] = value;
			}
			
			//print the key array
			System.out.print("key       = ");
			for(int n=0; n<key.length; n++) {
				System.out.print("(" + key[n] + "), ");
			}
			System.out.println();
		}
		
		System.out.println("\nKey has been generated!\n");
		return key;
	}
			
	public static String keyArrayToString(char[] key) {
		//will soon be removed 
		//going to optimize by saving key as string in keygen
		String keyString="";
		for(int i = 0; i<key.length; i++) {
			keyString += key[i];
		}
		return keyString;
	}
	
	public static void saveKeyFile(String key) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		System.out.println("What would you like to name your key: ");
		String keyFileName = in.next();
		
		//*add a check if filename already exists*
		File keyFile = new File(keyFileName+"KEY.txt");
		PrintWriter writer = new PrintWriter(keyFile);
		writer.print(key);
		writer.close();
		System.out.println("Key saved succesfully! Saved as '"+keyFileName+"KEY.txt'\n");
	}
	
	public static void saveEncFile(String encString) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		System.out.println("What would you like to name your encrypted file: ");
		String encFileName = in.next();
		
		//check if filename already exists
		File enc = new File(encFileName+"ENC.txt");
		PrintWriter writer = new PrintWriter(enc);
		writer.print(encString);
		writer.close();
		System.out.println("Encrypted file saved succesfully! Saved as '"+encFileName+"ENC.txt'\n");
	}
	
	public static void saveDecFile(String decString) throws FileNotFoundException {
		Scanner in = new Scanner(System.in);
		System.out.println("What would you like to name your decrypted file: ");
		String decFileName = in.next();
		
		//*add a check if filename already exists*
		File decFile = new File(decFileName+"DEC.txt");
		PrintWriter writer = new PrintWriter(decFile);
		writer.print(decString);
		writer.close();
		System.out.println("Key saved succesfully! Saved as '"+decFileName+"DEC.txt'\n");
	}
	
	public static char charSwap(char x, String key, String type) {
		//char x is the current character in the for loop through the input text
		//generate a new char to return that matches with the key
		char newChar = ' ';
		char[] alphabet = new char[] 
				{'a','b','c','d','e','f','g','h','i','j','k','l','m',
				'n','o','p','q','r','s','t','u','v','w','x','y','z'};
		
		//String alph = "abcdefghijklmnopqrstuvwxyz";
		
		if(type == "encrypt") {
			//iterate through alphabet and find match, save index
			for(int i=0; i<alphabet.length; i++) {
				//if x is a letter, swap it
				if(x == alphabet[i]) {
					newChar = key.charAt(i);
				}
			}
		}
		
		if(type == "decrypt") {
			for(int z=0; z<key.length()-2; z++) {
				//if x is a letter, swap it
				if(x == key.charAt(z)) {
					newChar = alphabet[z];
				}
			}
		}
		//will return swapped letter or a space
		return newChar;
		
	}
	
	public static String inputText() {
		//get user input text to be encrypted
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the phrase you would like to encrypt:");
		String phrase = in.nextLine();
		return phrase;
	}
			
	public static String encrypt(String phrase, String key) {
		//iterate through user input text, swap each character with the key
		String enc = "";
		for(int i=0; i<phrase.length(); i++) {
			char x = charSwap(phrase.charAt(i), key, "encrypt");
			enc += x;
		}
		return enc;
	}
	
	public static String decrypt(String encText, String key) {
		//iterate through encrypted text and swap each character
		String dec = "";
		for(int i=0; i<encText.length(); i++) {
			char x = charSwap(encText.charAt(i), key, "decrypt");
			dec += x;
		}
		return dec;
	}
}
