package Batch02.SSF.Assessment.models;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;

public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message="You forgot to select a pizza")
	private String item;

	@NotNull(message="Must specify a pizza size")
	private String quantity;

    

    public String getItem() {
        return item;
    }



    public void setItem(String item) {
        this.item = item;
    }



    public String getQuantity() {
        return quantity;
    }



    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }



    @Override
	public String toString() {
		return "Cart{Item=%s, Quantity=%s}".formatted(item, quantity);
	}

}
