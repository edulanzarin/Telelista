package online.telelista.telelista.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "itens_telegram")
public class ItemTelegram {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nome;
    private String link;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private ItemType tipo;

    @ManyToOne
    @JoinColumn(name = "dono_usuario_id", nullable = false)
    private Usuario dono;
}
