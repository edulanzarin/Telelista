package online.telelista.telelista.controller;

import online.telelista.telelista.dto.ChangePasswordRequest;
import online.telelista.telelista.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/me/change-password")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody ChangePasswordRequest request) {

        usuarioService.alterarSenha(userDetails, request);
        return ResponseEntity.ok("Senha alterada com sucesso.");
    }
}
