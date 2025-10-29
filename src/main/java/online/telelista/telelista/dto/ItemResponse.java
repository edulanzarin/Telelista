package online.telelista.telelista.dto;

import lombok.Data;
import online.telelista.telelista.model.ItemTelegram;
import online.telelista.telelista.model.ItemType;
import java.util.UUID;

@Data
public class ItemResponse {
    private UUID id;
    private String nome;
    private String link;
    private String descricao;
    private ItemType tipo;
    private String donoNome;
    private String categoriaNome;

    public ItemResponse(ItemTelegram item) {
        this.id = item.getId();
        this.nome = item.getNome();
        this.link = item.getLink();
        this.descricao = item.getDescricao();
        this.tipo = item.getTipo();
        this.donoNome = item.getDono().getUsername();

        if (item.getCategoria() != null) {
            this.categoriaNome = item.getCategoria().getNome();
        } else {
            this.categoriaNome = null;
        }
    }

}
