package online.telelista.telelista.dto;

import lombok.Data;

@Data
public class RegisterRequest {

    private String usuario;
    private String email;
    private String senha;
}
