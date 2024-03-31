import java.util.ArrayList;
import java.util.List;

public class Patron {
    private String patronId;
    private String name;
    private List<Book> borrowedBooks = new ArrayList<>();

    public Patron(String patronId, String name) {
        this.patronId = patronId;
        this.name = name;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
        book.checkoutBook();
    }

    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.returnBook();
        }
    }

    // Add getters for the attributes if needed for other operations
}
