public class Book {
    private String name;
    private String author;
    private String year_of_publish;
    private String ISBN;
    private String number_of_book = "0";

//    Book(String name, String author, String year_of_publish, String ISBN)
//    {
//        this.name = name;
//        this.author = author;
//        this.year_of_publish = year_of_publish;
//        this.ISBN = ISBN;
//    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear_of_publish(String year_of_publish) {
        this.year_of_publish = year_of_publish;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public String getName()
    {
        return name;
    }
    public String getAuthor()
    {
        return author;
    }
    public String getYear_of_publish()
    {
        return year_of_publish;
    }
    public String getISBN()
    {
        return ISBN;
    }

    public void setNumber_of_book(int number_of_book) {
        this.number_of_book = String.valueOf(number_of_book);
    }

    public String getNumber_of_book() {
        return number_of_book;
    }
//Book should contain name,author,year of publish and ISBN
}
