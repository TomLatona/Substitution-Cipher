package cus1185_lab1;
import java.util.*;

public class SubstitutionCipher {

	public static void main(String[] args) {
		//terminal program
		//accept user input to map each letter to a pairing
		//save key with those pairings
		
		//accept a message from user input
		//use key to encrypt
		//to do next: save encrypted string as text file and save key
		//so they can be sent to recipient
		
		//part 2: decryption
		
		System.out.println("This program will generate a key to encrypt a file using a substitution cipher.");
		System.out.println("Enter a lowercase letter to map to each letter of the alphabet.\n");
		
		char[] key = keygen();
		System.out.println("key has been generated");
		
		String phrase = inputText();
		String enc = encrypt(phrase, key);
		System.out.println(enc);
		
	}
	
	public static char[] keygen() {

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
		
		return key;
	}
			
	public static char charSwap(char x, char[] key) {
		//char x is the current character in the for loop through the input text
		//generate a new char to return that matches with the key
		int index=0;
		char newChar;
		char[] alphabet = new char[] 
				{'a','b','c','d','e','f','g','h','i','j','k','l','m',
				'n','o','p','q','r','s','t','u','v','w','x','y','z'};
		
		//iterate through alphabet and find match, save index
		for(int i=0; i<alphabet.length; i++) {
			if(x == alphabet[i]) {
				index = i;
			}
		}
		//match the index of that letter with the index of the key
		newChar = key[index];
		return newChar;
	}
	
	public static String inputText() {
		//get user input text to be encrypted
		Scanner in = new Scanner(System.in);
		System.out.println("Enter the phrase you would like to encrypt:");
		String phrase = in.nextLine();
		in.close();
		return phrase;
	}
			
	public static String encrypt(String phrase, char[] key) {
		//accept the phrase from the user to be encrypted and the key
		//iterate through the phrase and swap each character with their corresponding letter from the key
		//append every new char to an empty string and return it
		String enc = "";
		for(int i=0; i<phrase.length(); i++) {
			char x = charSwap(phrase.charAt(i), key);
			//System.out.println(x);
			enc = enc + x;
		}
		return enc;
	}
	
//	public static void decrypt(*encrypted text*) {
//		//decrypt the text
//	}

}
