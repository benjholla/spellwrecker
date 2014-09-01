package spellwrecker.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import spellwrecker.monitors.Monitor;
import spellwrecker.spellwreckers.QwertySpellWrecker;

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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		final JTextArea monitoredTextArea = new JTextArea();
		monitoredTextArea.setWrapStyleWord(true);
		frame.getContentPane().add(monitoredTextArea);
		
		final JLabel keystrokesPerUnitLabel = new JLabel("Keystrokes Per Second: ");
		keystrokesPerUnitLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(keystrokesPerUnitLabel, BorderLayout.NORTH);
		
		final Monitor monitor = new Monitor(1000, 5); // 1 second window, with 5 second history
		
		monitoredTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
//				monitor.observe();
//				keystrokesPerUnitLabel.setText("Keystrokes Per Second: " + monitor.getObservations() 
//						+ ", Max: " + monitor.getMaxObservations());
//				
//				if(monitor.getObservations() > 6 && monitor.getObservations() == monitor.getMaxObservations()){
//					if(rnd.nextInt(4) == 0){
//						e.setKeyChar(QwertySpellWrecker.spellwreck(e.getKeyChar()));
//						monitor.resetMaxObservations();
//						keystrokesPerUnitLabel.setForeground(Color.RED);
//					}
//				} else {
//					keystrokesPerUnitLabel.setForeground(Color.BLACK);
//				}
			}
		});
	}

}
