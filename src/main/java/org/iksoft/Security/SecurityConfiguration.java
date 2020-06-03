package org.iksoft.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author IK
 */

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfiguration(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests()
            .antMatchers("/admin/**"). hasRole("ADMIN")
            .antMatchers("/customer/**").hasRole("USER")
            .antMatchers("/user/**").authenticated()
            .antMatchers("/order/detailed").authenticated()
            .antMatchers("/invoice/detailed").authenticated()
            .antMatchers("/**").permitAll()
        .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/login-success")
                .failureUrl("/login?error")
        .and()
            .logout()
                .logoutSuccessUrl("/login?logout")
        .and().httpBasic();
    }

    @Bean
    PasswordEncoder getPasswordEncoder(){
        return new PasswordHashEncoder();
    }
}
