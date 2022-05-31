package bll;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dao.CustomerDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Customer;
import model.Order;
import model.Product;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * OrderBLL extends the AbstractBLL class
 * adds an order only if it is valid and
 * creates bill for orders
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class OrderBLL extends AbstractBLL<Order>{
    private ProductDAO productDAO = new ProductDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private CustomerDAO customerDAO = new CustomerDAO();

    public OrderBLL() {
        super(new OrderDAO());
    }

    @Override
    public void add(Order order) throws BLLException {
        Product product = productDAO.findById(order.getProductId());
        if(product.getNrInStock() >= order.getQuantity()){
            orderDAO.insert(order);
            product.setNrInStock(product.getNrInStock() - order.getQuantity());
            productDAO.update(product);
        } else {
            throw new BLLException("Sorry, product out of stock!");
        }
    }

    public void createBill() throws FileNotFoundException, DocumentException {
        List<Order> orders = orderDAO.listing();

        for(Order order : orders){
            Document bill = new Document();
            String docName = "Bill" + order.getIdOrder() + ".pdf";
            PdfWriter.getInstance(bill, new FileOutputStream(docName));
            bill.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            Product product = productDAO.findById(order.getProductId());
            Customer customer = customerDAO.findById(order.getCustomerId());

            String name = "Customer: " + customer.getName();
            Chunk customerName = new Chunk(name, font);

            String productName = "Product: " + product.getName();
            Chunk productChunk = new Chunk(productName, font);

            String quantity = "Quantity: " + order.getQuantity();
            Chunk quantityChunk = new Chunk(quantity, font);

            int price = order.getQuantity() * product.getPrice();
            String p = "Price: " + price;
            Chunk priceChunk = new Chunk(p, font);

            bill.add(customerName);
            bill.add(new Paragraph("\n"));
            bill.add(productChunk);
            bill.add(new Paragraph("\n"));
            bill.add(quantityChunk);
            bill.add(new Paragraph("\n"));
            bill.add(priceChunk);
            bill.add(new Paragraph("\n"));

            bill.close();
        }

    }
}
