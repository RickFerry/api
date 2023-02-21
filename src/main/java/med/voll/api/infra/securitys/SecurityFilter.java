package med.voll.api.infra.securitys;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.repositories.UsuarioRepository;
import med.voll.api.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       String tokenJWT = recuperarToken(request);
       if (tokenJWT != null){
           String subject = tokenService.getSubject(tokenJWT);
           UserDetails usuario = usuarioRepository.findByLogin(subject);
           UsernamePasswordAuthenticationToken authenticated = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
           SecurityContextHolder.getContext().setAuthentication(authenticated);
       }
       filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        String header = request.getHeader("AUTHORIZATION");
        if (header == null){
            return header.replace("Bearer ", "");
        }
        return null;
    }
}
