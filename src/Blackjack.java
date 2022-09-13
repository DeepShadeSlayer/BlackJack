import acm.graphics.GLabel;
import acm.program.GraphicsProgram;
import svu.csc213.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Blackjack extends GraphicsProgram {

    // data about the game
    private int wager = 10;
    private int balance = 1000;
    private int bank = 10000;


    // labels to display info to the player
    private GLabel bankLabel, wagerLabel, balanceLabel, blackjack;
    // buttons for controls
    private JButton wagerButton, playButton, hitButton, stayButton, quitButton;

    // objects in the game
    private Deck deck;
    private GHand player, dealer;


    @Override
    public void init() {
        deck = new Deck();

        this.setBackground(Color.RED);

        wagerButton = new JButton("Change Wager");
        add(wagerButton, SOUTH);
        playButton = new JButton("Play");
        add(playButton, SOUTH);
        hitButton = new JButton("Hit");
        add(hitButton, SOUTH);
        stayButton = new JButton("Stay");
        add(stayButton, SOUTH);
        quitButton = new JButton("Quit");
        add(quitButton, SOUTH);

        hitButton.setVisible(false);
        stayButton.setVisible(false);

        addActionListeners();

        // set up GLabels
        balanceLabel = new GLabel("Player Balance: " + balance);
        wagerLabel = new GLabel("Wager: " + wager);
        bankLabel = new GLabel("Bank: " + bank);

        add(wagerLabel, 10, 20);
        add(balanceLabel, 10, wagerLabel.getY() + wagerLabel.getHeight() + 5);
        add(bankLabel, 10, balanceLabel.getY() + wagerLabel.getHeight() + 5);

        Hand playerHand = new Hand(deck, false);
        Hand dealerHand = new Hand(deck, true);
        player = new GHand(playerHand);
        dealer = new GHand(dealerHand);
    }

    @Override
    public void actionPerformed (ActionEvent ae) {
        switch(ae.getActionCommand()) {
            case "Change Wager":
                wager();
                break;
            case "Play":
                play();
                break;
            case "Hit":
                hit();
                break;
            case "Stay":
                stay();
                break;
            case "Quit":
                System.exit(0);
                break;
            default:
                System.out.println("I do not recognize that action command. Check your button text. Stupid");
        }
    }

    private void wager() {
        wager = Dialog.getInteger("What would you like to wager? Minimum of 10.");
        if(wager < 10) {
            wager();
        }
        wagerLabel.setVisible(false);
        wagerLabel.setLabel("Wager: " + wager);
        wagerLabel.setVisible(true);
    }

    private void play() {
        if(wager > balance) {
            Dialog.showMessage("Your wager is higher than your balance. Go change the wager.");
            return;
        }
        if(bank <= 0) {
            Dialog.showMessage("Congratulations");
        }
        playButton.setVisible(false);
        wagerButton.setVisible(false);
        deck.shuffle();
        add(player, getWidth()/2 - player.getWidth()/2, 250);
        add(dealer, getWidth()/2 - dealer.getWidth()/2, 50);
        hitButton.setVisible(true);
        stayButton.setVisible(true);
    }

    private void hit() {
        player.hit();
        player.setLocation(getWidth()/2 - player.getWidth()/2, 250);
        if(player.getTotal() > 21) {
            lose();
        }
    }

    private void stay() {
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        dealer.flipCard(0);
        while(dealer.getTotal() < 17) {
            dealer.hit();
            dealer.setLocation(getWidth()/2 - dealer.getWidth()/2, 50);
        }
        if(dealer.getTotal() > 21) {
            win();
        } else if((player.getTotal() > dealer.getTotal())) {
            win();
        } else if(dealer.getTotal() > player.getTotal()) {
            lose();
        } else if(dealer.getTotal() == player.getTotal()) {
            tie();
        }
    }

    private void win() {
        Dialog.showMessage("You Won!");
        balance += wager;
        bank -= wager;
        balanceLabel.setLabel("Player Balance: " + balance);
        bankLabel.setLabel("Bank: " + bank);
        Dialog.showMessage("Your balance is now " + balance);
        reset();
    }
    private void lose() {
        Dialog.showMessage("You Lost!");
        balance -= wager;
        bank += wager;
        bankLabel.setLabel("Bank: " + bank);
        if(balance <= 0) {
            Dialog.showMessage("You have lost all your money. Good job!");
            System.exit(0);
        }
        balanceLabel.setLabel("Player Balance: " + balance);
        Dialog.showMessage("Your balance is now " + balance);
        remove(player);
        remove(dealer);
        reset();
    }
    private void tie() {
        Dialog.showMessage("You tied. Do better");
        reset();
    }

    private void reset() {
        remove(player);
        remove(dealer);
        player = new GHand(new Hand(deck, false));
        dealer = new GHand(new Hand(deck, true));
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        playButton.setVisible(true);
        wagerButton.setVisible(true);
    }

    public static void main(String[] args) {
        new Blackjack().start();
    }
}