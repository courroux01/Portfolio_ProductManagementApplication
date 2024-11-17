package ca.sheridan.alojadoa.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ProductDatabaseSecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSec) throws Exception {
        return httpSec
        		.authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/css/**", "/Auth/**", "/register", "/logout")
                        	.permitAll()
                        .requestMatchers("/ProductEdit/**", "/ProductDelete/**")
                        	.hasAuthority("ADMIN")
                        .requestMatchers("/ProductAdd/**")
                        	.hasAnyAuthority("ADMIN", "SELLER")
                        .requestMatchers("/ProductView/**", "/ProductSearch/**")
                        	.hasAnyAuthority("ADMIN", "SELLER", "BUYER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/index", true)
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout").permitAll()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                ).build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Use BCrypt for password encoding
    }

    @Bean
    UserDetailsService users() {
        PasswordEncoder encoder = passwordEncoder();

        // Define in-memory users with their roles
        UserDetails admin = User.builder()
            .username("userA")
            .password(encoder.encode("aaaa"))
            .authorities("ADMIN")
            .build();

        UserDetails seller = User.builder()
            .username("userS")
            .password(encoder.encode("ssss"))
            .authorities("SELLER")
            .build();

        UserDetails buyer = User.builder()
            .username("userB")
            .password(encoder.encode("bbbb"))
            .authorities("BUYER")
            .build();

        // Return the users
        return new InMemoryUserDetailsManager(admin, seller, buyer);
    }
}
