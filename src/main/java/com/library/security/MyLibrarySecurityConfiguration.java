package com.library.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.library.dataaccessobject.ReaderRepository;
import com.library.dataaccessobject.AuthorRepository;
import com.library.domainobject.ReaderDO;
import com.library.security.dataaccessobject.UserRepository;
import com.library.security.domainobject.RoleDO;
import com.library.security.domainobject.UserDO;
import com.library.security.domainvalue.RolesEnum;
import com.library.domainobject.AuthorDO;

@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
@Profile("!test")
public class MyLibrarySecurityConfiguration extends WebSecurityConfigurerAdapter
{

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(MyLibrarySecurityConfiguration.class);

    @Autowired
    UserRepository userRepo;


    public MyLibrarySecurityConfiguration()
    {
        super(false);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
        .authorizeRequests()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .and()
        .httpBasic();
    }
    
    @Override
    protected AuthenticationManager authenticationManager() throws Exception
    {

        return new ProviderManager(Arrays.asList((AuthenticationProvider) new AuthenticationProvider()
        {

            @Override
            public boolean supports(Class<?> authentication)
            {
                //return false;
                return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));

            }


            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException
            {
                String username = authentication.getPrincipal() + "";
                LOG.info("username: " + username);
                String password = authentication.getCredentials() + "";
                LOG.debug("Password: " + password);
                
                UserDO user = userRepo.findByUsername(username);

                // TODO: Work on encrypting password using salted MD5
                if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password))
                {
                    List<RoleDO> userRights = user.getRoles();
                    return new UsernamePasswordAuthenticationToken(username, password, userRights.stream().map(x -> new SimpleGrantedAuthority(x.getRole().toString())).collect(Collectors.toList()));
                }

                throw new BadCredentialsException("1000");

            }
        }));
    }

}
