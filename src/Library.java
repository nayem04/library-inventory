import java.util.ArrayList;
import java.util.List;

public class Library {
    public static Library instance;
    private final List<Book> books;

    private Library() {
        books = new ArrayList<>();

        /*books = Arrays.asList(new Book(1, "Debi", "author", 2014, "genre"),
                new Book(2, "Amar Bondhu Rashed", "author", 2005, "genre"),
                new Book(3, "Dipu Number 2", "author", 2000, "genre"));*/

    }

    public static Library getInstance() {
        if (instance == null) instance = new Library();
        return instance;
    }

    public List<Book> getBooks() {
        return books;
    }
}
