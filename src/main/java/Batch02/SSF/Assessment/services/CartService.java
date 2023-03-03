package Batch02.SSF.Assessment.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import Batch02.SSF.Assessment.models.Item;

@Service
public class CartService {
    /**
     * <option value="apple">Apple</option>
     * <option value="orange">Orange</option>
     * <option value="bread">Bread</option>
     * <option value="cheese">Cheese</option>
     * <option value="chicken">Chicken</option>
     * <option value="mineral_water">Mineral Water</option>
     * <option value="instant_noodles">Instant Noodles</option>
     */
    private String[] allowedItems = {"apple", "orange", "bread", "cheese", "chicken", "mineral_water", "instant_noodles"};
    private HashSet<String> allowedItemsSet = new HashSet(Arrays.asList(allowedItems));

    public boolean validItem(String itemName) {
        return this.allowedItemsSet.contains(itemName.toLowerCase());
    }

    public boolean validQuantity(int itemQuantity) {
        return itemQuantity > 0;
    }

    public List<ObjectError> validateItem(Item item) {
        List<ObjectError> errors = new ArrayList<>();
        FieldError error;

        if (!validItem(item.getName())) {
            error = new FieldError("item", "name"
                    , "We do not stock %s".formatted(item.getName()));
            errors.add(error);
        }

        if (!validQuantity(item.getQuantity())) {
            error = new FieldError("item", "quantity"
                    , "You must add at least one item");
            errors.add(error);
        }

        return errors;
    }
}
