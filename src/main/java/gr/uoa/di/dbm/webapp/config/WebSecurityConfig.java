package gr.uoa.di.dbm.webapp.config;

import gr.uoa.di.dbm.webapp.dao.GenericDAO;
import gr.uoa.di.dbm.webapp.entity.AppRole;
import gr.uoa.di.dbm.webapp.entity.AuditLog;
import gr.uoa.di.dbm.webapp.entity.Location;
import gr.uoa.di.dbm.webapp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GenericDAO<AppRole> appRoleGenericDAO() {
        GenericDAO<AppRole> appRoleGenericDAO = new GenericDAO<>();
        appRoleGenericDAO.setEntityClass(AppRole.class);
        return appRoleGenericDAO;
    }

    @Bean
    public GenericDAO<Location> locationGenericDAO() {
        GenericDAO<Location> locationGenericDAO = new GenericDAO<>();
        locationGenericDAO.setEntityClass(Location.class);
        return locationGenericDAO;
    }

    @Bean
    public GenericDAO<AuditLog> auditLogGenericDAO() {
        GenericDAO<AuditLog> auditLogGenericDAO = new GenericDAO<>();
        auditLogGenericDAO.setEntityClass(AuditLog.class);
        return auditLogGenericDAO;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        // The pages does not require login
        http.authorizeRequests().antMatchers("/login", "/logout").permitAll();

        //  pages require login as ROLE_USER or ROLE_ADMIN.
        // If no login, it will redirect to /login page.
        http.authorizeRequests().antMatchers("/","/welcome","/insert","/searchSuggestions/**","/searchResults","/sp/**","/spResults").access("hasRole('ROLE_USER')");

        // For ADMIN only.
        http.authorizeRequests().antMatchers("/admin").access("hasRole('ROLE_ADMIN')");

        //For USER only
        //http.authorizeRequests().antMatchers("/insert","/insert/addRequest").access("hasRole('ROLE_USER')");


        // When the user has logged in as XX.
        // But access a page that requires role YY,
        // AccessDeniedException will be thrown.
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/error403");

        // Config for Login Form
        http.authorizeRequests().and().formLogin()//
                // Submit URL of login page.
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/login")//
                .defaultSuccessUrl("/welcome")//
                .failureUrl("/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password")
                // Config for Logout Page
                .and().logout().logoutSuccessUrl("/login?logoutSuccess=true");

        // Config Remember Me.
        http.authorizeRequests().and() //
                .rememberMe().tokenRepository(this.persistentTokenRepository()) //
                .tokenValiditySeconds(1 * 24 * 60 * 60); // 24h

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

}
