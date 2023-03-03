package Batch02.SSF.Assessment.services;

import org.springframework.stereotype.Service;

import Batch02.SSF.Assessment.models.Item;
import Batch02.SSF.Assessment.models.Quotation;
import Batch02.SSF.Assessment.models.ShoppingCart;

@Service
public class InvoiceService {

    public static Float calculateItemPrice(Item item, Float itemPrice) {
        return item.getQuantity() * itemPrice;
    }

    public static Float calculateInvoice(ShoppingCart cart, Quotation quotation) {
        Float total = 0.0f;
        for (Item item: cart.items) {
            String itemName = item.getName().toLowerCase();
            Float itemPrice = calculateItemPrice(item, quotation.getQuotation(itemName));
            total += itemPrice;
        }
        return total;
    }

}