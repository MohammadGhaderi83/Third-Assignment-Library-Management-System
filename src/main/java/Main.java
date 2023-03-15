import java.util.Scanner;

public class Main {
    /*
    * make a functional library app using oop
    * run the main program in Main.java and code the oop part in other classes
    * don't forget to add at least 1 librarian to the library to make it functionable.
    * *  *** don't limit yourself to our template ***
     */
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        runMenu();
    }

    public static void runMenu(){
        System.out.println("Welcome to the Library System\n" +
                "please choose (1-6):\n" +
                "1-Sign in\n2-Search User\n3-Remove user\n4-Add a book\n5-Remove a book\n6-Update profile\n7-Exit");
        int userInput = input.nextInt();
        if (userInput == 7)
        {
            return;
        }
        else
        {
            Library library = new Library(userInput);
            runMenu();
        }
    }
}
