package Batch02.SSF.Assessment.controllers;

import Batch02.SSF.Assessment.models.Invoice;
import Batch02.SSF.Assessment.models.Item;
import Batch02.SSF.Assessment.models.Quotation;
import Batch02.SSF.Assessment.models.ShippingAddress;
import Batch02.SSF.Assessment.models.ShoppingCart;
import Batch02.SSF.Assessment.services.CartService;
import Batch02.SSF.Assessment.services.InvoiceService;
import Batch02.SSF.Assessment.services.QuotationService;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class PurchaseOrderController {
    @Autowired
    private CartService cartService;
    @Autowired
    private QuotationService quotationService;

    @GetMapping(path="/")
    public String getShoppingCart(Model model, HttpSession session) {
        ShoppingCart cart = getOrCreateCart(session);
        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);
        return "view1";
    }

    @PostMapping(path="/addItem")
    public String addItem(Model model, HttpSession session, @ModelAttribute("item") Item item, BindingResult bindings) {
        ShoppingCart cart = getOrCreateCart(session);
        // add the old, unedited item and cart into the model to prevent "items cannot be found on null" errors
        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);

        if (bindings.hasErrors())
            return "view1";

        List<ObjectError> errors = cartService.validateItem(item);
        
        if (!errors.isEmpty()) {
            for (ObjectError err: errors)
                bindings.addError(err);
            return "view1";
        }

        // Once the item is valid, add it to the cart, and reattach it to the Model object again
        cart.addItem(item);
        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);

        return "view1";
    }

    @GetMapping(path="/shippingaddress")
    public String getShippingAddress(Model model, HttpSession session, @ModelAttribute("cart") ShoppingCart dummyCart, BindingResult bindings) {

        ShoppingCart cart = getCart(session);
        System.out.println("Items in cart: " + cart.items.size());

        // if customer attempts to navigate to view 2 without cart, controller should redisplay view 1
        if (!CartService.validCart(cart)) {
            model.addAttribute("item", new Item());
            cart = new ShoppingCart();
            model.addAttribute("cart", cart);
            session.setAttribute("cart", cart);
            return "view1";
        }

        ShippingAddress newShippingAddress = new ShippingAddress();

        model.addAttribute("cart", cart);
        session.setAttribute("cart", cart);
        model.addAttribute("shipping", newShippingAddress);
        session.setAttribute("shipping", newShippingAddress);
        return "view2";
    }

    @PostMapping(path="/quote")
    public String getQuote(Model model, HttpSession session, @ModelAttribute("shipping") ShippingAddress dummyShipping, BindingResult bindings) {
        ShoppingCart cart = getCart(session);
        System.out.println("Items in cart: " + cart.items.size());

        ArrayList<String> itemsInCart = PurchaseOrderController.getItemsInCart(cart);

        Quotation quotation = null;
        Float invoiceTotal = 0.0f;

        try {
            quotation = quotationService.getQuotations(itemsInCart);
            invoiceTotal = InvoiceService.calculateInvoice(cart, quotation);
            Invoice invoice = new Invoice(quotation.getQuoteId(), dummyShipping.getName(), dummyShipping.getAddress(), invoiceTotal);
            model.addAttribute("invoice", invoice);
            // Clear the contents of the customer cart by invalidating the session
            session.invalidate();
            return "view3";
        } catch (Exception e) {
            // If the QSys returns a quotation error, then error should be displayed in view 2 along with the
            // previously filled name and address
            session.setAttribute("cart", cart);
            session.setAttribute("shipping", dummyShipping);
            model.addAttribute("cart", cart);
            model.addAttribute("shipping", dummyShipping);

            ObjectError qSysError = new ObjectError("shipping", e.getMessage());
            bindings.addError(qSysError);
            return "view2";
        }
    }

    public static ArrayList<String> getItemsInCart(ShoppingCart cart) {
        ArrayList<String> itemsInCart = new ArrayList<String>();
        for (Item item: cart.items) {
            itemsInCart.add(item.getName());
        }
        return itemsInCart;
    }

    public ShoppingCart getCart(HttpSession session) {
        return (ShoppingCart) session.getAttribute("cart");
    }

    public ShoppingCart getOrCreateCart(HttpSession session) {
        ShoppingCart cart = getCart(session);
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}