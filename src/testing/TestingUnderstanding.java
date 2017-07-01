package testing;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestingUnderstanding {
	public static void main(String args[]) {
		
		try {
			Document doc  = Jsoup.connect("http://basketball.realgm.com/nba/stats").userAgent("mozilla/17.0").get();
			Elements temp = doc.select("div.overall-leader");
			for(Element Player: temp) {
				System.out.println(" "+Player.getElementsByTag("a").text());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
