package tn.sharing.domainheader.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.sharing.domainheader.entities.Enterprise;

import java.util.Optional;

@Repository
public interface EnterpriseRepo extends JpaRepository<Enterprise, Long> {

    Optional<Enterprise> findById(Long id);
}
