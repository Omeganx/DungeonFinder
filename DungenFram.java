import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.TextArea;
import java.awt.Cursor;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Font;


public class DungenFram extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDungeons;
	private JTextField txtResult;
	private JTextField textSeed;
	private JTextField textRange;
	private JTextField dungeons_number;
	private TextArea textField;
	private JProgressBar progressBar;
	private JSlider DungeonNumb;
	private Thread t;
	private JCheckBox Inside;
	private JCheckBox setblock;
	private JTextField txtChunk;
	private JCheckBox checkBoxFile;
	/**
	 * Launch the application.
	 */

		

	/**
	 * Create the frame.
	 */
	public DungenFram() {
		setResizable(false);
		setTitle("Dungeon Finder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 446);
		contentPane = new JPanel();
		contentPane.setEnabled(false);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		progressBar = new JProgressBar();
		progressBar.setMaximum(10000);
		progressBar.setStringPainted(true);
		progressBar.setValue(0);
		progressBar.setBounds(10, 11, 644, 41);
		contentPane.add(progressBar);
		DungeonNumb = new JSlider();
		DungeonNumb.setValue(5);
		DungeonNumb.setMinimum(1);
		DungeonNumb.setMaximum(8);
		
		DungeonNumb.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				dungeons_number.setText("   Dungeons Selected: "+String.valueOf(DungeonNumb.getValue()));
			}
		});
		DungeonNumb.setBounds(22, 160, 270, 26);
		contentPane.add(DungeonNumb);
		txtDungeons = new JTextField();
		txtDungeons.setText("Dungeons:");
		txtDungeons.setHorizontalAlignment(SwingConstants.CENTER);
		txtDungeons.setEditable(false);
		txtDungeons.setBounds(22, 98, 270, 20);
		contentPane.add(txtDungeons);
		txtDungeons.setColumns(10);
		
		JButton btnStart = new JButton("START");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				t = new Thread(new Runnable() {
		            @Override
		            public void run() {
		            	
		            	long seed = Long.parseLong(textSeed.getText());
		            	int range = Integer.parseInt(textRange.getText());
		            	
		            	//long seed = 812014930105396964L;
		    			
		    			Maindungen.start(range, seed, DungeonNumb.getValue(), Inside.isSelected(), setblock.isSelected(), checkBoxFile.isSelected());
		               
		}});t.start();
			
			}
		});
		btnStart.setBounds(10, 356, 148, 54);
		contentPane.add(btnStart);
		
		txtResult = new JTextField();
		txtResult.setEditable(false);
		txtResult.setHorizontalAlignment(SwingConstants.CENTER);
		txtResult.setText("Result");
		txtResult.setBounds(314, 63, 246, 20);
		contentPane.add(txtResult);
		txtResult.setColumns(10);
		
		textSeed = new JTextField();
		textSeed.setBounds(77, 204, 215, 20);
		contentPane.add(textSeed);
		textSeed.setColumns(10);
		
		textRange = new JTextField();
		textRange.setBounds(77, 229, 215, 20);
		contentPane.add(textRange);
		textRange.setColumns(10);
		
		dungeons_number = new JTextField();
		dungeons_number.setEditable(false);
		dungeons_number.setBounds(22, 129, 270, 20);
		contentPane.add(dungeons_number);
		dungeons_number.setColumns(10);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {
		            @Override
		            public void run() {
		            	System.exit(0);
		               
		}});t.start();
				
			}
		});
		btnExit.setBounds(191, 387, 89, 23);
		contentPane.add(btnExit);
		
		JLabel lblSeed = new JLabel("Seed:");
		lblSeed.setBounds(20, 207, 36, 14);
		contentPane.add(lblSeed);
		
		JLabel lblRange = new JLabel("Range:");
		lblRange.setBounds(21, 232, 46, 14);
		contentPane.add(lblRange);
		
		textField = new TextArea();
		textField.setCaretPosition(100);
		textField.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		textField.setBackground(new Color(255, 255, 255));
		textField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		textField.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		textField.setBounds(314, 98, 340, 312);

		contentPane.add(textField);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textField.setText("");
				Maindungen.clearText();
				
			}
		});
		btnClear.setBounds(570, 63, 84, 20);
		contentPane.add(btnClear);
		dungeons_number.setText("   Dungeons Selected: "+String.valueOf(DungeonNumb.getValue()));
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				t.stop();
			}
		});
		btnStop.setBounds(191, 356, 89, 23);
		contentPane.add(btnStop);
		
		Inside = new JCheckBox("Accept dungeons inside each others");
		Inside.setBounds(6, 326, 286, 23);
		contentPane.add(Inside);
		
		setblock = new JCheckBox("Generate a /setblock command");
		setblock.setBounds(6, 300, 256, 23);
		contentPane.add(setblock);
		
		txtChunk = new JTextField();
		txtChunk.setText("Chunk: ");
		txtChunk.setEditable(false);
		txtChunk.setBounds(20, 63, 272, 20);
		contentPane.add(txtChunk);
		txtChunk.setColumns(10);
		
		checkBoxFile = new JCheckBox("output file");
		checkBoxFile.setBounds(10, 271, 256, 23);
		contentPane.add(checkBoxFile);
	}

	public void setText(String str) {
		textField.setText(str);

	}
	
	public void setChunk(String str) {
		txtChunk.setText(str);
	}
	
	public void setLoadValue(int x){
		 SwingUtilities.invokeLater(new Runnable() {
		        @Override
		        public void run() {
	            	progressBar.setValue(x);
	            	progressBar.updateUI();
		        }
		    });
            	

		
		
		
		
		
	}
}
	
