package view;

import javax.swing.*;
import java.awt.*;

public class EmployeeView extends JPanel implements EntityView{
    JTextField firstNameField = new JTextField();
    JTextField lastNameField = new JTextField();
    JTextField sexField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField phoneNumberField = new JTextField();
    JTextField dateOfBirthField = new JTextField();

    public EmployeeView(){
        super();
        GridLayout gridLayout = new GridLayout(0, 2);
        gridLayout.setVgap(1);
        super.setLayout(gridLayout);

        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        sexField.setEditable(false);
        emailField.setEditable(false);
        phoneNumberField.setEditable(false);
        dateOfBirthField.setEditable(false);

        super.add(new JLabel("First name: "));
        super.add(firstNameField);

        super.add(new JLabel("Last name: "));
        super.add(lastNameField);

        super.add(new JLabel("Sex: "));
        super.add(sexField);

        super.add(new JLabel("Email: "));
        super.add(emailField);

        super.add(new JLabel("Phone number: "));
        super.add(phoneNumberField);

        super.add(new JLabel("Date of birth: "));
        super.add(dateOfBirthField);
    }
    public void showEmployeeDetails(String firstName, String lastName, String sex, String phoneNumber, String email, String dateOfBirth){
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        sexField.setText(sex);
        emailField.setText(email);
        phoneNumberField.setText(phoneNumber);
        dateOfBirthField.setText(dateOfBirth);
    }

    @Override
    public void clear() {
        firstNameField.setText("");
        lastNameField.setText("");
        sexField.setText("");
        emailField.setText("");
        phoneNumberField.setText("");
        dateOfBirthField.setText("");
    }
}
