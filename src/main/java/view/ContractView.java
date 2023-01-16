package view;

import screen.ContractScreen;

import javax.swing.*;
import java.awt.*;

public class ContractView extends JPanel {
    JTextField paymentAmountField = new JTextField();
    JTextField dateStartField = new JTextField();
    JTextField dateEndField = new JTextField();
    JTextField typeField = new JTextField();
    JTextField employeeField = new JTextField();

    public ContractView(){
        super();
        GridLayout gridLayout = new GridLayout(0, 2);
        gridLayout.setVgap(1);
        super.setLayout(gridLayout);

        paymentAmountField.setEditable(false);
        dateStartField.setEditable(false);
        dateEndField.setEditable(false);
        typeField.setEditable(false);
        employeeField.setEditable(false);

        super.add(new JLabel("Payment amount: "));
        super.add(paymentAmountField);
        super.add(new JLabel("Contract starting: "));
        super.add(dateStartField);
        super.add(new JLabel("Contract ending: : "));
        super.add(dateEndField);
        super.add(new JLabel("Type of work: "));
        super.add(typeField);
        super.add(new JLabel("Employee name: "));
        super.add(employeeField);
    }
    public void showContractDetails(String paymentAmount, String dateStart, String dateEnd, String type, String employee){
        paymentAmountField.setText(paymentAmount);
        dateStartField.setText(dateStart);
        dateEndField.setText(dateEnd);
        typeField.setText(type);
        employeeField.setText(employee);
    }

    public void clear() {
        paymentAmountField.setText("");
        dateStartField.setText("");
        dateEndField.setText("");
        typeField.setText("");
        employeeField.setText("");
    }
}
