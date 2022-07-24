import java.util.Scanner;
public class main_class {
    public static void main(String [] args){
        Scanner scnr = new Scanner(System.in);

        String user_name;

        System.out.println("Welcome! There has been a distaster and animals need your help!");
        System.out.print("What is your name?: ");
        user_name=scnr.nextLine();
        System.out.printf("Hi %s! You now have the ability to help animals!\n",user_name);

        Game new_game = new Game(user_name,scnr);
        new_game.start();



    }
}