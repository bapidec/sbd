package view;

import javax.swing.*;
import java.awt.*;

public class ProductGenreView extends JPanel implements EntityView{
    JTextField nameField = new JTextField();
    JTextArea desctiptionArea = new JTextArea();

    public ProductGenreView() {
        super();
        GridLayout gridLayout = new GridLayout(0, 2);
        gridLayout.setVgap(1);
        super.setLayout(gridLayout);

        nameField.setEditable(false);
        desctiptionArea.setEditable(false);

        super.add(new JLabel("Name: "));
        super.add(nameField);

        super.add(new JLabel("Description: "));
        super.add(desctiptionArea);
    }

    public void showClientsDetails(String name, String desc) {
        nameField.setText(name);
        desctiptionArea.setText(desc);
    }

    public void showProductGenreDetails(String name, String description) {
        nameField.setText(name);
        desctiptionArea.setText(description);
    }
    @Override
    public void clear() {

    }
}

