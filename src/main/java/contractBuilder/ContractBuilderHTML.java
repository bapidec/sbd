package contractBuilder;

import entity.ContractEntity;
import entityFactory.DefaultEntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ContractBuilderHTML implements ContractBuilder {
    private final ContractEntity contract;
    private final JFrame frame;
    private String htmlString;

    public ContractBuilderHTML(JFrame frame, ContractEntity contract) {
        this.contract = contract;
        this.frame = frame;
        File htmlTemplateFile = new File("template.html");
        try {
            htmlString = FileUtils.readFileToString(htmlTemplateFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void buildTitle() {
        this.htmlString = this.htmlString.replace("$title", "KONTRAKT");
    }

    @Override
    public void buildContents() {
        EntityManager entityManager = DefaultEntityManagerFactory.getInstance().createEntityManager();
        this.htmlString = this.htmlString.replace(
                "$content",
                "Zatrudniony: " + contract.getEmployeeName(entityManager) + " " + contract.getEmployeeSurname(entityManager) + "<br><br>Okres zatrudnienia: " + contract.getDateStart() + " do " + contract.getDateEnd() + "<br><br>Stanowisko: " + contract.getType() + "<br><br>");
    }

    @Override
    public void buildSignature(String signature) {
        this.htmlString = this.htmlString.replace("$signature", signature);
    }

    public void getHTMLDocument() {
        File newHtmlFile = new File("contracts/contract_nr_"+contract.getContractId()+".html");
        try {
            FileUtils.writeStringToFile(newHtmlFile, htmlString);
            JOptionPane.showMessageDialog(this.frame, "Generated HTML document", "HTML contract", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
