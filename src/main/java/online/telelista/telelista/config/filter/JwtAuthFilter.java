package online.telelista.telelista.config.filter; // (ou o pacote que você criou)

import online.telelista.telelista.service.TokenService;
import online.telelista.telelista.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Avisa ao Spring para gerenciar este filtro
// OncePerRequestFilter garante que ele rode apenas UMA VEZ por requisição
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService; // Nosso UserDetailsService

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 1. Pegar o cabeçalho "Authorization"
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 2. Verifica se o cabeçalho existe e se começa com "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Se não, só continua o fluxo (o Spring Security vai barrar depois)
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extrai o token (remove o "Bearer ")
        jwt = authHeader.substring(7);

        // 4. Pega o username de dentro do token
        username = tokenService.getUsernameFromToken(jwt);

        // 5. Se o username existe E o usuário ainda não está autenticado no "contexto"
        // do Spring
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 6. Carrega o usuário do banco de dados
            UserDetails userDetails = this.usuarioService.loadUserByUsername(username);

            // 7. Valida o token (compara com o usuário do banco)
            if (tokenService.validateToken(jwt, userDetails)) {

                // 8. Se o token for válido, AUTENTICA O USUÁRIO
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null, // Credenciais (senha) são nulas, pois estamos usando token
                        userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 9. Coloca o usuário no Contexto de Segurança do Spring
                // A partir daqui, o Spring sabe que o usuário está LOGADO!
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 10. Continua o fluxo da requisição
        filterChain.doFilter(request, response);
    }
}