package pl.coderstrust.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${spring.security.username}")
  private String userName;
  @Value("${spring.security.userpassword}")
  private String userPassword;
  @Value("${spring.security.adminname}")
  private String adminName;
  @Value("${spring.security.adminpassword}")
  private String adminPassword;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers("/h2-console/**").hasAnyRole("ADMIN")
        .anyRequest().authenticated()
        .and().formLogin()
        .and().csrf().disable().headers().frameOptions().disable();
  }

  @Autowired
  public void securityUsers(AuthenticationManagerBuilder authentication) throws Exception {
    authentication.inMemoryAuthentication()
        .withUser(userName).password(userPassword).roles("USER")
        .and()
        .withUser(adminName).password(adminPassword).roles("ADMIN");
  }
}
