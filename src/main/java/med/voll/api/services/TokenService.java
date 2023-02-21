package med.voll.api.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.entities.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public String gerarToken(Usuario usuario) {
        try {
            return JWT.create().withIssuer("API voll.med").withSubject(usuario.getLogin()).withExpiresAt(dataExpiracao()).sign(Algorithm.HMAC256(""));
        } catch (JWTCreationException ex) {
            throw new RuntimeException("Erro ao gerar o token!", ex);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
