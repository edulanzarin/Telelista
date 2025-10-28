package online.telelista.telelista.repository;

import online.telelista.telelista.model.ItemTelegram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface ItemTelegramRepository extends JpaRepository<ItemTelegram, UUID> {

    @Query(value = "SELECT i FROM ItemTelegram i " +
            "LEFT JOIN Boost b ON b.item = i AND b.dataFim > :dataAtual " +
            "ORDER BY b.dataFim DESC NULLS LAST",

            countQuery = "SELECT COUNT(i) FROM ItemTelegram i")

    Page<ItemTelegram> findAllOrderByActiveBoost(
            @Param("dataAtual") LocalDateTime dataAtual,
            Pageable pageable);

}