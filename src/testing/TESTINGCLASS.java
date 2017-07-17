package testing;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TESTINGCLASS {

	private Document A;
	private ArrayList<Player> PlayerData;
	private DefaultTableModel Table;
	public TESTINGCLASS(Document A,ArrayList<Player> PlayerData, DefaultTableModel Table2) {
		this.A = A;
		this.PlayerData = new ArrayList<Player>();
		this.Table = Table2;
	}
	

	public void execute() {
		Elements Table = this.A.select("table");
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
			publish(B);
		}
	}


	private void publish(Vector<Comparable> b) {
		DefaultTableModel tModel = (DefaultTableModel)this.Table;
		tModel.addRow(b);
		tModel.fireTableDataChanged();
	}
}
