package cus1185_lab1;
import java.util.*;

public class SubstitutionCipher {

	public static void main(String[] args) {
		//terminal program
		//accept user input to map each letter to a pairing
		//save key with those pairings
		//create method so that plaintext can be ran through algorithm and 
		// produce encrypted text file -- in.txt and out.txt
		
		//part 2: decryption
		
		System.out.println("This program will generate a key to encrypt a file using a substitution cipher.");
		System.out.println("Enter a lowercase letter to map to each letter of the alphabet.\n");
		
		char[] key = keygen();
		System.out.println("key has been generated");
		
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
			
	public static String inputText() {
		//get user input text to be encrypted
		return null;
		}
			
	public static void encrypt() {
			//do the thing
		}
	
//	public static void decrypt(*encrypted text*) {
//		//decrypt the text
//	}

}
