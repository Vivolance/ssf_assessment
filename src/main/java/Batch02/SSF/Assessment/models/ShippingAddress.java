package Batch02.SSF.Assessment.models;

import jakarta.validation.constraints.Size;

public class ShippingAddress {
    @Size(min=2, message="Name must be at least 2 characters long")
    private String name;
    private String address;

    public ShippingAddress() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}