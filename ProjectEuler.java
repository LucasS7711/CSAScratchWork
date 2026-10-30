public class ProjectEuler {
   public static int problem1(int range) {
      int count = 0;
      int answer = 0;
      while(count < range) {
         if((count % 3) == 0) {
            //System.out.println(count);
            answer+=count;
         } else {
            if((count % 5) == 0) {
               answer += count;
               //System.out.println(count);
            }
         }
         count++;
      }
      return answer;
   }
   public static int problem2(int range) {
      int answer = 0;
      int curNum = 1;
      int prevNum = 0;
      int stoNum;
      while(curNum < range) {
         stoNum = curNum;
         curNum += prevNum;
         prevNum = stoNum;
         if((curNum % 2) == 0) {
            answer += curNum;
         }
         
         
      }
      return answer;
   }
   public static void main(String[] args) {
      System.out.println("The answer to 'Find the sum of all the multiples of $3$ or $5$ below $1000$.' is: " + problem1(1000));   
      System.out.println("The answer to 'By considering the terms in the Fibonacci sequence whose values do not exceed four million, find the sum of the even-valued terms' is: " + problem2(4000000));
   }  
}