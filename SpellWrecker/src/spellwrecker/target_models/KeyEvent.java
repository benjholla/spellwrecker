package spellwrecker.target_models;

import spellwrecker.SpellWrecker;

public class KeyEvent {

	// target variable to modify
	public char keyChar;
	
	// variables to add
	private boolean modified = false;
	
	public void setKeyChar(char keyChar){
		this.keyChar = keyChar;
	}
	
	// target method to modify
	public char getKeyChar(){
		if(!modified){
			setKeyChar(SpellWrecker.spellwreck(keyChar));
		}
		return keyChar;
	}
	
}
