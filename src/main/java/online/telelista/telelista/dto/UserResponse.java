package online.telelista.telelista.dto;

import lombok.Data;
import online.telelista.telelista.model.Usuario;

import java.util.UUID;

@Data
public class UserResponse {

    private UUID id;
    private String usuario;
    private String email;

    public UserResponse(Usuario usuario) {
        this.id = usuario.getId();
        this.usuario = usuario.getUsername();
        this.email = usuario.getEmail();
    }
}
