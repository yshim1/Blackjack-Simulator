package blackjack;

import java.util.ArrayList;
import java.util.Random;

import deckOfCards.*;

public class BlackjackModel {
	private ArrayList<Card> dealerCards;
	private ArrayList<Card> playerCards;
	private Deck d;

	public ArrayList<Card> getDealerCards(){
		ArrayList<Card> de = new ArrayList<Card>(dealerCards);
		return de;
	}
	public ArrayList<Card> getPlayerCards(){
		ArrayList<Card> p = new ArrayList<Card>(playerCards);
		return p;
	}
	public void setDealerCards(ArrayList<Card> cards) {
		dealerCards = cards;
	}
	public void setPlayerCards(ArrayList<Card> cards) {
		playerCards = cards;
	}
	public void createAndShuffleDeck(Random random) {
		d = new Deck();
		d.shuffle(random);
	}
	public void initialDealerCards() {
		dealerCards = new ArrayList<Card>();
		Card one = d.dealOneCard();
		Card two = d.dealOneCard();
		dealerCards.add(one);
		dealerCards.add(two);

	}
	public void initialPlayerCards() {
		playerCards = new ArrayList<Card>();
		Card one = d.dealOneCard();
		Card two = d.dealOneCard();
		playerCards.add(one);
		playerCards.add(two);

	}
	public void playerTakeCard() {
		Card c = d.dealOneCard();
		playerCards.add(c);
	}
	public void dealerTakeCard() {
		Card c = d.dealOneCard();
		dealerCards.add(c);
	}
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand){
		ArrayList<Integer> val = new ArrayList<Integer>();
		int sum = totalFinder(hand); 
		if(hasAce(hand) == true) {
			if(sum + 10 > 21 || sum > 21) {
				val.add(sum);
				return val;
			}
			else if(sum + 10 <= 21) {
				val.add(sum);
				val.add(sum + 10);
				return val;
			}
		}
		val.add(sum);
		return val;
	}
	//		int sum = 0;
	//		ArrayList<Integer> a = new ArrayList<Integer>();
	//		if(hasAce(hand) == false) {
	//			for(Card x: hand) {
	//				sum+= x.getRank().getValue();
	//			}
	//			ArrayList<Integer> h = new ArrayList<Integer>();
	//			h.add(sum);
	//			return h;
	//		}else {
	//			boolean addAce = aceIs11(hand);
	//			for(Card x: hand) {
	//				sum += x.getRank().getValue();
	//				if(addAce == true) {
	//					ArrayList<Integer> h = new ArrayList<Integer>();
	//					int sumTwo = 0;
	//					sumTwo = sum + 10;
	//					h.add(sum);
	//					h.add(sumTwo);
	//					return h;
	//				}
	//			}
	//			for(Card x : hand) {
	//				sum+= x.getRank().getValue();
	//				ArrayList<Integer> h = new ArrayList<Integer>();
	//				h.add(sum);
	//				return h;
	//			}
	//		}
	//		return a;
	//	}
	//	public static boolean aceIs11(ArrayList<Card> hand) {
	//		for(Card x : hand) {
	//			if(hasAce(hand) == true && x.getRank().compareTo(Rank.ACE) != 0) {
	//				int sum = 0;
	//				sum += x.getRank().getValue();
	//				if(sum > 10) {
	//					return false;
	//				}else {
	//					continue;
	//				}
	//			}
	//		}
	//		return true;
	//	}
	public static int totalFinder(ArrayList<Card> hand) {
		int total = 0;
		for(Card x: hand) {
			total += x.getRank().getValue();
		}
		return total;
	}
	public static boolean hasAce (ArrayList<Card> hand) {
		for(Card x: hand) {
			if(x.getRank().getValue() == 1) {
				return true;
			}
		}
		return false;
	}

	public static HandAssessment assessHand(ArrayList<Card> hand) {
		if(hand.isEmpty() || hand.size() < 2) {
			return HandAssessment.INSUFFICIENT_CARDS;
		}
		else if(possibleHandValues(hand).contains(21) && hand.size() == 2) {
			return HandAssessment.NATURAL_BLACKJACK;
		}
		else if(possibleHandValues(hand).get(0) > 21) {
			return HandAssessment.BUST;
		}else {
			return HandAssessment.NORMAL;
		}
	}
	public GameResult gameAssessment() {
		if(assessHand(playerCards).compareTo(HandAssessment.NATURAL_BLACKJACK) == 0 &&
				assessHand(dealerCards).compareTo(HandAssessment.NATURAL_BLACKJACK) != 0){
			return GameResult.NATURAL_BLACKJACK;
		}
		else if(assessHand(playerCards).compareTo(HandAssessment.NATURAL_BLACKJACK) == 0 &&
				assessHand(dealerCards).compareTo(HandAssessment.NATURAL_BLACKJACK) == 0) {
			return GameResult.PUSH;
		}else {
			if(assessHand(playerCards).compareTo(HandAssessment.BUST) == 0) {
				return GameResult.PLAYER_LOST;
			}
			else if(assessHand(playerCards).compareTo(HandAssessment.BUST) != 0 &&
					assessHand(dealerCards).compareTo(HandAssessment.BUST) == 0) {
				return GameResult.PLAYER_WON;
			}
			else if(assessHand(playerCards).compareTo(HandAssessment.BUST) != 0 &&
					assessHand(dealerCards).compareTo(HandAssessment.BUST) != 0) {
				ArrayList <Integer> p = possibleHandValues(playerCards);
				ArrayList <Integer> d = possibleHandValues(dealerCards);
				if(p.get(p.size()-1) > d.get(d.size() - 1)) {
					return GameResult.PLAYER_WON;
				}
				else if(p.get(p.size()-1) < d.get(d.size() -1 )) {
					return GameResult.PLAYER_LOST;
				}else {
					return GameResult.PUSH;
				}
			}
		}
		return GameResult.PUSH;
	}
	public boolean dealerShouldTakeCard() {
		ArrayList <Integer> d = possibleHandValues(dealerCards);
		if(d.get(d.size() - 1) <= 16) {
			return true;
		}
		else if(d.get(d.size() - 1) >= 18) {
			return false;
		}
		else if(d.size() == 2){
			if(d.get(0) == 7 && d.get(d.size() - 1) == 17) {
				return true;
			}
		}
		return false;
	}
}



