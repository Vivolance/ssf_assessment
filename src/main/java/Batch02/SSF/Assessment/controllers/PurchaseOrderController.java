package Batch02.SSF.Assessment.controllers;

import Batch02.SSF.Assessment.models.Item;
import Batch02.SSF.Assessment.models.ShippingAddress;
import Batch02.SSF.Assessment.models.ShoppingCart;
import Batch02.SSF.Assessment.services.CartService;
import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;


@Controller
class PurchaseOrderController {
    @Autowired
    private CartService cartService;

    @GetMapping(path="/")
    public String getShoppingCart(Model model, HttpSession session) {
//        if (!session.isNew()) {
//            session.invalidate();
//        }
        ShoppingCart cart = getOrCreateCart(session);
        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);
        return "view1";
    }

    @PostMapping(path="/addItem")
    public String addItem(Model model, HttpSession session, @ModelAttribute("item") Item item, BindingResult bindings) {
        ShoppingCart cart = getOrCreateCart(session);
        // add the old, unedited item and cart into the model to prevent "items cannot be found on null" errors
        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);

        if (bindings.hasErrors())
            return "view1";

        List<ObjectError> errors = cartService.validateItem(item);
        
        // Temporary logging
        // System.out.println("Errors: " + errors.size());

        if (!errors.isEmpty()) {
            for (ObjectError err: errors)
                bindings.addError(err);
            return "view1";
        }

        // Once the item is valid, add it to the cart, and reattach it to the Model object again
        cart.addItem(item);
        model.addAttribute("cart", cart);

        return "view1";
    }

    @GetMapping(path="/shippingaddress")
    public String getShippingAddress(Model model, HttpSession session, @ModelAttribute("cart") ShoppingCart cart, BindingResult bindings) {
        System.out.println("cart: " + cart.items.size());

        // if customer attempts to navigate to view 2 without cart, controller should redisplay view 1
        if (!CartService.validCart(cart)) {
            model.addAttribute("item", new Item());
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
            return "view1";
        }

        session.setAttribute("cart", cart);
        session.setAttribute("shippingaddress", new ShippingAddress());
        return "view2";
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