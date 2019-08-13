package xyz.majorkevin.bbs.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.majorkevin.bbs.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String name);
}
