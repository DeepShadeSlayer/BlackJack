public class Hand {
    private final Card[] CARDS;
    private int count;
    private final Deck DECK;

    public Hand(Deck deck, boolean isDealer) {
        this.DECK = deck;
        CARDS = new Card[7];
        CARDS[0] = deck.deal();
        CARDS[1] = deck.deal();
        count = 2; // number of cards this hand has

        if (isDealer) {
            CARDS[0].setFaceUp(false);
        }
    }

        public int getTotal() {
            int total = 0;
            int aces = 0;
            for (int i = 0; i < count; i++) {
                total += CARDS[i].getValue();
                if(CARDS[i].getFace() == Card.Face.ACE) {
                    aces++;
                }
            }
            while (total > 21 && aces > 0) {
                total -= 10;
                aces--;
            }
            return total;
        }

        public void hit(){
            CARDS[count++] = this.DECK.deal();

        }

        public Card getCard(int pos){
            return CARDS[pos];
        }

        public int getCount(){
            return this.count;
        }

}
