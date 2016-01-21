import java.util.*;

public class A3Part1 extends Thread{
	public static void main(String[] args) throws InterruptedException{
		String skierLeavingLift;
		Queue<String> waitQueue = new LinkedList<String>();
		Queue<String> liftQueue = new LinkedList<String>();
		for (int i = 1; i <= 30; i++){
			waitQueue.add(Integer.toString(i));
			}
		for (int i = 1; i <=10; i++){
			liftQueue.add("Empty");
		}
		while(true){
			printLiftQueue(liftQueue);
		    printWaitQueue(waitQueue);
			if (liftQueue.peek() != "Empty" && liftQueue.peek() != null){
				skierLeavingLift = liftQueue.poll();
				new SkierOnHill(skierLeavingLift, waitQueue, 12000, 2000).start();			
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
			isLiftWorking();
			System.out.print("\n");
			sleep(1000);
			
			}
	}
	private static void printWaitQueue(Queue<String> waitQueue){
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
			System.out.println(outString + "]");
		}
		else{
			System.out.println(outString);
		}
		
	}
	
	private static void printLiftQueue(Queue<String> liftQueue){
		String liftQueueElements = "";
		int liftQueueSize = 0;
		for (String i: liftQueue){
			liftQueueElements += i + ", ";
			if (i != "Empty"){
				liftQueueSize +=1;				
			}
		}
		String outString = "On Lift (" + liftQueueSize + "): [" + 
		                   liftQueueElements + "]";
		int finalElement = outString.indexOf(", ]");
		if (finalElement != -1){
		outString = outString.substring(0, finalElement);
        System.out.println(outString + "]");
		}
		
	}	
	
	private static void isLiftWorking() throws InterruptedException{
		Random rnd = new Random();
		if (rnd.nextDouble() < 0.05){
			int timeStopped = rnd.nextInt(8000);
			System.out.println("\nLift stops temporarily (for " + timeStopped + " milliseconds).");
			sleep(timeStopped);
			System.out.println("Lift continues operation");
		}
	}
}
