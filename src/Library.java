public class Library {
    public static Library instance;

    private Library() {}

    public static Library getInstance() {
        if(instance == null) instance = new Library();
        return instance;
    }
}
