package testing;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.io.FileReader;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import java.io.FileNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PlayerStats extends SwingWorker<Void,Vector>{
	private Object[][] SeasonPlayerData;
	private ArrayList<Player> PlayerData;
	private String PlayerName;
	private int i;
	private DefaultTableModel Table;
	public PlayerStats(String PlayerName,DefaultTableModel Table) {
		this.PlayerName = PlayerName;
		this.PlayerData = new ArrayList<Player>();
		this.Table = Table;
		this.SeasonPlayerData = new Object[10][8];
	}

	protected Void doInBackground() throws Exception {
		String[] parts = this.PlayerName.split(" ", 2);
		String string1 = parts[0];
		String string2 = parts[1];
		String string3 = parts[0]+"+"+parts[1];
		Document doc =  Jsoup.connect("http://basketball.realgm.com/search?q="+string3+ "&num=20").userAgent( "mozilla/17.0")
				.timeout(5000).get();
		Elements Table = doc.select("table");
		this.i = 0;

		
			for(Element Row:Table.select("tr")) {
				Elements A = Row.select("td");
				if(A.isEmpty()) {
					continue;
				}
				if(A.get(0).text().equals("CAREER")|| this.i == 1) {
					this.i = 1;
					break;
				}
				int B = Integer.parseInt(A.get(3).text());
				this.PlayerData.add(new Player(A.get(0).text(),A.get(1).text(),Integer.parseInt(A.get(2).text())
						,Integer.parseInt(A.get(3).text()),Double.parseDouble(A.get(4).text()),
						Double.parseDouble(A.get(17).text()),Double.parseDouble(A.get(16).text()),Double.parseDouble(A.get(22).text())
						,Double.parseDouble(A.get(18).text()),Double.parseDouble(A.get(19).text())));
			}
			for(Player A: this.PlayerData) {
				Vector<Comparable> B = new Vector<Comparable>();
				B.add(A.Season);
				B.add(A.Team);
				B.add(A.GamesPlayed);
				B.add(A.GamesStarted);
				B.add(A.MinutesPlayed);
				B.add(A.Steals);
				B.add(A.Blocks);
				B.add(A.Assit);
				B.add(A.TRB);
				B.add(A.PPG);
				publish(B);
			}
			return null;
	}
	protected void process(List<Vector> rowsList)
	{
		for(Vector row : rowsList){
			DefaultTableModel tModel = (DefaultTableModel)this.Table;
			tModel.addRow(row);
			tModel.fireTableDataChanged();
		}
}
}

