import java.util.Random;
import java.util.Queue;

public class SkierOnHill extends Thread{
	Queue<String>waitQueue;
	String name;
	int maxTime;
	int minTime;
	
	public SkierOnHill(String name, Queue<String>waitQueue, int maxSlopeTime, int minSlopeTime){
		super(name);
		this.waitQueue = waitQueue;
		this.maxTime = maxSlopeTime;
		this.minTime = minSlopeTime;
	}
 
	public void run(){
		Random randomNumGen = new Random(); 
		int randomSleepTime = randomNumGen.nextInt(maxTime-minTime);
		try{
			sleep(randomSleepTime + minTime);
		} catch (InterruptedException e) {}
		waitQueue.add(getName());
	}
}
