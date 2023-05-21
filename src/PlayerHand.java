import java.util.ArrayList;

public class PlayerHand {
    private ArrayList<String> hand = new ArrayList<String>();
    private ArrayList<ArrayList<String>> pairs = new ArrayList<ArrayList<String>>();
    private int temp = 0;


    public PlayerHand(){
        for (int i = 0; i < 20; i++){
            hand.add(CardDeck.generateCard());
        }
    }

    public ArrayList<String> getHand(){
        return hand;
    }

    public ArrayList<ArrayList<String>> getPairs(){
        return pairs;
    }

    public void addPairs(ArrayList<String> arr){
        pairs.add(arr);
        for (int i = 0; i < pairs.size(); i++){
            if (pairs.get(i).size() == 0){
                pairs.remove(i);
                i--;
            }
        }
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

}
