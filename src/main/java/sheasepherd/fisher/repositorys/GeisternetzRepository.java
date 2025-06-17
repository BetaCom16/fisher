package sheasepherd.fisher.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import sheasepherd.fisher.entitys.Geisternetz;
import sheasepherd.fisher.entitys.Mitglied;

import java.util.List;

public interface GeisternetzRepository extends JpaRepository<Geisternetz, Long> {
    List<Geisternetz> findByStatusAndBergendePersonIsNull(String status);
    List<Geisternetz> findByBergendePersonAndStatusNot(Mitglied bergendePerson, String status);
    List<Geisternetz> findByStatus(String status);

}
