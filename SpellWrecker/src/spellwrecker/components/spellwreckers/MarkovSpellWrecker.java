package spellwrecker.components.spellwreckers;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

/**
 * Using a Markov chain, this corrupts the input character by predicting the next character
 * More often than not the prediction will be wrong, but similar to the observed training input.
 */
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
	
	public char spellwreck(char input) {
		char output = input;
		if(inputHistory.length() == (markovOrder-1)){
			Character prediction = getPredictedCharacter(inputHistory);
			if(prediction != null){
				inputHistory = inputHistory.substring(1, inputHistory.length()) + prediction;
				output = prediction;
			} else {
				inputHistory = inputHistory.substring(1, inputHistory.length()) + input;
			}
		} else {
			inputHistory += input;
		}
		return output;
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
}


