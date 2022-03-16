package cus1185_lab1;
import java.io.*;
import java.util.*;

public class SubstitutionCipher {

	public static void main(String[] args) throws FileNotFoundException {
		//terminal program
		//accept user input to map each letter to a pairing
		//save key with those pairings
		
		//accept a message from user input
		//use key to encrypt
		
		
		//SEQUENCE OF EVENTS
		//ask user if they want to encrypt or decrypt, run respective methods (soon to be seperate classes)
		
		//if they choose encryption
			//ask if user wants to use existing key or make new one
			//if new key run keygen
			//if existing key, accept the filename and then search for it in directory
				//if found, send to encrypt method
		
		//if they choose decryption
			//ask for name of enc file, search for it
			//ask for name of key, search for it
			//run decrypt method
		
		
		// *change so that keygen returns a string*
		char[] key = keygen();
		String keyString = keyArrayToString(key);
		saveKeyFile(keyString);
		
		
		String phrase = inputText(); //gets user input
		String encString = encrypt(phrase, keyString); //encrypts user input
		System.out.println("Encrypted text: " + encString + "\n");
		
		saveEncFile(encString);	
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
	
	public static char charSwap(char x, String key) {
		//char x is the current character in the for loop through the input text
		//generate a new char to return that matches with the key
		char newChar = ' ';
		char[] alphabet = new char[] 
				{'a','b','c','d','e','f','g','h','i','j','k','l','m',
				'n','o','p','q','r','s','t','u','v','w','x','y','z'};
		
		//iterate through alphabet and find match, save index
		for(int i=0; i<alphabet.length; i++) {
			//if x is a letter, swap it
			if(x == alphabet[i]) {
				newChar = key.charAt(i);
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
		//accept the phrase from the user to be encrypted and the key
		//iterate through the phrase and swap each character with their corresponding letter from the key
		//append every new char to an empty string and return it
		String enc = "";
		for(int i=0; i<phrase.length(); i++) {
			char x = charSwap(phrase.charAt(i), key);
			//System.out.println(x);
			enc += x;
		}
		return enc;
	}
	
//	public static void decrypt(*encrypted text*) {
//		//decrypt the text
//	}

}
