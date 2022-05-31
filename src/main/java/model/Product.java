package model;

/**
 * Represents a product
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class Product {
    private int idProduct;
    private String name;
    private int price;
    private int nrInStock;

    public Product(){

    }

    public Product(int idProduct, String name, int price, int nrInStock) {
        this.idProduct = idProduct;
        this.name = name;
        this.price = price;
        this.nrInStock = nrInStock;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNrInStock() {
        return nrInStock;
    }

    public void setNrInStock(int nrInStock) {
        this.nrInStock = nrInStock;
    }
}
