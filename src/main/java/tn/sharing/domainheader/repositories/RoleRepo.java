package tn.sharing.domainheader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sharing.domainheader.entities.ERole;
import tn.sharing.domainheader.entities.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByRoleName(ERole role);
}
