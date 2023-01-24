import entity.ClientEntity;
import jakarta.persistence.*;
import screen.*;
import view.ClientView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("FIGURKI");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuDelete = new JMenu("Delete");
        menuDelete.add(new JMenuItem("Image"));
        menuDelete.add(new JMenuItem("Keyword"));

        menuBar.add(menuDelete);
        mainFrame.setJMenuBar(menuBar);


        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clients", new ClientsScreen());
        tabbedPane.addTab("Employees", new EmployeeScreen());
        tabbedPane.addTab("Places", new PlacesScreen());
        tabbedPane.addTab("Suppliers", new SuppliersScreen());
        tabbedPane.addTab("Genres", new ProductGenresScreen());
        tabbedPane.addTab("Contracts", new ContractScreen());
        //tabbedPane.addTab("Orders", new OrdersScreen());
        tabbedPane.addTab("Products", new ProductsScreen());
        mainFrame.add(tabbedPane);
        mainFrame.pack();

        //clientView.showClientsDetails("Bartek", "Dec", "666666666", "gmail", "bialy");

    }
}
