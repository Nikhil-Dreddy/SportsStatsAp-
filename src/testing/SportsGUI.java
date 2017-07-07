package testing;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class SportsGUI extends JPanel implements ActionListener,TableModelListener   {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String[] coulmnNames = {"Name","Pos","Age","Team","GP","GS","AST","TRB","PPG"};
	String[] Team = { "ATL", "BOS", "BRK", "CHI", "CHO","CLE","DAL","DEN","DET","GSW","HOU","IND","LAC","LAL","MEM","MIA","MIL","MIN","NOP"
			,"NYK","OKC","ORL","PHI","PHO","PHX","POR","SAC","SAS","TOR","UTA","WAS","All Teams"};
	TestingUnderstanding A = new TestingUnderstanding();
	private JButton _updateBtn;
	private JTextArea _outputLog;
	private Object[][] PlayerData = A.GetPlayerData();
	private JTable _table;
	private JScrollPane scrollPane;
	private final JPanel topPanel; // container panel for the top
	DefaultTableModel model;
	private int count = 0;
	public SportsGUI() throws IOException {
		_updateBtn = new JButton("UpdateButton");
		_outputLog = new JTextArea(1,1);
		_outputLog.setEditable(false);
		JComboBox TeamList = new JComboBox(Team);
		TeamList.addActionListener(this);
		if(count == 0){
			PlayerData = A.GetPlayerData();
		}
		model = new DefaultTableModel(PlayerData,coulmnNames){
			public Class getColumnClass(int column) {
				Class returnValue;
				if ((column >= 0) && (column < getColumnCount())) {
					returnValue = getValueAt(0, column).getClass();
				} else {
					returnValue = Object.class;
				}
				return returnValue;
			}
		};
		_table = new JTable(model);
		_table.setDefaultEditor(Object.class, null);
		_table.setFillsViewportHeight(true);
		_table.setAutoCreateRowSorter(true);
		this.SetColumnSizes(_table);
		BufferedImage myPicture;
		scrollPane = new JScrollPane(_table);
		topPanel = new JPanel();
		JPanel RightPanel = new JPanel(new BorderLayout());
		RightPanel.setSize(100, 100);
		topPanel.add(scrollPane);
		RightPanel.add(_updateBtn,BorderLayout.NORTH);
		RightPanel.add(_outputLog,BorderLayout.SOUTH);
		RightPanel.add(TeamList,BorderLayout.WEST);
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
	@Override
	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox)e.getSource();
		String TeamName = (String)cb.getSelectedItem();
		try {
			PlayerData = A.GetTeamData(TeamName);
			model = new DefaultTableModel(PlayerData,coulmnNames){
				public Class getColumnClass(int column) {
					Class returnValue;
					if ((column >= 0) && (column < getColumnCount())) {
						returnValue = getValueAt(0, column).getClass();
					} else {
						returnValue = Object.class;
					}
					return returnValue;
				}
			};
			_table.setModel(model);
			this.SetColumnSizes(_table);
		} catch (IOException e1) {
			e1.printStackTrace();
		} 
	}
	private static void createAndShowGUI() throws IOException {

		JFrame frame = new JFrame("SportsStats");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent newContentPane = new SportsGUI();
		newContentPane.setOpaque(true); 
		frame.validate();
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
	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub

	}
		public void updateLabel(String Team) {
			
		}
}
