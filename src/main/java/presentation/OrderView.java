package presentation;

import model.Order;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * OrderView extends the abstract View class and
 * adds a new button (Create bill button) to the GUI
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class OrderView extends View<Order>{
    private JButton billButton = new JButton("Create bill");

    public OrderView(){
        super();
        this.addButton(billButton);
    }

    public void addBillButtonListener(ActionListener actionListener){
        this.billButton.addActionListener(actionListener);
    }
}
