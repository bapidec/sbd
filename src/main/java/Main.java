import entity.ClientEntity;
import jakarta.persistence.*;
import screen.ClientsScreen;
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

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Clients", new ClientsScreen());
        mainFrame.add(tabbedPane);
        mainFrame.pack();

        //clientView.showClientsDetails("Bartek", "Dec", "666666666", "gmail", "bialy");

    }
}
