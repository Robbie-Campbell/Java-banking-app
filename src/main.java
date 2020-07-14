import java.io.*;
import java.util.Scanner;

class Banking{
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            try {
                File check_balance = new File("balance.txt");
                Scanner checker = new Scanner(check_balance);
                int current_balance = checker.nextInt();
                String transaction_type;
                String past_tense_type;
                System.out.println("What kind of transaction do you need to perform?: ");
                Scanner input_type = new Scanner(System.in);
                String i = input_type.nextLine();
                switch (i) {

                    case "deposit":
                        System.out.println("Deposit selected.");
                        transaction_type = "deposit";
                        past_tense_type = "deposited";
                        break;
                    case "withdrawal":
                        System.out.println("Withdrawal selected");
                        transaction_type = "withdraw";
                        past_tense_type = "withdrawn";
                        break;
                    default:
                        System.out.println("Transaction cancelled.");
                        continue;
                }

                System.out.println("Your current balance is £" + current_balance);
                System.out.printf("How much money do you need to %s?: ", transaction_type);
                Scanner transaction = new Scanner(System.in);
                int amount = transaction.nextInt();
                if (transaction_type.equals("deposit")) {
                    if (amount > 0) {
                        FileWriter update_balance = new FileWriter("balance.txt");
                        update_balance.write(String.valueOf(current_balance + amount));
                        current_balance += amount;
                        update_balance.close();
                    } else {
                        System.out.println("Please enter a positive value");
                        continue;
                    }
                } else {
                    if (amount < current_balance && amount > 0) {
                        FileWriter update_balance = new FileWriter("balance.txt");
                        int withdraw_balance = current_balance - amount;
                        update_balance.write(String.valueOf(withdraw_balance));
                        current_balance = withdraw_balance;
                        update_balance.close();
                    } else {
                        System.out.println("Please enter a valid amount");
                        continue;
                    }
                }
                String confirmation = String.format("£%d %s, your new balance is £%d", amount, past_tense_type, current_balance);
                System.out.println(confirmation);
                running = false;
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}
