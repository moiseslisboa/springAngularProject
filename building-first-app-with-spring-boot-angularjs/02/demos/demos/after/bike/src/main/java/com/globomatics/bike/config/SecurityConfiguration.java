package com.globomatics.bike.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@EnableWebSecurity //tag that says "go ahead and enable security for the app. And how to do this is described by the class below
@Configuration //tell to spring boot it is a config class and should be run when container is started
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Value(value = "${auth0.apiAudience}")
	private String apiAudience;
	@Value(value = "${auth0.issuer}")
	private String issuer;

	
	
	// bulk of configuration happens here in this config block.
	// if you ever work with spring security you will work with some security configuration file
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    JwtWebSecurityConfigurer
        .forRS256(apiAudience, issuer)
        .configure(http)
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/api/v1/bikes").permitAll()
        .antMatchers(HttpMethod.GET, "/api/v1/bikes").hasAuthority("view:registrations")
        //.antMatchers(HttpMethod.GET, "/api/v1/bikes").permitAll()
        .antMatchers(HttpMethod.GET, "/api/v1/bikes/**").hasAuthority("view:registration")
        //.antMatchers(HttpMethod.GET, "/api/v1/bikes/**").permitAll()
        .anyRequest().authenticated();
	}

}