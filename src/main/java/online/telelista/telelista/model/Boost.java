package online.telelista.telelista.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "boosts")
public class Boost {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // @ManyToOne: muitos registros de boost podem estar associado a um ItemTelegram
    @ManyToOne
    // @JoinColumn: define qual sera a coluna no banco boosts que fara a ligaçao
    @JoinColumn(name = "item_telegram_id", nullable = false)
    private ItemTelegram item;

    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
}
