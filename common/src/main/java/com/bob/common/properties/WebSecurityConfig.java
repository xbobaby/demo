/*
package com.bob.common.properties;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] exclusivePaths = {"/main/login", "/login", "/css/**", "/js/**", "/fonts/**"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(exclusivePaths).permitAll().
                anyRequest().
                authenticated().
                and().
                formLogin().
                loginPage("/main/login").
                defaultSuccessUrl("/main/index");
    }

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() {
        return new MyUserDetailServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean());
    }
}
*/
