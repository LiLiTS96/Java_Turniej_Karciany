import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
public class main implements Deck{	
	
	public static void main(String args[]) {
		ArrayList <Player> graczy = new ArrayList<>();
		ArrayList <Statistic> statFirstRound = new ArrayList<>();
		Statistic st = new Statistic(0,"","",0,0);
		String tmpline = null;
		try {
			
			
			BufferedReader reader = new BufferedReader(new FileReader("daneBZ.txt"));
			try {
				tmpline = reader.readLine();
				tmpline = tmpline.substring(3);
				String name, sur, plec,city,wojew;
				int wiek;
				//PIERWSZY GRACZ
				String[] parts = tmpline.split(";");
				name = parts[0];
				sur = parts[1];
				wiek = Integer.parseInt(parts[2]);
				plec = parts[3];
				city = parts[4];
				wojew = parts[5];
				
				Player p1 = new Player(name,sur,wiek,plec,city,wojew,1,0);
				graczy.add(p1);
				
				///LISTA GRACZY
				while((tmpline = reader.readLine())!=null)
				{
					parts = tmpline.split(";");
					name = parts[0];
					sur = parts[1];
					wiek = Integer.parseInt(parts[2]);
					plec = parts[3];
					city = parts[4];
					wojew = parts[5];
					
					p1 = new Player(name,sur,wiek,plec,city,wojew,1,0);
					graczy.add(p1);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		
		
		Collections.shuffle(graczy);
		
		//LISTA WATKOW NA JEDNA RUNDE
		List<Callable<Object>> list = new ArrayList<>();		
		
		//TURNIEJ
		int runda = 1; 
		while(runda < 11) {
			
			Collections.shuffle(graczy);
			
			System.out.print("\n RUNDA "+ runda+"\n");
			//UZUPEWNIENIE LISTY WATKOW PARAMI
			int i=0;
			while(graczy.size()>i) {
				list.add(Executors.callable(new WatekDoGry(graczy.get(i), graczy.get(i+1))));
				i = i+2;
			}
			
			//WYLOWANIE LISTY WATKOW
			ExecutorService e = Executors.newFixedThreadPool(list.size());
			try {
				e.invokeAll(list);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
	
			//TWORZENIE LISTY WINNERS	
			ArrayList <Player> winners = new ArrayList<>();
			
			for(int j=0; j<graczy.size(); j++)
			{
				if(graczy.get(j).round == runda+1)
				{
					winners.add(graczy.get(j));
				}
					
			}
			
			if(runda == 1)
			{
				for(int j=0; j<graczy.size(); j++)
				{
					st = new Statistic(graczy.get(j).age,graczy.get(j).gender,graczy.get(j).wojew,graczy.get(j).punk,graczy.get(j).round);
					statFirstRound.add(st);
				}
			}
				
			
			if(runda == 9)
			{
				
				System.out.print("\n ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
				for(int j=0; j<graczy.size(); j++)
				{		
					graczy.get(j).nameAndSur();
					graczy.get(j).printDeck();
					graczy.get(j).printScore();	
				}
				System.out.print("\n ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
					
				for(int j=0; j<graczy.size(); j++)
				{			
					if(graczy.get(j).round == runda)
					{
						graczy.get(j).gratulacja();
					}			
				}
			}
			
			
			if(runda > 9 )
			{
				
				System.out.print("\n ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
				for(int j=0; j<graczy.size(); j++)
				{		
					graczy.get(j).nameAndSur();
					graczy.get(j).printDeck();
					graczy.get(j).printScore();	
				}
				System.out.print("\n ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
				
				
				for(int j=0; j<graczy.size(); j++)
				{			
						graczy.get(j).gratulacja();			
				}
				
			}
			//PRZEPISANIE LISTY WINNERS DO LISTY GRACZY DLA NOWEJ RUNDY
			graczy = winners;
			
				runda ++;
		}
		
		System.out.println("1:");
		//srednia ilosc punktow uzyskanych przez graczy wedlug wojewodztw.
		statFirstRound.stream().collect(Collectors.groupingBy(Statistic -> Statistic.wojew, Collectors.averagingInt(Statistic -> Statistic.score))).forEach((wojew, avgScore)->System.out.println(wojew+": "+(int) Math.round(avgScore)));
		
		
		
		
		System.out.println("2:");
		//Mediana ilosci punktow uzyskanych przez kobiety i mezczyzn.
		
		double a = (statFirstRound.stream().filter(p -> (p.gender.contains("M"))).toArray().length % 2 == 0?
						statFirstRound.stream().filter(p -> (p.gender.contains("M"))).mapToInt(Statistic::getScore).sorted().skip(statFirstRound.size() / 2-1).limit(2).average().getAsDouble():
							statFirstRound.stream().filter(p -> (p.gender.contains("M"))).mapToInt(Statistic::getScore).sorted().skip(statFirstRound.size() / 2).findFirst().getAsInt());
							
		System.out.println("Man: " + a);
		
		double b = (statFirstRound.stream().filter(p -> (p.gender.contains("K"))).toArray().length % 2 == 0?
				statFirstRound.stream().filter(p -> (p.gender.contains("K"))).mapToInt(Statistic::getScore).sorted().skip(statFirstRound.size() / 2-1).limit(2).average().getAsDouble():
					statFirstRound.stream().filter(p -> (p.gender.contains("K"))).mapToInt(Statistic::getScore).sorted().skip(statFirstRound.size() / 2).findFirst().getAsInt());
					
		System.out.println("Woman: " + b);
		
		System.out.println("3:");
		//Maksymalna i minimalna ilosc punktow wsrod osob, ktore przeszly do drugiej rundy.
		Statistic min = statFirstRound.stream().filter(p->(p.round==2)).min(Comparator.comparing(Statistic::getScore)).orElseThrow(NoSuchElementException::new);
		System.out.println("Min: "+min.score);
		
		Statistic max = statFirstRound.stream().filter(p->(p.round==2)).max(Comparator.comparing(Statistic::getScore)).orElseThrow(NoSuchElementException::new);
		System.out.println("Max: "+max.score);
		
		
		
		System.out.println("4:");			
		Integer do18 = statFirstRound.stream().filter(p -> (p.age >= 0))
				.filter(p -> (p.age <= 18)).mapToInt(Statistic::getScore).sum();
		Integer do30 = statFirstRound.stream().filter(p -> (p.age > 18))
				.filter(p -> (p.age <= 30)).mapToInt(Statistic::getScore).sum();
		Integer do55 = statFirstRound.stream().filter(p -> (p.age > 30))
				.filter(p -> (p.age <= 55)).mapToInt(Statistic::getScore).sum();
		Integer do100 = statFirstRound.stream().filter(p -> (p.age > 55))
				.filter(p -> (p.age <= 100)).mapToInt(Statistic::getScore).sum();
		System.out.println("(0,18): "+do18);
		System.out.println("[18,30): "+do30);
		System.out.println("[30,55): "+do55);
		System.out.println("[55,100): "+do100);
		
		
		
		System.out.print("Koniec");
		
	}	
}
