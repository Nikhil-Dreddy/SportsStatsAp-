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

public class PlayerStats{
	private Object[][] SeasonPlayerData;
	private ArrayList<Player> PlayerData;
	private String PlayerName;
	private int i;
	private DefaultTableModel Table;
	private ArrayList<Player> CurrentData;
	private String RefName;
	public PlayerStats(ArrayList<Player> CurrentData) {

		this.PlayerData = new ArrayList<Player>();
		this.Table = Table;
		this.SeasonPlayerData = new Object[24][10];
		this.CurrentData = CurrentData;
	}

	protected Object[][] doInBackground() throws Exception {

		for(Player B: CurrentData){
			Vector<Comparable> A = new Vector<Comparable>();
			if(B.Name.equals(PlayerName)) {
				this.RefName = B.RefName;
				break;
			}
		}
		String Url = "http://www.basketball-reference.com/players/c/cartevi01.html";
		Document doc =  Jsoup.connect(Url)
				.userAgent( "mozilla/17.0")
				.timeout(5000).get();
		Elements Table = doc.select("table");
		int i  =0;
		for(Element Row:Table.select("tr")) {
			Elements A = Row.select("td");
			if(A.isEmpty()) {
				continue;
			}
			if(A.get(0).text().isEmpty()) {
				break;
			}
			this.PlayerData.add(new Player(Integer.parseInt(A.get(0).text()),A.get(1).text(),Integer.parseInt(A.get(4).text())
					,Integer.parseInt(A.get(5).text()),Double.parseDouble(A.get(6).text()),
					Double.parseDouble(A.get(24).text()),Double.parseDouble(A.get(28).text()),Double.parseDouble(A.get(23).text())
					,Double.parseDouble(A.get(23).text()),Double.parseDouble(A.get(25).text())));
		}
		for(Player A: this.PlayerData) {
			Vector<Comparable> B = new Vector<Comparable>();
			SeasonPlayerData[i][0] = (A.Age);
			SeasonPlayerData[i][1] = (A.Team);
			SeasonPlayerData[i][2] = (A.GamesPlayed);
			SeasonPlayerData[i][3] = (A.GamesStarted);
			SeasonPlayerData[i][4] = (A.MinutesPlayed);
			SeasonPlayerData[i][5] = (A.Steals);
			SeasonPlayerData[i][6] = (A.Blocks);
			SeasonPlayerData[i][7] = (A.Assit);
			SeasonPlayerData[i][8] = (A.TRB);
			SeasonPlayerData[i][9] = (A.PPG);
			i++;
		}
		return SeasonPlayerData;
	}
}

