package elephant.mail.mail_sender.config;

import elephant.mail.mail_sender.service.AdminService;
import elephant.mail.mail_sender.service.CustomerService;
import elephant.mail.mail_sender.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ExpertService expertService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("**/register",
                        "/confirm**",
                        "/confirm-account",
                        "/registration**",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/webjars/**",
                        "/register**/**",
                        "/confirm/**",
                        "/successful**",
                        "/images/**",
                        "/**entry",
                        "/**error**",
                        "/accountVerified",
                        "/expert**",
                        "/mainPage",
                        "/mainPage/**",
                        "/expertSkills**",
                        "/addSkill**",
                        "/successfulAddSkill**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/mainPage")
                .loginProcessingUrl("/mainPage")
//                .defaultSuccessUrl("/mainPage", true)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
        ;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerService)
                .passwordEncoder(passwordEncoder());
        auth.userDetailsService(adminService)
                .passwordEncoder(passwordEncoder());
        auth.userDetailsService(expertService)
                .passwordEncoder(passwordEncoder());

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(customerService);
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(adminService);
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(expertService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


}