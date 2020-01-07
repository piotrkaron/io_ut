package io.entity;

import java.util.Objects;

public class NewRentObject {

    private Product product;
    private int quantity;

    public NewRentObject(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity){
        this.quantity+=quantity;
    }

    @Override
    public String toString() {
        return product.toString() + ": " + quantity +"szt.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewRentObject that = (NewRentObject) o;
        return product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, quantity);
    }

}
