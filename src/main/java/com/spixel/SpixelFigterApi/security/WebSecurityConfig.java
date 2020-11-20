package com.spixel.SpixelFigterApi.security;

import com.spixel.SpixelFigterApi.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    // UserDetailsService o su implementacion, será usado para configurar el
    // DaoAuthenticationProvider
    @Autowired
    private UserDetailsServiceImp userDetailsService;

    @Autowired
    private AuthenticationEntryPoint entryPoint;

    @Bean
    public AuthTokenFilterJwt tokenFilterJwt() throws Exception {
        return new AuthTokenFilterJwt();
    }

    // Vamos a necesitar un passworEncoder para el DaoAuthenticationProvider.
    // Si no especificamos uno, usará texto plano por defecto.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(entryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/signin/**").permitAll()
                .antMatchers("/api/signon").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(this.tokenFilterJwt(), UsernamePasswordAuthenticationFilter.class); // filter, beforeFilter
    }
}
