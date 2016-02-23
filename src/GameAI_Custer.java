import java.util.ArrayList;
import java.util.List;

public class GameAI_Custer implements Player {

    int numHands = 1;

    public Card playFirstCard(ArrayList<Card> hand, ArrayList<Card> playedCards, Card trump, int tricks1, int tricks2) {
        Card cardToPlay = null;
        for(int i = 0; i < numHands; i++) {
            ArrayList<Card> opponentHand = generateHand(hand, playedCards, trump);
            Node origin = new Node(hand, playedCards, trump, tricks1, tricks2, opponentHand);



            alphaBeta(origin, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            // Find child with highest v-score.
            int v = Integer.MIN_VALUE;
            for (Node child: origin.children) {
                if (child.v > v) {
                    cardToPlay = child.playedCards.get(child.playedCards.size()-1);
                }
            }

        }
        return cardToPlay;
    }

    public Card playSecondCard(ArrayList<Card> hand, ArrayList<Card> playedCards, Card trump, int tricks1, int tricks2) {
        int lowestScore = Integer.MAX_VALUE; // The lowest value found so far, this being the one MIN would play.
        int highestScore = Integer.MIN_VALUE; // The highest score we have seen, the one we want to do.
        Card bestMove = null; // The move we want to make.




        //temporary
        return null;
    }

    private int alphaBeta(Node node, int alpha, int beta, boolean maxPlayer) {
        if(terminalTest(node.tricks1, node.tricks2)) {
            return node.tricks1 - node.tricks2; // Value of node: ie. end state is we win four tricks while over player wins 1 so the value would be 3.
        }

        int v;
        if (maxPlayer) {
            v = Integer.MIN_VALUE;
            // For each card in hand, each move you can make...
            for(Card c: node.hand) {
                // Generate new board state.
                ArrayList<Card> newHand = new ArrayList<Card>(node.hand.size());
                for (Card card: node.hand) {
                    newHand.add(card);
                }
                newHand.remove(c);
                ArrayList<Card> newPlayedCards = new ArrayList<Card>(node.playedCards.size());
                for (Card card: node.playedCards) {
                    newPlayedCards.add(card);
                }
                newPlayedCards.add(c);
                ArrayList<Card> newOppHand = new ArrayList<Card>(node.opponentHand.size());
                for (Card card: node.opponentHand) {
                    newOppHand.add(card);
                }

                Node child = new Node(newHand, newPlayedCards, node.trump, node.tricks1, node.tricks2, newOppHand);
                node.children.add(child);

                v = Math.max(v, alphaBeta(child, alpha, beta, false));

                alpha = Math.max(alpha, v);
                if (beta <= alpha) {
                    break;
                }
            }
            node.setV(v);
            return v;
        }

        else {
            v = Integer.MAX_VALUE;

            for(Card c: node.opponentHand) {
                // Generate new board state.
                ArrayList<Card> newHand = new ArrayList<Card>(node.hand.size());
                for (Card card: node.hand) {
                    newHand.add(card);
                }
                ArrayList<Card> newPlayedCards = new ArrayList<Card>(node.playedCards.size());
                for (Card card: node.playedCards) {
                    newPlayedCards.add(card);
                }
                newPlayedCards.add(c);
                ArrayList<Card> newOppHand = new ArrayList<Card>(node.opponentHand.size());
                for (Card card: node.opponentHand) {
                    newOppHand.add(card);
                }
                newOppHand.remove(c);
                // Check to see who wins trick.
                int newTricks1 = node.tricks1;
                int newTricks2 = node.tricks2;
                if (c.greater(node.playedCards.get(node.playedCards.size()-1), node.trump)) {
                    newTricks2++;
                }
                else {
                    newTricks1++;
                }
                Node child = new Node(newHand, newPlayedCards, node.trump, newTricks1, newTricks2, newOppHand);
                node.children.add(child);

                v = Math.min(v, alphaBeta(child, alpha, beta, true));

                beta = Math.min(beta, v);
                if (beta <= alpha) {
                    break;
                }
            }
            node.setV(v);
            return v;
        }
    }

    private boolean terminalTest(int tricks1, int tricks2) {
        if (tricks1 == 4 || tricks2 == 4) {
            return true;
        }
        else {
            return false;
        }
    }

    private ArrayList<Card> generateHand(ArrayList<Card> hand, ArrayList<Card> played, Card trump) {
        ArrayList<Card> randomHand = new ArrayList<Card>();
        Card tempCard;
        Deck deck = new Deck();
        for (Card c: played) {
            hand.add(c);
        }
        hand.add(trump);// Hand is now a list of all cards to be omitted.
        int i = 0;
        while(randomHand.size() != 7) {
            tempCard = deck.get(i);
            if (!hand.contains(tempCard)) {
                randomHand.add(tempCard);
            }
        }
        return randomHand;
    }
}

class Node {
    ArrayList<Card> hand;
    ArrayList<Card> playedCards;
    ArrayList<Card> opponentHand;
    Card trump;
    int tricks1;
    int tricks2;
    ArrayList<Node> children;
    int v;

    Node(ArrayList<Card> _hand, ArrayList<Card> _playedCards, Card _trump, int _tricks1, int _tricks2, ArrayList<Card> _opponentHand) {
        hand = _hand;
        playedCards = _playedCards;
        trump = _trump;
        tricks1 = _tricks1;
        tricks2 = _tricks2;
        opponentHand = _opponentHand;
        ArrayList<Node> children = new ArrayList<Node>();
    }

    void setV(int _v) {
        v = _v;
    }
}