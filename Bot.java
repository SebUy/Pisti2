import java.util.ArrayList;
import java.util.Random;

public class Bot extends Player {
    private Random random;

    public Bot(String name) {
        super(name);
        this.random = new Random();
    }

    public Card chooseCard(Board board) { // Logic for bot to choose a suitable card
        Card topCard = board.getTopCard();
        ArrayList<Card> playableCards = getPlayableCards(board, topCard);

        Card chosenCard;
        if (playableCards.isEmpty()) {
            chosenCard = playRandomCard(); // If there are no suitable cards
        } else {
            chosenCard = playableCards.get(0); // Choose the first valid card
        }

        // Ensure the chosen card is directly from the bot's hand
        int cardIndex = getHand().indexOf(chosenCard);
        if (cardIndex == -1) {
            throw new IllegalStateException("Chosen card not found in hand!");
        }

        return playCard(cardIndex); // Play the chosen card
    }

    private ArrayList<Card> getPlayableCards(Board board, Card topCard) {
        ArrayList<Card> playableCards = new ArrayList<>();

        // Priority 1: Check for Pisti
        if (topCard != null && board.isPisti()) {
            for (Card card : getHand()) {
                if (card.getRank() == topCard.getRank()) {
                    playableCards.add(card);
                    return playableCards; // Return immediately if a Pisti card is found
                }
            }
        }

        // Priority 2: Check for matching rank
        if (topCard != null) {
            for (Card card : getHand()) {
                if (card.getRank() == topCard.getRank()) {
                    playableCards.add(card);
                }
            }
        }

        // Priority 3: Check for Jack
        for (Card card : getHand()) {
            if (card.isJack()) {
                playableCards.add(card);
            }
        }

        return playableCards; // Return all valid cards
    }

    private Card playRandomCard() { // Initiated when there are no valid plays
        ArrayList<Card> hand = getHand();
        if (hand.isEmpty()) {
            throw new IllegalStateException("Bot has no cards to play");
        }
        return hand.get(random.nextInt(hand.size())); // Select a random card
    }
}