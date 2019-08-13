package xyz.majorkevin.bbs.service;

import xyz.majorkevin.bbs.entity.User;

public interface UserService {
    void save(User user);

    User findById(Long id);

    User findByUsername(String name);

    void addBaseRole(User user);

}
