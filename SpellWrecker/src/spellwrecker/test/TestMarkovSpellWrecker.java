package spellwrecker.test;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import spellwrecker.MarkovSpellWrecker;


public class TestMarkovSpellWrecker {

public static void main(String[] args) throws Exception {
		
		MarkovSpellWrecker wrecker = new MarkovSpellWrecker(5);
		String trainingInput = readFile("Robinson_Crusoe.txt");
		for(char c : trainingInput.toCharArray()){
			wrecker.train(c);
		}
		
		String input = "This is a test string and I want to spellwreck it!";
		System.out.println(input);
		for(char c : input.toCharArray()){
			if(new Random().nextInt(10) == 0){ // spellwreck 1/10 times
				wrecker.spellwreck(c, MarkovSpellWrecker.Typo.values()[new Random().nextInt(3)]);
			} else {
				wrecker.spellwreck(c, MarkovSpellWrecker.Typo.NONE);
			}
		}
	}
	
	private static String readFile(String path) throws Exception {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, Charset.defaultCharset());
	}

}
