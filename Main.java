import java.util.*;

class Main {
  public static void main(String[] args) {

    //Scanner Intializing setup variables.
    Scanner console = new Scanner(System.in);
    System.out.print("How many players? Player = ");
    int playerNum = console.nextInt();
    System.out.println();

    System.out.print("How many decks? Deck = ");
    int deckNum = console.nextInt();
    System.out.println();

    //////////////////////////////////////////
    //       Creating the deck of cards     //
    //////////////////////////////////////////

    //Intializing arrays and arrayLists.
    int[] rank = {1,2,3,4,5,6,7,8,9,10,11,12,13,14};
    String[] suit = {"Diamond", "Club", "Heart", "Spade"};

    ArrayList<Card> deckOfCards = new ArrayList<Card>();
    
    
    //Creating and assigning suits to the value of the "cards".
    for (int i = 0; i < deckNum; i++){ //Deck num = 2
      for (int x = 0; x < 13; x++){
        for (int z = 0; z < 4; z++){
          deckOfCards.add(new Card(rank[x], suit[z]));
        }
      }
    }

    for (int i = 0; i < deckNum; i++){
      deckOfCards.add(new Card(14, "Joker [+]"));
      deckOfCards.add(new Card(14, "Joker [-]"));
    }

    
    //////////////////////////////////////////
    //        Configuring player hands      //
    //////////////////////////////////////////
    
    System.out.println(deckOfCards);
    Deck gameDeck = new Deck(deckOfCards, playerNum);

    gameDeck.shuffleDeck(7);
    System.out.println("\n\n");

    System.out.println(gameDeck.getDeck());
    System.out.println("\n\n");
    
    gameDeck.distributeCards();
    gameDeck.organizeHand();
    System.out.println("\n\n\n");


    //////////////////////////////////////////
    //           Running the game           //
    //////////////////////////////////////////
    
    int[] scoring = new int[playerNum];
    int pos = gameDeck.getRotation();
    int round = 1;
    int win = 0;

    while(gameDeck.isEmpty()){
      //System.out.print("\033[0;0H\033[2J");  
      //System.out.flush();

      //Clears the gameboard after every "round".
      System.out.println("\nRound: " + round);

      //Creating the variables for each round.

      Card tests = new Card();

      ArrayList<ArrayList<Card>> playerHands =  new ArrayList<>(playerNum);
      for(int i = 0; i < playerNum; i++) {
        playerHands.add(new ArrayList());
      }

      System.out.println("\nCurrent Pool:\t" + playerHands); //Prints hand of all players.

      for (int i = 0; i < playerNum; i++){
        System.out.println("This is hand of player " + (i+1) + ": \n----------------------------\n" + gameDeck.getHand(i));
        System.out.println();
      }

      System.out.println("THIS IS POSITION!!!  \t:" + pos);


      System.out.println("!!! Starting player is player " + (pos+1) + " !!!");
      System.out.print("How many cards do you want to select and use?\n- Singles(1), pairs(2) or triples(3) ? Max = ");

      int num = console.nextInt();
      
      ////////////////////////
      //Code for each round.//
      ////////////////////////
      
      for (int i = 0; i < playerNum; i++){

        System.out.println("-----------------------------------\n-----------------------------------\nCurrent person in play: player " + (i+1) + "!");
        System.out.println("You can enter a maximum of " + num + " cards.");

        int indexOfCard = 15;

        for (int x = 0; x < num; x++){
          System.out.print("\t*Enter a card using the format: <num>:<letterSuit>\t");

          String input = console.next();
          System.out.println();


          indexOfCard = gameDeck.searchCard(input, pos);
          

          if (indexOfCard != 15){
            playerHands.get(pos).add(gameDeck.getHand(pos).get(indexOfCard));

            gameDeck.removeCardInHand(pos, indexOfCard);


            System.out.println(playerHands);

          }
          else{
            System.out.print("Please reselect(y) a card or pass(any other): ");
            String passing = console.next();
            if (passing.equalsIgnoreCase("y")){
              x--;
            }
            else{

              break;
            }
          }
        }

        if (tests.isAllowed(playerHands, pos)){
          System.out.println(tests.isAllowed(playerHands, pos));
          System.out.println("The Entered Comp does not match each other.");
          System.out.println("Remember that only singles, pairs, and triples are  allowed to be played.");

          for (int z = 0; z < playerHands.get(pos).size(); z++){
            gameDeck.getHand(pos).add(playerHands.get(pos).get(z));
          }

          playerHands.get(pos).clear();

          i--;
          pos--;
        }

        
        if (pos == playerNum){
          pos = 0;
        }

        pos++;
      }


      win = tests.battle(playerHands, playerNum);
      System.out.println("Winner was player " + win + "!!!");

      scoring[win] += 1;
      pos = 0;
      round++;


      
    }

    
    win = scoring[0];
    int winningIndex = 0;
    for (int i = 0; i < scoring.length; i++){
      if (win < scoring[i]){
        win = scoring[i];
        winningIndex += i;
      }
    }


    System.out.println("The winner is Player" + winningIndex + "!!!");
      


    console.close();
  }


}