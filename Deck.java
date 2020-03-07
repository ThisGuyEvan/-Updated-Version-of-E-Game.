import java.util.*;

public class Deck{

  //Private variables.
  private ArrayList<ArrayList<Card>> handOfPlayers;
  private ArrayList<Card> collectionOfCards;
  private int playerNum;
  private int[] findThree;


  //Constructor.
  Deck(ArrayList<Card> deckOfCards, int playerNum){
    this.collectionOfCards = deckOfCards;
    this.playerNum = playerNum;
    this.findThree = new int[playerNum];

    //Creates a arraylist within a arraylist -> 2d
    this.handOfPlayers = new ArrayList<>(playerNum);
    for(int i=0; i < playerNum; i++) { 
      handOfPlayers.add(new ArrayList()); //adding arraylist as element.
    }
  }

  //Shuffles deck using the collections library.
  public void shuffleDeck(int num){
    for (int i = 0; i < num; i++){
      Collections.shuffle(collectionOfCards);
    }
  }

  //Distribute method.
  public void distributeCards(){
    int countPassed = 0;
    
    //Removes 1 card from the deck(s) incase of uneven distribution (odd).
    if (collectionOfCards.size()/playerNum % 2 != 0){ 
      countPassed = (collectionOfCards.size()/playerNum) - 1;
    }
    //Does not remove since mod is equal to 0 (even)
    else{
      countPassed = collectionOfCards.size()/playerNum;
    }

    //Iterating through the cards.
    int count = 0;
    for (int i = 0; i <  countPassed; i++){
      for (int x = 0; x < playerNum; x++){
        //Searching for diamond 3 as it cycles through all the cards.
        if (collectionOfCards.get(count).getRank() == 3 && collectionOfCards.get(count).getSuit().equalsIgnoreCase("Diamond")){
          findThree[x] += 1; //adds a point to its respective player who recieved d3.
        }

        //As [x] changes, the player who will recieve the card changes.
        handOfPlayers.get(x).add(collectionOfCards.get(count));
        count++; //Increments so it does not affect the loop and addition of cards.
      }
    }

  }

  //Sorts the deck from lowest ranking (ignores suits) to largest ranking.
  public void organizeHand(){

    for(int i = 0; i < playerNum; i++){
      int count = 0;
      int min = handOfPlayers.get(i).get(0).getRank(); //Begins with first value as min.
        while (count != handOfPlayers.get(0).size()-1){ //Until all values r sorted.
        int maxIndex = 0;

        //Find min algorithm by getting ranking values.
        for (int x = count; x < handOfPlayers.get(0).size(); x++){
          if(min >= handOfPlayers.get(i).get(x).getRank()){
            
            maxIndex = x;
            min = handOfPlayers.get(i).get(x).getRank();
          }
        }
      //Holds the value, sets a new loc of the value, and removes the pre-loc value.
        handOfPlayers.get(i).add(count, handOfPlayers.get(i).get(maxIndex)); //Sets
        handOfPlayers.get(i).remove(maxIndex+1); //Removes
        count++; 
        min = handOfPlayers.get(i).get(count).getRank(); //Moves up.
      }
    }
  }

  public int searchCard(String s, int playerPos){
    int val = Integer.parseInt(s.substring(0, s.indexOf(":")));
    String suited = s.substring(s.indexOf(":")+1, s.length());

    //System.out.println(val + "\t\t" + suited);
    

    //////////////////////////////////////////////////////////////
    //Sets the user input into card values and searches for it.//
    ////////////////////////////////////////////////////////////

    switch(suited.toLowerCase()){ //Converts to full suit.
      case "d": suited = "Diamond"; break;
      case "c": suited = "Club"; break;
      case "h": suited = "Heart"; break;
      case "s": suited = "Spade"; break;
      case "j+": suited = "Joker [+]"; break;
      case "j-": suited = "Joker [-]"; break;
    }

    int indexCard = 15; //15 = not in deck.
    
    //Checks whether the user had the card or not.
    for (int i = 0; i < handOfPlayers.get(playerPos).size(); i++){
      if (handOfPlayers.get(playerPos).get(i).getRank() == val && handOfPlayers.get(playerPos).get(i).getSuit().equals(suited)){ //2d arraylist getting vals.
        indexCard = i;
      }
    }

    if (indexCard == 15){ //if not in deck.
      System.out.println("[!] You do not have this card in your possession [!]");
      return indexCard;
    }
    else{ //if is in deck.
      System.out.println("Index of card: " + indexCard);
      return indexCard;
    }
  }


  //Setters & getters.
  public int getRotation(){
    int playerIndex = 0;

    //Finds the user with the most diamond 3s. (Max v min method).
    for (int i = 0; i < playerNum; i++){
      if (playerIndex < findThree[i]){
        playerIndex = i;
      }
      System.out.println(Arrays.toString(findThree) + "\t" + playerIndex);
    }

    //Counter-clockwise rotation.
    return playerIndex;
  }
  
//Tests whether a deck in the whole collection is empty. (Utilized to end the game).
  public boolean isEmpty(){
    for (int i = 0; i < playerNum; i++){
      if (handOfPlayers.get(i).size() == 0){
        System.out.println("F");
        return false;
      }
    }
    System.out.println("T");
    return true;
  }

//Returns the value of a card in the CARD arrayList.
  public Card getCardValue(int index){
    return collectionOfCards.get(index);
  }

//Returns the untouch collection deck (pre-distributed).
  public ArrayList<Card> getDeck(){
    return this.collectionOfCards;
  }

//Returns the distributed decks given the index of them.
  public ArrayList<Card> getHand(int num){
    return this.handOfPlayers.get(num);
  }

//Removes the card given the index of the hand and index of the card.
  public void removeCardInHand(int num, int index){
    this.handOfPlayers.get(num).remove(index);
  }

}