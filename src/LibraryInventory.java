import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LibraryInventory {
    public static void main(String[] args) {
        System.out.println("Welcome to the Library Inventory Management System.");

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        Library library = Library.getInstance();

        while (!exit) {
            System.out.println("""
                    \nPlease enter a number between 1 to 5 to choose an option
                    1. Display Books
                    2. Add A New Book
                    3. Search For A Book
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
                case 1 -> {
                    System.out.println("Display Books");
                    displayBooks(library);
                }
                case 2 -> System.out.println("Add A New Book");
                case 3 -> System.out.println("Search For A Book");
                case 4 -> System.out.println("Remove A Book");
                case 5 -> exit = true;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void displayBooks(Library library) {
        List<Book> books = library.getBooks();
        if (books.isEmpty()) {
            System.out.println("There are no books in the library.");
        } else {
            books.stream()
                    .sorted(Comparator.comparing(Book::getTitle))
                    .forEach(System.out::println);
        }
    }
}