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
                    4. Remove A Book
                    5. Exit The Program
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
                case 3 -> System.out.println("Add A New Book");
                case 4 -> System.out.println("Remove A Book");
                case 5 -> exit = true;
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
            year = (yearString.isBlank()) ? null: Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid year. Please try again.");
            return;
        }

        List<Book> books = library.searchBooks(title, author, year);
        if (books.isEmpty()) System.out.println("No books found.");
        else books.forEach(System.out::println);
    }
}