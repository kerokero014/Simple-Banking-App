import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private String accountNumber;
    private String accountHolderName;
    private double balance;
    private String password;
    private ArrayList<String> transactionHistory;

    public BankAccount(String accountNumber, String accountHolderName, double initialBalance, String password) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialBalance;
        this.password = password;
        this.transactionHistory = new ArrayList<>();
        this.transactionHistory.add("Account created with balance: " + initialBalance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: " + amount);
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: " + amount);
            System.out.println("Withdrew: " + amount);
        } else {
            System.out.println("Invalid withdraw amount.");
        }
    }
}

class Bank {
    private ArrayList<BankAccount> accounts;

    public Bank() {
        accounts = new ArrayList<>();
    }

    public void addAccount(BankAccount account) {
        accounts.add(account);
    }

    public BankAccount findAccount(String accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    public boolean deleteAccount(String accountNumber, String password) {
        BankAccount account = findAccount(accountNumber);
        if (account != null && account.validatePassword(password)) {
            accounts.remove(account);
            return true;
        }
        return false;
    }
}

public class BankingApp {
    private static Bank bank = new Bank();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    checkBalance();
                    break;
                case 5:
                    deleteAccount();
                    break;
                case 6:
                    viewTransactionHistory();
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nBanking System Menu:");
        System.out.println("1. Create a new account");
        System.out.println("2. Deposit money");
        System.out.println("3. Withdraw money");
        System.out.println("4. Check balance");
        System.out.println("5. Delete account");
        System.out.println("6. View transaction history");
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void createAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter account holder name: ");
        String accountHolderName = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (bank.findAccount(accountNumber) != null) {
            System.out.println("Account number already exists.");
            return;
        }

        BankAccount newAccount = new BankAccount(accountNumber, accountHolderName, initialBalance, password);
        bank.addAccount(newAccount);
        System.out.println("Account created successfully!");
    }

    private static void depositMoney() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = bank.findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (account.validatePassword(password)) {
                System.out.print("Enter amount to deposit: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                account.deposit(amount);
            } else {
                System.out.println("Invalid password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void withdrawMoney() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = bank.findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (account.validatePassword(password)) {
                System.out.print("Enter amount to withdraw: ");
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                account.withdraw(amount);
            } else {
                System.out.println("Invalid password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void checkBalance() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = bank.findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (account.validatePassword(password)) {
                System.out.println("Account balance: " + account.getBalance());
            } else {
                System.out.println("Invalid password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void deleteAccount() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        boolean success = bank.deleteAccount(accountNumber, password);
        if (success) {
            System.out.println("Account deleted successfully.");
        } else {
            System.out.println("Account not found or invalid password.");
        }
    }

    private static void viewTransactionHistory() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        BankAccount account = bank.findAccount(accountNumber);
        if (account != null) {
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if (account.validatePassword(password)) {
                System.out.println("Transaction history:");
                for (String transaction : account.getTransactionHistory()) {
                    System.out.println(transaction);
                }
            } else {
                System.out.println("Invalid password.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }
}
