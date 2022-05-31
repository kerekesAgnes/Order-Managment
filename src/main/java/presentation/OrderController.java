package presentation;

import bll.OrderBLL;
import com.itextpdf.text.DocumentException;
import model.Order;

import java.io.FileNotFoundException;

/**
 * OrderController extends the AbstractController class and
 * handles the create bill event
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class OrderController extends AbstractController<Order> {
    public OrderController(OrderView view, OrderBLL bll) {
        super(view, bll);

        view.addBillButtonListener(a -> {
            try {
                bll.createBill();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        });
    }
}
