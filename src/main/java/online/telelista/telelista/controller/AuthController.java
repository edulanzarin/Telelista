package online.telelista.telelista.controller;

import online.telelista.telelista.dto.AuthRequest;
import online.telelista.telelista.dto.AuthResponse;
import online.telelista.telelista.dto.RegisterRequest;
import online.telelista.telelista.dto.UserResponse;
import online.telelista.telelista.model.Usuario;
import online.telelista.telelista.service.TokenService;
import online.telelista.telelista.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsuario(),
                authRequest.getSenha());

        Authentication authentication = authenticationManager.authenticate(authToken);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = tokenService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest) {

        Usuario novoUsuario = new Usuario();
        novoUsuario.setUsuario(registerRequest.getUsuario());
        novoUsuario.setEmail(registerRequest.getEmail());
        novoUsuario.setSenha(registerRequest.getSenha());

        Usuario usuarioSalvo = usuarioService.cadastrarUsuario(novoUsuario);

        UserResponse response = new UserResponse(usuarioSalvo);

        return ResponseEntity.status(201).body(response);
    }
}