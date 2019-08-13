package xyz.majorkevin.bbs.service.impl;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.majorkevin.bbs.dao.RoleRepository;
import xyz.majorkevin.bbs.dao.UserRepository;
import xyz.majorkevin.bbs.entity.Role;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.service.UserService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        Optional<User> result = userRepository.findById(id);

        User user = null;
        if (result.isPresent()) {
            user = result.get();
        } else {
            throw new RuntimeException("Did not find user id - " + id);
        }
        return user;
    }

    @Override
    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public void addBaseRole(User user) {
        Role baseRole = roleRepository.getOne(Long.valueOf(1));
        user.addRole(baseRole);
    }
}
