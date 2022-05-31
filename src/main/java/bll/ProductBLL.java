package bll;

import dao.ProductDAO;
import model.Product;

/**
 * ProductBLL extends the AbstractBLL class
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class ProductBLL extends AbstractBLL<Product>{
    public ProductBLL() {
        super(new ProductDAO());
    }
}
