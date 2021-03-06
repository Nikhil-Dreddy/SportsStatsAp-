package testing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;
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
			,"NYK","OKC","ORL","PHI","PHO","POR","SAC","SAS","TOR","UTA","WAS","All Teams","Traded Players"};
	String[] SeasoncolumnNames = {"Age","Team","GP","GS","MPG","STL","BLK","AST","TRB","PPG"};
	String[] playercolumnNames = {"GP","GS","MPG","STL","BLK","AST","TRB","PPG"};
	Integer[][] data = new Integer[5][SeasoncolumnNames.length];
	TestingUnderstanding A = new TestingUnderstanding();
	private JButton _updateBtn;
	private JTextField _outputLog;
	private JTextField _compareText;
	private Object[][] PlayerData;
	private ArrayList<Player> PlayerArray = A.intilazePlayerData();
	private JTable _table;
	private JScrollPane scrollPane;
	private final JPanel topPanel; // container panel for the top
	DefaultTableModel model;
	private JLabel picLabel;
	private JLabel playerLabel;
	private JPanel RightPanel;
	private JScrollPane playerScroll;
	private JTable _playerTable;
	private JPanel chartPanel;
	private JComboBox graphBox;
	private ArrayList<Player> seasonStats = new ArrayList<Player>();
	private JFreeChart chart;
	private int numberOfSeasons = 0;
	
	ImageIcon myPicture =  new ImageIcon( SportsGUI.class.getResource(
            "/res/ABA.png"));
	public class TeamWorker extends SwingWorker<Void,Vector>{
		private ArrayList<Player> CurrentData;
		private String Team;

		public TeamWorker(ArrayList<Player> PlayerData,String TeamName,DefaultTableModel Table){
			this.CurrentData = PlayerData;
			this.Team = TeamName;
		}
		@Override
		protected Void doInBackground() throws Exception {
			if(this.Team.equals("Traded Players")) {
				this.Team = "TOT";
			}
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
			}
			return null;
		}
		protected void process(List<Vector> rowsList)
		{
			for(Vector row : rowsList){
				DefaultTableModel tModel = (DefaultTableModel)_table.getModel();
				boolean existsinTable = false;
				for(int i =0;i<tModel.getRowCount();i++) {
					if(tModel.getValueAt(i,0).equals(row.get(0))) {
						existsinTable = true;
						break;
					}
				}
				if(!existsinTable) {
					tModel.addRow(row);
					tModel.fireTableDataChanged();
				}
			}
		}

	}
	public class PlayerWorker extends SwingWorker<Void,Vector>{
		private ArrayList<Player> CurrentData;
		private String Player;
		public PlayerWorker(ArrayList<Player> PlayerData,String PlayerName,DefaultTableModel Table,String refname){
			this.CurrentData = PlayerData;
			this.Player = PlayerName;
		}
		@Override
		protected Void doInBackground() throws Exception {
			for(Player B: CurrentData){
				Vector<Comparable> A = new Vector<Comparable>();
				if(B.Name.equals(Player)) {
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
			}
			return null;
		}
		protected void process(List<Vector> rowsList)
		{
			for(Vector row : rowsList){
				DefaultTableModel tModel = (DefaultTableModel)_table.getModel();
				boolean existsinTable = false;
				for(int i =0;i<tModel.getRowCount();i++) {
					if(tModel.getValueAt(i,0).equals(row.get(0))) {
						existsinTable = true;
						break;
					}
				}
				if(!existsinTable) {
					tModel.addRow(row);
					tModel.fireTableDataChanged();
				}
			}
		}

	}
	public class TeamIconWorker extends SwingWorker<Void,ImageIcon> {
		private String TeamName;
		private ImageIcon TeamIcon;
		private JLabel TheLabel;

		public TeamIconWorker(String A,JLabel B) {
			this.TeamName = A;
			this.TheLabel = B;
		}

		@Override
		protected Void doInBackground() throws Exception {
			if(TeamName.equals( "All Teams")||TeamName.equals("Traded Players")) {
				TeamIcon =  new ImageIcon( SportsGUI.class.getResource("/res/ABA.png"));
			}
			else{
				TeamIcon =  new ImageIcon( SportsGUI.class.getResource("/res/"+TeamName+".png"));
			}
			publish(TeamIcon);
			return null;
		}
		@Override
		protected void process(List<ImageIcon> chunks) {
			for (ImageIcon TeamIcon : chunks) {
				this.TheLabel.setIcon(TeamIcon);
				this.TheLabel.setText("");
			}
		}
	}

	public class PlayerStats2 extends SwingWorker<Void,Vector>{
		private ArrayList<Player> PlayerData;
		private String PlayerName;
		private ArrayList<Player> CurrentData;
		private String RefName;
		private String Stat;
		public PlayerStats2(String PlayerName,DefaultTableModel Table,ArrayList<Player> CurrentData,ArrayList<Player> A,String Stat) {
			this.PlayerName = PlayerName;
			this.PlayerData = A;
			this.CurrentData = CurrentData;
			this.Stat = Stat;

		}
		@Override
		protected Void doInBackground() throws Exception {
			for(Player B: CurrentData){
				if(B.Name.equals(PlayerName)) {
					this.RefName = B.RefName;
					break;
				}
			}
			char firstletter = this.RefName.charAt(0);
			String Url = "http://www.basketball-reference.com/players/"+firstletter+"/"+this.RefName+".html";
			Document doc =  Jsoup.connect(Url)
					.userAgent( "mozilla/17.0")
					.timeout(5000).get();
			Elements Table = doc.select("table");
			for(Element Row:Table.select("tr")) {
				Elements A = Row.select("td");

				if(A.isEmpty()||A.size() <=3) {
					continue;
				}
				if(A.get(0).text().isEmpty()) {
					break;
				}


				this.PlayerData.add(new Player(Integer.parseInt(A.get(0).text()),A.get(1).text(),Integer.parseInt(A.get(4).text())
						,Integer.parseInt(A.get(5).text()),Double.parseDouble(A.get(6).text()),
						Double.parseDouble(A.get(23).text()),Double.parseDouble(A.get(22).text()),Double.parseDouble(A.get(28).text())
						,Double.parseDouble(A.get(24).text()),Double.parseDouble(A.get(25).text()),this.PlayerName));
			}
			for(Player A: this.PlayerData) {
				Vector<Comparable> B = new Vector<Comparable>();
				B.add(A.Age);
				B.add(A.Team);
				B.add(A.GamesPlayed);
				B.add(A.GamesStarted);
				B.add(A.MinutesPlayed);
				B.add(A.Steals);
				B.add(A.Blocks);
				B.add(A.Assit);
				B.add(A.TRB);
				B.add(A.PPG);
				B.add(A.Name);
				publish(B);
			}
			return null;
		}
		@Override
		protected void process(List<Vector> rowsList)
		{			
			for(Vector row : rowsList){
				DefaultTableModel tModel = (DefaultTableModel)_playerTable.getModel();
				boolean existsinTable = false;
				for(int i =0;i<tModel.getRowCount();i++) {
					if((int)tModel.getValueAt(i,0)== (int)row.get(0)) {
						existsinTable = true;
						break;
					}
				}
				if(!existsinTable) {
					tModel.addRow(row);
					tModel.fireTableDataChanged();
				}
			}

		}
		@Override
		protected void done() {

			try {
				get();
			} catch (InterruptedException | ExecutionException ex) {
				ex.printStackTrace();
			}

			DefaultTableModel tModel = (DefaultTableModel)_playerTable.getModel();
			final XYSeries series = new XYSeries("PPG");

			for(int i = 0;i<tModel.getRowCount();i++) {
				series.add((int)tModel.getValueAt(i, 0),(double)tModel.getValueAt(i,_playerTable.getColumn(this.Stat).getModelIndex() ));
			}
			final XYSeriesCollection data = new XYSeriesCollection(series);
			final JFreeChart chart2 = ChartFactory.createXYLineChart(
					this.PlayerName,
					"Age", 
					this.Stat, 
					data,
					PlotOrientation.VERTICAL,
					true,
					true,
					false
					);


			RightPanel.removeAll();
			RightPanel.revalidate(); // This removes the old chart 
			Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
			XYPlot xyPlot = (XYPlot) chart2.getPlot();
			XYLineAndShapeRenderer renderer =
					(XYLineAndShapeRenderer) xyPlot.getRenderer();
			renderer.setBaseShapesVisible(true);
			renderer.setSeriesShape(0, cross);
			chartPanel = new ChartPanel(chart2);
			RightPanel.setLayout(new BoxLayout(RightPanel, BoxLayout.PAGE_AXIS)); 
			RightPanel.add(chartPanel);
			RightPanel.add(graphBox);
			RightPanel.add(_compareText);
			RightPanel.repaint();// This method makes the new chart appear
		}
	}


	public class ComparePlayers extends SwingWorker<Void,Vector>{
		private ArrayList<Player> PlayerData;
		private String PlayerName;
		private int i;
		private ArrayList<Player> CurrentData;
		private String RefName;
		private String Stat;
		public ComparePlayers(String PlayerName,DefaultTableModel Table,ArrayList<Player> CurrentData,ArrayList<Player> A,String Stat) {
			this.PlayerName = PlayerName;
			this.PlayerData = A;
			this.CurrentData = CurrentData;
			this.Stat = Stat;
			this.i = this.PlayerData.size();
		}
		@Override
		protected Void doInBackground() throws Exception {
			for(Player B: CurrentData){
				if(B.Name.equals(PlayerName)) {
					this.RefName = B.RefName;
					break;
				}
			}
			char firstletter = this.RefName.charAt(0);
			String Url = "http://www.basketball-reference.com/players/"+firstletter+"/"+this.RefName+".html";
			Document doc =  Jsoup.connect(Url)
					.userAgent( "mozilla/17.0")
					.timeout(5000).get();
			Elements Table = doc.select("table");
			for(Element Row:Table.select("tr")) {
				Elements A = Row.select("td");

				if(A.isEmpty()||A.size() <=3) {
					continue;
				}
				if(A.get(0).text().isEmpty()) {
					break;
				}

				this.PlayerData.add(new Player(Integer.parseInt(A.get(0).text()),A.get(1).text(),Integer.parseInt(A.get(4).text())
						,Integer.parseInt(A.get(5).text()),Double.parseDouble(A.get(6).text()),
						Double.parseDouble(A.get(23).text()),Double.parseDouble(A.get(22).text()),Double.parseDouble(A.get(28).text())
						,Double.parseDouble(A.get(24).text()),Double.parseDouble(A.get(25).text()),this.PlayerName));
			}
			for(Player A: this.PlayerData) {
				Vector<Comparable> B = new Vector<Comparable>();
				B.add(A.Age);
				B.add(A.Team);
				B.add(A.GamesPlayed);
				B.add(A.GamesStarted);
				B.add(A.MinutesPlayed);
				B.add(A.Steals);
				B.add(A.Blocks);
				B.add(A.Assit);
				B.add(A.TRB);
				B.add(A.PPG);
			}
			return null;
		}
		@Override
		protected void done() {
			CompareChart(PlayerName, Stat, this.i);
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
			TeamIconWorker Icon = new TeamIconWorker(TeamName,picLabel);
			Icon.execute();
			SetColumnSizes(_table);
		}
	};

	private ActionListener PlayerComboListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox)e.getSource();
			String Stat = (String)cb.getSelectedItem();
			_playerTable.getModel();
			String PlayerName = _table.getValueAt(_table.getSelectedRow(), 0).toString();
			refreshChart(PlayerName, Stat);
		}

	};

	Action playercompareaction = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String PlayerName = _compareText.getText();

			DefaultTableModel tModel = (DefaultTableModel)_playerTable.getModel();
			ComparePlayers b = new ComparePlayers(PlayerName,tModel,PlayerArray,seasonStats,(String)graphBox.getSelectedItem());
			boolean PlayerAdded = false;
			for(int i = 0;i<seasonStats.size();i++) {
				if(seasonStats.get(i).Name.equals(PlayerName)) {
					PlayerAdded = true;
					break;
				}
			}
			if(PlayerAdded) {
				CompareChart(PlayerName, (String)graphBox.getSelectedItem(), numberOfSeasons);
			}
			else {
				numberOfSeasons = seasonStats.size();
				b.execute();
			}
		}
	};


	private ListSelectionListener PlayerSelect = (new ListSelectionListener(){
		public void valueChanged(ListSelectionEvent event) {
			// do some actions here, for example
			// print first column value from selected row
			DefaultTableModel tModel = (DefaultTableModel)_playerTable.getModel();
			_table.setSelectionBackground(Color.RED);
			if(_table.getSelectedRow() == -1) {
			}
			else {
				String PlayerName = _table.getValueAt(_table.getSelectedRow(), 0).toString();
				_outputLog.setText(PlayerName);				
				tModel.setColumnIdentifiers(SeasoncolumnNames);
				tModel.setRowCount(0);
				seasonStats = new ArrayList<Player>();
				PlayerStats2 Player = new PlayerStats2(PlayerName, tModel,PlayerArray,seasonStats,"TRB");
				try {
					Player.execute();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	});
	private ActionListener OutPutListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String PlayerName = _outputLog.getText();
			DefaultTableModel tModel = (DefaultTableModel)_table.getModel();
			tModel.setRowCount(0);
			PlayerWorker b = new PlayerWorker(PlayerArray,PlayerName,model,"B");
			b.execute();
			TeamIconWorker Icon = new TeamIconWorker("ABA",picLabel);
			Icon.execute();
			SetColumnSizes(_table);
		}

	};
	Action action = new AbstractAction()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String PlayerName = _outputLog.getText();
			DefaultTableModel tModel = (DefaultTableModel)_table.getModel();
			tModel.setRowCount(0); 
			PlayerWorker b = new PlayerWorker(PlayerArray,PlayerName,model,"B");
			b.execute();
			TeamIconWorker Icon = new TeamIconWorker("ABA",picLabel);
			Icon.execute();
			SetColumnSizes(_table);
		}
	};

	public SportsGUI() throws IOException {
		_updateBtn = new JButton("SearchButton");
		_outputLog = new JTextField();
		_outputLog.setEditable(true);
		_outputLog.addActionListener(action);
		_compareText = new JTextField();
		_compareText.addActionListener(playercompareaction);
		JComboBox TeamList = new JComboBox(Team);
		graphBox = new JComboBox(playercolumnNames);
		graphBox.addActionListener(PlayerComboListener);
		TeamList.addActionListener(ComboListener);
		_updateBtn.addActionListener(OutPutListener);
		PlayerData = A.GetPlayerData();
		TableModel model = new DefaultTableModel(PlayerData,coulmnNames){
			Class[] types = { String.class, String.class, Integer.class,
					String.class,Integer.class,Integer.class,Double.class,Double.class,Double.class };
			@Override
			public Class getColumnClass(int columnIndex) {
				return this.types[columnIndex];
			}
		};

		TableModel playermodel = new DefaultTableModel(5,SeasoncolumnNames.length){
			Class[] types = { Integer.class, String.class, Integer.class,
					Integer.class,Double.class,Double.class,Double.class,Double.class,Double.class,Double.class,
					Double.class,Double.class};
			@Override
			public Class getColumnClass(int columnIndex) {
				return this.types[columnIndex];
			}
		};

		model.addTableModelListener(this);
		this.Overalltable(model);
		this.PlayerTable(playermodel);
		playerScroll = new JScrollPane(_playerTable);
		scrollPane = new JScrollPane(_table);
		topPanel = new JPanel();
		this.SetColumnSizes(_table);
		picLabel = new JLabel(myPicture);
		playerLabel = new JLabel(myPicture);
		JPanel westPanel = new JPanel();
		BoxLayout layout = new BoxLayout(westPanel, BoxLayout.Y_AXIS);
		westPanel.setLayout(layout);
		westPanel.setAlignmentX(0);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setTopComponent(topPanel);         // at the top we want our "topPanel"
		splitPane.setBottomComponent(westPanel);
		splitPane.setEnabled(false);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.PAGE_AXIS));
		topPanel.add(scrollPane);
		_outputLog.setMaximumSize(new Dimension(150,30));
		TeamList.setMaximumSize(TeamList.getPreferredSize());
		Box box = Box.createVerticalBox();
		TeamList.setAlignmentX(LEFT_ALIGNMENT);
		picLabel.setAlignmentX(LEFT_ALIGNMENT);
		_outputLog.setAlignmentX(LEFT_ALIGNMENT);
		_updateBtn.setAlignmentX(LEFT_ALIGNMENT);			
		westPanel.add(TeamList,BorderLayout.NORTH);
		westPanel.add(picLabel,BorderLayout.WEST);
		westPanel.add(_outputLog,BorderLayout.SOUTH);
		westPanel.add(_updateBtn,BorderLayout.EAST);
		box.add(splitPane);
		JSplitPane PlayersplitPane = new JSplitPane();
		JPanel TabelPanel = new JPanel();
		RightPanel = new JPanel(new BorderLayout());
		PlayersplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		PlayersplitPane.setTopComponent(TabelPanel);
		PlayersplitPane.setBottomComponent(RightPanel);
		JTabbedPane tabbedPane = new JTabbedPane();
		chartPanel = createChartPanel();
		PlayersplitPane.setEnabled(false);
		PlayersplitPane.setLayout(new BoxLayout(PlayersplitPane, BoxLayout.PAGE_AXIS));

		TabelPanel.add(playerScroll);
		RightPanel.setLayout(new BoxLayout(RightPanel, BoxLayout.PAGE_AXIS)); 
		RightPanel.add(chartPanel);
		RightPanel.add(graphBox);
		RightPanel.add(_compareText);
		_outputLog.setText(("Lebron Is Goat"));;
		tabbedPane.addTab("TeamTab", box);
		tabbedPane.addTab("PlayerTab",PlayersplitPane);
		add(tabbedPane,BorderLayout.CENTER);
	}



	private static void createAndShowGUI() throws IOException {
		JFrame frame = new JFrame("SportsStats");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JComponent newContentPane = new SportsGUI();
		newContentPane.setOpaque(true); 
		frame.setLayout(new FlowLayout());
		frame.validate();
		frame.add(newContentPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.revalidate();
		frame.repaint();
	}
	public void SetColumnSizes(JTable _table) {
		_table.getTableHeader().setReorderingAllowed(false);
		_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		_table.getColumnModel().getColumn(0).setPreferredWidth(140);
		_table.getColumnModel().getColumn(1).setPreferredWidth(40);
		_table.getColumnModel().getColumn(2).setPreferredWidth(40);
		_table.getColumnModel().getColumn(3).setPreferredWidth(50);
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
					try {
						UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
							| UnsupportedLookAndFeelException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					createAndShowGUI();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void tableChanged(TableModelEvent e) {
		_table.repaint();
	}
	public void Overalltable(TableModel m) {
		_table = new JTable(m);
		_table.getSelectionModel().addListSelectionListener( PlayerSelect);
		_table.setDefaultEditor(Object.class, null);
		_table.setFillsViewportHeight(true);
		_table.setAutoCreateRowSorter(true);
		_table.setBorder(new EtchedBorder(EtchedBorder.RAISED));
	}

	public void PlayerTable(TableModel m) {
		_playerTable = new JTable(m);
		_playerTable.setDefaultEditor(Object.class, null);
		_playerTable.setPreferredScrollableViewportSize(_playerTable.getPreferredSize());
		_table.setFillsViewportHeight(true);
		_playerTable.setAutoCreateRowSorter(true);
		_playerTable.setBorder(new EtchedBorder(EtchedBorder.RAISED));

	}


	private JPanel createChartPanel() {
		String chartTitle = "Objects Movement Chart";
		String xAxisLabel = "X";
		String yAxisLabel = "Y";

		XYDataset dataset = createDataset();

		chart = ChartFactory.createXYLineChart(chartTitle,
				xAxisLabel, yAxisLabel, dataset);

		return new ChartPanel(chart);
	}



	private XYDataset createDataset() {
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries series1 = new XYSeries("Object 1");

		series1.add(1.0, 2.0);
		series1.add(2.0, 3.0);
		series1.add(3.0, 2.5);
		series1.add(3.5, 2.8);
		series1.add(4.2, 6.0);

		dataset.addSeries(series1);

		return dataset;	}
	private void refreshChart(String PlayerName,String Stat) {
		DefaultTableModel tModel = (DefaultTableModel)_playerTable.getModel();
		final XYSeries series = new XYSeries("PPG");



		for(int i = 0;i<tModel.getRowCount();i++) {
			if(tModel.getValueAt(i,_playerTable.getColumn(Stat).getModelIndex()) instanceof Integer) {
				double A = (int) tModel.getValueAt(i,_playerTable.getColumn(Stat).getModelIndex());
				series.add((int)tModel.getValueAt(i, 0),A);
			}
			else {
				series.add((int)tModel.getValueAt(i, 0),(double)(tModel.getValueAt(i,_playerTable.getColumn(Stat).getModelIndex())));
			}
		}
		final XYSeriesCollection data = new XYSeriesCollection(series);
		final JFreeChart chart2 = ChartFactory.createXYLineChart(
				PlayerName,
				"Age", 
				Stat, 
				data,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
				);
		RightPanel.removeAll();
		RightPanel.revalidate(); // This removes the old chart 
		Shape cross = ShapeUtilities.createDiagonalCross(4, 1);
		XYPlot xyPlot = (XYPlot) chart2.getPlot();
		XYLineAndShapeRenderer renderer =
				(XYLineAndShapeRenderer) xyPlot.getRenderer();
		renderer.setBaseShapesVisible(true);
		renderer.setSeriesShape(0, cross);
		chartPanel = new ChartPanel(chart2);
		RightPanel.setLayout(new BoxLayout(RightPanel, BoxLayout.PAGE_AXIS)); 
		RightPanel.add(chartPanel);
		RightPanel.add(graphBox);
		RightPanel.add(_compareText);
		graphBox.setMaximumSize(getPreferredSize());
		RightPanel.repaint();// This method makes the new chart appear
	}
	private void CompareChart(String PlayerName,String Stat,int B) {
		_playerTable.getModel();
		Stat = this.fieldNameConverter(Stat);
		final XYSeries series = new XYSeries(_table.getValueAt(_table.getSelectedRow(), 0).toString());
		final XYSeries series2 = new XYSeries(PlayerName);
		for(int i = 0;i<this.seasonStats.size();i++) {

			if(i>=B) {
				Number temp = 0;
				try {
					temp = (Number) this.seasonStats.get(i).getClass().getDeclaredField(Stat).get(this.seasonStats.get(i));
					series2.add(this.seasonStats.get(i).Age, temp);
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
						| SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {	
				Number temp = 5;
				try {
					temp = (Number) this.seasonStats.get(i).getClass().getDeclaredField(Stat).get(this.seasonStats.get(i));
					series.add(this.seasonStats.get(i).Age, temp);
				} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException
						| SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		final XYSeriesCollection data = new XYSeriesCollection(series);
		data.addSeries(series2);
		final JFreeChart chart2 = ChartFactory.createXYLineChart(
				PlayerName + " vs " + _table.getValueAt(_table.getSelectedRow(), 0).toString(),
				"Age", 
				Stat, 
				data,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
				);
		RightPanel.removeAll();
		RightPanel.revalidate(); // This removes the old chart 
		Shape cross = ShapeUtilities.createDiagonalCross(4, 1);
		XYPlot xyPlot = (XYPlot) chart2.getPlot();
		XYLineAndShapeRenderer renderer =
				(XYLineAndShapeRenderer) xyPlot.getRenderer();
		renderer.setBaseShapesVisible(true);
		renderer.setSeriesShape(0, cross);
		chartPanel = new ChartPanel(chart2);
		RightPanel.setLayout(new BoxLayout(RightPanel, BoxLayout.PAGE_AXIS)); 
		RightPanel.add(chartPanel);
		RightPanel.add(graphBox);
		RightPanel.add(_compareText);
		graphBox.setMaximumSize(getPreferredSize());
		RightPanel.repaint();// This method makes the new chart appear
	}

	public String fieldNameConverter(String A) {
		if(A.equals("AST")) {
			return "Assit";
		}
		else if(A.equals("MPG")) {
			return "MinutesPlayed";
		}
		else if(A.equals("GP")) {
			return  "GamesPlayed";
		}
		else if(A.equals("GS")) {
			return "GamesStarted";
		}
		else if(A.equals("STL")) {
			return "Steals";
		}
		else if(A.equals("BLK")) {
			return "Blocks";
		}
		else {
			return A;
		}
	}


}
