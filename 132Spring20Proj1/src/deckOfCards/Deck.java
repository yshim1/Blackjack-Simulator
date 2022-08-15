package deckOfCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Deck {
	private ArrayList<Card> c;

	public Deck() {
		c = new ArrayList<Card>();
		for(Suit a : Suit.values()) {
			for(Rank b : Rank.values()) {
				c.add(new Card(b, a));
			}
		}
	}
	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(c , randomNumberGenerator);
	}
	public Card dealOneCard() {
		return c.remove(0);
	}
}
