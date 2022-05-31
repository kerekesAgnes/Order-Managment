package presentation;

import bll.ProductBLL;
import model.Product;

/**
 * ProductController extends the AbstractController class
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class ProductController extends AbstractController<Product> {

    public ProductController(ProductView view, ProductBLL bll) {
        super(view, bll);
    }
}
