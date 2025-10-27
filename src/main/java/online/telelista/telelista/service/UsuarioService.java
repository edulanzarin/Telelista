package online.telelista.telelista.service;

import online.telelista.telelista.model.Usuario;
import online.telelista.telelista.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
    }

    public Usuario cadastrarUsuario(Usuario usuario) {
        String senhaPura = usuario.getSenha();
        String senhaCriptografada = passwordEncoder.encode(senhaPura);
        usuario.setSenha(senhaCriptografada);
        return usuarioRepository.save(usuario);
    }
}