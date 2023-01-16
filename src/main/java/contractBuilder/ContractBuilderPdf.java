package contractBuilder;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import entity.ContractEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ContractBuilderPdf implements ContractBuilder{
    private final ContractEntity contract;
    private final JFrame frame;
    private Document document;
    private Paragraph title;
    private Paragraph content;
    private Paragraph signature;

    public ContractBuilderPdf(JFrame frame, ContractEntity contract) {
        this.contract = contract;
        this.frame = frame;
    }

    @Override
    public void buildTitle() {
        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 32, BaseColor.BLACK);
        title = new Paragraph("KONTRAKT\n\n", font);
    }

    @Override
    public void buildContents() {
        Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        content = new Paragraph(
                "Zatrudniony: " + contract.getEmployeeName(entityManager) + " " + contract.getEmployeeSurname(entityManager) + "\n\nOkres zatrudnienia: " + contract.getDateStart() + " do " + contract.getDateEnd() + "\n\nStanowisko: " + contract.getType() + "\n\n\n");
    }

    @Override
    public void buildSignature(String signatureText) {
        Font font = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
        signature = new Paragraph(signatureText);
    }

    public void getPdfDocument() {
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("contracts/contract_nr_" + contract.getContractId() + ".pdf"));
            document.open();
            if(title != null)
                document.add(title);
            if(content != null)
                document.add(content);
            if(signature != null)
                document.add(signature);
            document.close();
            JOptionPane.showMessageDialog(this.frame, "Generated Pdf document", "Pdf contract", JOptionPane.INFORMATION_MESSAGE);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
