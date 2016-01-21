import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class SkiFieldText extends JFrame {
	JButton stopButton;
	JButton startButton;
	SkiFieldTextWorker worker;

    public SkiFieldText() {
        setTitle("SkiFieldText");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container cp = getContentPane();
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.PAGE_AXIS));

        final JTextArea textArea = new JTextArea();
        JTextField liftSeats = new JTextField("Number of seats: -", 15);
        JTextField liftSpeed = new JTextField("Speed of lift (Hz): -" , 15);
        JTextField maxPossibleSlopeTime = new JTextField("Longest slope time (ms): -", 20);
        JTextField liftStopProb = new JTextField("Probability of lift stop: -", 15);
        JTextField totalSkiers = new JTextField("Skiers on field: -", 15);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        cp.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(liftSeats);
        rightPanel.add(liftSpeed);
        rightPanel.add(totalSkiers);
        rightPanel.add(maxPossibleSlopeTime);
        rightPanel.add(liftStopProb);

        JPanel topPanel = new JPanel();
          
        final JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try{
                startButton.setEnabled(false);
                worker.execute();
            	} catch(Exception excep) {
            	  textArea.append("Must be initialized\n");
            	  startButton.setEnabled(true);
            	}
            }
        });
        topPanel.add(startButton);

        final JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopButton.setEnabled(false);
                startButton.setEnabled(false);
                try{
                    worker.cancel(true);
                } catch(Exception excep){
                	textArea.append("Nothing to stop\n");
                }
                stopButton.setEnabled(true);
            }
        });
        topPanel.add(stopButton);
        
        JButton initializeButton = new JButton("Initialize Ski Field");
        initializeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		worker.cancel(true);
            	} catch(Exception ex) {}
                worker = initializeWorker(textArea, initializeButton, liftSeats,
                		                  liftSpeed, maxPossibleSlopeTime, totalSkiers, liftStopProb);
                textArea.append("Initialized!\n\n");
                startButton.setEnabled(true);
            }
        });
        topPanel.add(initializeButton);
        
        
        cp.add(topPanel, BorderLayout.NORTH);
        cp.add(rightPanel, BorderLayout.EAST);

        pack();
    }
    
    private static SkiFieldTextWorker initializeWorker(JTextArea textArea, 
    		JButton startButton, JTextField liftSeats, JTextField liftSpeed, 
    		JTextField maxPossibleSlopeTime, JTextField totalSkiers, JTextField liftStopProb){
    	
    	SkiFieldTextWorker worker = new SkiFieldTextWorker(textArea, startButton, 
    			liftSeats, liftSpeed, maxPossibleSlopeTime, totalSkiers, liftStopProb);
    	
    	return worker;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SkiFieldText().setVisible(true);
            }
        });
    }
 
}
