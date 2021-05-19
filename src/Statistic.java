public class Statistic {
	
	public int age;
	public String gender;
	public String wojew;
	public int score;
	public int round;
	
	public Statistic(int a, String g, String w, int s, int r)
	{
		age=a;
		gender=g;
		wojew=w;
		score = s;
		round = r;
	}
	
	public void who() {
		System.out.println(age+" "+gender+" "+wojew+" "+score+" "+round+" ");
	}
	
	public void print() {
		System.out.println(round);
	}
	
	public int getScore()
	{
		return score;
	}
	public int getAge()
	{
		return age;
	}
	public String getGen()
	{
		return gender;
	}
}
