package nl.novi.cannoliworld.config;

import nl.novi.cannoliworld.filter.JwtRequestFilter;
import nl.novi.cannoliworld.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService)
     .passwordEncoder(passwordEncoder);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .cors().and()
                .csrf().disable()
                .authorizeRequests()

                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()

                .antMatchers(HttpMethod.GET, "/users", "/users/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/all").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/*").permitAll()
                .antMatchers(HttpMethod.POST, "/users/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/users/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/users/delete/*").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/persons", "/persons/").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/persons/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/persons/*").permitAll()
                .antMatchers(HttpMethod.POST, "/persons/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/persons/*").permitAll()
                .antMatchers(HttpMethod.DELETE, "/persons/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.GET, "/cannolis/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/cannolis/*/image").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/cannolis/**").hasRole("ADMIN")
                
                .antMatchers(HttpMethod.GET,  "/deliveryRequests/all").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,  "/deliveryRequests/mine").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/deliveryRequests/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.GET,  "/deliveryRequests/**").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.PUT,  "/deliveryRequests/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/deliveryRequests/**").hasRole("ADMIN")

                .antMatchers(HttpMethod.PUT, "/images/upload").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/images/download/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/images/delete/**").hasRole("ADMIN")

                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        cfg.setAllowedOriginPatterns(List.of("http://localhost:*", "http://127.0.0.1:*"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("Authorization","Content-Type","X-Requested-With","Accept","Origin"));
        cfg.setExposedHeaders(List.of("Authorization","Location"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
