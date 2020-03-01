import java.util.*;

public class Card{

  private int rank;
  private String suit;

  Card(int rank, String suit){
    this.rank = rank;
    this.suit = suit;
  }

  Card(){

  }

  public boolean isAllowed(ArrayList<ArrayList<Card>> playerHands, int num){
    for (int i = 0; i < playerHands.get(num).size()-1; i++){
      if (playerHands.get(num).get(i).getRank() != playerHands.get(num).get(i +1).getRank()){
        System.out.println(playerHands.get(num).get(i).getRank() + "\t" + playerHands.get(num).get(i+1).getRank());
        return true;
      }
    }

    return false;
  }


  public int battle(ArrayList<ArrayList<Card>> playerHands, int playerNum){
    int maxInt = 0;
    
    for (int i = 0; i < playerHands.get(0).size(); i++){
      for (int x = 0; x < playerNum-1; x++){
        if (playerHands.get(x).get(i).getRank() < playerHands.get(x+1).get(i).getRank()){
          maxInt = x+1;
        }
        else if (playerHands.get(x).get(i).getRank() == playerHands.get(x+1).get(i).getRank()){
          if (getSuitValue(playerHands.get(x).get(i).getSuit()) > getSuitValue(playerHands.get(x+1).get(i).getSuit())){
            maxInt = x;
          }
          else{
            maxInt = x+1;
          }
        }
      }
    }


    return maxInt;
  }


  //Setters & getters.
  public int getRank(){
    return this.rank;
  }

  public String getSuit(){
    return this.suit;
  }

  public int getSuitValue(String s){
    int value = 0;

    switch(s){
      case "Diamond": value = 1;
      case "Club": value = 2;
      case "Heart": value = 3;
      case "Spade": value = 4;
      case "Joker": value = 5;
    }

    return value;
  } 


  @Override
  public String toString(){
    return "[" + rank + "]: " + suit;
  }



}