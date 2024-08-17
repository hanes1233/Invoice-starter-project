package cz.itnetwork.entity.repository;
//region imports
import cz.itnetwork.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//endregion

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
    List<PersonEntity> findByHidden(boolean hidden);
    List<PersonEntity> findByIdentificationNumber(String identificationNumber);
}
