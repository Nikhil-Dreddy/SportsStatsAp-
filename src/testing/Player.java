package testing;

public class Player {
	protected String Name;
	protected  String Position;
	protected   int Age;
	protected   String Team;
	protected   int GamesPlayed;
	protected   int GamesStarted;
	protected   String MinutesPlayed;
	protected   String FiedGoals;
	protected   String FieldGoalPercentage;
	protected   String ThreePointers;
	protected   String ThreePA;
	protected   String ThreePercentage;
	protected   String TwoPointers;
	protected   String Twopercentage;
	protected   String eFG;
	protected   String FT;
	protected   String FTA;
	protected   String FTpercentage;
	protected   double ORB;
	protected   double DRB;
	protected   double TRB;
	protected   double Assit;
	protected   String Steals;
	protected   String Blocks;
	protected   double PPG;
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
