package model;

/**
 * Represents an order
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class Order {
    private int idOrder;
    private int customerId;
    private int productId;
    private int quantity;

    public Order(){

    }

    public Order(int idOrder, int customerId, int productId, int quantity) {
        this.idOrder = idOrder;
        this.customerId = customerId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
