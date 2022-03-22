package cus1185_lab1;

public class Letter {

	private char letter;
	private double frequency;
	
	public Letter(char let, double freq){
		this.letter = let;
		this.frequency = freq;
	}
	
	public void print() {
		System.out.println(this.letter + ": " + Math.round(this.frequency*100.0)/100.0 + "%");
	}
	
	public char getGh() {
		return this.letter;
	}
	
	public double getFr() {
		return Math.round(this.frequency*100.0)/100.0;
	}
}
