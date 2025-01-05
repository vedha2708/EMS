package com.BisagN.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.BisagN.controller.redirectLogin;
import com.BisagN.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    UserDetailsServiceImpl userDetailsService;
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { 
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());     
    }
 
   //---------------------------------------FOR CSP----------------------- 
    
    @Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().cacheControl();
		http.addFilterBefore(new CSPNonceFilter(), HeaderWriterFilter.class);
		
		http.headers().contentSecurityPolicy("default-src  'self'; script-src  'self' 'nonce-{nonce}'; "
				+ " style-src 'self' 'nonce-{nonce}' ; "  // 'nonce-{nonce}' unsafe-inline
				+ " frame-ancestors 'none'; object-src 'none'; base-uri 'self'; connect-src 'self';"
				+ " font-src 'self'; frame-src 'self';  img-src  'self' data: ; "
				+ " manifest-src 'self'; media-src 'self';");
		http.authorizeRequests().antMatchers("/logout").permitAll();
		http.authorizeRequests().antMatchers("/login/**", "/admin/**").permitAll();
		//http.authorizeRequests().antMatchers("/login/** /RegistrationUrl", "/admin/**").permitAll();
		//http.authorizeRequests().antMatchers("/login/** /about  /RegistrationUrl"  , "/admin/**").permitAll();
		http.authorizeRequests().antMatchers("/auth/login_check?targetUrl", "/checkCapchaCode").permitAll();
		http.authorizeRequests().antMatchers("/genCapchaCode").permitAll();
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
		http.authorizeRequests().anyRequest().authenticated();
		http.authorizeRequests().and().formLogin().usernameParameter("username").passwordParameter("password")
				.loginProcessingUrl("/auth/login_check").loginPage("/login").successHandler(redirectLogin())
				.failureUrl("/login?error=true");
		http.logout().logoutUrl("/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/login?logout");
		http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
		http.sessionManagement().sessionFixation().migrateSession()
				.sessionAuthenticationStrategy(registerSessionAuthStr());

	}
    
    
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//    	 http.headers().cacheControl();
//    	 //http.headers().referrerPolicy(ReferrerPolicy.SAME_ORIGIN);
//    	 //.and()
//    	 //.contentSecurityPolicy("script-src 'self' https://trustedscripts.example.com; "
//    	 //		+ "object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/");
//    	 
////    	 http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
////    	 .maximumSessions(1).expiredUrl("/login?logout")
////    	 .maxSessionsPreventsLogin(true)
////    	 .and()
////    	 .invalidSessionUrl("/login?logout").sessionFixation().migrateSession();
//    	 http.authorizeRequests().antMatchers( "/logout").permitAll();
//    	 http.authorizeRequests().antMatchers( "/login/**","/admin/**").permitAll();
//        http.authorizeRequests().antMatchers( "/auth/login_check?targetUrl","/checkCapchaCode").permitAll();
//        http.authorizeRequests().antMatchers( "/genCapchaCode").permitAll();
//        
//        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
//        http.authorizeRequests().anyRequest().authenticated();
//        http.authorizeRequests()
//        		.and().formLogin()
//        		.usernameParameter("username")
//        		.passwordParameter("password")        
//        		.loginProcessingUrl("/auth/login_check") 
//                .loginPage("/login")
//                .successHandler(redirectLogin())
//                .failureUrl("/login?error=true");
////        http
////        .logout(logout -> logout                                                
////            .logoutUrl("/logout")                                            
////            .logoutSuccessUrl("/login?logout")                                      
//////            .logoutSuccessHandler(logoutSuccessHandler)                         
////            .invalidateHttpSession(true)                                        
//////            .addLogoutHandler(logoutHandler)                                    
////            .deleteCookies("JSESSIONID")                                  
////        );
//                http
////                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
////                .clearAuthentication(true).deleteCookies("JSESSIONID").invalidateHttpSession(true)
////                .logoutSuccessUrl("/login");
//                .logout().logoutUrl("/logout")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID").logoutSuccessUrl("/login?logout");
//                http.sessionManagement( ).maximumSessions(1). maxSessionsPreventsLogin(true);
//                http.sessionManagement( ).sessionFixation( ).migrateSession( )
//                        .sessionAuthenticationStrategy( registerSessionAuthStr( ) );
////                http.csrf();
//       
//    }
    @Bean
    public SessionRegistry sessionRegistry( ) {
        SessionRegistry sessionRegistry = new SessionRegistryImpl( );
        return sessionRegistry;
    }
    @Bean
    public RegisterSessionAuthenticationStrategy registerSessionAuthStr( ) {
        return new RegisterSessionAuthenticationStrategy( sessionRegistry( ) );
    }
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
    @Bean
    public AuthenticationSuccessHandler redirectLogin(){
        return new redirectLogin();
    }
}