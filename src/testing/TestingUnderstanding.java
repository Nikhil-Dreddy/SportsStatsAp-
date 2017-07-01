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
	public static void main(String args[]) {
		Map<String,Player> PlayerMap = new HashMap<String,Player>();
		ArrayList<Player> PlayerData = new ArrayList<Player>();
		try {
			Document doc  = Jsoup.connect("http://www.basketball-reference.com/leagues/NBA_2017_per_game.html").userAgent("mozilla/17.0").get();
			Elements Table = doc.select("table");;
			String[] InfoHolder = new String[16];
			int Counter = 1;
			for(Element Row:Table.select("tr")) {
				if(Row.text().equals("Rk Player Pos Age Tm G GS MP FG FGA FG% 3P 3PA 3P% 2P 2PA 2P% eFG% FT FTA FT% ORB DRB TRB AST STL BLK TOV PF PS/G" )){
					continue;
				}
				System.out.println(" "+Row.text());
				Elements A = Row.select("td");
				PlayerMap.put(A.get(0).text(),new Player(A.get(0).text(),A.get(1).text(),A.get(2).text(),A.get(3).getElementsByTag("a").text(),A.get(4).text(),A.get(5).text(),A.get(24).text(),A.get(23).text(),A.get(28).text()));
				PlayerData.add(new Player(A.get(0).text(),A.get(1).text(),A.get(2).text(),A.get(3).text(),A.get(4).text(),A.get(5).text(),A.get(24).text(),A.get(23).text(),A.get(28).text()));
				if(Row.text().equals("Rk Player Pos Age Tm G GS MP FG FGA FG%")|Counter == 100) {
					break;
				}
				Counter++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0;i<PlayerData.size();i++) {
			if( i == 0) {
				System.out.println("Name Pos Age Team GP GS AST TRB PPG");
			}
			System.out.println(PlayerData.get(i).toString());
		}
	}
}
