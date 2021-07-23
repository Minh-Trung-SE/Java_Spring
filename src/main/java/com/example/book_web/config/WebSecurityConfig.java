package com.example.book_web.config;

import com.example.book_web.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServices userServices;

    public WebSecurityConfig(UserServices userServices) {
        this.userServices = userServices;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userServices).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Disable other url
        http.csrf().disable();

        http.authorizeRequests().antMatchers("/", "/home").permitAll();

        http.authorizeRequests().antMatchers(HttpMethod.GET, "/loginSuccess")
                .access("hasRole('ROLE_USER')");

        http.authorizeRequests().and().formLogin()
                .loginProcessingUrl("/user/login")
                .loginPage("/home")
                .usernameParameter("userPhone")
                .passwordParameter("userPassword")
                .defaultSuccessUrl("/loginSuccess")
                .failureUrl("/home");
    }
}
