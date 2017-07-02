package testing;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
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
	String[] coulmnNames = {"Name","Pos","Team","GP","GS","AST","TRB","PPG"};
	TestingUnderstanding A = new TestingUnderstanding();
	private JButton _updateBtn;
	private JTextArea _outputLog;
	private JTable _table;
	private JScrollPane scrollPane;
	private final JSplitPane splitPane;  // split the window in top and bottom
	private final JPanel topPanel;       // container panel for the top
	private final JPanel bottomPanel;    // container panel for the bottom
	public SportsGUI() {
		_updateBtn = new JButton("UpdateButton");
		_outputLog = new JTextArea();
		_outputLog.setEditable(false);
		_table = new JTable(A.GetPlayerData(),coulmnNames);
		_table.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(_table);
		splitPane = new JSplitPane();
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		topPanel.add(scrollPane);
		bottomPanel.add(_updateBtn);
		splitPane.setLeftComponent(bottomPanel);
		splitPane.setRightComponent(topPanel);
		add(splitPane);
		add(_outputLog);
	}

	private static void createAndShowGUI() {
		
		JFrame frame = new JFrame("SportsStats");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent newContentPane = new SportsGUI();
		newContentPane.setOpaque(true); 
		frame.add(newContentPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public static void main(String []args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});

	}
}
