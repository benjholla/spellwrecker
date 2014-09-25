package spellwrecker.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import spellwrecker.components.monitors.Monitor;
import spellwrecker.components.spellwreckers.QwertySpellWrecker;
import java.awt.Font;

public class TestMonitor {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestMonitor window = new TestMonitor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestMonitor() {
		initialize();
	}
	
	private long lastAction = System.currentTimeMillis();

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		final JTextArea monitoredTextArea = new JTextArea();
		monitoredTextArea.setLineWrap(true);
		monitoredTextArea.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		monitoredTextArea.setWrapStyleWord(true);
		frame.getContentPane().add(monitoredTextArea);
		
		final JLabel keystrokesPerUnitLabel = new JLabel("");
		keystrokesPerUnitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(keystrokesPerUnitLabel, BorderLayout.NORTH);
		
		final Monitor monitor = new Monitor(1000, 5); // 1 second window, with 5 second history
		
		monitoredTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				monitor.observe();
				
				DecimalFormat df = new DecimalFormat("#.##");
				keystrokesPerUnitLabel.setText("Keystrokes Per Second: " + monitor.getCurrentObservations() 
						+ ", Average: " + df.format(monitor.getAverageObservations()) + ", Max: " + monitor.getMaxObservations() 
						+ ", History Std Deviation: " + df.format(monitor.getHistoricalStandardDeviation())
						+ ", History: " + Arrays.toString(monitor.getHistoricalObservations()));
				
				keystrokesPerUnitLabel.setForeground(Color.BLACK);
				
				if(monitor.getAverageObservations() > 3){
					if(monitor.getHistoricalStandardDeviation() < 3.5){
						if(System.currentTimeMillis() - lastAction > 700){
							keystrokesPerUnitLabel.setForeground(Color.RED);
							e.setKeyChar(QwertySpellWrecker.spellwreck(e.getKeyChar()));
							lastAction = System.currentTimeMillis();
						}
					}
				}
			}
		});
	}

}
