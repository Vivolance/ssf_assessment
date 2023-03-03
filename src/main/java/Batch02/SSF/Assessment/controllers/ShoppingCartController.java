package Batch02.SSF.Assessment.controllers;

import Batch02.SSF.Assessment.models.Item;
import Batch02.SSF.Assessment.models.ShoppingCart;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;


@Controller
class PurchaseOrderController {
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
        cart.addItem(item);
        model.addAttribute("item", new Item());
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
