package io.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class RentList {

    private Set<NewRentObject> products;
    private LocalDateTime creationTime;
    private String id;
    private String ownerId;

    public RentList(String ownerId) {
        products = new HashSet<>();
        creationTime = LocalDateTime.now();
        this.ownerId = ownerId;
    }

    public Set<NewRentObject> getProducts() {
        return products;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void createRent(Product product, int quantity) {
        Optional<NewRentObject> newRentObjectOpt = find(product);

        if (newRentObjectOpt.isEmpty()) {
            addNew(new NewRentObject(product, quantity));
        } else {
            NewRentObject obj = newRentObjectOpt.get();
            obj.addQuantity(quantity);
            update(obj);
        }
    }

    private Optional<NewRentObject> find(Product product) {
        return products.stream()
                .filter(newRentObject -> newRentObject.getProduct().equals(product))
                .findAny();
    }

    private void addNew(NewRentObject obj) {
        products.add(obj);
    }

    private void update(NewRentObject obj) {
        products.remove(obj);
        products.add(obj);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public BigDecimal getCost(){
        return products.stream()
                .map(prod -> prod.getProduct().getPrice().multiply(BigDecimal.valueOf(prod.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)                ;
    }

    public BigDecimal getBail(){
        return products.stream()
                .map(prod -> prod.getProduct().getBail().multiply(BigDecimal.valueOf(prod.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        products.forEach(prod -> builder.append(prod.toString()).append("\n"));
        builder.append("Suma kosztu: ").append(getCost().toString()).append("zl").append("\n");
        builder.append("Suma kaucji: ").append(getBail().toString()).append("zl").append("\n");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentList rentList = (RentList) o;
        return Objects.equals(id, rentList.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setProducts(Set<NewRentObject> products) {
        this.products = products;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}
