package com.businessassistantbcn.mydata.config;

import com.businessassistantbcn.mydata.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.HttpStatusRequestRejectedHandler;
import org.springframework.security.web.firewall.RequestRejectedHandler;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class HttpSecurityConfig {

    @Profile("dev")
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    @EnableWebSecurity
    public static class DisableSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.authorizeRequests().anyRequest().permitAll();
        }
    }

    @Profile("pro")
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    @EnableWebSecurity
    public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private PropertiesConfig config;
        @Autowired
        private JwtAuthenticationFilter jwtFilter;
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.csrf().disable();
            http.headers().xssProtection().and().contentSecurityPolicy("script-src 'self'");
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            http.authorizeRequests().anyRequest().authenticated();
            http.exceptionHandling().authenticationEntryPoint(
                    new BasicAuthenticationEntryPoint() {
                        @Override
                        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
                                throws IOException {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().print(config.getErr());
                        }
                    }
            );
        }
    }

    //Doble slash produce un error 400
    @Bean
    RequestRejectedHandler requestRejectedHandler() {
        // sends an error response with a configurable status code (default is 400 BAD_REQUEST)
        // we can pass a different value in the constructor
        return new HttpStatusRequestRejectedHandler();
    }
}
