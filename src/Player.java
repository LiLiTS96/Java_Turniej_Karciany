import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Osoba implements Deck{

	public int round;
	public ArrayList<String> cards;
	public int punk;
	
	public Player(String n, String s, int a, String g, String c, String w, int r,int p) {
		super(n, s, a, g, c, w);
		round = r;
		punk=p;
	}
	
	public void whoAmI(){
		super.whoAmI();
		System.out.println(punk +" "+ round+ "\n");
	}

	public void nameAndSur(){
		super.nameAndSur();
	}
	
	public void printDeck() {
		System.out.println(cards);
	}
	
	public void printScore() {
		System.out.println(punk  + "\n");
	}
	
	public void gratulacja()
	{
		String nazwa = name+" "+surname+".txt";
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(nazwa));
			writer.write(name+ " " +surname+"\n");
			writer.write(city+"\n"+"\n");
			if(gender.equals("K"))
			{
				writer.write("Szanowna Pani ");
			}else
				writer.write("Szanowny Panie ");
			writer.write(name+ " " +surname+"\n");
			writer.write("Gratulujemy zdobycia "); 
			if(round == 9)writer.write("3");
			else if(round == 10)writer.write("2");
			else writer.write("1");
			
			writer.write(" miejsca w naszym turnieju.\n");
			writer.write("Organizator turnieju.\n");
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
