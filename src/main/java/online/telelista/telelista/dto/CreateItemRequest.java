package online.telelista.telelista.dto;

import lombok.Data;
import online.telelista.telelista.model.ItemType;

@Data
public class CreateItemRequest {

    private String nome;
    private String link;
    private String descricao;
    private ItemType tipo;
}
