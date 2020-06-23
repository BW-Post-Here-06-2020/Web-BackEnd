package com.lambdaschool.subredditpredictor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	private static final String RESOURCE_ID = "resource_id";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(
				"/",
				"/h2-console/**",
				"/swagger-resources/**",
				"/swagger-resource/**",
				"/swagger-ui.html",
				"/v2/api-docs",
				"/webjars/**",
				"/createnewuser"
			)
			.permitAll()
			.antMatchers(
				HttpMethod.POST,
				"/users/**",
				"/data/**"
			)
			.hasAnyRole("ADMIN")
			.antMatchers(
				HttpMethod.DELETE,
				"/users/**",
				"/data/**"
			)
			.hasAnyRole("ADMIN")
			.antMatchers(
				HttpMethod.PUT,
				"/users/**",
				"/data/**"
			)
			.hasAnyRole("ADMIN")
			.antMatchers(
				"/users/**",
				// "/useremails/**",
				"/oauth/revoke-token",
				"/logout"
			)
			.authenticated()
			.antMatchers("/roles/**")
			.hasAnyRole("ADMIN")
			.and()
			.exceptionHandling()
			.accessDeniedHandler(new OAuth2AccessDeniedHandler());

		// http.requiresChannel().anyRequest().requiresSecure(); required for https

		http.csrf().disable();

		http.headers().frameOptions().disable();

		http.logout().disable();
	}
}
