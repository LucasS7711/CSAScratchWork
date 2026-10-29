public class ProjectEuler {
   public static int problem1(int range) {
      int count = 0;
      int answer = 0;
      while(count < range) {
         if((count % 3) == 0) {
            System.out.println(count);
            answer+=count;
         } else {
            if((count % 5) == 0) {
               answer += count;
               System.out.println(count);
            }
         }
         count++;
      }
      return answer;
   }
   public static void main(String[] args) {
      System.out.println("The answer to 'Find the sum of all the multiples of $3$ or $5$ below $1000$.' is: " + problem1(1000));   
   }  
}