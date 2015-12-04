import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Formatter;

import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.SwingConstants;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IDE extends JFrame{
	private final int FRAME_WIDTH = 900;
	private final int FRAME_HEIGHT = 600;

	private JFrame ABS;
	private static JTextArea txt;
	private static Formatter x;
	
	private final JTextArea console = new JTextArea(5, 30);
	private final JTextArea codeWindow = new JTextArea(5, 30);
	
	private final JButton btnRun = new JButton("RUN");
	private final JButton btnDebug = new JButton("Debug");
	
	private final JPanel contentPane = new JPanel(new BorderLayout());
	private final JPanel btnPanel = new JPanel(new GridLayout());
	
	public IDE() {
		super();
		
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		    	System.exit(0);
		    }
		});
		
		this.setTitle("ABS Language");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLocationRelativeTo(null);
		
		this.setContentPane(contentPane);
		
		contentPane.add(btnPanel, BorderLayout.NORTH);
		{
			btnPanel.add(btnRun);
			btnPanel.add(btnDebug);
		}
		
		contentPane.add(console, BorderLayout.EAST);
		{
			console.setEditable(false);
			console.setBackground(Color.BLACK);
			console.setForeground(Color.WHITE);
		}
		
		contentPane.add(codeWindow, BorderLayout.WEST);		
		
		this.setVisible(true);
	}
}
