import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
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

public class Environment {

	private JFrame ABS;
	private static JTextArea txt;
	private static Formatter x;
	private static JTextArea console;
	private JButton btnRun;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			x = new Formatter("text.txt");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Environment window = new Environment();
					window.ABS.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public Environment() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		ABS = new JFrame();
		ABS.setTitle("ABS Language");
		ABS.setBounds(100, 100, 1500, 800);
		ABS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//ABS.getContentPane().setLayout(new GridLayout(1,2));		
		
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		btnRun = new JButton("RUN");
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				x.format("%s", txt.getText().toString());
				x.close();
				
				try {
                    FileReader reader = new FileReader( "text.txt" );
                    BufferedReader br = new BufferedReader(reader);
                    console.read( br, null );
                    br.close();
                    console.requestFocus();
                }
                catch(Exception e2) {
                	System.out.println(e2); 
                }
			}
		});
		btnRun.setBounds(10, 13, 110, 30);
		p.add(btnRun);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1,2));
		txt = new JTextArea();
		txt.setTabSize(4);
		txt.setBounds(10, 43, 387, 392);
		p1.add(txt);
		
		console = new JTextArea();
		console.setEditable(false);
		console.setTabSize(4);
		console.setBackground(Color.BLACK);
		console.setForeground(Color.WHITE);
		console.setBounds(413, 43, 388, 392);
		p1.add(console);
		
		ABS.getContentPane().add(p, BorderLayout.NORTH);
		ABS.getContentPane().add(p1, BorderLayout.CENTER);
		
	}
}
