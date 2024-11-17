package ca.sheridan.alojadoa.controllers;

import ca.sheridan.alojadoa.beans.Product;
import ca.sheridan.alojadoa.dao.ProductDatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller class for managing Product operations.
 * 
 * Made by Abdullah Alojado
 */
@Controller
public class ProductController {

    @Autowired
    private ProductDatabaseAccess pda;
    
    // Return the index page
    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/login")
	String getLogin() {
		return "Auth/login";
	}
    
    // Display the product add form
    @GetMapping("/ProductAdd/getAddProduct")
    public String getAddProduct(Model model) {
        model.addAttribute("product", new Product());
        return "ProductAdd/productAddForm";
    }

    // Handle adding a new product
    @PostMapping("/ProductAdd/postAddProduct")
    public String postAddProduct(Model model, @ModelAttribute Product product) {
        long rowsUpdated = pda.addProduct(product);
        model.addAttribute("message", this.getMessage(rowsUpdated, "added"));
        return "Miscellaneous/productQueryOutcome";
    }

    // Display all products
    @GetMapping("/ProductView/getViewProducts")
    public String getViewProducts(Model model) {
        model.addAttribute("products", pda.selectAllProducts());
        return "ProductView/productView";
    }
    
    // Display all products
    @GetMapping("/ProductView/getViewEditableProducts")
    public String getViewEditableProducts(Model model) {
        model.addAttribute("products", pda.selectAllProducts());
        return "ProductEdit/productViewEditable";
    }

    // Display the product edit form for a specific product
    @GetMapping("/ProductEdit/getEditProduct/{id}")
    public String getEditProduct(Model model, @PathVariable int id) {
        Product product = pda.selectProductById(id);
        model.addAttribute("product", product);
        return "ProductEdit/productEditForm";
    }

    // Handle editing a product
    @PostMapping("/ProductEdit/postEditProduct")
    public String postEditProduct(Model model, @ModelAttribute Product product) {
        long rowsUpdated = pda.updateProductById(product.getId(), product);
        model.addAttribute("message", this.getMessage(rowsUpdated, "updated"));
        return "Miscellaneous/productQueryOutcome";
    }

    // Handle deleting a product
    @GetMapping("/ProductDelete/getDeleteProduct/{id}")
    public String deleteProduct(Model model, @PathVariable int id) {
        long rowsUpdated = pda.deleteProductById(id);
        model.addAttribute("message", this.getMessage(rowsUpdated, "deleted"));
        return "Miscellaneous/productQueryOutcome";
    }

    // Display the search form for products by brand
    @GetMapping("/ProductSearch/getSearchProductsByBrand")
    public String getSearchProductsByBrand() {
        return "ProductSearch/productSearchFormByBrand";
    }

    // Handle searching products by brand
    @PostMapping("/ProductSearch/postSearchProductsByBrand")
    public String postSearchProductsByBrand(
            Model model, 
            @RequestParam String productBrand
    ) {
        List<Product> products = pda.selectProductsByBrand(productBrand);
        String message = "Found these products by brand: " + productBrand;
        
        model.addAttribute("products", products);
        model.addAttribute("message", message);
        
        return "ProductSearch/productViewAfterSearch";
    }

    // Display the search form for products by quantity
    @GetMapping("/ProductSearch/getSearchProductsByQuantity")
    public String getSearchProductsByQuantity() {
        return "ProductSearch/productSearchFormByQuantity";
    }

    // Handle searching products by quantity
    @PostMapping("/ProductSearch/postSearchProductsByQuantity")
    public String postSearchProductsByQuantity(
            Model model, 
            @RequestParam String productQuantity
    ) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Current user authorities: " + authentication.getAuthorities());
        
        List<Product> products = pda.selectProductsBelowQuantity(Integer.parseInt(productQuantity));
        String message = "Found these products below quantity: " + productQuantity;
        
        model.addAttribute("products", products);
        model.addAttribute("message", message);
        
        return "ProductSearch/productViewAfterSearch";
    }

    // Return a message based on query success or failure
    private String getMessage(long rowsUpdated, String queryType) {
        return rowsUpdated > 0 
            ? "Success! The product has been successfully " + queryType + " to the database."
            : "Failure! The product was not " + queryType + " to the database.";
    }
}
