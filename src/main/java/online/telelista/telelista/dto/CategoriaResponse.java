package online.telelista.telelista.dto;

import java.util.UUID;

import lombok.Data;
import online.telelista.telelista.model.Categoria;

@Data
public class CategoriaResponse {

    private UUID id;
    private String nome;

    public CategoriaResponse(Categoria categoria) {
        this.id = categoria.getId();
        this.nome = categoria.getNome();
    }
}
