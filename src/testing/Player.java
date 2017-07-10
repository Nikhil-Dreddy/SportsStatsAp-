package testing;

public class Player {
	public String Name;
	public String Position;
	public  int Age;
	public  String Team;
	public  int GamesPlayed;
	public  int GamesStarted;
	public  String MinutesPlayed;
	public  String FiedGoals;
	public  String FieldGoalPercentage;
	public  String ThreePointers;
	public  String ThreePA;
	public  String ThreePercentage;
	public  String TwoPointers;
	public  String Twopercentage;
	public  String eFG;
	public  String FT;
	public  String FTA;
	public  String FTpercentage;
	public  double ORB;
	public  double DRB;
	public  double TRB;
	public  double Assit;
	public  String Steals;
	public  String Blocks;
	public  double PPG;
	public Player(String Name,String Pos,int Age,String Tm,int G,int GS,String MP,String FG,String FG2,String three,String threePA,String threepercent,int TRB,int AST,String Steal,String BLK) {
		this.Name = Name;
		this.Position = Pos;
		this.Age = Age;
		this.Team= Tm;
		this.GamesPlayed = G;
		this.GamesStarted = GS;
		this.MinutesPlayed = MP;
		this.FiedGoals = FG;
		this.FieldGoalPercentage = FG2;
		this.ThreePointers = three;
		this.ThreePA = threePA;
		this.ThreePercentage=threepercent;
		this.TRB = TRB;
		this.Assit = AST;
		this.Steals = Steal;
		this.Blocks = BLK;
	}
	public Player(String Name,String Pos,int Age,String Tm,int G,int GS,double AST,double TRB,double PPG) {
		this.Name = Name;
		this.Position = Pos;
		this.Team = Tm;
		this.Age = Age;
		this.GamesPlayed = G;
		this.GamesStarted = GS;
		this.Assit = AST;
		this.TRB = TRB;
		this.PPG = PPG;
	}
	@Override
	public String toString() {
		String A = this.Name+" "+ this.Position+" "+this.Age+" "+this.Team+" "+this.GamesPlayed+" "+this.GamesStarted+" "+this.Assit+" "+this.TRB+" "+this.PPG;	
		return A;
	}
}
