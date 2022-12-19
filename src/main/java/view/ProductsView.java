package view;

import javax.swing.*;
import java.awt.*;

public class ProductsView extends JPanel{

    JTextField productNameField = new JTextField();
    JTextField typeField = new JTextField();
    JTextField priceField = new JTextField();
    JTextField descriptionField = new JTextField();
    JTextField dateOfProductionField = new JTextField();
    JTextField discountField = new JTextField();
    JTextField genreField = new JTextField();
    JTextArea keywordsArea = new JTextArea();

    public ProductsView() {
        super();
        GridLayout gridLayout = new GridLayout(0, 2);
        gridLayout.setVgap(1);
        super.setLayout(gridLayout);

        productNameField.setEditable(false);
        typeField.setEditable(false);
        priceField.setEditable(false);
        descriptionField.setEditable(false);
        dateOfProductionField.setEditable(false);
        discountField.setEditable(false);
        genreField.setEditable(false);
        keywordsArea.setEditable(false);


        super.add(new JLabel("Name: "));
        super.add(productNameField);

        super.add(new JLabel("Type: "));
        super.add(typeField);

        super.add(new JLabel("Base price: "));
        super.add(priceField);

        super.add(new JLabel("Discount price: "));
        super.add(discountField);

        super.add(new JLabel("Description: "));
        super.add(descriptionField);

        super.add(new JLabel("Date of production: "));
        super.add(dateOfProductionField);


        super.add(new JLabel("Genre:  "));
        super.add(genreField);

        super.add(new JLabel("Keywords:  "));
        super.add(keywordsArea);




    }

    public void showProductDetails(String productName, String type, String price, String description, String dateOfProduction, String discount, String genre, String keywords) {
        productNameField.setText(productName);
        typeField.setText(type);
        priceField.setText(price);
        descriptionField.setText(description);
        dateOfProductionField.setText(dateOfProduction);
        discountField.setText(discount);
        genreField.setText(genre);
        keywordsArea.setText(keywords);

    }
}
