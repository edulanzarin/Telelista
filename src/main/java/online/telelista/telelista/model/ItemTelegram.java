package online.telelista.telelista.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Data // do LOMBOK: cria getters, setters, construtores, etc. automaticamente
@Entity // do JPA: avisa ao Spring que esta classe é uma tabela no banco de dados
@Table(name = "itens_telegram") // define o nome da tabela no banco
public class ItemTelegram {

    @Id // avisa que é o campo chave primária
    @GeneratedValue(strategy = GenerationType.UUID) // gera um uuid aleatório
    private UUID id;

    private String nome;
    private String link;
    private String descricao;

    @Enumerated(EnumType.STRING)
    private ItemType tipo;
}
