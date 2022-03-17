package cus1185_lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CryptoanalysisAttack {

	public static void main(String[] args) throws FileNotFoundException {
		//This program will accept an encrypted file from the substitution-cipher
		//It will run it through a frequency assessment using a db I will build of letter frequencies
		//It will print the likelihood of what each letter could be, and assemble a partially decrypted version
		
		//This decryption attempt works by analyzing linguistic patters in the encrypted text
		//and matching it with known patterns of letter frequencies in words
		Scanner in = new Scanner(System.in);
		System.out.println("file name");
		String encName = in.next();
		File file = new File(encName);
		String encText="";
		
		StringBuilder fileContents = new StringBuilder((int)file.length());
		try (Scanner scanner = new Scanner(file)) {
	        while(scanner.hasNextLine()) {
	            fileContents.append(scanner.nextLine() + System.lineSeparator());
	        }
	        encText = fileContents.toString();
	    }
		
		
		//encText is the encrypted file as a string
		analyze(encText);

	}
	public static void analyze(String enc) {
		//brute force the string and count how often each letter is used
		
		for(int i=0; i<enc.length(); i++) {
			
		}
	}

}
