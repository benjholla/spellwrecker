package spellwrecker.test;

import spellwrecker.components.spellwreckers.QwertySpellWrecker;

public class TestQwertySpellWrecker {

	public static void main(String[] args) {
		char[][] keyboard = {{'`', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=', ' '},
							 {' ', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\'},
							 {' ', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\'', ' ', ' '},
							 {' ', 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/', ' ', ' ', ' '}};

		char[][] shiftKeyboard = {{'~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', ' '},
								  {' ', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', '{', '}', '|'},
								  {' ', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', ':', '"', ' ', ' '},
								  {' ', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '<', '>', '?', ' ', ' ', ' '}};
		
		for(int i=0; i<keyboard.length; i++){
			for(int j=0; j<keyboard[i].length; j++){
				if(keyboard[i][j] == ' ') continue;
				System.out.println("Input: " + keyboard[i][j]);
				System.out.println("Output: " + QwertySpellWrecker.spellwreck(keyboard[i][j]) + "\n");
			}
		}
		
		for(int i=0; i<shiftKeyboard.length; i++){
			for(int j=0; j<shiftKeyboard[i].length; j++){
				if(shiftKeyboard[i][j] == ' ') continue;
				System.out.println("Input: " + shiftKeyboard[i][j]);
				System.out.println("Output: " + QwertySpellWrecker.spellwreck(shiftKeyboard[i][j]) + "\n");
			}
		}
	}

}
