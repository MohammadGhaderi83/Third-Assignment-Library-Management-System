import java.io.*;
import java.util.Scanner;

public class Library {
    /*
    * The library should have a list of books.
    * The library should have a map of books ISBNs which is linked to the amount of book
    -> (for example: harry potter -> 4 means there are currently 4 harry potter books)
    * The library should have a list of users and a list of librarians.
     */
    Scanner input = new Scanner(System.in);
    Library(int userInput)
    {
        if (userInput == 1)
        {
            addUser();
        }
        else if(userInput == 2)
        {
            System.out.println("Please enter your username:");
            String username = input.nextLine();
            System.out.println("Please enter your password:");
            String password = input.nextLine();
            System.out.println(searchUser(username, password));
        }
        else if (userInput == 3)
        {
            removeUser();
        }
        else if (userInput == 4)
        {
            addBook();
        }
        else if(userInput == 5)
        {
            removeBook();
        }
        else if(userInput == 6)
        {
            updateUser();
        }
        else
        {
            System.out.println("Invalid Input!");
        }
    }
    //book related functions

    public void addBook(){
        Book book = new Book();
        System.out.println("Please enter the book's name:");
        book.setName(input.nextLine()) ;
        System.out.println("Please enter the book's author:");
        book.setAuthor(input.nextLine());
        System.out.println("PLease enter the book's year of publish");
        book.setYear_of_publish(input.nextLine());
        System.out.println("Please enter the book's ISBN:");
        book.setISBN(input.nextLine());
        System.out.println("How many books do you want to donate?");
        int amount = input.nextInt();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("books.txt",true));
            if (!doesBookExist(book.getName(), book.getAuthor(), book.getYear_of_publish(), book.getISBN()))
            {
                book.setNumber_of_book(Integer.parseInt(book.getNumber_of_book()) + amount);
                writer.write(book.getName() + "," + book.getAuthor() + "," + book.getYear_of_publish() +"," + book.getISBN() + "," + book.getNumber_of_book() + "\n");
                System.out.println("book successfully added!");
            }
            else
            {
                System.out.println("This book is already exists, then it's amount just get increased:)");
                increaseBook(book, amount);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeBook(){
        System.out.println("Please enter your username:");
        String username = input.nextLine();
        System.out.println("Please enter your password:");
        String password = input.nextLine();
        System.out.println("Please enter the book's name:");
        String name = input.nextLine();
        System.out.println("Please enter the book's author:");
        String author = input.nextLine();
        System.out.println("PLease enter the book's year of publish");
        String year_of_Publish = input.nextLine();
        System.out.println("Please enter the book's ISBN:");
        String ISBN = input.nextLine();
        File inputFile = new File("books.txt");
        if(!inputFile.isFile())
        {
            System.out.println("File doesn't exist!");
        }
        else
        {
            File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");
            try {
                BufferedReader reader = new BufferedReader(new FileReader("books.txt"));
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
                    String line = reader.readLine();
                    while(line != null)
                    {
                        if (!line.trim().equals(name + "," + author + "," + year_of_Publish + "," + ISBN + "," + "1"))
                        {
                            writer.println(line);
                            writer.flush();
                        }
                        line = reader.readLine();
                    }
                    writer.close();
                    reader.close();

                    if (!inputFile.delete())
                    {
                        System.out.println("Could not delete file :(");
                        return;
                    }
                    else if (!tempFile.renameTo(inputFile))
                    {
                        System.out.println("Could not rename the temporary file :(");
                    }
                    else
                    {
                        System.out.println("\"" + username + "\"" + " removed " + "" + "\"" + name + "\" successfully");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void searchBook(){

    }

//    public void updateBook(){
//        //TODO
//    }

    public boolean doesBookExist(String name, String author, String year_of_publish, String ISBN){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("books.txt"));
            try {
                String line = reader.readLine();
                while (line != null)
                {
                    String books[] = line.split(",");
                    if (books[0].equals(name) && books[1].equals(author) && books[2].equals(year_of_publish) && books[3].equals(ISBN))
                    {
                        reader.close();
                        return true;
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void increaseBook(Book book, int addAmount){
        File myFile = new File("books.txt");
        String filePath = myFile.getAbsolutePath();
        try {
            Scanner sc = new Scanner(new File(filePath));
            StringBuffer buffer = new StringBuffer();
            while(sc.hasNextLine())
            {
                buffer.append(sc.nextLine() + System.lineSeparator());
            }
            String fileContents = buffer.toString();
            sc.close();
            String oldLine = book.getName() + "," + book.getAuthor() + "," + book.getYear_of_publish() + "," + book.getISBN() + "," + book.getNumber_of_book() + "\n";
            book.setNumber_of_book(Integer.parseInt(book.getNumber_of_book()) + addAmount);
            String newLine = book.getName() + "," + book.getAuthor() + "," + book.getYear_of_publish() + "," + book.getISBN() + "," + book.getNumber_of_book() + "\n";
            fileContents = fileContents.replaceAll(oldLine,newLine);
            try {
                FileWriter writer = new FileWriter(filePath);
                writer.append(fileContents);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void decreaseBook(Book book, int rentAmount){
        File myFile = new File("books.txt");
        String filePath = myFile.getAbsolutePath();
        try {
            Scanner sc = new Scanner(new File(filePath));
            StringBuffer buffer = new StringBuffer();
            while(sc.hasNextLine())
            {
                buffer.append(sc.nextLine() + System.lineSeparator());
            }
            String fileContents = buffer.toString();
            sc.close();
            String oldLine = book.getName() + "," + book.getAuthor() + "," + book.getYear_of_publish() + "," + book.getISBN() + "," + book.getNumber_of_book() + "\n";
            book.setNumber_of_book(Integer.parseInt(book.getNumber_of_book()) - rentAmount);
            String newLine = book.getName() + "," + book.getAuthor() + "," + book.getYear_of_publish() + "," + book.getISBN() + "," + book.getNumber_of_book() + "\n";
            fileContents = fileContents.replaceAll(oldLine,newLine);
            try {
                FileWriter writer = new FileWriter(filePath);
                writer.append(fileContents);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //user related functions

    public void addUser() {
        System.out.println("Please make a username:");
        String username = input.nextLine();
        System.out.println("Please make a password:");
        String password = input.nextLine();
        System.out.println("Please repeat the password again(For preventing make any mistake!):");
        String repeatPassword = input.nextLine();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt",true));
            if (!doesUserExist(username, password)) {
                if (password.equals(repeatPassword)) {
                    writer.write(username + "," + password + "\n");
                    System.out.println("Sign Up successfully!:)");
                } else {
                    System.out.println("Passwords don't match!");
                }
            } else {
                System.out.println("This username is already exists :(");
            }
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public void removeUser(){
        System.out.println("Please enter your username:");
        String username = input.nextLine();
        System.out.println("Please enter your password:");
        String password = input.nextLine();
        File inputFile = new File("users.txt");
        if(!inputFile.isFile())
        {
            System.out.println("File doesn't exist!");
        }
        else
        {
            File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");
            try {
                BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
                    String line = reader.readLine();
                    while(line != null)
                    {
                        if (!line.trim().equals(username + "," + password))
                        {
                            writer.println(line);
                            writer.flush();
                        }
                        line = reader.readLine();
                    }
                    writer.close();
                    reader.close();

                    if (!inputFile.delete())
                    {
                        System.out.println("Could not delete file :(");
                        return;
                    }
                    else if (!tempFile.renameTo(inputFile))
                    {
                        System.out.println("Could not rename the temporary file :(");
                    }
                    else
                    {
                        System.out.println("\"" + username + "\"successfully got removed:)");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean searchUser(String username, String password){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            try {
                String line = reader.readLine();
                while(line != null)
                {
                    String users[] = line.split(",");
                    if (users[0].equals(username) && users[1].equals(password))
                    {
                        reader.close();
                        return true;
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void updateUser(){
        System.out.println("Please enter your username:");
        String username = input.nextLine();
        System.out.println("Please enter your password:");
        String password = input.nextLine();
        System.out.println("Okay!\n" +
                "How can I help you? (1-2)");
        System.out.println("1-Change username\n2-Change password");
        int choice = input.nextInt();
        if (choice == 1)
        {
            changeUsername(username, password);
        }
        else if (choice == 2)
        {
            changePassword(username,password);
        }
        else
        {
            System.out.println("Invalid Input!");
        }
    }

    public boolean doesUserExist(String username, String password){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
            try {
                String line = reader.readLine();
                while (line != null)
                {
                    String users[] = line.split(",");
                    if (users[0].equals(username) && users[1].equals(password))
                    {
                        reader.close();;
                        return true;
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    //librarian related functions

    public void addLibrarian(){
        System.out.println("Please make a username:");
        String username = input.nextLine();
        System.out.println("Please make a password:");
        String password = input.nextLine();
        System.out.println("Please repeat the password again(For preventing make any mistake!):");
        String repeatPassword = input.nextLine();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("librarian.txt",true));
            if (!doesUserExist(username, password)) {
                if (password.equals(repeatPassword)) {
                    writer.write(username + "," + password + "\n");
                    System.out.println("Sign Up successfully!:)");
                } else {
                    System.out.println("Passwords don't match!");
                }
            } else {
                System.out.println("This username is already exists :(");
            }
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void removeLibrarian(){
        System.out.println("Please enter your username:");
        String username = input.nextLine();
        System.out.println("Please enter your password:");
        String password = input.nextLine();
        File inputFile = new File("librarian.txt");
        if(!inputFile.isFile())
        {
            System.out.println("File doesn't exist!");
        }
        else
        {
            File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");
            try {
                BufferedReader reader = new BufferedReader(new FileReader("librarian.txt"));
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(tempFile));
                    String line = reader.readLine();
                    while(line != null)
                    {
                        if (!line.trim().equals(username + "," + password))
                        {
                            writer.println(line);
                            writer.flush();
                        }
                        line = reader.readLine();
                    }
                    writer.close();
                    reader.close();

                    if (!inputFile.delete())
                    {
                        System.out.println("Could not delete file :(");
                        return;
                    }
                    else if (!tempFile.renameTo(inputFile))
                    {
                        System.out.println("Could not rename the temporary file :(");
                    }
                    else
                    {
                        System.out.println("\"" + username + "\"successfully got removed:)");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean searchLibrarian(String username, String password){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("librarian.txt"));
            try {
                String line = reader.readLine();
                while(line != null)
                {
                    String users[] = line.split(",");
                    if (users[0].equals(username) && users[1].equals(password))
                    {
                        reader.close();
                        return true;
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void updateLibrarian(){
        System.out.println("Please enter your username:");
        String username = input.nextLine();
        System.out.println("Please enter your password:");
        String password = input.nextLine();
        System.out.println("Okay!\n" +
                "How can I help you? (1-2)");
        System.out.println("1-Change username\n2-Change password");
        int choice = input.nextInt();
        if (choice == 1)
        {
            changeUsername(username, password);
        }
        else if (choice == 2)
        {
            changePassword(username,password);
        }
        else
        {
            System.out.println("Invalid Input!");
        }
    }

    public boolean doesLibrarianExist(String username, String password){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("librarian.txt"));
            try {
                String line = reader.readLine();
                while (line != null)
                {
                    String users[] = line.split(",");
                    if (users[0].equals(username) && users[1].equals(password))
                    {
                        reader.close();;
                        return true;
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public void changeUsername(String username, String password)
    {
        String path = null;
        if (searchUser(username, password))
        {
            path = "users.txt";
        }
        else if (searchLibrarian(username, password))
        {
            path = "librarian.txt";
        }
        else
        {
            System.err.println("This username doesn't exist!");
        }
        File myFile = new File(path);
        String filePath = myFile.getAbsolutePath();
        try {
            Scanner sc = new Scanner(new File(filePath));
            StringBuffer buffer = new StringBuffer();
            while(sc.hasNextLine())
            {
                buffer.append(sc.nextLine() + System.lineSeparator());
            }
            String fileContents = buffer.toString();
            sc.close();
            String oldLine = username + "," + password;
            String newUsername = newUsername();
            String newLine = newUsername + "," + password;
            fileContents = fileContents.replaceAll(oldLine,newLine);
            try {
                FileWriter writer = new FileWriter(filePath);
                writer.append(fileContents);
                writer.flush();
                writer.close();
                System.out.println("YOur username successfully changed:)");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void changePassword(String username, String password)
    {
        String path = null;
        if (searchUser(username, password))
        {
            path = "users.txt";
        }
        else if (searchLibrarian(username, password))
        {
            path = "librarian.txt";
        }
        File myFile = new File(path);
        String filePath = myFile.getAbsolutePath();
        try {
            Scanner sc = new Scanner(new File(filePath));
            StringBuffer buffer = new StringBuffer();
            while(sc.hasNextLine())
            {
                buffer.append(sc.nextLine() + System.lineSeparator());
            }
            String fileContents = buffer.toString();
            sc.close();
            String oldLine = username + "," + password;
            String newPassword = newPassword();
            String newLine = username + "," + newPassword;
            fileContents = fileContents.replaceAll(oldLine,newLine);
            try {
                FileWriter writer = new FileWriter(filePath);
                writer.append(fileContents);
                writer.flush();
                writer.close();
                System.out.println("Your password successfully changed:)");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public String newPassword()
    {
        System.out.println("Please enter a new password:");
        String newPassword = input.next();
        System.out.println("PLease confirm your new password(for preventing make any mistake!):");
        String repeatPassword = input.next();
        if (newPassword.equals(repeatPassword))
        {
            return newPassword;
        }
        else
        {
            return "The password that you have entered in repeat username section doesn't match with new one!";
        }
    }
    public String newUsername()
    {
        System.out.println("Please enter a new username:");
        String newUsername = input.next();
        System.out.println("PLease confirm your new username(for preventing make any mistake!):");
        String repeatUsername = input.next();
        if (newUsername.equals(repeatUsername))
        {
            return newUsername;
        }
        else
        {
            return "The username that you have entered in repeat username section doesn't match with new one!";
        }
    }
}