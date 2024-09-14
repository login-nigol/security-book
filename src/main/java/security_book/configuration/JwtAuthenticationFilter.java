package security_book.configuration;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import jakarta.servlet.FilterChain;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import io.micrometer.common.util.StringUtils;
import security_book.services.jwt.AppUserService;
import security_book.services.jwt.JwtSecurityService;


// Класс JwtAuthenticationFilter реализует фильтр аутентификации для обработки JWT токенов в запросах. Он расширяет
// OncePerRequestFilter, что гарантирует однократное выполнение фильтра для каждого запроса.
//Извлечение JWT токена из заголовка Authorization
//Проверка наличия и валидности токена
//Аутентификация пользователя на основе данных из токена
//Установка аутентификационной информации в SecurityContextHolder
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtSecurityService jwtSecurityService;
    private final AppUserService appUserService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException{

        String header = request.getHeader("Authorisation");

        if (StringUtils.isEmpty(header)
            || !org.apache.commons.lang3.StringUtils.startsWith(header, "Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        String jwt = header.substring(7);
        String email = jwtSecurityService.extractUsername(jwt);

        if (StringUtils.isNotEmpty(email)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = appUserService
                    .getDetailsService()
                    .loadUserByUsername(email);

            if (jwtSecurityService.validateToken(jwt, userDetails)) {
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}













