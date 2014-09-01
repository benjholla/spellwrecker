package spellwrecker.monitors;

public class Monitor {
	
	private long windowLength;
	private int[] observations;
	private long[] timestamps;
	private int index = 0;
	
	public Monitor(long windowLength, int windowHistory){
		this.windowLength = windowLength;
		this.observations = new int[windowHistory];
		this.timestamps = new long[windowHistory];
		
		// initialize the timestamps array
		long currentTimestamp = System.currentTimeMillis();
		for(int i=1; i<=windowHistory; i++){
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
 
}
