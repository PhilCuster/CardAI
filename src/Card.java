public class Card {

    public enum Value { TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), 
	    EIGHT("8"), NINE("9"), TEN("10"), JACK("Jack"), QUEEN("Queen"), KING("King"), ACE("Ace");
	private String name;
	Value(String name) { this.name = name; }
	public String toString() { return name; }
    }

    public enum Suit { CLUBS("clubs"), DIAMONDS("diamonds"), HEARTS("hearts"), SPADES("spades");
	private String name;
	Suit(String name) { this.name = name; }
	public String toString() { return name; }
    }

    private static Value[] allValues = Value.values();
    private static Suit[] allSuits = Suit.values();
    public Value value;
    public Suit suit;

    public Card(Value value, Suit suit) {
	this.value = value; this.suit = suit; 
    }

    public Card(int i, int j) {
	this(allValues[i], allSuits[j]);
    }

    public String toString() { return "" + value + " of " + suit + " (" + getNumber() + ")"; }

    public int getNumber() { return value.ordinal() * 4 + suit.ordinal(); }

    public boolean greater(Card c, Card trump) {
	// Assumes this and c are not equal.
	return suit == c.suit ? value.compareTo(c.value) > 0 : c.suit != trump.suit;
    }
}
