package org.iksoft.Security;

import org.iksoft.Service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author IK
 */

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!userService.isUserRegistered(username))
            throw new UsernameNotFoundException("Not found username "+username);

        return new MyUserDetails(userService.getUserByUsername(username));
    }
}
