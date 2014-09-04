package spellwrecker.components.spellwreckers;

import java.util.Random;

/**
 * Uses the QWERTY keyboard layout as cheap means for a typo generator 
 * Picks a suitable typo based on keys that are physically located near each other on a QWERTY keyboard.
 */
public class QwertySpellWrecker {

	public static char spellwreck(char input){
		
		if(input == ' '){
			return input;
		}
		
		// create some character arrays to hold the keyboard layouts
		// note a space is used as a filler character and will be ignored
		char[][] keyboard = {{'`', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '=', ' '},
				{' ', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', '[', ']', '\\'},
				{' ', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', ';', '\'', ' ', ' '},
				{' ', 'z', 'x', 'c', 'v', 'b', 'n', 'm', ',', '.', '/', ' ', ' ', ' '}};

		char[][] shiftKeyboard = {{'~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '_', '+', ' '},
					 {' ', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', '{', '}', '|'},
					 {' ', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', ':', '"', ' ', ' '},
					 {' ', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '<', '>', '?', ' ', ' ', ' '}};
		
		// search both keyboards for the input character
		for(int row=0; row<keyboard.length; row++){
			for(int column=0; column<keyboard[row].length; column++){
				if(input == keyboard[row][column] || input == shiftKeyboard[row][column]){
					// for convenience lets just work with the keyboard the input is in from now on
					if(input == shiftKeyboard[row][column]){
						keyboard = shiftKeyboard;
					}
					// compute the minimum and maximum key locations we could choose from
					int rowLowerBound = row == 0 ? 1 : (row-1);
					int rowUpperBound = row == (keyboard.length-1) ? (keyboard.length-2) : (row+1);
					int columnLowerBound = column == 0 ? 1 : (column-1);
					int columnUpperBound = column == (keyboard[row].length-1) ? (keyboard[row].length-2) : (column+1);
					
					// create an array of unique typo characters
					String neighbors = "";
					char rowLowerBoundCharacter = keyboard[rowLowerBound][column];
					if(rowLowerBoundCharacter != ' ' && !neighbors.contains("" + rowLowerBoundCharacter)){
						neighbors += rowLowerBoundCharacter;
					}
					char rowUpperBoundCharacter =  keyboard[rowUpperBound][column];
					if(rowUpperBoundCharacter != ' ' && !neighbors.contains("" + rowUpperBoundCharacter)){
						neighbors += rowUpperBoundCharacter;
					}
					char columnLowerBoundCharacter = keyboard[row][columnLowerBound];
					if(columnLowerBoundCharacter != ' ' && !neighbors.contains("" + columnLowerBoundCharacter)){
						neighbors += columnLowerBoundCharacter;
					}
					char columnUpperBoundCharacter = keyboard[row][columnUpperBound];
					if(columnUpperBoundCharacter != ' ' && !neighbors.contains("" + columnUpperBoundCharacter)){
						neighbors += columnUpperBoundCharacter;
					}
					
					// another neighbor for upper case letter is the lower case letter (user let go of shift key too early)
					if(Character.isLetter(input) && Character.isUpperCase(input)){
						neighbors += Character.toLowerCase(input);
					}
					
					// randomly pick a possible typo
					char[] typos = neighbors.toCharArray();
					char typo = typos[new Random().nextInt(typos.length)];
					
					// don't convert letters to symbols, it is too noticeable
					if(Character.isLetter(input) && !Character.isLetter(typo)){
						return input;
					} else {
						return typo;
					}
				}
			}
		}
		
		// just return the original input if a typo cannot be generated
		return input;
	}
	
}
