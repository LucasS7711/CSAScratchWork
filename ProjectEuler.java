public class ProjectEuler {
   //Sum of multiples of 3 & 5
   public static int problem1(int range) {
      int count = 0;
      int answer = 0;
      while(count < range) {
         if((count % 3) == 0) {
            answer+=count;
         } else {
            if((count % 5) == 0) {
               answer += count;
            }
         }
         count++;
      }
      return answer;
   }
   //Sum of odd fibinachi numbers
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
   //Checks if input is a prime number, if it is it returns true
   public static boolean isPrime(long number) {
      boolean isPrime = true;
      if ((number % 2) == 0) {
         isPrime = false;
      } else {
         int count = 3;
         while (count < (number/2) && isPrime == true) {
            if ((number % count) == 0) {
               isPrime = false;
            } else {
               isPrime = true;
            }
         count += 2;
         }
      }
      return isPrime;
   }
   //Finds the largest Prime Factor 
   public static long largestPrimeFactor(long number) {
      long count = number;
      long answer = 0;
      while (count > 0 && answer == 0) {
         if ((number % count) == 0 && isPrime(count)) {
            answer = count;
         }
         count -= 2;
      }
   return answer;
   }
   //Checks if input is a palindrome
/*   public static boolean isPalindrome(int number) {
      
   }*/
   public static void main(String[] args) {
      System.out.println("The answer to problem 1 is: " + problem1(1000));   
      System.out.println("The answer to probelm 2 is: " + problem2(4000000));
      System.out.println("The answer to problem 3 is: " + largestPrimeFactor(221));
   }  
}