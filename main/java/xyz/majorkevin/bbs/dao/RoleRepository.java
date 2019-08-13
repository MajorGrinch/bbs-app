package xyz.majorkevin.bbs.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.majorkevin.bbs.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
