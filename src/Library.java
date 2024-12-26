import common.exceptions.BookNotFoundException;
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

    public void updateBookByID(int id, String title, String author, int year, String genre) throws BookNotFoundException {
        Book book = books.stream().filter(b -> b.getId() == id).findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found."));
        if (title != null && !title.isBlank()) book.setTitle(title);
        if (author != null && !author.isBlank()) book.setAuthor(author);
        if (year > 0) book.setPublicationYear(year);
        if (genre != null && !genre.isBlank()) book.setGenre(genre);
    }

    public void removeBookById(int id) throws BookNotFoundException {
        Book book = books.stream().filter(b -> b.getId() == id).findFirst()
                .orElseThrow(() -> new BookNotFoundException("Book not found."));
        books.remove(book);
    }
}
