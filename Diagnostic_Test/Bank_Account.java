//Bank Account Class (Python or Java):
// Implement a BankAccount class with the following:

// Properties: account_number, holder_name, balance

// Methods: deposit(amount), withdraw(amount), get_balance()

// Withdraw should not allow balance to go negative.
// (Tests: OOP basics, encapsulation, class design).
import java.util.Scanner;

class Bank_Account  //class name
{
    int balance = 100;      //initializing account balance
    void deposit(int dep)       //deposit function
    {
        System.out.println("Deposited money of Rs." +dep);
        this.balance += dep;        //updating balance
    }

    void withdraw(int amt)      //withdraw function
    {
        if ((this.balance - amt) < 0){      //checking for insufficient funds
            System.out.println("Insufficient Funds! Please Try Again!");
        }
        else{
            System.out.println("Amount Withdrawn Rs." +amt);
            this.balance -= amt;        //updating balance
        }
    }

    void get_balance()      //fucntion to get balance
    {
        System.out.println("Current Balance: " +this.balance);
    }

    public static void main (String arg[]){
        Bank_Account b = new Bank_Account();
        Scanner sc = new Scanner(System.in);
        int op;
        while(true)
        {
            System.out.println("\nMenu\n1. Deposit\n2. Withdraw\n3. Check Balance\n4. Exit");
            op = sc.nextInt();
            switch(op)
            {
                case 1: System.out.println("Enter the amount to be Deposited: ");
                b.deposit(sc.nextInt());
                break;
                case 2: System.out.println("Enter the amount to be Withdrawn: ");
                b.withdraw(sc.nextInt());
                break;
                case 3: b.get_balance(); break;
                case 4: System.exit(0);
                default: System.out.println("Illegal Option Selected!\nPlease Select a new Option");
            }
        }
    }
}