public final class BookFactory {
    public static Book createBook(int id, String title, String author, int year, String genre) {
        return new Book(id, title, author, year, genre);
    }
}
