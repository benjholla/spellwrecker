package spellwrecker.monitors;

import java.util.LinkedList;

public class Monitor {

	private long timeDelta;
	private LinkedList<Long> observations = new LinkedList<Long>();
//	private LinkedList<Integer> history = new LinkedList<Integer>();
	private int maxObservations = 0;
//	private double averageObservations = 0;
	
	public Monitor(long timeDelta){
		this.timeDelta = timeDelta;
	}
	
	public void observe(){
		long currentTimestamp = System.currentTimeMillis();
		observations.add(currentTimestamp);
		long expirationTimestamp = currentTimestamp - timeDelta;
		while(true){
			if(observations.getFirst() <= expirationTimestamp){
				observations.removeFirst();
			} else {
				break;
			}
		}
		int numObservations = observations.size();
		if(numObservations > maxObservations){
			maxObservations = numObservations;
		}
	}
	
	public int getMaxObservations(){
		return maxObservations;
	}
	
	public int getObservations(){
		return observations.size();
	}
	
	public void resetMaxObservations(){
		maxObservations = 0;
		observations.clear();
	}
}
