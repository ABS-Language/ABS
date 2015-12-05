import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class IDE extends JFrame{
	private final int FRAME_WIDTH = 900;
	private final int FRAME_HEIGHT = 600;
	
	private final JTextArea console = new JTextArea(5, 30);
	private final JTextArea codeWindow = new JTextArea(5, 30);
	
	private final JButton btnRun = new JButton("Run");
	private final JButton btnDebug = new JButton("Debug");
	private final JButton btnOpen =  new JButton("Open");
	
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
			btnPanel.add(btnOpen);
			{
				btnOpen.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser chooser  = new JFileChooser();
						 FileNameExtensionFilter filter = new FileNameExtensionFilter(
							        "Text Files", "txt");
						 
						chooser.setFileFilter(filter);
						
						if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
							
							FileReader read = null;
							
							try {
								read = new FileReader(chooser.getSelectedFile());
							} 
							catch (FileNotFoundException e) {
									System.out.println("File not found!");
									e.printStackTrace();
							}
							
							BufferedReader buffer = new BufferedReader(read);
							
							String textRead;
							
							try {
								while((textRead = buffer.readLine()) != null) {	
									System.out.println(textRead);
									codeWindow.setText(textRead);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						 
					}
				});
			}
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
