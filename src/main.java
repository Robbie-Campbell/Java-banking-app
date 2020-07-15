import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DecimalFormat;
import java.util.Scanner;

abstract class Banking implements ActionListener {
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private static void bank_app() {
        try {
            File check_balance = new File("balance.txt");
            Scanner checker = new Scanner(check_balance);
            final float[] current_balance = {checker.nextFloat()};

            JLabel title = new JLabel("Welcome to the transaction app");
            JLabel input_prompt = new JLabel("Input amount: ");
            JLabel select = new JLabel("Select transaction type: ");
            JTextField input_amount = new JTextField(10);
            JComboBox<String> type_of_transaction = new JComboBox<>();
            JButton enter_amount = new JButton("Enter");
            JButton another_transaction = new JButton("Perform another transaction?");
            JLabel current_balance_display = new JLabel(String.format("Current balance is: £%s", df2.format(current_balance[0])));

            /////////// GUI elements set up ///////////////////

            type_of_transaction.addItem("");
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
            main_panel.add(select, constraints);

            constraints.gridy = 3;
            constraints.gridx = 0;
            main_panel.add(type_of_transaction, constraints);

            constraints.gridx = 1;
            main_panel.add(enter_amount, constraints);

            constraints.gridy = 4;
            constraints.gridx = 0;
            another_transaction.setVisible(false);
            main_panel.add(another_transaction, constraints);

            enter_amount.addActionListener((ActionListener) -> {
                    if (input_amount.getText().isBlank())
                    {
                        current_balance_display.setText("Please enter a value");
                        input_amount.setText("");
                        enter_amount.setEnabled(false);
                    }
                    another_transaction.setVisible(true);
                    if (type_of_transaction.getSelectedItem() == "Deposit"){
                        float value = Float.parseFloat(input_amount.getText());
                        try {
                            if (value > 0) {
                                FileWriter update_balance = new FileWriter("balance.txt");
                                update_balance.write(String.valueOf(current_balance[0] + value));
                                current_balance[0] += value;
                                update_balance.close();
                                current_balance_display.setText(String.format("Congratulations! Your new balance is: £%s", df2.format(current_balance[0])));
                                input_amount.setText("");
                                enter_amount.setEnabled(false);
                            }else{
                                current_balance_display.setText("Please enter a valid amount.");
                            }
                        }catch (IOException a) {
                            System.out.println("An error occurred.");
                            a.printStackTrace();
                        }
                    }
                    else if (type_of_transaction.getSelectedItem() == "Withdrawal"){
                        float value = Float.parseFloat(input_amount.getText());
                        try {
                            if (value > 0 && (current_balance[0] - value) > 0) {
                                FileWriter update_balance = new FileWriter("balance.txt");
                                update_balance.write(String.valueOf(current_balance[0] - value));
                                current_balance[0] -= value;
                                update_balance.close();
                                current_balance_display.setText(String.format("Congratulations! Your new balance is: £%s",df2.format(current_balance[0])));
                                input_amount.setText("");
                                enter_amount.setEnabled(false);
                            }else {
                                current_balance_display.setText("Please enter a valid amount.");
                                input_amount.setText("");
                                enter_amount.setEnabled(false);
                            }
                        }catch (IOException a) {
                            System.out.println("An error occurred.");
                            a.printStackTrace();
                        }
                    }
                    else if (type_of_transaction.getSelectedItem() == ""){
                        current_balance_display.setText("Please enter the type of transaction.");
                        input_amount.setText("");
                        enter_amount.setEnabled(false);
                    }
            });
            another_transaction.addActionListener((ActionListener) ->
                    enter_amount.setEnabled(true)
            );
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        bank_app();
    }
}