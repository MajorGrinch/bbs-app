package xyz.majorkevin.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.majorkevin.bbs.entity.MyUserDetail;
import xyz.majorkevin.bbs.entity.Role;
import xyz.majorkevin.bbs.entity.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public MyUserDetail loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userService.findByUsername(name);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        MyUserDetail myUserDetail = new MyUserDetail();
        myUserDetail.setUser(user);
        return myUserDetail;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
