package io.entity;

import java.util.Objects;

public class Address {

    private String street;
    private int number;
    private int postalNumber;
    private String town;

    public Address(String street, int number, int postalNumber, String town) {
        this.street = street;
        this.number = number;
        this.postalNumber = postalNumber;
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPostalNumber() {
        return postalNumber;
    }

    public void setPostalNumber(int postalNumber) {
        this.postalNumber = postalNumber;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return number == address.number &&
                postalNumber == address.postalNumber &&
                street.equals(address.street) &&
                town.equals(address.town);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, postalNumber, town);
    }
}
