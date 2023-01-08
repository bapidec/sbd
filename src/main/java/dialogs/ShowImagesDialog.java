package dialogs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ShowImagesDialog extends JDialog{

    JButton nextButton = new JButton("Next");
    JButton previousButton = new JButton("Previous");
    JButton removeButton = new JButton("Remove");

    JPanel imagePanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    public ShowImagesDialog(JFrame frame) {
        super(frame, "Choose image");
        super.setVisible(true);
        super.setModal(true);
        super.setAlwaysOnTop(true);
        super.setModalityType(ModalityType.APPLICATION_MODAL);
        super.setLocationRelativeTo(null);
        super.setLayout(new BorderLayout());
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("images.jpg"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));

        buttonsPanel.add(previousButton);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(removeButton);

        this.add(picLabel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();
    }
}
