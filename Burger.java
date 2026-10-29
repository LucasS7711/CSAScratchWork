public class Burger {
   //instance variables
   private int numPatties;
   private boolean isTasty;
   private double gramsOfSauce;
   private String burgerName;
   private int numberOfBites;
   
   //constructors
   //no argument constructor
   public Burger() {
      numPatties = 1;
      isTasty = true;
      gramsOfSauce = 30;
      burgerName = "Craig the Burger";
      numberOfBites = 0;
   }
   //argument constructor
   public Burger(int numPatties, boolean isTasty, double gramsOfSauce, String burgerName) {
      this.numPatties = numPatties;
      this.isTasty = isTasty;
      this.gramsOfSauce = gramsOfSauce;
      this.burgerName = burgerName;
      numberOfBites = 0;
   }
   
   //methods
   public void bite() {
      System.out.println("chomp");
      numberOfBites++;
   }
   public int getNumberOfBites() {
      return (numberOfBites);
   }
   public static String getBurgerRecipe() {
      return "make burger";
   }
   public static void main(String[] args) {
      Burger plain = new Burger();
      
      Burger delux = new Burger(2, true, 25.8, "Dicks Delux");
      
      delux.bite();

      delux.bite();
            
      System.out.println(delux.getNumberOfBites());
      
      getBurgerRecipe();
   }
}