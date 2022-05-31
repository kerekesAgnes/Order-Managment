package start;

import bll.CustomerBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import dao.CustomerDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import presentation.*;

/**
 * Starts the OrderManagment application
 * which processes client orders for a warehouse
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class Start {
    public static void main(String[] args) throws IllegalAccessException {
        CustomerView customerView = new CustomerView();
        ProductView productView = new ProductView();
        OrderView orderView = new OrderView();

        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        OrderDAO orderDAO = new OrderDAO();

        customerView.updateTable(customerDAO.listing());
        productView.updateTable(productDAO.listing());
        orderView.updateTable(orderDAO.listing());

        CustomerBLL customerBLL = new CustomerBLL();
        ProductBLL productBLL = new ProductBLL();
        OrderBLL orderBLL = new OrderBLL();

        CustomerController customerController = new CustomerController(customerView, customerBLL);
        ProductController productController = new ProductController(productView, productBLL);
        OrderController orderController = new OrderController(orderView, orderBLL);

    }
}
