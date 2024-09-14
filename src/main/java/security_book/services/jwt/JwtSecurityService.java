package security_book.services.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

// Класс JwtSecurityService предоставляет функциональность для работы с JSON Web Tokens в контексте безопасности приложения.
// Он отвечает за генерацию, валидацию и извлечение информации из JWT токенов.
@Service
public class JwtSecurityService {

    private static final String SECRET_KAY = "6yU3AaLTrj/YSKQtYF6yU3/YSKAaLTIv9aRtGxOcU39h7T/aRtGxO+syA=";

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KAY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) { // создание JWT.
        return Jwts.builder()
                .subject(userDetails.getUsername()) // добавляет "subject" (предмет) в JWT.
                // .claim("field", "value") // Добавление других нужных полей.
                .issuedAt(new Date(System.currentTimeMillis())) // когда токен был выпущен (issued).
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // когда токен истечет (expiration).
                .signWith(getSigningKey()) // гарантирует, что токен не был изменен после его создания.
                .compact(); // возвращает токен в виде строки (JWT).
        // .claim("field", "value"): "Claims" — это дополнительная информация, которую можно передать в токен,
        // например, роли пользователя, идентификатор и т.д: .claim("role", userDetails.getAuthorities()).
    }

    public String generateRefreshToken(Map<String, String> claims, UserDetails userDetails) {
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 60))
                .signWith(getSigningKey())
                .compact();
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Получение имени юзера (он же почта)
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Когда срок действия заканчивается
    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    // Когда выдан токен
    public Date extractIssuedAt(String token) {
        return extractClaims(token, Claims::getIssuedAt);
    }

    // Метод проверки срока действия токена
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Метод для валидации токена
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }
}

















