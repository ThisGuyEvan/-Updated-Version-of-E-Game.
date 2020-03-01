import java.util.*;

public class Deck{

  private ArrayList<ArrayList<Card>> handOfPlayers;
  private ArrayList<Card> collectionOfCards;
  private int playerNum;
  private int[] findThree;

  Deck(ArrayList<Card> deckOfCards, int playerNum){
    this.collectionOfCards = deckOfCards;
    this.playerNum = playerNum;
    this.findThree = new int[playerNum];

    this.handOfPlayers = new ArrayList<>(playerNum);
    for(int i=0; i < playerNum; i++) {
      handOfPlayers.add(new ArrayList());
    }
  }

  public void shuffleDeck(int num){
    for (int i = 0; i < num; i++){
      Collections.shuffle(collectionOfCards);
    }
  }

  public void distributeCards(){
    int countPassed = collectionOfCards.size()/playerNum;
    if (countPassed % 2 != 0){
      countPassed -= 1;
    }

    int count = 0;
    for (int i = 0; i <  countPassed; i++){
      for (int x = 0; x < playerNum; x++){
        if (collectionOfCards.get(count).getRank() == 3 && collectionOfCards.get(count).getSuit().equalsIgnoreCase("Diamond")){
          findThree[x] += 1;
        }
        handOfPlayers.get(x).add(collectionOfCards.get(count));
        
        count++;
      }
    }

  }

  
  public void organizeHand(){
    for(int i = 0; i < playerNum; i++){
      int count = 0;
      int min = handOfPlayers.get(i).get(0).getRank();
        while (count != handOfPlayers.get(0).size()-1){
        int maxIndex = 0;

        //Simple find min algorithm
        for (int x = count; x < handOfPlayers.get(0).size(); x++){
          if(min >= handOfPlayers.get(i).get(x).getRank()){
            
            maxIndex = x;
            min = handOfPlayers.get(i).get(x).getRank();
          }
        }
      
        handOfPlayers.get(i).add(count, handOfPlayers.get(i).get(maxIndex));
        handOfPlayers.get(i).remove(maxIndex+1);
        count++;
        min = handOfPlayers.get(i).get(count).getRank();
      }
    }
  }


  //Write a deal card method that returns both the rank and suit of the card.

  public Card getCardValue(int index){
    return collectionOfCards.get(index);
  }

  public int searchCard(String s, int playerPos){
    int val = Integer.parseInt(s.substring(0, s.indexOf(":")));
    String suited = s.substring(s.indexOf(":")+1, s.length());

    //System.out.println(val + "\t\t" + suited);

    switch(suited.toLowerCase()){
      case "d": suited = "Diamond"; break;
      case "c": suited = "Club"; break;
      case "h": suited = "Heart"; break;
      case "s": suited = "Spade"; break;
      case "j+": suited = "Joker [+]"; break;
      case "j-": suited = "Joker [-]"; break;
    }

    int indexCard = 15;
    for (int i = 0; i < handOfPlayers.get(playerPos).size(); i++){
      if (handOfPlayers.get(playerPos).get(i).getRank() == val && handOfPlayers.get(playerPos).get(i).getSuit().equals(suited)){
        indexCard = i;
      }
    }

    if (indexCard == 15){
      System.out.println("You do not have this card in your possession.");
      return indexCard;
    }
    else{
      System.out.println("Index of card: " + indexCard);
      return indexCard;
    }
  }


  //Setters & getters.
  public int getRotation(){
    int playerIndex = 0;

    for (int i = 0; i < playerNum; i++){
      if (playerIndex < findThree[i]){
        playerIndex = i;
      }
      System.out.println(Arrays.toString(findThree) + "\t" + playerIndex);
    }

    //Counter-clockwise rotation.

    return playerIndex;
  }
  
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

  public ArrayList<Card> getDeck(){
    return this.collectionOfCards;
  }

  public ArrayList<Card> getHand(int num){
    return this.handOfPlayers.get(num);
  }

  public void removeCardInHand(int num, int index){
    this.handOfPlayers.get(num).remove(index);
  }

}