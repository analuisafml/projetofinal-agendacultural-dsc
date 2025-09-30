package br.com.agendacultural.api.services;

import br.com.agendacultural.api.models.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withIssuer("AgendaCultural API")
                .withSubject(usuario.getEmail())
                .withExpiresAt(dataExpiracao())
                .sign(algorithm);
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }


    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("AgendaCultural API")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (Exception exception){
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
}