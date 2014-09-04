package spellwrecker.components.monitors;

public class Monitor {
	
	private long windowLength;
	private int[] observations;
	private long[] timestamps;
	private int index = 0;
	
	public Monitor(long windowLength, int windowHistory){
		this.windowLength = windowLength;
		this.observations = new int[windowHistory + 1];
		this.timestamps = new long[windowHistory + 1];
		
		// initialize the timestamps array
		long currentTimestamp = System.currentTimeMillis();
		for(int i=1; i<=timestamps.length; i++){
			timestamps[i-1] = currentTimestamp + (i * windowLength);
		}
	}
	
	public void observe(){
		// record the observation
		long currentTimestamp = System.currentTimeMillis();
		if(currentTimestamp < timestamps[index]){
			observations[index]++;
		} else {
			while(currentTimestamp >= timestamps[index]){
				long lastTimestamp = timestamps[index];
				index = (index + 1) % timestamps.length;
				// extend history forward  by one cell if needed
				if(lastTimestamp > timestamps[index]){
					timestamps[index] = lastTimestamp + windowLength;
					observations[index] = 0;
				}
			}
			observations[index]++;
		}
	}
	
	public int getCurrentObservations(){
		return observations[index];
	}

	public double getAverageObservations(){
		double result = 0.0;
		for(int i=0; i<observations.length; i++){
			result += observations[i];
		}
		return result / (double) observations.length;
	}

	public int getMaxObservations() {
		int result = 0;
		for(int i=0; i<observations.length; i++){
			if(observations[i] > result){
				result = observations[i];
			}
		}
		return result;
	}
	
	// TODO: Don't use this algorithm as advised on http://stackoverflow.com/a/14839593/475329
	public double getHistoricalStandardDeviation(){
		int[] history = getHistoricalObservations();
		
		// calculate the mean
		double sum = 0.0;
        for(int i=0; i<history.length; i++){
        	sum += observations[i];
        }
		double mean = ((double) sum) / ((double) history.length);
		
		// calculate the standard variance
		double temp = 0;
        for(int i=0; i<history.length; i++){
        	double x = (double) observations[i];
        	temp += (mean-x)*(mean-x);
        }
        double variance = temp/((double)history.length);
        
		return Math.sqrt(variance);
	}
	
	public int[] getHistoricalObservations(){
		int size = observations.length-1;
		int offset = (index + 1) % observations.length;;
		int[] historicalObservations = new int[size];
		for(int i=0; i<size; i++){
			historicalObservations[i] = observations[offset];
			offset = (offset + 1) % observations.length;
		}
		return historicalObservations;
	}
 
}
