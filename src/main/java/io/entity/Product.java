package io.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private String name;
    private BigDecimal price;
    private BigDecimal bail;
    private int id;

    public Product(String name, BigDecimal price, BigDecimal bail, int id) {
        this.name = name;
        this.price = price;
        this.bail = bail;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getBail() {
        return bail;
    }

    public void setBail(BigDecimal bail) {
        this.bail = bail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Id:" + id +
                "[" + name + "]  " +
                "Koszt:" + price + "zl  " +
                "Kaucja:" + bail + "zl  ";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
