package Springboot.springboot.Repository;

import Springboot.springboot.Entity.UserLoginAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginAuditRepository extends JpaRepository<UserLoginAudit, Long> {
}
