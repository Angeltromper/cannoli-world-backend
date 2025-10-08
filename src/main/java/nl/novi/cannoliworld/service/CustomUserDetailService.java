package nl.novi.cannoliworld.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserService userService;
    public CustomUserDetailService(UserService userService) {
        this.userService = userService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        return userService.getUser(username)
                .orElseThrow(() ->
                        new org.springframework.security.core.userdetails.UsernameNotFoundException(
                                "User " + username + " not found"));
    }
}
