package model;

/**
 * Represents a customer
 *
 * @author Kerekes Agnes
 * @version 1.0
 * @since 2021-06-30
 */

public class Customer {
    private int idCustomer;
    private String name;
    private String email;

    public Customer (){

    }

    public Customer(int idCustomer, String name, String email){
        this.idCustomer = idCustomer;
        this.name = name;
        this.email = email;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return "id: " + idCustomer + " name: " + name + " email: " + email;
    }
}
