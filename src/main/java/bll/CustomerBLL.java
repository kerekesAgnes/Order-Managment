package bll;

import dao.CustomerDAO;
import model.Customer;

/**
 * CustomerBLL extends the AbstractBLL class
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class CustomerBLL extends AbstractBLL<Customer>{
    public CustomerBLL() {
        super(new CustomerDAO());
    }
}
