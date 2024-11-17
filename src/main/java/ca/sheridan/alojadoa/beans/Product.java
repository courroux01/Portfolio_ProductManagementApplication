package ca.sheridan.alojadoa.beans;

import java.io.Serializable;

/**
 * Product Model
 * 
 * Made by Abdullah Alojado
 *
 * Represents a product entity with details like code, brand, quantity, and unit price.
 */

public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String productCode;
    private String productBrand;
    private Integer productQuantity;
    private Double productUnitPrice;

    // Default constructor
    public Product() {
    }

    // Get product ID
    public int getId() {
        return id;
    }

    // Set product ID
    public void setId(int productId) {
        this.id = productId;
    }

    // Get product code
    public String getProductCode() {
        return productCode;
    }

    // Set product code
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    // Get product brand
    public String getProductBrand() {
        return productBrand;
    }

    // Set product brand
    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    // Get product quantity
    public Integer getProductQuantity() {
        return productQuantity;
    }

    // Set product quantity
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    // Get product unit price
    public Double getProductUnitPrice() {
        return productUnitPrice;
    }

    // Set product unit price
    public void setProductUnitPrice(Double productUnitPrice) {
        this.productUnitPrice = productUnitPrice;
    }

    // String representation of the product object
    @Override
    public String toString() {
        return "Product{" +
               "id=" + id +
               ", productCode='" + productCode + '\'' +
               ", productBrand='" + productBrand + '\'' +
               ", productQuantity=" + productQuantity +
               ", productUnitPrice=" + productUnitPrice +
               '}';
    }
}
