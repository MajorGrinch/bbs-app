package xyz.majorkevin.bbs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.majorkevin.bbs.dao.RoleRepository;
import xyz.majorkevin.bbs.dao.UserRepository;
import xyz.majorkevin.bbs.entity.Role;
import xyz.majorkevin.bbs.entity.User;
import xyz.majorkevin.bbs.service.UserService;
import xyz.majorkevin.bbs.service.VoteService;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private VoteService voteService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, VoteService voteService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.voteService = voteService;
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
