package testing;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TestingUnderstanding {
	private ArrayList<Player> PlayerData = new ArrayList<Player>();
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
	public ArrayList<Player> intilazePlayerData() throws IOException{
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		String[] Lines = new String[50];
		try {
			
			InputStream in = getClass().getResourceAsStream("/res/2016-2017 Stats.csv"); 
			br = new BufferedReader(new InputStreamReader(in));

			Lines[0] = "1";
			//Processing Data only after we read the line #Data 
			while ((line = br.readLine()) != null && !Lines[0].equals("Rk")) {
				// using tab as a separator
				Lines = line.split(cvsSplitBy);
			}
			while((line = br.readLine()) != null) {
				// using tab as a separator
				Lines = line.split(cvsSplitBy);
				String refname = Lines[1].substring(Lines[1].indexOf('\\')+1, Lines[1].length());
				String playername = Lines[1].substring(0, Lines[1].indexOf('\\'));
				PlayerData.add(new Player(playername,Lines[2],Integer.parseInt(Lines[3]),Lines[4],Integer.parseInt(Lines[5]),Integer.parseInt(Lines[6]),
						Double.parseDouble(Lines[24]),Double.parseDouble(Lines[23]),Double.parseDouble(Lines[29]),refname));
			}

		} catch (FileNotFoundException e) {
			// Catches our exception 
			e.printStackTrace();
		} catch (IOException e) {
			// Catches our exception
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					// close the data file 
					br.close();
				} catch (IOException e) {
					// if it can't catches the exception
					e.printStackTrace();
				}
			}
		}
		// add elements to al, including duplicates
		return this.PlayerData;
	}
	
	

}
