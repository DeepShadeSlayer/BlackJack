import acm.graphics.GCompound;

public class GHand extends GCompound {

    private Hand hand;
    private GCard[] gCards;

    public GHand(Hand hand) {
        this.hand = hand;

        gCards = new GCard[7];

        for (int i = 0; i < hand.getCount(); i++) {
            Card card = hand.getCard(i);
            GCard gCard = new GCard(card);

            add(gCard, i * gCard.getWidth()*1.25, 0);
            gCards[i] = gCard;
        }
    }

    // get the total value of the hand
    public int getTotal() {
        return hand.getTotal();
    }
    // flip the first card over (dealer only)
    public void flipCard(int index) {
        gCards[index].flip();
    }
    // draw a card from the deck (this is called  a "hit")
    public void hit() {
        hand.hit();

        Card temp = hand.getCard(hand.getCount() - 1);
        GCard newCard = new GCard(temp);

        gCards[hand.getCount() - 1] = newCard;

        add(newCard, (hand.getCount() - 1) * (newCard.getWidth()*1.25), 0);
    }
}
