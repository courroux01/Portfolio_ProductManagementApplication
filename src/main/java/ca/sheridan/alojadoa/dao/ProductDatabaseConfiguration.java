package ca.sheridan.alojadoa.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configuration class for setting up the database connection.
 * 
 * Made by Abdullah Alojado
 */
@Configuration
public class ProductDatabaseConfiguration {

    // Creates a DataSource bean for connecting to MySQL database.
    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/productsdb");
        dataSource.setUsername("root");
        dataSource.setPassword("Sheridan");
        return dataSource;
    }

    // Bean definition for NamedParameterJdbcTemplate.
    // This will be used in the DatabaseAccess class to submit JDBC query strings with named parameters.
    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
