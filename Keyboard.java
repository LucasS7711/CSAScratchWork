//class declaration
public class Keyboard {
   //instance variable
   String keyboardColor;
   
   //constructor
   public Keyboard() {
      keyboardColor = "Black";
   }
   public Keyboard(String color) {
      keyboardColor = color;
   }
   //method declarations
   public  String write(String wordToType) {
      return "Keyboard is " + keyboardColor + ", typing " + wordToType;
   }
   public  String click() {
      return "clack";
   }
}