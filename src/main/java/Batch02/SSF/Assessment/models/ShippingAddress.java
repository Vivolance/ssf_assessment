package Batch02.SSF.Assessment.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ShippingAddress {
    @Size(min=2, message="Name must be at least 2 characters long")
    private String name;
    @NotNull(message="Address is required!")
    private String address;

    public ShippingAddress() {

    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}