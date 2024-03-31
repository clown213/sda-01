public class Book {
    private String ISBN;
    private String title;
    private String author;
    private int availableCopies;

    public Book(String ISBN, String title, String author, int availableCopies) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
    }

    public void checkoutBook() {
        if (availableCopies > 0) {
            availableCopies--;
        } else {
            System.out.println("No copies available for checkout.");
        }
    }

    public void returnBook() {
        availableCopies++;
    }

    // Add getters for the attributes if needed for other operations
}
