package Batch02.SSF.Assessment.controllers;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import Batch02.SSF.Assessment.models.Cart;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShoppingCartController {

	//@Autowired
	//private PizzaService pizzaSvc;

	private Logger logger = Logger.getLogger(ShoppingCartController.class.getName());

	@GetMapping(path="/")
	public String getIndex(Model model, HttpSession sess) {
		sess.invalidate();
		model.addAttribute("cart", new Cart());
		return "view1";
	}
    
}
