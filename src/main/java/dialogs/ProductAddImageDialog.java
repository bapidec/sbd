package dialogs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProductAddImageDialog extends JDialog {
    private final JButton addImageButton = new JButton("Add");
    private final JButton newImageButton = new JButton("New");
    private final JButton deleteImageButton = new JButton("Delete");
    JButton nextButton = new JButton("Next");
    JButton previousButton = new JButton("Previous");

    JPanel imagePanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    public ProductAddImageDialog(JFrame frame) {
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

        buttonsPanel.add(addImageButton);
        buttonsPanel.add(previousButton);
        buttonsPanel.add(nextButton);
        buttonsPanel.add(newImageButton);
        buttonsPanel.add(deleteImageButton);

        this.add(picLabel);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        this.pack();
    }
}

