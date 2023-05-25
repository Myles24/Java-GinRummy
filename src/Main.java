import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // sets up the Player objects and other instance variables - Carson
    private static boolean turn = true;
    private static PlayerHand p1;
    private static PlayerHand p2;
    private static boolean turn1 = true;
    private static int numPlayer;

    // sets up game by initializing player variables and creates the deck - Carson
    public static void setUpGame(){
        CardDeck.createFullDeck();
        p1 = new PlayerHand();
        p2 = new PlayerHand();
        CardDeck.discardPile.add(CardDeck.generateCard());
        turn1 = false;
    }
    // determines who's turn it is and checks if the game is over - Carson
    public static void main(String[] args){
        setUpGame();
        while (true) {
            if (turn1){
                setUpGame();
            }
            if (turn) {
                numPlayer = 1;
                play(p1);
            } else {
                System.out.print("\n\n\n");
                numPlayer = 2;
                play(p2);
                System.out.print("\n\n\n");
            }
            if (CardDeck.currentCards <= 0 || p1.getHand().size()==0 || p2.getHand().size()==0){
                System.out.println(winConditions());
                System.exit(0);
            }
        }
    }
    // method that simulates the play of the game and player interaction - Carson
    public static void play(PlayerHand player){
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < player.getHand().size(); i++){
            player.addPairs(pairings(i, player));
        }
        System.out.println("Your hand: " + player.getHand() + "  " + "Discard Pile: "+CardDeck.discardPile+"  Your pairs: "+player.getPairs());
        System.out.print("Player "+numPlayer+"'s turn. Pick from deck or discard pile: ");
        String option1 = sc.nextLine();
        if (option1.equals("deck")){
            CardDeck.getCard(player);
            player.addPairs(pairings(player.getHand().size()-1, player));
            System.out.println("Your hand: " + player.getHand() + "  " + "Discard Pile: "+CardDeck.discardPile+"  Your pairs: "+player.getPairs());
            discardCard(player);

        }
        if (option1.equals("discard")){
            System.out.print("What index of card would you like to pick up? ");
            int index = sc.nextInt();
            pickFromDiscard(index, player);
            System.out.println("Your hand: " + player.getHand() + "  " + "Discard Pile: "+CardDeck.discardPile+"  Your pairs: "+player.getPairs());
            discardCard(player);
        }
        turn = !turn;
    }

    // algorithm that pairs cards in hand by suit or rank - Carson
    public static ArrayList<String> pairings(int index, PlayerHand player){
        Scanner sc = new Scanner(System.in);
        ArrayList<String> suitPairs = new ArrayList<String>();
        ArrayList<String> rankPairs = new ArrayList<String>();
        String rank = player.getHand().get(index).substring(0,1);
        String suit = player.getHand().get(index).substring(1,2);
        int negOffset = 0; int posOffset = 0;
        for (int i = 0; i < player.getHand().size(); i++){
            if (player.getHand().get(i).substring(0,1).equals(rank)){
                rankPairs.add(player.getHand().get(i));
            }
            if (player.returnCard(i).substring(1,2).equals(suit)){
                for (int j = 0; j < 19; j++){
                    for (int k = 0; k < 10; k++){
                        if (player.returnCard(index).equals(CardDeck.deckOrder[j][k])){
                            if (player.returnCard(i).equals(CardDeck.deckOrder[j-(1+negOffset)][k])){
                                negOffset++;
                                suitPairs.add(player.returnCard(i));
                            }
                            if (player.returnCard(i).equals(CardDeck.deckOrder[j+1+posOffset][k])){
                                posOffset++;
                                suitPairs.add(player.returnCard(i));
                            }
                        }
                    }
                }
            }
        }
        if (rankPairs.size() >= 3 && suitPairs.size() < 2) {
            removePairs(rankPairs, player);
            return rankPairs;
        }
        if (suitPairs.size() >= 2 && rankPairs.size() < 3){
            suitPairs.add(player.returnCard(index));
            removePairs(suitPairs, player);
            return suitPairs;
        }
        if (suitPairs.size() >= 2 && rankPairs.size() >= 3){
            System.out.print("Use "+player.returnCard(index)+" for suit or rank? ");
            String option3 = sc.nextLine();
            if (option3.equals("suit")){
                suitPairs.add(player.returnCard(index));
                removePairs(suitPairs, player);
                return suitPairs;
            } else{
                removePairs(rankPairs, player);
                return rankPairs;
            }
        }
        return new ArrayList<String>();
    }

    // removes any cards from hand that have already been paired - Carson
    public static void removePairs(ArrayList<String> arr, PlayerHand pX){
        for (String card : arr){
            for (int i = 0; i < pX.getHand().size(); i++){
                if (card.equals(pX.returnCard(i))){
                    pX.remove(i);
                    i--;
                }
            }
        }
    }

    // shifts cards from discard pile into hand and implements pairing method - Carson
    public static void pickFromDiscard(int index, PlayerHand pX){
        String card = CardDeck.discardPile.get(index);
        int tempIndex = pX.getHand().size();
        for (int i = index; i < CardDeck.discardPile.size(); i++){
            pX.setHand(CardDeck.discardPile.get(i));
            CardDeck.discardPile.remove(i);
            i--;
        }
        for (int i = tempIndex; i < pX.getHand().size(); i++){
            pX.addPairs(pairings(i, pX));
        }

    }

    // shifts card from a player's hand into the discard pile - Carson
    public static void discardCard(PlayerHand player){
        Scanner sc = new Scanner(System.in);
        System.out.print("What index of card would you like to discard? ");
        int dis = sc.nextInt();
        CardDeck.discardPile.add(player.returnCard(dis));
        player.remove(dis);
        System.out.println("Your hand: " + player.getHand() + "  " + "Discard Pile: "+CardDeck.discardPile+"  Your pairs: "+player.getPairs());
    }

    // determines who wins based on a point system of their pairings - Carson
    public static String winConditions(){
        PlayerHand[] players = {p1, p2};
        for (PlayerHand player : players) {  // Adds to each player's score each card's point value in their pairs
            for (int i = 0; i < player.getPairs().size(); i++) {
                for (String str : player.getPairs().get(i)) {
                    for (String fP : CardDeck.fivePoints) {
                        if (str.substring(0, 1).equals(fP)) {
                            player.setPoints(5);
                        }
                    }
                    for (String tP : CardDeck.tenPoints) {
                        if (str.substring(0, 1).equals(tP)) {
                            player.setPoints(10);
                        }
                    }
                }
            }
        }
        for (PlayerHand player : players){ // Subtracts the point value of each card left in each player's hand from their score
            for (String card : player.getHand()){
                for (String fP : CardDeck.fivePoints){
                    if (card.substring(0,1).equals(fP)){
                        player.setPoints(-5);
                    }
                }
                for (String tP : CardDeck.fivePoints){
                    if (card.substring(0,1).equals(tP)){
                        player.setPoints(-10);
                    }
                }
            }
        }

        if (p1.getPoints() > p2.getPoints()){
            return "Player 1 is the winner! P1 points: "+p1.getPoints()+" P2 Points: "+p2.getPoints();
        }
        return "Player 2 is the winner! P1 points: "+p1.getPoints()+" P2 Points: "+p2.getPoints();
    }

}