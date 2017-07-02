package testing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;


public class SportsGUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] coulmnNames = {"Name","Pos","Age","Team","GP","GS","AST","TRB","PPG"};
	TestingUnderstanding A = new TestingUnderstanding();
	private JButton _updateBtn;
	private JTextArea _outputLog;
	private JTable _table;
	private JScrollPane scrollPane;
	private final JPanel topPanel;       // container panel for the top
	public SportsGUI() throws IOException {
		_updateBtn = new JButton("UpdateButton");
		_outputLog = new JTextArea(1,1);
		_outputLog.setEditable(false);
		_table = new JTable(A.GetPlayerData(),coulmnNames);
		_table.setFillsViewportHeight(true);
		this.SetColumnSizes(_table);
		BufferedImage myPicture;
		scrollPane = new JScrollPane(_table);
		topPanel = new JPanel();
		JPanel RightPanel = new JPanel(new BorderLayout());
		RightPanel.setSize(100, 100);
		topPanel.add(scrollPane);
		RightPanel.add(_updateBtn,BorderLayout.NORTH);
		RightPanel.add(_outputLog,BorderLayout.SOUTH);
		_outputLog.append("Lebron Is Great");
		try {
			myPicture = ImageIO.read(new File("C:/Users/Nikhil/Desktop/thumbnails/Life of pablo.jpg"));
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			RightPanel.add(picLabel,BorderLayout.CENTER);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		add(topPanel);
		add(RightPanel);
	}

	private static void createAndShowGUI() throws IOException {

		JFrame frame = new JFrame("SportsStats");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent newContentPane = new SportsGUI();
		newContentPane.setOpaque(true); 
		frame.add(newContentPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public void SetColumnSizes(JTable _table) {
		_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		_table.getColumnModel().getColumn(0).setPreferredWidth(140);
		_table.getColumnModel().getColumn(1).setPreferredWidth(40);
		_table.getColumnModel().getColumn(2).setPreferredWidth(40);
		_table.getColumnModel().getColumn(3).setPreferredWidth(40);
		_table.getColumnModel().getColumn(4).setPreferredWidth(40);
		_table.getColumnModel().getColumn(5).setPreferredWidth(40);
		_table.getColumnModel().getColumn(6).setPreferredWidth(40);
		_table.getColumnModel().getColumn(7).setPreferredWidth(40);
		_table.getColumnModel().getColumn(8).setPreferredWidth(40);
	}
	public static void main(String []args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}
}
