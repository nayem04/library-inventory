import common.exceptions.BookNotFoundException;
import common.exceptions.DuplicateBookException;

import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LibraryInventory {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Library library = Library.getInstance();

    public static void main(String[] args) {
        System.out.println("Welcome to the Library Inventory Management System.");

        boolean exit = false;
        while (!exit) {
            System.out.println("""
                    \nPlease enter a number between 1 to 5 to choose an option
                    1. Display Books
                    2. Search For Books
                    3. Add A New Book
                    4. Update A Book By ID
                    5. Remove A Book By ID
                    6. Exit The Program
                    """);

            int option;
            try {
                option = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number between 1 to 5 to choose an option");
                continue;
            } finally {
                scanner.nextLine();
            }

            switch (option) {
                case 1 -> displayBooks();
                case 2 -> searchBooks();
                case 3 -> addBook();
                case 4 -> updateBookByID();
                case 5 -> removeBookById();
                case 6 -> exit = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void displayBooks() {
        System.out.println("Display Books");
        List<Book> books = library.getBooks();
        if (books.isEmpty()) {
            System.out.println("There are no books in the library.");
        } else {
            books.stream()
                    .sorted(Comparator.comparing(Book::getTitle))
                    .forEach(System.out::println);
        }
    }

    private static void searchBooks() {
        System.out.println("Search For Books");
        System.out.print("Enter Title or enter: ");
        String title = scanner.nextLine();

        System.out.print("Enter Author or enter: ");
        String author = scanner.nextLine();

        System.out.print("Enter Publication Year or enter: ");
        String yearString = scanner.nextLine();
        Integer year;
        try {
            year = (yearString.isBlank()) ? null : Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid year. Please try again.");
            return;
        }

        List<Book> books = library.searchBooks(title, author, year);
        if (books.isEmpty()) System.out.println("No books found.");
        else books.forEach(System.out::println);
    }

    private static void addBook() {
        System.out.println("Add A New Book");
        try {
            System.out.print("Enter ID: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Title: ");
            String title = scanner.nextLine();

            System.out.print("Enter Author: ");
            String author = scanner.nextLine();

            System.out.print("Enter Publication Year: ");
            int year = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Genre: ");
            String genre = scanner.nextLine();

            Book book = BookFactory.createBook(id, title, author, year, genre);
            library.addBook(book);
            System.out.println("Book added successfully: " + book);
        } catch (DuplicateBookException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
            scanner.nextLine();
        }
    }

    private static void updateBookByID() {
        try {
            System.out.print("Enter Book ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter new Title or enter: ");
            String title = scanner.nextLine();

            System.out.print("Enter new Author or enter: ");
            String author = scanner.nextLine();

            System.out.print("Enter new Publication Year or enter: ");
            String yearInput = scanner.nextLine();
            int year = yearInput.isBlank() ? 0 : Integer.parseInt(yearInput);

            System.out.print("Enter new Genre or enter: ");
            String genre = scanner.nextLine();

            library.updateBookByID(id, title, author, year, genre);
            System.out.println("Book updated successfully.");
        } catch (BookNotFoundException bookNotFoundException) {
            System.out.println(bookNotFoundException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("Invalid input. Please try again.");
            scanner.nextLine();
        }
    }

    private static void removeBookById() {
        System.out.println("Remove A Book By ID");
        try {
            System.out.print("Enter Book ID to remove: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            library.removeBookById(id);
            System.out.println("Book removed successfully.");
        } catch (BookNotFoundException bookNotFoundException) {
            System.out.println(bookNotFoundException.getMessage());
        } catch (InputMismatchException inputMismatchException) {
            System.out.println("Invalid input. Please try again.");
            scanner.nextLine();
        }
    }
}