package ca.sheridan.alojadoa.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;    
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridan.alojadoa.beans.Product;

import java.util.List;

/**
 * Repository class for accessing Product data in the database.
 * 
 * Made by Abdullah Alojado
 */
@Repository
public class ProductDatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    /**
     * Adds a product to the database.
     */
    public long addProduct(Product product) {
        String query = 
            "INSERT INTO Product (productCode, productBrand, productQuantity, productUnitPrice) " +
            "VALUES (:productCode, :productBrand, :productQuantity, :productUnitPrice)";
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource()
            .addValue("productCode", product.getProductCode())
            .addValue("productBrand", product.getProductBrand())
            .addValue("productQuantity", product.getProductQuantity())
            .addValue("productUnitPrice", product.getProductUnitPrice());
        
        return jdbc.update(query, namedParameters);
    }

    /**
     * Retrieves all products from the database.
     */
    public List<Product> selectAllProducts() {
        String query = "SELECT * FROM Product";
        return jdbc.query(query, new BeanPropertyRowMapper<>(Product.class));
    }

    /**
     * Retrieves a product by its ID from the database.
     */
    public Product selectProductById(int id) {
        String query = "SELECT * FROM Product WHERE id = :id";
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);
        
        List<Product> result =  jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Product.class));
        
        return result.get(0);
    }
    
    /**
     * Updates a product in the database by its ID.
     */
    public int updateProductById(int id, Product product) {
        String query = 
            "UPDATE Product " +
            "SET productCode = :productCode, " +
            "productBrand = :productBrand, " + 
            "productQuantity = :productQuantity, " +
            "productUnitPrice = :productUnitPrice " +
            "WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("productCode", product.getProductCode());
        namedParameters.addValue("productBrand", product.getProductBrand());
        namedParameters.addValue("productQuantity", product.getProductQuantity());
        namedParameters.addValue("productUnitPrice", product.getProductUnitPrice());
        namedParameters.addValue("id", id);
        
        return jdbc.update(query, namedParameters);
    }

    /**
     * Deletes a product from the database by its ID.
     */
    public long deleteProductById(int id) {
        String query = "DELETE FROM Product WHERE id = :id";

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);

        return jdbc.update(query, namedParameters);
    }

    /**
     * Retrieves products from the database by their brand.
     */
    public List<Product> selectProductsByBrand(String brand) {
        String query = "SELECT * FROM Product WHERE productBrand = :productBrand";
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("productBrand", brand);
        
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Product.class));
    }

    /**
     * Retrieves products with a quantity less than the specified amount.
     */
    public List<Product> selectProductsBelowQuantity(int quantity) {
        String query = "SELECT * FROM Product WHERE productQuantity < :quantity";
        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("quantity", quantity);
        
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Product.class));
    }

}
