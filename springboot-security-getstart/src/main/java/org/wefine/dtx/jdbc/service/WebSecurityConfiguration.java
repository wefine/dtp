package org.wefine.dtx.jdbc.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.wefine.dtx.jdbc.dao.CustomerRepository;
import org.wefine.dtx.jdbc.entity.Customer;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerRepository customerRepository;

    @PostConstruct
    public void initCustomer() {
        final PasswordEncoder encoder = passwordEncoder();

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setUsername("admin");
        customer.setPassword(encoder.encode("111111"));
        customer.setRole("ADMIN");
        customerRepository.save(customer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            Customer customer = customerRepository.findOneByUsername(username);
            if (customer != null) {
                return User.withUsername(customer.getUsername()).password(customer.getPassword()).roles(customer.getRole()).build();
            } else {
                throw new UsernameNotFoundException("用户不存在 '" + username + "'");
            }
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .userDetailsService(userDetailsService())
                .formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/api/customer/**").hasRole("ADMIN")
                // anyRequest 需要靠后
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}
