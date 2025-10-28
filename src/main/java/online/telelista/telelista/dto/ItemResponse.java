package online.telelista.telelista.dto;

import lombok.Data;
import online.telelista.telelista.model.ItemTelegram;
import online.telelista.telelista.model.ItemType;
import java.util.UUID;

@Data
public class ItemResponse {
    // TODO: implementar lógica de categoria

    private UUID id;
    private String nome;
    private String link;
    private String descricao;
    private ItemType tipo;
    private String donoNome;

    public ItemResponse(ItemTelegram item) {
        this.id = item.getId();
        this.nome = item.getNome();
        this.link = item.getLink();
        this.descricao = item.getDescricao();
        this.tipo = item.getTipo();
        this.donoNome = item.getDono().getUsername();
    }

}
