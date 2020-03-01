import java.util.*;

///////////////////////////////////////////////
/*       Information about this game
Rules: 
  - Players lose 1 point if they pass a round.
  - Players gain a point if they successfully beat others during the round.

Strategies:
  - Players can use larger quantities of the same card rank to "sabatogage others".
  - Look at the ranks and suits together, if one suit is larger, and another 1 is lower, use the interations of the game to move the cards in your deck to a certain position thatr is greater to output max value.

How to win:
  - Earn  the most points and win.
  - Game ends when one deck is empty. If you pass, your pool of cards- 
  does not get cleared! It is re-distributed back into your main hand.

Fact(s):
  - Combonations are endless, decks and players are also endless 
  unless given that it is not possible to distribute equally.

Bug:
  - The variable pos in the while-loop is being affected by other properities of 
  the game, so it's leading the count of player 2 -> player 3 (supposed to be player 1) despite max players being 2.
*/
///////////////////////////////////////////////

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

    //Variables coving the scope of the whole game.
    
    int[] scoring = new int[playerNum];
    int pos = gameDeck.getRotation();
    int round = 1;
    int win = 0;

    while(gameDeck.isEmpty()){
      //Clears the repl.it terminal/screen/console.
      //System.out.print("\033[0;0H\033[2J");  
      //System.out.flush();

      //Clears the gameboard after every "round".
      System.out.println("\nRound: " + round);

      //Creating the variables and objects for each round.
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

      //System.out.println("THIS IS POSITION!!!  \t:" + pos);

      System.out.println("!!! Starting player is player " + (pos+1) + " !!!");
      System.out.print("How many cards do you want to select and use?\n- Singles(1), pairs(2) or triples(3)...etc? Max = ");

      int num = console.nextInt();
      
      ////////////////////////
      //Code for each round.//
      ////////////////////////
      
      //Iterates until every one of the players have went once.
      for (int i = 0; i < playerNum; i++){

        System.out.println("-----------------------------------\n-----------------------------------\nCurrent person in play: player " + (i+1) + "!");
        System.out.println("You can enter a maximum of " + num + " cards.");

        int indexOfCard = 15;

        for (int x = 0; x < num; x++){
          //Prompting the user given the amount of playable cards.
          System.out.print("\t*Enter a card using the format: <num>:<letterSuit>\t");

          String input = console.next();
          System.out.println();

          indexOfCard = gameDeck.searchCard(input, pos); //Searches if the card is in deck. (Valid or Invalid).

          if (indexOfCard != 15){ //15 means not in deck.
            //Adds the card temporary into the 2dArraylist.
            playerHands.get(pos).add(gameDeck.getHand(pos).get(indexOfCard)); 
            //Removes from the main hand of given player index, pos.
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
              scoring[pos]--; //Deducts point from the player due to passing.
              break;
            }
          }
        }

        //Tests whether the card is a pair, triple and so on.
        if (tests.isAllowed(playerHands, pos)){
          System.out.println(tests.isAllowed(playerHands, pos));
          System.out.println("The Entered Comp does not match each other.");
          System.out.println("Remember that only singles, pairs, and triples are  allowed to be played.");

          for (int z = 0; z < playerHands.get(pos).size(); z++){
            gameDeck.getHand(pos).add(playerHands.get(pos).get(z));
          }
          playerHands.get(pos).clear();
          i--; //Player goes again since the given pool goes against rules.
          pos--; // ^Same.
        }
        //Prevents the cycle from breaking. 
        if (pos == playerNum){
          pos = 0;
        }
        pos++;
      }

      //Distribute the winner's point based on the "battle".
      win = tests.battle(playerHands, playerNum);
      System.out.println("Winner was player " + win + "!!!");

      scoring[win] += 1;
      pos = 0;
      round++;

    }

    //After completing the loop, this finds the winner based on acquired points.
    win = scoring[0];
    int winningIndex = 0;

    //min v max algorithm
    for (int i = 0; i < scoring.length; i++){
      if (win < scoring[i]){
        win = scoring[i];
        winningIndex += i;
      }
    }
    System.out.println("The winner is Player" + winningIndex + "!!!");

    console.close(); //Closes scanner.
  }

}


/* Notes (Helpful Links)
https://www.baeldung.com/java-multi-dimensional-arraylist
https://repl.it/talk/ask/In-repil-how-do-you-clear-the-screen-when-using-java/9810
*/