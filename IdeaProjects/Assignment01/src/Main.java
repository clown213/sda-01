import java.util.ArrayList;
import java.util.List;

// Abstract Account class applying the Single Responsibility Principle (SRP) -
// it is only responsible for account-related actions (balance management, deposits, and withdrawals).
abstract class Account {
    protected double balance; // Protected to allow access in derived classes
    private String id; // Unique identifier for each account

    // Constructor establishes a single responsibility: initializing the account
    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.id = "ACC" + System.nanoTime(); // Generates a unique identifier
    }

    // Deposit method applies SRP by only managing deposit actions
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    // Abstract withdraw method ensures subclasses provide their own withdrawal behaviors, adhering to the Liskov Substitution Principle (LSP)
    public abstract boolean withdraw(double amount);

    // Get balance method applies SRP by only handling balance inquiries
    public double getBalance() {
        return balance;
    }

    // Get ID method applies SRP by only providing ID information
    public String getId() {
        return id;
    }
}

// SavingsAccount class applying Liskov Substitution Principle (LSP) - it can replace Account without affecting the behavior.
class SavingsAccount extends Account {
    public SavingsAccount(double initialBalance) {
        super(initialBalance);
    }

    // Overridden withdraw method ensures LSP compliance, as SavingsAccount can substitute Account
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

// CheckingAccount class also applying LSP - it can be used anywhere an Account is expected.
class CheckingAccount extends Account {
    public CheckingAccount(double initialBalance) {
        super(initialBalance);
    }

    // Withdraw method follows the same contract as the Account class, ensuring LSP compliance
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

// CreditCardAccount class demonstrates Interface Segregation Principle (ISP) -
// it does not force the Account to have credit-related methods.
class CreditCardAccount extends Account {
    private double creditLimit;

    public CreditCardAccount(double initialBalance, double creditLimit) {
        super(initialBalance);
        this.creditLimit = creditLimit;
    }

    // Overridden withdraw method tailored for CreditCardAccount, applying both LSP and ISP
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && balance + creditLimit >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Specific method for CreditCardAccount demonstrates ISP - not all accounts need a credit limit
    public double getCreditLimit() {
        return creditLimit;
    }
}

// Bank class demonstrates the Dependency Inversion Principle (DIP) -
// it depends on the abstract Account rather than concrete account implementations.
class Bank {
    private List<Account> accounts = new ArrayList<>(); // Dependency on abstract class, not concrete ones

    // Demonstrates Open/Closed Principle (OCP) - new account types can be added without altering existing code
    public Account createAccount(String type, double initialBalance) {
        Account newAccount = null;
        switch (type.toLowerCase()) {
            case "savings":
                newAccount = new SavingsAccount(initialBalance);
                break;
            case "checking":
                newAccount = new CheckingAccount(initialBalance);
                break;
            case "credit":
                newAccount = new CreditCardAccount(initialBalance, 5000); // Default credit limit
                break;
        }
        if (newAccount != null) {
            accounts.add(newAccount); // Adheres to DIP by adding the high-level Account abstraction
        }
        return newAccount;
    }

    // Method for finding an account by ID, applying SRP by focusing solely on account retrieval
    public Account findAccount(String accountId) {
        return accounts.stream().filter(a -> a.getId().equals(accountId)).findFirst().orElse(null);
    }
}

// Main class to test the system, primarily for demonstration and not part of the banking system classes
class BankTest {
    public static void main(String[] args) {
        Bank bank = new Bank(); // Create a new bank instance

        // Creating and testing a savings account
        Account savings = bank.createAccount("savings", 1000);
        System.out.println("Initial Savings Balance: " + savings.getBalance());
        savings.deposit(200);
        System.out.println("After Deposit Savings Balance: " + savings.getBalance());
        boolean withdrawalSuccess = savings.withdraw(500);
        System.out.println("After Withdrawal Savings Balance: " + savings.getBalance() + ". Withdrawal success: " + withdrawalSuccess);

        // Creating and testing a checking account
        Account checking = bank.createAccount("checking", 500);
        System.out.println("\nInitial Checking Balance: " + checking.getBalance());
        checking.deposit(150);
        System.out.println("After Deposit Checking Balance: " + checking.getBalance());
        withdrawalSuccess = checking.withdraw(300);
        System.out.println("After Withdrawal Checking Balance: " + checking.getBalance() + ". Withdrawal success: " + withdrawalSuccess);

        // Creating and testing a credit card account
        Account credit = bank.createAccount("credit", 300);
        System.out.println("\nInitial Credit Card Balance: " + credit.getBalance());
        credit.deposit(100);
        System.out.println("After Deposit Credit Card Balance: " + credit.getBalance());
        withdrawalSuccess = credit.withdraw(400); // Assuming the credit limit allows this
        System.out.println("After Withdrawal Credit Card Balance: " + credit.getBalance() + ". Withdrawal success: " + withdrawalSuccess);
    }
}
