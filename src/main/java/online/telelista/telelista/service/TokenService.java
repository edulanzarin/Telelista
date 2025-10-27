package online.telelista.telelista.service;

import io.jsonwebtoken.Claims; // <-- IMPORTAR
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Service
public class TokenService {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private long jwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256) // Corrigido
                .compact();
    }

    // --- NOVO MÉTODO 1: PEGAR O "DONO" (USERNAME) DO TOKEN ---
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // Usa a mesma chave para "abrir"
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // Retorna o username (subject)
    }

    // --- NOVO MÉTODO 2: VALIDAR O TOKEN ---
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            // Tenta "abrir" o token com a chave
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            // Se conseguir abrir (não deu erro):
            // 1. Pega o username do token
            final String username = getUsernameFromToken(token);
            // 2. Verifica se o username do token é o mesmo do UserDetails
            // E se o token não está expirado
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            // Se deu erro (expirado, assinatura inválida, etc)
            return false;
        }
    }

    // --- MÉTODO AUXILIAR PRIVADO ---
    private boolean isTokenExpired(String token) {
        final Date expiration = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}