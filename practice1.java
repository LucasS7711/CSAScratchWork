import java.util.Scanner;
public class practice1 {
   public static void main(String[] args) {
      //variables
      String teamName;
      int numPlayers;
      double pointsScored;
      int oppPoints;
      double avgPoints;
      double hoursPlayed;
      double avgPlayTime;
      boolean win = false;
      boolean tie = false;
      
      //scanner
      Scanner oppScan = new Scanner(System.in);
      Scanner scan = new Scanner(System.in);

      //Questions
      System.out.println("What is your teams name?");
      teamName = scan.nextLine();
      System.out.println("How many players are on " + teamName + "?");
      numPlayers = scan.nextInt();
      System.out.println("What was " + teamName +"'s final score?");
      pointsScored = scan.nextInt();
      System.out.println("Who were you guys playing?");
      String oppName = oppScan.nextLine();
      System.out.println("What was " + oppName + "'s score?");
      oppPoints = scan.nextInt();
      if (pointsScored == oppPoints) { //depending on score, there is a different message and tie and win are set to be true or fals depending on outcome
         System.out.println("Tie game!");
         tie = true;
      } else {
         if ((pointsScored/oppPoints) > 1) {
            System.out.println("Nice! You guys won!");
            win = true;
         } else {
            System.out.println("Oh no! Next time.");
            win = false;
         }
      }
      System.out.println("How long was the game in hours?");
      hoursPlayed = scan.nextDouble();
      
      //Finding Stats
      avgPoints = pointsScored / numPlayers; 
      avgPlayTime = hoursPlayed / numPlayers;
      
      //showing stats
      System.out.println("Okay, here are your stats for " + teamName + ":" );
      System.out.println(" The average player had " + (avgPlayTime * 60/*converting to minutes*/) + " minutes of play time");
      System.out.println(" The average player scored " + avgPoints + " points");
      if (tie == true) {
      } else {
         if (win == true) {
            System.out.println("You guys won by " + (pointsScored - oppPoints) + " points!");
         } else {
            System.out.println(oppName + " won by " + (oppPoints - pointsScored) + " points");
         }
      } 
   }
}
