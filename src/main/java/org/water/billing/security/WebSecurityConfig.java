package org.water.billing.security;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;  
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;  
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;  
 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.water.billing.security.support.CustomUserDetailsService;
import org.water.billing.security.support.LoginSuccessHandler;
import org.water.billing.security.support.MyFilterSecurityInterceptor;  
  

@Configuration  
@EnableWebSecurity  
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {  
	@Autowired  
    private MyFilterSecurityInterceptor mySecurityFilter;  
      
    @Autowired  
    private CustomUserDetailsService customUserDetailsService;  
      
    @Override  
    public AuthenticationManager authenticationManagerBean() throws Exception {  
       
    return super.authenticationManagerBean();  
       
    }  

    @Override  
    protected void configure(HttpSecurity http) throws Exception {  
        http
        .csrf().disable()
        .headers().disable()
        .addFilterBefore(mySecurityFilter, FilterSecurityInterceptor.class)
        .authorizeRequests()
        .antMatchers("/css/*").permitAll()
        .antMatchers("/js/*").permitAll()
        .antMatchers("/images/*").permitAll()
        .antMatchers("/files/*").permitAll()
        .antMatchers("/rest/*").permitAll()
        .antMatchers("/logout").permitAll()
        .anyRequest().authenticated()  
        .and()  
        .formLogin()  
        .loginPage("/login")      
        .permitAll()  
        .successHandler(loginSuccessHandler()) 
        .and()  
        .logout()  
        .logoutSuccessUrl("/login")  
        .permitAll()  
        .invalidateHttpSession(true)  
        .and()  
        .rememberMe()
        .tokenValiditySeconds(86400);  
    }  
 
	public void configure(WebSecurity web) throws Exception {  
            super.configure(web);  
    }  
   
	@Autowired  
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {       
    	auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());  
        auth.eraseCredentials(false);
    }  
      
    @Bean  
    public BCryptPasswordEncoder passwordEncoder() {  
        return new BCryptPasswordEncoder(4);  
    }  
  
    @Bean  
    public LoginSuccessHandler loginSuccessHandler(){  
        return new LoginSuccessHandler();  
    } 
    
    public static void main(String[] args) {
    	System.out.println(new BCryptPasswordEncoder(4).encode("pass"));
    }
}  
