import java.util.ArrayList;
public class CardDeck {
    // instantiates variables such as the number of current cards and the discard pile list
    public static int currentCards = 52;
    public static ArrayList<String> discardPile = new ArrayList<String>();
    public static final String[][] deckOrder = new String[19][10];

    // lists that dictate which cards are worth a certain point value for scoring
    public static final String[] fivePoints = {"A","2","3","4","5","6","7","8","9"};
    public static final String[] tenPoints = {"T","J","Q","K"};


    // 2D array deck of cards that is modified throughout the game
    private static String[][] deck = {  {"AH","AD","AC","AS"},
                                        {"2H","2D","2C","2S"},
                                        {"3H","3D","3C","3S"},
                                        {"4H","4D","4C","4S"},
                                        {"5H","5D","5C","5S"},
                                        {"6H","6D","6C","6S"},
                                        {"7H","7D","7C","7S"},
                                        {"8H","8D","8C","8S"},
                                        {"9H","9D","9C","9S"},
                                        {"TH","TD","TC","TS"},
                                        {"JH","JD","JC","JS"},
                                        {"QH","QD","QC","QS"},
                                        {"KH","KD","KC","KS"}};


    public static void getCard(PlayerHand pX){
        pX.setHand(generateCard());
    }

    // returns a random card from the deck and sets that index to null
    public static String generateCard(){
        int cardRow = (int) (Math.random()*13);
        int cardCol = (int) (Math.random()*4);
        while (deck[cardRow][cardCol] == null){
            cardRow = (int) (Math.random()*13);
            cardCol = (int) (Math.random()*4);
        }
        String card = deck[cardRow][cardCol];
        deck[cardRow][cardCol] = null;
        currentCards--;
        return card;
    }

    // creates ordered, unmodified deck of cards with padding to avoid index exceptions
    public static void createFullDeck(){
        for (int i = 0; i < 19; i++){
            for (int j = 0; j < 10; j++){
                deckOrder[i][j] = null;
            }
        }
        for (int i = 3; i < 16; i++){
            for (int j = 3; j < 7; j++){
                deckOrder[i][j] = deck[i-3][j-3];
            }
        }
    }


}
