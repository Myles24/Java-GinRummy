import java.util.ArrayList;

public class PlayerHand {
    private ArrayList<String> hand = new ArrayList<String>();
    private ArrayList<ArrayList<String>> pairs = new ArrayList<ArrayList<String>>();
    private int temp = 0;
    private int points = 0;

    // creates the hand size with 7 cards - Cole
    public PlayerHand(){
        for (int i = 0; i < 7; i++){
            hand.add(CardDeck.generateCard());
        }
    }



    // adds an ArrayList of cards to the pairs list - Cole
    public void addPairs(ArrayList<String> arr){
        pairs.add(arr);
        for (int i = 0; i < pairs.size(); i++){
            if (pairs.get(i).size() == 0){
                pairs.remove(i);
                i--;
            }
        }
    }

    // other getters and setters for the hand or pairs list - Cole
    public ArrayList<String> getHand(){
        return hand;
    }

    public ArrayList<ArrayList<String>> getPairs(){
        return pairs;
    }

    public void setHand(String s1){
        hand.add(s1);
    }

    public void remove(int index){
        hand.remove(index);
    }

    public String returnCard(int index){
        return hand.get(index);
    }

    public int getPoints(){
        return points;
    }
    public void setPoints(int pnts){
        points += pnts;
    }

}
