package ar.edu.uno.sehda.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception{
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


 /*   @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }*/

  /*  @Override //METODO PARA AGREGAR NUEVOS USUARIOS
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers(HttpMethod.POST).permitAll()
                .antMatchers(HttpMethod.PUT).permitAll()
                .antMatchers(HttpMethod.DELETE).permitAll()
                .antMatchers("/MedicLoged", "/")
                .hasRole("ADMIN")
                .antMatchers("/PatientLoged")
                .hasAnyRole("USER")
                .and()
                .formLogin()
                .loginPage("/login").permitAll().successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        log.info("ANtes de redireccion");
                        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                        if (roles.contains("ROLE_ADMIN")) {
                            response.sendRedirect("/MedicLoged");
                            log.info("Redericcionamiento a Usuario Medico");
                        } else if (roles.contains("ROLE_USER")) {
                            response.sendRedirect("/PatientLoged");
                        }
                    }
                })
                .and()
                .exceptionHandling().accessDeniedPage("/errores/403");
    }
}
