import java.util.ArrayList;
import java.util.Collections;

public class WatekDoGry implements Deck, Runnable{

	
	public WatekDoGry(Player p1, Player p2) {
		super();
					
					Collections.shuffle(deck);
					
					int size = deck.size();
					
					p1.cards = new ArrayList<>(deck.subList(0, (size + 1)/2));
					p2.cards = new ArrayList<>(deck.subList((size + 1)/2, size));				
					
					p1.punk = punkty(p1.cards);
					p2.punk = punkty(p2.cards);
					
					if(p1.punk > p2.punk) {
						p1.round++; 
					}else if(p2.punk > p1.punk) {
						p2.round++; 
					}else {
						if (p1.cards.contains("AS"))
						{
							p1.round++;
						}else {p2.round++;}
					}
					
				}
				
				static int punkty(ArrayList<String> lista) {
					int count = 0;
					int punkty = 0;
					String aaa;
					
					while(lista.size() > count) {
						
						aaa = lista.get(count);
						aaa = aaa.substring(0, aaa.length() - 1);

						if(aaa.equals("W")) {
							punkty = punkty + 11;
						}else if (aaa.equals("D")) {
							punkty = punkty + 12;
						}else if (aaa.equals("K")) {
							punkty = punkty + 13;
						}else if (aaa.equals("A")) {
							punkty = punkty + 14;
						}else {
							punkty = punkty + Integer.parseInt(aaa);
						}
						
						count++;
					}
					
					return punkty;
				}
	
	@Override
	public void run() {
	
	}	
}
