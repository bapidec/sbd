package contractBuilder;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import entity.ContractEntity;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ContractBuilderPdf implements ContractBuilder{
    private final ContractEntity contract;
    private final JFrame frame;
    private Document document;
    private Chunk title;
    private Chunk content;
    private Chunk signature;

    public ContractBuilderPdf(JFrame frame, ContractEntity contract) {
        this.contract = contract;
        this.frame = frame;
    }

    @Override
    public void buildTitle() {
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        title = new Chunk("Hello World", font);
    }

    @Override
    public void buildContents() {

    }

    @Override
    public void buildSignature(String signature) {

    }

    public void getPdfDocument() {
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("contracts/contract_nr_" + contract.getContractId() + ".pdf"));
            document.open();
            document.add(title);
            document.add(content);
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
