
package security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.company.understandablepractice.model.User;
import ru.company.understandablepractice.service.UserService;

/**
 *
 * @author mish
 */
@Slf4j
@Component
public class AuthenticationService implements AuthenticationProvider {
    @Autowired
    private UserService userDataService;

    @Autowired
    private BCryptPasswordEncoder encoder;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Starting auth ");
        Optional<User> user = userDataService.findUserByLogin(authentication.getPrincipal().toString());
        if(user.isEmpty()){
           throw new NoSuchUserException("No such user");
        }
        if(!encoder.matches(
            authentication.getCredentials().toString(),user.get().getUserCredentials().getPassword())){
           throw new BadCredentialsException("Bad credentials");
        }
        return new UsernamePasswordAuthenticationToken(user.get().getUserCredentials().getLogin()
        ,user.get().getUserCredentials().getPassword(), null);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
