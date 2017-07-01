package testing;

public class Player {
	protected String Name;
	protected String Position;
	protected String Age;
	protected String Team;
	protected String GamesPlayed;
	protected String GamesStarted;
	protected String MinutesPlayed;
	protected String FiedGoals;
	protected String FieldGoalPercentage;
	protected String ThreePointers;
	protected String ThreePA;
	protected String ThreePercentage;
	protected String TwoPointers;
	protected String Twopercentage;
	protected String eFG;
	protected String FT;
	protected String FTA;
	protected String FTpercentage;
	protected String ORB;
	protected String DRB;
	protected String TRB;
	protected String Assit;
	protected String Steals;
	protected String Blocks;
	protected String PPG;
	public Player(String Name,String Pos,String Age,String Tm,String G,String GS,String MP,String FG,String FG2,String three,String threePA,String threepercent,String TRB,String AST,String Steal,String BLK) {
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
	public Player(String Name,String Pos,String Age,String Tm,String G,String GS,String AST,String TRB,String PPG) {
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
