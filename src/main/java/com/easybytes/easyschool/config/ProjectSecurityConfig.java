package com.easybytes.easyschool.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

// from Spring Security 6.1 and Spring Boot 3.1.0 versions,
// the Spring Security framework team recommends using the Lambda DSL style
// for configuring security for APIs, web paths, etc.
@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,
                                                   HandlerMappingIntrospector introspector) throws Exception {
        // We can disable the same for now and enable it in the coming sections when we started generating CSRF tokens.
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        /* for testing H2-console, spring boot 2.22, spring security 6
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                )
                .headers(headers -> headers
                        .frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**"));

         */
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(mvcMatcherBuilder.pattern("/dashboard")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/displayMessages/**")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/api/**")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/closeMsg/**")).hasRole("ADMIN")
                        .requestMatchers(mvcMatcherBuilder.pattern("/displayProfile")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/updateProfile")).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern("/student/**")).hasRole("STUDENT")
                        .requestMatchers(mvcMatcherBuilder.pattern("")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/home")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/holidays/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/contact")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/saveMsg")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/courses")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/about")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/assets/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/login")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/logout")).permitAll()
                        .requestMatchers(mvcMatcherBuilder.pattern("/public/**")).permitAll()
                        // 迁移去MySQL了，不需要h2了
                        // .requestMatchers(PathRequest.toH2Console()).permitAll()
                        //.requestMatchers(mvcMatcherBuilder.pattern("/h2-console/**")).permitAll()
                )
                // .headers(headersConfigurer -> headersConfigurer
                //        .frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .csrf((csrf)->csrf
                        .ignoringRequestMatchers(mvcMatcherBuilder.pattern("/saveMsg"))
                        .ignoringRequestMatchers(mvcMatcherBuilder.pattern("/public/**"))
                        .ignoringRequestMatchers(mvcMatcherBuilder.pattern("/api/**"))
                        // 迁移去MySQL了，不需要h2了
                        // .ignoringRequestMatchers(PathRequest.toH2Console())
                        //.ignoringRequestMatchers(mvcMatcherBuilder.pattern("/h2-console/**"))
                )
                .formLogin(formLogin->formLogin.loginPage("/login")
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout->logout.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll()
                )//这段可有可无，是因为我们已经自定义了，但是留着也不影响，留着以后说不准会用到
                .httpBasic(Customizer.withDefaults());





        /**
         * Example 27

        // permit all requests inside the web application
        http.authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        // deny all requests inside the web application
        http.authorizeHttpRequests(authorize -> authorize
                        .anyRequest().denyAll())// HTTP ERROR 403
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
         */

        return http.build();
    }
    /*
    Example 29

    refer to link:
    https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/in-memory.html#page-title
    */

    /* before example 38
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users
                .username("user")
                .password("12345")
                .roles("USER")
                .build();
        UserDetails admin = users
                .username("admin")
                .password("54321")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


}
