import java.util.*; 

public class Card{

  //Private variables.
  private int rank;
  private String suit;

  //Constructor utilized to create cards.
  Card(int rank, String suit){
    this.rank = rank;
    this.suit = suit;
  }

  //Overload? made so that the [tests] variable can intialize as an card object.
  Card(){

  }

  //Tests whether the value of the given cards are the same by the given length of the 2d arrayList.
  public boolean isAllowed(ArrayList<ArrayList<Card>> playerHands, int num){
    for (int i = 0; i < playerHands.get(num).size()-1; i++){
      if (playerHands.get(num).get(i).getRank() != playerHands.get(num).get(i +1).getRank()){ //If the value is not the same, returns false.
        System.out.println(playerHands.get(num).get(i).getRank() + "\t" + playerHands.get(num).get(i+1).getRank());
        return true;
      }
    }
    //returns true when values are the same.
    return false;
  }

  //Tests which card is larger based on rank and if they're the same, test which suits.
  public int battle(ArrayList<ArrayList<Card>> playerHands, int playerNum){
    int maxInt = playerHands.get(0).get(0).getRank(); //Default
    
    for (int i = 0; i < playerHands.get(0).size(); i++){
      for (int x = 0; x < playerNum-1; x++){
        //Tests if the ranking of the card is larger.
        if (playerHands.get(x).get(i).getRank() < playerHands.get(x+1).get(i).getRank()){
          maxInt = x+1;
        }
        //Since the ranking was not larger, then it might be equal so test suits.
        else if (playerHands.get(x).get(i).getRank() == playerHands.get(x+1).get(i).getRank()){
          //Tests which suit is larger by using the [getSuitValue] method.
          if (getSuitValue(playerHands.get(x).get(i).getSuit()) > getSuitValue(playerHands.get(x+1).get(i).getSuit())){
            maxInt = x;
          }
          else{ //If pre was greater.
            maxInt = x+1;
          }
        }
      }
    }
    return maxInt;
  }


  //Setters & getters.
//Returns the rank.
  public int getRank(){
    return this.rank;
  }

//Returns the suit.
  public String getSuit(){
    return this.suit;
  }

//Filters the suit by using a string value into a int value.
  public int getSuitValue(String s){
    int value = 0;

    //Testing cases. //For Durak Change one of these values to trump suit.
    switch(s){
      case "Diamond": value = 1;
      case "Club": value = 2;
      case "Heart": value = 3;
      case "Spade": value = 4;
      case "Joker": value = 5;
    }

    return value;
  } 

//To string.
  @Override
  public String toString(){
    return "[" + rank + "]: " + suit;
  }



}