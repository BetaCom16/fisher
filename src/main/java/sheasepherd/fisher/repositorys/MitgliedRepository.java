package sheasepherd.fisher.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import sheasepherd.fisher.entitys.Mitglied;

public interface MitgliedRepository extends JpaRepository<Mitglied, Long> {
    Mitglied findByEmail(String email);
}
