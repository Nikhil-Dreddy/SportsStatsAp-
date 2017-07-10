package testing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SportsGUI extends JPanel implements TableModelListener {
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
	private Object[][] PlayerData;
	private ArrayList<Player> PlayerArray = A.intilazePlayerData();
	private JTable _table;
	private JScrollPane scrollPane;
	private final JPanel topPanel; // container panel for the top
	DefaultTableModel model;
	private int count = 0;
	private JLabel picLabel;
	private JPanel RightPanel;
	private TableModelListener TeamListener;
	public class TeamWorker extends SwingWorker<Void,Vector>{
		private Object[] PlayerData;
		private DefaultTableModel Table;
		private ArrayList<Player> CurrentData;
		private String Team;

		public TeamWorker(ArrayList<Player> PlayerData,String TeamName,DefaultTableModel Table){
			this.CurrentData = PlayerData;
			this.Team = TeamName;
			this.Table = Table;
			this.PlayerData = new Object[8];
		}
		@Override
		protected Void doInBackground() throws Exception {
			int i = 0;
			for(Player B: CurrentData){
				Vector<Comparable> A = new Vector<Comparable>();
				if(B.Team.equals(Team) || Team.equals("All Teams")) {
					A.add(B.Name);
					A.add(B.Position);
					A.add(B.Age);
					A.add(B.Team);
					A.add(B.GamesPlayed);
					A.add(B.GamesStarted);
					A.add(B.Assit);
					A.add(B.TRB);
					A.add(B.PPG);
					publish(A);
				}
				i++;
			}
			return null;
		}
		protected void process(List<Vector> rowsList)
		{
			for(Vector row : rowsList){
				DefaultTableModel tModel = (DefaultTableModel)_table.getModel();
				tModel.addRow(row);
				tModel.fireTableDataChanged();
			}
		}

	}
	public class TeamIconWorker extends SwingWorker<Void,ImageIcon> {
		private String TeamName;
		private ImageIcon TeamIcon;

		public TeamIconWorker(String A) {
			this.TeamName = A;
		}

		@Override
		protected Void doInBackground() throws Exception {
			if(TeamName.equals( "All Teams")) {
				TeamIcon =  new ImageIcon("C:/Users/Nikhil/Desktop/NBA PROJECT/SportStats/teamlogos/ABA.png");
			}
			else{
			TeamIcon =  new ImageIcon("C:/Users/Nikhil/Desktop/NBA PROJECT/SportStats/teamlogos/"+TeamName+".png");
			}
			publish(TeamIcon);
			return null;
		}
		@Override
		protected void process(List<ImageIcon> chunks) {
			for (ImageIcon TeamIcon : chunks) {
				picLabel.setIcon(TeamIcon);
				picLabel.setText("");
			}
		}
	}

	private ActionListener ComboListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
			String TeamName = (String)cb.getSelectedItem();
			DefaultTableModel tModel = (DefaultTableModel)_table.getModel();
			tModel.setRowCount(0); 
			TeamWorker b = new TeamWorker(PlayerArray,TeamName,model);
			b.execute();
			TeamIconWorker Icon = new TeamIconWorker(TeamName);
			Icon.execute();
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

	};

	public SportsGUI() throws IOException {
		_updateBtn = new JButton("UpdateButton");
		_outputLog = new JTextArea(1,1);
		_outputLog.setEditable(false);
		JComboBox TeamList = new JComboBox(Team);
		TeamList.addActionListener(ComboListener);
		PlayerData = A.GetPlayerData();
		TableModel model = new DefaultTableModel(PlayerData,coulmnNames){
			Class[] types = { String.class, String.class, Integer.class,
                    String.class,Integer.class,Integer.class,Double.class,Double.class,Double.class };
            @Override
            public Class getColumnClass(int columnIndex) {
                return this.types[columnIndex];
            }
		};
		model.addTableModelListener(this);
		_table = new JTable(model);
		_table.setDefaultEditor(Object.class, null);
		_table.setFillsViewportHeight(true);
		_table.setAutoCreateRowSorter(true);
		scrollPane = new JScrollPane(_table);
		topPanel = new JPanel();
		this.SetColumnSizes(_table);
		JPanel RightPanel = new JPanel(new BorderLayout());
		RightPanel.setSize(800,800);
		topPanel.add(scrollPane);
		RightPanel.add(_updateBtn,BorderLayout.NORTH);
		RightPanel.add(_outputLog,BorderLayout.SOUTH);
		RightPanel.add(TeamList,BorderLayout.WEST);
		_outputLog.append("Lebron Is Great");
		ImageIcon myPicture =  new ImageIcon("C:/Users/Nikhil/Desktop/NBA PROJECT/SportStats/teamlogos/ABA.png");
		picLabel = new JLabel(myPicture);
		RightPanel.add(picLabel,BorderLayout.CENTER);
		add(topPanel);
		add(RightPanel);
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
		_table.getTableHeader().setReorderingAllowed(false);
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
	public void updateLabel(String Team) {
		ImageIcon myPicture2 =  new ImageIcon("C:/Users/Nikhil/Desktop/thumbnails/Life of pablo.jpg");
		picLabel = new JLabel(myPicture2);
		RightPanel.add(picLabel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		_table.repaint();
	}
}
