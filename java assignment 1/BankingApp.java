import java.util.Scanner;
public class BankingApp {
    private Account[] accounts;
    private int count;
    private int nextAccountNumber;
    private Scanner scanner;

    public BankingApp(int capacity) {
        accounts = new Account[capacity];
        count = 0;
        nextAccountNumber = 1001; // starting account number
        scanner = new Scanner(System.in);
    }

    private void ensureCapacity() {
        if (count >= accounts.length) {
            Account[] newArr = new Account[accounts.length * 2];
            for (int i = 0; i < accounts.length; i++) newArr[i] = accounts[i];
            accounts = newArr;
        }
    }

    private Account findAccount(int accNumber) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountNumber() == accNumber) return accounts[i];
        }
        return null;
    }

    public void createAccount() {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter initial deposit amount: ");
        double initial = readDouble();
        if (initial < 0) {
            System.out.println("Initial deposit must be non-negative. Aborting account creation.");
            return;
        }
        System.out.print("Enter email address: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();

        ensureCapacity();
        Account acc = new Account(nextAccountNumber, name, initial, email, phone);
        accounts[count++] = acc;
        System.out.println("Account created successfully with Account Number: " + nextAccountNumber);
        nextAccountNumber++;
    }

    public void performDeposit() {
        System.out.print("Enter account number: ");
        int accNum = readInt();
        Account acc = findAccount(accNum);
        if (acc == null) { System.out.println("Account not found."); return; }
        System.out.print("Enter amount to deposit: ");
        double amt = readDouble();
        if (acc.deposit(amt)) {
            System.out.println("Deposit successful. New balance: " + acc.getBalance());
        }
    }

    public void performWithdrawal() {
        System.out.print("Enter account number: ");
        int accNum = readInt();
        Account acc = findAccount(accNum);
        if (acc == null) { System.out.println("Account not found."); return; }
        System.out.print("Enter amount to withdraw: ");
        double amt = readDouble();
        if (acc.withdraw(amt)) {
            System.out.println("Withdrawal successful. New balance: " + acc.getBalance());
        }
    }

    public void showAccountDetails() {
        System.out.print("Enter account number: ");
        int accNum = readInt();
        Account acc = findAccount(accNum);
        if (acc == null) { System.out.println("Account not found."); return; }
        acc.displayAccountDetails();
    }

    public void updateContact() {
        System.out.print("Enter account number: ");
        int accNum = readInt();
        Account acc = findAccount(accNum);
        if (acc == null) { System.out.println("Account not found."); return; }
        System.out.print("Enter new email (leave blank to keep same): ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter new phone (leave blank to keep same): ");
        String phone = scanner.nextLine().trim();
        acc.updateContactDetails(email.isEmpty() ? null : email, phone.isEmpty() ? null : phone);
        System.out.println("Contact details updated.");
    }

    private int readInt() {
        while (true) {
            String s = scanner.nextLine();
            try {
                return Integer.parseInt(s.trim());
            } catch (Exception e) {
                System.out.print("Invalid number. Try again: ");
            }
        }
    }

    private double readDouble() {
        while (true) {
            String s = scanner.nextLine();
            try {
                return Double.parseDouble(s.trim());
            } catch (Exception e) {
                System.out.print("Invalid amount. Try again: ");
            }
        }
    }

    public void mainMenu() {
        while (true) {
            System.out.println("\nWelcome to the Banking Application!");
            System.out.println("1. Create a new account");
            System.out.println("2. Deposit money");
            System.out.println("3. Withdraw money");
            System.out.println("4. View account details");
            System.out.println("5. Update contact details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int ch = readInt();
            switch (ch) {
                case 1: createAccount(); break;
                case 2: performDeposit(); break;
                case 3: performWithdrawal(); break;
                case 4: showAccountDetails(); break;
                case 5: updateContact(); break;
                case 6: System.out.println("Exiting. Goodbye!"); scanner.close(); return;
                default: System.out.println("Invalid choice. Try again.");
            }
        }
    }

    public static void main(String[] args) {
        BankingApp app = new BankingApp(5);
        app.mainMenu();
    }
}
class Account {
    private int accountNumber;
    private String accountHolderName;
    private double balance;
    private String email;
    private String phoneNumber;

    public Account(int accountNumber, String accountHolderName, double initialDeposit, String email, String phoneNumber) {
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = initialDeposit;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Deposit method: amount must be positive
    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit failed: amount must be positive.");
            return false;
        }
        balance += amount;
        return true;
    }

    // Withdraw method: amount > 0 and sufficient balance
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal failed: amount must be positive.");
            return false;
        }
        if (amount > balance) {
            System.out.println("Withdrawal failed: insufficient balance.");
            return false;
        }
        balance -= amount;
        return true;
    }

    public void displayAccountDetails() {
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Account Holder : " + accountHolderName);
        System.out.println("Balance        : " + balance);
        System.out.println("Email          : " + email);
        System.out.println("Phone Number   : " + phoneNumber);
    }

    public void updateContactDetails(String email, String phoneNumber) {
        if (email != null && !email.trim().isEmpty()) this.email = email;
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) this.phoneNumber = phoneNumber;
    }

    // Getters
    public int getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
}
