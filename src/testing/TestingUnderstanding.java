package testing;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestingUnderstanding {
	public Object[][] GetPlayerData() throws IOException {
		ArrayList<Player> Player = this.intilazePlayerData();
		Object[][] PlayerData = new Object[Player.size()][9];
		for(int i = 0;i<Player.size();i++) {
			PlayerData[i][0] = Player.get(i).Name;
			PlayerData[i][1] = Player.get(i).Position;
			PlayerData[i][2] = Player.get(i).Age;
			PlayerData[i][3] = Player.get(i).Team;
			PlayerData[i][4] = Player.get(i).GamesPlayed;
			PlayerData[i][5] = Player.get(i).GamesStarted;
			PlayerData[i][6] = Player.get(i).Assit;
			PlayerData[i][7] = Player.get(i).TRB;
			PlayerData[i][8] = Player.get(i).PPG;
		}
		return PlayerData;
	}
	public Object[][] GetTeamData(String Team) throws IOException{
		ArrayList<Player> Player = this.intilazeTeamData(Team);
		Object[][] PlayerData = new Object[Player.size()][9];
		for(int i = 0;i<Player.size();i++) {
			PlayerData[i][0] = Player.get(i).Name;
			PlayerData[i][1] = Player.get(i).Position;
			PlayerData[i][2] = (int)Player.get(i).Age;
			PlayerData[i][3] = Player.get(i).Team;
			PlayerData[i][4] = (int)Player.get(i).GamesPlayed;
			PlayerData[i][5] = (int)Player.get(i).GamesStarted;
			PlayerData[i][6] = (double)Player.get(i).Assit;
			PlayerData[i][7] = (double)Player.get(i).TRB;
			PlayerData[i][8] = (double)Player.get(i).PPG;
		}
		return PlayerData;
	}
	public Integer NumberOfRows() throws IOException {
		Document doc  = Jsoup.connect("http://www.basketball-reference.com/leagues/NBA_2017_per_game.html").userAgent("mozilla/17.0").get();
		Elements Table = doc.select("table");
		int NumberOfRows = 0;
		for(Element table : Table.select("tr")){
			Elements A = table.select("td");
			NumberOfRows++;
		}
		return NumberOfRows;
	}
	public ArrayList<Player> intilazePlayerData() throws IOException{
		Map<String,Player> PlayerMap = new HashMap<String,Player>();
		ArrayList<Player> PlayerData = new ArrayList<Player>();
		Document doc  = Jsoup.connect("http://www.basketball-reference.com/leagues/NBA_2017_per_game.html").userAgent("mozilla/17.0").get();
		Elements Table = doc.select("table");
		for(Element Row:Table.select("tr")) {
			Elements A = Row.select("td");
			if(A.isEmpty()) {
				continue;
			}
			PlayerData.add(new Player(A.get(0).text(),A.get(1).text(),Integer.parseInt(A.get(2).text()),A.get(3).text(),Integer.parseInt(A.get(4).text()),Integer.parseInt(A.get(5).text())
					,Double.parseDouble(A.get(23).text()),Double.parseDouble(A.get(22).text()),Double.parseDouble(A.get(28).text())));
		}
		return PlayerData;
	}
	private ArrayList<Player> intilazeTeamData(String Team) throws IOException {
		ArrayList<Player> PlayerData = new ArrayList<Player>();
		Document doc  = Jsoup.connect("http://www.basketball-reference.com/leagues/NBA_2017_per_game.html").userAgent("mozilla/17.0").get();
		Elements Table = doc.select("table");
		for(Element Row:Table.select("tr")) {
			Elements A = Row.select("td");
			if(A.isEmpty()) {
				continue;
			}
			if(A.get(3).text().equals(Team)||Team.equals("All Teams")) {
				PlayerData.add(new Player(A.get(0).text(),A.get(1).text(),Integer.parseInt(A.get(2).text()),A.get(3).text(),Integer.parseInt(A.get(4).text()),Integer.parseInt(A.get(5).text())
						,Double.parseDouble(A.get(23).text()),Double.parseDouble(A.get(22).text()),Double.parseDouble(A.get(28).text())));
			}
			else {
				continue;
			}
		}
		return PlayerData;
	}
}
