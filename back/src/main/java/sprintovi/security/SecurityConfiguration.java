package sprintovi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

		authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
		authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
		return authenticationTokenFilter;
	}

	/*
	 * Definisanje globalne konfiguracije pristupa rutama.
	 *
	 * antMarchers - navode se rute kojima se pristupa (sablon ruta).
	 *
	 * permitAll - pustamo sve korisnike na rutu koja odgovara sablonu koji je
	 * definisan (ukljucujuci i neregistrovane korisnike)
	 *
	 * hasAuthority("ROLA") - pustamo sve korisnike sa rolom ROLA na rutu koja
	 * odgovara sablonu koji je definisan
	 *
	 * hasAnyAuthority("ROLA1", "ROLA2") - isto kao i hasAuthority ali nam da je
	 * mogucnost definisanja prava pristupa za vise od jedne grupe korisnika
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.headers().cacheControl().disable();
		httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests()

				.antMatchers("/api/users/login").permitAll().antMatchers("**").permitAll()

				.anyRequest().authenticated();

		// Custom JWT based authentication
		httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}

	/*
	 * Konfiguracija za CORS Definisanje koje zahteve pustamo ili proveravamo -
	 * zahtev sa kojom HTTP metodom, originom
	 */
	@Configuration
	public static class WebConfig implements WebMvcConfigurer {
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/api/stanja").allowedOrigins("http://localhost:3000")
					.allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH").allowCredentials(true)
					.maxAge(3600);

			registry.addMapping("/api/sprintovi").allowedOrigins("http://localhost:3000")
					.allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH").allowCredentials(true)
					.maxAge(3600);

			registry.addMapping("/api/users").allowedOrigins("http://localhost:3000")
					.allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH").allowCredentials(true)
					.maxAge(3600);

			registry.addMapping("/api/zadaci").allowedOrigins("http://localhost:3000")
					.allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
					.exposedHeaders("Total-Pages").allowCredentials(true).maxAge(3600);

			registry.addMapping("/**").allowedOrigins("*")
					.allowedMethods("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH").allowCredentials(true)
					.maxAge(3600);
		}
	}
}
