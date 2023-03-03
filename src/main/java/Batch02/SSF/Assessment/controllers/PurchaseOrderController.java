package Batch02.SSF.Assessment.controllers;

import Batch02.SSF.Assessment.models.Item;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;



@Controller
class PurchaseOrderController {
    
    @Autowired
    private CartService cartService;

    @GetMapping(path="/")
    public String getShoppingCart(Model model, HttpSession session) {
        // session.invalidate();
        ShoppingCart cart = getOrCreateCart(session);
        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);
        return "view1";
    }

    @PostMapping(path="/addItem")
    public String addItem(Model model, HttpSession session, @Valid Item item, BindingResult bindings) {
        ShoppingCart cart = getOrCreateCart(session);
        // add the old, unedited item and cart into the model to prevent "items cannot be found on null" errors
        model.addAttribute("item", new Item());
        model.addAttribute("cart", cart);

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

        return "view1";
    }

    public ShoppingCart getOrCreateCart(HttpSession session) {
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}