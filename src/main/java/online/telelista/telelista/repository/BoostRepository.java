package online.telelista.telelista.repository;

import online.telelista.telelista.model.Boost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoostRepository extends JpaRepository<Boost, UUID> {

}
