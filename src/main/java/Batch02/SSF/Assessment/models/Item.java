package Batch02.SSF.Assessment.models;

import jakarta.validation.constraints.Min;

public class Item {

    private String name;

    @Min(value=1, message="You must add at least 1 item")
    private int quantity;

    public Item() {

    }

    public void addQuantity(int quantity) {
        this.quantity = this.quantity + quantity;
    }

    public Item(String item, int quantity) {
        this.name = item;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}