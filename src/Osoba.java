import java.time.LocalDate;

public class Osoba implements Deck{

	public String name;
	public String surname;
	public int age;
	public String gender;
	public String city;
	public String wojew;
	
	public Osoba(String n, String s, int a, String g, String c, String w)
	{
		name=n;
		surname=s;
		age=a;
		gender=g;
		city=c;
		wojew=w;
	}
	
	public void whoAmI() {
		System.out.print(name+" "+surname+" "+age+" "+gender+" "+city+" "+wojew+" ");	
	}
	
	public void nameAndSur() {
		System.out.print(name+" "+surname+"\n");	
	}
}
