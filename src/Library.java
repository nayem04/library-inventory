import common.exceptions.DuplicateBookException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Library {
    public static Library instance;
    private final List<Book> books;

    private Library() {
        books = new ArrayList<>();
    }

    public static Library getInstance() {
        if (instance == null) instance = new Library();
        return instance;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> searchBooks(String title, String author, Integer year) {
        if (title.isBlank() && author.isBlank() && year == null)
            return new ArrayList<>();
        Stream<Book> bookStream = (year == null) ?
                books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title) || b.getAuthor().equalsIgnoreCase(author)) :
                books.stream().filter(b -> b.getTitle().equalsIgnoreCase(title) || b.getAuthor().equalsIgnoreCase(author) || b.getPublicationYear() == year);
        return bookStream.collect(Collectors.toList());
    }

    public void addBook(Book book) throws DuplicateBookException {
        if (books.stream().anyMatch(b -> b.getId() == book.getId()))
            throw new DuplicateBookException("A book with this ID already exists.");
        books.add(book);
    }
}
