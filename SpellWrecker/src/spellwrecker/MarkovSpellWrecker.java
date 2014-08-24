package spellwrecker;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;


public class MarkovSpellWrecker {

	private int markovOrder = 5;
	private String inputHistory = "";
	private String trainingHistory = "";
	private HashMap<String,Integer> frequencyAnalysis = new HashMap<String,Integer>();
	private Random rnd = new Random();
	
	public MarkovSpellWrecker(int markovOrder){
		this.markovOrder = markovOrder;
	}
	
	public void train(Character c){
		trainingHistory += c;
		if(trainingHistory.length() == markovOrder){
			if(frequencyAnalysis.containsKey(trainingHistory)){
				frequencyAnalysis.put(trainingHistory, frequencyAnalysis.get(trainingHistory)+1);
			} else {
				frequencyAnalysis.put(trainingHistory, 1);
			}
			trainingHistory = trainingHistory.substring(1, trainingHistory.length());
		}
	}
	
	public static enum Typo {
		DELETION, INSERTION, REPLACEMENT, NONE
	}
	
	public void spellwreck(Character c, Typo typo) {
		if(inputHistory.length() == (markovOrder-1)){
			switch(typo.ordinal()){
				case 0: // deletion
					new DeletionAction(c).performAction();
					break;
				case 1: // insertion
					// prediction is not perfect, which is what we are counting on ;)
					// replacement will look realistic based on training set
					Character insertionPrediction = getPredictedCharacter(inputHistory);
					if(insertionPrediction != null){
						System.out.print(c);
						inputHistory = inputHistory.substring(1, inputHistory.length()) + c;
						new InsertionAction(c, insertionPrediction).performAction();
						inputHistory = inputHistory.substring(1, inputHistory.length()) + insertionPrediction;
					} else {
						System.out.print(c);
						inputHistory = inputHistory.substring(1, inputHistory.length()) + c;
					}
					break;
				case 2: // replacement
					// prediction is not perfect, which is what we are counting on ;)
					// replacement will look realistic based on training set
					Character preplacementPrediction = getPredictedCharacter(inputHistory);
					if(preplacementPrediction != null){
						new ReplacementAction(c, preplacementPrediction).performAction();
						inputHistory = inputHistory.substring(1, inputHistory.length()) + preplacementPrediction;
					} else {
						System.out.print(c);
						inputHistory = inputHistory.substring(1, inputHistory.length()) + c;
					}
					break;
				default:
					// don't spell wreck, invalid option
					System.out.print(c);
					inputHistory = inputHistory.substring(1, inputHistory.length()) + c;
					break;
			}
		} else {
			System.out.print(c);
			inputHistory += c;
		}
	}

	private Character getPredictedCharacter(String stem){
		HashMap<Character,Double> predictions = getWeightedPredictions(stem);
		return getWeightedRandom(predictions, rnd);
	}
	
	private HashMap<Character,Double> getWeightedPredictions(String stem){
		HashMap<Character,Double> weightedSeeds = new HashMap<Character,Double>();
		for(String s : frequencyAnalysis.keySet()){
			if(s.startsWith(stem)){
				weightedSeeds.put(s.charAt(s.length()-1), new Double(frequencyAnalysis.get(s)));
			}
		}
		for(Entry<Character,Double> entry : weightedSeeds.entrySet()){
			entry.setValue(entry.getValue()/weightedSeeds.size());
		}
		return weightedSeeds;
	}
	
	private <E> E getWeightedRandom(Map<E, Double> weights, Random random) {
	    E result = null;
	    double bestValue = Double.MAX_VALUE;
	    for (E element : weights.keySet()) {
	        double value = -Math.log(random.nextDouble()) / weights.get(element);
	        if (value < bestValue) {
	            bestValue = value;
	            result = element;
	        }
	    }
	    return result;
	}
	
	private static abstract class Action {
		protected Character input;
		public Action(Character input){
			this.input = input;
		}
		
		public abstract void performAction();
		
		public abstract String toString();
	}
	
	private static class DeletionAction extends Action {
		public DeletionAction(Character input) {
			super(input);
		}

		@Override
		public void performAction() {
			// swallowing character
		}

		@Override
		public String toString() {
			return "Action: Delete " + input;
		}
	}
	
	private static class ReplacementAction extends Action {
		protected Character output;
		public ReplacementAction(Character input, Character output) {
			super(input);
			this.output = output;
		}

		@Override
		public void performAction() {
			System.out.print(output);
		}
		
		@Override
		public String toString() {
			return "Action: Replace " + input + " with " + output;
		}
	}
	
	private static class InsertionAction extends Action {
		protected Character output;
		public InsertionAction(Character input, Character output) {
			super(input);
			this.output = output;
		}

		@Override
		public void performAction() {
			System.out.print(input);
			System.out.print(output);
		}
		
		@Override
		public String toString() {
			return "Action: Insert " + output + " in place of " + input;
		}
	}
}


