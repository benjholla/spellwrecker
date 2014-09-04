package spellwrecker.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import spellwrecker.SpellWrecker;

public class TestSpellWrecker {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestSpellWrecker window = new TestSpellWrecker();
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
	public TestSpellWrecker() {
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
		
		final SpellWrecker monitor = new SpellWrecker();
		
		monitoredTextArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				e.setKeyChar(monitor.spellwreck(e.getKeyChar()));
			}
		});
	}

}
