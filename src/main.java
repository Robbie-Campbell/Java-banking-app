import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

class Banking{
    private static void bank_app() {
        try {
            File check_balance = new File("balance.txt");
            Scanner checker = new Scanner(check_balance);
            int current_balance = checker.nextInt();

            JLabel title = new JLabel("Welcome to the transaction app");
            JLabel input_prompt = new JLabel("Input amount: ");
            JTextField input_amount = new JTextField(20);
            JComboBox<String> type_of_transaction = new JComboBox<>();
            JButton enter_amount = new JButton("Enter");
            JLabel current_balance_display = new JLabel(String.format("Current balance is: %d", current_balance));

            /////////// GUI elements set up ///////////////////

            type_of_transaction.addItem("Withdrawal");
            type_of_transaction.addItem("Deposit");

            Font font = new Font(Font.SERIF, Font.BOLD, 24);
            Border raised = BorderFactory.createRaisedBevelBorder();

            JFrame main_frame = new JFrame("Transaction app");
            main_frame.setBounds(100, 100, 600, 400);
            main_frame.setLayout(null);
            main_frame.setResizable(false);
            main_frame.setVisible(true);

            JPanel title_panel = new JPanel();
            title_panel.setBounds(0, 0, 600, 70);
            main_frame.add(title_panel);
            title.setFont(font);
            title_panel.add(title);

            JPanel main_panel = new JPanel(new GridBagLayout());
            main_panel.setBounds(0, 67, 600, 300);
            main_panel.setBorder(raised);
            main_frame.add(main_panel);

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);
            constraints.gridx = 0;
            constraints.gridy = 0;

            main_panel.add(current_balance_display, constraints);

            constraints.gridy = 1;
            main_panel.add(input_prompt, constraints);

            constraints.gridx = 1;
            main_panel.add(input_amount, constraints);

            constraints.gridy = 2;
            constraints.gridx = 0;
            main_panel.add(type_of_transaction, constraints);

            constraints.gridx = 1;
            main_panel.add(enter_amount, constraints);
        }catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
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
                    case "cancel":
                        System.out.println("Thank you");
                        running = false;
                        return;
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
    public static void main(String[] args){
        bank_app();
    }
}
