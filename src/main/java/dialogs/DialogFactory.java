package dialogs;

import javax.swing.*;
import java.util.Objects;

public class DialogFactory{

    JFrame frame;
    JButton button;
    String title;


    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void getDialog(String dialogType){
        if(Objects.equals(dialogType, "Client")){
            new ClientDialog(frame, button, title);
            return;
        }
        if(Objects.equals(dialogType, "Employee")){
            new EmployeeDialog(frame, button, title);
            return;
        }
        if(Objects.equals(dialogType, "Place")){
            new PlacesDialog(frame, button, title);
            return;
        }
        if(Objects.equals(dialogType, "Supplier")){
            new SupplierDialog(frame, title);
            return;
        }
        if(Objects.equals(dialogType, "Genre")){
            new ProductGenreDialog(frame, title);
            return;
        }
        if(Objects.equals(dialogType, "Contract")){
            new ContractDialog(frame, title);
            return;
        }
        if(Objects.equals(dialogType, "Order")){
            new OrderDialog(frame, title);
            return;
        }
        if(Objects.equals(dialogType, "Product")){
            new ProductDialog(frame, title);
        }
    }
}
