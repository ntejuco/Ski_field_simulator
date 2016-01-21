import java.util.*;
import javax.swing.*;

public class SkiFieldTextWorker extends SwingWorker<Void, String> {

    JTextArea textArea;
    JButton startButton;
    boolean paused = false;
    JTextField liftSeats;
    JTextField liftSpeed;
    JTextField maxPossibleSlopeTime;
    JTextField totalSkiers;
    JTextField liftStopProb;

    public SkiFieldTextWorker(JTextArea textArea, JButton workerButton, JTextField liftSeats, JTextField liftSpeed, JTextField maxPossibleSlopeTime, JTextField totalSkiers, JTextField liftStopProb) {
        this.textArea = textArea;
        this.startButton = workerButton;
        this.liftSeats = liftSeats;
        this.liftSpeed = liftSpeed;
        this.maxPossibleSlopeTime = maxPossibleSlopeTime;
        this.totalSkiers = totalSkiers;
        this.liftStopProb = liftStopProb;
        
    }

    @Override
    public Void doInBackground() throws Exception {
    	double stopProbability = 0.05;
    	int maxTimeOnSlope = 12000;
    	int minTimeOnSlope = 2000;
    	double liftDelayTime = 1000;
    	String skierLeavingLift;
		Queue<String> waitQueue = new LinkedList<String>();
		Queue<String> liftQueue = new LinkedList<String>();
		for (int i = 1; i <= 30; i++){
			waitQueue.add(Integer.toString(i));
			}
		for (int i = 1; i <=10; i++){
			liftQueue.add("Empty");
		}
		liftSeats.setText("Number of seats: " + liftQueue.size());
		liftStopProb.setText("Probability of lift stop: " + stopProbability);
		maxPossibleSlopeTime.setText("Longest slope time (ms): " + maxTimeOnSlope);
		totalSkiers.setText("Skiers on field: " + waitQueue.size());
		liftSpeed.setText("Speed of lift (Hz): " + liftDelayTime / 1000);
		while(!isCancelled()){
			outputLiftQueue(liftQueue);
		    outputWaitQueue(waitQueue);
		    Thread.sleep((int) liftDelayTime);
			if (liftQueue.peek() != "Empty" && liftQueue.peek() != null){
				skierLeavingLift = liftQueue.poll();
				new SkierOnHill(skierLeavingLift, waitQueue, maxTimeOnSlope, minTimeOnSlope).start();			
				}
			else {
				liftQueue.remove();
			}
			if (waitQueue.peek() != null){
				liftQueue.add(waitQueue.remove());
			}
			else{
				liftQueue.add("Empty");
			}
			isLiftWorking(stopProbability);
			System.out.print("\n");
			}
		return null;
    }

    protected void process(List<String> outputStrings) {
    	for (String i : outputStrings){
    		textArea.append(i);
    	}   		       
    }

    @Override
    protected void done() {
        textArea.append("Stopped\n");
    }

private void outputWaitQueue(Queue<String> waitQueue){
	String waitQueueElements = "";
	int waitQueueSize = 0;
	for (String i: waitQueue){
		waitQueueElements += i + ", ";
		waitQueueSize +=1;
	}
	String outString = "InQueue (" + waitQueueSize + "): [" + 
	                   waitQueueElements + "]";
	int finalElement = outString.indexOf(", ]");
	if (finalElement != -1){
	outString = outString.substring(0, finalElement);
	}
	if (waitQueueSize != 0){
		publish(outString + "]\n\n");
	}
	else{
		publish(outString + "\n\n");
	}
	
}

private void outputLiftQueue(Queue<String> liftQueue){
	String liftQueueElements = "";
	int liftQueueSize = 0;
	for (String i: liftQueue){
		liftQueueElements += i + ", ";
		if (i != "Empty"){
			liftQueueSize +=1;				
		}
	}
	String outString = "On lift (" + liftQueueSize + "): [" + 
	                   liftQueueElements + "]";
	int finalElement = outString.indexOf(", ]");
	if (finalElement != -1){
	outString = outString.substring(0, finalElement);
	publish(outString + "]\n");
	}
	else {
		publish(outString);
	}
}	

private void isLiftWorking(double stopProbability) throws InterruptedException{
	Random rnd = new Random();
	if (rnd.nextDouble() < stopProbability){
		int timeStopped = rnd.nextInt(8000);
		publish("Lift stops temporarily (for " + timeStopped + " milliseconds).\n");
		Thread.sleep(timeStopped);
		publish("Lift continues operation\n\n");
		}
	}

protected void pauseThread(){
	try {
		wait();
	} catch (InterruptedException e) {}
}
protected void restartThread(){
		notify();
		}

}