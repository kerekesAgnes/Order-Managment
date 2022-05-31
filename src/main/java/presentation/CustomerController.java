package presentation;

import bll.CustomerBLL;
import model.Customer;

/**
 * CustomerController extends the AbstractController class
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class CustomerController extends AbstractController<Customer>{

    public CustomerController(CustomerView view, CustomerBLL bll) {
        super(view, bll);
    }
}
