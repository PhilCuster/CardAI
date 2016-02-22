import java.util.ArrayList;
import java.util.Arrays;

public class Tournament {

    private static class Contestant implements Comparable<Contestant> {
	Player player;
	String name;
	int wins;

	public Contestant(Player player, String name) {
	    this.player = player; this.name = name; wins = 0;
	}
	public int compareTo(Contestant other) { return wins - other.wins; }
	public void won() { wins++; }
	public String toString() { return name + "(" + wins + ")"; }
    }

    private static final Contestant[] contestants = {
	new Contestant(RandyP.getPlayer(), "RandyP"),
	/*
	new Contestant(DonaldS.getPlayer(), "DonaldSecond"),
	new Contestant(AlexanderW.getPlayer(), "AlexanderW"),
	new Contestant(ArianaL.getPlayer(), "ArianaL"),
	new Contestant(BradyS.getPlayer(), "BradyS"),
	new Contestant(BrianA.getPlayer(), "BrianA"),
	new Contestant(DanielS.getPlayer(), "DanielS"),
	new Contestant(DanielW.getPlayer(), "DanielW"),
	new Contestant(DerekP.getPlayer(), "DerekP"),
	new Contestant(DonovanR.getPlayer(), "DonovanR"),
	new Contestant(JasonA.getPlayer(), "JasonA"),
	new Contestant(JoelM.getPlayer(), "JoelM"),
	new Contestant(KinardiI.getPlayer(), "KinardiI"),
	new Contestant(MatthewB.getPlayer(), "MatthewB"),
	new Contestant(MatthewS.getPlayer(), "MatthewS"),
	new Contestant(NicholasM.getPlayer(), "NicholasM"),
	new Contestant(PhilipH.getPlayer(), "PhilipH"),
	*/
	new Contestant(PhillipC.getPlayer(), "PhillipC"),
	/*
	new Contestant(SeanV.getPlayer(), "SeanV"),
	new Contestant(ThomasV.getPlayer(), "ThomasV"),
	new Contestant(TimothyI.getPlayer(), "TimothyI"),
	new Contestant(TravisG.getPlayer(), "TravisG"),
	new Contestant(TylerG.getPlayer(), "TylerG"),
	new Contestant(VincentI.getPlayer(), "VincentI"),
	new Contestant(YujieZ.getPlayer(), "YujieZ"),
	new Contestant(YulderA.getPlayer(), "YulderA"),
	*/
    };

    private static void playHand(Contestant c1, Contestant c2) {
	Deck deck = new Deck();
	deck.shuffle();
	int numCards = 7;  // deal two hands of 7 cards and trump card
	ArrayList<Card> hand1 = new ArrayList<Card>();
	ArrayList<Card> hand2 = new ArrayList<Card>();
	ArrayList<Card> playedCards = new ArrayList<Card>();
	for(int i=0; i<numCards; i++) {
	    hand1.add(deck.get(2*i));
	    hand2.add(deck.get(2*i+1));
	}
	Card trump = deck.get(2*numCards);
	int tricks1 = 0, tricks2 = 0;
	System.out.println("Trump = " + trump + "\n" + c1.name + ": " + hand1 + "\n" + c2.name + ": " + hand2);
	while(!hand2.isEmpty()) {  // play hand
	    Card card1 = c1.player.playFirstCard(hand1, playedCards, trump, tricks1, tricks2);
	    if(hand1.indexOf(card1) == -1) { tricks1 = 0; tricks2 = 7; break; } // forfeit
	    hand1.remove(card1);
	    playedCards.add(card1);
	    Card card2 = c2.player.playSecondCard(hand2, playedCards, trump, tricks1, tricks2);
	    if(hand2.indexOf(card2) == -1) { tricks1 = 7; tricks2 = 0; break; } // forfeit
	    boolean canFollow = false;
	    for(Card c : hand2)
		if(c.suit == card1.suit)
		    canFollow = true;
	    if(canFollow && card2.suit != card1.suit && card2.suit != trump.suit) 
		{ tricks1 = 7; tricks2 = 0; break; } // forfeit
	    hand2.remove(card2);
	    playedCards.add(card2);
	    if(card1.greater(card2, trump))
		tricks1++; 
	    else 
		tricks2++;
	}
	if(tricks1 > 3)
	    { c1.won(); System.out.println(c1.name + " wins the hand."); }
	else
	    { c2.won(); System.out.println(c2.name + " wins the hand."); }
    }
    
    public static void main(String[] args) {
	int numMatches = 100;
	int k = 0;
	for(int i=0; i<contestants.length; i++)
	    for(int j=i+1; j<contestants.length; j++) {
		System.out.println(contestants[i].name + " vs. " + contestants[j].name);
		for(int n=0; n<numMatches; n++) {
		    System.out.print((++k) + ": ");
		    playHand(contestants[i], contestants[j]);
		    playHand(contestants[j], contestants[i]);
		    System.out.println(contestants[i] + " " + contestants[j]);
		}
	    }
	Arrays.sort(contestants);
	for(int i=0; i<contestants.length; i++)
	    System.out.println(contestants[i]);
    }
}