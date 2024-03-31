public class LibrarySystem {
    public static void main(String[] args) {
        Book book1 = new Book("ISBN123", "The Great Book", "John Doe", 3);
        Patron patron1 = new Patron("Patron1", "Alice");
        Librarian librarian1 = new Librarian("Librarian1", "Bob");

        // Simulate borrowing a book
        patron1.borrowBook(book1);
        System.out.println("Book borrowed by patron.");

        // Simulate returning a book
        patron1.returnBook(book1);
        System.out.println("Book returned by patron.");
    }
}
