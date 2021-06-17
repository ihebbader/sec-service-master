package org.sid.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration // pout noter que notre classe a la priorité sur les autre classe de l'application
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/login/**","/register/**","/exist**/**","/email","/resetPassword","/changePassword","/photoUser/{id}","/saveProperty/**","/getProperty/{id}","/getAllProperty","/saveP/**","/saveEntity/**","/test/{role}"
                ,"/getDataModleToDo/{username}","/getuser**/**","/addroles2**/**","/v2/api-docs","/swagger-ui.html","/spring-security-rest/swagger-ui/**").permitAll();
        http.authorizeRequests().antMatchers("/appUsers/**","/appRoles/**","/adduser","addroles**/**","/searchByUsername**/**","/searchByEmail**/**","/getAllModel/**","/createDataModel/**","/deleteDataFlow/**","/updateDataModel/**","/addNewEntity/{id}","/deleteEntity/{id}","/getModel/{id}",
                "/updateEntity**/**","/addUserToEntity/{id}","/addGroup**/**","/getNotification**/**","/getDisabledAccount**/**",
                "/deleteUserFromGroup/{id}","/deleteGroup/{id}","/verfyDate/{id}"
                ,"/AcceptRequest**/**","/RefuseRequest**/**").hasAuthority("ADMIN");
        http.authorizeRequests().antMatchers("/getAllForSuperVisor**/**").hasAuthority("SUPERVISEUR");
        http.authorizeRequests().antMatchers("/getusers","updateUser","/delete**/**","/uploadPhoto/{id}","/updateMotDePasse/{username}","/verifyPass/{username}","/executEntity**/**").hasAnyAuthority("ADMIN","USER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager()));
        http.addFilterBefore(new JWTAuthorizationFiler(), UsernamePasswordAuthenticationFilter.class);
    }
}
