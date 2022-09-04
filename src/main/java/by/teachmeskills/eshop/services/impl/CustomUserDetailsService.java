package by.teachmeskills.eshop.services.impl;

import by.teachmeskills.eshop.entities.User;
import by.teachmeskills.eshop.repositories.UserRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Log4j
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails;
        Optional<User> user = userRepository.findUserByLogin(username);
        if (user.isPresent()) {
            User loggingUser = user.get();
            Set<GrantedAuthority> roles = new HashSet<>();
            String roleName = loggingUser.getRole().getName();
            roles.add(new SimpleGrantedAuthority(roleName));
            userDetails = new org.springframework.security.core.userdetails.User(loggingUser.getLogin(), loggingUser.getPassword(), roles);
        } else {
            log.info("сant find user by login " + username);
            throw new UsernameNotFoundException("User wasn't found");
        }
        return userDetails;
    }
}